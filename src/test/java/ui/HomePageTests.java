package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import pageObject.HomePage;

class HomePageTests extends BaseTest {

    @Test
    void checkTitle() {
        HomePage homePage = new HomePage(driver);

        String actualTitle = homePage.getTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", actualTitle);
    }

    @Test
    void checkURL() {
        HomePage homePage = new HomePage(driver);

        String currentUrl = homePage.getCurrentUrl();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/", currentUrl);
    }

    @Test
    void checkChapter3Link() {
        driver.findElement(By.linkText("Web form")).click();
    }

}
