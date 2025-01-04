package com.LibraryApp.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Driver utility class for returning a singleton WebDriver instance.
 */
public class Driver {

    // Private constructor to prevent creating an object of this class
    private Driver() {
    }

    // Single private static WebDriver instance
    private static WebDriver driver;

    /**
     * Returns the singleton WebDriver instance.
     *
     * If the driver is null, a new one is created based on the 'browser' property
     * in the configuration.properties file. Otherwise, the existing instance is returned.
     *
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigurationReader.getProperty("browser"); // e.g., "chrome", "firefox"

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                // Add more cases if needed, e.g. "edge", "safari"

                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            // Optionally maximize window or apply other default settings
            driver.manage().window().maximize();
        }

        return driver;
    }

    /**
     * An optional alias method if you prefer "get()" instead of "getDriver()".
     * If you do not need this alias, you can remove it.
     */
    public static WebDriver get() {
        return getDriver();
    }

    /**
     * Closes the browser and sets the driver to null.
     * Useful to call in an @After method so that each test starts fresh.
     */
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();  // or driver.close()
            driver = null;
        }
    }
}
