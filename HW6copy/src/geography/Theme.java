package geography;

import java.awt.Color;
import java.awt.Stroke;

/**
 * Theme for coloring lines.
 * @author Jerome Donfack
 *
 */
public class Theme
{
  private Color color;
  private Stroke stroke;
  
  /**
   * Creates theme to use.
   * @param color - Color of the theme.
   * @param stroke - Stroke of the theme.
   */
  public Theme(final Color color, final Stroke stroke)
  {
    this.color = color;
    this.stroke = stroke;
  }
  
  /**
   * Gets the color of the theme.
   * @return - Color of the theme.
   */
  public Color getColor()
  {
    return this.color;
  }
  
  /**
   * Gets stroke of the theme.
   * @return - Stroke of the theme.
   */
  public Stroke getStroke()
  {
    return this.stroke;
  }
}
