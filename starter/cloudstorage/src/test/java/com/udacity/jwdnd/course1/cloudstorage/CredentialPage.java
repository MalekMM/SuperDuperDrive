package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {

    private final WebDriver webDriver;

    @FindBy(id = "btnAddNewCredential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement inputUrl;

    @FindBy(id = "credential-username")
    private WebElement inputUsername;

    @FindBy(id = "credential-password")
    private WebElement inputPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement saveCredentialButton;

    @FindBy(id = "tblCredentialUrl")
    private WebElement credUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement credUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement credPassword;

    @FindBy(id = "aDeleteCredential")
    private WebElement deleteCredButton;

    @FindBy(id = "btnEditCredential")
    private WebElement editCredButton;


    public CredentialPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void addCredential(String url, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", addCredentialButton);

        jse.executeScript("arguments[0].value='" + url + "';", inputUrl);
        jse.executeScript("arguments[0].value='" + username + "';", inputUsername);
        jse.executeScript("arguments[0].value='" + password + "';", inputPassword);

        jse.executeScript("arguments[0].click();", saveCredentialButton);
    }

    public void editCredential(String url, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", editCredButton);

        jse.executeScript("arguments[0].value='" + url + "';", inputUrl);
        jse.executeScript("arguments[0].value='" + username + "';", inputUsername);
        jse.executeScript("arguments[0].value='" + password + "';", inputPassword);

        jse.executeScript("arguments[0].click();", saveCredentialButton);
    }

    public void deleteCredential() {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", deleteCredButton);
    }

    public String getCredUrl() {
        try {
            return credUrl.getAttribute("innerHTML");
        } catch (Exception e) {
            return null;
        }
    }

    public String getCredUsername() {
        try {
            return credUsername.getAttribute("innerHTML");
        } catch (Exception e) {
            return null;
        }
    }

    public String getCredPassword() {
        try {
            return credPassword.getAttribute("innerHTML");
        } catch (Exception e) {
            return null;
        }
    }

}
