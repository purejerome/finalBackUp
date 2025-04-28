package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Math one coordinate system to another.
 * @author Jerome Donfack
 *
 */
public interface ViewTransformation
{
  /**
   * Gets the last reflection of the map.
   * @return The last reflection.
   */
  public AffineTransform getLastReflection();
  
  /**
   * Gets the last transformation of the map.
   * @return The last transformation.
   */
  public AffineTransform getLastTransform();
  
  /**
   * Gets an arbitrary transformation of the map.
   * @param displayBounds - Display bounds of map.
   * @param contentBounds - Display bounds of viewable content.
   * @return The transformation.
   */
  public AffineTransform getTransform(final Rectangle2D displayBounds, 
      final Rectangle2D contentBounds);
}
