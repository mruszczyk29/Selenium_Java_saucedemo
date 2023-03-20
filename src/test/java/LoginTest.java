import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    WebDriver driver;
    private String testName;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.navigate().to("https://www.saucedemo.com/");
    }


    @Test
    public void loginPassTest() {
        testName = "loginPassTest";
        //Sprawdzenie, czy można się zalogować na konto z prawidłowymi danymi logowania.
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        //Menu widać tylko po poprawnym zalogowaniu na stronie.
        //Sprawdzanie widoczności menu.
        WebElement menuIsVisible = driver.findElement(By.id("react-burger-menu-btn"));
        assertTrue(menuIsVisible.isDisplayed());
    }

    @Test
    public void loginFailTest() {
        testName = "loginFailTest";
        //błędny login oraz hasło w celu sprawdzenia czy pojawia się informacja o błędnym loginie lub haśle
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("fake_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("fake_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        //Pobieranie komunikatu o błędnym loginie/haśle
        WebElement actualErrorMessage1 = driver.findElement(By.cssSelector(".error-message-container"));
        String actualErrorMessage = driver.findElement(By.cssSelector(".error-message-container")).getText();
        //Sprawdzanie czy aktualny rezultat zgadza się z oczekiwanym
        assertTrue(actualErrorMessage.contains("Epic sadface: Username and password do not match any user in this service"));
        if (actualErrorMessage1.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", actualErrorMessage1);
        }

    }

    @After
    //Zamykanie przeglądarki, SS zapisywany w folderze po każdym wykonanym teście.
    public void tearDown() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\LoginTest\\" + testName + "_" + dateTime + ".jpg"));

        driver.quit();
    }
}
