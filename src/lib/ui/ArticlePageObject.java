package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "org.wikipedia:id/page_external_link",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            MY_LIST_EXIST_FOLDER_TPL = "//*[@text='{NAME_OF_MY_LIST_FOLDER}']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    //TEMPLATES METHODS
    private static String getMyListExistFolderElement(String name_of_my_list_folder)
    {
        return MY_LIST_EXIST_FOLDER_TPL.replace("{NAME_OF_MY_LIST_FOLDER}",name_of_my_list_folder);
    }
    //TEMPLATES METHODS

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(By.id(TITLE),"Cannot find article title on page", 15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void assertArticleTitle()
    {
        this.assertElementPresent(
                By.id(TITLE),
                "Cannot find article title"
        );
    }

    public void swipeToFooter()
    {
        this.swipeUpToElement(
                By.id(FOOTER_ELEMENT),
                "Cannot find the end of the article",
                20
        );
    }

    public void clickByOptionsButton()
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options"
        );
    }

    public void clickByAddToMyListButtonInOptions()
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list"
        );
    }

    public void clickByAddToMyListButtonWithFolderName(String folder_name)
    {
        String add_to_my_list_xpath = getMyListExistFolderElement(folder_name);
        this.waitForElementAndClick(
                By.xpath(add_to_my_list_xpath),
                "Cannot find created folder '" + folder_name + "' for saving article"
        );
    }

    public void addArticleToMyListThenItIsNoListsYet(String name_of_favorites_folder)
    {
        clickByOptionsButton();
        clickByAddToMyListButtonInOptions();

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay"
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot input to set name of articles folder"
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_favorites_folder,
                "Cannot put text into articles folder input"
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button"
        );
    }

    public void addArticleToExistMyList(String name_of_folder)
    {
        clickByOptionsButton();
        clickByAddToMyListButtonInOptions();
        clickByAddToMyListButtonWithFolderName(name_of_folder);
    }

    public void closeArticle()
    {
        this.waitForElementPresent(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find close article link, cannot find X link");
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find and click X link"
        );
    }
}
