package gps;

/**
 * Notfies GPSObserverS of new data.
 * @author Jerome Dofnack
 *
 */
public interface GPSSubject
{
  /**
   * Add a GPSObserver to notify.
   * @param observer - GPSObserver to add.
   */
  public abstract void addGPSObserver(final GPSObserver observer);
  
  /**
   * Notify the GPSObservers.
   * @param sentence - NMAAE sentence to notifiy the GPSObservers.
   */
  public abstract void notifyGPSObservers(final String sentence);
  
  /**
   * Remove a GPSObserver.
   * @param observer - GPSObserver to remove.
   */
  public abstract void removeGPSObserver(final GPSObserver observer);
}
