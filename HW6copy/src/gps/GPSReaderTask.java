package gps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * Reads in the stream of gps data.
 * @author Jerome Donfack
 *
 */
public class GPSReaderTask extends SwingWorker<Void, String> implements GPSSubject
{
  private BufferedReader in;
  private String[] sentences;
  private Collection<GPSObserver> observers;
  /**
   * Creates a new GPSReaderTask.
   * @param in - Input stream of gps data.
   * @param sentences - Previous stream of data?
   */
  public GPSReaderTask(final InputStream in, final String... sentences)
  {
    this.in = new BufferedReader(new InputStreamReader(in));
    this.sentences = sentences;
    this.observers = new HashSet<>();
  }
  
  
  @Override
  protected Void doInBackground()
  {
    String line;
    try
    {
      while(!this.isCancelled() && (line = in.readLine()) != null)
      {
        publish(line);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }


  @Override
  public void addGPSObserver(final GPSObserver observer)
  {
    this.observers.add(observer);
  }


  @Override
  public void notifyGPSObservers(final String sentence)
  {
    for(GPSObserver obs : this.observers)
    {
      obs.handleGPSData(sentence);
    }
  }


  @Override
  public void removeGPSObserver(final GPSObserver observer)
  {
    this.observers.remove(observer);
  }
  
  /**
   * Processes the gps sentences fed in.
   * @param lines - Sentences to be processed.
   */
  public void process(final List<String> lines)
  {
    for(String line : lines)
    {
      for(String sentence : this.sentences)
      {
        if(line.startsWith("$" + sentence))
        {
          this.notifyGPSObservers(line);
          break;
        }
      }
    }
  }
}
