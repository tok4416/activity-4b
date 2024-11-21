package edu.rit.swen253.page.GPA;

import edu.rit.swen253.page.AbstractAngularPage;
import edu.rit.swen253.utils.DomElement;

import java.util.List;

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
    DomElement button = angularView.findChildrenBy(By.className("primaryButton")).get(0);
    button.click();
  }

  public int getClassCount() {
    return angularView.findChildrenBy(By.className("w3-animate-opacity")).size();
  }

  public void addClassInfo(int index, int grade) {
    List<DomElement> classes = angularView.findChildrenBy(By.className("w3-animate-opacity"));
    DomElement classBox = classes.get(index);

    List<DomElement> inputs = classBox.findChildrenBy(By.cssSelector("input"));
    DomElement classname = inputs.get(0);

    String classString = "Class: " + index;
    classname.enterText(classString);

    List<DomElement> dropdowns = classBox.findChildrenBy(By.cssSelector("select"));
    DomElement gradeSelector = dropdowns.get(0);
    List<DomElement> options = gradeSelector.findChildrenBy(By.cssSelector("option"));
    DomElement selectedGrade = options.get(grade);
    
    selectedGrade.click();
  }

  public float getTermGPA(){
    List<DomElement> GPAs = angularView.findChildrenBy(By.className("results"));
    String gpa = GPAs.get(0).getText();
    return Float.parseFloat(gpa);
  }

  public float getCumulativeGPA(){
    List<DomElement> GPAs = angularView.findChildrenBy(By.className("results"));
    String gpa = GPAs.get(1).getText();
    return Float.parseFloat(gpa);
  }

}
