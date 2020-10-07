package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/hello.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement1.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement2.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement3.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement4.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement5.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement6_login.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement6_registration.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement7.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement8.feature"})
public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}

}
