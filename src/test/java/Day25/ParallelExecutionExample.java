// Package declaration: Organizes this class inside the "Day25" package
package Day25;

// Import Java library for handling time durations (used for waits)
import java.time.Duration;

// Import TestNG's DataProvider annotation (supplies test data)
import org.testng.annotations.DataProvider;
// Import TestNG's Test annotation (marks test methods)
import org.testng.annotations.Test;

// Import Selenium By class (used to locate elements)
import org.openqa.selenium.By;
// Import Selenium WebDriver interface (browser automation driver)
import org.openqa.selenium.WebDriver;
// Import Selenium WebElement class (represents elements on the web page)
import org.openqa.selenium.WebElement;
// Import Selenium ChromeDriver class (controls Chrome browser)
import org.openqa.selenium.chrome.ChromeDriver;
// Import Selenium WebDriverWait (used for explicit waits)
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Import WebDriverManager (manages browser driver binaries automatically)
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class: ParallelExecutionExample
 * Purpose: Demonstrates parallel execution of tests (Sign Up & Login)
 *          on the NopCommerce demo site using TestNG + Selenium WebDriver.
 */
public class ParallelExecutionExample {

    /**
     * DataProvider in TestNG:
     * - Supplies test data to test methods.
     * - "userData" is the name of this data provider.
     * - Returns a 2D Object array containing email and password.
     */
    @DataProvider(name = "userData", parallel = true)  // DataProvider named "userData", parallel execution enabled
    public Object[][] getUserData() {  // Method that returns test data for email & password
        return new Object[][] {  // 2D array of objects (rows = sets of test data)
            {"FirstUser1@gmail.com", "NewUser@1234"},  // Row 1: Email + Password
            {"FirstUser2@gmail.com", "NewUser@1234"},  // Row 2: Email + Password
            {"FirstUser3@gmail.com", "NewUser@1234"}   // Row 3: Email + Password
        };
    }

    /**
     * Test 1: Sign Up (Registration) on NopCommerce
     * - Runs in parallel because of "invocationCount" & "threadPoolSize".
     *
     * @param email    -> TestNG will inject email from DataProvider
     * @param password -> TestNG will inject password from DataProvider
     */
    @Test(dataProvider = "userData", invocationCount = 1, threadPoolSize = 2, priority = 1)  
    // Marks this as a TestNG test, uses "userData" provider, 1 invocation, runs 2 threads, executes before login (priority 1)
    public void signUpToNopCommerce(String email, String password) {  
        // Method for registering a new user, TestNG injects email + password automatically

        WebDriverManager.chromedriver().setup();  // Sets up ChromeDriver automatically (no manual driver needed)
        WebDriver driver = new ChromeDriver();   // Launches a new Chrome browser instance

        driver.manage().window().maximize();  // Maximizes browser window
        driver.get("https://demo.nopcommerce.com/register");  // Opens NopCommerce registration page

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Creates explicit wait (max 10s)

        // Print message in console with thread ID and email being used
        System.out.println("Thread " + Thread.currentThread().getId() + " - SignUp with " + email);

        // Click on "Male" gender radio button after waiting until clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.id("gender-male"))).click();
        // Enter first name after waiting until visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName"))).sendKeys("userFirstName");
        // Enter last name
        driver.findElement(By.id("LastName")).sendKeys("userLastName");
        // Enter email from DataProvider
        driver.findElement(By.id("Email")).sendKeys(email);
        // Enter company name
        driver.findElement(By.id("Company")).sendKeys("userCompany");

        // Find "Newsletter" checkbox
        WebElement newsletterCheckbox = driver.findElement(By.id("Newsletter"));
        // If newsletter is not already selected, click it
        if (!newsletterCheckbox.isSelected()) {
            newsletterCheckbox.click();
        }

        // Enter password from DataProvider
        driver.findElement(By.id("Password")).sendKeys(password);
        // Confirm password
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        // Click the "Register" button (after waiting until clickable)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("register-button"))).click();

        // Print success message in console
        System.out.println("Thread " + Thread.currentThread().getId() + " - SignUp attempted for " + email);

        driver.quit();  // Close browser and end session
    }

    /**
     * Test 2: Login to NopCommerce
     * - Runs in parallel using same data provider.
     *
     * @param email    -> TestNG will inject email from DataProvider
     * @param password -> TestNG will inject password from DataProvider
     */
    @Test(dataProvider = "userData", invocationCount = 1, threadPoolSize = 2, priority = 2)  
    // Marks this as a TestNG test, uses "userData" provider, runs in parallel, executes after sign-up (priority 2)
    public void loginToNopCommerce(String email, String password) {  
        // Method for logging into NopCommerce

        WebDriverManager.chromedriver().setup();  // Setup ChromeDriver
        WebDriver driver = new ChromeDriver();   // Launch Chrome browser

        driver.manage().window().maximize();  // Maximize browser window
        driver.get("https://demo.nopcommerce.com/login");  // Navigate to login page

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Explicit wait (max 10s)

        // Print message with thread ID and email being used
        System.out.println("Thread " + Thread.currentThread().getId() + " - Login with " + email);

        // Wait until email field is visible, then enter email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Email"))).sendKeys(email);
        // Enter password
        driver.findElement(By.name("Password")).sendKeys(password);
        // Click login button
        driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click();

        // Print login attempt message
        System.out.println("Thread " + Thread.currentThread().getId() + " - Login attempted for " + email);

        driver.quit();  // Close browser
    }
}

























/*
 * package → Defines namespace (folder for classes).

import → Brings external classes (like Selenium, TestNG).

public class → Declares the class (accessible everywhere).

@DataProvider → Supplies test data to test methods.

Object[][] → 2D array holding multiple sets of test data.

@Test → Marks a method as a test case (TestNG).

dataProvider → Connects test to data provider method.

invocationCount → Number of times test method will run.

threadPoolSize → Number of threads used for parallel execution.

priority → Defines execution order of tests.

WebDriver → Interface controlling the browser.

ChromeDriver() → Class to launch Chrome browser.

driver.manage().window().maximize() → Maximizes window.

driver.get(URL) → Opens the given URL.

WebDriverWait → Used for explicit waits (waiting for condition).

ExpectedConditions.elementToBeClickable() → Wait until element is clickable.

By.id() / By.name() / By.xpath() → Locator strategies for elements.

sendKeys() → Types input into a field.

click() → Clicks a button, link, or checkbox.

isSelected() → Checks if a checkbox/radio button is selected.

Thread.currentThread().getId() → Returns current thread ID (to track parallel execution).

driver.quit() → Closes the browser completely.*/
 