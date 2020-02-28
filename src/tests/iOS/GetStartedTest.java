package tests.iOS;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testPassThroughWelcome()
    {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForOpenOnboardingStepOne();
        WelcomePage.clickNextButton();

        WelcomePage.waitForOpenOnboardingStepTwo();
        WelcomePage.clickNextButton();

        WelcomePage.waitForOpenOnboardingStepThree();
        WelcomePage.clickNextButton();

        WelcomePage.waitForOpenOnboardingStepFour();
        WelcomePage.clickGetStartedButton();
    }
}
