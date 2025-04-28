package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

/**
 * Class for piecewise curves to print on screen.
 * @author Jerome Donfack
 *
 */
public class PiecewiseLinearCurve extends AbstractGeographicShape
{
  protected Path2D.Double shape;
  protected boolean closed = false;

  /**
   * Creates a blank piecewise curve.
   * @param id - The id of the curve.
   */
  public PiecewiseLinearCurve(final String id)
  {
    super(id);
  }
  
  /**
   * Creates a piecewise curve with a shape.
   * @param id - The id of the curve.
   * @param shape - Shape of the curve.
   */
  public PiecewiseLinearCurve(final String id, final Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
    this.closed = isClosed();
  }
  
  /**
   * Adds point to the curve.
   * @param point - Point to add to curve.
   */
  public void add(final double[] point)
  {
    if (shape == null)
    {
      shape = new Path2D.Double();
      shape.moveTo(point[0], point[1]);
    } 
    else 
    {
      shape.lineTo(point[0], point[1]);
    }
    isClosed();
  }
  
  /**
   * Append shape to curve.
   * @param addition - Shape to append.
   * @param connect - connect to the current shape
   */
  public void append(final Shape addition, final boolean connect)
  {
    this.shape.append(addition, connect);
    isClosed();
  }
  
  private boolean isClosed()
  {
    PathIterator pathIterator = shape.getPathIterator(null);
    double[] coords = new double[6];
    double[] firstCoords = new double[2];
    double[] lastCoords = new double[2];
    boolean firstCoordsCaptured = false;
    while (!pathIterator.isDone()) 
    {
      pathIterator.currentSegment(coords);
      if(!firstCoordsCaptured)
      {
        firstCoords[0] = coords[0];
        firstCoords[1] = coords[1];
        firstCoordsCaptured = true;
      }
      
      lastCoords[0] = coords[0];
      lastCoords[1] = coords[1];
      pathIterator.next();
    }
    return lastCoords[0] == firstCoords[0] && lastCoords[1] == firstCoords[1];
    
  }

  /**
   * Gets the shape of the piecewise curve.
   * @return Shape of piecewise curve.
   */
  @Override
  public Shape getShape()
  {
    return this.shape;
  }
}
