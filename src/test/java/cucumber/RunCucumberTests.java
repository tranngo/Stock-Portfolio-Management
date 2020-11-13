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

//Please run one at a time, just in case :)
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement2.feature", "src/test/resources/cucumber/requirement3.feature", 
//		"src/test/resources/cucumber/requirement4.feature", "src/test/resources/cucumber/requirement5.feature", "src/test/resources/cucumber/requirement6.feature", 
//		"src/test/resources/cucumber/requirement7_login.feature", "src/test/resources/cucumber/requirement7_register.feature", "src/test/resources/cucumber/requirement8.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement2.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement3.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement4.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement5.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement6.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement7_login.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement7_register.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/requirement8.feature"})

public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}

}
