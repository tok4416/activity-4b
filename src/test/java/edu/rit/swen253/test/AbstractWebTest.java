package edu.rit.swen253.test;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.page.PageFactory;
import edu.rit.swen253.utils.BrowserWindow;
import edu.rit.swen253.utils.SeleniumUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The base class for all Web UI test classes.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractWebTest {

  static {
    // configure logger; only need to do this once
    System.setProperty(
      "java.util.logging.SimpleFormatter.format",
      // <DATE> <SOURCE> <LEVEL> <MESSAGE>
      "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s %4$s: %5$s%n"
    );
  }

  private final Map<String, BrowserWindow<?>> windowMap = new HashMap<>();
  private BrowserWindow<?> currentWindow;

  protected AbstractWebTest() {
  }

  //
  // Test life cycle methods
  //

  /**
   * Set up the Selenium driver for every Web UI test class.
   */
  @BeforeAll
  public void setUpSeleniumDriver() {
    SeleniumUtils.setupDriver();
  }

  /**
   * Tear down the Selenium driver after the whole test class finishes.
   */
  @AfterAll
  public final void tearDown() {
    SeleniumUtils.shutdownDriver();
  }

  //
  // Protected methods
  //

  /**
   * Navigate to any URL and create Page object when it is rendered.
   *
   * @param pageURL  the URL string to navigate to
   * @param pageFactory  the Factory function to generate the Page Object
   * @return the Page Object
   */
  protected <P extends AbstractPage> P navigateToPage(final String pageURL, final PageFactory<P> pageFactory) {
    final P page = SeleniumUtils.navigateToPage(pageURL, pageFactory);
    // validate still on the initial window
    final Set<String> windowHandles = SeleniumUtils.getWindowHandles();
    assertEquals(1, windowHandles.size(), "Should only have one window handle");
    final String initialWindowHandle = windowHandles.iterator().next();
    cacheWindow(initialWindowHandle, page);
    //
    return page;
  }

  public <P extends AbstractPage> BrowserWindow<P> getCurrentWindow() {
    return (BrowserWindow<P>) currentWindow;
  }

  /**
   * Assert that the driver is on a new page and return the Page Object for that page.
   * @param pageFactory  the Factory function to generate the Page Object
   * @return the Page Object
   */
  protected <P extends AbstractPage> P assertNewPage(final PageFactory<P> pageFactory) {
    try {
      // attempt to create a new Page Object for the current window
      final P page = pageFactory.createPage();
      // update Current Window cache
      cacheWindow(currentWindow.handle(), page);
      // return the page
      return page;

    } catch (Exception e) {
      fail("Page creation failed: " + e.getMessage());
      return null;
    }
  }

  /**
   * Assert that the driver see exactly one new window (or tab) and return the Page Object for that window.
   *
   * @param pageFactory  the Factory function to generate the Page Object for the new window
   * @return the Page Object
   */
  protected <P extends AbstractPage> P assertNewWindowAndSwitch(final PageFactory<P> pageFactory) {
    // find the window handle for the new tab
    final String newTabHandle = SeleniumUtils.getWindowHandles().stream()
      .filter(handle -> !windowMap.containsKey(handle))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Could not find valid window handle for the new tab."));

    // switch to that tab and assert that the URL of this page matches the expected maps website
    SeleniumUtils.getDriver().switchTo().window(newTabHandle);

    // create a new Page Object for this tab
    final P page = pageFactory.createPage();
    cacheWindow(newTabHandle, page);
    return page;
  }

  protected <P extends AbstractPage> P switchToWindow(final BrowserWindow<P> window) {
    // guard condition
    if (!windowMap.containsKey(window.handle())) {
      fail(String.format("Unknown window handle: %s", window.handle()));
    }
    //
    currentWindow = window;
    SeleniumUtils.getDriver().switchTo().window(window.handle());
    // return the Page Object on that tab
    return window.page();
  }

  private <P extends AbstractPage> void cacheWindow(final String windowHandle, final P page) {
    currentWindow = new BrowserWindow<>(windowHandle, page);
    windowMap.put(windowHandle, currentWindow);
  }
}
