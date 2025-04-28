package matching;

import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Queue;

import javax.swing.SwingWorker;
import feature.StreetSegment;
import geography.PiecewiseLinearCurve;
import grid.GridTuple;
import math.Vector;

public class MapMatchingFactory 
    extends SwingWorker<GridTuple<GridTuple<Double, Point2D.Double>, 
    StreetSegment>, Point2D.Double>
{
  private HashSet<StreetSegment> surroundingSegments;
  private Point2D.Double currentPosition;
  private Queue<Point2D.Double> inertia;
  
  public MapMatchingFactory(final HashSet<StreetSegment> surroundingSegments,
      final Point2D.Double currentPosition, Queue<Point2D.Double> inertia)
  {
    this.surroundingSegments = surroundingSegments;
    this.currentPosition = currentPosition;
    this.inertia = inertia;
  }
  
  private Point2D.Double pointToLine(final Point2D.Double headPoint, 
      final Point2D.Double tailPoint, final Point2D.Double gpsPoint)
  {
      double segX1 = headPoint.getX();
      double segY1 = headPoint.getY();
      double segX2 = tailPoint.getX();
      double segY2 = tailPoint.getY();
      
      double gpsX = gpsPoint.getX();
      double gpsY = gpsPoint.getY();
      
      double dx = segX2 - segX1;
      double dy = segY2 - segY1;
      
      double dx1 = gpsX - segX1;
      double dy1 = gpsY - segY1;
      
      double dotProduct = dx * dx1 + dy * dy1;
      double segmentLengthSquared = dx * dx + dy * dy;
      
      double t = dotProduct / segmentLengthSquared;
      
      t = Math.max(0, Math.min(1, t));
      
      double projX = segX1 + t * dx;
      double projY = segY1 + t * dy;
      
      return new Point2D.Double(projX, projY);
  }
  
  private GridTuple<Double, Point2D.Double> findDistance(final StreetSegment seg, 
      final Point2D.Double gpsPos)
{
    double minInnerDistance = Double.POSITIVE_INFINITY;
    Point2D.Double snapPoint = null;
    PathIterator pathIterator = ((PiecewiseLinearCurve) seg.getGeographicShape())
        .getShape().getPathIterator(null);
    double[] coords = new double[6];
    Point2D.Double prevPoint = null;
    while (!pathIterator.isDone())
    {
      pathIterator.currentSegment(coords);
      Point2D.Double curPoint = new Point2D.Double(coords[0], coords[1]);
      if(prevPoint != null)
      {
        Point2D.Double newPos = pointToLine(prevPoint, curPoint, gpsPos);
        Line2D.Double tempLine = new Line2D.Double(prevPoint, curPoint);
        double distance = tempLine.ptSegDist(gpsPos);
        if(distance < minInnerDistance)
        {
          minInnerDistance = distance;
          snapPoint = newPos;
        }
      }
      prevPoint = curPoint;
      pathIterator.next();
    }
    return new GridTuple<>(minInnerDistance, snapPoint);
}

  @Override
  protected GridTuple<GridTuple<Double, 
    Point2D.Double>, StreetSegment> doInBackground() throws Exception
  {
    double minDistance = Double.POSITIVE_INFINITY;
    StreetSegment bestSeg = null;
    Point2D.Double snapPoint = null;
    for(StreetSegment seg : surroundingSegments)
    {
      double distance = 0;
      for(Point2D.Double inertiaPoint : this.inertia)
      {
        distance += this.findDistance(seg, inertiaPoint).getLeft();
      }
      
      GridTuple<Double, Point2D.Double> evalDistPoint = 
          this.findDistance(seg, this.currentPosition);
      distance += evalDistPoint.getLeft();
      if(distance < minDistance)
      {
        minDistance = distance;
        bestSeg = seg;
        snapPoint = evalDistPoint.getRight();
      }
    }
    
    return new GridTuple<>(new GridTuple<>(minDistance, snapPoint), bestSeg);
  }

}
