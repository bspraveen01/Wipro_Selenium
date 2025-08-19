package Day24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class SearchFunctionalityTest {
	
     WebDriver driver;
     
     
@Test(dataProvider="searchData")
public void searchTest(String searchTerm) throws InterruptedException{
	driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://www.google.com");
	driver.findElement(By.name("q")).sendKeys(searchTerm);
	driver.findElement(By.name("q")).submit();
	Thread.sleep(15000);
	System.out.println("search completed for : "+ searchTerm);
	driver.quit();
}

@DataProvider(name="searchData")
public Object[][] searchData(){
		return new Object[][] {
			{"selenium"},{"testng"},{"automation"}
		};
			
		
}
}
