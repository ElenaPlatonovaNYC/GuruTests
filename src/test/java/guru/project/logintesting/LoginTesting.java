package guru.project.logintesting;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;


public class LoginTesting {

    WebDriver driver;
    CredentialsStorage credentials = new CredentialsStorage();

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demo.guru99.com/V4/");
        closeConsentNoticeIframe();
    }

    @BeforeMethod
    public void setUpMethod() {
        driver.get("https://www.demo.guru99.com/V4/");
    }

    @Test
    public void testSucccesfulLogin() {


        WebElement login = driver.findElement(By.name("uid"));
        login.sendKeys(credentials.getLogin());
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(credentials.getPassword());
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        loginButton.click();
        String actualTitle = driver.getTitle();//Get title from Selenium Web Page
        String expectedTitle = "Guru99 Bank Manager HomePage";
        actualTitle = actualTitle.trim();

        Assert.assertEquals(actualTitle, expectedTitle);

        WebElement managerId = driver.findElement(By.cssSelector("body > table > tbody > tr > td > table > tbody > tr.heading3 > td"));
        String text2 = managerId.getText();
        Assert.assertTrue(text2.contains("Manger Id"));
    }

    private void closeConsentNoticeIframe() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ccpa-consent-notice")));
        wait.pollingEvery(Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.className("close-icon")));
        WebElement closeIframeBtn = driver.findElement(By.xpath("//*[contains(@class,'close-icon')]"));
        closeIframeBtn.click();
        driver.switchTo().defaultContent();
    }

    @Test
    public void testIncorrectCredentials() {

        testLoginPassword("love", "12345");

    }

    @Test
    public void testIncorrectLogin() {

        testLoginPassword("love", credentials.getPassword());


    }

    @Test
    public void testIncorrectPassword() {
        testLoginPassword(credentials.getLogin(), "12345");

    }


    public void testLoginPassword(String login, String password) {
        WebElement loginInput = driver.findElement(By.name("uid"));
        loginInput.sendKeys(login);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        WebElement loginBtn = driver.findElement(By.name("btnLogin"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        Assert.assertTrue(text.contains("User or Password is not valid"));

    }


    @AfterClass
    public void tearDown() {
        driver.close();
    }
}
