package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:Search Wikipedia";
        SEARCH_INPUT = "id:Search Wikipedia";
        SEARCH_CANCEL_BUTTON = "xpath://XCUIElementTypeButton[@name='Cancel']";
        SEARCH_RESULT_LIST_ELEMENT = "xpath://XCUIElementTypeOther/XCUIElementTypeCollectionView";
        SEARCH_RESULT_ELEMENT_IN_LIST = "xpath://XCUIElementTypeCell";
        SEARCH_RESULT_ELEMENT_TITLE = "xpath://XCUIElementTypeLink";
        SEARCH_RESULT_ELEMENT_DESCRIPTION = "xpath://XCUIElementTypeLink";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@type='" + SEARCH_RESULT_ELEMENT_IN_LIST.substring(6) + "' and .//*[@type='XCUIElementTypeLink' and contains(@name='{TITLE}')] and .//*[@type='XCUIElementTypeLink' and contains(@name={DESCRIPTION}')]]";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    }

    public IOSSearchPageObject(AppiumDriver <?> driver)
    {
        super(driver);
    }
}
