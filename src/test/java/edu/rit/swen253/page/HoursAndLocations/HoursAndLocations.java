package edu.rit.swen253.page.HoursAndLocations;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.utils.DomElement;
import edu.rit.swen253.utils.HtmlUtils;
import edu.rit.swen253.utils.SeleniumUtils;

public class HoursAndLocations extends TigerCenterHomePage{
    
    private WebDriver web_driver = SeleniumUtils.getDriver();

    /*
     * Hours and Locations constructor
     */
    public HoursAndLocations(){
        super();
    }

    /*
     * Go to hours and Locations Page function
     */
    public void hoursAndLocationsPage(){
        findButtonLink(NavButton.HOURS_AND_LOCATIONS).click();
    }

     /*
     * Select a Location such as Artesano
     */
    public void chooseDiningLocation() {
        String locationName = "Artesano Bakery & Cafe";

        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(20)); 
        WebElement locationLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.linkText(locationName))
        );
        locationLink.click();

        
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("locationDetailsContainer"))
        );
    }

    /*
     * Get hours of Artesano 
     */
    public String getLocationHours() {

        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(20));
        WebElement locationHours = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#locationDetailsContainer .hours"))
        );
        return locationHours.getText();
    }

    
    /*
     * Select Computer Labs button from the top of hours and locations page
     */
    public void selectComputerLabs() {
        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(20));  
        WebElement labsButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("computerLabsButton"))
        );
        labsButton.click();
        wait.until(ExpectedConditions.titleContains("Computer Labs"));
    }

    /*
    * Get computer lab hours from selecting a computer lab
    */
    public String getComputerLabHours(String labName) {

        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(20));  
        WebElement labsButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("computerLabsButton"))
        );
        labsButton.click();
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("labDetailsContainer"))
        );

        WebElement hoursInfo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#labDetailsContainer .hours"))
        );
        return hoursInfo.getText();

    }

   /*
    * Go to Student Affairs through pressing student affairs button
    */
    public void goToStudentAffairs() {
        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(10));  // 10-second timeout for each wait
        WebElement affairsButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("studentAffairsButton"))
        );
        affairsButton.click();
        wait.until(ExpectedConditions.titleContains("Student Affairs"));
    }

    /*
    * Go to Student Affairs through pressing student affairs button
    * and get hours from student affairs 
    */
    public String getStudentAffairsHours(String officeName) {

        WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(20));  
        WebElement labsButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("studentAffairsButton"))
        );
        labsButton.click();
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("affairsDetailsContainer"))
        );

        WebElement hoursInfo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#affairsDetailsContainer .hours"))
        );
        return hoursInfo.getText();

    }

    // // Helper method to get hours from a container
    // private String getHoursFromContainer(String containerCssSelector) {
    //     WebDriverWait wait = new WebDriverWait(web_driver, Duration.ofSeconds(10));  // 10-second timeout for each wait
    //     WebElement hoursElement = wait.until(
    //         ExpectedConditions.visibilityOfElementLocated(By.cssSelector(containerCssSelector))
    //     );
    //     return hoursElement.getText();
    // }
    
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
