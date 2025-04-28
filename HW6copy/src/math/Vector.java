package math;

/**
 * A utility class containing vector arithmetic.
 * This work complies with the JMU Honor Code.
 * @author Jerome Donfack
 */
public abstract class Vector
{
  private Vector()
  {
  }
  
  /**
   * Computes the dot product of two vectors.
   * @param v - Vector.
   * @param w - Vector.
   * @return - Dot product of v • w.
   */
  public static double dot(final double[] v, final double[] w)
  {
    double total = 0;
    int pointSize = v.length;
    for(int i = 0; i < pointSize; i++)
    {
      total += v[i] * w[i];
    }
    return total;
  }
  
  /**
   * Computes the difference of two vectors.
   * @param v - Vector.
   * @param w - Vector.
   * @return - Difference of v - w.
   */
  public static double[] minus(final double[] v, final double[] w)
  {
    int pointSize = v.length;
    double[] retArray = new double[pointSize];
    for(int i = 0; i < pointSize; i++)
    {
      retArray[i] = v[i] - w[i];
    }
    return retArray;
  }
  
  /**
   * Computes the length of a vector.
   * @param v - Vector.
   * @return - Length of v.
   */
  public static double norm(final double[] v)
  {
    return Math.sqrt(Vector.dot(v, v));
  }
  
  /**
   * Normalizes a vector.
   * @param v - Vector.
   * @return - Normalized vector of v.
   */
  public static double[] normalize(final double[] v)
  {
    return Vector.times(1.0/Vector.norm(v), v);
  }
  
  /**
   * Finds the perpendicular vector of a corresponding vector.
   * @param v - Vector.
   * @return - A vector perpendicular to v.
   */
  public static double[] perp(final double[] v)
  {
    return new double[]{-v[1], v[0]};
  }
  
  /**
   * omputes the sum of two vectors.
   * @param v - Vector.
   * @param w - Vector.
   * @return - The sum of v + w.
   */
  public static double[] plus(final double[] v, final double[] w)
  {
    int pointSize = v.length;
    double[] retArray = new double[pointSize];
    for(int i = 0; i < pointSize; i++)
    {
      retArray[i] = v[i] + w[i];
    }
    return retArray;
  }
  
  /**
   * Computes the product of a scalar and a vector.
   * @param s - Scalar
   * @param w - Vector.
   * @return Product of s * w.
   */
  public static double[] times(final double s, final double[] w)
  {
    int pointSize = w.length;
    double[] retArray = new double[pointSize];
    for(int i = 0; i < pointSize; i++)
    {
      retArray[i] = s * w[i];
    }
    return retArray;
  }
  
  /**
   * Computes the product of a vector and a scalar.
   * @param v - Vector.
   * @param s - Scalar.
   * @return - Product of v * s.
   */
  public static double[] times(final double[] v, final double s)
  {
    return Vector.times(s, v);
  }
}
