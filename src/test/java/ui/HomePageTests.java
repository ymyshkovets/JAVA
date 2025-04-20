package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import pageObject.BasePage;
import pageObject.HomePage;

class HomePageTests extends BaseTest {

    @Test
    void checkTitle() {
        BasePage basePage = new BasePage(driver);

        String actualTitle = basePage.getTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", actualTitle);
    }

    @Test
    void checkURL() {
        BasePage basePage = new BasePage(driver);

        String currentUrl = basePage.getCurrentUrl();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/", currentUrl);
    }

    @Test
    void checkChapter3Link() {
        HomePage homePage = new HomePage(driver);
        homePage.openPageByLinkText("Web form");
        BasePage basePage = new BasePage(driver);
        String currentUrl = basePage.getCurrentUrl();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/web-form.html", currentUrl);

    }

}
