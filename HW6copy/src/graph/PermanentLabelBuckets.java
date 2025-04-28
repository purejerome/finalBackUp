package graph;

import java.util.LinkedList;
import java.util.TreeMap;

import feature.StreetSegment;

/**
 * Organizes all permanent labels using buckets.
 * @author Jerome Donfack
 *
 */
public class PermanentLabelBuckets extends AbstractLabelManager 
    implements PermanentLabelManager
{
  private TreeMap<Integer, LinkedList<Label>> labelBuckets;
  
  /**
   * Create a new PermanentLabelBucket.
   * @param networkSize - Size of the network.
   */
  public PermanentLabelBuckets(final int networkSize)
  {
    super(networkSize);
    labelBuckets = new TreeMap<>();
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
      if(headLabel.getValue() != Double.POSITIVE_INFINITY)
      {
        removeFromBucket(headLabel);
      }
      headLabel.adjustValue(possibleValue, segment);
      addToBucket(headLabel);
    }
  }
  
  /**
   * Returns the label with the smallest value.
   * @return - Label with the smallest value.
   */
  @Override
  public Label getSmallestLabel()
  {
    boolean exit = false;
    Label retLabel = null;
    for(int index : labelBuckets.keySet())
    {
      LinkedList<Label> labelList = this.labelBuckets.get(index);
      double minValue = Double.POSITIVE_INFINITY;
      for(Label label : labelList)
      {
        if(label.getValue() < minValue && !label.isPermanent())
        {
          retLabel = label;
          minValue = label.getValue();
          exit = true;
        }
      }
      if(exit)
      {
        break;
      }
    }
    return retLabel;
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
  
  private void addToBucket(final Label label)
  {
    int key = ((int) (label.getValue() * 1000) / 100) * 100;
    labelBuckets.computeIfAbsent(key, 
        k -> new LinkedList<>()).add(label);
  }
  
  private void removeFromBucket(final Label label)
  {
    int key = ((int) (label.getValue() * 1000) / 100) * 100;
    labelBuckets.computeIfAbsent(key, 
        k -> new LinkedList<>()).remove(label);
  }
}
