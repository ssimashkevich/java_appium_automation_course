package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container";
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_LIST_ELEMENT = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULT_ELEMENT_IN_LIST = "id:org.wikipedia:id/page_list_item_container";
        SEARCH_RESULT_ELEMENT_TITLE = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_RESULT_ELEMENT_DESCRIPTION = "id:org.wikipedia:id/page_list_item_description";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='" + SEARCH_RESULT_LIST_ELEMENT.substring(3) + "']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='" + SEARCH_RESULT_ELEMENT_IN_LIST.substring(3) + "' and .//*[@resource-id='" + SEARCH_RESULT_ELEMENT_TITLE.substring(3) + "' and @text='{TITLE}'] and .//*[@resource-id='" + SEARCH_RESULT_ELEMENT_DESCRIPTION.substring(3) + "' and @text='{DESCRIPTION}']]";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
    }

    public AndroidSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
