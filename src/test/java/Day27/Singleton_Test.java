package Day27;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Singleton_Test {
	@BeforeClass
	public void setup() {
		System.out.println("this is before singleton");
		Singeltonpage.getDriver().get("https://demoqa.com/automation-practice-form");
  
	}
	@Test
	public void testDown() {
		Singeltonpage.quitDriver();
	}

}
