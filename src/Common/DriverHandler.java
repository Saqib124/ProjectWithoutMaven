package Common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverHandler {
    private static WebDriver driver = null;

    public WebDriver fireFoxDriver() {
        driver = new FirefoxDriver();
        return driver;
    }

    public WebDriver chromeDriver() {
        System.setProperty("webdriver.chrome.driver" , "./jars/chromedriver.exe");
        driver= new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public void tearDownDriver(){
        driver.quit();
    }

}
