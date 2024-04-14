package org.ucollective.taf.handlers;

import org.springframework.stereotype.Component;

@Component
public class OHRMHandler extends AbstractHandler {
    public OHRMHandler(String environment, String browserType, String downloadPath, String url, String ohrmUserName, String ohrmpassword) {
        super(environment, browserType, downloadPath, url, ohrmUserName, ohrmpassword);
    }
}
