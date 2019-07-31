package TestCase;

import Common.DriverHandler;
import Pages.FlightSearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class FlightSearch {

    private WebDriver driver = null;
    private DriverHandler handler = null;
    private FlightSearchPage flightSearchPage = null;

    @BeforeClass(alwaysRun = true)
    void setUp(){
        handler = new DriverHandler();
        driver = handler.chromeDriver();
        flightSearchPage = new FlightSearchPage(driver);
    }

    @Test
    void searchFlight(){
        driver.get("https://www.spicejet.com");
        flightSearchPage.selectTripType("Round");
        flightSearchPage.selectDepartureAirport("GOI");
        flightSearchPage.selectArrivalAirport("DEL");
        flightSearchPage.selectDepartureDate("+2"); //Date after number of days
        flightSearchPage.selectArrivalDate("+9"); //Date after number of days
        flightSearchPage.selectTheOfAdultPax("3");
        flightSearchPage.selectTheCurrencyType("USD");
        flightSearchPage.tapOfSearchButton();
        flightSearchPage.verifyThePageTitle("Goa  to  Delhi");
        flightSearchPage.verifyTheFinalUrl("https://book.spicejet.com/Select.aspx");
    }

    @Test
    void mouseHover(){
        driver.get("https://www.spicejet.com");
        flightSearchPage.moveMouseLoginSignup();
        flightSearchPage.printAllOptions();
    }

    @AfterClass(alwaysRun = true)
    void tearDown(){
        handler.tearDownDriver();
    }
}
