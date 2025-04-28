package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.ThemeLibrary;

/**
 * Class to display street segments.
 * @author Jerome Donfack
 *
 */
public class StreetSegmentCartographer implements Cartographer<StreetSegment>
{
  private ThemeLibrary theme;
  
  /**
   * Creates a new street segment cartographer.
   */
  public StreetSegmentCartographer()
  {
    this.theme = new StreetThemeLibrary();
  }

  /**
   * Paints the highlighted street segments.
   * @param model - Model to use.
   * @param g2 - Graphics to use.
   * @param at - Transformation to use.
   */
  @Override
  public void paintHighlights(final CartographyDocument<StreetSegment> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    Iterator<StreetSegment> highlighted = model.highlighted();
    g2.setColor(theme.getHighlightTheme().getColor());
    g2.setStroke(theme.getHighlightTheme().getStroke());
    while(highlighted.hasNext())
    {
      StreetSegment highlightItem = highlighted.next();
      g2.draw(at.createTransformedShape(highlightItem.getGeographicShape().getShape()));
    }
  }

  /**
   * Paints the street segments.
   * @param mode - Model to use.
   * @param g2 - Graphics to use.
   * @param at - Transformation to use.
   */
  @Override
  public void paintShapes(final CartographyDocument<StreetSegment> mode, 
      final Graphics2D g2, final AffineTransform at)
  {
    Iterator<StreetSegment> streetsegs = mode.iterator();
    while(streetsegs.hasNext())
    {
      StreetSegment streetseg = streetsegs.next();
      if(streetseg.highlighted)
      {
        g2.setColor(theme.getHighlightTheme().getColor());
        g2.setStroke(theme.getHighlightTheme().getStroke());
        g2.draw(at.createTransformedShape(streetseg.getGeographicShape().getShape()));
      }
      else
      {
        String themeCode = "" + streetseg.getCode().charAt(0) 
            + streetseg.getCode().charAt(1);
        g2.setColor(theme.getTheme(themeCode).getColor());
        g2.setStroke(theme.getTheme(themeCode).getStroke());
        g2.draw(at.createTransformedShape(streetseg.getGeographicShape().getShape()));
      }
//      String themeCode = "" + streetseg.getCode().charAt(0) 
//          + streetseg.getCode().charAt(1);
//      g2.setColor(theme.getTheme(themeCode).getColor());
//      g2.setStroke(theme.getTheme(themeCode).getStroke());
//      g2.draw(at.createTransformedShape(streetseg.getGeographicShape().getShape()));
    }
  }
  
}
