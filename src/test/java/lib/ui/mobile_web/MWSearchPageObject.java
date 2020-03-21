package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_CLEAR_BUTTON = "css:button.clear";
        SEARCH_RESULT_LIST_ELEMENT = "css:div.results-list-container";
        SEARCH_RESULT_ELEMENT_IN_LIST = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_ELEMENT_TITLE = "css:a.title";
        SEARCH_RESULT_ELEMENT_DESCRIPTION = "css:div.wikidata-description";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[contains(@class,'page-summary')]//*[contains(text(),'{SUBSTRING}') or contains(@data-title,'{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
    }

    public MWSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
