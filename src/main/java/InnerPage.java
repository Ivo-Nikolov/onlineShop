import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class InnerPage{
    private final By iFrameLocator = By.xpath("//*[@class='fancybox-iframe']");
    private final By quantityWantedLocator = By.id("quantity_wanted");
    private final By dressSizeLocator = By.id("group_1");
    private final By submitButtonLocator = By.xpath("//p[@id='add_to_cart']/button");
    private final By priceLocator = By.xpath("//span[@id='our_price_display']");

    protected WebDriver webDriver;
    protected Actions actions;

    public InnerPage(WebDriver webDriver, Actions actions){
        this.webDriver = webDriver;
        this.actions = actions;
    }

    /**
     * This method opens quick view iFrame
     */
    public void navigateToQuickViewIframe() {
        try {
            webDriver.switchTo().frame(webDriver.findElement(iFrameLocator));
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
            WebElement quantity = webDriver.findElement(quantityWantedLocator);
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
            Select sizeDropdown = new Select(webDriver.findElement(dressSizeLocator));
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
            WebElement submitButton = webDriver.findElement(submitButtonLocator);
            submitButton.click();
        } catch (NoSuchElementException e) {
            throw new AssertionError("submitButton not found", e);
        }
    }

    /**
     * @return the price of the dress
     */
    public double getPrice() {
        WebElement priceElement = webDriver.findElement(priceLocator);
        return Double.parseDouble(priceElement.getText().substring(1));
    }
    /**
     * This method clicks close button
     */

    public void implicitlyWait(long period, TimeUnit timeUnit) {
        webDriver.manage().timeouts().implicitlyWait(period, timeUnit);
    }
}

