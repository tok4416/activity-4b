package edu.rit.swen253.page.search;


import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import edu.rit.swen253.utils.DomElement;
import edu.rit.swen253.utils.HtmlUtils;

public class CourseCatalogView {
    
    private DomElement catalogDialog;
    private DomElement collegesContainer;

    public CourseCatalogView(DomElement catalogDialog) {
        this.catalogDialog = catalogDialog;
        try {
            DomElement dialogChild = this.catalogDialog.findChildBy(By.tagName("mat-dialog-content"));
            DomElement divChild = dialogChild.findChildBy(HtmlUtils.DIV_FINDER); // there is only 1 div as a child of this element
            collegesContainer = divChild.findChildBy(By.tagName("mat-accordion"));
        } catch (TimeoutException e) {
            fail("Could not initialize catalog's colleges container");
        }
    }

    /**
     * This method opens the specified college dropdown element
     * 
     * @param optionNumber The number of the dropdown being opened
     */
    public void openCollegeOption(int optionNumber) {
        DomElement targetCollege = getCollegeOption(optionNumber);
        targetCollege.click();
    }

    public List<String> getCollegeSubjects(int collegeNumber) {
        List<DomElement> subjectDropdownElements = getCollegeSubjectDropdowns(collegeNumber);
        List<String> subjectTexts = new LinkedList<>();

        for (DomElement subjectDropdown : subjectDropdownElements) {
            try {
                DomElement childExpandElement = subjectDropdown.findChildBy(By.tagName("mat-expansion-panel-header"));
                DomElement rootTextConainter = childExpandElement.findChildBy(HtmlUtils.SPAN_FINDER);
                DomElement subjectCodeContainer = rootTextConainter.findChildBy(By.tagName("mat-panel-title"));
                final String subjectCode = subjectCodeContainer.findChildBy(HtmlUtils.SPAN_FINDER).getText();
                DomElement subjectTitleContainer = rootTextConainter.findChildBy(By.tagName("mat-panel-description"));
                final String subjectTitle = subjectTitleContainer.findChildBy(HtmlUtils.SPAN_FINDER).getText();
                subjectTexts.add(subjectCode + " " + subjectTitle);
            } catch (TimeoutException e) {
                fail("Could not extract subject text from college subject dropdowns");
            }
        }
        return subjectTexts;
    }

    private List<DomElement> getCollegeSubjectDropdowns(int collegeNumber) {
        DomElement targetCollege = getCollegeOption(collegeNumber);
        try {
            DomElement collegeChildDiv = targetCollege.findChildBy(HtmlUtils.DIV_FINDER);
            DomElement nestedDiv = collegeChildDiv.findChildBy(HtmlUtils.DIV_FINDER);
            DomElement rootContainer = nestedDiv.findChildBy(By.tagName("mat-accordion"));
            return rootContainer.findChildrenBy(By.tagName("mat-expansion-panel"));
        } catch (TimeoutException e) {
            fail("Could not get college subject dropdowns");
            return null;
        }
    }

    /**
     * This method gets the specified college dropdown DOM element
     * 
     * @param collegeNumber The number of the college dropdown being retrieved
     * @return The college DOM element
     */
    private DomElement getCollegeOption(int collegeNumber) {
        final String targetId = "colleges" + collegeNumber; 
        try {
            DomElement targetCollege = collegesContainer.findChildBy(By.id(targetId));
            return targetCollege;
        } catch (TimeoutException e) {
            fail("Could not find college number " + (collegeNumber + 1)); // + 1 since we use 0 indexing
            return null;
        }
    }
    
}
