package Day27;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
   WebDriver driver;
   
   // locators using PageFactory
   
   @FindBy(name="username") //tells Selenium how to locate elements.
   WebElement username;
   
   @FindBy(name="password")
   WebElement password;
   
   @FindBy(xpath="//input[@value='Log In']")
   WebElement loginButton;
   
   //constructor to initializes webElements
   public LoginPage(WebDriver driver) {
	   this.driver =driver;
	   PageFactory.initElements( driver, this); //automatically initializes those elements when the class is created.

   }
   
   public void enterUsername(String user) {
	   username.clear();
	   username.sendKeys(user);
   }
   
   public void enterPassword(String pass) {
	   password.clear();
	   password.sendKeys(pass);
   }
   
   public void clickLogin() {
	   loginButton.click();
   }
}
