package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private NotesPage notesPage;
	private ResultPage resultPage;

//	private NoteService noteService;
//	private UserService userService;

	private String username = "myUsername";
	private String password = "myPassword";
	private String firstName = "Nour";
	private String lastName  = "Faza";

	private String noteTitle = "My super note title";
	private String noteDesc  = "My super duper description!";
	private String newNoteTitle = "My new note title";
	private String newNoteDesc = "My new note description";

	public void navigateTo(String page) {
		driver.get("http://localhost:" + this.port + page);
	}

	public void login() {
		navigateTo("/login");
		loginPage.loginWithUsernameAndPassword(username, password);
	}

	public void signup() {
		navigateTo("/signup");
		signupPage.createAccount(firstName, lastName, username, password);
	}

	public void addNote() {
		navigateTo("/home");
		homePage.goToNotesTab();
		notesPage.addNote(noteTitle, noteDesc);
		resultPage.resultClick();
		homePage.goToNotesTab();
	}


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(this.driver);
		signupPage = new SignupPage(this.driver);
		homePage = new HomePage(this.driver);
		notesPage = new NotesPage(this.driver);
		resultPage = new ResultPage(this.driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

//	@Test
//	public void testGetLoginPage() {
//		driver.get("http://localhost:" + this.port + "/login");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}

	@Test
	@Order(1)
	public void testLoginWithoutSignup() {
		login();
		Assertions.assertEquals("Invalid username or password", loginPage.getErrorMsg());
	}

	@Test
	@Order(2)
	public void testSignUpAndLogin() {
		signup();
		Assertions.assertTrue(signupPage.getSuccessMessage().contains("You successfully signed up!"));
		login();
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testLogOut() {
		signup();
		login();
		homePage.logout();
		navigateTo("/home");
		Assertions.assertFalse(driver.getTitle() == "Home");
	}

	@Test
	@Order(4)
	public void testAddNote() {
		signup();
		login();
		addNote();
		Assertions.assertTrue(resultPage.resultClick());
		homePage.goToNotesTab();
		Assertions.assertEquals(noteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(noteDesc, notesPage.getNoteDescription());
	}

	@Test
	@Order(5)
	public void testUpdateNote() {
		signup();
		login();
		addNote();
		Assertions.assertEquals(noteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(noteDesc, notesPage.getNoteDescription());
		notesPage.updateNote(newNoteTitle, newNoteDesc);
		resultPage.resultClick();
		homePage.goToNotesTab();
		Assertions.assertEquals(newNoteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(newNoteDesc, notesPage.getNoteDescription());
	}

	@Test
	@Order(6)
	public void testDeleteNote() {
		signup();
		login();
		addNote();
		notesPage.deleteNote();
//		Integer userID = userService.getUser(authentication.getName()).getUserID();
//		Assertions.assertTrue(Arrays.asList(noteService.getAllNotes(userID)).isEmpty());
		Assertions.assertEquals(null, notesPage.getNoteTitle());
		Assertions.assertEquals(null, notesPage.getNoteDescription());
	}

}
