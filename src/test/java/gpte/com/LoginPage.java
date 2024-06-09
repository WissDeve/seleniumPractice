package gpte.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Define the locators for username, password, login button, and logout button
    private By usernameLocator = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/form/div/div[2]/div/input");
    private By passwordLocator = By.xpath("//input[@type='password']");
    private By loginBtnLocator = By.xpath("//button");
    By logoutLocator = By.xpath("//i[@class='icon-default fas fa-power-off text-white']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }



	public void login(String username, String password) throws InterruptedException {
        driver.get("http://localhost:3000/login");

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginBtnLocator));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));

       
    }

    public void logout() {
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(getLogoutLocator()));
        logoutBtn.click();
    }



	public By getLogoutLocator() {
		return logoutLocator;
	}



	public void setLogoutLocator(By logoutLocator) {
		this.logoutLocator = logoutLocator;
	}
}
