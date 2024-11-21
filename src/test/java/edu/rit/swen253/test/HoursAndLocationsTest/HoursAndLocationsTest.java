package edu.rit.swen253.page.HoursAndLocationsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import edu.rit.swen253.page.HoursAndLocations.HoursAndLocations;
import edu.rit.swen253.test.AbstractWebTest;




@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HoursAndLocationsTest extends AbstractWebTest {

    private HoursAndLocations hoursAndLocations;

    /**
    * Set up function
    */
    @BeforeEach
    void setUp() {
        hoursAndLocations = new HoursAndLocations();
    
    }

     /*
     * Test to choose Location
     */
    @Test
    @Order(1)
    public void testChooseLocation() {
        hoursAndLocations.hoursAndLocationsPage();
        hoursAndLocations.chooseDiningLocation();
    }
    
        /*
     * Go to Student Affairs list
     */
    @Test
    @Order(2)
    public void testGoToStudentAffairs() {

        hoursAndLocations.goToStudentAffairs();

    }

     /*
     * Test to go to Computer Lab
     */
    @Test
    @Order(3)
    void testGoToComputerLabs() {
        hoursAndLocations.selectComputerLabs(); 

    }

    /*
     * Test to make sure Dining Location hours exist
     */
    @Test
    @Order(4)
    public void testGetLocationHours() {
    
        hoursAndLocations.hoursAndLocationsPage();
        hoursAndLocations.chooseDiningLocation();

        String locationHours = hoursAndLocations.getLocationHours();

        assertFalse(locationHours.isEmpty());
    } 
    


    /*
     * Test to make sure Computer Lab hours exist
     */
    @Test
    @Order(5)
    public void testGetComputerLabHours(){

     
        String labName = "3D World"; 
        String labHours = hoursAndLocations.getComputerLabHours(labName);

        hoursAndLocations.selectComputerLabs();

        assertFalse(labHours.isEmpty(), "Lab hours should not be empty.");
    }


    /*
     * Get Student Affairs Office Hours
     */
    @Test
    @Order(6)
    public void testGetStudentAffairsHours() {
            hoursAndLocations.goToStudentAffairs();
            String studentAffairsOffice = "Case Management"; 
            String studentAffairsOfficeHours = hoursAndLocations.getStudentAffairsHours(studentAffairsOffice);
      
            assertFalse(studentAffairsOfficeHours.isEmpty());
    }

    
}
