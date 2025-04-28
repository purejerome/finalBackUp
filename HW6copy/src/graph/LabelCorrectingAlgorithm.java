package graph;

import java.util.LinkedHashMap;
import java.util.Map;
import feature.Intersection;
import feature.StreetSegment;

/**
 * Uses the label correcting algorithm to find the shortest path.
 * @author Jerome Donfack
 *
 */
public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm
{
  private CandidateLabelManager labels;
  
  /**
   * Create new LabelCorrectingAlgorithm.
   * @param labels - Labels to use.
   */
  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
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
    labels.initializeCandiates(destination);
    while(!labels.isCandiatesEmpty())
    {
      Intersection workingIntersection = net.getIntersection(workingNode.getID());
      for(StreetSegment segment : workingIntersection.getOutbound())
      {
        labels.adjustHeadValue(segment);
      }
      workingNode = labels.getCandidateLabel();
    }
    for(Label curNode : labels.getAllLabels())
    {
      if(curNode.getPredecessor() != null)
      {
        String startSeg = curNode.getPredecessor().getID();
        Map<String, StreetSegment> path = new LinkedHashMap<>();
        while (curNode.getPredecessor() != null)
        {
          StreetSegment segment = curNode.getPredecessor();
          path.put(segment.getID(), segment);
          curNode = labels.getLabel(segment.getTail());
        }
        allPaths.put(startSeg, path);
      }
      else
      {
        System.out.println(curNode.getPredecessor());
        System.out.println(curNode.getValue());
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
