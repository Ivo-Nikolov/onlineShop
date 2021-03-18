import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String shopURL = "http://automationpractice.com";
        double singlePrice;
        driver.navigate(shopURL);

        driver.implicitlyWait(20, TimeUnit.MILLISECONDS);

        driver.navigateToWomenCategory();
        driver.navigateToDressType();
        expectedProductName = driver.getInitialProductName();
        driver.navigateToQuickView();
        driver.navigateToQuickViewIframe();
        singlePrice = driver.getPrice();
        driver.selectQuantity(expectedQuantity);
        driver.selectDressSize(expectedSize);
        driver.selectDressColor(expectedColor);
        driver.clickSubmitButton();
        driver.returnDefaultContent();
        driver.clickCloseButton();
        driver.navigateToShoppingCart();
        int currentQuantity = driver.getQuantity();
        Assert.assertEquals(expectedQuantity, currentQuantity);

        String[] productAttributes = driver.getProductAttributes().split(", ");
        String color = productAttributes[0];
        Assert.assertEquals(expectedColor, color);
        String size = productAttributes[1];
        Assert.assertEquals(expectedSize, size);

        Assert.assertEquals(expectedProductName, driver.getProductName());

        double currentShippingCost = driver.getShippingCost();
        double expectedTotalSum = (singlePrice * currentQuantity) + currentShippingCost;
        Assert.assertEquals(expectedTotalSum, driver.getTotalSum(), 0.0);

    }

    @After
    public void tearDown() {
        driver.destroyWebDriver();
    }
}
