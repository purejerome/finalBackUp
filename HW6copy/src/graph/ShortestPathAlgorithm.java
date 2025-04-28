package graph;

import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;

/**
 * Finds the shortest path from one place to another.
 * @author Jerome Donfack
 *
 */
public interface ShortestPathAlgorithm
{
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
  public abstract void addStreetSegmentObserver(final StreetSegmentObserver obsever);
  
  /**
   * Removes a street seg observer.
   * @param obsever - Observer to remove.
   */
  public abstract void removeStreetSegmentObserver(final StreetSegmentObserver obsever);

  /**
   * Notifies all street seg observers to update.
   * @param segmentIDs - Observers to notifiy.
   */
  public abstract void notifyStreetSegmentObservers(final List<String> segmentIDs);
}
