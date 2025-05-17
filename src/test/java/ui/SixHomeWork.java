
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

class SixHomeWork {

    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void setup() {
        driver = initDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    private WebDriver initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        System.out.println("Selenium_remote_url = " + remoteUrl);
        if (remoteUrl != null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        return driver;
    }

    @Test
    void inputField() {
        driver.findElement(By.linkText("Web form")).click();
        driver.findElement(By.name("my-text")).sendKeys("test");
        String currentText = driver.findElement(By.name("my-text")).getAttribute("value");
        assertEquals("test", currentText);
    }

    @Test
    void readOnlyField() {
        driver.findElement(By.linkText("Web form")).click();
        String currentText = driver.findElement(By.name("my-readonly")).getAttribute("value");
        driver.findElement(By.name("my-readonly")).sendKeys("test");
        assertEquals("Readonly input", currentText);
    }

    @Test
    void disabledField() {
        driver.findElement(By.linkText("Web form")).click();
        driver.findElement(By.name("my-disabled")).getAttribute("disabled");

    }

    @Test
    void listSelectedTest() {
        driver.findElement(By.linkText("Web form")).click();
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());
        Assertions.assertEquals("Open this select menu", select.getFirstSelectedOption().getText());
    }

    @Test
    void listSelectedAnotherOptionTest() {
        driver.findElement(By.linkText("Web form")).click();
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        select.selectByIndex(2);
        Assertions.assertEquals("Two", select.getFirstSelectedOption().getText());
    }

    @Test
    void checkboxTest() {
        driver.findElement(By.linkText("Web form")).click();
        Assertions.assertTrue(driver.findElement(By.name("my-check")).isSelected());
        driver.findElement(By.name("my-check")).click();
        Assertions.assertFalse(driver.findElement(By.name("my-check")).isSelected());
    }

    @Test
    void fileUploadTest() {
        String filePath = "C:/Users/Yuliya Myshkovets/JAVA/src/main/resources/text.txt";
        driver.findElement(By.linkText("Web form")).click();
        driver.findElement(By.name("my-file")).sendKeys(filePath);
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
    }

    @Test
    void dataPickerTest() {
        driver.findElement(By.linkText("Web form")).click();
        driver.findElement(By.name("my-date")).sendKeys("03/12/2025");
        String currentText = driver.findElement(By.name("my-date")).getAttribute("value");
        assertEquals("03/12/2025", currentText);

    }
}
