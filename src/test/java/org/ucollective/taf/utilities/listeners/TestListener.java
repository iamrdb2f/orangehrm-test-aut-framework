package org.ucollective.taf.utilities.listeners;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Objects;

import static org.ucollective.taf.utilities.extentreports.ExtentManager.getExtentReports;
import static org.ucollective.taf.utilities.extentreports.ExtentTestManager.getTest;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        byte[] bytes = new byte[0];
        if (Objects.nonNull(driver)) {
            bytes= ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return bytes;
    }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        getExtentReports().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        String methodId = iTestResult.getMethod().getId();
        String methodDescription = iTestResult.getMethod().getDescription();
        String description = methodId + "-" + methodDescription;

        getTest().log(Status.PASS, description);

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is failed.");
        logger.info(iTestResult.toString(), iTestResult);
        logger.info(iTestResult.getTestContext().toString());
        String methodId = iTestResult.getMethod().getId();
        String methodDescription = iTestResult.getMethod().getDescription();
        String description = methodId + "-" + methodDescription;
        WebDriver driver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");

        //TODO Screenshots references are wrong
        //Allure ScreenShotRobot and SaveTestLog
        if (driver != null) {
            System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
            saveScreenshotPNG(driver);
        }
        getTest().log(Status.FAIL, description);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}
