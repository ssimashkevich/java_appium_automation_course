package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListsPageObject (AppiumDriver driver)
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
    //TEMPLATES METHODS

    public void waitForMyListsFolderToAppearByName(String name_of_folder)
    {
        String folder_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                By.xpath(folder_xpath),
                "Cannot find folder by name '" + name_of_folder + "' in My Lists"
        );
    }

    public void openMyListsFolderByName (String name_of_folder)
    {
        this.waitForMyListsFolderToAppearByName(name_of_folder);
        String folder_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_xpath),
                "Cannot find and click folder by name '" + name_of_folder + "' in My Lists"
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title '" + article_title + "'",
                15
        );
    }

    public void clickByArticleWithTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot find and click saved article with title '" + article_title + "'",
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title '" + article_title + "'",
                15
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find and swipe saved article by title '" + article_title + "'"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }
}
