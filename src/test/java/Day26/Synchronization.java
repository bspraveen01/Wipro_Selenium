package Day26;

//Log4j2 imports for logging messages instead of just printing to console
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*; // Includes WebDriver, WebElement, By, etc.
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*; // Includes WebDriverWait and ExpectedConditions

import org.testng.Assert; // For assertions (pass/fail validation)
import org.testng.annotations.*; // For annotations like @BeforeClass, @Test, @AfterClass

import java.time.Duration;

/**
* Class: SyncExceptionTest
* Purpose:
*  - Demonstrates handling of synchronization-related exceptions in Selenium.
*  - Uses explicit wait + try-catch blocks to handle NoSuchElementException, TimeoutException, etc.
*/
public class Synchronization {

 // Logger instance for this class (used to log INFO/ERROR messages in test execution)
 private static final Logger logger = LogManager.getLogger(Synchronization.class);

 // Declare WebDriver and WebDriverWait globally so they can be used across methods
 WebDriver driver;
 WebDriverWait wait;

 @BeforeClass
 public void Logins() {
     // Logging message using Log4j
     logger.info("Starting the login test setup...");

     // Print message on console
     System.out.println("Launching the browser");

     // Initialize Chrome browser driver
     driver = new ChromeDriver();

     // Maximize the browser window
     driver.manage().window().maximize();

     // Apply implicit wait: Selenium will wait up to 10 seconds for elements to appear
     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

     // Initialize explicit wait (WebDriverWait): allows waiting for specific conditions
     wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     // Launch the application under test (AUT)
     driver.get("https://parabank.parasoft.com/");
 }

 @Test
 public void testLogin() {
     try {
         // Wait until the username field is visible
         // NOTE: The locator "sername" is incorrect (intentional to show exception handling).
         WebElement username = wait.until(
                 ExpectedConditions.visibilityOfElementLocated(By.name("sername"))
         );
         // Enter username value
         username.sendKeys("Siya12");

         // Locate password field and enter password
         WebElement password = driver.findElement(By.name("password"));
         password.sendKeys("Siya12");

         // Locate Login button and click it
         WebElement loginBtn = driver.findElement(By.cssSelector("input[value ='Log In']"));
         loginBtn.click();

         // Wait until the "Log Out" link is visible (this confirms login success)
         WebElement logoutLink = wait.until(
                 ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out"))
         );

         // Assertion: if logout link is displayed → login is successful
         Assert.assertTrue(logoutLink.isDisplayed(), "Login failed!");

         // Print success message
         System.out.println("Login successful, Logout link found!");

     } catch (NoSuchElementException e) {
         // Handles situation where element is not found in the DOM
         System.out.println("Element not found: " + e.getMessage());
         Assert.fail("Test failed due to missing element.");

     } catch (TimeoutException e) {
         // Handles situation where explicit wait timed out
         System.out.println("Timeout while waiting: " + e.getMessage());
         Assert.fail("Test failed due to timeout.");

     } catch (Exception e) {
         // Handles any other unexpected exception
         System.out.println("Unexpected error: " + e.getMessage());
         Assert.fail("Test failed due to unexpected exception.");
     }
 }

 @AfterClass
 public void tearDown() {
     // Quit driver if not null → closes all browser windows
     if (driver != null) {
         driver.quit();
     }
 }
}
