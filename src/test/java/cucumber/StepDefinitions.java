package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

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

	//	login.feature
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
	    driver.get(LOGIN_URL);
	}

	@When("I click on the sign up button")
	public void i_click_on_the_sign_up_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div/div[6]/p/a"));
	    button.click();
	}

	@Then("I should be on the register page")
	public void i_should_be_on_the_register_page() {
		String URL = driver.getCurrentUrl();
		assertTrue(URL.equalsIgnoreCase(REGISTER_URL));
	}
	
	@When("I type {string} into the login password text box")
	public void i_type_into_the_login_password_text_box(String string) {
	    WebElement textbox = driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div/div[3]/input"));
	    textbox.sendKeys(string);
	}

	@When("I click on the login button")
	public void i_click_on_the_login_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div/div[5]/button"));
	    button.click();
	}

	@When("I type {string} into the login username text box")
	public void i_type_into_the_login_username_text_box(String string) {
		WebElement textbox = driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div/div[1]/input"));
	    textbox.sendKeys(string);
	}
	
	@Then("I should see please enter a username error message")
	public void i_should_see_please_enter_a_username_error_message() {
	    assertTrue(driver.findElements(By.xpath("/html/body/div/div/div[2]/form/div/div[2]/div")).size() != 0);
	}
	
	@Then("I should see please enter your password error message")
	public void i_should_see_please_enter_your_password_error_message() {
		assertTrue(driver.findElements(By.xpath("/html/body/div/div/div[2]/form/div/div[4]/div")).size() != 0);
	}

	@Then("I should see please enter a username and please enter your password error messages")
	public void i_should_see_please_enter_a_username_and_please_enter_your_password_error_messages() {
		// Please enter a username error message
		assertTrue(driver.findElements(By.xpath("/html/body/div/div/div[2]/form/div/div[2]/div")).size() != 0);
		
		// Please enter your password error message
		assertTrue(driver.findElements(By.xpath("/html/body/div/div/div[2]/form/div/div[2]/div")).size() != 0);
	}
	
	// requirement2.feature
	@When("I log in")
	public void i_log_in() {
		WebElement login = driver.findElement(By.xpath("//*[@id=\"username\"]"));
	    login.sendKeys("wilson133");
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
	    password.sendKeys("racket");
	    WebElement button = driver.findElement(By.xpath("/html/body/div/div/div[2]/form/div/div[5]/button"));
	    button.click();
	}

	@Then("I should see a button to add a stock to my portfolio on the home page")
	public void i_should_see_a_button_to_add_a_stock_to_my_portfolio_on_the_home_page() {
		assertTrue(driver.findElements(By.xpath("/html/body/div[2]/div/div[1]/div[2]/button[1]")).size() != 0);
	}
	
	@Then("I should see a button to upload a stock to my portfolio on the home page")
	public void i_should_see_a_button_to_upload_a_stock_to_my_portfolio_on_the_home_page() {
		assertTrue(driver.findElements(By.xpath("/html/body/div[2]/div/div[1]/div[2]/button[2]")).size() != 0);
	}
	
	@When("I click on the add to portfolio button")
	public void i_click_on_the_add_to_portfolio_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/button[1]"));
	    button.click();
	}
	
	@Then("I should see a modal with a confirm and cancel button")
	public void i_should_see_a_modal_with_a_confirm_and_cancel_button() {
		// Checks if modal has display: block
		WebElement modal = driver.findElement(By.xpath("/html/body/div[1]"));
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]")).size() != 0);
		assertTrue(modal.getAttribute("style").contains("display: block;"));
		
		// Modal should have confirm and cancel buttons
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[3]/button[1]")).size() != 0);
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[3]/button[2]")).size() != 0);
	}
	
	@Then("I should see a field for stock name, stock quantity, purchase date, and sell date")
	public void i_should_see_a_field_for_stock_name_stock_quantity_purchase_date_and_sell_date() {
		// Checks if modal has display: block
		WebElement modal = driver.findElement(By.xpath("/html/body/div[1]"));
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]")).size() != 0);
		assertTrue(modal.getAttribute("style").contains("display: block;"));

		// Check if #add-modal-content has .display-block class
		WebElement addModalContent = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]"));
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]")).size() != 0);
		assertTrue(addModalContent.getAttribute("class").contains("display-block"));
		
		// Check for stock name input field
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[1]/input")).size() != 0);
		
		// Check for stock quantity input field
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[2]/input")).size() != 0);
		
		// Check for purchase date input field
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[3]/input")).size() != 0);
		
		// Check for sell date input field
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[4]/input")).size() != 0);
		
	}
	
	@When("I click on the upload to portfolio button")
	public void i_click_on_the_upload_to_portfolio_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/button[2]"));
	    button.click();
	}
	
	@When("I click on upload to portfolio button")
	public void i_click_on_upload_to_portfolio_button() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("I should see an input to upload a file")
	public void i_should_see_an_input_to_upload_a_file() {
		// Checks if modal has display: block
		WebElement modal = driver.findElement(By.xpath("/html/body/div[1]"));
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]")).size() != 0);
		assertTrue(modal.getAttribute("style").contains("display: block;"));

		// Check if #upload-file-modal-content has .display-block class
		WebElement uploadModalContent = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]"));
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]")).size() != 0);
		assertTrue(uploadModalContent.getAttribute("class").contains("display-block"));
		
		// Check for upload input button
		assertTrue(driver.findElements(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/form/div/input")).size() != 0);
	}
	
	@When("I have one or more stocks in my portfolio")
	public void i_have_one_or_more_stocks_in_my_portfolio() {
	    assertTrue(driver.findElements(By.className("stock-item")).size() > 0);
	}

	@Then("I should see a button to remove a stock from my portfolio")
	public void i_should_see_a_button_to_remove_a_stock_from_my_portfolio() {
	    assertTrue(driver.findElements(By.className("close-icon")).size() > 0);
	}

	@When("I enter {string} into the stock name input field")
	public void i_enter_into_the_stock_name_input_field(String string) {
		WebElement input = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[1]/input"));
		input.sendKeys(string);
	}
	

	@When("I enter {string} into the stock quantity input field")
	public void i_enter_into_the_stock_quantity_input_field(String string) {
		WebElement input = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[2]/input"));
		input.sendKeys(string);
	}


	@When("I enter {string} into the stock purchase date input field")
	public void i_enter_into_the_stock_purchase_date_input_field(String string) {
		WebElement input = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[3]/input"));
		input.sendKeys(string);
	}

	@When("I enter {string} into the stock sell date input field")
	public void i_enter_into_the_stock_sell_date_input_field(String string) {
		WebElement input = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/form/div/div[4]/input"));
		input.sendKeys(string);
	}
	
	@When("I click on the confirm button")
	public void i_click_on_the_confirm_button() {
	    WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/button[1]"));
	    button.click();
	}

	@Then("I should see {string} under my list of stocks in my portfolio")
	public void i_should_see_under_my_list_of_stocks_in_my_portfolio(String string) {
	    List<WebElement> stockItems = driver.findElements(By.className("stock-item"));
	    
	    Iterator<WebElement> iter = stockItems.iterator();

	    while(iter.hasNext()) {
	        WebElement stockItem = iter.next();

	        if (stockItem.getAttribute("innerHTML").equalsIgnoreCase(string)) {
	            assertTrue(true);
	        }
	    }
	    
	    assertTrue(false);
	}

	@When("I upload a CSV file with a TSLA stock")
	public void i_upload_a_CSV_file_with_a_TSLA_stock() {
	    assertTrue(false);
	}

	@When("I have {string} stock in my portfolio")
	public void i_have_stock_in_my_portfolio(String string) {
		List<WebElement> stockItems = driver.findElements(By.className("stock-item"));
	    
	    Iterator<WebElement> iter = stockItems.iterator();

	    while(iter.hasNext()) {
	        WebElement stockItem = iter.next();

	        if (stockItem.getAttribute("innerHTML").equalsIgnoreCase(string)) {
	            assertTrue(true);
	        }
	    }
	    
	    assertTrue(false);
	}

	@When("I press on the remove stock button")
	public void i_press_on_the_remove_stock_button() {
	    assertTrue(false);
	}

	@Then("I should no longer see {string} in my portfolio")
	public void i_should_no_longer_see_the_stock_in_my_portfolio(String string) {
		List<WebElement> stockItems = driver.findElements(By.className("stock-item"));
	    
	    Iterator<WebElement> iter = stockItems.iterator();

	    while(iter.hasNext()) {
	        WebElement stockItem = iter.next();

	        if (stockItem.getAttribute("innerHTML").equalsIgnoreCase(string)) {
	            assertTrue(false);
	        }
	    }
	    
	    assertTrue(true);
	}


	@After()
	public void after() {
		driver.quit();
	}
}
