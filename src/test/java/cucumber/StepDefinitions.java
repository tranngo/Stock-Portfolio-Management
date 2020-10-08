package cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String LOGIN_URL = "http://localhost:8080/login.html";
	private static final String REGISTER_URL = "http://localhost:8080/registration.html";
	private static final String REGISTER_SERVLET_URL = "http://localhost:8080/registrationServlet";
	private static final String HOME_URL = "http://localhost:8080/home.html";

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
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(LOGIN_URL));
	}

	@Then("I should be on the registration page")
	public void i_should_be_on_the_registration_page() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(REGISTER_URL));
	}
	
	@Then("I should be on the registration servlet page")
	public void i_should_be_on_the_registration_servlet_page() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(REGISTER_SERVLET_URL));
	}
	
	// requirement3.feature
	@Then("I should see a line chart that displays the value of the user's portfolio over time")
	public void i_should_see_a_line_chart_that_displays_the_value_of_the_user_s_portfolio_over_time() {
	    assertTrue(driver.findElements(By.id("main-chart")).size() != 0);
	}
	
	@Then("I should see buttons to select the from date and to date")
	public void i_should_see_buttons_to_select_the_from_date_and_to_date() {
		assertTrue(driver.findElements(By.id("fromDate")).size() != 0);
		assertTrue(driver.findElements(By.id("toDate")).size() != 0);
	}
	
	// requirement8.feature
	@Given("I am on the home page")
	public void i_am_on_the_home_page() {
		driver.get(HOME_URL);
	}

	@Then("I should see a gray banner across the top with {string} text")
	public void i_should_see_a_gray_banner_across_the_top_with_text(String string) {
		WebElement banner = driver.findElement(By.xpath("/html/body/nav/a"));
		assertTrue(banner.getAttribute("innerHTML").contains(string));
	}
	
	@Then("I should see a gray banner across the top with the color #{int}")
	public void i_should_see_a_gray_banner_across_the_top_with_the_color(Integer int1) {
		WebElement banner = driver.findElement(By.xpath("/html/body/nav"));
		assertEquals(banner.getCssValue("background-color"), "rgba(120, 120, 120, 1)");
	}

	@After()
	public void after() {
		driver.quit();
	}
}
