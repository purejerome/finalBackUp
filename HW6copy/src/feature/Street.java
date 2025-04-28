package feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import geography.GeographicShape;
import geography.PiecewiseLinearCurve;

/**
 * Class that stores many street segments to make a full street.
 * @author Jerome Donfack
 *
 */
public class Street extends AbstractFeature
{
  private PiecewiseLinearCurve shape;
  private String category;
  private String code;
  private String name;
  private String prefix;
  private String suffix;
  private List<StreetSegment> segments; 
  private Set<String> streetIds;
  
  /**
   * Constructs the street.
   * @param prefix - Prefix of street name.
   * @param name - Name of street.
   * @param category - Type of street.
   * @param suffix - Suffix of street name.
   * @param code - Area code of street.
   */
  public Street(final String prefix, final String name, final String category,
      final String suffix, final String code)
  {
    super(createCanonicalName(prefix, name, category, suffix));
    this.prefix = prefix;
    this.name = name;
    this.category = category;
    this.suffix = suffix;
    this.code = code;
    this.segments = new ArrayList<>();
    this.streetIds = new HashSet<>();
  }
  
  /**
   * Adds a street segment to the street.
   * @param segment - Street segment to add.
   */
  public void addSegment(final StreetSegment segment)
  {
    segments.add(segment);
    streetIds.add(segment.getID());
    if(this.shape == null)
    {
      this.shape = (PiecewiseLinearCurve) segment.getGeographicShape();
    }
    else
    {
      if(!streetIds.contains(segment.getID()))
      {
        this.shape.append(segment.getGeographicShape().getShape(), true);
      }
    }
  }
  
  /**
   * Returns the string version of street address name.
   * @param prefix - Prefix of street name.
   * @param name - Name of street.
   * @param category - Type of street.
   * @param suffix - Suffix of street name.
   * @return - Canonical name of street.
   */
  public static String createCanonicalName(final String prefix, final String name, 
      final String category, final String suffix)
  {
    ArrayList<String> streetAddyArray = 
        new ArrayList<>(Arrays.asList(prefix, name, category, suffix));
    String streetAddy = String.join(" ", streetAddyArray).trim();
    return streetAddy;
  }
  
  /**
   * Gets a certain number of street segments in a street.
   * @param number - Numer of street sgements to get.
   * @return - Street segments.
   */
  public List<StreetSegment> getSgements(final int number)
  {
    List<StreetSegment> matches = new ArrayList<>();
    for(StreetSegment streetSeg : this.segments)
    {
      if(streetSeg.getHighaddress() >= number 
          && streetSeg.getLowaddress() <= number
          && streetSeg.getLowaddress() % 2 == number % 2)
      {
        matches.add(streetSeg);
      }
    }
    return matches;
  }
  
  /**
   * Gets all street segments of a street.
   * @return - Iterator of all street segments.
   */
  public Iterator<StreetSegment> getSgements()
  {
    return this.segments.iterator();
  }
  
  /**
   * Returns shape of the street.
   * @return - Shape of the street.
   */
  @Override
  public GeographicShape getGeographicShape()
  {
    return this.shape;
  }
  
  /**
   * Get amount of segments stored in the list.
   * @return - Amount of segments in list.
   */
  public int getSize()
  {
    return this.segments.size();
  }
}
