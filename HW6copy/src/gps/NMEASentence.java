package gps;

/**
 * Class to handle NMEA Sentences.
 * @author Jerome Donfack
 *
 */
public abstract class NMEASentence
{
  /**
   * Adds chunk to checksum.
   * @param s - String to add.
   * @param originalChecksum - original checksum.
   * @return - The number to return.
   */
  public static int addToChecksum(final String s, 
      final int originalChecksum)
  {
    int current, length;
    int checksum = originalChecksum;
    
    length = s.length();
    for(int i=0; i <= length-1; i++) 
    {
      current = (int)(s.charAt(i));
      checksum ^= current;
    }
    
    return checksum;
  }
  
  /**
   * Convert latitude string to number.
   * @param latitudeString - Latitude in string format.
   * @return - Latitude in number format.
   */
  public static double convertLatitude(final String latitudeString)
  {
    double dd = Double.parseDouble(latitudeString.substring(0, 2));
    double mmmmm = Double.parseDouble(latitudeString.substring(2,9));
    String dir = latitudeString.substring(9);
    double latDeg = dd + (mmmmm / 60);
    if (dir.equals("S")) latDeg *= -1;
    return latDeg;
  }
  
  /**
   * Convert longitude string to number.
   * @param longitudeString - Longitude in string format.
   * @return - Longitude in number format.
   */
  public static double convertLongitude(final String longitudeString)
  {
    double dd = Double.parseDouble(longitudeString.substring(0, 3));
    double mmmmm = Double.parseDouble(longitudeString.substring(3, 10));
    String dir = longitudeString.substring(10);
    double longDeg = dd + (mmmmm / 60);
    if (dir.equals("W")) longDeg *= -1;
    return longDeg;
  }
}
