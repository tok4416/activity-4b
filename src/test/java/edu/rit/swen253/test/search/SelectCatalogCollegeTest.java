package edu.rit.swen253.test.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;
import edu.rit.swen253.utils.TimingUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelectCatalogCollegeTest extends AbstractWebTest {
    
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
    public void openCourseCatalog() {
        searchPage.clickCourseCatalogLink();
    }

    @Test
    @Order(4)
    public void openTopCollegeOption() {
        searchPage.openCollegeOption(0);
    }

    @Test
    @Order(5)
    public void getTopCollegeOptionSubjects() {
        List<String> subjectTexts = searchPage.getCollegeSubjects(0);
        final String[] expectedSubjects = new String[] {"ARED - Art Education", "ARTH - Art History", "ARTX - Art Experience",
                                                  "CCER - Ceramics", "DDDD - 3D Digital Design", "PHAP - Advertising Photography", 
                                                  "SOFA - Film & Animation"};

        for (int i = 0; i < expectedSubjects.length; i++) {
            assertEquals(expectedSubjects[i], subjectTexts.get(i));
        }
    }

}
