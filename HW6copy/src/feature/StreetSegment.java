package feature;

import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import geography.GeographicShape;
import geography.PiecewiseLinearCurve;

/**
 * Class for everything related to street segments on the map.
 * @author Jerome Donfack
 *
 */
public class StreetSegment extends AbstractFeature
{
  private double length;
  private int tail;
  private int head;
  private int highAddress;
  private int lowAddress;
  private GeographicShape geoGraphicShape;
  private String code;
  private double headAngle;
  private double tailAngle;
  private Point2D.Double headPoint;
  private Point2D.Double tailPoint;
  private String parentCanonicalName;
  public boolean highlighted = false;
  
  /**
   * Creates a street segment.
   * @param id - ID of segment.
   * @param code - Street code of segment.
   * @param shape - Shape of the segment.
   * @param lowAddress - Lowest address on the street.
   * @param highAddress - Highest address on the street.
   * @param tail - End of the street.
   * @param head - Start of the street.
   * @param length - Length of the street.
   */
  public StreetSegment(final String id, final String code, final GeographicShape shape,
      final int lowAddress, final int highAddress, final int tail, final int head,
      final double length, final String parentCanonicalName)
  {
    super(id);
    this.code = code;
    this.geoGraphicShape = shape;
    this.lowAddress = lowAddress;
    this.highAddress = highAddress;
    this.tail = tail;
    this.head = head;
    this.length = length;
    this.parentCanonicalName = parentCanonicalName;
  }

  /**
   * Returns shape of the street segment.
   * @return - Shape of the street segment.
   */
  @Override
  public GeographicShape getGeographicShape()
  {
    return this.geoGraphicShape;
  }
  
  /**
   * Gets parentCanonicalName.
   * @return - parentCanonicalName.
   */
  public String getparentCanonicalName()
  {
    return this.parentCanonicalName;
  }
  
  /**
   * Gets length of street segment.
   * @return - Length of street segment.
   */
  public double getLength()
  {
    return this.length;
  }
  
  /**
   * Gets tail of street segment.
   * @return - Tail of street segment.
   */
  public int getTail()
  {
    return this.tail;
  }
  
  /**
   * Gets head of street segment.
   * @return - Head of street segment.
   */
  public int getHead()
  {
    return this.head;
  }
  
  /**
   * Gets high address of street segment.
   * @return - High address of street segment.
   */
  public int getHighaddress()
  {
    return this.highAddress;
  }
  
  /**
   * Gets low address of street segment.
   * @return - Low address of street segment.
   */
  public int getLowaddress()
  {
    return this.lowAddress;
  }
  
  /**
   * Gets code of street segment.
   * @return - Code of street segment.
   */
  public String getCode()
  {
    return this.code;
  }
  
  /**
   * Gets the head point of the line.
   * @return - Head point of line.
   */
  public Point2D.Double getHeadPoint()
  {
    return this.headPoint;
  }
  
  /**
   * Inits head point.
   */
  public void initHeadPoint()
  {
    PathIterator pathIterator = ((PiecewiseLinearCurve) this.geoGraphicShape)
        .getShape().getPathIterator(null);
    double[] coords = new double[6];
    pathIterator.currentSegment(coords);
    this.headPoint = new Point2D.Double(coords[0], coords[1]);
  }
  
  /**
   * Gets tail point of the line.
   * @return - Tail point of the line.
   */
  public Point2D.Double getTailPoint()
  {
    return this.tailPoint;
  }
  
  /**
   * Inits tail point.
   */
  public void initTailPoint()
  {
    PathIterator pathIterator = ((PiecewiseLinearCurve) this.geoGraphicShape)
        .getShape().getPathIterator(null);
    double[] coords = new double[6];
    Point2D.Double tailPoint = null;
    
    while (!pathIterator.isDone())
    {
      pathIterator.currentSegment(coords);
      tailPoint = new Point2D.Double(coords[0], coords[1]);
      pathIterator.next();
    }
    this.tailPoint =  tailPoint;
  }
  
//  /**
//   * Inits tail angle value.
//   */
//  public void initTailAngle()
//  {
//    Point2D.Double tailPoint = this.getTailPoint();
//    Point2D.Double headPoint = this.getHeadPoint();
//    
//    double transX = headPoint.getX();
//    double transY = headPoint.getY();
//    
//    double transTailX = tailPoint.getX() - transX;
//    double transTailY = tailPoint.getY() - transY;
//    
//    double degrees = Math.toDegrees(Math.atan2(transTailY, transTailX));
//    
//    if(degrees < 0)
//    {
//      this.tailAngle = degrees + 360;
//    }
//    else
//    {
//      this.tailAngle = degrees;
//    }
//    
//  }
//  
//  /**
//   * Inits head angle value.
//   */
//  public void initHeadAngle()
//  {
//    Point2D.Double tailPoint = this.getTailPoint();
//    Point2D.Double headPoint = this.getHeadPoint();
//    
//    double transX = tailPoint.getX();
//    double transY = tailPoint.getY();
//    
//    double transHeadX = headPoint.getX() - transX;
//    double transHeadY = headPoint.getY() - transY;
//    
//    double degrees = Math.toDegrees(Math.atan2(transHeadY, transHeadX));
//    
//    if(degrees < 0)
//    {
//      this.headAngle = degrees + 360;
//    }
//    else
//    {
//      this.headAngle = degrees;
//    }
//  }
//  
//  /**
//   * Grab head angle.
//   * @return - Head angle.
//   */
//  public double getHeadAngle()
//  {
//    return this.headAngle;
//  }
//  
//  /**
//   * Grabs tail angle.
//   * @return - Tail angle.
//   */
//  public double getTailAngle()
//  {
//    return this.tailAngle;
//  }
}
