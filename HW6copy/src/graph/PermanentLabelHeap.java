package graph;

import java.util.ArrayList;
import java.util.Collections;
import feature.StreetSegment;

/**
 * Organizes all permanent labels using a heap.
 * @author Jerome Donfack
 *
 */
public class PermanentLabelHeap extends AbstractLabelManager 
    implements PermanentLabelManager
{
  private ArrayList<Label> labelsHeap;
  private int d;

  /**
   * Creates a new PermanentLabelHeap.
   * @param d - Max children of the heap.
   * @param networkSize - Size of the network.
   */
  public PermanentLabelHeap(final int d, final int networkSize)
  {
    super(networkSize);
    this.d = d;
    this.labelsHeap = new ArrayList<>();
    initializeHeap();
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
        siftUp(headLabel);
      }
    }
  }
  
  /**
   * Returns the label with the smallest value.
   * @return - Label with the smallest value.
   */
  @Override
  public Label getSmallestLabel()
  {
    if(this.labelsHeap.isEmpty()) return null;
    
    Label retLabel = remove();
    while(retLabel.isPermanent() && !this.labelsHeap.isEmpty())
    {
      retLabel = remove();
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
  
  private void siftUp(final Label label)
  {
    int index = this.labelsHeap.indexOf(label);
    while(index > 0) 
    {
      int parentIndex = (index - 1) / this.d;
      Label parent = this.labelsHeap.get(parentIndex);
      
      if (label.getValue() >= parent.getValue()) 
      {
        break;
      }
      
      swap(this.labelsHeap.get(index), parent);
      index = parentIndex;
    }
  }
  
  private void siftDown(final Label label)
  {
    int index = this.labelsHeap.indexOf(label);
    while(index < this.labelsHeap.size())
    {
      int smallestIndex = index;
      for(int c = 1; c <= this.d; c++)
      {
        int childIndex = (index * this.d) + c;
        if(childIndex < this.labelsHeap.size())
        {
          if(this.labelsHeap.get(childIndex).getValue() 
              < this.labelsHeap.get(smallestIndex).getValue())
          {
            smallestIndex = childIndex;
          }
        }
      }
      
      if(smallestIndex != index)
      {
        swap(this.labelsHeap.get(index), this.labelsHeap.get(smallestIndex));
        index = smallestIndex;
      }
      else
      {
        break;
      }
    }
  }
  
  private void add(final Label label)
  {
    this.labelsHeap.add(label);
    siftUp(label);
  }
  
  private Label remove()
  {
    if(this.labelsHeap.size() == 1)
    {
      return this.labelsHeap.remove(0);
    }
    swap(this.labelsHeap.get(0), 
        this.labelsHeap.get(this.labelsHeap.size() - 1));
    Label retLabel = this.labelsHeap.remove(this.labelsHeap.size() - 1);
    siftDown(this.labelsHeap.get(0));
    return retLabel;
  }
  
  private void swap(final Label label1, final Label label2)
  {
    int label1Index = this.labelsHeap.indexOf(label1);
    int label2Index = this.labelsHeap.indexOf(label2);
    Collections.swap(this.labelsHeap, label1Index, label2Index);
  }
  
  private void initializeHeap()
  {
    for(Label label : this.labels)
    {
      add(label);
    }
  }

}
