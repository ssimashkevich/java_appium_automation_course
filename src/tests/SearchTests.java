package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch(){
        String search_line = "Java";
        String article_title_on_search_page = "Java (programming language)";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForArticleWithSubstringInSearchResult(article_title_on_search_page);
    }

    @Test
    public void testCancelSearch()
    {
        String search_line = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchCancelButtonToAppear();
        SearchPageObject.clickSearchCancelButton(); //click for clear search field
        SearchPageObject.clickSearchCancelButton(); //click for close search screen
        SearchPageObject.waitForSearchCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_line = "Linkin Park discography";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        String search_line = "zxcdsa";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }
}
