package edu.rit.swen253.test.AppNavigationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.rit.swen253.page.AppNavigation.AppNavigation;
import edu.rit.swen253.test.AbstractWebTest;
import edu.rit.swen253.utils.SeleniumUtils;

 /**
 *  Tests for App Navigation on RIT Tiger Center homepage
 *  
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppNavigationTest extends AbstractWebTest{


    public AppNavigation tigercenter;

    /**
     * Set up function
     */
    @BeforeEach
    public void setUp() {
        // Give time for response due to website loading (based on internet connection) or else test fails
        SeleniumUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); 
        tigercenter = navigateToPage("https://tigercenter.rit.edu", AppNavigation::new);

        // Handle new windows being created when selected on certain links
        // that generate new windows
        Set<String> extraWindowHandler = SeleniumUtils.getWindowHandles();
        String mainWindowHandler = SeleniumUtils.getDriver().getWindowHandle();

        extraWindowHandler.stream()
            .filter(handle -> !handle.equals(mainWindowHandler))
            .forEach(handle -> {
                SeleniumUtils.getDriver().switchTo().window(handle).close();
            });

        SeleniumUtils.getDriver().switchTo().window(mainWindowHandler);

        WebDriverWait wait = new WebDriverWait(SeleniumUtils.getDriver(), Duration.ofSeconds(20));
        wait.until(driver -> SeleniumUtils.getWindowHandles().size() == 1);
    }
    /**
     * Class Search Page Test
     */
    @Test
    @Order(1)
    public void classSearchTest(){
        // Give time for response due to website loading (based on internet connection) or else test fails
        SeleniumUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); 
        tigercenter.selectClassSearch();
    }

     /**
     * GPA Calculator Test
     */
    @Test
    @Order(2)
    public void gpaCalculatorTest(){
        // Give time for response due to website loading (based on internet connection) or else test fails
        SeleniumUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        tigercenter.gpaCalculator();
    }

     /**
     * Hours and Locations Page Test
     */
    @Test
    @Order(3)
    public void hoursAndLocationsTest(){
        // Give time for response due to website loading (based on internet connection) or else test fails
        SeleniumUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); 
        tigercenter.hoursAndLocations();

        Set<String> extraWindowHandler = SeleniumUtils.getWindowHandles();
        String mainWindowHandler = SeleniumUtils.getDriver().getWindowHandle();

        extraWindowHandler.stream()
            .filter(handle -> !handle.equals(mainWindowHandler))
            .forEach(handle -> {
                SeleniumUtils.getDriver().switchTo().window(handle).close();
            });

        SeleniumUtils.getDriver().switchTo().window(mainWindowHandler);
    }


     /**
     * Select Maps at RIT Page Test
     */
    @Test
    @Order(4)
    public void selectMapAtRITTest(){
        // Give time for response due to website loading (based on internet connection) or else test fails
        SeleniumUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); 
        tigercenter.selectMapsAtRIT();

        // Handle new window being open when clicked 
        // on SelectMapAtRIT button to go to its webpage

        Set<String> extraWindowHandler = SeleniumUtils.getWindowHandles();
        String mainWindowHandler = SeleniumUtils.getDriver().getWindowHandle();

        extraWindowHandler.stream()
            .filter(handle -> !handle.equals(mainWindowHandler))
            .forEach(handle -> {
                SeleniumUtils.getDriver().switchTo().window(handle).close();
            });

        SeleniumUtils.getDriver().switchTo().window(mainWindowHandler);
    }

    @Test
    @Order(5)
    public void clickFeedbackTest(){
        tigercenter.clickTopRightLinks("https://help.rit.edu/sp?id=sc_cat_item&sys_id=36a3646ddbb60c50881c89584b9619f5");
    }


    @Test
    @Order(6)
    public void clickSupportTest(){
        tigercenter.clickTopRightLinks("https://help.rit.edu/sp?id=sc_cat_item&sys_id=9b613be5dbf24c50881c89584b961986");
    }

    @Test
    @Order(7)
    public void clickDirectoriesTest(){
        tigercenter.clickTopRightLinks("https://www.rit.edu/directory");
    }

    @Test
    @Order(8)
    public void clickRitHomepageTest(){
        tigercenter.clickTopRightLinks("https://www.rit.edu");

        //Handle new window being created when
        // RIT Home link has been clicked
        Set<String> extraWindowHandler = SeleniumUtils.getWindowHandles();
        String mainWindowHandler = SeleniumUtils.getDriver().getWindowHandle();

        extraWindowHandler.stream()
            .filter(handle -> !handle.equals(mainWindowHandler))
            .forEach(handle -> {
                SeleniumUtils.getDriver().switchTo().window(handle).close();
            });

        SeleniumUtils.getDriver().switchTo().window(mainWindowHandler);
    }

}