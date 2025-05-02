package feature;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import geography.GeographicShape;
import grid.Grid;
import gui.CartographyDocument;

/**
 * Reads in street segment data to be printed.
 * @author Jerome Donfack
 *
 */
public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;
  
  /**
   * Creates a new street reader.
   * @param is - Streets to read in.
   * @param shapes - Geogprahic shapes to use with reader.
   */
  public StreetsReader(final InputStream is, 
      final CartographyDocument<GeographicShape> shapes)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.geographicShapes = shapes;
  }
  
  /**
   * Reads street segment data, and returns mappable street segments.
   * @param streets - Storage for all streets.
   * @param grid - Grid to initialize based on street segments read.
   * @return - The mappable street segments.
   * @throws IOException
   */
  public CartographyDocument<StreetSegment> read(final Map<String, Street> streets, 
      final Grid grid) throws IOException
  {
    String line;
    String splitParam = "\\t+";
    String removeParam = "\\D";
    HashMap<String,StreetSegment> elementMap = new HashMap<>();
    
    Rectangle2D.Double bounds = this.geographicShapes.getBounds();
    
    while((line = in.readLine()) != null)
    {
      String[] words = line.split(splitParam);
      int tail = Integer.parseInt(words[0].trim());
      int head = Integer.parseInt(words[1].trim());
      double length = Double.parseDouble(words[2].trim());
      String code = words[3].trim();
      String id = words[4].trim();
      String prefix = words[5].trim();
      String name = words[6].trim();
      String type = words[7].trim();
      String suffix = words[8].trim();
      int tailAddress = words[9].trim().isEmpty() ? -1 
          : Integer.parseInt(words[9].trim().replaceAll(removeParam, ""));
      int headAddress = words[10].trim().isEmpty() ? -1 
          : Integer.parseInt(words[10].trim().replaceAll(removeParam, ""));
      int lowAddress = Math.min(tailAddress, headAddress);
      int highAddress = Math.max(tailAddress, headAddress);
      
      GeographicShape visualSegment = geographicShapes.getElement(id);
      
      
      String canonicalName = Street.createCanonicalName(prefix, name, type, suffix);
      
      StreetSegment seg = new StreetSegment(id, code, visualSegment, 
          lowAddress, highAddress, tail, head, length, canonicalName);
      elementMap.put(id, seg);
      
      if(!canonicalName.isEmpty())
      {
        streets.computeIfAbsent(Street.createCanonicalName(prefix, name, type, suffix), 
            k -> new Street(prefix, name, type, 
            suffix, code)).addSegment(seg);
      }
    }
    for(StreetSegment seg : elementMap.values())
    {
      seg.initHeadPoint();
      seg.initTailPoint();
    }
    
    grid.setBounds(bounds);
    grid.setElementMap(elementMap);
    grid.createGrid();
    grid.populateGrid();
    
    
    return new CartographyDocument<StreetSegment>(elementMap, bounds);
  }
}
