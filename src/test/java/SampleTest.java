import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class SampleTest {
    enum Platform {Android, IOS}

    Platform platform = Platform.Android;

    private AppiumDriver driver;
    private MobileObjects mobileObjects;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    public void setUp() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        if (platform == Platform.Android) {
            desiredCapabilities.setCapability("platformName", "android");
            desiredCapabilities.setCapability("appium:deviceName", "device");
            desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
            desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
            driver = new AndroidDriver(getUrl(), desiredCapabilities);

        } else if (platform == Platform.IOS) {
            desiredCapabilities.setCapability("platformName", "");
            desiredCapabilities.setCapability("appium:deviceName", "");
            desiredCapabilities.setCapability("appium:bundleId", "");
            desiredCapabilities.setCapability("appium:appActivity", "");
            desiredCapabilities.setCapability("appium:automationName", "");
            driver = new IOSDriver<>(getUrl(), desiredCapabilities);

        }
        mobileObjects = new MobileObjects(driver);


    }


    String homePageText = "Hello UiAutomator!";
    @Test
    public void emptyString() {
        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();
        mobileObjects.textToBeChanged.isDisplayed();
        Assertions.assertEquals(homePageText, mobileObjects.textToBeChanged.getText());
    }
    @Test
    public void sendingSpace() {
        mobileObjects.userInput.isDisplayed();
        mobileObjects.userInput.sendKeys(" ");
        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();
        mobileObjects.textToBeChanged.isDisplayed();
        Assertions.assertEquals(homePageText, mobileObjects.textToBeChanged.getText());
    }
    @Test
    public void newActivity() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        mobileObjects.userInput.isDisplayed();
        mobileObjects.userInput.sendKeys("hello");
        mobileObjects.buttonActivity.isDisplayed();
        mobileObjects.buttonActivity.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(mobileObjects.textActivity));
        mobileObjects.textActivity.isDisplayed();
        Assertions.assertEquals("hello", mobileObjects.textActivity.getText());
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}


