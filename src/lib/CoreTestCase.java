package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase {

    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Before
    public void setUp() throws Exception
    {
        driver = getDriverByPlatformEnv(getCapabilitiesByPlatformEnv());
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

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(platform.equals(PLATFORM_ANDROID)){
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","emulator-5554");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("orientation","PORTRAIT");
            capabilities.setCapability("app","/Users/ssimashkevich/IdeaProjects/java_appium_automation_course/apks/org.wikipedia.apk");
        } else if(platform.equals(PLATFORM_IOS)){
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 8");
            capabilities.setCapability("platformVersion","13.3");
            capabilities.setCapability("orientation","PORTRAIT");
            capabilities.setCapability("app","/Users/ssimashkevich/IdeaProjects/java_appium_automation_course/apks/Wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(DesiredCapabilities capabilities) throws Exception
    {
        String platform = System.getenv("PLATFORM");
        AppiumDriver platform_driver;

        if(platform.equals(PLATFORM_ANDROID)){
            platform_driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        } else if(platform.equals(PLATFORM_IOS)){
            platform_driver = new IOSDriver(new URL(AppiumURL), capabilities);
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return platform_driver;
    }
}
