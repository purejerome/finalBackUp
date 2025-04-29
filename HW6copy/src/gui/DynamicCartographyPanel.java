package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

import feature.StreetSegment;
import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;
import grid.Grid;
import grid.GridTuple;
import matching.MapMatchingFactory;

/**
 * Dynamic screen to allow for movement on gps screen.
 * @param <T> The type of the data
 * @author Jerome Donfack
 *
 */
public class DynamicCartographyPanel<T> extends CartographyPanel<T> 
    implements GPSObserver, PropertyChangeListener
{
  private static final long serialVersionUID = 1L;
  
  private GPGGASentence gpgga;
  private MapProjection proj;
  private Grid grid;
  private ArrayList<Path2D.Double> gridLines;
  private Queue<Point2D.Double> inertia;
  private Map<String, Map<String, T>> allPaths;
  private Map<String, T> path;
  private int intertiaReset;
  private int reCalcReset;
  
  /**
   * Creates new DynamicCartographyPanel.
   * @param model - Model to use for orginizing information.
   * @param cartographer - Cartographer used to make the conversion calculations.
   * @param proj - Projection used.
   * @param grid - Grid that has all related street segments.
   * @param allPaths - All paths to dest.
   * @param path - Current path following to dest.
   */
  public DynamicCartographyPanel(final CartographyDocument<T> model, 
      final Cartographer<T> cartographer, final MapProjection proj, 
      final Grid grid, final Map<String, Map<String, T>> allPaths,
      final Map<String, T> path)
  {
    super(model, cartographer);
    this.proj = proj;
    this.grid = grid;
    this.gridLines = new ArrayList<>();
    this.inertia = new LinkedList<>();
    this.allPaths = allPaths;
    this.path = path;
    this.intertiaReset = 0;
    this.reCalcReset = 0;
    createGridLines();
  }
  
  @Override
  public void propertyChange(final PropertyChangeEvent evt) 
  {
    if ("path".equals(evt.getPropertyName())) 
    {
      this.path = (Map<String, T>) evt.getNewValue();
      repaint();
    }
  }
  
  private void createGridLines() 
  {
    for(GridTuple<Double, Double> gridCell : this.grid.getGrid().keySet())
    {
      
      double widthStep = this.grid.getBounds().getWidth() / 100;
      double heightStep = this.grid.getBounds().getHeight() / 100;
      double startX = this.grid.getBounds().getMinX();
      double startY = this.grid.getBounds().getMinY();
      
      double tLX = gridCell.getLeft() * widthStep + startX;
      double tLY = gridCell.getRight() * heightStep + startY;
      double bRX = tLX + widthStep;
      double bRY = tLY + heightStep;
      
      Path2D.Double topLine = new Path2D.Double();
      Path2D.Double bottomLine = new Path2D.Double();
      Path2D.Double leftLine = new Path2D.Double();
      Path2D.Double rightLine = new Path2D.Double();
      
      topLine.moveTo(tLX, tLY);
      topLine.lineTo(bRX, tLY);
      
      bottomLine.moveTo(tLX, bRY);
      bottomLine.lineTo(bRX, bRY);
      
      leftLine.moveTo(tLX, tLY);
      leftLine.lineTo(tLX, bRY);
      
      rightLine.moveTo(bRX, tLY);
      rightLine.lineTo(bRX, bRY);
      
      this.gridLines.add(rightLine);
      this.gridLines.add(leftLine);
      this.gridLines.add(bottomLine);
      this.gridLines.add(topLine);
    }
    
  }

  @Override
  public void handleGPSData(final String sentence)
  {
    this.gpgga = GPGGASentence.parseGPGGA(sentence);
    repaint();
  }
  
  /**
   * Render the items on the screen.
   * @param g - Graphics to use for rendering.
   */
  public void paint(final Graphics g)
  {
    if (this.gpgga == null) 
    {
      return;
    }
    double latitude = this.gpgga.getLatitude();
    double longitude = this.gpgga.getLongitude();
    
    if (Double.isNaN(latitude) || Double.isNaN(longitude)) 
    {
      return;
    }
    
    double[] projections = this.proj.forward(new double[] {longitude, latitude});
    double projLong = projections[0];
    double projLat = projections[1];
    double left = projLong - 1;
    double top = projLat - 1;
    
    HashSet<HashSet<StreetSegment>> surroundingSegs = grid.getSurroundingItems(projLat, projLong);
    
    
    Rectangle2D.Double zoomedBounds = new Rectangle2D.Double(left, top, 2, 2);
    this.zoomStack.add(0, zoomedBounds);
    
    Graphics2D g2 = (Graphics2D) g;
    Rectangle screenBounds = g2.getClipBounds();
    Rectangle2D.Double bounds = this.zoomStack.getFirst();
    AffineTransform at = displayTransform.getTransform(screenBounds, bounds);
    
    Point2D.Double modelPoint = new Point2D.Double(projLong, projLat);
    
    ArrayList<MapMatchingFactory> factories = new ArrayList<>();
    for(HashSet<StreetSegment> segGrid : surroundingSegs)
    {
      MapMatchingFactory worker = new MapMatchingFactory(segGrid, modelPoint, this.inertia);
      factories.add(worker);
      worker.execute();
    }
    double minDistance = Double.POSITIVE_INFINITY;
    Point2D.Double newPoint = null;
    StreetSegment bestSeg = null;
    
    
    for(MapMatchingFactory worker : factories)
    {
      try
      {
        GridTuple<GridTuple<Double, 
            Point2D.Double>, StreetSegment> minDisCloseSeg = worker.get();
        if(minDisCloseSeg != null && minDisCloseSeg.getLeft().getLeft() < minDistance)
        {
          minDistance = minDisCloseSeg.getLeft().getLeft();
          newPoint = minDisCloseSeg.getLeft().getRight();
          bestSeg = minDisCloseSeg.getRight();
        }
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      catch (ExecutionException e)
      {
        e.printStackTrace();
      }
    }
    if(path != null)
    {
      if(path.keySet().contains(bestSeg.getID()))
      {
        this.reCalcReset = 0;
      }
      else
      {
        this.reCalcReset += 1;
      }
      if(this.reCalcReset == 40)
      {
        this.path = this.allPaths.get(bestSeg.getID());
        this.reCalcReset = 0;
      }
    }
    else
    {
      if(!this.allPaths.isEmpty() && this.path == null)
      {
        this.path = this.allPaths.get(bestSeg.getID());
      }
      this.reCalcReset = 0;
    }
    
    if(this.path != null)
    {
      this.model.setHighlighted(this.path);
    }

    super.paint(g);
    
    if(this.intertiaReset == 0)
    {
      this.inertia.offer(modelPoint);
    }
    this.intertiaReset = (this.intertiaReset + 1) % 4;
    
    if(this.inertia.size() > 3)
    {
      this.inertia.poll();
    }
    
    if(newPoint != null)
    {
      modelPoint = newPoint;
    }
    
    
    Point2D screenPoint = at.transform(modelPoint, null);
    
    int radius = 4;
    int diameter = radius * 2;
    g2.setColor(Color.RED);
    g2.fillOval((int)(screenPoint.getX() - radius), 
        (int)(screenPoint.getY() - radius), diameter, diameter);
  }
}
