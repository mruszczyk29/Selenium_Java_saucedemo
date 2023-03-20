import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class LogoutTest {
    WebDriver driver;
    private String testName;
    @Before
    //Otwieranie, maxmalizowanie przeglądarki i przechodzenie do wybranej strony.
    //przed każdym rozpoczęciem testu
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.navigate().to("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    @After
    //Zamykanie przeglądarki, SS zapisywany w folderze po każdym wykonanym teście.
    public void tearDown() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\LogoutTest\\" + testName + "_" + dateTime + ".jpg"));

        driver.quit();
    }

    @Test
    public void logout() {
        testName = "logout";
        WebElement menuIcon = driver.findElement(By.id("react-burger-menu-btn"));
        menuIcon.click();
        Duration timeout = Duration.ofSeconds(3);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        WebElement logoutClick = driver.findElement(By.id("logout_sidebar_link"));
        logoutClick.click();
        String actualUrl = driver.getCurrentUrl();
        String exceptedUrl = "https://www.saucedemo.com/";
        Assert.assertEquals(exceptedUrl, actualUrl);
    }
}
