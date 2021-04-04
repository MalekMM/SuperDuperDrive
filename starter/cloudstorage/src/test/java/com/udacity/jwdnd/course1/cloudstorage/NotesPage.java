package com.udacity.jwdnd.course1.cloudstorage;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {

    private final WebDriver webDriver;

    @FindBy(xpath = "//button[@id = 'btnAddNewNote']")
    private WebElement addNoteButton;

    @FindBy(id = "btnEditNote")
    private WebElement editNoteButton;

    @FindBy(id = "ancDeleteNote")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement submitButton;

    @FindBy(id = "btnSaveChanges")
    private WebElement saveChangesButton;

    @FindBy(id = "dismiss-changes")
    private WebElement dismissChangesButton;

    @FindBy(id = "tableNoteTitle")
    private WebElement noteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement noteDescription;

    public NotesPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void addNote(String title, String description) {
        // click on "Add new note"
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", addNoteButton);
        // Enter title and description
        jse.executeScript("arguments[0].value='" + title + "';", inputNoteTitle);
        jse.executeScript("arguments[0].value='" + description + "';", inputNoteDescription);
        // Save changes
        jse.executeScript("arguments[0].click();", saveChangesButton);
    }

    public void deleteNote() {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", deleteNoteButton);
    }

    public void updateNote(String title, String description) {
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", editNoteButton);
        // Enter title and description
        jse.executeScript("arguments[0].value='" + title + "';", inputNoteTitle);
        jse.executeScript("arguments[0].value='" + description + "';", inputNoteDescription);
        // Save changes
        jse.executeScript("arguments[0].click();", saveChangesButton);
    }

    public String getNoteTitle() {
        try {
            return noteTitle.getAttribute("innerHTML");
        } catch (Exception e) {
            return null;
        }
    }

    public String getNoteDescription() {
        try {
            return noteDescription.getAttribute("innerHTML");
        } catch (Exception e) {
            return null;
        }
    }
}
