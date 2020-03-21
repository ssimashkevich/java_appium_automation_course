package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:div.page-heading>h1";
        FOOTER_ELEMENT = "css:footer";
        ADD_TO_MY_LIST_BUTTON = "xpath://*[@role='button' and @title='Watch']";
        REMOVE_FROM_MY_LIST_BUTTON = "xpath://*[@role='button' and @title='Unwatch']";
    }

    public MWArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
