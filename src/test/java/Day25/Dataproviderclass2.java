package Day25;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Main Test Class for SauceDemo login functionality
 * Uses DataProvider for running multiple test scenarios
 */
public class Dataproviderclass2 {

    WebDriver driver;

    /**
     * @BeforeMethod
     * Runs before every test iteration (i.e. each row of data provider).
     * Ensures a fresh browser instance and clean state.
     */
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();          // Start Chrome
        driver.manage().window().maximize();  // Maximize window
        driver.get("https://www.saucedemo.com/"); // Navigate to login page
    }

    /**
     * @AfterMethod
     * Runs after each test iteration.
     * Cleans up browser instance.
     */
    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);   // (Optional) small delay for demo purposes
        driver.quit();        // Close browser
    }

    /**
     * Login Test with DataProvider
     *
     * @param username  supplied by DataProvider
     * @param password  supplied by DataProvider
     * @param expected  expected outcome flag ("success", "locked", "invalid")
     */
    @Test(dataProvider = "loginData", dataProviderClass = Dataproviderclass1.class)
    public void loginTest(String username, String password, String expected) {

        // Fill credentials
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        // Click login button
        driver.findElement(By.id("login-button")).click();

        // Validation based on expected outcome
        if (expected.equals("success")) {
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(
                currentUrl.contains("inventory"),
                "Login failed for valid user: " + username
            );
            System.out.println("✅ Login successful for: " + username);

        } else if (expected.equals("locked")) {
            String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
            Assert.assertTrue(
                errorMsg.toLowerCase().contains("locked out"),
                "Expected 'locked out' but got: " + errorMsg
            );
            System.out.println("✅ User locked out as expected: " + username);

        } else if (expected.equals("invalid")) {
            String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
            Assert.assertTrue(
                errorMsg.toLowerCase().contains("do not match"),
                "Expected invalid login error but got: " + errorMsg
            );
            System.out.println("✅ Invalid login as expected for: " + username);

        } else {
            Assert.fail("❌ Unknown expected flag: " + expected);
        }
    }
}
