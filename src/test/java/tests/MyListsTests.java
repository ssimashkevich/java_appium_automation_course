package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyListsTests extends CoreTestCase {

    @Before
    public void skipWelcome()
    {
        this.skipWelcomePageForIOSApp();
    }

    @Test
    public void testSaveFirstArticleToMyList()
    {
        String search_line = "Appium";
        String article_title_on_search_page = "Appium";
        String name_of_my_lists_folder = "Learning programming";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(article_title_on_search_page);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListThenItIsNoListsYet(name_of_my_lists_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickSearchCancelButton();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openMyListsFolderByName(name_of_my_lists_folder);
        } else {
            MyListsPageObject.closeSinkPromo();
        }
        MyListsPageObject.swipeByArticleToDelete(article_title_on_search_page);
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOne () //Ex5
    {
        String search_line = "Appium";
        String first_article_title = "Appium";
        String second_article_title = "AppImage";
        String name_of_my_lists_folder = "Favorites folder";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.clickByArticleWithSubstring(first_article_title);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListThenItIsNoListsYet(name_of_my_lists_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.initSearchInput();
            SearchPageObject.typeSearchLine(search_line);
        }

        SearchPageObject.waitForSearchResultsToAppear();
        SearchPageObject.clickByArticleWithSubstring(second_article_title);

        ArticlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistMyList(name_of_my_lists_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickSearchCancelButton();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openMyListsFolderByName(name_of_my_lists_folder);
        } else {
            MyListsPageObject.closeSinkPromo();
        }
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
