package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String LOGIN_URL = "http://localhost:8080/login.html";
	private static final String REGISTER_URL = "http://localhost:8080/registration.html";

	private final WebDriver driver = new ChromeDriver();

	@Given("I am on the index page")
	public void i_am_on_the_index_page() {
		driver.get(ROOT_URL);
	}

	@When("I click the link {string}")
	public void i_click_the_link(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}

	@Then("I should see header {string}")
	public void i_should_see_header(String header) {
		assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(header));
	}
	
	@Then("I should see text {string}")
	public void i_should_see_text(String text) {
		assertTrue(driver.getPageSource().contains(text));
	}
	
	// registration.feature
	@Given("I am on the registration page")
	public void i_am_on_the_registration_page() {
		driver.get(REGISTER_URL);
	}

	@When("I type {string} into the username text box")
	public void i_type_into_the_username_text_box(String string) {
	    WebElement textbox = driver.findElement(By.xpath("//*[@id=\"username\"]"));
	    textbox.sendKeys(string);
	}

	@When("I type {string} into the password text box")
	public void i_type_into_the_password_text_box(String string) {
		WebElement textbox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
	    textbox.sendKeys(string);
	}

	@When("I type {string} into the confirm password text box")
	public void i_type_into_the_confirm_password_text_box(String string) {
		WebElement textbox = driver.findElement(By.xpath("//*[@id=\"passwordConfirmation\"]"));
	    textbox.sendKeys(string);
	}
	
	@When("I click on the create account button")
	public void i_click_on_the_create_account_button() {
		WebElement button = driver.findElement(By.cssSelector("body > div > div.row.py-5.mt-4.align-items-center > div.col-md-7.col-lg-6.ml-auto > form > div > div.form-group.col-lg-12.mx-auto.mb-0 > button"));
	    button.click();
	}
	
	@When("I click on the already registered login button")
	public void i_click_on_the_already_registered_login_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/form/div/div[8]/p/a"));
	    button.click();
	}

	@Then("I should be on the login page")
	public void i_should_be_on_the_login_page() {
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(LOGIN_URL));
	}

	@Then("I should be on the registration page")
	public void i_should_be_on_the_registration_page() {
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(REGISTER_URL));
	}
	
	@Then("I should be on the registration valid page")
	public void i_should_be_on_the_registration_valid_page() {
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(REGISTER_URL + "#"));
	}
	

	@After()
	public void after() {
		driver.quit();
	}
}
