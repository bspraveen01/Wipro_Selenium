package Day24;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Array2d {

    // 🔹 DataProvider supplies multiple sets of data
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][] {
            {"admin", "admin123"},   // valid credentials
            {"user1", "wrongPass"},  // invalid password
            {"", ""}                 // empty username/password
        };
    }

    // 🔹 Test method consumes the DataProvider
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Running test with: " + username + " | " + password);
    }
}
