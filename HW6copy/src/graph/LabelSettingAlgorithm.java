package graph;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import feature.Intersection;
import feature.StreetSegment;

/**
 * Uses the label setting algorithm to find the shortest path.
 * @author Jerome Donfack
 *
 */
public class LabelSettingAlgorithm extends AbstractShortestPathAlgorithm
{
  private PermanentLabelManager labels;
  
  /**
   * Create new LabelSettingAlgorithm.
   * @param labels - Labels to use.
   */
  public LabelSettingAlgorithm(final PermanentLabelManager labels)
  {
    this.labels = labels;
  }
  
  /**
   * Finds path from origin node to desitnation node.
   * @param origin - Node to start from.
   * @param destination - Node to travel to.
   * @param net - Network of nodes.
   * @param allPaths - The shortest path from all nodes to dest.
   * @return - Path from origin node to desination node.
   */
  public Map<String, StreetSegment> findPath(final int origin, final int destination, 
      final StreetNetwork net, final Map<String, Map<String, StreetSegment>> allPaths)
  {
    Label workingNode = labels.getLabel(destination);
    workingNode.setValue(0);
//    labels.makePermanent(workingNode.getID());
    while(workingNode != null)
    {
      labels.makePermanent(workingNode.getID());
      Intersection workingIntersection = net.getIntersection(workingNode.getID());
      for(StreetSegment segment : workingIntersection.getOutbound())
      {
        labels.adjustHeadValue(segment);
      }
      workingNode = labels.getSmallestLabel();
//      labels.makePermanent(workingNode.getID());
    }
    
//    Map<String, Map<String, StreetSegment>> allPathsPros = new HashMap<>();
    for(Label curNode : labels.getAllLabels())
    {
      if(curNode.getPredecessor() != null)
      {
        String startSeg = curNode.getPredecessor().getID();
        Map<String, StreetSegment> path = new LinkedHashMap<>();
        while(curNode.getPredecessor() != null)
        {
          StreetSegment segment = curNode.getPredecessor();
          path.put(segment.getID(), segment);
          curNode = labels.getLabel(segment.getTail());
        }
        allPaths.put(startSeg, path);
      }
    }
    
    Map<String, StreetSegment> path = new LinkedHashMap<>();
    Label currentNode = labels.getLabel(origin);
    while (currentNode.getPredecessor() != null)
    {
      StreetSegment segment = currentNode.getPredecessor();
      path.put(segment.getID(), segment);
      currentNode = labels.getLabel(segment.getTail());
    }
    return path;
  }
}
