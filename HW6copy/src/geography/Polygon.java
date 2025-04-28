package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * Class for polygon to print on screen.
 * @author Jerome Donfack
 *
 */
public class Polygon extends PiecewiseLinearCurve
{
  /**
   * Create blank Polygon.
   * @param id - The id of the polygon.
   */
  public Polygon(final String id)
  {
    super(id);
  }
  
  /**
   * Create polygon with a shape.
   * @param id - The id of the polygon.
   * @param shape - The shape of the polygon.
   */
  public Polygon(final String id, final Path2D.Double shape)
  {
    super(id, shape);
  }
  
  /**
   * Gets shape of polygon(closes polygon before retrieving).
   * @return Shape of polygon.
   */
  @Override
  public Shape getShape()
  {
    if(!this.closed)
    {
      this.shape.closePath();
    }
    return shape;
  }

}
