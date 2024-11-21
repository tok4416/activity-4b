package edu.rit.swen253.page.AppNavigation;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.utils.DomElement;
import edu.rit.swen253.utils.HtmlUtils;
import edu.rit.swen253.utils.SeleniumUtils;

 /**
  * @author Tyler Kranzen tok4416
  * App Navigation Class 
  */
public class AppNavigation extends TigerCenterHomePage{

    /**
     * App Navigation Constructor
     */
    public AppNavigation(){
        super();
    }

    public void goToTigerCenterHompage(){
        SeleniumUtils.getDriver().get("https://tigercenter.rit.edu");
    }

      /*
     * Select the <em>Gpa Calculator</em> feature.
     * <p>
     * This action results in a directs you to the GPA Calculator web page.
     */
    public void gpaCalculator(){
        findButtonLink(NavButton.GPA_CALCULATOR).click();
    }

    /*
     * Select the <em>Hours and Locations</em> feature.
     * <p>
     * This action results in a directs you to the Hours and Locations web page.
     */
    public void hoursAndLocations(){
        findButtonLink(NavButton.HOURS_AND_LOCATIONS).click();
    }


    /**
     * Select the <em>Hours and Locations</em> feature (can use any other feature from Tiger Center Hompage).
     * <p>
     * This action results in a directs you to the Hours and Locations web page 
     * Will be testing all options on the LeftSideNavigationBar 
     */
    public void leftSideNavigationBar(String feature_name){
        //Wait for WebDriver to load to be able to interact
        WebDriverWait wait = new WebDriverWait(SeleniumUtils.getDriver(), Duration.ofSeconds(20));

        // Locate the button using CSS Selector
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[mattooltip='" + feature_name + "']")
        ));

        // Scroll to the button and click
        Actions actions = new Actions(SeleniumUtils.getDriver());
        actions.moveToElement(button).click().perform();
    }

      /**
   * Select the <em>Maps at RIT</em> feature.
   * <p>
   * This action results in a new window/tab that display the map.rit.edu web page.
   */
  public void selectMapsAtRIT() {
    findButtonLink(NavButton.MAPS_AT_RIT).click();
  }

  /**
   * Select the <em>Class Search</em> feature.
   * <p>
   * This action navigates to the Class Search page of the TigerCenter app.
   */
  public void selectClassSearch() {
    findButtonLink(NavButton.CLASS_SEARCH).click();
  }

  public void clickTopRightLinks(String topRightLink) {
    WebDriverWait wait = new WebDriverWait(SeleniumUtils.getDriver(), Duration.ofSeconds(20));
    WebElement feedbackLink = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("a[href='" + topRightLink + "']")
    ));

    //Scroll to get to link
    Actions actions = new Actions(SeleniumUtils.getDriver());
    actions.moveToElement(feedbackLink).click().perform();

    // If a link opens a new window handle it because
    // Selenium does not like having more than 1 window

    Set<String> windowHandles = SeleniumUtils.getDriver().getWindowHandles();
    if (windowHandles.size() > 1) {
        String mainWindow = SeleniumUtils.getDriver().getWindowHandle();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindow)) {
                SeleniumUtils.getDriver().switchTo().window(handle);
                break;
            }
        }
    }
  }

  public void HoursAndLocationsLeftBar(){
    findButtonLink(NavButton.HOURS_AND_LOCATIONS).click();
    WebElement LeftBarhoursButton = SeleniumUtils.getDriver().findElement(By.linkText("Hours & Locations"));
    LeftBarhoursButton.click();
  }

  public void classSearchLeftBar(){
    findButtonLink(NavButton.CLASS_SEARCH).click();
    WebElement classSearchButton = SeleniumUtils.getDriver().findElement(By.linkText("Class Search"));
    classSearchButton.click();
  }

  public void GpaCalculatorLeftBar() {
    findButtonLink(NavButton.GPA_CALCULATOR).click();
    WebElement gpaCalculatorButton = SeleniumUtils.getDriver().findElement(By.linkText("GPA Calculator"));
    gpaCalculatorButton.click();
  }
  

  private enum NavButton {
    CLASS_SEARCH("Class Search"),
    GPA_CALCULATOR("GPA Calculator"),
    HOURS_AND_LOCATIONS("Hours & Locations"),
    MAPS_AT_RIT("Maps at RIT")
    ;

    private final String buttonText;

    NavButton(final String buttonText) {
      this.buttonText = buttonText;
    }
  }

  private DomElement findButtonLink(final NavButton navButton) {
    final List<DomElement> allAnchors = angularView.findChildrenBy(HtmlUtils.ANCHOR_FINDER);
    final String buttonFinderXPath = String.format("./div[text() = '%s']", navButton.buttonText);
    final By buttonFinder = By.xpath(buttonFinderXPath);
    return allAnchors.stream()
      .filter(anchor -> anchor.hasChild(buttonFinder))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Could not find button link"));
  }
}


