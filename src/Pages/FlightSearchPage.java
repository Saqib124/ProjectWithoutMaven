package Pages;
import Common.PageActions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class FlightSearchPage extends PageActions {

    private By departureAirport = By.cssSelector("input#ctl00_mainContent_ddl_originStation1_CTXT.select_CTXT");
    private By arrivalAirport = By.cssSelector("input#ctl00_mainContent_ddl_destinationStation1_CTXT.select_CTXT");
    private By singleTrip = By.xpath("//input[@id='ctl00_mainContent_rbtnl_Trip_0']");
    private By roundTrip = By.xpath("//table//input[contains(@value,'RoundTrip')]");
    private By multiTrip = By.xpath("//input[@id='ctl00_mainContent_rbtnl_Trip_3']");
    private By departureDate = By.xpath("//input[@id='ctl00_mainContent_view_date1']");
    private By arrivalDate = By.xpath("//input[@id='ctl00_mainContent_view_date1']");
    private By paxInfo = By.xpath("//div[@id='divpaxinfo']");
    private By adultCount = By.xpath("//span[@id='spanAudlt']");
    private By incAdultCount = By.xpath("//span[@id='hrefIncAdt']");
    private By closePaxInfo = By.xpath("//input[@id='btnclosepaxoption']");
    private By currencyDropDown = By.xpath("//select[@id='ctl00_mainContent_DropDownListCurrency']");
    private By searchBtn = By.xpath("//input[@id='ctl00_mainContent_btn_FindFlights']");
    private By loginBtn = By.xpath("//a[@id='ctl00_HyperLinkLogin']");
    private By sector = By.xpath("//span[@class='trip-detrails-sector']");
    private By loginOptions = By.xpath("//li[@class='li-login float-right']/ul/li/a");

    public FlightSearchPage(WebDriver driver){
        super(driver);
    }

    public void selectDepartureAirport(String airport){
        selectTheAirport(airport,departureAirport);
    }

    public void selectArrivalAirport(String airport){
        selectTheAirport(airport,arrivalAirport);
    }

    private void selectTheAirport(String airPort, By xpath) {
        WebElement element = driver.findElement(xpath);
        element.clear();
        wait(500);
        element.click();
        for (char character : airPort.toCharArray()) {
            wait(500);
            typeInto(element,String.valueOf(character));
        }
        wait(500);
    }

    public void selectTripType(String tripType){
        waitForPageLoaded();
        if(StringUtils.equals(tripType,"Single")){
            clickOn(singleTrip);
        }
        else if(StringUtils.equals(tripType,"Round")){
            clickOn(roundTrip);
        }
        else{
            clickOn(multiTrip);
        }
    }

    public void selectDepartureDate(String datefterDays){
        String date = getDateAfterDays(datefterDays);
        String[] parts = date.split("-");

//        Issue with the xpath give different xpath when run on different network
//        int value = Integer.parseInt(parts[1]);
//        value = value - 1;

        clickOn(departureDate);
        By by = By.xpath("//td[@data-month='"+parts[1]+"']//a[text()='"+parts[0]+"']");
        clickOn(by);
    }

    public void selectArrivalDate(String datefterDays){
        String date = getDateAfterDays(datefterDays);
        String[] parts = date.split("-");

//        Issue with the xpath give different xpath when run on different network
//        int value = Integer.parseInt(parts[1]);
//        value = value - 1;

        wait(500);
        clickOn(arrivalDate);
        By by = By.xpath("//td[@data-month='"+parts[1]+"']//a[text()='"+parts[0]+"']");
        clickOn(by);
    }

    private String getDateAfterDays(String datefterDays){
        int noDays = Integer.parseInt(datefterDays);
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, noDays);
        dt = c.getTime();
        return new SimpleDateFormat("d-M").format(dt);
    }

    public void selectTheOfAdultPax(String adult) {
        wait(500);
        clickOn(paxInfo);
        String count = getTextOf(adultCount);
        while(!StringUtils.equals(adult, count)){
            clickOn(incAdultCount);
            count = getTextOf(adultCount);
        }
        clickOn(closePaxInfo);
    }

    public void selectTheCurrencyType(String currency) {
        selectByValueFromDropDown(currencyDropDown,currency);
    }

    public void tapOfSearchButton() {
        clickOn(searchBtn);
    }

    public void verifyThePageTitle(String text) {
        waitForPageLoaded();
        String uiText = getTextOf(sector);
        uiText = uiText.replaceAll("\\s+","");
        text = text.replaceAll("\\s+","");
        assertThat (uiText, is(text));
    }

    public void verifyTheFinalUrl(String url) {
        String currentUrl = driver.getCurrentUrl();
        assertThat (currentUrl, is(url));
    }

    public void moveMouseLoginSignup() {
        WebElement element = driver.findElement(loginBtn);

        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.perform();
        wait(500);
    }

    public void printAllOptions() {
        List<WebElement> elements = driver.findElements(loginOptions);
        for(int i=1 ; i<elements.size();i++){
            WebElement element = elements.get(i);
            System.out.println("Login Option: "+element.getText());
        }
    }
}
