package com.LibraryApp.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.LibraryApp.step_definitions",
        plugin = {
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "me.jvt.cucumber.report.PrettyReports:target/cucumber"
        },
        tags = "@wip",
        dryRun = false
)
public class CukesRunner {
}
