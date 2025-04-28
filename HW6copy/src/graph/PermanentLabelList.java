package graph;

import java.util.Arrays;
import java.util.LinkedList;
import feature.StreetSegment;

/**
 * Organizes all permanent labels using a list.
 * @author Jerome Donfack
 *
 */
public class PermanentLabelList extends AbstractLabelManager 
    implements PermanentLabelManager
{
  private LinkedList<Label> labelsList;

  /**
   * Creates a new PermanentLabelList.
   * @param networkSize - Size of the network.
   */
  public PermanentLabelList(final int networkSize)
  {
    super(networkSize);
    labelsList = new LinkedList<>(Arrays.asList(this.labels));
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
    }
  }
  
  /**
   * Returns the label with the smallest value.
   * @return - Label with the smallest value.
   */
  @Override
  public Label getSmallestLabel()
  {
    Label minLabel = null;
    double minValue = Double.POSITIVE_INFINITY;
    for(Label label : this.labelsList)
    {
      if(label.getValue() < minValue && !label.isPermanent())
      {
        minValue = label.getValue();
        minLabel = label;
      }
    }
    return minLabel;
  }
  
  /**
   * Makes the corresponding label permanent.
   * @param intersectionID - ID of the label to make permanent.
   */
  @Override
  public void makePermanent(final int intersectionID)
  {
    this.getLabel(intersectionID).makePermanent();
  }
  
}
