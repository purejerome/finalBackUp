package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.Street;
import feature.StreetSegment;

/**
 * Represent a network of streets to navigate.
 * @author Jerome Donfack
 *
 */
public class StreetNetwork
{
  private List<Intersection> intersections;
  
  /**
   * Creates a StreetNetwork.
   */
  public StreetNetwork()
  {
    intersections = new ArrayList<>();
  }
  
  /**
   * Adds intersection to street network.
   * @param index - Index to add intersection to.
   * @param intersection - Intersection to add.
   */
  public void addIntersection(final int index, final Intersection intersection)
  {
    this.intersections.add(index, intersection);
  }
  
  /**
   * Gets the intersection at an index in the street network.
   * @param index - Index of intersection.
   * @return - Intersection at index.
   */
  public Intersection getIntersection(final int index)
  {
    return this.intersections.get(index);
  }
  
  /**
   * Gets the size of the street network.
   * @return - Size of street network.
   */
  public int size()
  {
    return this.intersections.size();
  }
  
  /**
   * Creates street network from a collection of streets.
   * @param streets - Streets to create a network out of.
   * @return - The created street network.
   */
  public static StreetNetwork createStreetNetwork(final Map<String, Street> streets)
  {
    StreetNetwork retStreetNetwork = new StreetNetwork();
    for(Street street : streets.values())
    {
      Iterator<StreetSegment> streetSegs = street.getSgements();
      while(streetSegs.hasNext())
      {
        StreetSegment streetSeg = streetSegs.next();
        
        int tail = streetSeg.getTail();
        int head = streetSeg.getHead();
        retStreetNetwork.expandIfNeeded(tail);
        retStreetNetwork.expandIfNeeded(head);
        
        retStreetNetwork.getIntersection(tail).addOutbound(streetSeg);
        retStreetNetwork.getIntersection(head).addInbound(streetSeg);
      }
    }
    return retStreetNetwork;
  }
  
  private void expandIfNeeded(final int index)
  {
    if(this.intersections.size() <= index)
    {
      ((ArrayList<Intersection>) this.intersections).ensureCapacity(index + 1);
      while(this.intersections.size() <= index)
      {
        this.intersections.add(new Intersection()); 
      }
    }
  }
}
