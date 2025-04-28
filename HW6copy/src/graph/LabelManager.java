package graph;

import feature.StreetSegment;

/**
 * Interface on how to manage labels.
 * @author Jerome Donfack
 *
 */
public interface LabelManager
{
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
  public abstract Label getLabel(final int intersectionID);
  
  /**
   * Gets all labels.
   * @return - Every label.
   */
  public abstract Label[] getAllLabels();
}
