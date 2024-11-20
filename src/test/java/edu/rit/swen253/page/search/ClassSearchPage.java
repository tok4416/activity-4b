package edu.rit.swen253.page.search;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;

public class ClassSearchPage extends AbstractPage {
    
    // finders to extract various elements from the page
    private static final By SEARCHBAR_FINDER = By.className("completer-input"); 
    private static final By TERM_DROPDOWN_FINDER = By.name("termSelector");
    private static final By SUBMIT_SEARCH_BUTTON_FINDER = By.className("classSearchSearchButton"); 
    private static final By ADVANCED_SEARCH_BUTTON_FINDER = By.className("classSearchAdvancedSearchText"); 
    private static final By ADVANCED_SEARCH_DIALOG_FINDER = By.className("mat-dialog-container");
    private static final By SEARCH_RESULTS_CONTAINER_FINDER = By.className("classSearchBasicResultsMargin");

    private DomElement searchbar;
    private AdvancedSearchView advancedSearchDialog;
    private SearchResultsView searchResultsView;

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
     * This method selects an option from the dropdown "Grade", which will be graduate or undergraduate
     * 
     * @param level The selected course level from the dropdown
     */
    public void selectAdvancedSearchLevel(String level)  {
        advancedSearchDialog.selectLevel(level);
    }

    /**
     * This method closes the advanced search dialog
     */
    public void closeAdvancedSearchDialog() {
        advancedSearchDialog.close();
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

    /**
     * This method finds the text of all elements that appeared as search results after submitting a search
     * 
     * @return A list of strings, each containing the text in the "Name" column of search results
     */
    public List<String> findResultCourseDescriptions() {
        try {
            DomElement resultsContainer = findOnPage(SEARCH_RESULTS_CONTAINER_FINDER);
            searchResultsView = new SearchResultsView(resultsContainer);
            return searchResultsView.getSearchResultsClassText();
        } catch (TimeoutException e) {
            fail("Could not find search results container");
            return null;
        }
    }

    /**
     * This method returns the course levels of the courses in the search results
     * 
     * @return A list of integers, each representing a course level of a search result
     */
    public List<Integer> findResultCourseLevels() {
        try {
            DomElement resultsContainer = findOnPage(SEARCH_RESULTS_CONTAINER_FINDER);
            searchResultsView = new SearchResultsView(resultsContainer);
            return searchResultsView.getResultCourseLevels();
        } catch (TimeoutException e) {
            fail("Could not find search results container");
            return null;
        }
    }
}
