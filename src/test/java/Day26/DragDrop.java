package Day26;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
public class DragDrop {
    WebDriver driver; // Declare WebDriver instance globally so all methods can access it

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Automatically setup ChromeDriver
        driver = new ChromeDriver(); // Launch a new Chrome browser
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait: wait up to 10 seconds for elements to appear
        driver.get("https://demoqa.com/droppable"); // Navigate to the drag & drop demo page
    }

    @Test
    public void testDragAndDrop() {
        WebElement source = driver.findElement(By.id("draggable"));  // Locate the element that needs to be dragged
        WebElement target = driver.findElement(By.id("droppable"));  // Locate the element where the source will be dropped

        Actions actions = new Actions(driver); // Create Actions class object to perform advanced user interactions
        // Perform drag and drop manually using clickAndHold, moveToElement, and release
        actions.clickAndHold(source) // Click and hold the source element (simulate pressing mouse button)
               .moveToElement(target) // Move the mouse cursor to the center of the target element
               .release() // Release the mouse button to drop the element
               .build() // Build the action chain
               .perform(); // Execute the built action chain

        // Verify if the drop was successful by checking the target's text
        String textAfterDrop = target.getText(); // Get the text of the target element after drop
        Assert.assertTrue(textAfterDrop.contains("Dropped!"), "Drag and drop failed!"); // Assert that the drop worked
    }

    @AfterMethod
    public void tearDown() {
        driver.quit(); // Close the browser and end the WebDriver session
    }
}
