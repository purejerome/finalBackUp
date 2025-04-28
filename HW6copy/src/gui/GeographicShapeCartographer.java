package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import geography.GeographicShape;

/**
 * Class to properly transform shapes before printing.
 * @author Jerome Donfack
 *
 */
public class GeographicShapeCartographer implements Cartographer<GeographicShape>
{
  private Color color;
  
  /**
   * Initializes geographic shape drawer.
   * @param color - Color to draw in.
   */
  public GeographicShapeCartographer(final Color color)
  {
    this.color = color;
  }

  /**
   * Transforms highlighted shapes before printing.
   */
  @Override
  public void paintHighlights(final CartographyDocument<GeographicShape> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    Iterator<GeographicShape> highlighted = model.highlighted();
    g2.setColor(color);
    g2.setStroke(new BasicStroke(0));
    while(highlighted.hasNext())
    {
      GeographicShape highlightItem = highlighted.next();
      g2.draw(at.createTransformedShape(highlightItem.getShape()));
    }
  }

  /**
   * Transforms all shapes before printing.
   */
  @Override
  public void paintShapes(final CartographyDocument<GeographicShape> mode, 
      final Graphics2D g2, final AffineTransform at)
  {
    Iterator<GeographicShape> shapes = mode.iterator();
    g2.setColor(color);
    g2.setStroke(new BasicStroke(0));
    while(shapes.hasNext())
    {
      GeographicShape shape = shapes.next();
      g2.draw(at.createTransformedShape(shape.getShape()));
    }
  }
}
