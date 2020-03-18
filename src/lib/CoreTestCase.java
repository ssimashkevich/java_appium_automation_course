package lib;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase {

    protected AppiumDriver <?> driver;

    @Before
    public void setUp() throws Exception
    {
        driver = Platform.getInstance().getDriver();
        this.skipWelcomePageForIOSApp();
    }

    @After
    public void tearDown() {
        driver.quit();

    }

    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private void skipWelcomePageForIOSApp()
    {
        if(Platform.getInstance().isIOS()){
            WelcomePageObject WelcomePageObject = new WelcomePageObject((driver));
            WelcomePageObject.clickSkip();
        }
    }
}
