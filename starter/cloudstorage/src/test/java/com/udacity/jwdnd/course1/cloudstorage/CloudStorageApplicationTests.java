package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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
	private CredentialPage credentialPage;

	@Autowired
	private NoteService noteService;
	@Autowired
	private UserService userService;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	private String username = "myUsername";
	private String password = "myPassword";
	private String firstName = "Nour";
	private String lastName  = "Faza";

	private String noteTitle = "My super note title";
	private String noteDesc  = "My super duper description!";
	private String newNoteTitle = "My new note title";
	private String newNoteDesc = "My new note description";

	private String url = "www.domain.com";
	private String credUsername = "admin";
	private String credPassword = "credPassword";
	private String newUrl = "www.new-domain.com";
	private String newCredUsername = "super_admin";
	private String newCredPassword = "mySuperPassword";


	static Integer credID = 0;
//	static Integer noteID = 0;

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

	public void addCredential() {
		navigateTo("/home");
		homePage.goToCredentialsTab();
		credentialPage.addCredential(url, credUsername, credPassword);
		credID = credID + 1;
		resultPage.resultClick();
		homePage.goToCredentialsTab();
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
		credentialPage = new CredentialPage(this.driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testLoginWithoutSignup() {
		/* Logging in without signing up will result
		in the home page not showing. */

		login();
		Assertions.assertFalse(driver.getTitle() == "Home");
		Assertions.assertTrue(driver.getCurrentUrl().contains("http://localhost:" + this.port + "/login"));
		Assertions.assertEquals("Invalid username or password", loginPage.getErrorMsg());
	}

	@Test
	@Order(2)
	public void testSignUpAndLogin() {
		/* Logging in after signing up will result
		in redirecting to "/home" */

		signup();
		Assertions.assertTrue(signupPage.getSuccessMessage().contains("You successfully signed up!"));
		login();
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testLogOut() {
		/* After logging out the user cannot
		access the home page */

//		signup();
		login();
		navigateTo("/home");
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();
		navigateTo("/home");
		Assertions.assertFalse(driver.getTitle() == "Home");
	}

	@Test
	@Order(4)
	public void testAddNote() {
		/* Before adding a note the data base should be empty
		 After adding a note the data base should not be empty
		 and the title and the description of the note should be
		 the same as the ones entered by the user*/

//		signup();
		login();
		Integer userID = userService.getUser(username).getUserID();
		Assertions.assertTrue(Arrays.asList(noteService.getAllNotes(userID)).isEmpty());
		addNote();
		Assertions.assertFalse(Arrays.asList(noteService.getAllNotes(userID)).isEmpty());
		homePage.goToNotesTab();
		Assertions.assertEquals(noteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(noteDesc, notesPage.getNoteDescription());
	}

	@Test
	@Order(5)
	public void testUpdateNote() {
		/* Make sure the note has the title and the description
		added by the user. After the changes, the title and the description
		should be the same as the new ones.*/

//		signup();
		login();
//		addNote();
//		navigateTo("/home");
		homePage.goToNotesTab();

		Assertions.assertEquals(noteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(noteDesc, notesPage.getNoteDescription());

		notesPage.updateNote(newNoteTitle, newNoteDesc);
		Assertions.assertTrue(resultPage.resultClick());

		homePage.goToNotesTab();
		Assertions.assertEquals(newNoteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(newNoteDesc, notesPage.getNoteDescription());
	}

	@Test
	@Order(6)
	public void testDeleteNote() {
		/* Deleting the only note added will result
		in the data base being empty */

//		signup();
		login();
//		addNote();
		navigateTo("/home");
		homePage.goToNotesTab();
		notesPage.deleteNote();
		Assertions.assertTrue(resultPage.resultClick());

		Integer userID = userService.getUser(username).getUserID();
		Assertions.assertTrue(Arrays.asList(noteService.getAllNotes(userID)).isEmpty());
		Assertions.assertEquals(null, notesPage.getNoteTitle());
		Assertions.assertEquals(null, notesPage.getNoteDescription());
	}

	@Test
	@Order(7)
	public void testAddCredential() {
		/* Add a credential and check if the url and username
		are the same ones entered by the user.
		Make sure the password has been encrypted
		And that the decrypted password is the same one entered by the user*/

//		signup();
		login();
		addCredential();

		Assertions.assertEquals(url, credentialPage.getCredUrl());
		Assertions.assertEquals(credUsername, credentialPage.getCredUsername());

		Credentials credential = credentialService.getCredential(credID);
		String encryptedPassword = encryptionService.encryptValue(credPassword, credential.getKey());
		Assertions.assertEquals(encryptedPassword, credentialPage.getCredPassword());
		Assertions.assertEquals(credPassword, encryptionService.decryptValue(credentialPage.getCredPassword(), credential.getKey()));
	}

	@Test
	@Order(8)
	public void testUpdateCredential() {
		/* Update the credential and make sure the
		* password has been encrypted and corresponds
		* to the same one entered by the user*/

//		signup();
		login();
//		addCredential();
		navigateTo("/home");
		homePage.goToCredentialsTab();

		credentialPage.editCredential(newUrl, newCredUsername, newCredPassword);
		Assertions.assertTrue(resultPage.resultClick());
		homePage.goToCredentialsTab();

		Assertions.assertEquals(newUrl, credentialPage.getCredUrl());
		Assertions.assertEquals(newCredUsername, credentialPage.getCredUsername());

		Credentials credential = credentialService.getCredential(credID);
		String encryptedPassword = encryptionService.encryptValue(newCredPassword, credential.getKey());
		Assertions.assertEquals(encryptedPassword, credentialPage.getCredPassword());
		Assertions.assertEquals(newCredPassword, encryptionService.decryptValue(credentialPage.getCredPassword(), credential.getKey()));
	}

	@Test
	@Order(9)
	public void testDeleteCredential() {
		/* Deleting the only credential added will result
		in the data base being empty */

//		signup();
		login();
//		addCredential();
		navigateTo("/home");
		homePage.goToCredentialsTab();
		credentialPage.deleteCredential();
		Assertions.assertTrue(resultPage.resultClick());

		Integer userID = userService.getUser(username).getUserID();
		Assertions.assertTrue(Arrays.asList(credentialService.getAllCredentials(userID)).isEmpty());

		Assertions.assertEquals(null, credentialPage.getCredUrl());
		Assertions.assertEquals(null, credentialPage.getCredUsername());
		Assertions.assertEquals(null, credentialPage.getCredPassword());
	}

}
