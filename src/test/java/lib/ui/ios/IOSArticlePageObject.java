package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "xpath://XCUIElementTypeOther[@name='banner']/XCUIElementTypeOther[@value='1']";
        FOOTER_ELEMENT = "id:view-in-browser-footer-link";
        ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }

    public IOSArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
