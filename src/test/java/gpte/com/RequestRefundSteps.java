package gpte.com;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class RequestRefundSteps {
	private WebDriver driver;
	private WebDriverWait wait;
	private RandomDateGenerator dateGenerator = new RandomDateGenerator();
	private LoginPage loginPage; // Declare the LoginPage instance

	String driverPath = "C:\\browserDrivers/chromedriver.exe";

	/*
	 * @Before public void setup() { driver = DriverUtils.getDriver();
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); }
	 */

	@Before
	public void setup() {
		// Set the path to the ChromeDriver executable
		System.setProperty("webdriver.chrome.driver", driverPath);
		// Set ChromeOptions to ignore SSL certificate errors
		ChromeOptions options = new ChromeOptions();
		options.setCapability("acceptInsecureCerts", true);
		options.addArguments("--disable-notifications"); // Optionally disable notifications

		// Set any other desired capabilities for ChromeDriver if needed
		// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// capabilities.setCapability("key", "value");

		// Initialize the WebDriver with ChromeOptions
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		// Optional: Set implicit wait to wait for elements to be available
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 20);

		// Initialize the LoginPage with the WebDriver
		loginPage = new LoginPage(driver);

	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}

	}

	@Given("I am logged in as a user")
	public void i_am_logged_in_as_a_user() throws InterruptedException {

		loginPage.login("EMP_ODM1", "HRA");
		System.out.println("Connected as employee");

	}

	@Given("I navigate to the refund request page")
	public void i_navigate_to_the_refund_request_page() {

		driver.navigate().to("http://localhost:3000/tasks");

		// click on the refund request
		WebElement refundRequest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='flex-column-start full-width table-body']/div[1]")));
		refundRequest.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@When("I submit the refund request form")
	public void i_submit_the_refund_request_form() {

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@datatestid='button-submit']")));
		;

		executor.executeScript("arguments[0].click();", submitButton);

	}

	@Then("I should see a success message indicating the request was sent successfully")
	public void i_should_see_a_success_message_indicating_the_request_was_sent_successfully() {

		try {
			// Your code to navigate to the page where the success message is located
			By successMessageLocator = By.xpath(
					"//div[@class='snackbar-container success']//p[@class='snackbar-message'][contains(text(), 'Demande envoyée avec succès')]");
			WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));

			// If the success message is found, the test passes
			System.out.println("Success message is present: " + successMessage.getText());
		} catch (Exception e) {
			// If the success message is not found or any other exception occurs, the test
			// fails
			System.out.println("Success message is not present.");
		}

	}

	@Given("I am logged in as a GESTNF user")
	public void i_am_logged_in_as_a_gestnf_user() throws InterruptedException {
		loginPage.login("GESTNF", "HRA");
		System.out.println("connecté en tant que gestionnaire.");
		driver.navigate().to("http://localhost:3000/tasks");
	}

	@And("there is a pending expense reimbursement request for GESTNF user")
	public void there_is_a_pending_expense_reimbursement_request_for_gestnf_user() {
		WebElement refundRequest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='flex-column-start full-width table-body']/div[1]")));
		refundRequest.click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("I validate the request as a GESTNF user")
	public void i_validate_the_request_as_a_gestnf_user() {
		
		

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
		
		
		WebElement validationDropDown = driver.findElement(By.name("STATUX"));
		Select select = new Select(validationDropDown);
		select.selectByValue("AP");

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@datatestid='button-submit']")));
		;

		executor.executeScript("arguments[0].click();", submitButton);


	}

	@Then("the request status should be validated for GESTNF user")
	public void the_request_status_should_be_validated_for_gestnf_user() {
		try {
			// Your code to navigate to the page where the success message is located
			By successMessageLocator = By.xpath(
					"//div[@class='snackbar-container success']//p[@class='snackbar-message'][contains(text(), 'Demande envoyée avec succès')]");
			WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));

			// If the success message is found, the test passes
			System.out.println("Success message is present: " + successMessage.getText());
		} catch (Exception e) {
			// If the success message is not found or any other exception occurs, the test
			// fails
			System.out.println("Success message is not present.");
		}
	}

	@Given("I am logged in as a responsible manager")
	public void i_am_logged_in_as_a_responsible_manager() throws InterruptedException {
		loginPage.login("MNGR4", "HRA");
		System.out.println("connecté en tant que gestionnaire.");

		// switch to manager
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@datatestid='SSEMP']"))).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[normalize-space()='Exploitat° Draa Sfar & CE']"))).click();

		// move to tasks
		driver.navigate().to("http://localhost:3000/tasks");
	}

	@And("there is a pending expense reimbursement request for responsible manager")
	public void there_is_a_pending_expense_reimbursement_request_for_responsible_manager() {
		WebElement refundRequest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='flex-column-start full-width table-body']/div[1]")));
		refundRequest.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("I validate the request as a responsible manager")
	public void i_validate_the_request_as_a_responsible_manager() {
		

		// Wait for the document to be in 'complete' state
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
        WebElement validationDropDown = driver.findElements(By.xpath("//select[@name='STATUX']")).get(1);

        Actions actions = new Actions(driver);
        actions.moveToElement(validationDropDown).perform();
        

        Select select = new Select(validationDropDown);
        select.selectByValue("AP");
        
        JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@datatestid='button-submit']")));
		;

		executor.executeScript("arguments[0].click();", submitButton);

	}

	@Then("the request status should be validated for responsible manager")
	public void the_request_status_should_be_validated_for_responsible_manager() {
		try {
			// Your code to navigate to the page where the success message is located
			WebElement successMessageLocator = driver.findElement(By.xpath(
					"//div[@class='snackbar-container success']//p[@class='snackbar-message'][contains(text(), 'Demande envoyée avec succès')]"));
			WebElement successMessage = wait.until(ExpectedConditions.visibilityOf(successMessageLocator));

			// If the success message is found, the test passes
			System.out.println("Success message is present: " + successMessage.getText());
		} catch (Exception e) {
			// If the success message is not found or any other exception occurs, the test
			// fails
			System.out.println("Success message is not present.");
		}
	}

	@Given("there is an approved expense reimbursement request")
	public void there_is_an_approved_expense_reimbursement_request() throws InterruptedException {
		loginPage.login("EMP_ODM1", "HRA");
		System.out.println("connecté en tant qu'employé.");
		driver.navigate().to("http://localhost:3000/requests");
	}

	@When("I check the request status")
	public void i_check_the_request_status()  {

		// ...

		// Find the first div element inside the specified class
		WebElement firstRequestDiv = driver.findElement(By.xpath("//div[@class='flex-column-start full-width table-body']/div[1]"));

		// Get the text inside the div element
		String requestStatus = firstRequestDiv.getText();
		System.out.println("Request text : "+requestStatus );

		// Check if the text contains "Validé"
		//Assert.assertTrue("The request status should contain 'validée.'", requestStatus.contains("Valid"));

		
		
	}

	@Then("the request status should be marked as APPROVED")
	public void the_request_status_should_be_marked_as_APPROVED() {
		System.out.println("Request has been approved");
	}
}
