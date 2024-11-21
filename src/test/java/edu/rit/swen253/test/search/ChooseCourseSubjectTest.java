package edu.rit.swen253.test.search;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import edu.rit.swen253.page.search.ClassSearchPage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;

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
    searchPage.closeAdvancedSearchDialog(); // preparing for the next test
  }

  @Test
  @Order(5)
  public void enterSearchTerm() {
    searchPage.enterSearchTerm("intro"); // there are many courses from many subjects that have "intro" in the title, so this is a good test to ensure we only get computer science courses
    searchPage.submitSearch();
  }

  @Test
  @Order(6)
  public void verifyCourseDescriptionText() {
    List<String> results = searchPage.findResultCourseDescriptions();
    for (String result : results) {
      assertTrue(result.contains("CSCI")); // this should be a computer science course, and these characters are capitalized in a computer science course
      assertTrue(result.toLowerCase().contains("intro")); // we don't care the casing of this, though the course likely contains the string "Intro"
    }
  }

}
