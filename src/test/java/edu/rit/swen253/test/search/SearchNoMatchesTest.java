package edu.rit.swen253.test.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchNoMatchesTest extends AbstractWebTest {

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
        homePage.waitUntilGone(); // waiting until the class search page has fully rendered

        searchPage = assertNewPage(ClassSearchPage::new);
        assertNotNull(searchPage);
    }

    @Test
    @Order(3)
    public void enterSearchTerm() {
        searchPage.selectClassTerm("Spring"); // we need to select the class term before we can open the advanced search dialog
        searchPage.enterSearchTerm("No Matches 12345");
        searchPage.submitSearch();
    }

    @Test
    @Order(4)
    public void validateNoResultsText() {
        final String expectedText = "No results were found. Please refine your search.";
        assertEquals(expectedText, searchPage.findNoResultsText());
    }

}
