package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

    WebDriver driver;

    //WebElement submitButton = driver.findElement(By.xpath("//button[@type = 'submit']"));
    public BasePage(WebDriver driver) {
        this.driver = driver;

    }

    public String getTitle() {
        return driver.getTitle();

    }

    public void openPageByLinkText(String text) {
        driver.findElement(By.linkText(text)).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void findElementBynameandSendValue(String name, String value) {
        driver.findElement(By.name(name)).sendKeys(value);

    }

    public String findElementBynameandGetAttibute(String name, String value) {
        return driver.findElement(By.name(name)).getAttribute(value);

    }

}
