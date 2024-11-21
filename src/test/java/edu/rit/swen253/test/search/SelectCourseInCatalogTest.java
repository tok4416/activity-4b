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
public class SelectCourseInCatalogTest extends AbstractWebTest {
    
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
    public void openTopSubjectOption() {
        searchPage.openSubjectOption(0, 0);
    }

    @Test
    @Order(6)
    public void getCourseInformation() {
        String courseInformation = searchPage.getCourseInformation(0, 0, 0);
        final String expectedCourseDescription = "In this course students will investigate and study the topic of child development in art and education. Students will explore a range of perspectives on developmental theories; the creation, and understanding of children’s art and meaning making; and approaches to teaching art to children in a Birth-12 setting. Resources from the areas of art, psychology, sociology and art education will be investigated. Projects will include the development of a case study, relevant readings, research and studio activities, and collaborative research. Students will be expected to complete weekly reading, writing assignments, conduct research and field experience, and to participate in weekly discussions. This course has a field experience component of 20 hours.";
        assertEquals(expectedCourseDescription, courseInformation);
    }

}
