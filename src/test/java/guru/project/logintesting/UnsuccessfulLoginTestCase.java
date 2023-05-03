package guru.project.logintesting;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class UnsuccessfulLoginTestCase implements ITest {
    WebDriver driver;

    CredentialsStorage credentials = new CredentialsStorage();

    @BeforeMethod

    public void setUpMethod() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demo.guru99.com/V4/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ccpa-consent-notice")));
        wait.pollingEvery(Duration.ofSeconds(1)).until(ExpectedConditions.presenceOfElementLocated(By.id("close")));
        WebElement closeIframeBtn = driver.findElement(By.xpath("//*[@id='close']"));
        closeIframeBtn.click();
        driver.switchTo().defaultContent();
    }


    @Test(dataProvider = "unsuccessfulCredentialsProvider")
    public void testLoginPassword(String nameOfTheTestScenario, String login, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    //    private void howItWorks() {
//        String login1 = "login1";
//        String login2 = "login2";
//        String pass1 = "password1";
//        String pass2 = "password2";
//        Object[][] parameters = new String[][]{{login1, pass1},
//                                               {login2, pass2}};
//    }
//
    @DataProvider
    public Object[][] unsuccessfulCredentialsProvider() {
        return new String[][]{
                {"incorrectCredentials", "love", "12345"},
                {"incorrectLogin", "love", credentials.getPassword()},
                {"incorrectPassword", credentials.getLogin(), "12345"}
        };
    }

    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    public void changeTestName(Method method, Object[] parametersOfTheArray) {

        testName.set(parametersOfTheArray[0].toString());
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}



