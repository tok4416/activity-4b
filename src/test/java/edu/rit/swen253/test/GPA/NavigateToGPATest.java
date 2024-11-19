package edu.rit.swen253.test.GPA;

import edu.rit.swen253.page.SimplePage;
import edu.rit.swen253.page.tiger.TigerCenterHomePage;
import edu.rit.swen253.test.AbstractWebTest;
import edu.rit.swen253.utils.BrowserWindow;
import org.junit.jupiter.api.*;

import static edu.rit.swen253.utils.TimingUtils.sleep;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NavigateToGPATest extends AbstractWebTest {

  private TigerCenterHomePage homePage;
  private BrowserWindow<TigerCenterHomePage> homeWindow;

  //
  // Test sequence
  //

  @Test
  @Order(1)
  @DisplayName("First, navigate to the Tiger Center Home page.")
  void navigateToHomePage() {
    homePage = navigateToPage("https://tigercenter.rit.edu", TigerCenterHomePage::new);
    assertNotNull(homePage);
    homeWindow = getCurrentWindow();
  }

  @Test
  @Order(2)
  @DisplayName("Second, click on the GPA Calculator button and validate navigation.")
  void navigateToGPA() {
    homePage.selectGPA();
    final SimplePage currentPage = assertNewPage(SimplePage::new);

    // // there's a timing issue(give it a second to render)]
    sleep(1);

    assertEquals("https://tigercenter.rit.edu/tigerCenterApp/api/gpa-calc", currentPage.getURL());
  }

  @Test
  @Order(3)
  @DisplayName("Just to validate the new switchToWindow API.")
  void switchToApp() {
    assertNotSame(homePage, getCurrentWindow().page(), "Before switch");
    switchToWindow(homeWindow);
    assertSame(homePage, getCurrentWindow().page(), "After switch");
  }
}
