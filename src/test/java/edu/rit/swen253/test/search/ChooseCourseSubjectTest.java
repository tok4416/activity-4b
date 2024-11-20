package edu.rit.swen253.test.search;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;
import edu.rit.swen253.utils.TimingUtils;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChooseCourseSubjectTest extends AbstractWebTest {

  private TigerCenterHomePage homePage;
  private ClassSearchPage searchPage;

  @Test
  @Order(1)
  public void goToHomePage() {
    homePage = navigateToPage("https://tigercenter.rit.edu", TigerCenterHomePage::new);
    assertNotNull(homePage);
  }

  @Test
  @Order(2)
  public void goToSearchPage() {
    homePage.selectClassSearch();
    homePage.waitUntilGone(); // it takes time for the new page to render

    searchPage = assertNewPage(ClassSearchPage::new);
    assertNotNull(searchPage);
  }

  @Test
  @Order(3)
  public void selectOpenAdvancedSearchDialog() {
    searchPage.selectClassTerm("Spring"); // we need to select the class term before we can open this
    searchPage.openAdvancedSearchDialog(); 
  }

  @Test
  @Order(4)
  public void selectCourseSubject() {
    searchPage.selectAdvancedSearchCollege("Golisano Col Comp"); // first need to select the college, then we can select the subject
    searchPage.selectAdvancedSearchSubject("Computer Science");
    TimingUtils.sleep(5);
  }

}
