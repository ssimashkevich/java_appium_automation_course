package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch(){
        String search_line = "Java";
        String article_title_on_search_page = "Java (programming language)";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForArticleWithSubstringInSearchResult(article_title_on_search_page);
    }

    @Test
    public void testCancelSearch()
    {
        String search_line = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchCancelButtonToAppear();

        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.clickSearchCancelButton();
        }

        SearchPageObject.clickSearchCancelButton();
        SearchPageObject.waitForSearchCancelButtonToDisappear();
    }

    @Test
    public void testMultiResultSearchCancel() //Ex3
    {
        String search_line = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();

        assertTrue(
                "In search only one result",
                SearchPageObject.getAmountOfFoundArticles()>1
        );

        SearchPageObject.waitForSearchCancelButtonToAppear();
        SearchPageObject.clickSearchCancelButton(); //click for clear search field
        SearchPageObject.waitForSearchResultsToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_line = "Linkin Park discography";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testSearchResultsContainsSearchInput()//Ex4
    {
        String search_line = "Java";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.assertAllVisibleSearchResultTitlesContainsSearchLine(search_line);
    }

    @Test
    public void testSearchResultsCheckTitleAndDescription()//Ex9
    {
        String search_line = "Java";

        String first_article_title = "Java";
        String first_article_description = "Island of Indonesia";

        String second_article_title = "Java (programming language)";
        String second_article_description = "Object-oriented programming language";

        String third_article_title = "JavaScript";
        String third_article_description = "Programming language";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.waitForElementByTitleAndDescription(first_article_title,first_article_description);
        SearchPageObject.waitForElementByTitleAndDescription(second_article_title,second_article_description);
        SearchPageObject.waitForElementByTitleAndDescription(third_article_title,third_article_description);
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        String search_line = "zxcdsa";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }
}
