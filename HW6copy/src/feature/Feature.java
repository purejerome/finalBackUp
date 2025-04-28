package feature;

import geography.GeographicShape;

/**
 * Interface for all features to be printed.
 * @author Jerome Donfack
 *
 */
public interface Feature
{
  /**
   * Gets id of feature.
   * @return - ID of feature.
   */
  public abstract String getID();
  
  /**
   * Gets shape of feature.
   * @return - Shape of feature.
   */
  public abstract GeographicShape getGeographicShape();
}
