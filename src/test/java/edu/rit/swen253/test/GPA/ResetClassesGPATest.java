package edu.rit.swen253.test.GPA;

import edu.rit.swen253.page.GPA.GPAPage;
import edu.rit.swen253.test.AbstractWebTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResetClassesGPATest extends AbstractWebTest {

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
  @DisplayName("Third, make sure the classes have been added.")
  void checkCount() {
    assertEquals(6, GPAPage.getClassCount());
  }

  @Test
  @Order(4)
  @DisplayName("Fourth, reset all classes.")
  void resetClasses() {
    GPAPage.clickReset();
  }

  @Test
  @Order(5)
  @DisplayName("Lastly, re-check the count.")
  void checkCountAfterReset() {
    assertEquals(1, GPAPage.getClassCount());
  }

}
