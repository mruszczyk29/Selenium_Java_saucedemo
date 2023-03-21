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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CartTest {

    WebDriver driver;

    //dzięki tej deklaracji można używać zmienntej testName do innych testów.
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
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\CartTest\\" + testName + "_" + dateTime + ".jpg"));

        driver.quit();
    }

    @Test
    public void addToCartButton() {
        testName = "addToCartButton";
        //sprawdzanie zmiany przycisku po kliknieciu z add to cart do remove
        WebElement priceBar = driver.findElement(By.cssSelector("div.inventory_item:nth-child(1) div.pricebar button"));
        System.out.println(priceBar.getText());
        WebElement addCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement priceBar2 = driver.findElement(By.cssSelector("div.inventory_item:nth-child(1) div.pricebar button"));
        System.out.println(priceBar2.getText());
        //porównywanie wyniku oczekiwanego z aktualnym
        String expectedButtonText = "Remove";
        Assert.assertEquals(expectedButtonText, priceBar2.getText());
        //zaznaczenie ostatniego sprawdzanego elementu
        if (priceBar2.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", priceBar2);
        }
    }

    @Test
    public void cartIconChange() {
        testName = "cartIconChange";
        //sprawdzenie czy po dodaniu jednego produktu do koszyka pojawi się cyfra 1 przy koszyku
        WebElement addCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement cartIcon = driver.findElement(By.cssSelector("a.shopping_cart_link>span"));
        System.out.println(cartIcon.getText());
        String expectedCartNumber = "1";
        Assert.assertEquals(expectedCartNumber, cartIcon.getText());
        if (cartIcon.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", cartIcon);
        }
    }

    @Test
    public void itemInCart() {
        testName = "itemInCart";
        //sprawdzanie czy dodany produkt faktycznie znajduje się w koszyku
        WebElement addCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement cartIcon = driver.findElement(By.cssSelector("a.shopping_cart_link"));
        cartIcon.click();
        String itemName = driver.findElement(By.cssSelector(".inventory_item_name")).getText();
        System.out.println(itemName);
        Assert.assertTrue(itemName.contains("Sauce Labs Backpack"));
        //podświetlenie ostatniego sprawdzanego elementu.
        WebElement itemName2 = driver.findElement(By.cssSelector(".inventory_item_name"));
        if (itemName2.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", itemName2);
        }
    }

    @Test
    public void removeFromCart() {
        testName = "removeFromCart";
        //dodawanie produktu do koszyka
        WebElement addCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addCartButton.click();
        WebElement cartIcon = driver.findElement(By.cssSelector("a.shopping_cart_link"));
        cartIcon.click();
        WebElement backpackInCart = driver.findElement(By.cssSelector(".inventory_item_name"));
        //usuwanie produktu z koszyka
        WebElement removeButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
        removeButton.click();
        Duration timeout = Duration.ofSeconds(2);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOf(backpackInCart));
        Assert.assertTrue("Element jest niewidoczny", isElementInvisible);

    }

    @Test
    public void randomAddCartButton() {
        //test wybiera losowy przycisk add to cart i go klika, oznacza ostatnio kliknięty przycisk
        testName = "randomAddCartButton";
        //wyszukiwanie listy elementów
        List<WebElement> addCartButtons = driver.findElements(By.cssSelector("div.inventory_container button"));
        //losowanie przycisku z listy
        int randomIndex = new Random().nextInt(addCartButtons.size());
        WebElement addCartButton = addCartButtons.get(randomIndex);
        addCartButton.click();
        //ponowne wyszukiwanie przyciskow
        List<WebElement> addCartButtons1 = driver.findElements(By.cssSelector("div.inventory_container button"));
        //pętla do  wyciągnięcia ID ze wszystkich przycisków
        for (WebElement button : addCartButtons1) {
            System.out.println(button.getAttribute("id"));
            //sprawdzanie czy przycisk jest widoczny oraz porównywanie ID czy w któryms pojawiło sie słowo "remove"
            //jeśli tak to oznacza ten przycisk borderem, bo został kliknięty
            if (button.isDisplayed() && button.getAttribute("id").contains("remove")) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].style.border = '3px solid blue';", button);
            }
        }
        System.out.println(addCartButton);
    }


    @Test
    public void randomAddCartButtons() {
        //test wybiera losowy przycisk add to cart i go klika
        testName = "randomAddCartButtons";
        //wyszukiwanie listy elementów
        List<WebElement> addCartButtons = driver.findElements(By.cssSelector("div.inventory_container button"));
        //losowanie przycisków z listy
        Random rand = new Random();
        int previousIndex = -1;
        List<WebElement> clickedButtons = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int randomIndex = rand.nextInt(addCartButtons.size());
            while (randomIndex == previousIndex) {
                randomIndex = rand.nextInt(addCartButtons.size());
            }
            WebElement addCartButton = addCartButtons.get(randomIndex);
            addCartButton.click();
            previousIndex = randomIndex;
            clickedButtons.add(addCartButton);
        }

        }
    }





