package graph;

import feature.StreetSegment;

/**
 * Handles everything to do with labels.
 * @author Jerome Donfack
 *
 */
public abstract class AbstractLabelManager implements LabelManager
{
  protected Label[] labels;
  
  /**
   * Creates a new AbstractLabelManager.
   * @param networkSize - Size of the street network.
   */
  public AbstractLabelManager(final int networkSize)
  {
    this.labels = new Label[networkSize];
    for (int i=0; i<networkSize; i++) this.labels[i] = new Label(i);
  }
  
  /**
   * Adjust the head label.
   * @param segment - Segement to use for adjusting.
   */
  public abstract void adjustHeadValue(final StreetSegment segment);
  
  /**
   * Get the label.
   * @param intersectionID - ID of the label.
   * @return - Label associated with intersection.
   */
  @Override
  public Label getLabel(final int intersectionID)
  {
    return this.labels[intersectionID];
  }
  
  @Override
  public Label[] getAllLabels()
  {
    return this.labels;
  }
}
