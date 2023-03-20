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

public class CompleteOrder {
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
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\CompleteOrder\\" + testName + "_" + dateTime + ".jpg"));

        driver.quit();
    }

    @Test
    public void completeOrder() {
        testName = "completeOrder";
        WebElement addCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement cartIcon = driver.findElement(By.cssSelector("a.shopping_cart_link"));
        cartIcon.click();
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("Jan");
        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Nowak");
        WebElement zipCode = driver.findElement(By.id("postal-code"));
        zipCode.sendKeys("101010");
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();
        String exceptedCheckoutComplete = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
        WebElement checkoutComplete = driver.findElement(By.cssSelector("div.complete-text"));
        String checkoutCompleteText = checkoutComplete.getText();
        Assert.assertEquals(exceptedCheckoutComplete, checkoutCompleteText);
        if (checkoutComplete.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", checkoutComplete);
        }
    }
}
