package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

//    @FindBy(id = "logout-msg")
//    private WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

//    @FindBy(id = "signup-link")
//    private WebElement signupLink;

    @FindBy(id = "login-button")
    private WebElement loginBtn;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String getErrorMsg() {
        try {
            return errorMsg.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void loginWithUsernameAndPassword(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginBtn.click();
    }

//    public void navigateToSignupPage() {
//        signupLink.click();
//    }

//    public String getLogoutMsg() {
//        try {
//            return logoutMsg.getText();
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
