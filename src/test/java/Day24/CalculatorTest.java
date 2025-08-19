package Day24;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class CalculatorTest {
@Test(dataProvider="additionData")
public void testAddition(int num1, int num2,int expectedsum) {
	 int sum= num1 + num2;
    System.out.println("Adding " + num1 + " + " + num2 + " = " + sum);
	 Assert.assertEquals(sum, expectedsum,"sum mismatch");
}
@DataProvider(name="additionData")
public Object[][] additionData(){
	 return new Object[][] {
		 {1,4,5},{245,987,1232},{6735,3643,10378}
	 };
}
}

