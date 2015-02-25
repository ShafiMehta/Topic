package resources.utilFiles;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class UtilLib {
	private static RemoteWebDriver driver;
	private static StringBuffer verificationErrors = new StringBuffer();

	public static String parentWindowHandle;
	
	public static DocumentBuilderFactory documentBuilderFactory1;
	public static DocumentBuilder documentBuilder1;
	public static Document document1;
	public static boolean value = false;

	
	public static Element root1 = null;
	public static boolean value1 = false;
	public static String errorDetail_Name1 = null;
	public static FileOutputStream isod1 = null;
	public static File xmlFile1 = null;	

	/********************************************************************************************
	 * @Function_Name :  FindSysTimeExecution
	 * @Description : Find systime in specified format

	 ***************************************************************************************/

	public static String FindSysTimeExecution(){

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat SysDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		return SysDate.format(cal.getTime()).toUpperCase();

		//return SysDate;
	}
	
	
	/********************************************************************************************
	 * @throws Exception
	 * @Function_Name : uf_R_enterLoginCredetials(rpanjeta)
	 * @Description : Enter login credetials
	 ********************************************************************************************/
	
	public static boolean uf_R_enterLoginCredetials(String username, String Password) throws Exception{
		Robot robot;
		boolean status = false;
		try {
			robot = new Robot();
			UtilLib.WaitTime(3000);
			uf_R_parseChars(username, robot);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			uf_R_parseChars(Password, robot);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			status = true;
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
		
	}
	public static String TestTimeSummary(String ExecutionStartTime,String ExecutionStopTime) throws ParseException{

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		Date date1 = format.parse(ExecutionStartTime);
		Date date2 = format.parse(ExecutionStopTime);
		long difference = date2.getTime() - date1.getTime();
		System.out.println("difference : "+difference);

		difference=difference/1000;
		int minutes= (int)difference/60;
		int sec= (int)difference%60;
		int hours=minutes/60;
		minutes=minutes%60;
		System.out.println("Hours:"+hours);
		System.out.println("Minutes:"+minutes);
		System.out.println("Seconds:"+sec);

		System.out.println("Time duration:  " +hours+" hours "+minutes+" minutes "+sec+" seconds");

		String TimeDifference = ""+hours+":"+minutes+":"+sec+"";

		return TimeDifference;
	}
	/********************************************************************************************
	 * @Function_Name :  CloseDriver
	 * @Description : Close the Webdriver Session

	 ***************************************************************************************/

	public static void CloseDriver() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}


	}

	/********************************************************************************************
	 * @throws Exception
	 * @Function_Name : uf_R_SwitchToNewWindow(rpanjeta)
	 * @Description : Switch control to other window
	 ********************************************************************************************/

	public static void uf_R_SwitchToNewWindow() {
		parentWindowHandle = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.manage().window().maximize(); 
		}
	}
	
	/********************************************************************************************
	 * @throws Exception
	 * @Function_Name : uf_R_switchToOriginalWindow(rpanjeta)
	 * @Description : Switch control to other window
	 ********************************************************************************************/
	public static void uf_R_switchToOriginalWindow() {
		
		driver.switchTo().window(parentWindowHandle);
	}
	/********************************************************************************************
	 * @Function_Name : isElementPresent
	 * @Description : Checks if Element is present
	 * * @param : element - Name of the ELEMENT
	 * @param : by - XPATH of the element

	 ***************************************************************************************/ 

	public static boolean isElementPresent(String element , By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("no element");
			return false;
		}
	}


	/********************************************************************************************
	 * @throws Exception
	 * @Function_Name : uf_R_DataVerification
	 * @Description : Verify Data
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param browser
	 *            - DB data list
	 * @param Element xPath          
	 *  			- Xpath
	 ********************************************************************************************/

	public static boolean uf_R_DataVerification(String ElementName,String Xpath, String data, String TestCase_Name) throws Exception {
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
			try{
				boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath, TestCase_Name);
				if(Verify){
					String xPath = driver.findElement(By.xpath(Xpath)).getText();
			  
					if (xPath.equalsIgnoreCase(data)) {
						UtilLib.Report_PASS(xPath, "is verified", "uf_R_DataVerification");
						 return true;
					}
				}else{
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "ClickOnElement"); 
					UtilLib.CaptureScreenshot(TestCase_Name);
				}
				if(RerunFlag>0){
				
					return false;
				}else{
					return true;
				}

			}  catch(IndexOutOfBoundsException exception1) { 		
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("IndexOutOfBoundsException because of unavailability of getText values due to Change in XPATH","Kindly change the xpath value", TestCase_Name);
				return false;
			}catch(NullPointerException exception){ 
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("Change in XPATH",
						"Kindly change the xpath value", TestCase_Name);
				return false;
			}catch(UnhandledAlertException alert){
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
				return false;
			}catch (NoSuchElementException e) {
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
				return false;
			}catch (NoSuchFrameException e) {
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
				return false;
			}catch (Error e) {
				UtilLib.CaptureScreenshot(TestCase_Name);
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
				return false;
			}
	}
	/********************************************************************************************
	 * @Function_Name :  uf_C_ClickOnElement1
	 * @Description : Clicks on a particular 'Element' .
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element

	 ********************************************************************************************/

	public static boolean uf_C_ClickOnElement1(String ElementName,String Xpath,String TestCase_Name) throws Exception {
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}

		try {
			String ElementNameXpath=uf_C_GetUIData1(ElementName, Xpath, TestCase_Name);
			if(Xpath.endsWith("/a")){
			
					driver.findElement(By.xpath(Xpath)).click();
				
					if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
						long stoptime = System.currentTimeMillis()+5000;
						while(System.currentTimeMillis()<stoptime){
						}
					}

					UtilLib.WaitTime(4000);
					UtilLib.Report_PASS(ElementNameXpath, "is succesfully clicked", "ClickOnElement"); 
					
			
			}else{
				
				driver.findElement(By.xpath(Xpath)).click();
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
					}
				}

				UtilLib.WaitTime(4000);
				UtilLib.Report_PASS(ElementName, "is succesfully clicked", "ClickOnElement"); 
			}
	/*		boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);
			if (Verify == true) {*/
				
			

			/*}
			
			else{
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "ClickOnElement"); 
				UtilLib.CaptureScreenshot(TestCase_Name);
			}*/

			if(RerunFlag>0){
				//DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				return false;
			}else{
				return true;
			}

		}      catch(UnhandledAlertException alert){
			//	UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	} 


	/********************************************************************************************
	 * @throws Exception
	 * @Function_Name : uf_R_DropDownVerification
	 * @Description : Verify value
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param browser
	 *            - DB data list
	 * @param Element Xpath
	 *            - Xpath
	 *@param Element Name
	 *            - ElementName
	 ********************************************************************************************/

	public static boolean uf_R_DropDownVerification(String ElementName,List<String> data, String Xpath,String TestCase_Name) throws Exception {
		boolean status = false;


		boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath, TestCase_Name);
		if(Verify==true){

			try {

				Select select = new Select(driver.findElement(By.xpath(Xpath)));
				List<WebElement> dropdown = select.getOptions();

				for(int i = 0; i<dropdown.size(); i++){

					if ((dropdown.get(i).getText()).equals(data.get(i))) {
						//System.out.println("in if drop down method of util lib");
						UtilLib.Report_PASS(ElementName, "is verified", "DropDownVerification");

						status = true;
					}
					else{
						UtilLib.Report_FAIL(ElementName, "is not verified", "DropDownVerification");
						status = false;
					}

				}
			

			} catch (UnhandledAlertException alert) {
				System.out.println(alert);
				return status;
			}

			catch (NoSuchElementException e) {
				System.out.println(e);
				return status;
			}

			catch (NoSuchFrameException e) {
				System.out.println(e);
				return status;
			}

			catch (Error e) {
				System.out.println(e);
				return status;
			}

		}
		return status;
	}







	/********************************************************************************************
	 * @Function_Name :  uf_R_mouseHover
	 * @Description : Clicks on a particular 'Element' .
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element
	 * @throws Exception 

	 ********************************************************************************************/

	public static boolean uf_R_mouseHover(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try{
			boolean Verify = UtilLib.isElementPresent(ElementName, By.xpath(Xpath));
			if(Verify){
				WebElement link = driver.findElement(By.xpath(Xpath));
				Actions myMouse = new Actions(driver);
				myMouse.moveToElement(link).build().perform();
				UtilLib.WaitTime(4000);
				UtilLib.Report_PASS(ElementName, "is succesfully found", "mouseHover"); 
			}else{
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "mouseHover"); 
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				//DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				return false;
			}else{
				return true;
			}

		}      catch(UnhandledAlertException alert){
			//	UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}



	}

	/********************************************************************************************
	 * @Function_Name :  WaitTime
	 * @Description : Waits for given time in argument Bar to disappear
	 * * @param : milliseconds - time in milliseconds
	 ***************************************************************************************/

	public static void WaitTime(int milliseconds) throws Exception {
		//long stoptime = System.currentTimeMillis()+2000;
		long stoptime = System.currentTimeMillis()+milliseconds;
		while(System.currentTimeMillis()<stoptime){
		}
	}

	/********************************************************************************************
	 * @Function_Name :  getEnvVar
	 * @Description : Get the value from the EnvVar.xml

	 ********************************************************************************************/
	public static String getEnvVar(String tag) {
		String xmlValue = Retrieve_EnvVar.ReportGlobalParameters(tag);
		//System.out.println("url ="+URL);		
		return xmlValue;
	}
	/********************************************************************************************
	 * @throws MalformedURLException 
	 * @Function_Name :  getDriver
	 * @Description : Creates a FirefoxDriver and InternetExplorer object to launch scripts in Firefox and Internet browser

	 ********************************************************************************************/

		//String Browser = Retrieve_EnvVar.ReportGlobalParameters("Browser");
	public static  WebDriver getDriver (List<TopicSearchTestData>  dataList) throws MalformedURLException{
		for (TopicSearchTestData data : dataList) {
		if(( data.getBROWSER_TYPE().equalsIgnoreCase("Mozilla"))){
			DesiredCapabilities browser = DesiredCapabilities.firefox();
			browser.setVersion(data.getBROWSER_Version());
			browser.setPlatform(Platform.WINDOWS);
			//browser.setCapability(FirefoxDriver.BINARY, true);
			browser.setBrowserName("firefox");

			//driver = new RemoteWebDriver(new URL("http://10.65.192.110:5555/wd/hub"),data.getBROWSER_TYPE());
			//driver = new RemoteWebDriver(new URL("http://10.65.192.235/wd/hub"), browser);
		//	driver = new RemoteWebDriver(new URL("http://10.65.192.110:5555/wd/hub"), browser);
			driver = new RemoteWebDriver(new URL("http://10.65.192.235:5566/wd/hub"), browser);
			
			//driver = new RemoteWebDriver(new URL("http://bhive-beta.cisco.com/getsession"), browser);

			
			//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), browser);
			
			
			System.out.println("Firefox Version is -: "+browser);
			//driver = UtilLib.getDriver();
			driver.manage().deleteAllCookies();
			}
			else if(data.getBROWSER_TYPE().equalsIgnoreCase("Internet Explorer")){

			//String file = Retrieve_EnvVar.ReportGlobalParameters("IEPath");
			//File file = new File("src\\test\\java\\resources\\libFiles\\IEDriverServer.exe");
			DesiredCapabilities browser = DesiredCapabilities.internetExplorer();
			browser.setVersion(data.getBROWSER_Version());
			browser.setPlatform(Platform.WINDOWS);
			browser.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			browser.setBrowserName("internet explorer");
			driver = new RemoteWebDriver(new URL("http://10.65.192.110:5555/wd/hub"), browser);
			System.out.println("IE Version is -: "+browser);
			}

		}
		/*String Browser = Retrieve_EnvVar.ReportGlobalParameters("Browser");
		if(Browser.equalsIgnoreCase("Firefox")){
			driver = new FirefoxDriver();
		}*/
		return driver; 

		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}
	
	/********************************************************************************************
	 * @throws AWTException 
	 * @Function_Name :  CaptureScreenshot
	 * @Description : Captures the screenshot of Failed Case with current time stamp and copies to the created folder
	 * * @param : TestCase_Name - Name of the TestCase
	 ***************************************************************************************/		

	public static String CaptureScreenshot(String TestcaseName) {

		String SreenshotPath = null;

		String SubFolderName="FAIL";
		String ResultFolderName = null;



		/*String ResultFolderMain = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName;
		ResultFolderName = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName+"\\"+SubFolderName;*/

		//String ResultFolderMain = ".\\TestExecutionResults\\"+FolderName;
		ResultFolderName = Object_Definition_Library.getEnvVar("ScreenShotPath")+SubFolderName+"\\"+TestcaseName;



		File file = new File(ResultFolderName);
		try{
			if(file.mkdirs()){
				System.out.println("Sub Directory Created");
			}
			else{
				// System.out.println("Sub Directory is not created");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 


		DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
		Date screenshotDate = new Date();
		String picName = screenshotName1.format(screenshotDate);
		//String folderPath = ResultFolderName+"\\Screenshots\\"+TestcaseName+"\\"+picName+".png";
		String folderPath = ResultFolderName+"\\Screenshots\\"+picName+".png";
		System.out.println(picName);
		System.out.println(folderPath);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);


		try {
			FileUtils.copyFile(scrFile, new File(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		SreenshotPath = ResultFolderName+"\\Screenshots\\"+TestcaseName+"\\"+picName+".png";
		//SreenshotPath = "file:///"+folderPath;
		return SreenshotPath;
	}













	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






	/*	*//********************************************************************************************
	 * @Function_Name :  PressDownKey
	 * @Description : Creates a Robot object to launch simulate the Keys pressing

	 ***************************************************************************************//*

	public static void PressDownKey() throws AWTException,IOException
	{
		Robot rob = new Robot();

		for(int i=0;i<keyInput.length;i++){
			rob.keyPress(keyInput[i]);
			rob.delay(1000);
		}    		
		//System.out.println("pressed down");

	}*/





	/********************************************************************************************
	 * @Function_Name :  VerifyTableHeader
	 * @Description : verifies if the Table header is displayed as expected
	 * * @param : pageElement - Name of the ELEMENT
	 * @param : xpath - XPATH of the element
	 * @param : TestCase_Name - Name of the TestCase
	 ***************************************************************************************/

	public static boolean VerifyTableHeader(String pageElement, String xpath, String TestCase_Name) throws Exception {
		try {
			//UtilLib.WaitForProcessingBarToDisappear();


			String tableHeaderText = driver.findElement(By.xpath(xpath)).getText();

			if((tableHeaderText.trim()).equals(pageElement.trim())) {
				Reporter.log("PASS: POM_Lib.VerifyTableHeader : ' " +pageElement + "' table header is displayed. <br>");
				return true;
			} else {
				Reporter.log("FAIL: POM_Lib.VerifyTableHeader : ' " +pageElement + "' table header is Not displayed. <br>");
				UtilLib.CaptureScreenshot(TestCase_Name);
				return false;

			}



		}catch (Exception e) {
			Reporter.log("FAIL: POM_Lib.VerifyTableHeader : Error in page. ' " +pageElement + "' table header is Not displayed. <br>");
			UtilLib.CaptureScreenshot(TestCase_Name);
			return false;
		}
	}	

	/********************************************************************************************
	 * @Function_Name :  VerifyMandatoryFields
	 * @Description : verifies if the given page element and given xpath is a mandatory field or not
	 * * @param : pageElement - Name of the ELEMENT
	 * @param : xpath - XPATH of the element
	 * @param : TestCase_Name - Name of the TestCase
	 ***************************************************************************************/		

	public static boolean VerifyMandatoryFields(String pageElement, String xpath, String TestCase_Name) throws Exception {
		try {


			/* check for Service program Mandatory field */


			boolean mandatoryStatus = driver.findElement(By.xpath(xpath)).isDisplayed();

			if(mandatoryStatus == true) {
				Reporter.log("PASS: POM_Lib.VerifyMandatoryFields : ' " +pageElement + "' mandatory field is displayed. <br>");
				return true;
			} else {
				Reporter.log("FAIL: POM_Lib.VerifyMandatoryFields : ' " +pageElement + "' mandatory field is Not displayed. <br>");
				System.out.println("FAIL: POM_Lib.VerifyMandatoryFields : ' " +pageElement + "' mandatory field is Not displayed.");
				UtilLib.CaptureScreenshot(TestCase_Name);
				return false;
			}



		}catch (NoSuchElementException e) {
			Reporter.log("FAIL: POM_Lib.VerifyMandatoryFields : Error in page.  ' " +pageElement + "' mandatory field is Not displayed. <br>");
			System.out.println("FAIL: POM_Lib.VerifyMandatoryFields : Error in page.  ' " +pageElement + "' mandatory field is Not displayed. ");

			return false;
		}
	}	



	/********************************************************************************************
	 * @Function_Name :  exit
	 * @Description : Quit the Browser

	 ***************************************************************************************/

	public static void exit(){
		driver.close();
	}



	/********************************************************************************************
	 * @Function_Name :  CaptureScreenshot_PASS
	 * @Description : Captures the screenshot of Passed Case with current time stamp and copies to the created folder
	 * * @param : TestCase_Name - Name of the TestCase
	 ***************************************************************************************/		
	public static String CaptureScreenshot_PASS(String TestcaseName){

		String SreenshotPath = null;

		String SubFolderName="PASS";
		String ResultFolderName = null;


		/*String ResultFolderMain = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName;
		ResultFolderName = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName+"\\"+SubFolderName;*/

		//String ResultFolderMain = ".\\TestExecutionResults\\"+FolderName;
		ResultFolderName = Object_Definition_Library.getEnvVar("ScreenShotPath")+SubFolderName+"\\"+TestcaseName;

		File file = new File(ResultFolderName);
		try{
			if(file.mkdirs()){
				System.out.println("Sub Directory Created");
			}
			else{
				// System.out.println("Sub Directory is not created");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 


		DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
		Date screenshotDate = new Date();
		String picName = screenshotName1.format(screenshotDate);
		//String folderPath = ResultFolderName+"\\Screenshots\\"+TestcaseName+"\\"+picName+".png";
		String folderPath = ResultFolderName+"\\Screenshots\\"+picName+".png";
		System.out.println(picName);
		System.out.println(folderPath);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		SreenshotPath = ResultFolderName+TestcaseName+"\\"+picName+".png";
		//SreenshotPath = "file:///"+folderPath;
		return SreenshotPath;
	}





	/********************************************************************************************
	 * @Function_Name :  FindSysTimeExecution
	 * @Description : Find systime in specified format

	 ***************************************************************************************/

	/*public static String FindSysTimeExecution(){

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat SysDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		return SysDate.format(cal.getTime()).toUpperCase();

		//return SysDate;
	}
	 */
	/********************************************************************************************
	 * @Function_Name :  DeleteExecutionStatusXML
	 * @Description : deletes the Failed Test xml

	 ***************************************************************************************/
	/*public static void DeleteFailedTestXML()
	{	
		try{

			File file = new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation is failed.");
			}

		}catch(Exception e){

			e.printStackTrace();

		}

	}

	 */
	/********************************************************************************************
	 * @Function_Name :  CreateFailedTestXMLFile
	 * @Description : creates the Failed Test xml
	 ***************************************************************************************/

	/*public static void CreateFailedTestXMLFile() {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Suite");
			rootElement.setAttribute("name", "Suite");
			rootElement.setAttribute("parallel", "false");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("Error");
			rootElement.appendChild(staff);


			// firstname elements
			Element classes = doc.createElement("Classes");
			//firstname.appendChild(doc.createTextNode("Partner360"));
			staff.appendChild(classes);



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation")));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}	*/

	/********************************************************************************************
	 * @Function_Name :  AppendFailedTestXML
	 * @Description : appends the testcases to Failed Test xml
	 ***************************************************************************************/

	/*public static void AppendFailedTestXML(String ClassName) throws TransformerException, SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

		Node ExecutionStatus = doc.getElementsByTagName("Classes").item(0);
		NodeList list = ExecutionStatus.getChildNodes();
		@SuppressWarnings("unused")
		int len = list.getLength();

		Element teststep = doc.createElement("class");
		teststep.setAttribute("name", ClassName);
		ExecutionStatus.appendChild(teststep);
		System.out.println("Created");
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String abc = Object_Definition_Library.getEnvVar("FailedTestXmlLocation");
		StreamResult result = new StreamResult(new File(abc));
		transformer.transform(source, result);
	}*/


	/********************************************************************************************
	 * @Function_Name :  Report_PASS
	 * @Description : Reporter statement for Passed test step
	 * *@param : ElementName - Name of the Element, Button or Text Box etc
	 *@param : Message - output message
	 *@param : FunctionName - Name of the Function
	 ***************************************************************************************/
	/*public static void Report_PASS(String ElementName, String Message, String FunctionName) {
		String PassConsoleReportingRequired = Retrieve_EnvVar.ReportGlobalParameters("PassConsoleReportingRequired");
		Reporter.log("PASS: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+". <br>");
		if(PassConsoleReportingRequired.equalsIgnoreCase("YES")){
			System.out.println("PASS: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+".");
		}
	}
	 */

	/********************************************************************************************
	 * @Function_Name :  Report_FAIL
	 * @Description : Reporter statement for Failed test step
	 * *@param : ElementName - Name of the Element, Button or Text Box etc
	 *@param : Message - output message
	 *@param : FunctionName - Name of the Function
	 ***************************************************************************************/
	public static void Report_FAIL(String ElementName, String Message, String FunctionName) {
		String FailConsoleReportingRequired = Retrieve_EnvVar.ReportGlobalParameters("FailConsoleReportingRequired");
		Reporter.log("FAIL: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+". <br>");
		if(FailConsoleReportingRequired.equalsIgnoreCase("YES")){
			System.out.println("FAIL: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+".");
		}
	}


	/********************************************************************************************
	 * @Function_Name :  WaitForLoadingBarToDisappear
	 * @Description : Waits for the Loading bar to disappear
	 ***************************************************************************************/

	public static void WaitForLoadingBarToDisappear() throws Exception {
		Boolean bool=true;
		for( ;bool==true; ){
			//bool=driver.findElement(By.xpath("//*[@class='dijitDialogPaneContent']/div/p/b[contains(text(),'Loading...')]")).isDisplayed();
			bool=driver.findElement(By.xpath("//*[@class='dijitDialogPaneContent']/div/p/b[contains(text(),'Loading')]")).isDisplayed();

			long stoptime = System.currentTimeMillis()+2000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
	}



	public static String getTestCasePointerName(String TestCaseName){

		String delims = "_";
		String[] tokens = TestCaseName.split(delims);
		//	System.out.println(tokens[0]);
		return tokens[0];

	}




	/********************************************************************************************
	 * @Function_Name :  uf_C_ClickOnElement
	 * @Description : Clicks on a particular 'Element' .
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element

	 ********************************************************************************************/

	public static boolean uf_C_ClickOnElement(String ElementName,String Xpath,String TestCase_Name) throws Exception {
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}

		try {
			String ElementNameXpath=uf_C_GetUIData1(ElementName, Xpath, TestCase_Name);
			if(Xpath.endsWith("/a")){
				if(ElementNameXpath.equals(ElementName)){
					driver.findElement(By.xpath(Xpath)).click();
					if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
						long stoptime = System.currentTimeMillis()+5000;
						while(System.currentTimeMillis()<stoptime){
						}
					}

					UtilLib.WaitTime(4000);
					UtilLib.Report_PASS(ElementNameXpath, "is succesfully clicked", "ClickOnElement"); 
					
				}else{
					RerunFlag++;
					Report_FAIL(ElementNameXpath,"is not present in the correct position", "VerificationOfElement");
				}
			}else{
				
				driver.findElement(By.xpath(Xpath)).click();
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
					}
				}

				UtilLib.WaitTime(4000);
				UtilLib.Report_PASS(ElementName, "is succesfully clicked", "ClickOnElement"); 
			}
	/*		boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);
			if (Verify == true) {*/
				
			

			/*}
			
			else{
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "ClickOnElement"); 
				UtilLib.CaptureScreenshot(TestCase_Name);
			}*/

			if(RerunFlag>0){
				//DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				return false;
			}else{
				return true;
			}

		}      catch(UnhandledAlertException alert){
			//	UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	} 


	
	
	

	/********************************************************************************************
	 * @Function_Name :  uf_C_EnterDataInTextBox
	 * @Description : Enters data in textbox
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param Data - Data that is to be passed to text field
	 * @throws Exception 
	 ********************************************************************************************/


	public static String uf_C_EnterDataInTextBox(String ElementName,String Xpath,String Data, String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				driver.findElement(By.xpath(Xpath)).clear();
				if(UtilLib.getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+2000;
					while(System.currentTimeMillis()<stoptime){
					}
				}

				driver.findElement(By.xpath(Xpath)).sendKeys(Data);
				long stoptime = System.currentTimeMillis()+2000;
				while(System.currentTimeMillis()<stoptime){
				}
				//driver.findElement(By.xpath(Xpath)).sendKeys(Keys.TAB);
				UtilLib.Report_PASS(ElementName,"is present in the page. Data entered is "+Data,"EnterDataInTextBox");

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","EnterDataInTextBox");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}						
			if(RerunFlag>0){
				return "";
			} else{
				return Data;
			}


		}  catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

	}
	/********************************************************************************************
	 * @Function_Name :  uf_C_SelectValueFromDropdown
	 * @Description : Selects the value from the dropdown
	 * *@param TestCase_Name - Name of the TestCase
	 * * @param DropdownName - Name of the Dropdown
	 * * @param Xpath - Xpath of the dropdown (till select)
	 * * @param VisibleText - Visible text of Option to be selected

	 ********************************************************************************************/


	@SuppressWarnings("unused")
	public static boolean uf_C_SelectValueFromDropdown(String DropdownName,String Xpath,String VisibleText,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		String RMAXpath = "";

		try {
			boolean Verify = driver.findElement(By.xpath(Xpath)).isDisplayed();
			if (Verify == true){
				new Select(driver.findElement(By.xpath(Xpath))).selectByVisibleText(VisibleText);
				//new Select(driver.findElement(By.xpath(Xpath))).selectByIndex(1);

				UtilLib.Report_PASS(VisibleText,"is selected from the "+DropdownName+" list","SelectValueFromDropdown");
				if(UtilLib.getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
					}
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(DropdownName,"is not present in the page","verifyLinkPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.Report_FAIL(VisibleText,"is not available in the "+DropdownName+" list","SelectValueFromDropdown");
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			return false;

		}
	}
	
	

	/********************************************************************************************
	 * @Function_Name :  uf_C_HandleAlert_Accept
	 * @Description : Handles the alerts in the page by clicking on OK button
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param ErrorMessage - Expected Error Message 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	@SuppressWarnings("unused")
	public static boolean uf_C_HandleAlert_Accept(String ElementName, String ErrorMessage, String TestCase_Name) throws Exception {
		int RerunFlag = 0;
		String ScrPath = "";


		try {
			if(ErrorMessage.equalsIgnoreCase("")){
				Alert javascriptAlert = driver.switchTo().alert();
				String Alertmessage = javascriptAlert.getText(); 
				//UtilLib.captureScreen_Alert(TestCase_Name);
				javascriptAlert.accept();
				System.out.println("Alert message : "+Alertmessage);

			}else{
				Alert javascriptAlert = driver.switchTo().alert();
				String Alertmessage = javascriptAlert.getText(); 
				//UtilLib.captureScreen_Alert(TestCase_Name);
				javascriptAlert.accept();
				System.out.println("Alert message : "+Alertmessage);
				Alertmessage = Alertmessage.toLowerCase().trim();
				ErrorMessage = ErrorMessage.toLowerCase().trim();
				/*System.out.println("Alert lowercase = "+Alertmessage.toLowerCase());
				System.out.println("Expected lowercase = "+ErrorMessage.toLowerCase());*/

				if (Alertmessage.equals(ErrorMessage)){
					UtilLib.Report_PASS(ElementName, "Error text displayed '"+Alertmessage+"' is matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");
				} else {
					RerunFlag = RerunFlag + 1;
					UtilLib.Report_FAIL(ElementName, "Error text displayed '"+Alertmessage+"' is NOT matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");
					UtilLib.CaptureScreenshot(TestCase_Name);
					ScrPath = UtilLib.captureScreen_Alert(TestCase_Name);
				}
			}


			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);

			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}	
		catch (Exception e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Screenshot error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

	}


	/********************************************************************************************
	 * @Function_Name :  uf_C_HandleAlert_Dismiss
	 * @Description : Handles the alerts in the page by clicking on OK button
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param ErrorMessage - Expected Error Message 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/
	
	@SuppressWarnings("unused")
	public static void uf_C_HandleAlert_Dismiss(String ElementName, String ErrorMessage, String TestCase_Name)  {
		int RerunFlag = 0;
		String ScrPath = "";

		try {
			if(ErrorMessage.equalsIgnoreCase("")){
				Alert javascriptAlert = driver.switchTo().alert();
				String Alertmessage = javascriptAlert.getText(); 
				//UtilLib.captureScreen_Alert(TestCase_Name);
				javascriptAlert.dismiss();
				System.out.println("Alert message : "+Alertmessage);

			}else{
				Alert javascriptAlert = driver.switchTo().alert();
				String Alertmessage = javascriptAlert.getText(); 
				//UtilLib.captureScreen_Alert(TestCase_Name);
				javascriptAlert.dismiss();
				System.out.println("Alert message : "+Alertmessage);
				Alertmessage = Alertmessage.toLowerCase().trim();
				ErrorMessage = ErrorMessage.toLowerCase().trim();
				/*System.out.println("Alert lowercase = "+Alertmessage.toLowerCase());
				System.out.println("Expected lowercase = "+ErrorMessage.toLowerCase());*/

				if (Alertmessage.equals(ErrorMessage)){
					UtilLib.Report_PASS(ElementName, "Error text displayed '"+Alertmessage+"' is matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");
				} else {
					RerunFlag = RerunFlag + 1;
					UtilLib.Report_FAIL(ElementName, "Error text displayed '"+Alertmessage+"' is NOT matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");
					UtilLib.CaptureScreenshot(TestCase_Name);
					ScrPath = UtilLib.captureScreen_Alert(TestCase_Name);
				}
			}



		} 
		catch (NoAlertPresentException Ex) 
	    { 
	    	  System.out.println("Alert is not Present"); 
	    } catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			//UtilLib.captureScreen_Alert(TestCase_Name);
			//DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			//return false;
		}

		catch (NoSuchElementException e) {

			/*UtilLib.CaptureScreenshot(TestCase_Name);

			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);*/
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			//return false;
		}

		catch (NoSuchFrameException e) {
			/*UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);*/
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			//return false;
		}

		catch (Error e) {
			/*UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);*/
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			//return false;
		}	
		catch (Exception e) {

			/*UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);*/
			UtilLib.Report_FAIL("Screenshot error", "Kindly re-execute the testcase",TestCase_Name);
			//return false;
		}

	}


	/********************************************************************************************
	 * @Function_Name :  uf_C_GetUIData
	 * @Description : Gets data from UI
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static String uf_C_GetUIData(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		String UIData = "";

		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);
			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){

					UIData = driver.findElement(By.xpath(Xpath)).getText();
					UtilLib.Report_PASS(ElementName,"is present in the page and the value in UI is : "+UIData,"verifyElementPresent");
				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"is not present in the page","verifyElementPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","verifyElementPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}

			if(RerunFlag>0){
				return null;
			} else{
				return UIData;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}
	}




	/*public static String GetElementText(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		String Data= null;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
		long stoptime = System.currentTimeMillis()+5000;
		while(System.currentTimeMillis()<stoptime){
		}
		}
		try {
		boolean Verify = UtilLib.isElementPresent(ElementName, By.xpath(Xpath));
		if (Verify  == true){

		Data = driver.findElement(By.xpath(Xpath)).getText();
		if (Data.equalsIgnoreCase("")){
		RerunFlag = RerunFlag+1;
		UtilLib.Report_FAIL(ElementName,"Text is not available for this element. Text displayed is  '"+Data+"'","GetElementText");
		} else {
		UtilLib.Report_PASS(ElementName,"'"+Data+"' is the text available for this element","GetElementText");
		}

		} else {
		RerunFlag = RerunFlag+1;
		UtilLib.Report_FAIL(ElementName,"is not present in the page ","GetElementText");
		UtilLib.CaptureScreenshot(TestCase_Name);

		}
		if(RerunFlag>0){
		return "";
		} else{
		return Data;
		}


		} catch(UnhandledAlertException alert){
		// UtilLib.CaptureScreenshot(TestCase_Name);
		UtilLib.captureScreen_Alert(TestCase_Name);
		DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
		UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
		return null;
		}

		catch (NoSuchElementException e) {

		UtilLib.CaptureScreenshot(TestCase_Name);
		DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
		UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
		return null;
		}

		catch (NoSuchFrameException e) {
		UtilLib.CaptureScreenshot(TestCase_Name);
		DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
		UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
		return null;
		}

		catch (Error e) {
		UtilLib.CaptureScreenshot(TestCase_Name);
		DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
		UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
		return null;
		}
		}*/

	/********************************************************************************************
	 * @Function_Name :  uf_C_EnterTextDataWithTimeStamp
	 * @Description : Input the String and tabs out from the field
	 * * @param : Data - Data to be entered in the field
	 * @param : elementXPATH - XPATH of the element
	 * @param TestCase_Name - Name of the TestCase
	 ***************************************************************************************/

	public static String uf_C_EnterTextDataWithTimeStamp(String ElementName, String Xpath, String TestCase_Name) throws Exception{
		if(UtilLib.getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			Calendar cal = Calendar.getInstance();
			//	SimpleDateFormat SysDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
			SimpleDateFormat SysDate = new SimpleDateFormat("ddMMM-hh-mm-ss");
			String Data = SysDate.format(cal.getTime()).toUpperCase();

			UtilLib.WaitTime(2000);
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				driver.findElement(By.xpath(Xpath)).clear();
				if(UtilLib.getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+2000;
					while(System.currentTimeMillis()<stoptime){
					}
				}
				driver.findElement(By.xpath(Xpath)).sendKeys("TestAuto-"+Data);
				UtilLib.WaitTime(2000);
				driver.findElement(By.xpath(Xpath)).sendKeys(Keys.TAB);
				Data = "AutoTest-"+Data;
				UtilLib.Report_PASS(ElementName,"is present in the page. Data entered is "+Data,"EnterTextDataWithTimeStamp");
				return Data;
			} else {
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","EnterTextDataWithTimeStamp");
				UtilLib.CaptureScreenshot(TestCase_Name);
				return null;
			}						
			//  UtilLib.Report_PASS(ElementName, Message, FunctionName)




		}  catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}


	}
/******************************************************************************************** 
    
	 * @Function_Name : uf_R_GetSelectedDropDownValue(rpanjeta)                                            
	 * @Description : Return selected value.                                                                
	 * @param TestCase_Name                                                                       
	 *            - Name of the TestCase
	 * @param Xpath                                                                            
	 *            - element xpath                                                           
	 * @param ElementName                                                                            
	 *            - element name                                                                   
	 ********************************************************************************************/
                                                                                                  
	
	public static String uf_R_GetSelectedDropDownValue(String ElementName, String Xpath,String TestCase_Name) {                        
		                                                                
		String value = null;                                                                                    
		                                                                                          
		try {                                                                                     
			                     
			Select select = new Select(driver.findElement(By.xpath(Xpath))); 
			
			if(ElementName. equalsIgnoreCase("Text")){
				value = select.getFirstSelectedOption().getText();
				
			}else{
				value = select.getFirstSelectedOption().getAttribute("value");
			}
			return value;                                                                                         
		} catch(IndexOutOfBoundsException exception1) { 
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("IndexOutOfBoundsException because of unavailability of getText values due to Change in XPATH","Kindly change the xpath value", TestCase_Name);
			return value;
		}
		catch(NullPointerException exception){ 
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH",
					"Kindly change the xpath value", TestCase_Name);
			return value;
		}
		catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return value;
		}
		catch (NoSuchElementException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return value;
		}
		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return value;
		}
		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return value;
		}                                               
	}                                                                                             
	        




	/********************************************************************************************
	 * @Function_Name :  verifyElementNotPresent
	 * @Description : Verifies the Elements not present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean uf_C_verifyElementNotPresent(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;

		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){
					UtilLib.Report_FAIL(ElementName,"is present in the page","verifyElementPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);

				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_PASS(ElementName,"is not present in the page","verifyElementPresent");
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);


				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_PASS(ElementName,"is not present in the page ","verifyElementPresent");
				UtilLib.CaptureScreenshot_PASS(TestCase_Name);

			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}




	public static boolean uf_C_ElementIsDisabled(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);


			if (Verify == true){
				boolean Status = driver.findElement(By.xpath(Xpath)).isEnabled();
				if (Status == false){
					UtilLib.Report_PASS(ElementName,"is disabled","ElementIsDisabled");
				} else {
					UtilLib.Report_FAIL(ElementName,"is not disabled","ElementIsDisabled");
					RerunFlag = RerunFlag+1;
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page","ElementIsDisabled");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		} catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}




	////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////








	/***************************************************************************************/		

	public static String captureScreen_Alert(String TestcaseName) throws Exception {

		String SreenshotPath = null;

		String SubFolderName="FAIL";
		String ResultFolderName = null;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();

		/*String ResultFolderMain = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName;
		ResultFolderName = "C:\\"+ApplicationName+"_TestExecutionResults\\"+FolderName+"\\"+SubFolderName;*/

		//String ResultFolderMain = ".\\TestExecutionResults\\"+FolderName;
		ResultFolderName = Object_Definition_Library.getEnvVar("ScreenShotPath")+SubFolderName+"\\"+TestcaseName;



		File file = new File(ResultFolderName);
		try{
			if(file.mkdirs()){
				System.out.println("Sub Directory Created");
			}
			else{
				// System.out.println("Sub Directory is not created");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 


		DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
		Date screenshotDate = new Date();
		String picName = screenshotName1.format(screenshotDate);
		//String folderPath = ResultFolderName+"\\Screenshots\\"+TestcaseName+"\\"+picName+".png";
		String folderPath = ResultFolderName+"\\Screenshots\\"+picName+".png";
		System.out.println(picName);
		System.out.println(folderPath);
		//	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		ImageIO.write(image, "png", new File(folderPath));

		/*
		try {
			FileUtils.copyFile(scrFile, new File(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 */
		SreenshotPath = ResultFolderName+"\\Screenshots\\"+TestcaseName+"\\"+picName+".png";
		//SreenshotPath = "file:///"+folderPath;
		return SreenshotPath;




	}



	/********************************************************************************************
	 * @Function_Name :  DeleteExecutionStatusXML
	 * @Description : deletes the Failed Test xml

	 ***************************************************************************************/
	public static void DeleteFailedTestXML()
	{	
		try{

			File file = new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation is failed.");
			}

		}catch(Exception e){

			e.printStackTrace();

		}

	}


	/********************************************************************************************
	 * @Function_Name :  CreateFailedTestXMLFile
	 * @Description : creates the Failed Test xml
	 ***************************************************************************************/

	public static void CreateFailedTestXMLFile() {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Suite");
			rootElement.setAttribute("name", "Suite");
			rootElement.setAttribute("parallel", "false");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("Error");
			rootElement.appendChild(staff);


			// firstname elements
			Element classes = doc.createElement("Classes");
			//firstname.appendChild(doc.createTextNode("Partner360"));
			staff.appendChild(classes);



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Object_Definition_Library.getEnvVar("FailedTestXmlLocation")));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}	

	/********************************************************************************************
	 * @Function_Name :  AppendFailedTestXML
	 * @Description : appends the testcases to Failed Test xml
	 ***************************************************************************************/

	public static void AppendFailedTestXML(String ClassName) throws TransformerException, SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Object_Definition_Library.getEnvVar("FailedTestXmlLocation"));

		Node ExecutionStatus = doc.getElementsByTagName("Classes").item(0);
		NodeList list = ExecutionStatus.getChildNodes();
		@SuppressWarnings("unused")
		int len = list.getLength();

		Element teststep = doc.createElement("class");
		teststep.setAttribute("name", ClassName);
		ExecutionStatus.appendChild(teststep);
		System.out.println("Created");
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String abc = Object_Definition_Library.getEnvVar("FailedTestXmlLocation");
		StreamResult result = new StreamResult(new File(abc));
		transformer.transform(source, result);
	}


	/********************************************************************************************
	 * @Function_Name :  Report_PASS
	 * @Description : Reporter statement for Passed test step
	 * *@param : ElementName - Name of the Element, Button or Text Box etc
	 *@param : Message - output message
	 *@param : FunctionName - Name of the Function
	 ***************************************************************************************/
	public static void Report_PASS(String ElementName, String Message, String FunctionName) {
		String PassConsoleReportingRequired = Retrieve_EnvVar.ReportGlobalParameters("PassConsoleReportingRequired");
		Reporter.log("PASS: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+". <br>");
		if(PassConsoleReportingRequired.equalsIgnoreCase("YES")){
			System.out.println("PASS: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+".");
		}
	}



	/********************************************************************************************
	 * @Function_Name :  Report_INFO
	 * @Description : Reporter statement for Information
	 * *@param : ElementName - Name of the Element, Button or Text Box etc
	 *@param : Message - output message
	 *@param : FunctionName - Name of the Function
	 ***************************************************************************************/
	public static void Report_INFO(String ElementName, String Message, String FunctionName) {
		String InfoConsoleReportingRequired = Retrieve_EnvVar.ReportGlobalParameters("InfoConsoleReportingRequired");
		Reporter.log("INFO: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+". <br>");
		if(InfoConsoleReportingRequired.equalsIgnoreCase("YES")){
			System.out.println("INFO: POM_Lib."+FunctionName+" : '"+ElementName+"' "+Message+".");
		}
	}


	/********************************************************************************************
	 * @Function_Name :  WaitForLoadingBarToDisappear
	 * @Description : Waits for the Loading bar to disappear
	 ***************************************************************************************//*

	public static void WaitForLoadingBarToDisappear() throws Exception {
	Boolean bool=true;
	for( ;bool==true; ){
	//bool=driver.findElement(By.xpath("//*[@class='dijitDialogPaneContent']/div/p/b[contains(text(),'Loading...')]")).isDisplayed();
	bool=driver.findElement(By.xpath("//*[@class='dijitDialogPaneContent']/div/p/b[contains(text(),'Loading')]")).isDisplayed();

	long stoptime = System.currentTimeMillis()+2000;
	while(System.currentTimeMillis()<stoptime){
	}
	}
	}


	public static String TestTimeSummary(String ExecutionStartTime,String ExecutionStopTime) throws ParseException{

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		Date date1 = format.parse(ExecutionStartTime);
		Date date2 = format.parse(ExecutionStopTime);
		long difference = date2.getTime() - date1.getTime();
		System.out.println("difference : "+difference);

		difference=difference/1000;
		int minutes= (int)difference/60;
		int sec= (int)difference%60;
		int hours=minutes/60;
		minutes=minutes%60;
		System.out.println("Hours:"+hours);
		System.out.println("Minutes:"+minutes);
		System.out.println("Seconds:"+sec);

		System.out.println("Time duration:  " +hours+" hours "+minutes+" minutes "+sec+" seconds");

		String TimeDifference = ""+hours+":"+minutes+":"+sec+"";

		return TimeDifference;
	}


	  *//********************************************************************************************
	  * @Function_Name :  getEnvVar
	  * @Description : Get the value from the EnvVar.xml
	  ********************************************************************************************//*
	public static String getEnvVar(String tag) {
		String xmlValue = Retrieve_EnvVar.ReportGlobalParameters(tag);
		return xmlValue;
	}
	   */
	/********************************************************************************************
	 * @Function_Name : HandleAlert_Accept_withoutvalidatinMsg
	 * @Description : Handles the alerts in the page by clicking on OK button
	 * *@param TestCase_Name - Name of the TestCase
	 ******************************************************************************************/
	@SuppressWarnings("unused")
	public static boolean HandleAlert_Accept_withoutvalidatinMsg(String ElementName, String TestCase_Name) throws Exception {
		int RerunFlag = 0;
		String ScrPath = "";

		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}

		try {

			Alert javascriptAlert = driver.switchTo().alert();

			String Alertmessage = javascriptAlert.getText(); 

			javascriptAlert.accept();


			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);

			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

	}


	/**
	 * @throws Exception ******************************************************************************************/
	public static String RetrieveDataFromField(String ElementName, String Xpath, String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		String ReturnText = null;

		try {

			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true){                                                                           

				ReturnText = driver.findElement(By.xpath(Xpath)).getText();
				//System.out.println("swetha ReturnText========"+ReturnText);
				if (! (ReturnText.trim().equals("")) ){
					UtilLib.Report_PASS(ElementName, "The retrieved value from the field is "+ReturnText+"", "RetrieveDataFromField");
				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName, "Data is not present in the field as the retrieved value is - '"+ReturnText+"' ", "RetrieveDataFromField");
					UtilLib.CaptureScreenshot(TestCase_Name);
				}                                              

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page", "RetrieveDataFromField");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}

			if(RerunFlag>0){                                                                               
				return "";     
			} else {
				return ReturnText;
			}


		} catch (Error e) {

			return "";
		}
	}



	/********************************************************************************************
	 * @Function_Name :  CompareTextPresent
	 * @Description : Compare the Text present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean CompareTextPresent(String ElementName,String Xpath, String DataToBeVerified, String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){
					String Data = driver.findElement(By.xpath(Xpath)).getText();
					Data = Data.toLowerCase();
					DataToBeVerified = DataToBeVerified.toLowerCase();
					System.out.println("Compare Data = "+Data);
					if (Data.contains(DataToBeVerified))
						UtilLib.Report_PASS(ElementName,"Text entered and Text displayed is matching. Text Entered is '"+DataToBeVerified+"' and Text dispalyed is '"+Data+"'","CompareTextPresent");
					else {
						RerunFlag = RerunFlag+1;
						UtilLib.Report_FAIL(ElementName,"Text '"+Data+"' entered is NOT populated in the page","CompareTextPresent");
					}


				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"is not present in the page","CompareTextPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);


				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","CompareTextPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		}catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}


	public static String uf_C_GetUIData1(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		String UIData = "";

		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				UIData = driver.findElement(By.xpath(Xpath)).getText().trim();
				
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","verifyElementPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}

			if(RerunFlag>0){
				return null;
			} else{
				return UIData;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}
	}


	/********************************************************************************************
	 * @Function_Name :  verifyLabelPresent
	 * @Description : Verifies the Elements present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean verifyLabelPresent(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				String Verify_Displayed  = driver.findElement(By.xpath(Xpath)).getText();
				System.out.println("Label = "+Verify_Displayed);

				//System.out.println("Lable ="+Verify_Displayed);
				if (Verify_Displayed.contains(ElementName)){
					UtilLib.Report_PASS(ElementName,"is present in the page","verifyLabelPresent");


				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"is not present in the page","verifyLabelPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);


				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","verifyLabelPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}


	public static boolean verifyAttributeValue(String ElementName, String Xpath, String attributeName, String expectedAttrValue, String TestCase_Name) throws Exception
	{
		int RerunFlag=0; 
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer"))
		{
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){ }
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true)
			{
				String attributeValue = driver.findElement(By.xpath(Xpath)).getAttribute(attributeName); 
				if (expectedAttrValue.equalsIgnoreCase(attributeValue)) 
				{
					UtilLib.Report_PASS(ElementName, "is Graded out", "verifyAttributeValue");
				}
				else{
					RerunFlag = RerunFlag+1; UtilLib.Report_FAIL(ElementName, "is not Graded out", "verifyAttributeValue");
					UtilLib.CaptureScreenshot(TestCase_Name);
				}
			}else{
				RerunFlag = RerunFlag+1; UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "verifyAttributeValue");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}catch(UnhandledAlertException alert)
		{
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name); 
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}
		catch (NoSuchElementException e) 
		{
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
		}
		return false;
	}



	public static String GetAttributeValue(String ElementName, String Xpath, String attributeName, String TestCase_Name) throws Exception
	{
		int RerunFlag=0; 
		String attributeValue =null;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer"))
		{
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){ }
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true)
			{
				attributeValue = driver.findElement(By.xpath(Xpath)).getAttribute(attributeName); 
				UtilLib.Report_PASS(ElementName,"'"+ElementName+"' is  displayed in the page ","GetAttributeValue");
			}else{
				RerunFlag = RerunFlag+1; UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "UtilLib.verifyAttributeValue");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
		}catch(UnhandledAlertException alert)
		{
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name); 
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}
		catch (NoSuchElementException e) 
		{
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}
		return attributeValue;
	}


	/********************************************************************************************
	 * @Function_Name :  GetElementText
	 * @Description : Get the text of the element
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/
	public static String GetElementText(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		String Data= null;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){

				Data = driver.findElement(By.xpath(Xpath)).getText();
				if (Data.equalsIgnoreCase("")){
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"Text is not available for this element. Text displayed is  '"+Data+"'","GetElementText");
				} else {
					UtilLib.Report_PASS(ElementName,"'"+Data+"' is the text available for this element","GetElementText");
				}

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","GetElementText");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}
			if(RerunFlag>0){
				return "";
			} else{
				return Data;
			}


		} catch(UnhandledAlertException alert){
			// UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}
	}


	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  ElementIsEnabled
	 * @Description : Verifies if the element is enabled or not
	 * *@param TestCase_Name - Name of the TestCase
	 * * @param DropdownName - Name of the Dropdown
	 * * @param Xpath - Xpath of the dropdown (till select)
	 * * @param VisibleText - Visible text of Option to be selected

	 ********************************************************************************************/


	public static boolean ElementIsEnabled(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true){
				boolean Status = driver.findElement(By.xpath(Xpath)).isEnabled();
				if (Status == true) {
					UtilLib.Report_PASS(ElementName,"is enabled","ElementIsEnabled");
				} else {
					UtilLib.Report_FAIL(ElementName,"is not enabled","ElementIsEnabled");
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page","ElementIsEnabled");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		} catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}


	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  ElementIsDisabled
	 * @Description : Verifies if the element is Disabled or not
	 * *@param TestCase_Name - Name of the TestCase
	 * * @param DropdownName - Name of the Dropdown
	 * * @param Xpath - Xpath of the dropdown (till select)
	 * * @param VisibleText - Visible text of Option to be selected

	 ********************************************************************************************/


	public static boolean ElementIsDisabled(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true){
				boolean Status = driver.findElement(By.xpath(Xpath)).isEnabled();
				if (Status == false){
					UtilLib.Report_PASS(ElementName,"is disabled","ElementIsDisabled");
				} else {
					UtilLib.Report_FAIL(ElementName,"is not disabled","ElementIsDisabled");
					RerunFlag = RerunFlag+1;
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page","ElementIsDisabled");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		} catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}


	/********************************************************************************************
	 * @Function_Name :  verifyElementPresent
	 * @Description : Verifies the Elements present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean verifyElementPresent(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){
					UtilLib.Report_PASS(ElementName,"is present in the page","verifyElementPresent");


				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"is not present in the page","verifyElementPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);
				}

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","verifyElementPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);

			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch (Error e) {

			return false;
		}
	}




	/********************************************************************************************
	 * @Function_Name :  SelectValueFromDropdown
	 * @Description : Selects the value from the dropdown
	 * *@param TestCase_Name - Name of the TestCase
	 * * @param DropdownName - Name of the Dropdown
	 * * @param Xpath - Xpath of the dropdown (till select)
	 * * @param VisibleText - Visible text of Option to be selected

	 ********************************************************************************************/
	public static boolean SelectValueFromDropdown(String DropdownName,String Xpath,String VisibleText,String TestCase_Name){
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			System.out.println("VisibleText="+VisibleText);
			boolean Verify = driver.findElement(By.xpath(Xpath)).isDisplayed();
			if (Verify == true){
				new Select(driver.findElement(By.xpath(Xpath))).selectByVisibleText(VisibleText);
				UtilLib.Report_PASS(VisibleText,"is selected from the "+DropdownName+" list","SelectValueFromDropdown");
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
					}
				}
			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(DropdownName,"is not present in the page","verifyLinkPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.Report_FAIL(VisibleText,"is not available in the "+DropdownName+" list","SelectValueFromDropdown");
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			return false;

		}


	}




	/********************************************************************************************
	 * @Function_Name :  ClickOnElement
	 * @Description : Clicks on a particular 'Element' .
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element

	 ********************************************************************************************/

	public static boolean ClickOnElement(String ElementName,String Xpath,String TestCase_Name) throws Exception {
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}

		try {

			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true) {


				driver.findElement(By.xpath(Xpath)).click();
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
					}
				}

				UtilLib.WaitTime(4000);
				UtilLib.Report_PASS(ElementName, "is succesfully clicked", "ClickOnElement"); 


			}else{
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "ClickOnElement"); 
				UtilLib.CaptureScreenshot(TestCase_Name);
			}

			if(RerunFlag>0){
				//DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				return false;
			}else{
				return true;
			}

		}      catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}  



	/********************************************************************************************
	 * @Function_Name :  ClickElementAndVerify
	 * @Description : Clicks on a particular 'Element' and verify the 'VerifyElement' is present or not.
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element
	 *@param VerifyElement - Name of the VerifyElement
	 *@param VerifyElementXpath - XPATH of the VerifyElementXpath
	 *@param FunctionName - Function name in POM_Lib
	 *
	 ********************************************************************************************/

	public static boolean ClickElementAndVerify(String ElementName,String Xpath,String VerifyElement,String VerifyElementXpath,String TestCase_Name) throws Exception {
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {


			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify == true) {
				driver.findElement(By.xpath(Xpath)).click();
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+2000;
					while(System.currentTimeMillis()<stoptime){
					}
				}

				//Wait till element appear on the page
				WebDriverWait wait = new WebDriverWait( driver , 300 ); 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VerifyElementXpath)));

				boolean VerifyElementClick=POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

				if (VerifyElementClick == true) {
					UtilLib.Report_PASS(ElementName, "is succesfully clicked", "ClickElementAndVerify"); 
					UtilLib.Report_PASS(VerifyElement, "is displaying when clicked on "+ElementName, "ClickElementAndVerify"); 
				}else{
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName, "is not clicked", "ClickElementAndVerify"); 
					UtilLib.CaptureScreenshot(TestCase_Name);
				}
			}else{
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName, "is not displayed in the page", "ClickElementAndVerify"); 
				UtilLib.CaptureScreenshot(TestCase_Name);
			}

			if(RerunFlag>0){

				return false;
			}else{
				return true;
			}

		}catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

	}  


	/********************************************************************************************
	 * @Function_Name :  EnterDataInTextBox
	 * @Description : Verifies the Elements present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param Data - Data that is to be passed to text field
	 * @throws Exception 
	 ********************************************************************************************/
	public static String EnterDataInTextBox(String ElementName,String Xpath,String Data, String TestCase_Name) throws Exception{
		int RerunFlag = 0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			boolean Verify = POM_Lib.uf_p2s_checkVisibility(Xpath,ElementName,TestCase_Name);

			if (Verify  == true){
				driver.findElement(By.xpath(Xpath)).clear();
				if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+2000;
					while(System.currentTimeMillis()<stoptime){
					}
				}
				driver.findElement(By.xpath(Xpath)).sendKeys(Data);
				UtilLib.WaitTime(1000);
				driver.findElement(By.xpath(Xpath)).sendKeys(Keys.TAB);
				UtilLib.Report_PASS(ElementName,"is present in the page. Data entered is "+Data,"EnterDataInTextBox");

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","EnterDataInTextBox");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}						
			if(RerunFlag>0){
				return "";
			} else{
				return Data;
			}


		}  catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return null;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return null;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return null;
		}

	}	




	/********************************************************************************************
	 * @Function_Name :  verifyLinkPresent
	 * @Description : Verifies the Link present in the page
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean verifyLinkPresent(String ElementName,String Xpath,String TestCase_Name) throws Exception{
		int RerunFlag=0;
		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			boolean Verify = POM_Lib.uf_p2s_checkVisibility(ElementName, Xpath,TestCase_Name);

			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();

				if (Verify_Displayed  == true){


					UtilLib.Report_PASS(ElementName,"is present in the page","verifyLinkPresent");
				} else {
					RerunFlag = RerunFlag+1;
					UtilLib.Report_FAIL(ElementName,"is not present in the page","verifyLinkPresent");
					UtilLib.CaptureScreenshot(TestCase_Name);
				}

			} else {
				RerunFlag = RerunFlag+1;
				UtilLib.Report_FAIL(ElementName,"is not present in the page ","verifyLinkPresent");
				UtilLib.CaptureScreenshot(TestCase_Name);
			}	
			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		}  catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}
	}




	/********************************************************************************************
	 * @Function_Name :  HandleAlert_Accept
	 * @Description : Handles the alerts in the page by clicking on OK button
	 * *@param TestCase_Name - Name of the TestCase
	 *@param ElementName - Name of the Element
	 *@param ErrorMessage - Expected Error Message 
	 *@param FunctionName - Function name in POM_Lib
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean HandleAlert_Accept(String ElementName, String ErrorMessage, String TestCase_Name) throws Exception {
		int RerunFlag = 0;
		@SuppressWarnings("unused")
		String ScrPath = "";

		if(getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}

		try {


			Alert javascriptAlert = driver.switchTo().alert();
			String Alertmessage = javascriptAlert.getText(); 
			//UtilLib.captureScreen_Alert(TestCase_Name);			
			System.out.println("Alert message="+Alertmessage);	

			Alertmessage = Alertmessage.toLowerCase().trim();
			ErrorMessage = ErrorMessage.toLowerCase().trim();
			System.out.println("Alert lowercase = "+Alertmessage.toLowerCase());
			System.out.println("Expected lowercase = "+ErrorMessage.toLowerCase());

			if (Alertmessage.equals(ErrorMessage)){
				UtilLib.Report_PASS(ElementName, "Error text displayed '"+Alertmessage+"' is matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");

			} else {
				RerunFlag = RerunFlag + 1;
				UtilLib.Report_FAIL(ElementName, "Error text displayed '"+Alertmessage+"' is NOT matching with the expected Error text '"+ErrorMessage+"' ","HandleAlert");
				//UtilLib.CaptureScreenshot(TestCase_Name);
				ScrPath = UtilLib.captureScreen_Alert(TestCase_Name);

			}
			javascriptAlert.accept();


			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}


		} catch(UnhandledAlertException alert){
			//UtilLib.CaptureScreenshot(TestCase_Name);
			UtilLib.captureScreen_Alert(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return false;
		}

		catch (NoSuchElementException e) {

			UtilLib.CaptureScreenshot(TestCase_Name);

			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return false;
		}

		catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

		catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}	
		catch (Exception e) {

			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Screenshot error", "Kindly re-execute the testcase",TestCase_Name);
			return false;
		}

	}


	/********************************************************************************************
	 * @Function_Name :  uf_R_parseChars(rpanjeta)
	 * @Description : Parse character using robot class.
	 * @throws Exception 
	 ********************************************************************************************/
	public static void uf_R_parseChars(String path, Robot robot) throws Exception {
		String letter="";
		for (int i = 0; i < path.length(); i ++) {   
			char chary = path.charAt(i);  

			//typeCharacter(Character.toString(chary),robot);   
			letter=Character.toString(chary);
			//info("abc-=="+letter);

			if (Character.isLetterOrDigit(letter.charAt(0))) {   
				try {   
					boolean upperCase = Character.isUpperCase(letter.charAt(0));   
					String variableName = "VK_" + letter.toUpperCase();   
					KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');   
					Class clazz = ke.getClass();   
					Field field = clazz.getField(variableName);   
					int keyCode = field.getInt(ke);   
					robot.delay(80);   
					if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);   
					robot.keyPress(keyCode);   
					robot.keyRelease(keyCode);   
					if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);   
				}   
				catch(Exception e) { System.out.println(e); }   
			}   
			else {
				if (letter.equals("!")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_1);    
					robot.keyRelease(KeyEvent.VK_1);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("@")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_2);    
					robot.keyRelease(KeyEvent.VK_2);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("#")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_3);    
					robot.keyRelease(KeyEvent.VK_3);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("#")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_3);    
					robot.keyRelease(KeyEvent.VK_3);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("$")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_4);    
					robot.keyRelease(KeyEvent.VK_4);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("%")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_5);    
					robot.keyRelease(KeyEvent.VK_5);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("^")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_6);    
					robot.keyRelease(KeyEvent.VK_6);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("&")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_7);    
					robot.keyRelease(KeyEvent.VK_7);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("*")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_8);    
					robot.keyRelease(KeyEvent.VK_8);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("=")) {
					robot.keyPress(KeyEvent.VK_EQUALS);    
					robot.keyRelease(KeyEvent.VK_EQUALS);  
				}
				else if (letter.equals(" ")) {   
					robot.keyPress(KeyEvent.VK_SPACE);   
					robot.keyRelease(KeyEvent.VK_SPACE);   
				}   
				else if (letter.equals("/")) {
					robot.keyPress(KeyEvent.VK_BACK_SLASH);    
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);  
				}
				else if (letter.equals("\\")){
					robot.keyPress(KeyEvent.VK_BACK_SLASH);    
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);  
				}
				else if (letter.equals("_")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_MINUS);    
					robot.keyRelease(KeyEvent.VK_MINUS);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if(letter.equals(":")) { 
					robot.keyPress(KeyEvent.VK_SHIFT); 
					robot.keyPress(KeyEvent.VK_SEMICOLON);    
					robot.keyRelease(KeyEvent.VK_SEMICOLON);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if(letter.equals(";")) { 
					robot.keyPress(KeyEvent.VK_SEMICOLON);    
					robot.keyRelease(KeyEvent.VK_SEMICOLON);  
				}
				else if(letter.equals(",")) { 
					robot.keyPress(KeyEvent.VK_COMMA);    
					robot.keyRelease(KeyEvent.VK_COMMA);  
				}
				else if(letter.equals("?")) { 
					robot.keyPress(KeyEvent.VK_SHIFT);   
					robot.keyPress(KeyEvent.VK_SLASH);    
					robot.keyRelease(KeyEvent.VK_SLASH);   
					robot.keyRelease(KeyEvent.VK_SHIFT); 
				}
				else if(letter.equals(".")) { 
					robot.keyPress(KeyEvent.VK_DECIMAL);   
					robot.keyRelease(KeyEvent.VK_DECIMAL);    
					/*robot.keyRelease(KeyEvent.VK_SLASH);   
			                robot.keyRelease(KeyEvent.VK_SHIFT); */
				}

			}   
		}   
	}


		/********************************************************************************************
		 * @Function_Name :  uf_R_ConfimationMail
		 * @Description : Check for mail
		 * @param : String - username,String - password
		 ***************************************************************************************/
		
		
		/* public static String uf_R_ConfimationMail(String username,String password)throws Exception{

			   String strMailContent="";
			   String strlinkURL=null;
		       String value="";
		       Properties props = System.getProperties();
		       props.put("mail.imaps.ssl.checkserveridentity","false");
		       props.put("mail.imaps.ssl.trust", "*"); 
		       props.setProperty("mail.store.protocol","imaps");
		       Session session = Session.getDefaultInstance(props, null);
		       Store store = session.getStore("imaps");
		       store.connect("mail.cisco.com",username,password);
		      
		       Folder inbox = store.getFolder("Inbox"); 
		       System.out.println("No of Unread Messages : " + inbox.getUnreadMessageCount());
		       System.out.println("Total no of Messages : " + inbox.getMessageCount());
		       inbox.open(Folder.READ_ONLY);
		       Flags flags = new Flags(Flag.SEEN);
		       FlagTerm UnRead_flag = new FlagTerm(flags, false);
		       Message messages[] = inbox.search(UnRead_flag);
		     
	           System.out.println("Reading messages...");
	            
	           // Display the messages
	           for(Message message:messages) {
	               for (Address a: message.getFrom())
	                   System.out.println("From:" + a);
	                    
	               System.out.println("Title: " + message.getSubject());
	               System.out.println();
	               System.out.println(message.getContent());
	               Multipart multipart = (Multipart) message.getContent() ;
	              
	                for(int i=0;i<multipart.getCount();i++) {
	                    BodyPart bodyPart = multipart.getBodyPart(i);
	                    if (bodyPart.isMimeType("text/*")) {
	                        String s = (String) bodyPart.getContent().toString();
	                        System.out.println("---"+s);
	                    }
	                }
	               System.out.println("---");
	           }
//	- See more at: http://www.itcuties.com/java/javamail-read-email/#sthash.8WiOK8Y1.dpuf
		       System.out.println("UnRead Messages length : " + messages.length);
		       int flag=0;
		       for (int i = messages.length-1; i > -1; i--)
		       {     
		    	   System.out.println("falg value==="+flag);
		    	   System.out.println("messages['"+i+"']");
		             Message message = messages[i];
		             //info("value reversion"+row.get("I_RELEASEVERSION"));
		             System.out.println("Message "+i+" : " +message.getSubject());
		            if(message.getSubject()!=null){
			              if (message.getSubject().contains("Topic3 search")) {
			            	 System.out.println("====="+message.getContent()); 
			            	 if(message.getContent()!=null){
			            		  strMailContent= message.getContent().toString();
			            		
			            	 }
			            	  System.out.println(" shared a Topic3 search======="+strMailContent.trim());
			            	 String str[]= strMailContent.split("\n");
			            	 System.out.println("check length"+str.length);
			            	 StringBuffer sb1 = new StringBuffer();
			            	 for (int j = 0; j < str.length; j++) {
			            		 sb1.append(str[j] ).append("_last");
								
							}
			            	  
			            	 System.out.println("sb======="+sb1);
			              }
		             }
		      
		       }
		       inbox.close(true);
		       store.close();
		       
		       return strMailContent;
			
			}*/
		 /******************************************************************************************** 
			 * @throws Exception                                                                          
			 * @Function_Name : SelectDropDownValue(rpanjeta)                                            
			 * @Description : Verify value                                                                
			 * @param TestCase_Name                                                                       
			 *            - Name of the TestCase                                                          
			 * @param data                                                                            
			 *            - DB data                                                                   
			 ********************************************************************************************/
		                                                                                                  
			
			public static boolean SelectDropDownValue(String data, String Xpath) {                        
				boolean status = false;                                                                   
				                                                                                          
				                                                                                          
				try {                                                                                     
					                     
					Select select = new Select(driver.findElement(By.xpath(Xpath)));                      
					  List<WebElement> dropdown = select.getOptions();                                    
					                                                                                      
					                                                 
					  for(int i = 0; i<dropdown.size(); i++){                                               
			                               
						  if ((dropdown.get(i).getText()).equals(data)) {                                       
							  dropdown.get(i).click();                                                          
							  System.out.println("Dropdown value: "+dropdown.get(i).getText());                         
							  UtilLib.Report_PASS(data, "is verified", "DropDownVerification");                 

							  status =  true;  
							  return status;
						  } else {    


							  UtilLib.Report_FAIL(data, "is not verified",                                      
							  "DropDownVerification");                                                  

						  }     
						                                                                                
					                                                                                      
					  }                                                                                                 
				} catch (UnhandledAlertException alert) {                                                 
					System.out.println(alert);                                                            
					return false;                                                                         
				}                                                                                         
		                                                                                                  
				catch (NoSuchElementException e) {                                                        
					System.out.println(e);                                                                
					return false;                                                                         
				}                                                                                         
		                                                                                                  
				catch (NoSuchFrameException e) {                                                          
					System.out.println(e);                                                                
					return false;                                                                         
				}                                                                                         
		                                                                                                  
				catch (Error e) {                                                                         
					System.out.println(e);                                                                
					return false;                                                                         
				}                                                                                         
				return status;                                                                            
			}   

	
			/********************************************************************************************
			 * @Function_Name :  AppendPrimaryXML
			 * @Description : Generate the PrimaryTestng.xml
			 * @Name : rpanjeta
			 ********************************************************************************************/
			
			public static void AppendPrimaryXML() throws IOException{
				Connection con = null;
				ArrayList<TopicSearchTestData> testng = new ArrayList<TopicSearchTestData>();
				DBAutomationFunction db = new DBAutomationFunction();
				DBAutomationConnection dbCon = new DBAutomationConnection();
				String Phase = Retrieve_EnvVar.ReportGlobalParametersPom("phase");
				con = dbCon.getConnection();
				//System.out.println(Phase);
				testng = db.getDbData(Phase,con);
				int testCount = testng.size();
				for(int i = 0; i<testCount; i++){
				String TestCaseName = "TestScript."+testng.get(i).getTEST_SCRIPT_NAME();
				//System.out.println(TestCaseName);
				
				try {
					//Creating an empty XML Document	 
					documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
					documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
					document1 = documentBuilder1.newDocument();

					File f = new File(Object_Definition_Library.getEnvVar("PrimaryExecutionXMLPath"));	            
					if(f.exists()){
						value = true;
						//System.out.println("File found. Appending data to the XML");

						//If file exists call the append function	      	  			
						appendToPrimaryXML(TestCaseName);	      	  			
					} else{
						value1 = false;
						//System.out.println("File not found. Creating a new XML");	            	

						//Generate a new XML file
						generatePrimaryXML();

						Element suiteElement = document1.createElement("suite");
						suiteElement.setAttribute("name", "Suite");
						suiteElement.setAttribute("parallel", "false");
						document1.appendChild(suiteElement);

						Element testElement = document1.createElement("test");
						suiteElement.appendChild(testElement);
						testElement.setAttribute("name", TestCaseName);

						Element classesElement = document1.createElement("classes");
						testElement.appendChild(classesElement);

						Element itemId = document1.createElement("class");
						classesElement.appendChild(itemId);
						itemId.setAttribute("name",TestCaseName);	 

						//Stream Result
						transformerPrimaryXMLFac();	                      
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}  
			}
			}
			


		/********************************************************************************************
		 * @Function_Name :  parsePrimaryXML
		 * @Description : parse the PrimaryTestng.xml
		 * @Name : rpanjeta
		 ********************************************************************************************/

		public static void parsePrimaryXML(){

			try {
				documentBuilderFactory1 = DocumentBuilderFactory.newInstance();
				documentBuilder1 = documentBuilderFactory1.newDocumentBuilder();
				document1 = documentBuilder1.parse(Object_Definition_Library.getEnvVar("PrimaryExecutionXMLPath"));
				root1 = document1.getDocumentElement();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		/********************************************************************************************
		 * @Function_Name :  transformerPrimaryXMLFac
		 * @Description : transform the PrimaryTestng.xml
		 * @Name : rpanjeta
		 ********************************************************************************************/

		public static void transformerPrimaryXMLFac(){
			try{	            
				DOMSource source = new DOMSource(document1);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				StreamResult result = new StreamResult(Object_Definition_Library.getEnvVar("PrimaryExecutionXMLPath"));
				transformer.transform(source, result);	            
			}catch(Exception e){
				e.printStackTrace();
			}
		}


		/********************************************************************************************
		 * @Function_Name :  appendToPrimaryXML
		 * @Description : Append the test case to PrimaryTestng.xml
		 * @Name : rpanjeta
		 ********************************************************************************************/


		public static void appendToPrimaryXML(String TestCaseName){

			try{             
				parsePrimaryXML();

				//Search for existing error name
				Element link = null;   
				NodeList list = document1.getElementsByTagName("test");

				outermostloop:	            	
					if(true){
						for( int i = 0; i < list.getLength(); i++ ) {
							link = (Element) list.item(i);
							errorDetail_Name1 = link.getAttribute("name");
							if(TestCaseName.equals(errorDetail_Name1)){
								//System.out.println("Matching");
								//System.out.println("Appending data to the existing error tag!!!");
								Element classes1 = (Element) link.getFirstChild();
								Element newEle = document1.createElement("class");	            
								newEle.setAttribute("name", TestCaseName);

								classes1.appendChild(newEle);

								//Write into file
								transformerPrimaryXMLFac();
								break outermostloop;
							} 
						}

						Element server = document1.createElement("test");
						server.setAttribute("name", TestCaseName);
						root1.appendChild(server);

						Element port = document1.createElement("classes");
						server.appendChild(port);

						Element class_1 = document1.createElement("class");
						class_1.setAttribute("name",TestCaseName);	 

						port.appendChild(class_1);

						//Write into file
						transformerPrimaryXMLFac();
					}
			}catch(Exception e){
				e.printStackTrace();
			}	    		    	
		}

		/********************************************************************************************
		 * @Function_Name :  generatePrimaryXML
		 * @Description : generate PrimaryTestng.xml
		 * @Name : rpanjeta
		 ********************************************************************************************/


		public static void generatePrimaryXML(){
			try{
				//create XML file    		
				xmlFile1 = new File(Object_Definition_Library.getEnvVar("PrimaryExecutionXMLPath"));
				xmlFile1.createNewFile();
				isod1 = new FileOutputStream(xmlFile1);
				isod1.flush();
				isod1.close();
			}catch(Exception e ){
				e.printStackTrace();
			}
		}
			
		/********************************************************************************************
		 * @Function_Name :  DeletePrimaryTestngXML
		 * @Description : Delete DeletePrimaryTestngXML.xml
		 * @Name : rpanjeta
		 ********************************************************************************************/

		public static void DeletePrimaryTestngXML()
		{	
			try{

				File file = new File(Object_Definition_Library.getEnvVar("PrimaryExecutionXMLPath"));

				if(file.delete()){
					System.out.println(file.getName() + " is deleted!");
				}else{
					System.out.println("Delete operation is failed.");
				}

			}catch(Exception e){

				e.printStackTrace();

			}

		}
}
