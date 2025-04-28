package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Math for non display coordinates to display coordinates.
 * @author Jerome Donfack
 *
 */
public class DisplayCoordinatesTransformation implements ViewTransformation
{
  private AffineTransform lastReflection;
  private AffineTransform lastTransform;

  /**
   * Gets the last reflection.
   * @return Last reflection.
   */
  @Override
  public AffineTransform getLastReflection()
  {
    return lastReflection;
  }

  /**
   * Gets the last transformation.
   * @return Last transformation.
   */
  @Override
  public AffineTransform getLastTransform()
  {
    return lastTransform;
  }

  /**
   * Gets current transformation.
   * @return Current transformation.
   */
  @Override
  public AffineTransform getTransform(final Rectangle2D displayBounds, 
      final Rectangle2D contentBounds)
  {
    double contentHeight = contentBounds.getHeight();
    double contentWidth = contentBounds.getWidth();
    double contentX = contentBounds.getX();
    double contentY = contentBounds.getY();
    AffineTransform translation = 
        AffineTransform.getTranslateInstance(-contentX, -(contentHeight+contentY));   
    double displayWidth = displayBounds.getWidth();
    double displayHeight = displayBounds.getHeight();
    double scale = Math.min(displayWidth/contentWidth, displayHeight/contentHeight);
    AffineTransform scaling = AffineTransform.getScaleInstance(scale, scale);
    AffineTransform aroundX = new AffineTransform(1.0, 0.0, 0.0,-1.0, 0.0, 0.0);
    this.lastReflection = aroundX;
    
    AffineTransform at = translation;
    at.preConcatenate(aroundX);
    at.preConcatenate(scaling);
    this.lastTransform = at;
    
    return at;
  }

}
