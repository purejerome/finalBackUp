package gps;

import java.util.StringTokenizer;

/**
 * Class to represent a GPGGA sentence.
 * @author Jerome Donfack
 *
 */
public class GPGGASentence extends NMEASentence
{
  private String time;
  private double latitude;
  private double longitude;
  private int fixType;
  private int satellites;
  private double dilution;
  private double altitude;
  private String altitudeUnits;
  private double seaLevel;
  private String geoidUnits;
  
  /**
   * Creates a new GPGGASentence.
   * @param time - Current time. 
   * @param latitude - Latitude in DDMM.MMM.
   * @param longitude - Longitude in DDDMM.MMM.
   * @param fixType - 0 = no fix, 1 = GPS, 2 = DGPS.
   * @param satellites - Number of satellites.
   * @param dilution - Horizontal dilution of persision.
   * @param altitude - Altitude above sea level.
   * @param altitudeUnits - Units altitude is measured in.
   * @param seaLevel - Height of mean sea level.
   * @param geoidUnits - Units of above geoid seperation.
   */
  public GPGGASentence(final String time, final double latitude, final double longitude, 
      final int fixType, final int satellites, final double dilution, final double altitude, 
      final String altitudeUnits, final double seaLevel, final String geoidUnits)
  {
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fixType = fixType;
    this.satellites = satellites;
    this.dilution = dilution;
    this.altitude = altitude;
    this.altitudeUnits = altitudeUnits;
    this.seaLevel = seaLevel;
    this.geoidUnits = geoidUnits;
  }
  
  /**
   * Get the time.
   * @return - Time.
   */
  public String getTime()
  {
    return time;
  }
  
  /**
   * Get the latitude.
   * @return - Latitude.
   */
  public double getLatitude()
  {
    return latitude;
  }
  
  /**
   * Get the longitude.
   * @return - Longitude.
   */
  public double getLongitude()
  {
    return longitude;
  }
  
  /**
   * Get the fix type.
   * @return - Fix type.
   */
  public int getFixType()
  {
    return fixType;
  }
  
  /**
   * Get the number of corresponding satellite.
   * @return - Number of satellites.
   */
  public int getSatellites()
  {
    return satellites;
  }
  
  /**
   * Get the dilution.
   * @return - Dilution.
   */
  public double getDilution()
  {
    return dilution;
  }
  
  /**
   * Get the altitude.
   * @return - Altitude.
   */
  public double getAltitude()
  {
    return altitude;
  }
  
  /**
   * Get the altitude units.
   * @return - Altitude units.
   */
  public String getAltitudeUnits()
  {
    return altitudeUnits;
  }
  
  /**
   * Get the sea level.
   * @return - Sea level.
   */
  public double getSeaLevel()
  {
    return seaLevel;
  }
  
  /**
   * Get the geoid units.
   * @return - Geoid units.
   */
  public String getGeoidUnits()
  {
    return geoidUnits;
  }
  
  /**
   * Creates new GPGGA object from provided GPGGA sentence.
   * @param s - GPGGA sentence.
   * @return - GPGGA object.
   */
  public static GPGGASentence parseGPGGA(final String s)
  {
    boolean            ok, nextSum;
    int                validationValue;
    String             checkValue, token;
    StringTokenizer    tokenizer;
    String dollar = "$";
    String astrix = "*";
    
    checkValue = "";
    validationValue  = 0;
    nextSum = false;
    tokenizer = new StringTokenizer(s, "$,*", true);

    while (tokenizer.hasMoreElements())
    {
      token = (String)(tokenizer.nextElement());
      if (nextSum)
      {
        checkValue = token;
      } 
      else
      {
        if(!token.equals(dollar) && !token.equals(astrix))
        {
          validationValue = NMEASentence.addToChecksum(token, validationValue);
        }
      }
      if(token.equals(astrix)) nextSum = true;
    }

    ok = (validationValue == Integer.parseInt(checkValue, 16));
    
    if (!ok) return null;
    
    String[] splitSentence = s.split(",");
    String time = splitSentence[1];
    String latString = splitSentence[2] + splitSentence[3];
    double latitude = latString.isEmpty() ? Double.NaN 
        : NMEASentence.convertLatitude(latString);
    String logngString = splitSentence[4] + splitSentence[5];
    double longitude = logngString.isEmpty() ? Double.NaN 
        : NMEASentence.convertLongitude(logngString);
    int fixType = splitSentence[6].isEmpty() ? -1 
        : Integer.parseInt(splitSentence[6]);
    int satelites = splitSentence[7].isEmpty() ? -1 
        : Integer.parseInt(splitSentence[7]);
    double dilution = splitSentence[8].isEmpty() ? Double.NaN 
        : Double.parseDouble(splitSentence[8]);
    double altitude = splitSentence[9].isEmpty() ? Double.NaN 
        : Double.parseDouble(splitSentence[9]);
    String altUnits = splitSentence[10];
    double seaLevel = splitSentence[11].isEmpty() ? Double.NaN 
        : Double.parseDouble(splitSentence[11]);
    String geoidUnits = splitSentence[12];
    return new GPGGASentence(time, latitude, longitude, fixType, satelites,
        dilution, altitude, altUnits, seaLevel, geoidUnits);
  }

}
