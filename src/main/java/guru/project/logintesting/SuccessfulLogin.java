package guru.project.logintesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SuccessfulLogin {
    public static void main(String[] args) throws InterruptedException {

        CredentialsStorage credentials=new CredentialsStorage();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demo.guru99.com/V4/");
        // driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement login = driver.findElement(By.name("uid"));
        login.sendKeys(credentials.getLogin());
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(credentials.getPassword());
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        loginButton.click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ccpa-consent-notice")));
        //driver.switchTo().frame("ccpa-consent-notice");
        wait.pollingEvery(Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.className("close-icon")));
        WebElement closeIframeBtn = driver.findElement(By.xpath("//*[contains(@class,'close-icon')]"));
        closeIframeBtn.click();
        driver.switchTo().defaultContent();
        String actualTitle = driver.getTitle();//Get title from Selenium Web Page
        String expectedTitle = "Guru99 Bank Manager HomePage";
        actualTitle = actualTitle.trim();
        if (expectedTitle.contentEquals(actualTitle)) {
            System.out.println("The title of the home page is: " + actualTitle);
        } else {
            System.out.println("Test is failed");
        }

    }
}