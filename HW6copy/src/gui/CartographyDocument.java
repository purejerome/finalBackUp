package gui;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * The model for printing shapes to the screen.
 * @author Jerome Donfack
 *
 * @param <T> - Type of object that will be printed on screen.
 */
public class CartographyDocument<T> implements Iterable<T>
{
  private Map<String, T> highlighted;
  private Map<String, T> elements;
  private Rectangle2D.Double bounds;
  
  /**
   * Creates a new CartographyDocument.
   * @param elements - Elements that are contained in the document.
   * @param bounds - The boundaries of the document.
   */
  public CartographyDocument(final Map<String, T> elements, 
      final Rectangle2D.Double bounds)
  {
    this.elements = elements;
    this.bounds = bounds;
  }
  
  /**
   * Gets the bounds of the document.
   * @return Bounds of the document.
   */
  public Rectangle2D.Double getBounds()
  {
    return bounds;
  }
  
  /**
   * Gets shape element by id.
   * @param id - Id of the shape.
   * @return The found shape.
   */
  public T getElement(final String id)
  {
    return this.elements.get(id);
  }
  
  /**
   * Gets the highlighted shape.
   * @return The highlighted shape.
   */
  public Iterator<T> highlighted()
  {
    if(highlighted != null) return highlighted.values().iterator();
    else return Collections.emptyIterator();
  }

  /**
   * Returns an iterator of all corresponding shapes.
   * @return The shape iterator.
   */
  @Override
  public Iterator<T> iterator()
  {
    return elements.values().iterator();
  }
  
  /**
   * Highlight the set shapes.
   * @param highlighted - Shapes to highlight.
   */
  public void setHighlighted(final Map<String, T> highlighted)
  {
    this.highlighted = highlighted;
  }

}
