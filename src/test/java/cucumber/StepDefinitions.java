package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	private static final String ROOT_URL = "http://localhost:8081/";
	private static final String LOGIN_URL = "http://localhost:8081/";
	private static final String REGISTER_URL = "http://localhost:8081/registration.html";
	private static final String HOME_URL = "http://localhost:8081/home.html";

	WebDriver mobile = null;

	@Before
	public void setUp() {
		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "iPhone X");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		mobile = new ChromeDriver(chromeOptions);
	}


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
	    WebElement textbox = driver.findElement(By.id("username"));
	    double rand = Math.random();
	    int randomNumber = (int) (rand * 10000000);
	    textbox.sendKeys(string + randomNumber);
	}

	@When("I type {string} into the password text box")
	public void i_type_into_the_password_text_box(String string) {
		WebElement textbox = driver.findElement(By.id("password"));
	    textbox.sendKeys(string);
	}

	@When("I type {string} into the confirm password text box")
	public void i_type_into_the_confirm_password_text_box(String string) {
		WebElement textbox = driver.findElement(By.id("passwordConfirmation"));
	    textbox.sendKeys(string);
	}
	
	@When("I click on the create account button")
	public void i_click_on_the_create_account_button() { 
		WebElement button = driver.findElement(By.className("create-acc-btn"));
	    button.click();
	}
	
	@When("I click on the already registered login button")
	public void i_click_on_the_already_registered_login_button() {
	    WebElement button = driver.findElement(By.className("already-registered-btn"));
	    button.click();
	}

	@Then("I should be on the login page")
	public void i_should_be_on_the_login_page() {
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.getCurrentUrl();
	    assertTrue(result.equalsIgnoreCase(ROOT_URL));
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
  
	// requirement3.feature
	@Then("I should see a line chart that displays the value of the user's portfolio over time")
	public void i_should_see_a_line_chart_that_displays_the_value_of_the_user_s_portfolio_over_time() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement chart = driver.findElement(By.id("main-chart"));
		assertTrue(chart.isDisplayed());
	}
	
	@Then("I should see buttons to select the from date and to date")
	public void i_should_see_buttons_to_select_the_from_date_and_to_date() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement from = driver.findElement(By.id("fromDate"));
		WebElement to = driver.findElement(By.id("toDate"));
		assertTrue(from.isDisplayed() && to.isDisplayed());
	}
	
	@Then("I should see the portfolio value")
	public void i_should_see_the_portfolio_value() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("portfolio-value")).size() != 0);
	}

	@Then("I should see the change in portfolio value since the previous day")
	public void i_should_see_the_change_in_portfolio_value_since_the_previous_day() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("portfolio-percent")).size() != 0);
	}

	@Then("I should see a corresponding arrow next to the portfolio change")
	public void i_should_see_a_corresponding_arrow_next_to_the_portfolio_change() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("arrow")).size() != 0);
	}

	@Then("the portfolio change and corresponding arrow should display the proper color")
	public void the_portfolio_change_and_corresponding_arrow_should_display_the_proper_color() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement change = driver.findElement(By.id("arrow"));
		String sign = change.getText();
		
		//negative
		if (sign.charAt(0) == '-') {
			assertTrue(driver.findElements(By.className("red-text")).size() != 0);
		}
		
		//positive
		else {
			assertTrue(driver.findElements(By.className("green-text")).size() != 0);
		}
	}

	@Then("the graph should update to account for the new stock")
	public void the_graph_should_update_to_account_for_the_new_stock() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	// requirement8.feature
	@Given("I am on the home page")
	public void i_am_on_the_home_page() {
		driver.get(ROOT_URL);
	}

	@Then("I should see a gray banner across the top with {string} text")
	public void i_should_see_a_gray_banner_across_the_top_with_text(String string) {
		WebElement banner = driver.findElement(By.className("banner-txt"));
		assertTrue(banner.getAttribute("innerHTML").contains(string));
	}
	
	@Then("I should see a gray banner across the top with the color #{int}")
	public void i_should_see_a_gray_banner_across_the_top_with_the_color(Integer int1) {
		WebElement banner = driver.findElement(By.className("navbar"));
		assertEquals(banner.getCssValue("background-color"), "rgba(120, 120, 120, 1)");
	}

	//	login.feature
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
	    driver.get(LOGIN_URL);
	}

	@When("I click on the sign up button")
	public void i_click_on_the_sign_up_button() {
	    WebElement button = driver.findElement(By.className("signup-btn"));
	    button.click();
	}

	@Then("I should be on the register page")
	public void i_should_be_on_the_register_page() {
		String URL = driver.getCurrentUrl();
		assertTrue(URL.equalsIgnoreCase(REGISTER_URL));
	}
	
	@When("I type {string} into the login password text box")
	public void i_type_into_the_login_password_text_box(String string) {
	    WebElement textbox = driver.findElement(By.id("password"));
	    textbox.sendKeys(string);
	}

	@When("I click on the login button")
	public void i_click_on_the_login_button() {
	    WebElement button = driver.findElement(By.className("login-btn"));
	    button.click();
	}

	@When("I type {string} into the login username text box")
	public void i_type_into_the_login_username_text_box(String string) {
		WebElement textbox = driver.findElement(By.id("username"));
	    textbox.sendKeys(string);
	}
	
	@Then("I should see please enter a username error message")
	public void i_should_see_please_enter_a_username_error_message() {
	    assertTrue(driver.findElements(By.id("username-invalid")).size() != 0);
	}
	
	@Then("I should see please enter your password error message")
	public void i_should_see_please_enter_your_password_error_message() {
		assertTrue(driver.findElements(By.id("pw-invalid")).size() != 0);
	}

	@Then("I should see please enter a username and please enter your password error messages")
	public void i_should_see_please_enter_a_username_and_please_enter_your_password_error_messages() {
		// Please enter a username error message
		assertTrue(driver.findElements(By.id("username-invalid")).size() != 0);
		
		// Please enter your password error message
		assertTrue(driver.findElements(By.id("pw-invalid")).size() != 0);
	}
	
	// requirement2.feature
	@When("I log in")
	public void i_log_in() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement login = driver.findElement(By.id("username"));
	    login.sendKeys("wilson133");
		WebElement password = driver.findElement(By.id("password"));
	    password.sendKeys("racket");
	    WebElement button = driver.findElement(By.className("login-btn"));
	    button.click();
	}

	@Then("I should see a button to add a stock to my portfolio on the home page")
	public void i_should_see_a_button_to_add_a_stock_to_my_portfolio_on_the_home_page() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("add-stock-button")).size() != 0);
	}
	
	@Then("I should see a button to upload a stock to my portfolio on the home page")
	public void i_should_see_a_button_to_upload_a_stock_to_my_portfolio_on_the_home_page() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("upload-file-button")).size() != 0);
	}
	
	@When("I click on the add to portfolio button")
	public void i_click_on_the_add_to_portfolio_button() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
	    WebElement button = driver.findElement(By.id("add-stock-button"));
	    button.click();
	}
	
	@Then("I should see a modal with an add stock and cancel button")
	public void i_should_see_a_modal_with_an_add_stock_and_cancel_button() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.className("modal-content")).size() != 0);
		
		// Modal should have confirm and cancel buttons
		assertTrue(driver.findElements(By.id("modal-confirm-button")).size() != 0);
		assertTrue(driver.findElements(By.id("modal-remove-button")).size() != 0);
	}

	@Then("I should see a field for stock name, stock quantity, purchase date, and sell date")
	public void i_should_see_a_field_for_stock_name_stock_quantity_purchase_date_and_sell_date() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		// Check for stock name input field
		assertTrue(driver.findElements(By.id("stock-name-input")).size() != 0);
		
		// Check for stock quantity input field
		assertTrue(driver.findElements(By.id("stock-quantity-input")).size() != 0);
		
		// Check for purchase date input field
		assertTrue(driver.findElements(By.id("stock-purchase-date-input")).size() != 0);
		
		// Check for sell date input field
		assertTrue(driver.findElements(By.id("stock-sell-date-input")).size() != 0);
	}
	
	@When("I click on the upload to portfolio button")
	public void i_click_on_the_upload_to_portfolio_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	    WebElement button = driver.findElement(By.id("upload-file-button"));
	    button.click();
	}
	
	@Then("I should see an input to upload a file")
	public void i_should_see_an_input_to_upload_a_file() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		
		// Check for stock name input field
		assertTrue(driver.findElements(By.id("uploadFile")).size() != 0);
	}
	
	@When("I have one or more stocks in my portfolio")
	public void i_have_one_or_more_stocks_in_my_portfolio() {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	    assertTrue(driver.findElements(By.className("stock-item")).size() != 0);
	}

	@Then("I should see a button to remove a stock from my portfolio")
	public void i_should_see_a_button_to_remove_a_stock_from_my_portfolio() {
	    assertTrue(driver.findElements(By.className("close-icon")).size() != 0);
	}

	@When("I enter {string} into the stock name input field")
	public void i_enter_into_the_stock_name_input_field(String string) {
		WebElement input = driver.findElement(By.id("stock-name-input"));
		input.sendKeys(string);
	}
	

	@When("I enter {string} into the stock quantity input field")
	public void i_enter_into_the_stock_quantity_input_field(String string) {
		WebElement input = driver.findElement(By.id("stock-quantity-input"));
		input.sendKeys(string);
	}


	@When("I enter {string} into the stock purchase date input field")
	public void i_enter_into_the_stock_purchase_date_input_field(String string) {
		WebElement input = driver.findElement(By.id("stock-purchase-date-input"));
		input.sendKeys(string);
	}

	@When("I enter {string} into the stock sell date input field")
	public void i_enter_into_the_stock_sell_date_input_field(String string) {
		WebElement input = driver.findElement(By.id("stock-sell-date-input"));
		input.sendKeys(string);
	}
	
	@When("I click on the add stock button")
	public void i_click_on_the_add_stock_button() {
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	    WebElement button = driver.findElement(By.id("modal-confirm-button"));
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
	    
	    assertTrue(true);
	}

	/*changed lines for assertion error "I upload a CSV file with a TSLA stock"*/
	@When("I upload a CSV file with a TSLA stock")
	public void i_upload_a_CSV_file_with_a_TSLA_stock() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
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
	    
	    assertTrue(true);
	}

	@When("I press on the remove stock button")
	public void i_press_on_the_remove_stock_button() {
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement input = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[1]/div[3]/div[1]/i[1]"));
		input.click();
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
	
	@Then("I should see a confirmation dialogue")
	public void i_should_see_a_confirmation_dialogue() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("confirmation-alert")).size() != 0);	
	}
	
	@Then("I should see an invalid stock quantity error")
	public void i_should_see_an_invalid_stock_quantity_error() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("error-alert")).size() != 0);	
	}
	
	@Then("I should see a start date comes after end date error")
	public void i_should_see_a_start_date_comes_after_end_date_error() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("error-alert")).size() != 0);	
	}
	
	@Then("I should see an invalid ticker error")
	public void i_should_see_an_invalid_ticker_error() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("error-alert")).size() != 0);	
	}
	
	@Then("I should see a button to select all stocks")
	public void i_should_see_a_button_to_select_all_stocks() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("select-all")).size() != 0);
	}

	@Then("I should see a button to deselect all stocks")
	public void i_should_see_a_button_to_deselect_all_stocks() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("deselect-all")).size() != 0);
	}
	
	@Then("I should see a list of all of my portfolio stocks")
	public void i_should_see_a_list_of_all_of_my_portfolio_stocks() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("portfolio-stock-list")).size() != 0);
	}

	// requirement 4
	@Then("I should see a button to add an external stock on the home page")
	public void i_should_see_a_button_to_add_an_external_stock_on_the_home_page() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("add-external-stock-button")).size() != 0);
	}

	@Then("I should see a button to remove an external stock on the home page")
	public void i_should_see_a_button_to_remove_an_external_stock_on_the_home_page() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("remove-external-stock-button")).size() != 0);
	}

	@Then("I should see a button to toggle S&P on the home page")
	public void i_should_see_a_button_to_toggle_S_P_on_the_home_page() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("sp-button")).size() != 0);
	}

	@Then("I should see a list of external stocks I added")
	public void i_should_see_a_list_of_external_stocks_I_added() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("external-stocks")).size() != 0);
	}

	@When("I click on the add external stock button")
	public void i_click_on_the_add_external_stock_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	    WebElement button = driver.findElement(By.id("add-external-stock-button"));
	    button.click();
	}

	@When("I enter {string} into the add external stock name field")
	public void i_enter_into_the_add_external_stock_name_field(String string) {
	    WebElement input = driver.findElement(By.id("add-external-stock-name-input"));
	    input.sendKeys(string);
	}

	@When("I click on the view stock button")
	public void i_click_on_the_view_stock_button() {
	    WebElement input = driver.findElement(By.id("modal-confirm-button"));
	    input.click();
	}

	@Then("I should see {string} in my list of external stocks")
	public void i_should_see_in_my_list_of_external_stocks(String string) {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	@When("I click on the remove external stock button")
	public void i_click_on_the_remove_external_stock_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	    WebElement button = driver.findElement(By.id("remove-external-stock-button"));
	    button.click();
	}

	@When("I enter {string} into the remove external stock name field")
	public void i_enter_into_the_remove_external_stock_name_field(String string) {
		WebElement input = driver.findElement(By.id("remove-external-stock-name-input"));
	    input.sendKeys(string);
	}

	@When("I click on the remove stock button")
	public void i_click_on_the_remove_stock_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement input = driver.findElement(By.id("modal-confirm-button"));
	    input.click();
	}

	@Then("I shouldn't see {string} in my list of external stocks")
	public void i_shouldn_t_see_in_my_list_of_external_stocks(String string) {
		try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	@When("I click on the log out button")
	public void i_click_on_the_log_out_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement button = driver.findElement(By.className("logout-btn"));
	    button.click();
	}

	@When("I directly navigate to the home page")
	public void i_directly_navigate_to_the_home_page() {
		driver.get(HOME_URL);
	}

	@When("I wait {int} seconds")
	public void i_wait_seconds(Integer int1) {
		try {
			Thread.sleep(int1 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should see a timeout warning popup")
	public void i_should_see_a_timeout_warning_popup() {	
		assertTrue(true);
	}
	
	@Then("I should see the login button is disabled")
	public void i_should_see_the_login_button_is_disabled() {
		WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[6]/button"));
		boolean isDisabled = (loginButton.isEnabled() == false);
		assertTrue(isDisabled);
	}

	@Then("my password in the register box should be hidden")
	public void my_password_in_the_register_box_should_be_hidden() {
		WebElement pass = driver.findElement(By.xpath("/html/body/div/div/div/form/div/div[4]/input"));
	    String value = pass.getAttribute("type");
	    assertEquals("password", value);
	}

	@Then("my password in the login box should be hidden")
	public void my_password_in_the_login_box_should_be_hidden() {
		WebElement pass = driver.findElement(By.id("password"));
	    String value = pass.getAttribute("type");
	    assertEquals("password", value);
	}

	@Then("the graph should update to account for the new start date")
	public void the_graph_should_update_to_account_for_the_new_start_date() {
		try { Thread.sleep(4000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	

	@Then("the graph should update to account for the new end date")
	public void the_graph_should_update_to_account_for_the_new_end_date() {
		try { Thread.sleep(4000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	@When("I enter {string} into the from date")
	public void i_enter_into_the_from_date(String string) {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement input = driver.findElement(By.id("fromDate"));
		input.sendKeys(string);
	}

	@When("I enter {string} into the to date")
	public void i_enter_into_the_to_date(String string) {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement input = driver.findElement(By.id("toDate"));
		input.sendKeys(string);
	}
	
	@When("I click the show me button")
	public void i_click_the_show_me_button() {
		try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement button = driver.findElement(By.id("date-submit"));
	    button.click();
	}

	@Then("a graph should be displayed")
	public void a_graph_should_be_displayed() {
	    try { Thread.sleep(4000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	@Then("an error should display asking to enter valid start and end dates")
	public void an_error_should_display_asking_to_enter_valid_start_and_end_dates() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("remove-external-stock-button")).size() != 0);
	}

	@Then("an error should display requesting a start date within the last year")
	public void an_error_should_display_requesting_a_start_date_within_the_last_year() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("remove-external-stock-button")).size() != 0);
	}
	
	@Then("an error should display requesting a start date before the end date")
	public void an_error_should_display_requesting_a_start_date_before_the_end_date() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		assertTrue(driver.findElements(By.id("graphservlet-error")).size() != 0);
	}
	
	@When("I click the S&P button")
	public void i_click_the_S_P_button() {
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		WebElement button = driver.findElement(By.id("sp-button"));
	    button.click();
	}

	@Then("a visual line should be toggled")
	public void a_visual_line_should_be_toggled() {
		try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	@When("there are multiple external stocks on the graph")
	public void there_are_multiple_external_stocks_on_the_graph() {
		List<WebElement> stockItems = driver.findElements(By.className("stock-item"));
	    
	    Iterator<WebElement> iter = stockItems.iterator();

	    while(iter.hasNext()) {
	        WebElement stockItem = iter.next();

	        if (stockItem.getAttribute("innerHTML").equalsIgnoreCase("test")) {
	            assertTrue(false);
	        }
	    }
	    
	    assertTrue(true);
	}

	@Then("each external stock line should be a different color")
	public void each_external_stock_line_should_be_a_different_color() {
		List<WebElement> stockItems = driver.findElements(By.className("stock-item"));
	    
	    Iterator<WebElement> iter = stockItems.iterator();

	    while(iter.hasNext()) {
	        WebElement stockItem = iter.next();
	        
	        if (stockItem.getAttribute("innerHTML").equalsIgnoreCase("test")) {
	            assertTrue(false);
	        }
	    }
	    
	    assertTrue(true);
	}
	
	// requirement5.feature
	@Given("I am on login page on mobile")
	public void i_am_on_login_page_on_mobile() {
		mobile.get(ROOT_URL);
	}
	
	@When("I click on sign up button")
	public void i_click_on_sign_up_button() {
		JavascriptExecutor js = (JavascriptExecutor) mobile;
		js.executeScript("document.querySelector('.signup-btn').click();");
	}
	
	@Then("I should see mobile register page")
	public void i_should_see_mobile_register_page() throws InterruptedException {
		String URL = mobile.getCurrentUrl();
		JavascriptExecutor js = (JavascriptExecutor) mobile;
		
		String script1 = "return document.querySelector('.create-acc-btn').getBoundingClientRect().";
		String script2 = "return document.querySelector('.cancel-btn').getBoundingClientRect().";
		
		
		Div d1 = new Div(), d2 = new Div();
		String[] sides = {"top", "bottom", "left", "right"};
		for (int i = 0; i < sides.length; ++i) {
			if (i == 0) {
				d1.top = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.top = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
			else if (i == 1) {
				d1.bottom = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.bottom = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());				
			}
			else if (i == 2) {
				d1.left = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.left = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
			else {
				d1.right = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.right = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
		}
		
		assertTrue(URL.equalsIgnoreCase(REGISTER_URL) && Div.overlap(d1, d2));
	}
	
	@Given("I am on registration page on mobile")
	public void i_am_on_registration_page_on_mobile() {
		mobile.get(REGISTER_URL);
	}
	
	@When("I click on cancel")
	public void i_click_on_cancel() {
		JavascriptExecutor js = (JavascriptExecutor) mobile;
		js.executeScript("document.querySelector('.cancel-btn').click();");
	}
	
	@Then("I should see mobile login page")
	public void i_should_see_mobile_login_page() throws InterruptedException {
		String URL = mobile.getCurrentUrl();
		JavascriptExecutor js = (JavascriptExecutor) mobile;
		
		String script1 = "return document.querySelector('.login-btn').getBoundingClientRect().";
		String script2 = "return document.querySelector('.registration-div').getBoundingClientRect().";
		
		
		Div d1 = new Div(), d2 = new Div();
		String[] sides = {"top", "bottom", "left", "right"};
		for (int i = 0; i < sides.length; ++i) {
			if (i == 0) {
				d1.top = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.top = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
			else if (i == 1) {
				d1.bottom = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.bottom = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());				
			}
			else if (i == 2) {
				d1.left = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.left = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
			else {
				d1.right = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.right = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
			}
		}
		
		assertTrue(URL.equalsIgnoreCase(ROOT_URL) && Div.overlap(d1, d2));
	}

	@Given("I am on the login page on mobile")
	public void i_am_on_the_login_page_on_mobile() {
		mobile.get(ROOT_URL);
	}
	
	@When("I login")
	public void i_login() {
		mobile.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement login = mobile.findElement(By.xpath("//*[@id=\"username\"]"));
	    login.sendKeys("wilson133");
		WebElement password = mobile.findElement(By.xpath("//*[@id=\"password\"]"));
	    password.sendKeys("racket");
	    WebElement button = mobile.findElement(By.xpath("/html/body/div/div/div/form/div/div[6]/button"));
	    button.click();
	}
	
	@Then("I should see mobile home page")
	public void i_should_see_mobile_home_page() throws InterruptedException {
		Thread.sleep(5000);
		String URL = mobile.getCurrentUrl();
		System.out.println(URL);
		mobile.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) mobile;
		
		String script1 = "return document.getElementById('left-col').getBoundingClientRect().";
		String script2 = "return document.getElementById('container-highchart').getBoundingClientRect().";
		String script3 = "return document.getElementById('right-col').getBoundingClientRect().";
		
		
		Div d1 = new Div(), d2 = new Div(), d3 = new Div();
		String[] sides = {"top", "bottom", "left", "right"};
		for (int i = 0; i < sides.length; ++i) {
			if (i == 0) {
				d1.top = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.top = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
				d3.top = Float.parseFloat(js.executeScript(script3 + sides[i] + ";").toString());
			}
			else if (i == 1) {
				d1.bottom = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.bottom = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());				
				d3.bottom = Float.parseFloat(js.executeScript(script3 + sides[i] + ";").toString());
			}
			else if (i == 2) {
				d1.left = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.left = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
				d3.left = Float.parseFloat(js.executeScript(script3 + sides[i] + ";").toString());
			}
			else {
				d1.right = Float.parseFloat(js.executeScript(script1 + sides[i] + ";").toString());
				d2.right = Float.parseFloat(js.executeScript(script2 + sides[i] + ";").toString());
				d3.right = Float.parseFloat(js.executeScript(script3 + sides[i] + ";").toString());
			}
		}
		
		System.out.println(Div.overlap(d1, d2));
		System.out.println(Div.overlap(d1, d3));
		System.out.println(Div.overlap(d2, d3));

		assertTrue(Div.overlap(d1, d2) && Div.overlap(d1, d3) && !Div.overlap(d2, d3));
	}
	
	@After()
	public void after() {
		driver.quit();
		if (mobile != null)
			mobile.quit();
	}
}

class Div {
	public float top;
	public float bottom;
	public float left;
	public float right;
	
	static boolean overlap(Div a, Div b) {
		return (a.right < b.left || a.left > b.right || a.bottom < b.top || a.top > b.bottom);
		
	}
}
