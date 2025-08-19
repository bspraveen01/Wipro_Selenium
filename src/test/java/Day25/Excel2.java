package Day25;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Excel2 {
    WebDriver driver;

    @Test(dataProvider = "Getdata")
    public void register(String gender, String firstName, String lastName, String email, String password)
            throws InterruptedException {

        WebDriverManager.chromedriver().setup();  
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/register");

        // Gender selection
        if (gender.equalsIgnoreCase("Male")) {
            driver.findElement(By.id("gender-male")).click();
        } else {
            driver.findElement(By.id("gender-female")).click();
        }

        // Fill details
        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        // Submit form
        driver.findElement(By.id("register-button")).click();
        Thread.sleep(3000);

        try {
            // Case 1: Registration success
            WebElement successMsg = driver.findElement(By.className("result"));
            if (successMsg.isDisplayed() && successMsg.getText().contains("Your registration completed")) {
                System.out.println("Registration successful for: " + email);
            }
        } catch (Exception e) {
            // Case 2: Registration failed (email already exists OR other errors)
            WebElement errorMsg = driver.findElement(By.cssSelector(".validation-summary-errors li"));
            String msg = errorMsg.getText(); // to retrieve the visible text of element
            System.out.println("Registration failed for: " + email + " | Message: " + msg);

            
            Assert.fail("Registration failed for email: " + email);
        }


        driver.quit();
    }

    @DataProvider(name = "Getdata")
    public String[][] getdata() throws IOException {
        File s = new File("C:\\Users\\SRIRAMPRAVEEN\\OneDrive\\Documents\\RegisterDataProviders.xlsx");
        System.out.println("File exists: " + s.exists());

        FileInputStream fis = new FileInputStream(s);
        
       // Excel file => workbook => sheets =>cell =>data
 
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int noOfRows = sheet.getPhysicalNumberOfRows();
        int noOfCols = sheet.getRow(0).getLastCellNum();  // pass index header it gives the number of columns in first row.

        String[][] data = new String[noOfRows - 1][noOfCols];

        for (int i = 0; i < noOfRows - 1; i++) {
            for (int j = 0; j < noOfCols; j++) {
                DataFormatter df = new DataFormatter();
                data[i][j] = df.formatCellValue(sheet.getRow(i + 1).getCell(j));
            }
        }

        workbook.close();
        fis.close();

        return data;
    }
}

