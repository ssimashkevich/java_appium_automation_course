package lib;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase {

    protected RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception
    {
        System.setProperty("webdriver.chrome.driver", "/Users/ssimashkevich/IdeaProjects/java_appium_automation_course/driver/chromedriver");
        driver = Platform.getInstance().getDriver();
        this.openWikiWebPageForMobileWeb();
    }

    @After
    public void tearDown() {
        driver.quit();

    }

    protected void rotateScreenPortrait()
    {
        if (driver instanceof AppiumDriver) {
            AppiumDriver <?> driver = (AppiumDriver<?>) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void rotateScreenLandscape()
    {
        if (driver instanceof AppiumDriver) {
            AppiumDriver <?> driver = (AppiumDriver<?>) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void backgroundApp(int seconds)
    {
        if (driver instanceof AppiumDriver) {
            AppiumDriver <?> driver = (AppiumDriver<?>) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            System.out.println("Method backgroundApp(int seconds) does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void skipWelcomePageForIOSApp()
    {
        if(Platform.getInstance().isIOS()){
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }
    }

    protected void openWikiWebPageForMobileWeb()
    {
        if(Platform.getInstance().isMW()){
            driver.get("https://en.m.wikipedia.org");
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
