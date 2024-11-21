package edu.rit.swen253.page.HoursAndLocations;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.rit.swen253.utils.SeleniumUtils;

public class HoursAndLocations {
    



    public void clickOnALocation() {
    String location = "Artesano's";
    // findButtonLink(NavButton.HOURS_AND_LOCATIONS).click();
    // SeleniumUtils.findElement(By.xpath("//a[text()='" + location + "']")).click();

    // Wait for the location details to load (either in a new tab or same window)
    WebDriverWait wait = new WebDriverWait(SeleniumUtils.getDriver(), Duration.ofSeconds(20));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("locationDetailsContainer")));
  }
}
