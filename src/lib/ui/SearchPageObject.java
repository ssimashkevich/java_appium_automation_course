package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container",
            SEARCH_INPUT = "id:org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_LIST_ELEMENT = "id:org.wikipedia:id/search_results_list",
            SEARCH_RESULT_ELEMENT_IN_LIST = "id:org.wikipedia:id/page_list_item_container",
            SEARCH_RESULT_ELEMENT_TITLE = "id:org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_ELEMENT_DESCRIPTION = "id:org.wikipedia:id/page_list_item_description",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='" + SEARCH_RESULT_LIST_ELEMENT.substring(3) + "']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='" + SEARCH_RESULT_ELEMENT_IN_LIST.substring(3) + "' and .//*[@resource-id='" + SEARCH_RESULT_ELEMENT_TITLE.substring(3) + "' and @text='{TITLE}'] and .//*[@resource-id='" + SEARCH_RESULT_ELEMENT_DESCRIPTION.substring(3) + "' and @text='{DESCRIPTION}']]",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //TEMPLATES METHODS
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }

    private static String getResultSearchElement(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}",title).replace("{DESCRIPTION}",description);
    }
    //TEMPLATES METHODS

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,"Cannot find and click search init element");
        this.waitForElementPresent(SEARCH_INPUT,"Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line,"Cannot find and type into search input");
    }

    public void waitForSearchCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button");
    }

    public void clickSearchCancelButton()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button");
    }

    public void waitForSearchCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void waitForSearchResultsToAppear ()
    {
        this.waitForElementPresent(SEARCH_RESULT_LIST_ELEMENT, "Cannot find search results by the request", 15);
    }

    private WebElement getSearchResultsListElement()
    {
        return this.waitForElementPresent(SEARCH_RESULT_LIST_ELEMENT, "Cannot find search results by the request", 15);
    }

    public void waitForSearchResultsToDisappear ()
    {
        this.waitForElementNotPresent(SEARCH_RESULT_LIST_ELEMENT, "Search results still present on page", 15);
    }

    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT_IN_LIST, "We supposed not to find any results");
    }

    public int getAmountOfFoundArticles()
    {
        WebElement search_results_list = this.getSearchResultsListElement();

        return this.getAmountOfElements(
                search_results_list,
                SEARCH_RESULT_ELEMENT_IN_LIST
        );
    }

    public void waitForArticleWithSubstringInSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,"Cannot find search result with substring " + substring, 15);
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElement(title,description);
        this.waitForElementPresent(search_result_xpath,"Cannot find search result with title '" + title +"' and description '"+ description +"'", 15);
    }

    public void  clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,"Cannot find and click search result with substring " + substring, 15);
    }

    public void assertSearchResultTitleContainsSearchLine(WebElement element, String search_line)
    {
        search_line = search_line.toLowerCase();

        String title = this.getListOfChildesInElement(element,SEARCH_RESULT_ELEMENT_TITLE).get(0).getAttribute("text").toLowerCase();

        assertTrue(
                    "Search result title '" + title + "' should contains '" + search_line + "'",
                    title.contains(search_line)
        );
    }

    public void assertAllVisibleSearchResultTitlesContainsSearchLine(String search_line)
    {
        List<WebElement> results = this.getListOfChildesInElement(getSearchResultsListElement(),SEARCH_RESULT_ELEMENT_IN_LIST);

        for (WebElement result : results) {
            if (!this.getListOfChildesInElement(result,SEARCH_RESULT_ELEMENT_TITLE).isEmpty())
                this.assertSearchResultTitleContainsSearchLine(result, search_line);
        }
    }
}
