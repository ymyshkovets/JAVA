package ui;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pageObject.BasePage;

class WebFormPageTests extends BaseTest {

    @Test
    void inputField() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        basePage.findElementBynameandSendValue("my-text", "test");
        String currentText = basePage.findElementBynameandGetAttibute("my-text", "value");
        assertEquals("test", currentText);
    }

    @Test
    void readOnlyField() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        String currentText = basePage.findElementBynameandGetAttibute("my-readonly", "value");
        basePage.findElementBynameandSendValue("my-readonly", "test");
        assertEquals("Readonly input", currentText);
    }

    @Test
    void disabledField() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        basePage.findElementBynameandGetAttibute("my-disabled", "disabled");

    }

    @Test
    void listSelectedTest() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());
        Assertions.assertEquals("Open this select menu", select.getFirstSelectedOption().getText());
    }

    @Test
    void listSelectedAnotherOptionTest() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        select.selectByIndex(2);
        Assertions.assertEquals("Two", select.getFirstSelectedOption().getText());
    }

    @Test
    void checkboxTest() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        Assertions.assertTrue(driver.findElement(By.name("my-check")).isSelected());
        driver.findElement(By.name("my-check")).click();
        Assertions.assertFalse(driver.findElement(By.name("my-check")).isSelected());
    }

    @Test
    void fileUploadTest() {
        String filePath = "C:/Users/Yuliya Myshkovets/JAVA/src/main/resources/text.txt";
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        driver.findElement(By.name("my-file")).sendKeys(filePath);
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
    }

    @Test
    void dataPickerTest() {
        BasePage basePage = new BasePage(driver);
        basePage.openPageByLinkText("Web form");
        basePage.findElementBynameandSendValue("my-date", "03/12/2025");
        String currentText = basePage.findElementBynameandGetAttibute("my-date", "value");
        assertEquals("03/12/2025", currentText);

    }
}
