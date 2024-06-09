/*
 * package gpte.com;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.WebDriver; import
 * org.openqa.selenium.WebElement; import
 * org.openqa.selenium.chrome.ChromeDriver;
 * 
 * import io.cucumber.java.After; import io.cucumber.java.Before; import
 * io.cucumber.java.en.Given; import io.cucumber.java.en.Then; import
 * io.github.bonigarcia.wdm.WebDriverManager;
 * 
 * 
 * public class Steps {
 * 
 * private WebDriver driver;
 * 
 * 
 * public WebDriver getDriver() { if (driver == null) {
 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver();
 * driver.manage().window().maximize(); } return driver; }
 * 
 * 
 * @Before public void setup() { driver = DriverUtils.getDriver(); }
 * 
 * 
 * @After public void teardown() { if(driver!=null) { driver.quit(); } }
 * 
 * 
 * 
 * @Given("I am in the login page") public void i_am_in_the_login_page() {
 * 
 * driver.get("https://practicetestautomation.com/practice-test-login/");
 * 
 * }
 * 
 * @Given("I enter valid credentials") public void i_enter_valid_credentials() {
 * 
 * WebElement usernameField = driver.findElement(By.id("username")); // Replace
 * with appropriate locator WebElement passwordField =
 * driver.findElement(By.id("password")); // Replace with appropriate locator
 * 
 * usernameField.sendKeys("student"); passwordField.sendKeys("Password123");
 * driver.findElement(By.id("submit")).click();
 * 
 * 
 * 
 * }
 * 
 * @Then("I should be taken to the overview page") public void
 * i_should_be_taken_to_the_overview_page() {
 * 
 * String expectedUrl=
 * "https://practicetestautomation.com/logged-in-successfully/"; String
 * currentUrl = driver.getCurrentUrl();
 * 
 * //Assertion assert currentUrl.equals(expectedUrl):"Wrong credentials";
 * 
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * }
 */