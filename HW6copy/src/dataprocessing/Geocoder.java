package dataprocessing;

import feature.*;
import geography.*;
import gui.*;
import math.*;

import java.awt.Shape;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Geocoder converts a street address to geographic coordinates.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class Geocoder
{
  private CartographyDocument<StreetSegment> segments;
  private CartographyDocument<GeographicShape> shapes;
  private Map<String, Street> streets;

  /**
   * Creates a geocodeer.
   * @param shapes - Shapes to use.
   * @param segments - Segments to use.
   * @param streets - Streets to use.
   */
  public Geocoder(final CartographyDocument<GeographicShape> shapes, 
      final CartographyDocument<StreetSegment> segments,
      final Map<String, Street> streets)
  {
    this.segments = segments;
    this.shapes = shapes;
    this.streets = streets;
  }

  /**
   * Geocode a street address.
   * 
   * @param canonicalName The (canonical) name of the Street
   * @param streetNumber The street number
   * @param segmentIDs Contains the segmentIDs corresponding to each location (OUTBOUND)
   * @return The geocoded address (in longitude/latitude)
   */
  public List<double[]> geocode(final String canonicalName, final int streetNumber, 
      final List<String> segmentIDs)
  {
    ArrayList<double[]> result = new ArrayList<double[]>();

    AffineTransform identity = new AffineTransform();
    Street street = streets.get(canonicalName);
    if (street != null)
    {
      List<StreetSegment> matches = street.getSgements(streetNumber);
      for (StreetSegment segment: matches) 
      {
        GeographicShape gshape = segment.getGeographicShape();
        Shape s = gshape.getShape();
        PathIterator pi = s.getPathIterator(identity);

        double[] first = new double[2];
        double[] last = new double[2];
        pi.currentSegment(first);
        do
        {
          pi.currentSegment(last);
          pi.next();
        } while (!pi.isDone());

        double fraction = (double)(streetNumber - segment.getLowaddress())
            / (double)(segment.getHighaddress() - segment.getLowaddress());


        double[] loc = Vector.minus(last, first);
        loc = Vector.times(fraction, loc);
        loc = Vector.plus(first, loc);

        segmentIDs.add(segment.getID());

        result.add(loc);
      }
    }
    return result;
  }
}
