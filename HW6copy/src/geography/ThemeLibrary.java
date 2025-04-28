package geography;

/**
 * Place to store themes to be used.
 * @author Jerome
 *
 */
public interface ThemeLibrary
{
  /**
   * Gets theme for highlighted lines.
   * @return - Highlighted lines theme.
   */
  public abstract Theme getHighlightTheme();
  /**
   * Gets theme for all lines.
   * @param code - Code for theme to get.
   * @return - All lines theme.
   */
  public abstract Theme getTheme(String code);
}
