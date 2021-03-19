import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class AutoPracticeTest {

    private WebDriver webDriver;
    private Actions actions;
    protected HomePage homePage;
    protected InnerPage innerPage;


    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        actions = new Actions(webDriver);
    }

    @Test
    public void testAddToCartWorks() {
        int expectedQuantity = 3;
        String expectedSize = "M";
        String expectedColor = "White";
        String expectedProductName;

        double singlePrice;

        homePage = new HomePage(webDriver, actions);
        homePage.navigateToHomePage();
        homePage.implicitlyWait(20, TimeUnit.MILLISECONDS);
        homePage.navigateToWomenCategory();
        homePage.navigateToDressType();
        homePage.implicitlyWait(20, TimeUnit.MILLISECONDS);
        expectedProductName = homePage.getInitialProductName();
        homePage.navigateToQuickView();

        innerPage = new InnerPage(webDriver, actions);
        innerPage.navigateToQuickViewIframe();

        singlePrice = innerPage.getPrice();

        innerPage.selectQuantity(expectedQuantity);
        innerPage.selectDressSize(expectedSize);
        innerPage.selectDressColor(expectedColor);
        innerPage.clickSubmitButton();
        innerPage.implicitlyWait(12, TimeUnit.SECONDS);

        webDriver.switchTo().defaultContent();

        homePage.clickCloseButton();
        homePage.implicitlyWait(5, TimeUnit.SECONDS);
        homePage.navigateToShoppingCart();

        int currentQuantity = homePage.getQuantity();
        Assert.assertEquals(expectedQuantity, currentQuantity);

        String[] productAttributes = homePage.getProductAttributes().split(", ");
        String color = productAttributes[0];
        Assert.assertEquals(expectedColor, color);
        String size = productAttributes[1];
        Assert.assertEquals(expectedSize, size);

        Assert.assertEquals(expectedProductName, homePage.getProductName());

        double currentShippingCost = homePage.getShippingCost();
        double expectedTotalSum = (singlePrice * currentQuantity) + currentShippingCost;
        Assert.assertEquals(expectedTotalSum, homePage.getTotalSum(), 0.0);

    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
