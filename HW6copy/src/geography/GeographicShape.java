package geography;

import java.awt.Shape;

/**
 * Basic interface for all shapes.
 * @author Jerome Donfack
 *
 */
public interface GeographicShape
{
  /**
   * Get the id of specific shape.
   * @return id of shape.
   */
  String getId();
  /**
   * Get this shape.
   * @return The shape.
   */
  Shape getShape();
}
