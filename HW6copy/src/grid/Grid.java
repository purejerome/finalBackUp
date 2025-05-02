package grid;

import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.locationtech.jts.geom.*;
import feature.StreetSegment;

/**
 * Grid of the drawn items.
 * @author Jerome Donfack
 *
 */
public class Grid
{
  private HashMap<GridTuple<Double, Double>, HashSet<StreetSegment>> grid;
  private HashMap<GridTuple<Double, Double>, ArrayList<LineString>> gridLines;
  private Rectangle2D.Double bounds;
  private HashMap<String, StreetSegment> elementMap;
  private GeometryFactory geometryFactory;
  
  /**
   * Creates new grid.
   */
  public Grid()
  {
    this.geometryFactory = new GeometryFactory();
  }
  
  /**
   * Creates new grid with elements to map and bounds to follow.
   * @param elementMap - Items to store in grid.
   * @param bounds - Bounds of items.
   */
  public Grid(final HashMap<String, StreetSegment> elementMap, 
      final Rectangle2D.Double bounds)
  {
    this();
    this.bounds = bounds;
    this.elementMap = elementMap;
    this.createGrid();
    this.populateGrid();
  }
  
  /**
   * Return corresponding grid.
   * @return - The grid returned.
   */
  public HashMap<GridTuple<Double, Double>, HashSet<StreetSegment>> getGrid()
  {
    return this.grid;
  }
  
  /**
   * Sets the bounds of the grid.
   * @param bounds - Bounds to use.
   */
  public void setBounds(final Rectangle2D.Double bounds)
  {
    this.bounds = bounds;
  }
  
  /**
   * Get bounds of the grid.
   * @return - Bounds of grid.
   */
  public Rectangle2D.Double getBounds()
  {
    return this.bounds;
  }
  
  /**
   * Sets the items to add to the grid.
   * @param elementMap - Items to add to grid.
   */
  public void setElementMap(final HashMap<String, StreetSegment> elementMap)
  {
    this.elementMap = elementMap;
  }
  
  /**
   * Gets targeted grid, and the 3x3 grid surrounding the targeted grid.
   * @param lat - Latitude.
   * @param lon - Longitude.
   * @return - Surrounding segments in the 3x3 grid.
   */
  public HashSet<HashSet<StreetSegment>> getSurroundingItems(final double lat,
      final double lon)
  {
    double startX = bounds.getMinX();
    double startY = bounds.getMinY();
    HashSet<HashSet<StreetSegment>> surroundingItems = new HashSet<>();
    
    double x = Math.floor((lon - startX) / (bounds.getMaxX() - startX) * 100);
    double y = Math.floor((lat - startY) / (bounds.getMaxY() - startY) * 100);
    
    double topBorder = Math.max(0, y - 1);
    double leftBorder = Math.max(0, x - 1);
    
    for(double h = topBorder, count = 0; 
        h < 100 && count < 3; h++, count++)
    {
      for(double w = leftBorder, inCount = 0; 
          w < 100 && inCount < 3; w++, inCount++)
      {
        GridTuple<Double, Double> coords = new GridTuple<>(w, h);
        if(this.grid.get(coords) != null)
        {
          if(this.grid.get(coords).size() > 0)
          {
            surroundingItems.add(this.grid.get(coords));
          }
        }
      }
    }
    return surroundingItems;
  }
  
  /**
   * Creates the grid.
   */
  public void createGrid()
  {
    this.grid = new HashMap<>();
    this.gridLines = new HashMap<>();
    double widthStep = bounds.getWidth() / 100;
    double heightStep = bounds.getHeight() / 100;
    double startX = bounds.getMinX();
    double startY = bounds.getMinY();
    
    for(double w = 0.0; w < 100; w++)
    {
      for(double h = 0.0; h < 100; h++)
      {
        GridTuple<Double, Double> coords = new GridTuple<>(w, h);
        this.grid.put(coords, new HashSet<>());
      }
    }
    
    for(GridTuple<Double, Double> boundCoords : this.grid.keySet())
    {
      double tLX = boundCoords.getLeft() * widthStep + startX;
      double tLY = boundCoords.getRight() * heightStep + startY;
      double bRX = tLX + widthStep;
      double bRY = tLY + heightStep;
      
      Coordinate[] topLineCoords = new Coordinate[]
          {new Coordinate(tLX, tLY), new Coordinate(bRX, tLY)};
      Coordinate[] bottomLineCoords = new Coordinate[]
          {new Coordinate(tLX, bRY), new Coordinate(bRX, bRY)};
      Coordinate[] leftLineCoords = new Coordinate[]
          {new Coordinate(tLX, tLY), new Coordinate(tLX, bRY)};
      Coordinate[] rightLineCoords = new Coordinate[]
          {new Coordinate(bRX, tLY), new Coordinate(bRX, bRY)};
      
      LineString topLine = geometryFactory.createLineString(topLineCoords);
      LineString bottomLine = geometryFactory.createLineString(bottomLineCoords);
      LineString leftLine = geometryFactory.createLineString(leftLineCoords);
      LineString rightLine = geometryFactory.createLineString(rightLineCoords);
      ArrayList<LineString> boundaries = new ArrayList<>();
      
      boundaries.add(topLine);
      boundaries.add(bottomLine);
      boundaries.add(leftLine);
      boundaries.add(rightLine);
      this.gridLines.put(boundCoords, boundaries);
    }
  }
  
  /**
   * Fills in the grid based on the items in the elementMap.
   */
  public void populateGrid()
  {
    double widthStep = bounds.getWidth() / 100;
    double heightStep = bounds.getHeight() / 100;
    double startX = bounds.getMinX();
    double startY = bounds.getMinY();
    
    for(StreetSegment seg : this.elementMap.values())
    {
      Point2D.Double headPoint = seg.getHeadPoint();
      Point2D.Double tailPoint = seg.getTailPoint();
      
      double minX = Math.min(headPoint.getX(), tailPoint.getX());
      double minY = Math.min(headPoint.getY(), tailPoint.getY());
      double maxX = Math.max(headPoint.getX(), tailPoint.getX());
      double maxY = Math.max(headPoint.getY(), tailPoint.getY());
      
      for(GridTuple<Double, Double> boundCoords : this.grid.keySet())
      {
        double tLX = boundCoords.getLeft() * widthStep + startX;
        double tLY = boundCoords.getRight() * heightStep + startY;
        double bRX = tLX + widthStep;
        double bRY = tLY + heightStep;
        
        if(headPoint.getX() >= tLX && headPoint.getX() <= bRX 
            && headPoint.getY() >= tLY && headPoint.getY() <= bRY)
        {
          this.grid.get(boundCoords).add(seg);
          continue;
        }
        
        if(tailPoint.getX() >= tLX && tailPoint.getX() <= bRX 
            && tailPoint.getY() >= tLY && tailPoint.getY() <= bRY)
        {
          this.grid.get(boundCoords).add(seg);
          continue;
        }
        
        if (!(maxX < tLX || minX > bRX || maxY < tLY || minY > bRY))
        {
          ArrayList<Coordinate> compLineCoords = new ArrayList<>();
          
          PathIterator pathIterator = 
              seg.getGeographicShape().getShape().getPathIterator(null);
          double[] coords = new double[6];
          while (!pathIterator.isDone())
          {
            pathIterator.currentSegment(coords);
            compLineCoords.add(new Coordinate(coords[0], coords[1]));
            pathIterator.next();
          }
          LineString compLine = this.geometryFactory.createLineString(
              compLineCoords.toArray(new Coordinate[0]));
          ArrayList<LineString> gLines = this.gridLines.get(boundCoords);
          for(LineString line: gLines)
          {
            if(line.intersects(compLine))
            {
              this.grid.get(boundCoords).add(seg);
              break;
            }
          }
        }
      }
    }
  }
  
}
