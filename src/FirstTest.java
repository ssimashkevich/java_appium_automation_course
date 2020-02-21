import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    public void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testMultiResultSearchCancel() //TODO refactoring Ex3 for Ex8
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input"
        );

        WebElement search_result_list = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results"
        );

        List<WebElement> Results = search_result_list.findElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );

        assertTrue("In search 1 result", Results.size()>1);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search"
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search results still present on page",
                5
        );
    }

    @Test
    public void testSearchResultsContainsSearchInput() //TODO refactoring Ex4 for Ex8
    {
        String searchInput = "Java";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchInput,
                "Cannot find search input"
        );

        List<WebElement> Results = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results"
        ).findElements(
                By.id("org.wikipedia:id/page_list_item_title")
        );

        searchInput = searchInput.toLowerCase();

        for (int i=0; i<Results.size(); i++) {
            String title = Results.get(i).getAttribute("text").toLowerCase();
            assertTrue(
                    "Search result №" + ++i + " didn't contains '" + searchInput + "'",
                    title.contains(searchInput)
            );
        }

    }

    @Test
    public void saveTwoArticlesToMyListAndDeleteOne () //TODO refactoring Ex5 for Ex8
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

        MainPageObject.waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(first_search_result_locator),
                "Cannot find '" + first_article_title + "' topic searching by '" + search_line + "'",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(article_options_locator),
                "Cannot find button to open article options",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find option to add article to reading list"
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay"
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input to set name of articles folder"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_favorites_folder,
                "Cannot put text into articles folder input"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(close_article_locator),
                "Cannot close article, cannot find X link"
        );

        MainPageObject.waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(second_search_result_locator),
                "Cannot find '" + second_article_title + "' topic searching by '" + search_line + "'",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(article_options_locator),
                "Cannot find button to open article options",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find option to add article to reading list"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_favorites_folder + "']"),
                "Cannot find created folder for saving second article"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(close_article_locator),
                "Cannot close article, cannot find X link"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_favorites_folder + "']"),
                "Cannot find created folder for open"
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + first_article_title + "']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='" + second_article_title + "']"),
                "Second article not present"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + second_article_title + "']"),
                "Second article not present"
        );

        String second_article_title_after_open = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        assertEquals(
                "Article title on favorites page didn't match with title on article page",
                second_article_title,
                second_article_title_after_open
        );
    }

    @Test
    public void assertTitle () //TODO refactoring Ex6 for Ex8
    {
        String main_screen_search_input_locator = "org.wikipedia:id/search_container";
        String search_screen_search_input_locator = "org.wikipedia:id/search_src_text";
        String search_line = "Java";
        String article_title = "Java (programming language)";
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article_title + "']";

        MainPageObject.waitForElementAndClick(
                By.id(main_screen_search_input_locator),
                "Cannot find Search Wikipedia input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id(search_screen_search_input_locator),
                search_line,
                "Cannot find search input"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(search_result_locator),
                "Cannot find '" + article_title + "' topic searching by '" + search_line + "'",
                15
        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
    }
}
