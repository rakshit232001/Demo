package testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;
import pages.StartupPage;
import pages.appointment_Pages;
import testBase.AppTestBase;
import testdata.LocatorsFactory;

public class appointment_testcase extends AppTestBase
{
	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath + "expected_data.xlsx";
	String loginFilePath = loginDataFilePath + "Login.xlsx";
	StartupPage startupPage;


	appointment_Pages appointment_PagesInstance;
	LocatorsFactory locatorsFactoryInstance;




	@Parameters({"browser", "environment"})
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readExcelPOI(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);

		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl, configData.get("url")+" might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}


	@Test(priority = 1, groups = {"sanity"}, description="* Navigate to the URL.\r\n"
			+ "* Retrieve Title and URL of the current page.\r\n"
			+ "* Verify Title & URL: Check if the title  & URL matches the expected title.")
	public void verifyTitleOfTheHomePage() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);

		Map<String, String> loginData = new FileOperations().readExcelPOI(loginFilePath, "credentials");
		Assert.assertTrue(appointment_PagesInstance.loginToHealthAppByGivenValidCredetial(loginData),"Login failed, Invalid credentials ! Please check manually");
		Map<String, String> expectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "healthApp");
		Assert.assertEquals(appointment_PagesInstance.verifyTitleOfThePage(),expectedData.get("dasboardTitle")) ;
		Assert.assertEquals(appointment_PagesInstance.verifyURLOfThePage(),expectedData.get("homePageUrl")) ;
		Assert.assertTrue(locatorsFactoryInstance.verifyAppointmentModuleIsPresent(driver).isDisplayed(), "Appointment Module is not present in the current page, Please check manually");
	}

	@Test(priority = 2, groups = {"sanity"}, description="* Verify that Appointment module is present or not ?\r\n"
			+ "* While trying to navigate to the Appointment Module,\r\n"
			+ "it popups the \"Select Counter\" Page \r\n"
			+ "* Verify the \"Select Counter\" Page Name")
	public void verifyAppointmentModuleIsPresent() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		Map<String, String> expectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "appointmentModule");

		Assert.assertEquals(appointment_PagesInstance.verifyAppointmentModuleIsPresent(),expectedData.get("selectCounterTitle"),  "select Counter Title is not present, please check manually");
		Assert.assertTrue(locatorsFactoryInstance.verifySelectCounterPopupsIsPresent(driver).isDisplayed(), "select counter popups is not present in the current page, Please check manually");
	}

	@Test(priority = 3, groups = {"sanity"}, description="On the Appointment module's \"New Visit\" Page,\r\n"
			+ "Verify the  \"New Patient\" Button is present or not ?\r\n"
			+ "If \"New Patient\" button is present, \r\n"
			+ "then click on it.\r\n"
			+ "If it clicking on \"New Patient\" button,\r\n"
			+ "then verify the \"Patient Information\" text is present or not ?")
	public void verifyButtonAndTextIsPresent() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		Map<String, String> expectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "appointmentModule");

		Assert.assertEquals(appointment_PagesInstance.verifyButtonAndTextIsPresent(),expectedData.get("patientInformationTitle"),  "patient Information Title is not present, please check manually");
		Assert.assertTrue(locatorsFactoryInstance.verifyPatientInformationTextIsPresent(driver).isDisplayed(), "Patient Information Text is not present in the current page, Please check manually");
	}

	@Test(priority = 4, groups = {"sanity"}, description="On the Appointment module's New Visit Page,\r\n"
			+ "scroll to the buttom of the page.\r\n"
			+ "Then highlight the \"Care of Person Contact\" textbox as Blue color")
	public void scrollToBottomVerifyFieldAndHighlight() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);

		Assert.assertTrue(appointment_PagesInstance.scrollToBottomVerifyFieldAndHighlight(), "care of person contact textbox is not present, please check manually");
		Assert.assertTrue(locatorsFactoryInstance.verifyPrintInvoiceButtonIsPresent(driver).isDisplayed(), "print invoice button is not present in the current page, Please check manually");
	}

	@Test(priority = 5, groups = {"sanity"}, description="On the Appointment module's New Visit Page,\r\n"
			+ "user must be on buttom of the page.\r\n"
			+ "Then click on  \"Care of Person\" textbox and\r\n"
			+ "get the placeholder name of the \"Care of Person\" textbox.\r\n"
			+ "Then Verify the placeholder name is \"Care Taker Person\"")
	public void verifyPlaceholderNameOfTexboxIfTextboxIsEnabled() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		Map<String, String> expectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "appointmentModule");
		Assert.assertEquals(appointment_PagesInstance.verifyPlaceholderNameOfTexbox(),expectedData.get("careOfPersonTextboxPlaceHolderName")) ;
		Assert.assertTrue(locatorsFactoryInstance.verifyCareOfPersonTextboxIsPresent(driver).isDisplayed(), "Care Of Person" + "Textbox is not present in the current page, Please check manually");
	}

	@Test(priority = 6, groups = {"sanity"}, description="On the Appointment module's \"New Visit\" page,\r\n"
			+ "validate the error message in \"Patient Information\" form's  lastname textfield\r\n"
			+ "after clicking on \"Print Invoice\" Button\r\n"
			+ "without filling any information in the form.")
	public void verifyErrorMessage() throws Exception {
		appointment_PagesInstance = new appointment_Pages(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);	
		Map<String, String> expectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "appointmentModule");
		Assert.assertEquals(appointment_PagesInstance.verifyErrorMessage(), expectedData.get("errorMessageOfLastNameTextbox")) ;
		Assert.assertTrue(locatorsFactoryInstance.verifyErrorMessageOfLastNameTextbox(driver).isDisplayed(), "Error Message is not present in the current page, Please check manually");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
	}

	@AfterMethod
	public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
	}
}
