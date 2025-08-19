package Day23;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class DependOnGroups {
    WebDriver driver;
    WebDriverWait wait;
    private void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("=== Start of Test Suite ===");
    }

    @BeforeMethod
	@BeforeClass
    @Parameters({"browserName", "url"})
    public void setUp(String browserName, String url) {
        System.out.println("Launching WebOrders On " + browserName);

        switch (browserName.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Invalid browser");
                return;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        pause(2);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_username"))).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
        driver.findElement(By.id("ctl00_MainContent_login_button")).click();

        AssertJUnit.assertTrue(driver.getTitle().contains("Web Orders"));
    }

    // Smoke group
    @Test(priority = 1, groups = {"smoke"})
    public void verifyHomePage() {
        String title = driver.getTitle();
        AssertJUnit.assertTrue(title.contains("Web Orders"));
    }

    @Test(priority = 2, groups = {"smoke"})
    public void viewAllOrders() {
        WebElement ordersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View all orders")));
        ordersLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_orderGrid_ctl02_OrderSelector"))).click();
        AssertJUnit.assertTrue(driver.getPageSource().contains("List of All Orders"));
    }

    // Regression group
    @Test(priority = 3, groups = {"regression"})
    public void placeOrder() {
        WebElement orderLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Order")));
        orderLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_MainContent_fmwOrder_ddlProduct"))).sendKeys("FamilyAlbum");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).clear();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys("2");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys("sushmitha");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys("123 Main St");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys("London");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys("NY");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys("12345");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys("4111111111111111");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys("12/25");
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
        AssertJUnit.assertTrue(driver.getPageSource().contains("New order has been successfully added."));
    }

    @Test(priority = 4, groups = {"regression"})
    public void verifyOrderInList() {
        driver.findElement(By.linkText("View all orders")).click();
        AssertJUnit.assertTrue(driver.getPageSource().contains("sushmitha"));
    }

    // DependsOnGroups
    @Test(dependsOnGroups = {"smoke", "regression"})
    public void finalReportTest() {
        System.out.println("Final Report: All smoke & regression tests completed successfully.");
    }

    @AfterMethod
	@AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("=== Browser Closed ===");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("=== Test Suite Completed ===");
    }
}
