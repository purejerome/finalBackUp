package geography;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import gui.CartographyDocument;

/**
 * Reads shapes from file to print on screen.
 * @author Jerome Donfack
 *
 */
public class GeographicShapesReader
{
  private BufferedReader in;
  private MapProjection proj;
  
  /**
   * Creates a new shape reader.
   * @param is - Shapes to read.
   * @param proj - Type of map projection.
   */
  public GeographicShapesReader(final InputStream is, final MapProjection proj)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.proj = proj;
  }
  
  /**
   * Reads in the shape data.
   * @return The cartography document used.
   */
  public CartographyDocument<GeographicShape> read()
  {
    String line;
    String splitParam = "\\s+";
    HashMap<String, GeographicShape> elementMap = new HashMap<>();
    double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
    double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
    try
    {
      while((line = in.readLine()) != null)
      {
        String[] words = line.split(splitParam);
        String type = words[1];
        if(type.toLowerCase().equals("comment"))
        {
          in.readLine();
        }
        else
        {
          PiecewiseLinearCurve inputShape = null;
          String id = words[3];
          
          if(type.toLowerCase().equals("piecewiselinearcurve"))
          {
            inputShape = new PiecewiseLinearCurve(id);
          }
          else if (type.toLowerCase().equals("polygon"))
          {
            inputShape = new Polygon(id);
          }
          line = in.readLine();
          while(!line.equals("END"))
          {
            String[] splitStringCoords = line.trim().split(splitParam);
            line = in.readLine();
            double coord1 = Double.parseDouble(splitStringCoords[0]);
            double coord2 = Double.parseDouble(splitStringCoords[1]);
            double[] projectedCoords = proj.forward(new double[] {coord1, coord2});
            inputShape.add(projectedCoords);
            minX = Math.min(minX, projectedCoords[0]);
            maxX = Math.max(maxX, projectedCoords[0]);
            minY = Math.min(minY, projectedCoords[1]);
            maxY = Math.max(maxY, projectedCoords[1]);
          }
          elementMap.put(id, inputShape);
        }
      } 
    }
    catch (IOException e)
    {
      System.out.println("File does not exsist.");
    }
    Rectangle2D.Double bounds = new Rectangle2D.Double(minX, minY, 
        Math.abs(maxX - minX), Math.abs(maxY - minY));
    return new CartographyDocument<GeographicShape>(elementMap, bounds);
  }
}
