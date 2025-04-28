package feature;

/**
 * Abstract class for all features.
 * @author Jerome Donfack
 *
 */
public abstract class AbstractFeature implements Feature
{
  private String id;
  
  /**
   * Creates an abstract feature.
   * @param id - ID of abstract feature.
   */
  public AbstractFeature(final String id)
  {
    this.id = id;
  }
  
  /**
   * Gets the id of the feature.
   * @return - ID of feature.
   */
  @Override
  public String getID()
  {
    return this.id;
  }
}
