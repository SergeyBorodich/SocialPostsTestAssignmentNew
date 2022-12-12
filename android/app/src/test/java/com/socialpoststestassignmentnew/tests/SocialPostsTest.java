package com.socialpoststestassignmentnew.tests;

import com.socialpoststestassignmentnew.po.PostsListPage;
import com.socialpoststestassignmentnew.po.SettingsPage;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialPostsTest {

    public static AppiumDriver driver;

    protected SettingsPage settingsPage = new SettingsPage(driver);
    protected PostsListPage postsListPage = new PostsListPage(driver);

    protected final String validationMessage = "Value can't be blank or value is incorrect";

    @BeforeClass
    public static void setup() {

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "3600");
            capabilities.setCapability("appPackage", "com.socialpoststestassignmentnew");
            capabilities.setCapability("appActivity", "com.socialpoststestassignmentnew.MainActivity");
            capabilities.setCapability("appium:ensureWebviewsHavePages", true);
            capabilities.setCapability("appium:nativeWebScreenshot", true);
            capabilities.setCapability("appium:connectHardwareKeyboard", true);

            URL url = new URL("http://localhost:4724/wd/hub");

            driver = new AppiumDriver(url, capabilities);
            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Cause is: " +e.getCause());
            System.out.println("Message is: " +e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void resetValues() throws InterruptedException {

        final String mockUrl = "https://run.mocky.io/v3/01a05594-3f31-405e-bf81-edefc1624aad";
        final String defaultUrl = "https://run.mocky.io/v3/27a44f7d-67ca-4e96-b901-a159d315b922";

        settingsPage.setUrl(mockUrl);
        Assert.assertEquals(settingsPage.getCurrentUrl(), mockUrl);

        settingsPage.setNumberOfMessages("100");
        Assert.assertEquals(settingsPage.getNumberOfMessages(), "100");

        settingsPage.setInterval("60:00");
        Assert.assertEquals(settingsPage.getCurrentInterval(), "60:00");

        settingsPage.clickResetButton();
        Thread.sleep(3000);
        Assert.assertEquals(settingsPage.getCurrentUrl(), defaultUrl);
        Assert.assertEquals(settingsPage.getNumberOfMessages(), "10");
        Assert.assertEquals(settingsPage.getCurrentInterval(), "01:00");
    }

    @Test
    public void enterIncorrectUrl() throws InterruptedException {
        settingsPage.setUrl("https://Test");
        settingsPage.clickSaveButton();
        Thread.sleep(1000);
        Assert.assertEquals(settingsPage.getValidationMessageOfUrlField(), validationMessage);
    }

    @Test
    public void enterIncorrectNumberOfMessages() throws InterruptedException {
        settingsPage.setNumberOfMessages("0");
        settingsPage.clickSaveButton();
        Thread.sleep(1000);
        Assert.assertEquals(settingsPage.getValidationMessageOfMessagesNumberField(), validationMessage);
    }

    @Test
    public void enterIncorrectInterval() throws InterruptedException {
        settingsPage.setInterval("00:29");
        settingsPage.clickSaveButton();
        Thread.sleep(1000);
        Assert.assertEquals(settingsPage.getValidationMessageOfIntervalField(), validationMessage);
    }

    @Test
    public void setMinimalMessages() throws InterruptedException {
        final String mockUrl = "https://run.mocky.io/v3/27a44f7d-67ca-4e96-b901-a159d315b922";

        settingsPage.setUrl(mockUrl);
        Assert.assertEquals(settingsPage.getCurrentUrl(), mockUrl);

        settingsPage.setNumberOfMessages("1");
        Assert.assertEquals(settingsPage.getNumberOfMessages(), "1");

        settingsPage.setInterval("01:10");
        Assert.assertEquals(settingsPage.getCurrentInterval(), "01:10");

        settingsPage.clickSaveButton();
        Thread.sleep(7000);
        Assert.assertEquals(postsListPage.getPostsCount().toString(), "1");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
