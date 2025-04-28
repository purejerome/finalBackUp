package graph;

import java.util.ArrayList;
import java.util.List;

import feature.StreetSegment;

/**
 * Orginizes all canidate labels.
 * @author Jerome Donfack
 *
 */
public class CandidateLabelList extends AbstractLabelManager 
    implements CandidateLabelManager
{
  public static final String NEWEST = "N";
  public static final String OLDEST = "O";
  private List<Integer> candidates;
  private String policy;
  /**
   * Creates a new CandidateLabelList.
   * @param policy - Policy to use.
   * @param networkSize - Size of the street network.
   */
  public CandidateLabelList(final String policy, final int networkSize)
  {
    super(networkSize);
    this.candidates = new ArrayList<>();
    this.policy = policy;
  }
  
  /**
   * Adjust the head label.
   * @param segment - Segement to use for adjusting.
   */
  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int headNode = segment.getHead();
    int tailNode = segment.getTail();

    Label headLabel = this.getLabel(headNode);
    Label tailLabel = this.getLabel(tailNode);

    if (headLabel.isPermanent()) return;

    double possibleValue = tailLabel.getValue() + segment.getLength();

    if(headLabel != null) 
    {
      headLabel.adjustValue(possibleValue, segment);
      if(headLabel.getValue() == possibleValue)
      {
        candidates.add(headLabel.getID());
      }
    }
  }
  
  /**
   * Gets the candidate label to use.
   * @return - The candidate label.
   */
  @Override
  public Label getCandidateLabel()
  {
    if(this.candidates.size() == 0)
    {
      return null;
    }
    else if(this.policy.equals(NEWEST))
    {
      int labelID = this.candidates.remove(0);
      return this.getLabel(labelID);
    }
    else
    {
      int labelID = this.candidates.remove(this.candidates.size() - 1);
      return this.getLabel(labelID);
    }
  }
  
  /**
   * Initializes the starting candiate to serach from.
   * @param labelID - ID to add to canidates.
   */
  @Override
  public void initializeCandiates(final int labelID)
  {
    if(this.candidates.isEmpty())
    {
      this.candidates.add(labelID);
    }
  }
  
  /**
   * Checks if there are any remaining canidates.
   * @return - Bool for whether there are any remaining canidates.
   */
  @Override
  public boolean isCandiatesEmpty()
  {
    return this.candidates.isEmpty();
  }

}
