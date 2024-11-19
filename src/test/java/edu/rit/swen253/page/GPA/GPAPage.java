package edu.rit.swen253.page.GPA;

import edu.rit.swen253.page.AbstractAngularPage;
import edu.rit.swen253.utils.DomElement;
import org.openqa.selenium.By;

public class GPAPage extends AbstractAngularPage {

  public GPAPage() {
    super("gpa-calc");
  }
  
  public void clickAdd() {
    DomElement surrounder = angularView.findChildrenBy(By.className("buttonRow")).get(0);
    DomElement button = surrounder.findChildBy(By.cssSelector("button"));
    button.click();
  }
  
  public void clickReset() {
    DomElement button = angularView.findChildrenBy(By.className("secondaryButton")).get(0);
    button.click();
  }
  
  public void clickCalculate() {
    DomElement surrounder = angularView.findChildrenBy(By.className("primaryButton")).get(0);
    DomElement button = surrounder.findChildBy(By.cssSelector("button"));
    button.click();
  }

  public int getClassCount() {
    return angularView.findChildrenBy(By.className("w3-animate-opacity")).size();
  }
}
