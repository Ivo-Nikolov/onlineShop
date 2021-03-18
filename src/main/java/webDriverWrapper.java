import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;


import org.junit.Assert;


import java.util.concurrent.TimeUnit;

public class webDriverWrapper {

    private WebDriver webDriver;
    private Actions actions;

    public webDriverWrapper() {
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        actions = new Actions(webDriver);
    }

    /**
     * @param urlLocation url which we want to navigate to
     */
    public void navigate(String urlLocation) {
        webDriver.navigate().to(urlLocation);
    }

    /**
     * This Method add implicit wait
     *
     * @param milliSeconds long number specifying how much to wait
     * @param timeUnit     type of wait eg. MILLISECONDS, SECONDS  etc.
     */
    public void implicitlyWait(long milliSeconds, TimeUnit timeUnit) {
        webDriver.manage().timeouts().implicitlyWait(milliSeconds, timeUnit);
    }

    /**
     * This method navigates to women category from home page
     */
    public void navigateToWomenCategory() {
        try {
            WebElement womenCategory = webDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Women')]"));
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
            WebElement dressType = webDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Summer Dresses')]"));
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
        WebElement productNameElement = webDriver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]//a[@class='product-name']"));
        return productNameElement.getText();
    }

    /**
     * This method navigate to quick view
     */
    public void navigateToQuickView() {
        try {
            WebElement secondDress = webDriver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]"));
            actions.moveToElement(secondDress).perform();

            WebElement quickViewLocator = webDriver.findElement(By.xpath("(//a[@class='quick-view'])[2]"));
            quickViewLocator.click();

            implicitlyWait(15, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    /**
     * This method opens quick view iFrame
     */
    public void navigateToQuickViewIframe() {
        try {
            webDriver.switchTo().frame(webDriver.findElement(By.xpath("//*[@class='fancybox-iframe']")));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Iframe now found", e);
        }
    }

    /**
     * This method populates dress quantity
     *
     * @param expectedQuantity requires quantity integer number
     */
    public void selectQuantity(int expectedQuantity) {
        try {
            WebElement quantity = webDriver.findElement(By.id("quantity_wanted"));
            quantity.clear();
            quantity.sendKeys(Integer.toString(expectedQuantity));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    /**
     * This method populates dress size
     *
     * @param expectedSize requires character for size eg S, M, L etc
     */
    public void selectDressSize(String expectedSize) {
        try {
            Select sizeDropdown = new Select(webDriver.findElement(By.id("group_1")));
            sizeDropdown.selectByVisibleText(expectedSize.toUpperCase());
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }
    }

    /**
     * This method populates dress color
     *
     * @param expectedColor requires color name eg. White, Yellow etc.
     */
    public void selectDressColor(String expectedColor) {
        try {
            WebElement colorSelection = webDriver.findElement(By.xpath("//a[@title='" + expectedColor + "']"));
            colorSelection.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }
    }

    /**
     * This method clicks Submit button
     */
    public void clickSubmitButton() {
        try {
            WebElement submitButton = webDriver.findElement(By.xpath("//p[@id='add_to_cart']/button"));
            submitButton.click();
            implicitlyWait(10, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("submitButton not found", e);
        }
    }

    /**
     * This method return to default page
     */
    public void returnDefaultContent() {
        webDriver.switchTo().defaultContent();
    }

    /**
     * This method clicks close button
     */
    public void clickCloseButton() {
        try {
            WebElement closeIcon = webDriver.findElement(By.xpath("//div[@id='layer_cart']//span[@title = 'Close window']"));
            closeIcon.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("closeIcon not found", e);
        }
    }

    /**
     * @return the price of the dress
     */
    public double getPrice() {
        WebElement priceElement = webDriver.findElement(By.xpath("//span[@id='our_price_display']"));
        return Double.parseDouble(priceElement.getText().substring(1));
    }

    /**
     * This method navigates to Shopping cart
     */
    public void navigateToShoppingCart() {
        WebElement shoppingCart = webDriver.findElement(By.xpath("//a[@title='View my shopping cart']"));
        actions.moveToElement(shoppingCart).perform();
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        WebElement quantity = webDriver.findElement(By.xpath("//span[@class='quantity']"));
        String quantityStr = quantity.getAttribute("innerHTML");
        return Integer.parseInt(quantityStr);
    }

    /**
     * @return product attributes such as color and size
     */
    public String getProductAttributes() {
        WebElement productAttributes = webDriver.findElement(By.xpath("//div[@class='product-atributes']/a"));
        return productAttributes.getText();
    }

    /**
     * @return the shipping cost
     */
    public double getShippingCost() {
        WebElement shippingCostElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]"));
        return Double.parseDouble(shippingCostElement.getText().substring(1));
    }

    /**
     * @return Total sum of products
     */
    public double getTotalSum() {
        WebElement totalSumElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]"));
        return Double.parseDouble(totalSumElement.getText().substring(1));
    }

    /**
     * @return Product name
     */
    public String getProductName() {
        WebElement name = webDriver.findElement(By.xpath("//a[@class='cart_block_product_name']"));
        return name.getAttribute("title");
    }

    public void destroyWebDriver() {
        webDriver.quit();
    }
}

