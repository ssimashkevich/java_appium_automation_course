package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            CLOSE_SINK_PROMO_BUTTON,
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON_TPL;

    public MyListsPageObject (RemoteWebDriver driver)
    {
        super(driver);
    }

    //TEMPLATES METHODS
    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveLocatorByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON_TPL.replace("{TITLE}", article_title);
    }
    //TEMPLATES METHODS

    public void closeSinkPromo()
    {
        this.waitForElementAndClick(
                CLOSE_SINK_PROMO_BUTTON,
                "Cannot find and click X button on sink promo"
        );
    }

    public void waitForMyListsFolderToAppearByName(String name_of_folder)
    {
        String folder_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                folder_xpath,
                "Cannot find folder by name '" + name_of_folder + "' in My Lists"
        );
    }

    public void openMyListsFolderByName (String name_of_folder)
    {
        this.waitForMyListsFolderToAppearByName(name_of_folder);
        String folder_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_xpath,
                "Cannot find and click folder by name '" + name_of_folder + "' in My Lists"
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title '" + article_title + "'",
                15
        );
    }

    public void clickByArticleWithTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot find and click saved article with title '" + article_title + "'",
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title '" + article_title + "'",
                15
        );
    }

    public void swipeByArticleToDelete(String article_title) throws Exception
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find and swipe saved article by title '" + article_title + "'"
            );
        } else {
            TimeUnit.SECONDS.sleep(2);
            String remove_locator = getRemoveLocatorByTitle(article_title);
            this.waitForElementAndClick(remove_locator,"Cannot find and click botton to remove article from saved", 10);
        }

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath,"Cannot find saved article and click to right upper corner to delete it");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }
}
