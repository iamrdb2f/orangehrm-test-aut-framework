package org.ucollective.taf.testcases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.ucollective.taf.config.SeleniumConfiguration;
import org.ucollective.taf.handlers.OHRMHandler;
import org.ucollective.taf.pages.ohrm.BaseClass;
import org.ucollective.taf.pages.ohrm.LoginPage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@ContextConfiguration(classes = SeleniumConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class AbstractTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected WebDriver driver;

    @Autowired
    protected OHRMHandler ohrmHandler;

    protected BaseClass baseClass;
    protected LoginPage loginPage;

    protected int index;

    @BeforeClass
    public void setup() {
        baseClass = new BaseClass(driver);
        baseClass.setup(ohrmHandler);
        loginPage = new LoginPage(baseClass);
        index = "test".equals(ohrmHandler.getEnvironment()) ? 6 : 5;
    }

    @BeforeMethod
    public void setDriver(ITestContext context) {
        context.setAttribute("driver", driver);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    @AfterClass
    public void tearDown(ITestContext context) {
        baseClass.tearDown();
    }

    protected Object[][] getJsonData(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode n1 = mapper.readTree(new File(path));
        return new Object[][]{{n1}};
    }

    @Step
    public synchronized void runContinue(ITestContext context, int zone, ObjectNode data) throws InterruptedException {
        loginPage.login(ohrmHandler.getOhrmUserName(), ohrmHandler.getOhrmpassword());
        Assert.assertEquals("Employee Management", driver.getTitle());
        logger.info("Logged in! :)");
    }

    @Step
    public synchronized void runInit(ITestContext context, String[] data, String productText) throws Exception {
        //Initialize transaction
        loginPage.login(ohrmHandler.getOhrmUserName(), ohrmHandler.getOhrmpassword());
        Assert.assertEquals("Employee Management", driver.getTitle());
        logger.info("Logged in! :)");
    }
}
