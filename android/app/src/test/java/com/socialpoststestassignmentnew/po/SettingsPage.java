package com.socialpoststestassignmentnew.po;

import static com.socialpoststestassignmentnew.tests.SocialPostsTest.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingsPage {

    public SettingsPage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private final By urlField = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.EditText");
    private final By urlFieldDescription = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
            "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[1]");
    private final By messagesNumberField = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
            "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/" +
            "android.widget.EditText");
    private final By messagesNumberFieldDescription = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
            "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[2]");
    private final By intervalField = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
            "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/" +
            "android.widget.EditText");
    private final By intervalFieldDescription = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
            "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[3]");
    private final By saveButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[5]");
    private final By resetButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/" +
            "android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[6]");

    public void setUrl(String url) {

        driver.findElement(urlField).clear();
        driver.findElement(urlField).sendKeys(url);
    }

    public String getCurrentUrl() {
        return driver.findElement(urlField).getText();
    }

    public void setNumberOfMessages(String messages) {
        driver.findElement(messagesNumberField).clear();
        driver.findElement(messagesNumberField).sendKeys(messages);
    }

    public String getNumberOfMessages() {
        return driver.findElement(messagesNumberField).getText();
    }

    public void setInterval(String interval) {
        driver.findElement(intervalField).clear();
        driver.findElement(intervalField).sendKeys(interval);
    }

    public String getCurrentInterval() {
        return driver.findElement(intervalField).getText();
    }

    public void clickSaveButton() {
        driver.findElement(saveButton).click();
    }

    public void clickResetButton() {
        driver.findElement(resetButton).click();
    }

    public String getValidationMessageOfUrlField() {
        return driver.findElement(urlFieldDescription).getText();
    }

    public String getValidationMessageOfMessagesNumberField() {
        return driver.findElement(messagesNumberFieldDescription).getText();
    }

    public String getValidationMessageOfIntervalField() {
        return driver.findElement(intervalFieldDescription).getText();
    }
}
