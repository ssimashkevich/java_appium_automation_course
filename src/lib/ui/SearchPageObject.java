package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_LIST_ELEMENT = "org.wikipedia:id/search_results_list",
            SEARCH_RESULT_ELEMENT_IN_LIST = "org.wikipedia:id/page_list_item_container",
            SEARCH_RESULT_ELEMENT_TITLE = "org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='" + SEARCH_RESULT_LIST_ELEMENT + "']//*[@resource-id='" + SEARCH_RESULT_ELEMENT_IN_LIST + "']",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='" + SEARCH_RESULT_LIST_ELEMENT + "']//*[@text='{SUBSTRING}']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //TEMPLATES METHODS
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    //TEMPLATES METHODS

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT),"Cannot find and click search init element");
        this.waitForElementPresent(By.id(SEARCH_INPUT),"Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.id(SEARCH_INPUT), search_line,"Cannot find and type into search input");
    }

    public void waitForSearchCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button");
    }

    public void clickSearchCancelButton()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button");
    }

    public void waitForSearchCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void waitForSearchResultsToAppear ()
    {
        this.waitForElementPresent(By.id(SEARCH_RESULT_LIST_ELEMENT), "Cannot find search results by the request", 15);
    }

    private WebElement getSearchResultsListElement()
    {
        return this.waitForElementPresent(By.id(SEARCH_RESULT_LIST_ELEMENT), "Cannot find search results by the request", 15);
    }

    public void waitForSearchResultsToDisappear ()
    {
        this.waitForElementNotPresent(By.id(SEARCH_RESULT_LIST_ELEMENT), "Search results still present on page", 15);
    }

    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }

    public int getAmountOfFoundArticles()
    {
        WebElement search_results_list = this.getSearchResultsListElement();

        return this.getAmountOfElements(
                search_results_list,
                By.id(SEARCH_RESULT_ELEMENT_IN_LIST)
        );
    }

    public void waitForArticleWithSubstringInSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with substring " + substring, 15);
    }

    public void  clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),"Cannot find and click search result with substring " + substring, 15);
    }

    public void assertSearchResultTitleContainsSearchLine(WebElement element, String search_line)
    {
        search_line = search_line.toLowerCase();

        String title = element.findElement(By.id(SEARCH_RESULT_ELEMENT_TITLE)).getAttribute("text").toLowerCase();

        assertTrue(
                    "Search result title '" + title + "' should contains '" + search_line + "'",
                    title.contains(search_line)
        );
    }

    public void assertAllVisibleSearchResultTitlesContainsSearchLine(String search_line)
    {
        List<WebElement> results = this.getSearchResultsListElement().findElements(By.id(SEARCH_RESULT_ELEMENT_IN_LIST));

        for (WebElement result : results) {
            if (!result.findElements(By.id(SEARCH_RESULT_ELEMENT_TITLE)).isEmpty())
                this.assertSearchResultTitleContainsSearchLine(result, search_line);
        }
    }
}
