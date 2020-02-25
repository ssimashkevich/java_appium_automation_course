package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList()
    {
        String search_line = "Java";
        String article_title_on_search_page = "Java (programming language)";
        String name_of_my_lists_folder = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(article_title_on_search_page);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyListThenItIsNoListsYet(name_of_my_lists_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openMyListsFolderByName(name_of_my_lists_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title_on_search_page);
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOne () //Ex5
    {
        String search_line = "Java";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Java";
        String name_of_my_lists_folder = "Java folder";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.clickByArticleWithSubstring(first_article_title);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyListThenItIsNoListsYet(name_of_my_lists_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.clickByArticleWithSubstring(second_article_title);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToExistMyList(name_of_my_lists_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openMyListsFolderByName(name_of_my_lists_folder);
        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        MyListsPageObject.waitForArticleToAppearByTitle(second_article_title);
        MyListsPageObject.clickByArticleWithTitle(second_article_title);

        String second_article_title_after_open = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title on favorites page didn't match with title on article page",
                second_article_title,
                second_article_title_after_open
        );
    }
}
