package Day27;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;
    
    //After login, customer name appears on top right
    @FindBy(css="p.smallText")
    WebElement welcomemessage;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getWelcomeText() {
        return welcomemessage.getText(); //returns text like "Welcome John Smith".
    }

}
