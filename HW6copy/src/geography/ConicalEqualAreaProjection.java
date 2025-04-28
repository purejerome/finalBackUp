package geography;

/**
 * Math for conical equal area projection.
 * @author Jerome Donfack
 *
 */
public class ConicalEqualAreaProjection extends AbstractMapProjection
{
  private double refM;
  private double c;
  private double n;
  private double p0;
  /**
   * Creates a conical equal area projection strategy.
   * @param refM - Reference longitude
   * @param refP - Reference latitude
   * @param refP1 - Standard parallel 1
   * @param refP2 - Standard parallel 2
   */
  public ConicalEqualAreaProjection(final double refM, final double refP, 
      final double refP1, final double refP2)
  {
    double radConv = this.RADIANS_PER_DEGREE;
    double refMR = refM * radConv;
    double refPR = refP * radConv;
    double refP1R = refP1 * radConv;
    double refP2R = refP2 * radConv;
    this.refM = refMR;
    this.n = (Math.sin(refP1R) + Math.sin(refP2R)) / 2;
    this.c = Math.pow(Math.cos(refP1R), 2) + 2 * this.n * Math.sin(refP1R);
    this.p0 = Math.sqrt(this.c - 2 * this.n * Math.sin(refPR)) / this.n;
  }

  /**
   * The forward conical equal area projection (i.e., from Longitude/Latitude in
   * __radians__ to kilometers above the equator and to the west of the
   * reference meridian).
   *
   * @param lambda The longitude in __radians__
   * @param phi    The latitude in __radians__
   * @return   KMs west of reference and north of equator (in that order)
   */
  @Override
  public double[] forward(final double lambda, final double phi)
  {
    double thetha = this.n * (lambda - this.refM);
    double p = Math.sqrt(this.c - 2 * this.n * Math.sin(phi)) / this.n;
    double p1 = this.R * p * Math.sin(thetha);
    double p2 = this.R *(this.p0 - p * Math.cos(thetha));
    return new double[] {p1, p2};
  }

  /**
   * The inverse conical equal area projection from kilometers (above the 
   * equator and to the west of the reference meridian)
   * to Longitude/Latitude in __radians__.
   *
   * @param ew The distance east/west in kilometers
   * @param ns The distance north/south in kilometers
   * @return   The longitude and latitude in __radians__ (in that order)
   */
  @Override
  public double[] inverse(final double ew, final double ns)
  {
    double p1Div = ew / this.R;
    double p2Div = this.p0 - ns / this.R;
    double a = Math.sqrt(Math.pow(p1Div, 2) + Math.pow(p2Div, 2));
    double b = Math.atan(p1Div / p2Div);
    double phi = Math.asin((this.c - Math.pow(a, 2) * Math.pow(this.n, 2)));
    double lambda = this.refM + (b / this.n);
    return new double[] {lambda, phi};
  }

}
