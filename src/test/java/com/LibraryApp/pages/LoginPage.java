package com.LibraryApp.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    @FindBy(id="inputEmail") public WebElement emailBox;
    @FindBy(id="inputPassword") public WebElement passwordBox;
    @FindBy(xpath="//button[.='Sign in']") public WebElement signInButton;

    public void login(String user, String pass) {
        emailBox.sendKeys(user);
        passwordBox.sendKeys(pass);
        signInButton.click();
    }
}
