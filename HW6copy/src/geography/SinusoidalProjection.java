package geography;

/**
 * Math for sinusoidal projection.
 * @author Jerome Donfack
 *
 */
public class SinusoidalProjection extends AbstractMapProjection
{

  /**
   * The forward sinusoidal projection (i.e., from Longitude/Latitude in
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
    double p1 = lambda * this.R * Math.cos(phi);
    double p2 = phi * this.R;
    return new double[] {p1, p2};
  }

  /**
   * The inverse sinusoidal projection from kilometers (above the 
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
    double phi = ns / this.R;
    double lambda = ew / (this.R * Math.cos(phi));
    return new double[] {lambda, phi};
  }

}
