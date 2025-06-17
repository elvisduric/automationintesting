package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BookingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkinLabelLocator = By.cssSelector("label[for='checkin']");
    private final By checkoutLabelLocator = By.cssSelector("label[for='checkout']");
    private final By checkinFieldLocator = By.xpath("//label[@for='checkin']/following-sibling::div//input[@class='form-control']");
    private final By checkinCalendar = By.xpath("//label[@for='checkin']/following-sibling::div//div[@class='react-datepicker']");
    private final By checkoutFieldLocator = By.xpath("//label[@for='checkout']/following-sibling::div//input[@class='form-control']");
    private final By checkoutCalendar = By.xpath("//label[@for='checkout']/following-sibling::div//div[@class='react-datepicker']");

    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToBookingPage() {
        driver.get("https://automationintesting.online/#booking");
        waitForPageToLoad();
    }

    private void waitForPageToLoad() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }

    public boolean isCheckinElementPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkinLabelLocator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkinFieldLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isCheckoutElementPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutLabelLocator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutFieldLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public WebElement getCheckinElement() {
        return wait.until(ExpectedConditions.elementToBeClickable(checkinFieldLocator));
    }

    public WebElement getCheckinCalendar() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkinCalendar));
    }

    public WebElement getCheckoutElement() {
        return wait.until(ExpectedConditions.elementToBeClickable(checkoutFieldLocator));
    }

    public WebElement getCheckoutCalendar() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutCalendar));
    }
}