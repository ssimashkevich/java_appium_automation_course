import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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
        
        driver.rotate(ScreenOrientation.PORTRAIT); //set portrait orientation before each test
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

        searchInput = searchInput.toLowerCase();
        
        for (int i=0; i<Results.size(); i++) {
            String title = Results.get(i).getAttribute("text").toLowerCase();
            Assert.assertTrue(
                    "Search result №" + ++i + " didn't contains '" + searchInput + "'",
                    title.contains(searchInput)
            );
        }

    }


    @Test
    public void testSwipeArticle ()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' article in search",
                15
        );

        swipeUpToElement(
                By.id("org.wikipedia:id/page_external_link"),
                "Cannot find the end of the article",
                20
        );
    }


    @Test
    public void saveFirstArticleToMyList ()
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

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input to set name of articles folder"
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder"
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Saved article still present",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        String search_line = "Linkin Park discography";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input"
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        String search_line = "zxcdsa";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input"
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";
        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We,ve found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearhResult()
    {
        try {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
        } finally {
            driver.rotate(ScreenOrientation.PORTRAIT); //set portrait orientation if only this test fails
        }
    }

    @Test
    public void testCheckSearchArticleInBackground()
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

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic after returning from background",
                15
        );
    }

    @Test
    public void saveTwoArticlesToMyListAndDeleteOne ()
    {
        String main_screen_search_input_locator = "org.wikipedia:id/search_container";
        String search_screen_search_input_locator = "org.wikipedia:id/search_src_text";
        String search_line = "Java";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Java";
        String first_search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_article_title + "']";
        String second_search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_article_title + "']";
        String article_options_locator = "//android.widget.ImageView[@content-desc='More options']";
        String add_to_reading_list_locator = "//*[@text='Add to reading list']";
        String close_article_locator = "//android.widget.ImageButton[@content-desc='Navigate up']";
        String name_of_favorites_folder = "Learning programming";

        waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath(first_search_result_locator),
                "Cannot find '" + first_article_title + "' topic searching by '" + search_line + "'",
                15
        );

        waitForElementAndClick(
                By.xpath(article_options_locator),
                "Cannot find button to open article options",
                15
        );

        waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find option to add article to reading list"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input to set name of articles folder"
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_favorites_folder,
                "Cannot put text into articles folder input"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button"
        );

        waitForElementAndClick(
                By.xpath(close_article_locator),
                "Cannot close article, cannot find X link"
        );

        waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath(second_search_result_locator),
                "Cannot find '" + second_article_title + "' topic searching by '" + search_line + "'",
                15
        );

        waitForElementAndClick(
                By.xpath(article_options_locator),
                "Cannot find button to open article options",
                15
        );

        waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find option to add article to reading list"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_favorites_folder + "']"),
                "Cannot find created folder for saving second article"
        );

        waitForElementAndClick(
                By.xpath(close_article_locator),
                "Cannot close article, cannot find X link"
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_favorites_folder + "']"),
                "Cannot find created folder for open"
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + first_article_title + "']"),
                "Cannot find saved article"
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + second_article_title + "']"),
                "Second article not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + second_article_title + "']"),
                "Second article not present"
        );

        String second_article_title_after_open = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Article title on favorites page didn't match with title on article page",
                second_article_title,
                second_article_title_after_open
        );
    }

    @Test
    public void assertTitle ()
    {
        String main_screen_search_input_locator = "org.wikipedia:id/search_container";
        String search_screen_search_input_locator = "org.wikipedia:id/search_src_text";
        String search_line = "Java";
        String article_title = "Java (programming language)";
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article_title + "']";

        waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        waitForElementAndClick(
                By.xpath(search_result_locator),
                "Cannot find '" + article_title + "' topic searching by '" + search_line + "'",
                15
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
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

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x,start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x,end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToElement (By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_massage)
    {
        WebElement element = waitForElementPresent(
                by,
                error_massage);

        TouchAction action = new TouchAction(driver);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        action
                .press(right_x,middle_y)
                .waitAction(300)
                .moveTo(left_x,middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private  void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_massage = "An element '" + by.toString() + "' supposed to be not present";
            throw  new AssertionError(default_massage + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return  element.getAttribute(attribute);
    }

    private void assertElementPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0){
            String default_massage = "An element '" + by.toString() + "' supposed to be present";
            throw  new AssertionError(default_massage + " " + error_message);
        }
    }
}
