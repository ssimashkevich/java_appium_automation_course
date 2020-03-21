package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class AutorizationPageObject extends MainPageObject {
    private static final String
        LOGIN_BUTTON = "xpath://body/div/div/a[text()='Log in']",
        LOGIN_INPUT = "css:input.loginText",
        PASSWORD_INPUT = "css:input.loginPassword",
        SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AutorizationPageObject (RemoteWebDriver driver) {super(driver);}

    public void clickAuthButton() throws Exception
    {
        TimeUnit.SECONDS.sleep(2);
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find autn button");
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button");
    }

    public void enterLoginData (String login, String password) throws Exception
    {
        TimeUnit.SECONDS.sleep(2);
        this.waitForElementPresent(LOGIN_INPUT,"Cannot find input field");
        this.waitForElementAndSendKeys(LOGIN_INPUT,login,"Cannot find login input field and put login in it");
        this.waitForElementAndSendKeys(PASSWORD_INPUT,password,"Cannot find password input field and put password in it");
    }

    public void submitForm()
    {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button");
    }
}
