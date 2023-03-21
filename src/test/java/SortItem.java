import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SortItem {
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
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\SortItemTest\\" + testName + "_" + dateTime + ".jpg"));

        driver.quit();
    }


    @Test
    public void sortItemPriceLowToHigh() {
        //sortuje po cenie od najniższej do najwyższej
        testName = "sortItemPriceLotToHigh";
        Select sortItemLoHi = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortItemLoHi.selectByValue("lohi");
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceString = priceElement.getText().replace("$", "");
            double price = Double.parseDouble(priceString);
            prices.add(price);
        }
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        System.out.println(prices);
        System.out.println(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);
    }

    @Test
    public void sortItemPriceHighToLow() {
        //sortowanie po cenie od najwyższej do najniższej
        testName = "sortItemPriceHighToLow";
        Select sortItemHiLo = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortItemHiLo.selectByValue("hilo");
        //tworzenie listy
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> prices = new ArrayList<>();
        //tworzenie listy, usuwanie znaków str i zamiana na liczby typu double
        for (WebElement priceElement : priceElements) {
            String priceString = priceElement.getText().replace("$", "");
            double price = Double.parseDouble(priceString);
            prices.add(price);
        }

        //sortowanie
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices, Collections.reverseOrder());
        System.out.println(prices);
        System.out.println(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);
    }

    @Test
    public void sortItemNameAtoZ() {
        testName = "sortItemNameAtoZ";
        Select sortItemNameAtoZ = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortItemNameAtoZ.selectByValue("az");
        //wyszukiwanie i tworzenie listy
        List<WebElement> itemNames = driver.findElements(By.className("inventory_item_name"));
        List<String> items = new ArrayList<>();
        for (WebElement itemName : itemNames) {
            String itemString = itemName.getText().replace(" ", "");
            items.add(itemString);
        }
        // sortowanie stworzonej listy
        items.sort(String.CASE_INSENSITIVE_ORDER);

        for (String item : items) {
            System.out.println(item);
        }
        //sprawdzanie czy oryginalna lista elementów jest równa posortowanej liście
        List<String> originalItems = new ArrayList<>();
        for (WebElement itemName : itemNames) {
            String itemString = itemName.getText().replace(" ", "");
            originalItems.add(itemString);
        }
        Assert.assertEquals(originalItems, items);

    }


    @Test
    public void sortItemNameZtoA() {
        testName = "sortItemNameZtoA";
        //sortowanie na stronie od Z do A
        Select sortItemNameZtoA = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortItemNameZtoA.selectByValue("za");
        // tworznie listy elementów
        List<WebElement> itemNames = driver.findElements(By.className("inventory_item_name"));
        // tworzenie listę stringów z tekstami elementów
        List<String> items = new ArrayList<>();
        for (WebElement itemName : itemNames) {
            String itemString = itemName.getText().replace(" ", "");
            items.add(itemString);
        }
        //sortowanie listy od Z do A
        Collections.sort(items, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        //wyświetlanie posortowanej listy
        for (String item : items) {
            System.out.println(item);
        }

        //sprawdzanie czy oryginalna lista elementów jest równa posortowanej liście
        List<String> originalItems = new ArrayList<>();
        for (WebElement itemName : itemNames) {
            String itemString = itemName.getText().replace(" ", "");
            originalItems.add(itemString);
        }
        Assert.assertEquals(originalItems, items);
    }
}




