package pages;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class appointment_Pages extends StartupPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // Locators
    By usernameTextbox = By.xpath("//input[@id='username_id']");
    By passwordTextbox = By.xpath("//input[@id='password']");
    By signInButton = By.xpath("//button[@id='login']");
    By appointmentModuleByElement = By.xpath("//a[@href='#/Appointment']");
    By appointmentModuleToggleByElement = By.xpath("//span[@data-target='#Appointment']");
    By selectCounterTitleNameByElement = By.xpath("//span[contains(text(), 'Select Counter')]");
    By selectCounterNewOneLinkByElement = By.xpath("//div[@class='counter-item']//h5[contains(text(), 'New-1')]");
    By newPatientButtonLinkByElement = By.xpath("//button[@id='btnNewPatient']");
    By patientInformationTextByElement = By.xpath("//h4[contains(text(), 'Patient Information')]");
    By careOfPersonContactTextboxByElement = By.xpath("//input[@id='id_CareTaker_CareTakerContact']");
    By careOfPersonTextboxByElement = By.xpath("//input[@id='id_CareTaker_CareTakerName']");
    By printInvoiceButtonByElement = By.xpath("//input[@id='btnPrintInvoice']");
    By confirmButtonByElement = By.xpath("//button[@id='id_btn_confirm_confirmation']");
    By errorMessageOfLastNameTextboxByElement = By.xpath("//span[contains(text(), ' Last Name is required.')]");

    public appointment_Pages(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Helper methods
    private WebElement waitForElement(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void highlightElement(WebElement element) {
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
    }

    private void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].click();", element);
    }

    private void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /** Test1 - Login */
    public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) {
        try {
            WebElement username = waitForElement(usernameTextbox, 10);
            highlightElement(username);
            username.sendKeys(expectedData.get("username"));

            WebElement password = waitForElement(passwordTextbox, 10);
            highlightElement(password);
            password.sendKeys(expectedData.get("password"));

            WebElement signIn = waitForElement(signInButton, 20);
            highlightElement(signIn);
            jsClick(signInButton);

            WebElement appointment = waitForElement(appointmentModuleByElement, 10);
            highlightElement(appointment);

            return appointment.isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }

    /** Test1.2 - Verify Title */
    public String verifyTitleOfThePage() {
        return driver.getTitle();
    }

    /** Test1.3 - Verify URL */
    public String verifyURLOfThePage() {
        return driver.getCurrentUrl();
    }

    /** Test2 - Appointment module */
    public String verifyAppointmentModuleIsPresent() {
        try {
            if (driver.findElement(appointmentModuleByElement).isDisplayed()) {
                jsClick(appointmentModuleToggleByElement);
            }

            WebElement title = waitForElement(selectCounterTitleNameByElement, 10);
            return title.getText();
        } catch (Exception e) {
            throw new RuntimeException("Appointment module verification failed", e);
        }
    }

    /** Test3 - Verify Button and Text */
    public String verifyButtonAndTextIsPresent() {
        try {
            if (driver.findElement(selectCounterNewOneLinkByElement).isDisplayed()) {
                jsClick(selectCounterNewOneLinkByElement);
            }

            waitForElement(newPatientButtonLinkByElement, 10).click();
            WebElement infoText = waitForElement(patientInformationTextByElement, 10);
            return infoText.getText();
        } catch (Exception e) {
            throw new RuntimeException("Button and text verification failed", e);
        }
    }

    /** Test4 - Scroll to bottom and verify */
    public boolean scrollToBottomVerifyFieldAndHighlight() {
        try {
            waitForElement(patientInformationTextByElement, 10);
            scrollToBottom();

            WebElement careOfPersonContact = waitForElement(careOfPersonContactTextboxByElement, 10);
            highlightElement(careOfPersonContact);
            return careOfPersonContact.isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Scroll & verify failed", e);
        }
    }

    /** Test5 - Verify Placeholder */
    public String verifyPlaceholderNameOfTexbox() {
        try {
            WebElement careOfPersonTextbox = waitForElement(careOfPersonTextboxByElement, 10);
            careOfPersonTextbox.click();
            return careOfPersonTextbox.getAttribute("placeholder");
        } catch (Exception e) {
            throw new RuntimeException("Placeholder verification failed", e);
        }
    }

    /** Test6 - Verify Error Message */
    public String verifyErrorMessage() {
        try {
            waitForElement(printInvoiceButtonByElement, 10).click();
            waitForElement(confirmButtonByElement, 10).click();

            WebElement errorMsg = waitForElement(errorMessageOfLastNameTextboxByElement, 10);
            return errorMsg.getText();
        } catch (Exception e) {
            throw new RuntimeException("Error message verification failed", e);
        }
    }
}
