package com.example.testinium;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.open;

public class MainPageTest {

    private static final String USERNAME = "denemetest986@gmail.com";
    private static final String PASSWORD = "trial.1990";


    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://www.gittigidiyor.com");
    }

    @Test
    public void login(){

        WebDriver chromeDriver = new ChromeDriver();

        // launch browser & open web site
        chromeDriver.get("https://www.gittigidiyor.com/");

        WebElement loginPanel = chromeDriver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div"));
        loginPanel.click();
        waitMillis(1000);
        WebElement loginButton = chromeDriver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div/a"));
        loginButton.click();
        waitMillis(1000);

        WebElement username = chromeDriver.findElement(By.xpath("//*[@id=\"L-UserNameField\"]"));
        WebElement password = chromeDriver.findElement(By.xpath("//*[@id=\"L-PasswordField\"]"));
        username.sendKeys(USERNAME);
        waitMillis(1000);
        password.sendKeys(PASSWORD);
        waitMillis(1000);

        WebElement login = chromeDriver.findElement(By.xpath("//*[@id=\"gg-login-enter\"]"));
        login.click();

        waitMillis(3000);


        search(chromeDriver);

    }

    public void search(WebDriver chromeDriver){

        chromeDriver.navigate().to("https://www.gittigidiyor.com");

        WebElement searchItem = chromeDriver.findElement(By.tagName("input"));

        searchItem.sendKeys("bilgisayar");

        WebElement searchButton = chromeDriver.findElement(By.tagName("button"));

        searchButton.click();

        goToSecondPageAndAddItemToTheCart(chromeDriver);
    }

    public void goToSecondPageAndAddItemToTheCart(WebDriver chromeDriver) {

        chromeDriver.get("https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");

        Actions actions = new Actions(chromeDriver);
        WebElement addToCartButton = chromeDriver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div/div/div[2]/div/div[2]/div[3]/ul/li[2]/article/div/footer/button"));

        new WebDriverWait(chromeDriver, 50)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/main/div[2]/div/div/div[2]/div/div[2]/div[3]/ul/li[2]/article/div/footer/button"))).click();

        actions.moveToElement(addToCartButton).click().build().perform();
        addToCartButton.click();

        chromeDriver.get("https://www.gittigidiyor.com/sepetim");

        increaseProductAmount(chromeDriver);

        deleteProduct(chromeDriver);
    }

    public void deleteProduct(WebDriver chromeDriver){

        new WebDriverWait(chromeDriver, 20)
                .until(ExpectedConditions
                        .elementToBeClickable(By
                                .xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[1]/div[1]/div/div[6]/div[2]/div[2]/div[1]/div[3]/div/div[2]/div/a")))
                .click();
        new WebDriverWait(chromeDriver, 80);

    }

    public void increaseProductAmount(WebDriver chromeDriver){
        WebElement increaseButton = chromeDriver
                .findElement(By
                        .xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[1]/div[1]/div/div[6]/div[2]/div[2]/div[1]/div[4]/div/div[2]/select"));
        increaseButton.click();
        increaseButton.sendKeys("2");
    }

    private void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
