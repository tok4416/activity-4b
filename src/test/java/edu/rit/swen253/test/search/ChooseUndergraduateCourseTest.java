package edu.rit.swen253.test.search;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChooseUndergraduateCourseTest extends AbstractWebTest {

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
    homePage.waitUntilGone(); // it takes time for the search page to render

    searchPage = assertNewPage(ClassSearchPage::new);
    assertNotNull(searchPage);
  }

  @Test
  @Order(3)
  public void selectOpenAdvancedSearchDialog() {
    searchPage.selectClassTerm("Spring"); // we need to select the class term before we can open the advanced search dialog
    searchPage.openAdvancedSearchDialog(); 
  }

  @Test
  @Order(4)
  public void selectCourseLevel() {
    searchPage.selectAdvancedSearchLevel("Undergraduate");
    searchPage.closeAdvancedSearchDialog();
  }

  @Test
  @Order(5)
  public void enterSearchTerm() {
    searchPage.enterSearchTerm("SWEN"); // in this case we decide to look for undergraduate SWEN courses
    searchPage.submitSearch();
  }

  @Test
  @Order(6)
  public void verifyCourseLevels() {
    List<Integer> courseLevels = searchPage.findResultCourseLevels();
    for (Integer courseLevel : courseLevels) {
      assertTrue(courseLevel < 600); // undergraduate courses have a level # below 600
    }
  }
}
