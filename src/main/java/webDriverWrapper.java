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

    public webDriverWrapper(){
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        actions = new Actions(webDriver);
    }

    public void navigate(String urlLocation){
        webDriver.navigate().to(urlLocation);
    }

    public void implicitlyWait(long milliSeconds, TimeUnit timeUnit){
        webDriver.manage().timeouts().implicitlyWait(milliSeconds, timeUnit);
    }

    public void navigateToWomenCategory(){
        try {
            WebElement womenCategory = webDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Women')]"));
            actions.moveToElement(womenCategory).perform();
            implicitlyWait(20,TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    public void navigateToDressType(){
        try {
            WebElement dressType = webDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Summer Dresses')]"));
            Assert.assertNotNull("Specified element {0} is empty", dressType);
            actions.moveToElement(dressType).click().perform();
            implicitlyWait(20,TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    public String getInitialProductName(){
        WebElement productNameElement = webDriver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]//a[@class='product-name']"));
        return productNameElement.getText();
    }
    public void navigateToQuickView(){
        try {
            WebElement secondDress = webDriver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]"));
            actions.moveToElement(secondDress).perform();

            WebElement quickViewLocator = webDriver.findElement(By.xpath("(//a[@class='quick-view'])[2]"));
            quickViewLocator.click();

            implicitlyWait(15,TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    public void navigateToQuickViewIframe(){
        try {
            webDriver.switchTo().frame(webDriver.findElement(By.xpath("//*[@class='fancybox-iframe']")));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Iframe now found", e);
        }
    }

    public void selectQuantity(int expectedQuantity){
        try {
            WebElement quantity = webDriver.findElement(By.id("quantity_wanted"));
            quantity.clear();
            quantity.sendKeys(Integer.toString(expectedQuantity));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }
    }

    public void selectDressSize(String expectedSize){
        try {
            Select sizeDropdown = new Select(webDriver.findElement(By.id("group_1")));
            sizeDropdown.selectByVisibleText(expectedSize);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }
    }

    public void selectDressColor(String expectedColor){
        try {
            WebElement colorSelection = webDriver.findElement(By.xpath("//a[@title='" + expectedColor + "']"));
            colorSelection.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }
    }

    public void clickSubmitButton(){
        try {
            WebElement submitButton = webDriver.findElement(By.xpath("//p[@id='add_to_cart']/button"));
            submitButton.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("submitButton not found", e);
        }
    }

    public void returnDefaultContent(){
        webDriver.switchTo().defaultContent();
    }

    public void clickCloseButton(){
        try {
            WebElement closeIcon = webDriver.findElement(By.xpath("//div[@id='layer_cart']//span[@title = 'Close window']"));
            closeIcon.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("closeIcon not found", e);
        }
    }

    public double getPrice(){
        WebElement priceElement = webDriver.findElement(By.xpath("//span[@id='our_price_display']"));
        return Double.parseDouble(priceElement.getText().substring(1));
    }

    public void navigateToShoppingCart(){
        WebElement shoppingCart = webDriver.findElement(By.xpath("//a[@title='View my shopping cart']"));
        actions.moveToElement(shoppingCart).perform();
    }
    public int getQuantity(){
        WebElement quantity = webDriver.findElement(By.xpath("//span[@class='quantity']"));
        String quantityStr = quantity.getAttribute("innerHTML");
        return Integer.parseInt(quantityStr);
    }

    public String getProductAttributes(){
        WebElement productAttributes = webDriver.findElement(By.xpath("//div[@class='product-atributes']/a"));
        return productAttributes.getText();
    }

    public double getShippingCost(){
        WebElement shippingCostElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]"));
        return Double.parseDouble(shippingCostElement.getText().substring(1));
    }

    public double getTotalSum(){
        WebElement totalSumElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]"));
        return Double.parseDouble(totalSumElement.getText().substring(1));
    }

    public String getProductName(){
        WebElement name = webDriver.findElement(By.xpath("//a[@class='cart_block_product_name']"));
        return name.getAttribute("title");
    }

    public void destroyWebDriver(){
        webDriver.quit();
    }
}

