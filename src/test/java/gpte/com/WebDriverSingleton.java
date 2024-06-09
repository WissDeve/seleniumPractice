package gpte.com;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSingleton {

    private static WebDriver driver;
    private static WebDriverWait wait;

    private WebDriverSingleton() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            // Setup ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();

            // Create ChromeOptions with desired capabilities
            ChromeOptions options = new ChromeOptions();
            options.setCapability("acceptInsecureCerts", true);
            options.addArguments("--disable-notifications"); // Optionally disable notifications

            // Initialize the WebDriver with ChromeOptions
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

            // Initialize WebDriverWait with the driver
            wait = new WebDriverWait(driver, 20);
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null; // Reset the WebDriverWait instance
        }
    }
}

