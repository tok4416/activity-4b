package edu.rit.swen253.test.GPA;

import edu.rit.swen253.page.GPA.GPAPage;
import edu.rit.swen253.test.AbstractWebTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddClassesGPATest extends AbstractWebTest {

  private GPAPage GPAPage;

  //
  // Test sequence
  //

  @Test
  @Order(1)
  @DisplayName("First, navigate to the page.")
  void navigateToHomePage() {
    GPAPage = navigateToPage("https://tigercenter.rit.edu/tigerCenterApp/api/gpa-calc", GPAPage::new);
    assertNotNull(GPAPage);
  }

  @Test
  @Order(2)
  @DisplayName("Second, add dem classes.")
  void addClasses() {
    GPAPage.clickAdd();
    GPAPage.clickAdd();
    GPAPage.clickAdd();
    GPAPage.clickAdd();
    GPAPage.clickAdd();
  }

  @Test
  @Order(3)
  @DisplayName("Lastly, make sure the classes have been added.")
  void checkCount() {
    assertEquals(6, GPAPage.getClassCount());
  }

}
