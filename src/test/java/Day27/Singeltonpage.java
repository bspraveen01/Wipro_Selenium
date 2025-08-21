package Day27;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Singeltonpage {
private static WebDriver driver;
	
	private Singeltonpage() { //prevents creating an object of this class with new.Singleton pattern (only one instance of WebDriver can exist).
		
	}
	public static WebDriver getDriver() { //can be accessed without creating an object.
		if(driver ==null) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}
	public static void quitDriver() {
		if(driver!= null)
		{
			driver.quit();
			driver=null;
			}
		}

}
