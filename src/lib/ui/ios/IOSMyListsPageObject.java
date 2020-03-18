package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        CLOSE_SINK_PROMO_BUTTON = "id:Close";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
    }

    public IOSMyListsPageObject(AppiumDriver <?> driver)
    {
        super(driver);
    }
}
