package Common;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageActions {

    protected WebDriver driver;
    private WebDriverWait defaultWait;

    public PageActions(WebDriver driver) {
        this.driver = driver;
        defaultWait = new WebDriverWait(this.driver, 30);
    }

    protected void clickOn(By by) {
        try {
            waitForElementToVisible(by);
            WebElement element = this.driver.findElement(by);
            element.click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
    
    protected void typeInto(WebElement element, String string) {
        element.sendKeys(string);
    }

    protected String getTextOf(By by) {
        return driver.findElement(by).getText();
    }

    protected void scrollIntoView(By by) {
        waitForElementToVisible(by);
        WebElement element = this.driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void waitForElementToVisible(By e) {
        defaultWait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    protected void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            defaultWait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    protected void selectByValueFromDropDown(By by, String value){
        WebElement element = this.driver.findElement(by);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    protected void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
