package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	private String username = "myUsername";
	private String password = "myPassword";
	private String firstName = "Nour";
	private String lastName  = "Faza";

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
	}

//	@AfterEach
//	public void afterEach() {
//		if (this.driver != null) {
//			driver.quit();
//		}
//	}

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
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", homePage.getNotesTab());

	}

}
