package edu.rit.swen253.test.GPA;

import edu.rit.swen253.page.GPA.GPAPage;
import edu.rit.swen253.test.AbstractWebTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GradesTest extends AbstractWebTest {

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
  @DisplayName("Fourth, add dem grades.")
  void resetClasses() {
    GPAPage.addClassInfo(0, 1); // A
    GPAPage.addClassInfo(1, 2); // A-
    GPAPage.addClassInfo(2, 1); // A
    GPAPage.addClassInfo(3, 2); // A-
    GPAPage.addClassInfo(4, 3); // B+
    GPAPage.addClassInfo(5, 4); // B
  }

  @Test
  @Order(4)
  @DisplayName("Fifth, calculate the GPA.")
  void calculateGPA() {
    GPAPage.clickCalculate();
  }

  @Test
  @Order(5)
  @DisplayName("Lastly, check that the calculated GPA matches.")
  void checkCountAfterReset() {
    assertEquals(3.61,Math.round(GPAPage.getTermGPA()* 100.0) / 100.0);
  }

}
