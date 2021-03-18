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
    public String getQuantity(){
        WebElement quantity = webDriver.findElement(By.xpath("//span[@class='quantity']"));
        return quantity.getText();
    }

    public String getProductAttributes(){
        WebElement productAttributes = webDriver.findElement(By.xpath("//div[@class='product-atributes']/a"));
        return productAttributes.getText();
    }

    public String getShippingCost(){
        WebElement shippingCostElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]"));
        return shippingCostElement.getText().substring(1);
    }

    public String getTotalSum(){
        WebElement totalSumElement = webDriver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]"));
        return totalSumElement.getText().substring(1);
    }

    public String getProductName(){
        WebElement name = webDriver.findElement(By.xpath("//a[@class='cart_block_product_name']"));
        return name.getAttribute("title");
    }

    public void destroyWebDriver(){
        webDriver.quit();
    }

    public static void main(String[] args) {

        int expectedQuantity = 3;
        String expectedSize = "M";
        String expectedColor = "White";
        String expectedProductName = "";
        String googleURL = "http://google.com";
        String shopURL = "http://automationpractice.com";
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to(googleURL);

        // add wait for page load
        driver.navigate().to(shopURL);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
        // add wait for page load

        Actions testAction = new Actions(driver);
        try {
            WebElement womenCategory = driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Women')]"));
            Assert.assertNotNull("Specified element {0} is empty", womenCategory);
            testAction.moveToElement(womenCategory).perform();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
        try {
            WebElement dressType = driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]//a[contains(text(), 'Summer Dresses')]"));
            Assert.assertNotNull("Specified element {0} is empty", dressType);
            testAction.moveToElement(dressType).click().perform();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }


        try {
            WebElement productNameElement = driver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]//a[@class='product-name']"));
            Assert.assertEquals(expectedProductName, "");
            expectedProductName = productNameElement.getText();
            Assert.assertTrue(expectedProductName.length() > 0);
            WebElement secondDress = driver.findElement(By.xpath("//ul[contains(@class,'product_list')]/li[2]"));
            testAction.moveToElement(secondDress).perform();

            WebElement quickViewLocator = driver.findElement(By.xpath("(//a[@class='quick-view'])[2]"));
            quickViewLocator.click();

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }

        try {
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@class='fancybox-iframe']")));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Iframe now found", e);
        }

        try {
            WebElement quantity = driver.findElement(By.id("quantity_wanted"));
            quantity.clear();
            quantity.sendKeys(Integer.toString(expectedQuantity));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Clear message of failure", e);
        }

        try {
            Select sizeDropdown = new Select(driver.findElement(By.id("group_1")));
            sizeDropdown.selectByVisibleText(expectedSize);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }

        try {
            WebElement colorSelection = driver.findElement(By.xpath("//a[@title='" + expectedColor + "']"));
            colorSelection.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("Select not found", e);
        }

        WebElement priceElement = driver.findElement(By.xpath("//span[@id='our_price_display']"));
        double price = Double.parseDouble(priceElement.getText().substring(1));

        try {
            WebElement submitButton = driver.findElement(By.xpath("//p[@id='add_to_cart']/button"));
            submitButton.click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (NoSuchElementException e) {
            throw new AssertionError("submitButton not found", e);
        }
//TODO conbine actions in Iframe???
        driver.switchTo().defaultContent();

        try {
            WebElement closeIcon = driver.findElement(By.xpath("//div[@id='layer_cart']//span[@title = 'Close window']"));
            closeIcon.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("closeIcon not found", e);
        }

        try {
            WebElement shoppingCart = driver.findElement(By.xpath("//a[@title='View my shopping cart']"));
            testAction.moveToElement(shoppingCart).perform();
            WebElement quantity = driver.findElement(By.xpath("//span[@class='quantity']"));
            Assert.assertEquals(Integer.toString(expectedQuantity), quantity.getText());

            WebElement productAttributes = driver.findElement(By.xpath("//div[@class='product-atributes']/a"));
            String color = productAttributes.getText().split(", ")[0];
            Assert.assertEquals(expectedColor, color);
            String size = productAttributes.getText().split(", ")[1];
            Assert.assertEquals(expectedSize, size);

            WebElement shippingCostElement = driver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'first-line')]/span[1]"));
            double shippingCost = Double.parseDouble(shippingCostElement.getText().substring(1));
            WebElement totalSumElement = driver.findElement(By.xpath("//div[@class='cart-prices']/div[contains(@class, 'last-line')]/span[1]"));
            double totalSum = Double.parseDouble(totalSumElement.getText().substring(1));
            double expectedTotalSum = (price * expectedQuantity) + shippingCost;
            Assert.assertEquals(expectedTotalSum, totalSum, 0.0);
            WebElement name = driver.findElement(By.xpath("//a[@class='cart_block_product_name']"));
            Assert.assertEquals(expectedProductName, name.getText());
        } catch (NoSuchElementException e) {
            throw new AssertionError("shoppingCart not found", e);
        }


    }
}

