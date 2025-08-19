package Day26;

import org.testng.annotations.*;                     // TestNG annotations
import org.openqa.selenium.*;                       // Selenium WebDriver + By
import org.openqa.selenium.chrome.ChromeDriver;     // ChromeDriver for running tests
import org.openqa.selenium.support.ui.*;            // WebDriverWait + ExpectedConditions
import java.sql.*;                                  // JDBC (Connection, ResultSet, SQLException, etc.)
import java.time.Duration;                          // For explicit waits

/**
 * JDBC + Selenium Integration Test
 * ---------------------------------
 * This class demonstrates:
 *  1. Connecting to a MySQL database with JDBC
 *  2. Reading active users from the "users" table
 *  3. Using each username/password from DB to log in to a sample WebOrders app
 *  4. Running under TestNG lifecycle with @BeforeClass, @Test, @AfterClass
 */
public class JDBC {

    // Utility class for handling JDBC operations (connection, query execution, closing)
    JdbcUtil jdbcUtil = new JdbcUtil();

    // JDBC ResultSet to hold query results
    ResultSet resultSet;

    // Selenium WebDriver
    WebDriver driver;

    /**
     * @BeforeClass
     * Runs once before all test methods.
     * Responsible for:
     *   - Connecting to the MySQL database
     *   - Initializing WebDriver (Chrome)
     */
    @BeforeClass
    public void setup() throws ClassNotFoundException, SQLException {
        // MySQL database connection details
        String dbUrl = "jdbc:mysql://localhost:3306/DB"; // "DB" = database name
        String user = "root";                            // MySQL username
        String pass = "Srirampraveen01@";                // MySQL password

        // Connect to DB using custom JdbcUtil
        jdbcUtil.connect(dbUrl, user, pass);

        // Launch Chrome browser
        driver = new ChromeDriver();
    }

    /**
     * @Test
     * Main test method:
     *   - Reads active users from DB
     *   - Iterates over each record
     *   - Uses Selenium to log in to the WebOrders demo app
     */
    @Test
    public void readDataAndTest() throws SQLException {
        // SQL query to fetch all active users from "users" table
        String query = "SELECT * FROM users WHERE status='active'";

        // Execute query → store result set
        resultSet = jdbcUtil.executeQuery(query);

        // Explicit wait (10 seconds) for elements in browser
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Loop through each row returned from DB
        while (resultSet.next()) {
            // Extract username and password columns
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            // Open the WebOrders login page
            driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

            // Wait until username input field is visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_username")));

            // Enter username
            WebElement usernameField = driver.findElement(By.id("ctl00_MainContent_username"));
            usernameField.clear();             // clear any old value
            usernameField.sendKeys(username);  // type username from DB

            // Enter password
            WebElement passwordField = driver.findElement(By.id("ctl00_MainContent_password"));
            passwordField.clear();
            passwordField.sendKeys(password);  // type password from DB

            // Click login button
            driver.findElement(By.id("ctl00_MainContent_login_button")).click();

            // (Optional) → You can add validation here:
            // e.g., check if login successful by verifying logout link, or error message
        }
    }

    /**
     * @AfterClass
     * Runs once after all test methods.
     * Responsible for:
     *   - Closing the WebDriver
     *   - Closing DB connection
     */
    @AfterClass
    public void tearDown() throws SQLException {
        // Close browser if it was opened
        if (driver != null) driver.quit();

        // Close JDBC connection
        jdbcUtil.close();
    }
}

