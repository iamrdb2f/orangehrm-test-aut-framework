package org.ucollective.taf.pages.ohrm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);
    private final BaseClass baseClass;

    // Constructor initializes the stat of the driver
    public LoginPage(BaseClass baseClass) {
        this.baseClass = baseClass;
        WebDriver driver = this.baseClass.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"txtUsername\"]")
    WebElement username;

    @FindBy(xpath = "//*[@id=\"txtPassword\"]")
    WebElement password;

    @FindBy(xpath = "//*[@id=\"btnLogin\"]")
    WebElement btnLogin;

    public void setUserName(String user) {
        LOG.info("Username element exists? " + baseClass.exists(username));
        baseClass.waitForElement(username);
        username.clear();
        username.sendKeys(user);
    }

    public void setPassword(String pass) {
        baseClass.waitForElement(password);
        password.clear();
        password.sendKeys(pass);
    }

    public void clickSubmit() {
        baseClass.waitForElement(btnLogin);
        btnLogin.click();
    }

    public void login(String user, String pass) {

        setUserName(user);
        LOG.info("Setting username...");
        setPassword(pass);
        LOG.info("Setting password...");
        clickSubmit();
    }
}
