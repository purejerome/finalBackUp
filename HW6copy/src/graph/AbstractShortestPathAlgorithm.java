package graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;

/**
 * Finds the shortest path from one place to another.
 * @author Jerome Donfack
 *
 */
public abstract class AbstractShortestPathAlgorithm implements ShortestPathAlgorithm
{
  private Collection<StreetSegmentObserver> observers;
  
  /**
   * Creates new AbstractShortestPathAlgorithm.
   */
  public AbstractShortestPathAlgorithm()
  {
    this.observers = new LinkedList<>();
  }
  
  /**
   * Finds path from origin node to desitnation node.
   * @param origin - Node to start from.
   * @param destination - Node to travel to.
   * @param net - Network of nodes.
   * @param allPaths - The shortest path from all nodes to dest.
   * @return - Path from origin node to desination node.
   */
  public abstract Map<String, StreetSegment> findPath(final int origin, 
      final int destination, final StreetNetwork net, 
      final Map<String, Map<String, StreetSegment>> allPaths);
  
  /**
   * Adds a street seg observer.
   * @param obsever - Observer to add.
   */
  public void addStreetSegmentObserver(final StreetSegmentObserver obsever)
  {
    this.observers.add(obsever);
  }
  
  /**
   * Removes a street seg observer.
   * @param obsever - Observer to remove.
   */
  public void removeStreetSegmentObserver(final StreetSegmentObserver obsever)
  {
    this.observers.remove(obsever);
  }
  
  /**
   * Notifies all street seg observers to update.
   * @param segmentIDs - Observers to notifiy.
   */
  public void notifyStreetSegmentObservers(final List<String> segmentIDs)
  {
    for(StreetSegmentObserver observer : this.observers)
    {
      observer.handleStreetSegments(segmentIDs);
    }
  }
}
