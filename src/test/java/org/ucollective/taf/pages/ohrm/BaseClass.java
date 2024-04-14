package org.ucollective.taf.pages.ohrm;

import org.openqa.selenium.WebDriver;
import org.ucollective.taf.handlers.AbstractHandler;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

//BASE CLASS IS A BASE FOR ALL OTHERS PAGES
public class BaseClass {

    private static final Logger LOG = LoggerFactory.getLogger(BaseClass.class);
    //Set variables
    protected WebDriver driver;

    protected String timeStamp;

    public BaseClass(WebDriver driver) {
        this.driver = driver;
    }

    public void setup(AbstractHandler handler) {
        LOG.info("Setting up driver for test...");
        driver.get(handler.getUrl());
        timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());
    }

    //This method closes all browser's tabs

    public void tearDown() {
        LOG.info("Closing driver...");
        driver.quit();
        LOG.info("Driver Closed.");
    }

    //This method returns the driver
    public WebDriver getDriver() {
        return driver;
    }

    //This method returns the timestamp
    public String getTimeStamp() {
        return this.timeStamp;
    }

    //Wait for element 20s
    public void waitForElement(WebElement findElement) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofMillis(900))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(ExpectedConditions.elementToBeClickable(findElement));
    }

    //Wait for element with a given timeout
    public void waitForElement(WebElement findElement, int timeout) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(ExpectedConditions.elementToBeClickable(findElement));
    }

    //Wait for loader to finish
    /**
     * Duration in seconds
     */
    public void waitSpinner(int duration) {
        LOG.info("Waiting for page to load...");
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(duration))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("inProgressPage")));
    }


    //Return true if the element (by xpath) is present on the page
    public boolean verifyElementPresent(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() != 0;
    }

    //Other way to check if WebElement exists
    public boolean exists(WebElement elem) {
        try {
            elem.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Get the xpath of a WebElement
    public String getXpath(WebElement elem) {
        if (exists(elem)) {
            String[] a = elem.toString().split("xpath:");
            return a[a.length - 1].substring(0, a[a.length - 1].length() - 1);
        }
        return null;
    }

    //Takes a screenshot
    public void captureScreen(WebDriver driver, String tname) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File target = new File(System.getProperty("user.dir") + "/screenshots/" + tname + ".png");
            FileUtils.copyFile(source, target);
            LOG.info("Screenshot taken");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
