package org.ucollective.taf.handlers;

public class AbstractHandler {
    protected String environment;
    protected String browserType;
    protected String downloadPath;
    protected String url;
    protected String ohrmUserName;
    protected String ohrmpassword;

    public String getEnvironment() {
        return environment;
    }

    public String getBrowserType() {
        return browserType;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getUrl() {
        return url;
    }

    public String getOhrmUserName() {
        return ohrmUserName;
    }

    public String getOhrmpassword() {
        return ohrmpassword;
    }

    public AbstractHandler(String environment, String browserType, String downloadPath, String url, String ohrmUserName, String ohrmpassword) {
        this.environment = environment;
        this.browserType = browserType;
        this.downloadPath = downloadPath;
        this.url = url;
        this.ohrmUserName = ohrmUserName;
        this.ohrmpassword = ohrmpassword;
    }
}
