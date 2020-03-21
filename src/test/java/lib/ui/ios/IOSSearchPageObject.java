package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:Search Wikipedia";
        SEARCH_INPUT = "id:Search Wikipedia";
        SEARCH_CANCEL_BUTTON = "xpath://XCUIElementTypeButton[@name='Cancel']";
        SEARCH_CLEAR_BUTTON = "id:Clear text";
        SEARCH_RESULT_LIST_ELEMENT = "xpath://XCUIElementTypeOther/XCUIElementTypeCollectionView";
        SEARCH_RESULT_ELEMENT_IN_LIST = "xpath://XCUIElementTypeCell";
        SEARCH_RESULT_ELEMENT_TITLE = "xpath://XCUIElementTypeLink";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@type='" + SEARCH_RESULT_ELEMENT_TITLE.substring(8) + "' and contains(@name,'{TITLE}') and contains(@name,'{DESCRIPTION}')]";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    }

    public IOSSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
