package com.LibraryApp.pages;

import com.LibraryApp.utilities.Driver;
import com.LibraryApp.utilities.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    @FindBy(css = "span.title-level-1")
    public List<WebElement> menuOptions;

    @FindBy(css = "div[class='loader-mask shown']")
    @CacheLookup
    protected WebElement loaderMask;

    @FindBy(css = "h1[class='oro-subtitle']")
    public WebElement pageSubTitle;

    @FindBy(css = "#user-menu > a")
    public WebElement userName;

    @FindBy(linkText = "Logout")
    public WebElement logOutLink;

    @FindBy(linkText = "My User")
    public WebElement myUser;

    public BasePage() {
        PageFactory.initElements(Driver.get(), this);
    }

    public String getPageSubTitle() {
        waitUntilLoaderScreenDisappear();
        return pageSubTitle.getText();
    }

    public void waitUntilLoaderScreenDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOf(loaderMask));
        } catch (Exception e) {
            logger.error("Loader mask did not disappear", e);
        }
    }

    public void navigateToModule(String tab, String module) {
        String tabLocator = "//span[normalize-space()='" + tab + "' and contains(@class, 'title title-level-1')]";
        String moduleLocator = "//span[normalize-space()='" + module + "' and contains(@class, 'title title-level-2')]";
        try {
            BrowserUtils.clickWithWait(By.xpath(tabLocator), 5);
            BrowserUtils.clickWithWait(By.xpath(moduleLocator), 5);
        } catch (Exception e) {
            logger.error("Failed to navigate to module: Tab -> {}, Module -> {}", tab, module, e);
            throw new RuntimeException("Navigation failed!");
        }
    }
}
