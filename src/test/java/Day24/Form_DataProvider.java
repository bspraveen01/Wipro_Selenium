package Day24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class Form_DataProvider {
  WebDriver driver;
   @Test(dataProvider="Form")
   public void register(String gender, String firstName, String lastName, String email, String password) throws InterruptedException {
       driver = new ChromeDriver();
       driver.manage().window().maximize();
       driver.get("https://demowebshop.tricentis.com/register");
       if(gender.equalsIgnoreCase("Male")) {
       driver.findElement(By.id("gender-male")).click();
       }  
       else {
           driver.findElement(By.id("gender-female")).click();
       }
       driver.findElement(By.id("FirstName")).sendKeys(firstName);
       driver.findElement(By.id("LastName")).sendKeys(lastName);
       driver.findElement(By.id("Email")).sendKeys(email);
       driver.findElement(By.id("Password")).sendKeys(password);
       driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
   
       // Submit form
       driver.findElement(By.id("register-button")).click();
       Thread.sleep(5000); // wait for registration confirmation
       System.out.println("Form submitted for: " + firstName + " " + lastName + " (" + gender + ")");
       driver.quit();

   }
   
   @DataProvider(name="Form")
   public Object[][] Form() {
       return new Object[][] { 
           {"Male", "Sriram", "Praveen", "srirampraveen@gmail.com", "praveen8976"},
           {"Female", "Sravani", "S", "Sravani@gmail.com", "sravani123"},
           {"Male", "Ganesh", "Balachandran", "ganeshbalachandran@gmail.com", "ganesh123"}
       };
   }
}
