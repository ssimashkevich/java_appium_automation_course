package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.presenceOfElementLocated(getLocatorByString(locator))
        );
    }

    public  WebElement waitForElementPresent(String locator, String error_message)
    {
        return  waitForElementPresent(locator, error_message, 5);
    }

    private WebElement waitForElementClickable(String locator, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.elementToBeClickable(getLocatorByString(locator))
        );
    }

    private   WebElement waitForElementClickable(String locator, String error_message)
    {
        return  waitForElementClickable(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementClickable(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(String locator, String error_message)
    {
        WebElement element = waitForElementClickable(locator, error_message);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementClickable(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String element_expected_preset_text, String value, String error_message)
    {
        WebElement element = waitForElementClickable(locator, error_message);
        String element_preset_text = element.getAttribute("text");

        assertEquals(
                "We see unexpected element preset text before type in it new value",
                element_expected_preset_text,
                element_preset_text
        );
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message)
    {
        WebElement element = waitForElementClickable(locator, error_message);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementClickable(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public WebElement waitForElementAndClear(String locator, String error_message)
    {
        WebElement element = waitForElementClickable(locator, error_message);
        element.clear();
        return element;
    }

    public WebElement waitForElementToClearAndSendKeys(String locator, String value, String error_message)
    {
        WebElement element = waitForElementClickable(locator, error_message);
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public  boolean waitForElementNotPresent (String locator, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.invisibilityOfElementLocated(getLocatorByString(locator))
        );
    }

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(PointOption.point(x,start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x,end_y))
                .release()
                .perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToElement (String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        By by = getLocatorByString(locator);
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes){
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(String locator, String error_massage)
    {
        WebElement element = waitForElementPresent(locator, error_massage);

        TouchAction action = new TouchAction(driver);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        action
                .press(PointOption.point(right_x,middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(left_x,middle_y))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator)
    {
        List<WebElement> elements = driver.findElements(getLocatorByString(locator));
        return elements.size();
    }

    public int getAmountOfElements(WebElement element,String locator)
    {
        List<WebElement> elements = element.findElements(getLocatorByString(locator));
        return elements.size();
    }

    public List<WebElement> getListOfChildesInElement(WebElement element, String locator)
    {
        return element.findElements(getLocatorByString(locator));
    }

    public  void assertElementNotPresent(String locator, String error_message)
    {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0){
            String default_massage = "An element '" + locator + "' supposed to be not present";
            throw  new AssertionError(default_massage + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return  element.getAttribute(attribute);
    }

    public void assertElementPresent(String locator, String error_message)
    {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements == 0){
            String default_massage = "An element '" + locator + "' supposed to be present";
            throw  new AssertionError(default_massage + " " + error_message);
        }
    }

    private By getLocatorByString(String locator_with_type)
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        if (exploded_locator.length != 2){
            throw new IllegalArgumentException("Locator type for element '" + exploded_locator[0] + "' didn't set. Set locator by template: '{locator_type}:{locator}'");
        }
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")){
            return By.xpath(locator);
        }else if(by_type.equals("id")){
            return By.id(locator);
        }else{
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator);
        }
    }
}
