package Day27;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PraticePagePOM {
    
    WebDriver driver;
    WebDriverWait wait;
    
    // Constructor
    public PraticePagePOM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // -------------------- Locators --------------------
    By firstNameInput = By.id("firstName");
    By lastNameInput = By.id("lastName");
    By emailInput = By.id("userEmail");
    By mobileNumber = By.id("userNumber");
    By dateOfBirth = By.id("dateOfBirthInput");
    By subjectsInput = By.cssSelector(".subjects-auto-complete__value-container input"); // corrected selector
    By hobbiesCheckbox1 = By.xpath("//label[@for='hobbies-checkbox-1']");
    By hobbiesCheckbox2 = By.xpath("//label[@for='hobbies-checkbox-2']");
    By hobbiesCheckbox3 = By.xpath("//label[@for='hobbies-checkbox-3']");
    By selectPic = By.id("uploadPicture");
    By addressInput = By.id("currentAddress");
    By stateDropDown = By.id("react-select-3-input");
    By cityDropDown = By.id("react-select-4-input");
    By submitButton = By.id("submit");
    
    // -------------------- Methods --------------------
    
    // Enter First Name
    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
    }
    
    // Enter Last Name
    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
    }
    
    // Enter Email
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }
    
    // Select Gender dynamically (Male/Female/Other)
    public void selectGender(String gender) {
        By genderLocator = By.xpath("//label[text()='" + gender + "']");
        WebElement genderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(genderLocator));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", genderElement);
        // JS click to avoid intercepted click by ads
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderElement);
    }
    
    // Enter Mobile Number
    public void enterMobileNumber(String mobile) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumber)).sendKeys(mobile);
    }
    
    // Enter Date of Birth
    public void enterDOB(String dob) {
        WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfBirth));
        dobField.click();
        dobField.clear();
        dobField.sendKeys(dob);
        dobField.sendKeys(Keys.ENTER); // confirm date
    }
    
    // Enter Subject
    public void enterSubject(String subject) {
        WebElement subjectField = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectsInput));
        subjectField.sendKeys(subject);
        subjectField.sendKeys(Keys.ENTER);
    }
    
    // Select a single hobby safely
    public void selectHobby(String hobby) {
        WebElement hobbyElement = null;
        if (hobby.equalsIgnoreCase("Sports")) {
            hobbyElement = wait.until(ExpectedConditions.elementToBeClickable(hobbiesCheckbox1));
        } else if (hobby.equalsIgnoreCase("Music")) {
            hobbyElement = wait.until(ExpectedConditions.elementToBeClickable(hobbiesCheckbox2));
        } else if (hobby.equalsIgnoreCase("Reading")) {
            hobbyElement = wait.until(ExpectedConditions.elementToBeClickable(hobbiesCheckbox3));
        }
        if (hobbyElement != null) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hobbyElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hobbyElement);
        }
    }
    
    // Select multiple hobbies
    public void selectHobbies(String... hobbies) {
        for (String h : hobbies) {
            selectHobby(h);
        }
    }
    
    // Upload Picture
    public void uploadPicture(String filePath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectPic)).sendKeys(filePath);
    }
    
    // Enter Address
    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput)).sendKeys(address);
    }
    
    // Select State from dropdown
    public void selectState(String state) {
        WebElement stateField = wait.until(ExpectedConditions.visibilityOfElementLocated(stateDropDown));
        stateField.sendKeys(state);
        stateField.sendKeys(Keys.ENTER);
    }
    
    // Select City from dropdown
    public void selectCity(String city) {
        WebElement cityField = wait.until(ExpectedConditions.visibilityOfElementLocated(cityDropDown));
        cityField.sendKeys(city);
        cityField.sendKeys(Keys.ENTER);
    }
    
    // Click Submit
    public void clickSubmit() {
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
    }
}
