package grid;

import java.util.Objects;

/**
 * Acts as a simple tuple class but in Java.
 * @author Jerome Donfack
 *
 * @param <T>
 * @param <S>
 */
public class GridTuple<T, S>
{
  private T left;
  private S right;
  
  /**
   * Creates new tuple.
   * @param left - Item stored on the left.
   * @param right - Item stored on the right.
   */
  public GridTuple(final T left, final S right)
  {
    this.left = left;
    this.right = right;
  }
  
  /**
   * Get item stored on the left.
   * @return - Item stored on the left.
   */
  public T getLeft()
  {
    return this.left;
  }
  
  /**
   * Get item stored on the right.
   * @return - Item stored on the right.
   */
  public S getRight()
  {
    return this.right;
  }
  
  /**
   * Checks if object in left and right it equal.
   * If the objects are Double, there is a slight tolerance.
   * @param obj - Object to compare to.
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    GridTuple<?, ?> that = (GridTuple<?, ?>) obj;
    
    double tolerance = 0.000001;
    
    if(left instanceof Double && that.left instanceof Double)
    {
      if(Math.abs((Double) left - (Double) that.left) > tolerance)
      {
        return false;
      }
    }
    else
    {
      if (!Objects.equals(left, that.left))
      {
        return false;
      }
    }
    
    if(right instanceof Double && that.right instanceof Double)
    {
      if(Math.abs((Double) right - (Double) that.right) > tolerance)
      {
        return false;
      }
    }
    else
    {
      if (!Objects.equals(right, that.right))
      {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Creates hash code, for Double, the hash code has a tolerance.
   */
  @Override
  public int hashCode()
  {
    int leftHash = 0;
    if(left instanceof Double) 
    {
      leftHash = (int) (Math.round((Double) left * 10000000.0) / 10000000.0);
    } 
    else 
    {
      leftHash = Objects.hash(left);
    }
    
    int rightHash = 0;
    if(right instanceof Double) 
    {
      rightHash = (int) (Math.round((Double) right * 10000000.0) / 10000000.0);
    } 
    else 
    {
      rightHash = Objects.hash(right);
    }

    return 31 * leftHash + rightHash;
  }
}
