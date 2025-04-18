package ui;

import java.time.Duration;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class SevenHomeWork extends BaseTest {

    @Test
    void loadingImages() {
        driver.findElement(By.linkText("Loading images")).click();
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
    }

    @Test
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
    void shadowDom() {
        driver.findElement(By.linkText("Shadow DOM")).click();
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }

    @Test
    void cookieTest() {
        driver.findElement(By.linkText("Cookies")).click();
        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);
        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        Cookie date = options.getCookieNamed("date");
        assertThat(date.getValue()).isEqualTo("10/07/2018");

    }

    @Test
    void iframeTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.findElement(By.linkText("IFrames")).click();
        WebElement iframeElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iframeElement);
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet");
        driver.switchTo().defaultContent();
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");

    }

    @Test
    void alertTest() {
        driver.findElement(By.linkText("Dialog boxes")).click();
        driver.findElement(By.id("my-alert")).click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Hello world!");
        alert.accept();
    }

    @Test
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
    }

}
