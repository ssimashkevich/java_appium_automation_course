import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Users\\SSimashkevich\\Desktop\\java_appium_automation_course\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Java",
                "Cannot find search input"
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search"
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X still present on page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle ()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testMultiResultSearchCancel()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input"
        );

        WebElement search_result_list = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results"
        );

        List<WebElement> Results = search_result_list.findElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );

        Assert.assertTrue("In search 1 result", Results.size()>1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search"
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search results still present on page",
                5
        );
    }

    @Test
    public void testSearchResultsContainsSearchInput()
    {
        String searchInput = "Java";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchInput,
                "Cannot find search input"
        );

        List<WebElement> Results = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results"
            ).findElements(
                By.id("org.wikipedia:id/page_list_item_title")
        );

        for (int i=0; i<Results.size(); i++) {
            String title = Results.get(i).getAttribute("text");
            Assert.assertTrue(
                    "Search result №" + ++i + " didn't contains '" + searchInput + "'",
                    title.contains(searchInput)
            );
        }

    }

    private  WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private  WebElement waitForElementPresent(By by, String error_message)
    {
        return  waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String element_expected_preset_text, String value, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message);
        String element_preset_text = element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected element preset text before type in it new value",
                element_expected_preset_text,
                element_preset_text
        );
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement waitForElementAndClear(By by, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message);
        element.clear();
        return element;
    }

        private  boolean waitForElementNotPresent (By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

}
