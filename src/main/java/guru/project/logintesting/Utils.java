package guru.project.logintesting;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utils {

    public static void testUnsuccessfulLoginAttempt(String login, String password) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demo.guru99.com/V4/");
        // driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement loginInput = driver.findElement(By.name("uid"));
        loginInput.sendKeys(login);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        WebElement loginBtn = driver.findElement(By.name("btnLogin"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        if (text.contains("User or Password is not valid")) {
            System.out.println("a pop up: " + text + " ,is shown");
        }
        alert.accept();
        driver.close();
    }
}
