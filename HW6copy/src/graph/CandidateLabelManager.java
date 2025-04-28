package graph;

/**
 * Handles the non permanent labels.
 * @author Jerome Donfack
 *
 */
public interface CandidateLabelManager extends LabelManager
{
  /**
   * Gets the candidate label to use.
   * @return - The candidate label.
   */
  public abstract Label getCandidateLabel();
  
  /**
   * Initializes the starting candiate to serach from.
   * @param labelID - ID to add to canidates.
   */
  public void initializeCandiates(final int labelID);
  
  /**
   * Checks if there are any remaining canidates.
   * @return - Bool for whether there are any remaining canidates.
   */
  public boolean isCandiatesEmpty();
}
