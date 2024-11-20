package edu.rit.swen253.page.search;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;
import edu.rit.swen253.utils.TimingUtils;

public class ClassSearchPage extends AbstractPage {
    
    // finders to extract various elements from the page
    private static final By SEARCHBAR_FINDER = By.className("completer-input"); // the searchbar is the only element on the page with this classname
    private static final By TERM_DROPDOWN_FINDER = By.name("termSelector");
    private static final By SUBMIT_SEARCH_BUTTON_FINDER = By.className("classSearchSearchButton"); // the search submission button is the only element with this class
    private static final By ADVANCED_SEARCH_BUTTON_FINDER = By.className("classSearchAdvancedSearchText"); // the 'advanced search' link is the only element on the page with this classname
    private static final By ADVANCED_SEARCH_DIALOG_FINDER = By.className("mat-dialog-container");

    private DomElement searchbar;
    private AdvancedSearchView advancedSearchDialog;

    public ClassSearchPage() {
        super();
        try {
            searchbar = findOnPage(SEARCHBAR_FINDER);
        } catch (TimeoutException e) {
            fail("Searchbar not found");
        }
    }

    /**
     * This method selects an academic term from the dropdown if it contains the text specified in the 'term' parameter
     * 
     * @param term The text that the selected term must contain
     */
    public void selectClassTerm(String term) {
        DomElement termDropdown;
        try {
            termDropdown = findOnPage(TERM_DROPDOWN_FINDER);
        } catch (TimeoutException e) {
            fail("Could not find term dropdown");
            return;
        }
        termDropdown.click(); // opening selection options of the dropdown
        List<DomElement> options = termDropdown.findChildrenBy(By.cssSelector("*")); // getting all children (dropdown options)
        for (DomElement option : options) { // looking for the spring term from the dropdown, selecting it, then breaking
            if (option.getText().contains(term)) { 
                option.click();
                return;
            }
        }
        fail("Could not find the term '" + term + "' in dropdown"); // if we get here, we didn't find the specified term
    }
 
    /**
     * This method clicks the "Advanced Search" text on the search page to open the dialog with more options
     */
    public void openAdvancedSearchDialog() {
        try {
            DomElement advancedSearchLink = findOnPage(ADVANCED_SEARCH_BUTTON_FINDER);
            advancedSearchLink.click(); // opening the dialog
        } catch (TimeoutException e) {
            fail("Could not find advanced search dialog link");
            return; // we don't want to try to construct the dialog if we can't even click the link
        }
        try {
            DomElement advancedSearchContainer = findOnPage(ADVANCED_SEARCH_DIALOG_FINDER);            
            advancedSearchDialog = new AdvancedSearchView(advancedSearchContainer);
        } catch (TimeoutException e) {
            fail("Could not find advanced search dialog");
        }
    }

    /**
     * This method selects a certain college in the advanced search dialog
     * 
     * @param college A string containing the college being selected. Note that this string should be specific, e.g. "Golisano" can refer to GCCIS or Golisano Institute for Sustainability
     */
    public void selectAdvancedSearchCollege(String college) {
        advancedSearchDialog.selectCollege(college);
    }

    /**
     * This method selects a certain subject in the advanced search dialog
     * 
     * @param subject A string containing the subject being selected
     */
    public void selectAdvancedSearchSubject(String subject) {
        advancedSearchDialog.selectSubject(subject);
    }

    /**
     * This method enters the provided search term text into the searchbar, but does not submit the search terms
     * 
     * @param searchTerm The text that is entered in the searchbar
     */
    public void enterSearchTerm(String searchTerm) {
        searchbar.click(); // firefox requires us to select the element before we enter text
        searchbar.enterText(searchTerm);
    }

    /**
     * This method clicks the search button to submit any entered search terms
     */
    public void submitSearch() {
        try {
            DomElement submitSearchButton = findOnPage(SUBMIT_SEARCH_BUTTON_FINDER);
            submitSearchButton.click();
        } catch (TimeoutException e) {
            fail("Could not find search submission button");
        }
    }
}
