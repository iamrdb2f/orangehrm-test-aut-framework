package org.ucollective.taf.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AjaxFileDownloader {

    private WebDriver driver;

    public AjaxFileDownloader(WebDriver driverObject) {
        this.driver = driverObject;
    }

    public InputStream download(String url) throws IOException {
        String script = "var url = arguments[0];" +
                "var callback = arguments[arguments.length - 1];" +
                "var xhr = new XMLHttpRequest();" +
                "xhr.open('GET', url, true);" +
                "xhr.responseType = \"arraybuffer\";" + //force the HTTP response, response-type header to be array buffer
                "xhr.onload = function() {" +
                "  var arrayBuffer = xhr.response;" +
                "  var byteArray = new Uint8Array(arrayBuffer);" +
                "  callback(byteArray);" +
                "};" +
                "xhr.send();";
        Object response = ((JavascriptExecutor) driver).executeAsyncScript(script, url);
        // Selenium returns an Array of Long, we need byte[]
        ArrayList<Long> byteList = (ArrayList<Long>) response;
        byte[] bytes = new byte[byteList.size()];
        for(int i = 0; i < byteList.size(); i++) {
            bytes[i] = (byte)(long)byteList.get(i);
        }
        return new ByteArrayInputStream(bytes);
    }

}