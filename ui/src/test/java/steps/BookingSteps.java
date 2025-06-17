package steps;

import driver.DriverManager;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import pageobjects.BookingPage;

public class BookingSteps {

    private final RemoteWebDriver driver;
    private final BookingPage bookingPage;

    public BookingSteps() {
        this.driver = DriverManager.getDriver();
        this.bookingPage = new BookingPage(driver);
    }

    @Given("I navigate to the booking page")
    public void i_navigate_to_the_booking_page() {
        bookingPage.navigateToBookingPage();
    }

    @Then("the page title should contain booking information")
    public void the_page_title_should_contain_booking_information() {
        String actualTitle = driver.getTitle();
        System.out.println("Actual page title: " + actualTitle);
        Assert.assertFalse("Page title should not be empty", actualTitle.isEmpty());
        Assert.assertTrue("Page title should contain booking information",
                actualTitle.toLowerCase().contains("booker-platform"));
    }

    @Then("the current URL should contain {string}")
    public void the_current_url_should_contain(String expectedUrlPart) {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue("URL should contain: " + expectedUrlPart,
                currentUrl.contains(expectedUrlPart));
    }

    @Then("I should see checkin element")
    public void i_should_see_checkin_element() {
        boolean isPresent = bookingPage.isCheckinElementPresent();
        Assert.assertTrue("Checkin element is not present on the page", isPresent);
    }

    @Then("I should see checkout element")
    public void i_should_see_checkout_element() {
        boolean isPresent = bookingPage.isCheckoutElementPresent();
        Assert.assertTrue("Checkout element is not present on the page", isPresent);
    }

    @When("I click on checkin element")
    public void i_click_on_checkin_element() {
        if (bookingPage.isCheckinElementPresent()) {
            bookingPage.getCheckinElement().click();
        } else {
            Assert.fail("Checkin element not found to click");
        }
    }

    @Then("checkin calendar should be active")
    public void checkin_calendar_should_be_active() {
        boolean isDisplayed = bookingPage.getCheckinCalendar().isDisplayed();
        Assert.assertTrue("Checkin calendar is not displayed on the page", isDisplayed);
        boolean isEnabled = bookingPage.getCheckinCalendar().isEnabled();
        Assert.assertTrue("Checkin calendar is not enabled on the page", isEnabled);
    }

    @When("I click on checkout element")
    public void i_click_on_checkout_element() {
        if (bookingPage.isCheckoutElementPresent()) {
            bookingPage.getCheckoutElement().click();
        } else {
            Assert.fail("Checkout element not found to click");
        }
    }

    @Then("checkout calendar should be active")
    public void checkout_calendar_should_be_active() {
        boolean isDisplayed = bookingPage.getCheckoutCalendar().isDisplayed();
        Assert.assertTrue("Checkout calendar is not displayed on the page", isDisplayed);
        boolean isEnabled = bookingPage.getCheckoutCalendar().isEnabled();
        Assert.assertTrue("Checkout calendar is not enabled on the page", isEnabled);
    }
}