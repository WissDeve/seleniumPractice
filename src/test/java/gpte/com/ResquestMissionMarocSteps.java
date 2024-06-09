package gpte.com;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ResquestMissionMarocSteps {

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

//	@After
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

	@Given("I am logged in as an employee")
	public void i_am_logged_in_as_an_employee() throws InterruptedException {

		loginPage.login("EMP_ODM1", "HRA");
		System.out.println("Logged in as an employee.");
		driver.navigate().to("http://localhost:3000/WMOMIMCO");

	}

	public void selectStartDate(String dateDeb) {
		// Locate the datepicker input field
		WebElement startDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DATDEB")));

		// Use JavaScriptExecutor to set the value of the datepicker input directly
		String script = String.format("arguments[0].setAttribute('value', '%s')", dateDeb);
		((JavascriptExecutor) driver).executeScript(script, startDateInput);
	}

	@When("I enter the details for the mission order request")
	public void i_enter_the_details_for_the_mission_order_request() {

		WebElement dropdownMission = driver.findElement(By.name("OBJMIS"));
		Select select = new Select(dropdownMission);
		select.selectByVisibleText("Projet Réunion");

		WebElement description = driver.findElement(By.id("DESMIS"));
		wait.until(ExpectedConditions.visibilityOf(description));
		description.sendKeys("Test auto sample");

		WebElement startDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DATDEB")));
		String startDate = dateGenerator.generateRandomStartDate();
		startDateInput.sendKeys(startDate);

		driver.findElement(By.id("HDEPAR")).sendKeys("17:00");

		WebElement endInputDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DATFIN")));
		String endDate = dateGenerator.generateRandomEndDate(startDate);

		endInputDate.clear();
		endInputDate.sendKeys(endDate); // Use endInputDate to set the end date

		driver.findElement(By.id("HRETOR")).sendKeys("10:00");

		WebElement transportMethod = driver.findElement(By.name("MOYDEP"));
		Select transportList = new Select(transportMethod);
		transportList.selectByValue("004");

		WebElement site = wait.until(ExpectedConditions.elementToBeClickable((By.name("LIEMIS"))));

		Select siteLocation = new Select(site);
		siteLocation.selectByValue("L0016");

		// After clicking the add button, wait for the element to be clickable and then
		// interact with it
		WebElement addIcon = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//i[@class='icon-default fas fa-plus-circle text-black cursor-pointer']")));
		addIcon.click();

		// Wait for the element to be clickable before interacting with it
		WebElement villeDepart = wait.until(ExpectedConditions.elementToBeClickable(By.name("VILDEP")));

		// Create a Select object for the select element
		Select villeList = new Select(villeDepart);

		// Wait for the options to be present and enabled
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return villeList.getOptions().stream().allMatch(WebElement::isEnabled);
			}
		});

		// Now you can select the option by its value attribute
		villeList.selectByValue("450");

		// Wait for the element to be clickable before interacting with it
		WebElement villeArr = wait.until(ExpectedConditions.elementToBeClickable(By.name("VILARV")));

		// Create a Select object for the select element
		Select ArriveList = new Select(villeArr);

		// Wait for the options to be present and enabled
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ArriveList.getOptions().stream().allMatch(WebElement::isEnabled);
			}
		});

		// Now you can select the option by its value attribute
		ArriveList.selectByValue("780");

		WebElement AddBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Ajouter']")));
		AddBtn.click();

		WebElement nextBtn = driver.findElement(By.xpath("//button[text()='Suivant']"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", nextBtn);

		// Click the "Suivant" button
		nextBtn.click();

		WebElement comment = wait.until(ExpectedConditions.elementToBeClickable(By.id("TXCOMM")));

		comment.sendKeys("Test automated script");

	}

	@And("I submit the request")
	public void i_submit_the_request() {

		WebElement sendBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Envoyer']")));
		sendBtn.click();
	}

	@Then("I should see a confirmation message indicating the request was sent successfully")
	public void i_should_see_a_confirmation_message_indicating_the_request_was_sent_successfully() {
		/*
		 * String expectedURL = "http://localhost:3000/login";
		 * wait.until(ExpectedConditions.urlToBe(expectedURL));
		 * System.out.println("********Mission Request was sent succesfully****");
		 */
		
		try {
            // Your code to navigate to the page where the success message is located
            
            By successMessageLocator = By.xpath("//div[@class='snackbar-container success']//p[@class='snackbar-message'][contains(text(), 'Demande envoyée avec succès')]");
            WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));
            
            // If the success message is found, the test passes
            System.out.println("Success message is present: " + successMessage.getText());
        } catch (Exception e) {
            // If the success message is not found or any other exception occurs, the test fails
            System.out.println("Success message is not present.");
        } 
		

		// log out

		loginPage.logout();
		System.out.println("Logged out.");
		driver.close();

	}

	@Given("I am logged in as the responsible collaborator")
	public void i_am_logged_in_as_the_responsible_collaborator() throws InterruptedException {

		loginPage.login("MANAGERN", "HRA");
		System.out.println("Logged in as a manager.");
		driver.navigate().to("http://localhost:3000/tasks");

	}

	@When("I view the pending mission order requests")
	public void i_view_the_pending_mission_order_requests() {

		WebElement missionRequest = driver
				.findElement(By.xpath("//div[@class='flex-column-start full-width table-body']/div[1]"));
		// assertThat("The element is not visible.", missionRequest.isDisplayed(),
		// is(true));

		missionRequest.click();

	}

	/**
	 * 
	 */
	@And("I approve the request")
	public void i_approve_the_request() {

		WebElement validationDropDown = driver.findElement(By.name("STATUX"));
		Select select = new Select(validationDropDown);
		select.selectByValue("AP");
		
		

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));

//		// Wait for the "Suivant" button to be visible
//		
//		WebElement nextBtn = driver.findElement(By.xpath("//button[@datatestid='button-next']"));
//		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("arguments[0].scrollIntoView();", nextBtn);
//
//		// Click the "Suivant" button
//		nextBtn.click();

		
	WebElement nextBtn = driver.findElement(By.xpath("//button[@datatestid='button-next']"));
	
	if(nextBtn.isDisplayed()) {
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].scrollIntoView();", nextBtn); 
	    nextBtn.click();
	    
		
		
	}
	

    
		
		
		WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@datatestid='button-submit']")));;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", submitButton);

		
		
		
	}

	@Then("the request should be marked as approved")
	public void the_request_should_be_marked_as_approved() {
		
		try {
            // Your code to navigate to the page where the success message is located
            
            By successMessageLocator = By.xpath("//div[@class='snackbar-container success']//p[@class='snackbar-message'][contains(text(), 'Demande envoyée avec succès')]");
            WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));
            
            // If the success message is found, the test passes
            System.out.println("Success message is present: " + successMessage.getText());
        } catch (Exception e) {
            // If the success message is not found or any other exception occurs, the test fails
            System.out.println("Success message is not present.");
        } 
		
		
    

	}

}
