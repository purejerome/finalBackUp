package graph;

/**
 * Interface on how to manage permanent labels.
 * @author Jerome Donfack
 *
 */
public interface PermanentLabelManager extends LabelManager
{
  /**
   * Returns the label with the smallest value.
   * @return - Label with the smallest value.
   */
  public abstract Label getSmallestLabel();
  
  /**
   * Makes the corresponding label permanent.
   * @param intersectionID - ID of the label to make permanent.
   */
  public abstract void makePermanent(final int intersectionID);
}
