import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class HomePage{

    private final By womenCategoryLocator = By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Women')]");
    private final By dressTypeLocator = By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Summer Dresses')]");
    private final By initialProductNameLocator = By.xpath("//ul[contains(@class,'product_list')]/li[2]//a[@class='product-name']");
    private final By secondDressLocator = By.xpath("//ul[contains(@class,'product_list')]/li[2]");
    private final By quickViewLocator = By.xpath("(//a[@class='quick-view'])[2]");
    private final By shoppingCartLocator = By.xpath("//a[@title='View my shopping cart']");
    private final By quantityLocator = By.xpath("//span[@class='quantity']");
    private final By productAttributesLocator = By.xpath("//div[@class='product-atributes']/a");
    private final By shippingCostLocator = By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]");
    private final By totalSumLocator = By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]");
    private final By productNameLocator = By.xpath("//a[@class='cart_block_product_name']");
    private final String homePageUrl = "http://automationpractice.com";
    private final By closeButtonLocator = By.xpath("//div[@id='layer_cart']//span[@title = 'Close window']");

    protected WebDriver webDriver;
    protected Actions actions;


    public HomePage(WebDriver webDriver, Actions actions){
       this.webDriver = webDriver;
       this.actions = actions;
    }

    /**
     * @param urlLocation url which we want to navigate to
     */
    public void navigate(String urlLocation) {
        webDriver.navigate().to(urlLocation);
    }

    public void navigateToHomePage() {
        webDriver.navigate().to(homePageUrl);
    }
    /**
     * This method navigates to women category from home page
     */
    public void navigateToWomenCategory() {
        try {
            WebElement womenCategory = webDriver.findElement(womenCategoryLocator);
            actions.moveToElement(womenCategory).perform();
            implicitlyWait(5, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    /**
     * This method navigates to dress type after falling menu is visible
     */
    public void navigateToDressType() {
        try {
            WebElement dressType = webDriver.findElement(dressTypeLocator);
            Assert.assertNotNull("Specified element {0} is empty", dressType);
            actions.moveToElement(dressType).click().perform();
            implicitlyWait(5, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    /**
     * @return initial Product name
     */
    public String getInitialProductName() {
        WebElement productNameElement = webDriver.findElement(initialProductNameLocator);
        return productNameElement.getText();
    }

    public void navigateToQuickView() {
        try {
            WebElement secondDress = webDriver.findElement(secondDressLocator);
            actions.moveToElement(secondDress).perform();

            WebElement quickView = webDriver.findElement(quickViewLocator);
            quickView.click();
            implicitlyWait(15, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    /**
     * This method navigates to Shopping cart
     */
    public void navigateToShoppingCart() {
        WebElement shoppingCart = webDriver.findElement(shoppingCartLocator);
        actions.moveToElement(shoppingCart).perform();
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        WebElement quantity = webDriver.findElement(quantityLocator);
        String quantityStr = quantity.getAttribute("innerHTML");
        return Integer.parseInt(quantityStr);
    }

    /**
     * @return product attributes such as color and size
     */
    public String getProductAttributes() {
        WebElement productAttributes = webDriver.findElement(productAttributesLocator);
        return productAttributes.getText();
    }

    /**
     * @return the shipping cost
     */
    public double getShippingCost() {
        WebElement shippingCostElement = webDriver.findElement(shippingCostLocator);
        return Double.parseDouble(shippingCostElement.getText().substring(1));
    }

    /**
     * @return Total sum of products
     */
    public double getTotalSum() {
        WebElement totalSumElement = webDriver.findElement(totalSumLocator);
        return Double.parseDouble(totalSumElement.getText().substring(1));
    }

    /**
     * @return Product name
     */
    public String getProductName() {
        WebElement name = webDriver.findElement(productNameLocator);
        return name.getAttribute("title");
    }

    public void implicitlyWait(long period, TimeUnit timeUnit) {
        webDriver.manage().timeouts().implicitlyWait(period, timeUnit);
    }

    public void clickCloseButton() {
        try {
            WebElement closeIcon = webDriver.findElement(closeButtonLocator);
            closeIcon.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("closeIcon not found", e);
        }
    }
}
