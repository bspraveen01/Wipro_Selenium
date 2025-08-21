package Day27;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FormTest {
    
    WebDriver driver;
    PraticePagePOM formPage;
    
    @BeforeClass
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Navigate to DemoQA Practice Form
        driver.get("https://demoqa.com/automation-practice-form");
        
        // Initialize the POM class
        formPage = new PraticePagePOM(driver);  // initializes the POM, passing the driver so it can interact with the page.

    }
    
    @Test
    public void fillForm() {
        // Enter first and last name
        formPage.enterFirstName("Sriram");
        formPage.enterLastName("Praveen");
        
        // Enter email
        formPage.enterEmail("Srirampraveen01@gmail.com");
        
        // Select Gender
        formPage.selectGender("Male");
        
        // Enter mobile number
        formPage.enterMobileNumber("78815829770");
        
        // Enter Date of Birth
        formPage.enterDOB("10-june-2003");
        
        // Enter subject
        formPage.enterSubject("English");
        
        // Select hobbies (can select multiple if needed)
        formPage.selectHobbies("Music", "Reading"); // Example: selecting two hobbies
        
        // Upload picture
        formPage.uploadPicture("C:\\Users\\SRIRAMPRAVEEN\\Downloads\\profile photo.jpg");
        
        // Enter address
        formPage.enterAddress("126, Chadalwada");
        
        // Select state and city
        formPage.selectState("NCR");
        formPage.selectCity("Delhi");
        
        // Submit the form
        formPage.clickSubmit();
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }
}
