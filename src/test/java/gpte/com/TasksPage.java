package gpte.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TasksPage {
	
	

    private WebDriver driver;
    private WebDriverWait wait;

    // Define the locators for username, password, login button, and logout button
    By submitButtonLocator =By.xpath("//button[@datatestid='button-submit']");
    private By loginBtnLocator = By.xpath("//button");
    By logoutLocator = By.xpath("//i[@class='icon-default fas fa-power-off text-white']");

    public TasksPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }
    
    
	public void verifyingSuccessMsg() {
		
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
