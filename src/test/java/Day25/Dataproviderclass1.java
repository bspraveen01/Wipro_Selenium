package Day25;



import org.testng.annotations.DataProvider;

/**
 * Separate DataProvider class to supply login test data.
 * 
 * - Each row = one test case
 * - Columns = username, password, expectedOutcome
 * - expectedOutcome is a simple flag:
 *      "success" -> login should succeed
 *      "locked"  -> user is locked out
 *      "invalid" -> invalid credentials
 */
public class Dataproviderclass1 {

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][] {
            {"standard_user", "secret_sauce", "success"},       // valid login
            {"locked_out_user", "secret_sauce", "locked"},      // locked account
            {"performance_glitch_user", "secret_sauce", "success"}, // valid but slower
            {"invalid_user", "wrong_pass", "invalid"}           // invalid login
        };
    }
}

