import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class autoPracticeTest {

    private webDriverWrapper driver;

    @Before
    public void setup() {
        driver = new webDriverWrapper();
    }

    @Test
    public void testAddToCartWorks() {
        int expectedQuantity = 3;
        String expectedSize = "M";
        String expectedColor = "White";
        String expectedProductName;
        String googleURL = "http://google.com";
        String shopURL = "http://automationpractice.com";
        driver.navigate(googleURL);
        driver.navigate(shopURL);

        driver.implicitlyWait(20, TimeUnit.MILLISECONDS);

        driver.navigateToWomenCategory();
        driver.navigateToDressType();
        expectedProductName = driver.getInitialProductName();
        driver.navigateToQuickView();
        driver.navigateToQuickViewIframe();
        driver.selectQuantity(expectedQuantity);
        driver.selectDressSize(expectedSize);
        driver.selectDressColor(expectedColor);
        driver.clickSubmitButton();
        driver.implicitlyWait(10, TimeUnit.SECONDS);
        driver.returnDefaultContent();
        driver.clickCloseButton();
        driver.navigateToShoppingCart();

        Assert.assertEquals(Integer.toString(expectedQuantity), driver.getQuantity());

        String[] productAttributes = driver.getProductAttributes().split(", ");
        String color = productAttributes[0];
        Assert.assertEquals(expectedColor, color);
        String size = productAttributes[1];
        Assert.assertEquals(expectedSize, size);

        Assert.assertEquals(expectedProductName, driver.getProductName());


//        WebElement shippingCostElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]"));
//        double shippingCost = Double.parseDouble(shippingCostElement.getText().substring(1));
//        WebElement totalSumElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]"));
//        double totalSum = Double.parseDouble(totalSumElement.getText().substring(1));
        double expectedTotalSum = (driver.getPrice() * Double.parseDouble(driver.getQuantity())) + Double.parseDouble(driver.getShippingCost());
        Assert.assertEquals(expectedTotalSum, Double.parseDouble(driver.getTotalSum()), 0.0);
//        WebElement name = webDriver.findElement(By.xpath("//a[@class='cart_block_product_name']"));

    }

    @After
    public void tearDown(){
        driver.destroyWebDriver();
    }
}
