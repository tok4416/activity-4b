package edu.rit.swen253.page.search;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.openqa.selenium.By;

import edu.rit.swen253.utils.DomElement;

/**
 * This class is used to make it easier for the ClassSearchPage class to interact with the Advanced Search dialog
 */
public class AdvancedSearchView {
    
    private DomElement advancedSearchContainer;
    private DomElement advancedDialog;
    private DomElement optionsContainerRoot;

    private static final By OPTION_DIVS_FINDER = By.className("advancedSearchNoPaddingLeftRight");
    private static final By CLOSE_BUTTON_FINDER = By.className("advancedSearchDoneButton");

    /**
     * Construct a new AdvancedSearchView & inject the container of the advanced search dialog into the class
     * 
     * @param container The container of the advanced search dialog
     */
    public AdvancedSearchView(DomElement container) {
        advancedSearchContainer = container;
        advancedDialog = advancedSearchContainer.findChildBy(By.className("ng-star-inserted")); 

        DomElement optionsContainer = advancedDialog.findChildBy(By.className("mat-dialog-content")); 
        DomElement optionsContainerDiv = optionsContainer.findChildBy(By.className("row"));
        optionsContainerRoot = optionsContainerDiv.findChildBy(By.className("col-xs-12"));
    }

    /**
     * This method selects a certain college in the advanced search dialog
     * 
     * @param college A string containing the college being selected. Note that this string should be specific, e.g. "Golisano" can refer to GCCIS or Golisano Institute for Sustainability
     */
    public void selectCollege(String college) {
        List<DomElement> optionsDivs = optionsContainerRoot.findChildrenBy(OPTION_DIVS_FINDER);
        DomElement collegeDiv = optionsDivs.get(2).findChildBy(By.className("styledSelect"));
        DomElement dropdown = collegeDiv.findChildBy(By.className("styledSelectWidthFix"));
        dropdown.click(); // opening the dropdown to make its options clickable
        
        List<DomElement> options = dropdown.findChildrenBy(By.cssSelector("*")); // getting all options in the dropdown
        for (DomElement option : options) { // finding the option that matches the provided college & immediately breaking
            if (option.getText().contains(college)) {
                option.click();
                return; 
            }
        }
        fail("Could not find college option containing text '" + college + "'");
    }

    /**
     * This method selects a certain subject in the advanced search dialog
     * 
     * @param subject A string containing the subject being selected
     */
    public void selectSubject(String subject) {
        List<DomElement> optionsDivs = optionsContainerRoot.findChildrenBy(OPTION_DIVS_FINDER);
        DomElement collegeDiv = optionsDivs.get(3).findChildBy(By.className("styledSelect"));
        DomElement dropdown = collegeDiv.findChildBy(By.className("styledSelectWidthFix"));
        dropdown.click(); // opening the dropdown to make its options clickable

        List<DomElement> options = dropdown.findChildrenBy(By.cssSelector("*")); // getting all options in the dropdown
        for (DomElement option : options) { // finding the option that matches the provided subject & immediately breaking
            if (option.getText().contains(subject)) {
                option.click();
                return; 
            }
        }
        fail("Could not find college option containing text '" + subject + "'");
    }

    /**
     * This method selects an option from the dropdown "Grade", which will be graduate or undergraduate
     * 
     * @param level The selected course level from the dropdown
     */
    public void selectLevel(String level) {
        // the targeted dropdown seems to be the 3rd to last div
        List<DomElement> optionsDivs = optionsContainerRoot.findChildrenBy(OPTION_DIVS_FINDER);
        DomElement levelDiv = optionsDivs.get(optionsDivs.size() - 3).findChildBy(By.className("styledSelect"));
        DomElement dropdown = levelDiv.findChildBy(By.className("styledSelectWidthFix"));
        dropdown.click(); // opening the dropdown to make its options clickable

        List<DomElement> options = dropdown.findChildrenBy(By.cssSelector("*")); // getting all options in the dropdown
        for (DomElement option : options) { // finding the option that matches the provided subject & immediately breaking
            if (option.getText().contains(level)) {
                option.click();
                return; 
            }
        }
        fail("Could not find level option containing text '" + level + "'");
    }

    /**
     * This method closes the advanced search view
     */
    @SuppressWarnings("static-access")
    public void close() {
        DomElement closeButton = advancedSearchContainer.findBy(CLOSE_BUTTON_FINDER);
        closeButton.click();
    }

}
