package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        CLOSE_SINK_PROMO_BUTTON = "id:Close";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
    }

    public IOSMyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
