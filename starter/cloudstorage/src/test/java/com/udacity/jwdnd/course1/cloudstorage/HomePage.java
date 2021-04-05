package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final WebDriver webDriver;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    // @FindBy(xpath = "//a[@href='#nav-notes']")
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "fileUpload")
    private WebElement selectFile;

//    @FindBy(xpath = "//a[@href='#nav-files']")
//    @FindBy(id = "nav-files-tab")
//    private WebElement filesTab;

    @FindBy(xpath = "//a[@href='#nav-credentials']")
    private WebElement credentialsTab;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void logout() {
        logoutButton.click();
    }

    public void goToNotesTab() {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", notesTab);
    }

    public void goToCredentialsTab() {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", credentialsTab);
    }

}
