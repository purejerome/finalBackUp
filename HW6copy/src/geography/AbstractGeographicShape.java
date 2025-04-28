package geography;

import java.awt.Shape;

/**
 * The abstract parent class of all shapes on screen.
 * @author Jerome Donfack
 *
 */
public abstract class AbstractGeographicShape implements GeographicShape
{
  private String id;
  
  /**
   * Creates an abstract shape.
   * @param id - Id of abstract shape.
   */
  public AbstractGeographicShape(final String id)
  {
    this.id = id;
  }

  /**
   * Gets the the ID of abstract shape.
   * @return ID of abstract shape.
   */
  @Override
  public String getId()
  {
    return id;
  }

  /**
   * Gets the actual shape.
   * @return The printed shape.
   */
  @Override
  public abstract Shape getShape();

}
