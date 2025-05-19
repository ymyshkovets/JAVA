package ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import org.aeonbits.owner.ConfigFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import configs.TestPropertiesConfig;
import io.qameta.allure.Feature;
import steps.AllureSteps;

@Feature("JS execute")

class SevenHomeWork {

    WebDriver driver;
    AllureSteps allureSteps = new AllureSteps();
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        driver = initDriver();
        driver.get(config.getBaseUrl());
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.getPageSource();
        driver.quit();
    }

    private WebDriver initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
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
    @DisplayName("Загрузка картинок")
    void loadingImages() {
        driver.findElement(By.linkText("Loading images")).click();
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
        allureSteps.captureScreenshot(driver);
    }

    @Test
    @DisplayName("Тест калькулятора")
    void slowCalculator() {
        driver.findElement(By.linkText("Slow calculator")).click();

        driver.findElement(By.xpath("//div[@class='keys']/span[text()='4']")).click();
        driver.findElement(By.xpath("//div[@class='keys']/span[text()='+']")).click();
        driver.findElement(By.xpath("//div[@class='keys']/span[text()='6']")).click();
        driver.findElement(By.xpath("//div[@class='keys']/span[text()='=']")).click();

        By resultField = By.xpath("//div[@class='screen']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(resultField, "10"));
    }

    @Test
    @DisplayName("shadowDom")
    void shadowDom() {
        driver.findElement(By.linkText("Shadow DOM")).click();
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        assertThat(textElement.getText()).contains("Hello Shadow DOM");
        allureSteps.captureScreenshot(driver);

    }

    @Test
    @DisplayName("Cookies test")
    void cookieTest() {
        driver.findElement(By.linkText("Cookies")).click();
        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);
        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        Cookie date = options.getCookieNamed("date");
        assertThat(date.getValue()).isEqualTo("10/07/2018");
        allureSteps.captureScreenshot(driver);

    }

    @Test
    @DisplayName("Iframe test")
    void iframeTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.findElement(By.linkText("IFrames")).click();
        WebElement iframeElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iframeElement);
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet");
        driver.switchTo().defaultContent();
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
        allureSteps.captureScreenshot(driver);

    }

    @Test
    @DisplayName("Alert test")
    void alertTest() {
        driver.findElement(By.linkText("Dialog boxes")).click();
        driver.findElement(By.id("my-alert")).click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Hello world!");
        alert.accept();
        allureSteps.captureScreenshot(driver);

    }

    @Test
    @DisplayName("Web storage test")
    void testWebStorage() {
        driver.findElement(By.linkText("Web storage")).click();
        WebStorage webStorage = (WebStorage) driver;

        var localStorage = webStorage.getLocalStorage();
        System.out.printf("Local storage elements: {%s}\n", localStorage.size());

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.keySet()
                .forEach(key -> System.out.printf("Session storage: {%s}={%s}\n", key, sessionStorage.getItem(key)));
        assertThat(sessionStorage.size()).isEqualTo(2);

        sessionStorage.setItem("new element", "new value");
        assertThat(sessionStorage.size()).isEqualTo(3);

        driver.findElement(By.id("display-session")).click();
        allureSteps.captureScreenshot(driver);

    }

}
