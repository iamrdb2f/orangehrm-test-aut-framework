package org.ucollective.taf.utilities.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./target/extent-reports/extent-report.html");
        reporter.config().setReportName("Extent Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}