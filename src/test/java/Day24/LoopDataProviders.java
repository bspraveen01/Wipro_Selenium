package Day24;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoopDataProviders {

    //  DataProvider method
    @DataProvider(name = "loginData")
    public Object[][] getData() {
        int size = 3;   // Number of test iterations you want
        Object[][] data = new Object[size][2]; // 2 columns → username & password

        // 🔹 Fill data using for loop
        for (int i = 0; i < size; i++) {
            data[i][0] = "admin";     // username (same for all iterations here)
            data[i][1] = "admin123";  // password (same for all iterations here)
        }

        return data;
    }

    // Test method consumes DataProvider
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Running test with: " + username + " | " + password);
    }
}

