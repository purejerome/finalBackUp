package feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle street intersections.
 * @author Jerome Donfack
 *
 */
public class Intersection
{
  private List<StreetSegment> inbound;
  private List<StreetSegment> outbound;
  
  /**
   * Creates intersection.
   */
  public Intersection()
  {
    inbound = new ArrayList<>();
    outbound = new ArrayList<>();
  }
  
  /**
   * Adds street to inbound streets.
   * @param segment - Segment to add.
   */
  public void addInbound(final StreetSegment segment)
  {
    inbound.add(segment);
  }
  
  /**
   * Adds street to outbound streets.
   * @param segment - Segment to add.
   */
  public void addOutbound(final StreetSegment segment)
  {
    outbound.add(segment);
  }
    
  /**
   * Gets inbound streets.
   * @return - List of inbound segments.
   */
  public List<StreetSegment> getInbound()
  {
    return inbound;
  }
  
  /**
   * Gets outbound streets.
   * @return - List of outbound segments.
   */
  public List<StreetSegment> getOutbound()
  {
    return outbound;
  }
  
  /**
   * Return true if there are no inbound or outbound street segs.
   * @return - Any out bound street seg bools.
   */
  public boolean isEmpty()
  {
    return this.inbound.isEmpty() && this.outbound.isEmpty();
  }
  
  /**
   * Return true if there are no inbound street segs.
   * @return - Any inbound street seg bools.
   */
  public boolean isInboundEmpty()
  {
    return this.inbound.isEmpty();
  }
  
  /**
   * Return true if there are no outbound street segs.
   * @return - Any outbound street seg bools.
   */
  public boolean isOutboundEmpty()
  {
    return this.outbound.isEmpty();
  }
}
