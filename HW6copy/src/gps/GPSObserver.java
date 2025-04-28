package gps;

/**
 * Handles the GPS data given to it.
 * @author Jerome Donfack
 *
 */
public interface GPSObserver
{
  /**
   * Converts the GPS data into usable coordinate data.
   * @param sentence - The NMEA sentence to handle.
   */
  public abstract void handleGPSData(final String sentence);
}
