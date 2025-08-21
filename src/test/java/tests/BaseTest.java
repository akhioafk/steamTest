package tests;

import aquality.selenium.browser.AqualityServices;
import pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.SettingsTestData;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public abstract class BaseTest {
    protected final HomePage homePage = new HomePage();

    @BeforeMethod
    public void setup() {
        getBrowser().maximize();
        getBrowser().goTo(SettingsTestData.getEnvData().getHost());
    }

    @AfterMethod
    public void teardown() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
