package edu.rit.swen253.page.search;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import edu.rit.swen253.utils.DomElement;

public class SearchResultsView {
    
    private static final By CONTAINER_CHILD_FINDER = By.tagName("app-class-search-row"); // all children of the container of search results that we want to find have this tag name

    private DomElement resultsContainer;
    private List<DomElement> results;

    /**
     * This constructor initializes the resultsContainer field and attempts to initialize a list of its child divs that each contain a search result
     * 
     * @param resultsContainer The container that the view is initialized with
     */
    public SearchResultsView(DomElement resultsContainer) {
        this.resultsContainer = resultsContainer;
        try {
            results = this.resultsContainer.findChildrenBy(CONTAINER_CHILD_FINDER);
        } catch (TimeoutException e) {
            fail("Could not find search result divs");
        }
    }

    /**
     * This method finds the text of all elements that appeared as search results after submitting a search
     * 
     * @return A list of strings, each containing the text in the "Name" column of search results
     */
    public List<String> getSearchResultsClassText() {
        List<String> resultsClassText = new LinkedList<>();

        for (DomElement resultContainer : results) {
            try {
                DomElement lowerContainer = resultContainer.findChildBy(By.className("classSearchBasicResultsDecorator"));
                DomElement rootContainer = lowerContainer.findChildBy(By.className("classSearchDivHover"));
                DomElement columnHeader = rootContainer.findChildBy(By.className("ng-star-inserted"));
                DomElement classTextDivContainer = columnHeader.findChildBy(By.className("col-xs-2"));
                DomElement classTextElement = classTextDivContainer.findChildBy(By.className("classSearchBasicResultsText"));
                String result = "";
                for (DomElement textHolder : classTextElement.findChildrenBy(By.tagName("span"))) {
                    result += textHolder.getText() + "\n";
                }
                resultsClassText.add(result);
            } catch (TimeoutException e) {
                fail("Could not find container element");
                return resultsClassText;
            }
        }

        return resultsClassText;
    }

    /**
     * This method returns the course levels of the courses in the search results
     * 
     * @return A list of integers, each representing a course level of a search result
     */
    public List<Integer> getResultCourseLevels() {
        List<Integer> courseResultLevels = new LinkedList<>();
        List<String> courseTexts = getSearchResultsClassText();
        Pattern courseCodeFinder = Pattern.compile("SWEN \\d{2,3}"); // course level values are of the form "SWEN ###" (SWEN 99 has 2 numbers, hence \\d{2,3})
        for (String courseText : courseTexts) {
            Matcher levelMatcher = courseCodeFinder.matcher(courseText);
            levelMatcher.find();
            int startIndex = levelMatcher.start();
            int endIndex = levelMatcher.end();
            if (startIndex == -1) {
                fail("Could not extract course level from course element text");
                return courseResultLevels;
            }
            Integer courseLevel = Integer.parseInt(courseText.substring(startIndex + 5, endIndex)); // skipping over the 'SWEN ' part of the text
            courseResultLevels.add(courseLevel);
        }
        return courseResultLevels;
    }

}
