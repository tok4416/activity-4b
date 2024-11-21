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
public class SearchNoTermSelectedTest extends AbstractWebTest {

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
        searchPage.enterSearchTerm("SWEN"); // we have NOT selected a term, so while this term would normally get results, here we do not
        searchPage.submitSearch();
    }

    @Test
    @Order(4)
    public void validateNoResultsText() {
        final String expectedText = "Please select a term from the dropdown menu.";
        assertEquals(expectedText, searchPage.findNoResultsText());
    }
    
}
