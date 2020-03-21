package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            ADD_TO_MY_LIST_BUTTON,
            REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            MY_LIST_EXIST_FOLDER_TPL,
            CLOSE_ARTICLE_BUTTON;

    //TEMPLATES METHODS
    private static String getMyListExistFolderElement(String name_of_my_list_folder)
    {
        return MY_LIST_EXIST_FOLDER_TPL.replace("{NAME_OF_MY_LIST_FOLDER}",name_of_my_list_folder);
    }
    //TEMPLATES METHODS

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE,"Cannot find article title on page", 15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        if(Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if(Platform.getInstance().isIOS()){
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    public void assertArticleTitle()
    {
        this.assertElementPresent(
                TITLE,
                "Cannot find article title"
        );
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        } else if(Platform.getInstance().isIOS()){
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisable(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        }
    }

    public void clickByOptionsButton()
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options"
        );
    }

    public void clickByAddToMyListButton()
    {
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list"
        );
    }

    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(REMOVE_FROM_MY_LIST_BUTTON)){
            this.waitForElementAndClick(REMOVE_FROM_MY_LIST_BUTTON,"Cannot click button to remove article from saved");
        }
    }

    public void clickByAddToMyListButtonWithFolderName(String folder_name)
    {
        String add_to_my_list_xpath = getMyListExistFolderElement(folder_name);
        this.waitForElementAndClick(
                add_to_my_list_xpath,
                "Cannot find created folder '" + folder_name + "' for saving article"
        );
    }

    public void addArticleToMyListThenItIsNoListsYet(String name_of_favorites_folder)
    {
        clickByOptionsButton();
        clickByAddToMyListButton();

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay"
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot input to set name of articles folder"
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_favorites_folder,
                "Cannot put text into articles folder input"
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button"
        );
    }

    public void addArticleToExistMyList(String name_of_folder)
    {
        clickByOptionsButton();
        clickByAddToMyListButton();
        clickByAddToMyListButtonWithFolderName(name_of_folder);
    }

    public void addArticleToMySaved() throws Exception
    {
        if(Platform.getInstance().isMW()){
            removeArticleFromSavedIfItAdded();
        }
        TimeUnit.SECONDS.sleep(2);
        clickByAddToMyListButton();
    }

    public void closeArticle()
    {
        if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementPresent(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot find close article link, cannot find X link");
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find and click X link"
            );
        }else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
