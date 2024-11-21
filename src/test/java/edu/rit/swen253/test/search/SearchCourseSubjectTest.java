package edu.rit.swen253.test.search;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchCourseSubjectTest extends AbstractWebTest {
    
    private TigerCenterHomePage homePage;
    private ClassSearchPage searchPage;

    @Test
    @Order(1)
    public void navigateToHomePage() {
        homePage = navigateToPage("https://tigercenter.rit.edu", TigerCenterHomePage::new);
        assertNotNull(homePage);
    }

    @Test
    @Order(2)
    public void goToSearchPage() {
        homePage.selectClassSearch();
        homePage.waitUntilGone(); // waiting until the search page renders

        searchPage = assertNewPage(ClassSearchPage::new);
        assertNotNull(searchPage);
    }

    @Test
    @Order(3)
    public void enterSearchTerms() {
        searchPage.selectClassTerm("Spring"); // we need to select the class term before we can open the advanced search dialog
        searchPage.enterSearchTerm("CSCI");
        searchPage.submitSearch();
    }

    @Test
    @Order(4)
    public void validateSearchResults() {
        List<String> results = searchPage.findResultCourseDescriptions();
        for (String result : results) { 
            assertTrue(result.contains("CSCI")); // all courses should be computer science courses, and these characters are capitalized in a computer science course
        }
    }

}
