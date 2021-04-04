package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    private final JavascriptExecutor jse;

    @FindBy(id = "aResultSuccess")
    private WebElement aResultSuccess;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        jse = (JavascriptExecutor) webDriver;
    }


    public boolean resultClick() {
        try {
            jse.executeScript("arguments[0].click();", aResultSuccess);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
