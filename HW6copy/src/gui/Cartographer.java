package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Interface for cartography printing.
 * @author Jerome Donfack
 *
 * @param <T> - Type of shape that will be printed on screen.
 */
public interface Cartographer<T>
{
  /**
   * Paint the highlight shapes.
   * @param model - Model containing the shapes.
   * @param g2 - Graphics.
   * @param at - Paint style.
   */
  public void paintHighlights(final CartographyDocument<T> model, final Graphics2D g2,
      final AffineTransform at);
  
  /**
   * Paint the shapes.
   * @param mode - Model containing the shapes.
   * @param g2 - Graphics.
   * @param at - Paint style.
   */
  public void paintShapes(final CartographyDocument<T> mode, final Graphics2D g2,
      final AffineTransform at);
}
