package graph;

import feature.StreetSegment;

/**
 * Marks that contain info about each node.
 * @author Jerome Donfack
 *
 */
public class Label
{
  private boolean permanent;
  private double value;
  private int id;
  private StreetSegment predecessor;
  
  /**
   * Creates a label.
   */
  public Label()
  {
    this.permanent = false;
    this.predecessor = null;
    this.value = Double.POSITIVE_INFINITY;
  }
  
  /**
   * Creates a label with an id.
   * @param id - ID to use for label.
   */
  public Label(final int id)
  {
    this();
    this.id = id;
  }
  
  /**
   * Adjust the value and predecessor of label if necessary.
   * @param possibleValue - Value to adjust to.
   * @param possiblePredecessor - Predecessor to adjust to.
   */
  public void adjustValue(final double possibleValue, 
      final StreetSegment possiblePredecessor)
  {
    if(possibleValue < this.value)
    {
      this.value = possibleValue;
      this.predecessor = possiblePredecessor;
    }
  }
  
  /**
   * Returns the id of label.
   * @return - ID of label.
   */
  public int getID()
  {
    return this.id;
  }
  
  /**
   * Gets predecessor to the label.
   * @return - Label predecessor.
   */
  public StreetSegment getPredecessor()
  {
    return this.predecessor;
  }
  
  /**
   * Gets the value of the label.
   * @return - Value of label.
   */
  public double getValue()
  {
    return this.value;
  }
  
  /**
   * Checks if label is permanent.
   * @return - If label is permanent.
   */
  public boolean isPermanent()
  {
    return this.permanent;
  }
  
  /**
   * Makes the label permanent.
   */
  public void makePermanent()
  {
    this.permanent = true;
  }
  
  /**
   * Sets the value of the label.
   * @param value - New value to set.
   */
  public void setValue(final double value)
  {
    this.value = value;
  }
}
