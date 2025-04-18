/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pageObject;

import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;
    protected static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    public HomePage(WebDriver driver) {
        this.driver = driver;

    }

    public String getTitle() {
        return driver.getTitle();

    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();

    }

}
