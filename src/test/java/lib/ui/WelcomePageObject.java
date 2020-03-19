package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject
{
    private static final String
            ONBOARDING_STEP_ONE = "id:The free encyclopedia",
            ONBOARDING_STEP_TWO = "id:New ways to explore",
            ONBOARDING_STEP_THREE = "id:Search in nearly 300 languages",
            ONBOARDING_STEP_FOUR = "id:Help make the app better",
            NEXT_BUTTON = "xpath://XCUIElementTypeButton[@name='Next']",
            GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name='Get started']",
            SKIP_BUTTON = "xpath:////XCUIElementTypeButton[@name='Skip']";

    public WelcomePageObject(AppiumDriver <?> driver)
    {
        super(driver);
    }

    public void waitForOpenOnboardingStepOne()
    {
        this.waitForElementPresent(ONBOARDING_STEP_ONE,"Cannot find 'The free encyclopedia' title");
    }

    public void waitForOpenOnboardingStepTwo()
    {
        this.waitForElementPresent(ONBOARDING_STEP_TWO,"Cannot find 'New ways to explore' title");
    }

    public void waitForOpenOnboardingStepThree()
    {
        this.waitForElementPresent(ONBOARDING_STEP_THREE,"Cannot find 'Search in nearly 300 languages' title");
    }

    public void waitForOpenOnboardingStepFour()
    {
        this.waitForElementPresent(ONBOARDING_STEP_FOUR,"Cannot find 'Help make the app better' title");
    }

    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_BUTTON,"Cannot find and click 'Next' button");
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON,"Cannot find and click 'Get started' button");
    }

    public void clickSkip ()
    {
        this.waitForElementAndClick(SKIP_BUTTON,"Cannot find and click 'Skip' button");
    }
}
