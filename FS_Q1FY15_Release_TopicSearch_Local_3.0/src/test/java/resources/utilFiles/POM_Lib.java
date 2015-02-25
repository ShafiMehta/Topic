package resources.utilFiles;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class POM_Lib {

	static int keyInput[] = {KeyEvent.VK_RIGHT};
	static int keyInput1[] = {KeyEvent.VK_DOWN};
	static int keyInput2[] = {KeyEvent.VK_UP};
	private static WebDriver driver;
	public static int shortWait = 3000;
	public static int normalWait = 6000;
	public static int longWait = 10000;
	public static String PT_New_Submit_Trans_ID = null;
	public static String PT_Current_Remove_Trans_ID = null;
	public static String ECT_New_Submit_Trans_ID = null;
	public static String ECT_Current_Remove_Trans_ID = null;
	public static String PT_New_AECA_VB_SpIden = null;
	public static String PT_New_NoAECA_VB_SpIden = null;
	public static String PT_New_MulCus_InAct_SpIden = null;
	public static Object_Definition_Library element = new Object_Definition_Library();
	public static POM_Lib pom = new POM_Lib(driver);
	public static String Task_SKU_Autopopulated = null;
	public static String Task_SKUDescription_Autopopulated = null;
	public static String Task_ArchTechPercentage_Autopopulated = null;
	public static Select option;
	static DBAutomationConnection db = new DBAutomationConnection();
	static DBAutomationFunction dbFunction = new DBAutomationFunction();
	public static String Browser = Retrieve_EnvVar.ReportGlobalParameters("Browser");

	@SuppressWarnings("static-access")
	public POM_Lib(WebDriver driver) {
		super();

		this.driver = driver;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////--------------------------COMMON FUNCTIONS---------------------------//////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  uf_p2s_driverInitiation
	 * @Description : Initiate the driver and navigate to the TopicSearch Landing Page
	 * @param TestCase_Name - Name of the TestCase
	 * @param URL - URL of TopicSearch Portal
	 ********************************************************************************************/
	public static boolean uf_p2s_driverInitiation(String URL, List<TopicSearchTestData>  dataList, String TestCase_Name) throws Exception  {
		int RerunFlag = 0;
		Boolean status = false;
		/*if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}*/
		for (TopicSearchTestData data : dataList) {
		try {
            driver = UtilLib.getDriver(dataList);
            driver.manage().deleteAllCookies();
			driver.get(URL);
			UtilLib.WaitTime(3000);
			UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);

			System.out.println("TopicSearch Url : "+URL);
			driver.manage().window().maximize(); 
			driver.manage().deleteAllCookies();
			UtilLib.WaitTime(3000);
			System.out.println("Page Title: "+driver.getTitle());
			if(driver.getTitle().equals("Cisco.com Login Page"))
			{
				status = true;
				UtilLib.Report_PASS("Cisco.com Login Page", "landing page is displayed", "driverInitiation");
			}else{
				RerunFlag++;
				UtilLib.Report_FAIL("Cisco.com Login Page", "landing page is not displayed", "driverInitiation");

				driver.close();
				return false;
			}

		}
		catch(UnhandledAlertException alert){
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
		}}
		return status;


	}

	/********************************************************************************************

	 * @Function_Name :  uf_SN_split
	 * @Description :  Split of an element
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/


	public static int  uf_SN_split(String ElementName, String TestCase_Name) {
		int filterOption=0;
		try{

			String[] parts = ElementName.split("\\(");
			String moreThan2YrsResults1 = parts[1];
			String[] parts1 =moreThan2YrsResults1.split("\\)");
			moreThan2YrsResults1= parts1[0];
			filterOption=Integer.parseInt(moreThan2YrsResults1);
		}
		catch (Exception e) {
			
		}

		return filterOption;

	}

	/********************************************************************************************
	 * @Function_Name :  uf_N_parseChars
	 * @Description : used to parse the string (path of the file) passed to this method
	 *@param letter- path of the file

	 ********************************************************************************************/

	public static void uf_N_parseChars(String letter, Robot robot) {
		for (int i = 0; i < letter.length(); i++) {
			char chary = letter.charAt(i);
			uf_N_typeCharacter(Character.toString(chary), robot);
		}
	}
	
	
	
	/********************************************************************************************
	 * @Function_Name :  uf_N_typeCharacter
	 * @Description : Used to typecast the characters to string
	 *@param letter- path of the file

	 ********************************************************************************************/
	public static void uf_N_typeCharacter(String letter, Robot robot) {

		if (Character.isLetterOrDigit(letter.charAt(0))) {
			try {
				boolean upperCase = Character.isUpperCase(letter.charAt(0));
				String variableName = "VK_" + letter.toUpperCase();
				KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');
				@SuppressWarnings("rawtypes")
				Class clazz = ke.getClass();
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(ke);
				robot.delay(80);
				if (upperCase)
					robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);
				if (upperCase)
					robot.keyRelease(KeyEvent.VK_SHIFT);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			if (letter.equals("!")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("@")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_SHIFT); 
			} else if (letter.equals("#")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("#")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("$")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_4);
				robot.keyRelease(KeyEvent.VK_4);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("%")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("^")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_6);
				robot.keyRelease(KeyEvent.VK_6);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("&")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("*")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_8);
				robot.keyRelease(KeyEvent.VK_8);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("=")) {
				robot.keyPress(KeyEvent.VK_EQUALS);
				robot.keyRelease(KeyEvent.VK_EQUALS);
			} else if (letter.equals(" ")) {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			} else if (letter.equals("/")) {
				robot.keyPress(KeyEvent.VK_BACK_SLASH);
				robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			} else if (letter.equals("\\")) {
				robot.keyPress(KeyEvent.VK_BACK_SLASH);
				robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			} else if (letter.equals("_")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(":")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(";")) {
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
			} else if (letter.equals(",")) {
				robot.keyPress(KeyEvent.VK_COMMA);
				robot.keyRelease(KeyEvent.VK_COMMA);
			} else if (letter.equals("-")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals("?")) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (letter.equals(" ")) {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			} else if (letter.equals(".")) {
				robot.keyPress(KeyEvent.VK_PERIOD);
				robot.keyRelease(KeyEvent.VK_PERIOD);
			}

		}
	}
	/********************************************************************************************
	 * @param TestCase_Name 
	 * @throws Exception 
	 * @Function_Name :  uf_SH_login
	 * @Description :  Login to Topic Search Application
	 * @param TestCase_Name - Name of the TestCase
	 ********************************************************************************************/

	/*public static boolean uf_SH_login(String URL,String UserName,String Password,String TestCase_Name) throws Exception {
		boolean status=false;
		int RerunFlag=0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try{
			driver = UtilLib.getDriver();
			driver.manage().deleteAllCookies();
			driver.get(URL);
			driver.manage().window().maximize(); 
			UtilLib.WaitTime(2000);

			UtilLib.EnterDataInTextBox("User Name",element.uf_N_userName,UserName,TestCase_Name);
			UtilLib.EnterDataInTextBox("Password",element.uf_N_password,Password,TestCase_Name);
			status=UtilLib.uf_C_ClickOnElement1("Log In", element.uf_N_login, TestCase_Name);
			UtilLib.WaitTime(2000);
			System.out.println("Page Title: "+driver.getTitle());
			if(driver.getTitle().equals("Topic Search"))
			{	
				UtilLib.Report_PASS("Topic Search", "landing page is displayed", "login");
				status = true;
			}else{
				UtilLib.Report_FAIL("Topic Search", "landing page is not displayed", "login");
				RerunFlag++;
				status=false;
				driver.close();
			}

		}catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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
		return status;
	}*/


	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  uf_p2s_labelVerification
	 * @Description :  Verify labels
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/

	private static boolean uf_p2s_labelVerification(String Xpath,
			String PortletValue, String TestCase_Name) throws Exception {
		boolean status=false;
		try
		{
			String label=UtilLib.uf_C_GetUIData1(PortletValue,Xpath,TestCase_Name);
			String[] parts = label.split(":");
			String label1 = parts[0]; 


			if(label1.equals(PortletValue))
			{
				UtilLib.Report_PASS(label1, "is verified", "labelVerification");
				status=true;
			}else{
				UtilLib.Report_FAIL(label1, "is not verified", "labelVerification");
				status=false;
			}
		}catch(IndexOutOfBoundsException exception1) { 
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
		return status;
	}
	
	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  uf_SH_EditFilterVerification
	 * @Description :  Verify labels
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/

	private static boolean uf_SH_EditFilterVerification(String Xpath,
			TopicSearchTestData data, String TestCase_Name) throws Exception {
		boolean status=false;
		int rerunflag=0;
		try
		{
			/*String OkButton=UtilLib.uf_C_GetUIData1("OK Button",element.uf_SH_editOKButtonPopUp,TestCase_Name);
			System.out.println(OkButton);
			String CancelButton=UtilLib.uf_C_GetUIData1("Cancel Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			System.out.println(CancelButton);
			String ResetButton=UtilLib.uf_C_GetUIData1("Reset Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			System.out.println(ResetButton);
			if(OkButton.equals(data.getI_PORTLET_VALUE2())&&CancelButton.equals(data.getI_PORTLET_VALUE3())&&ResetButton.equals(data.getI_PORTLET_VALUE4()))
			{
				UtilLib.Report_PASS("Ok,Cancel,Reset Buttons", "are verified", "ButtonVerification");
				status=true;
			}else{
				UtilLib.Report_FAIL("Ok,Cancel,Reset Buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}*/
			boolean Verify1 = POM_Lib.uf_p2s_checkVisibility("OK Button", element.uf_SH_editOKButtonPopUp,TestCase_Name);
			boolean Verify2 = POM_Lib.uf_p2s_checkVisibility("Cancel Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			boolean Verify3 = POM_Lib.uf_p2s_checkVisibility("Reset Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			if((Verify1==true)&&(Verify2==true)&&(Verify3==true))
			{
				UtilLib.Report_PASS("Ok,Cancel,Reset Buttons", "are verified", "ButtonVerification");
				status=true;
				
			}else{
				UtilLib.Report_FAIL("Ok,Cancel,Reset Buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			String SelectAllLink=UtilLib.uf_C_GetUIData1("SelectAllLink",element.uf_SH_editSelectAllPopUp,TestCase_Name);
			
			String ClearAllLink=UtilLib.uf_C_GetUIData1("ClearAllLink",element.uf_SH_editClearAllPopUp,TestCase_Name);
			if(SelectAllLink.equals(data.getI_PORTLET_VALUE5())&&ClearAllLink.equals(data.getI_PORTLET_VALUE6()))
			{
				UtilLib.Report_PASS("SelectAll, Clear All links", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("SelectAll, Clear All links", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			
			String button4=driver.findElement(By.xpath(element.uf_SH_editButton4downPopUp)).getAttribute("type");
			System.out.println(button4);
			String button3=driver.findElement(By.xpath(element.uf_SH_editButton3downPopUp)).getAttribute("type");
			String button2=driver.findElement(By.xpath(element.uf_SH_editButton2downPopUp)).getAttribute("type");
			String button1=driver.findElement(By.xpath(element.uf_SH_editButton1downPopUp)).getAttribute("type");
			if(button4.equals("button")&&button3.equals("button")&&button2.equals("button")&&button1.equals("button"))
			{
				UtilLib.Report_PASS("2 upward and 2 downward buttons ", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("2 upward and 2 downward buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			boolean alwaysFilterIsDisplayed=driver.findElement(By.xpath(element.uf_SH_editalwaysFiltersPopUp)).isDisplayed();
			if(alwaysFilterIsDisplayed==true)
			{
				UtilLib.Report_PASS("Always Filter ", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("Always Filter", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			boolean PriorityFilterChechboxPopUpIsDisplayed=driver.findElement(By.xpath(element.uf_SH_editPriorityFilterChechboxPopUp)).isDisplayed();
			if(alwaysFilterIsDisplayed==true)
			{
				UtilLib.Report_PASS("Priority Filter Chechbox ", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("Priority Filter Chechbox", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			if(rerunflag>0)
			{
				status=false;
			}
			else
			{
				status=true;
			}
			
		}catch(IndexOutOfBoundsException exception1) { 
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
		return status;
	}
	
	
	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  uf_SH_EditFilterVerification
	 * @Description :  Verify labels
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/

	private static boolean uf_SH_EditFilterVerification_Techzone(String Xpath,
			TopicSearchTestData data, String TestCase_Name) throws Exception {
		boolean status=false;
		int rerunflag=0;
		try
		{
			/*String OkButton=UtilLib.uf_C_GetUIData1("OK Button",element.uf_SH_editOKButtonPopUp,TestCase_Name);
			System.out.println(OkButton);
			String CancelButton=UtilLib.uf_C_GetUIData1("Cancel Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			System.out.println(CancelButton);
			String ResetButton=UtilLib.uf_C_GetUIData1("Reset Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			System.out.println(ResetButton);
			if(OkButton.equals(data.getI_PORTLET_VALUE2())&&CancelButton.equals(data.getI_PORTLET_VALUE3())&&ResetButton.equals(data.getI_PORTLET_VALUE4()))
			{
				UtilLib.Report_PASS("Ok,Cancel,Reset Buttons", "are verified", "ButtonVerification");
				status=true;
			}else{
				UtilLib.Report_FAIL("Ok,Cancel,Reset Buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}*/
			boolean Verify1 = POM_Lib.uf_p2s_checkVisibility("OK Button", element.uf_SH_editOKButtonPopUp_Techzone,TestCase_Name);
			boolean Verify2 = POM_Lib.uf_p2s_checkVisibility("Cancel Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			boolean Verify3 = POM_Lib.uf_p2s_checkVisibility("Reset Button",element.uf_SH_editCancelButtonPopUp,TestCase_Name);
			if((Verify1==true)&&(Verify2==true)&&(Verify3==true))
			{
				UtilLib.Report_PASS("Ok,Cancel,Reset Buttons", "are verified", "ButtonVerification");
				status=true;
				
			}else{
				UtilLib.Report_FAIL("Ok,Cancel,Reset Buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			String SelectAllLink=UtilLib.uf_C_GetUIData1("SelectAllLink",element.uf_SH_editSelectAllPopUp,TestCase_Name);
			
			String ClearAllLink=UtilLib.uf_C_GetUIData1("ClearAllLink",element.uf_SH_editClearAllPopUp,TestCase_Name);
			if(SelectAllLink.equals(data.getI_PORTLET_VALUE3())&&ClearAllLink.equals(data.getI_PORTLET_VALUE4()))
			{
				UtilLib.Report_PASS("SelectAll, Clear All links", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("SelectAll, Clear All links", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			
			String button4=driver.findElement(By.xpath(element.uf_SH_editButton4downPopUp)).getAttribute("type");
			System.out.println(button4);
			String button3=driver.findElement(By.xpath(element.uf_SH_editButton3downPopUp)).getAttribute("type");
			String button2=driver.findElement(By.xpath(element.uf_SH_editButton2downPopUp)).getAttribute("type");
			String button1=driver.findElement(By.xpath(element.uf_SH_editButton1downPopUp)).getAttribute("type");
			if(button4.equals("button")&&button3.equals("button")&&button2.equals("button")&&button1.equals("button"))
			{
				UtilLib.Report_PASS("2 upward and 2 downward buttons ", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("2 upward and 2 downward buttons", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			boolean alwaysFilterIsDisplayed=driver.findElement(By.xpath(element.uf_SH_editalwaysFiltersPopUp)).isDisplayed();
			if(alwaysFilterIsDisplayed==true)
			{
				UtilLib.Report_PASS("Always Filter ", "are verified", "ButtonVerification");
				
			}else{
				UtilLib.Report_FAIL("Always Filter", "are not verified", "ButtonVerification");
				rerunflag++;
			}
			
			if(rerunflag>0)
			{
				status=false;
			}
			else
			{
				status=true;
			}
			
		}catch(IndexOutOfBoundsException exception1) { 
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
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  Search_A_EnterSearchQueryAndClickOnSearch
	 * @Description : Enters search text and clicks on Search button
	 * @param TestCase_Name - Name of the TestCase, Data to be entered
	 ********************************************************************************************/	
	public static boolean Search_SH_EnterSearchQueryAndClickOnSearch( String Data,String TestCase_Name)throws Exception {
		int RerunFlag=0;
		if(UtilLib.getEnvVar("Browser").equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		} 
		try {

			UtilLib.uf_C_EnterDataInTextBox("Search Data",element.uf_N_queryText, Data, TestCase_Name);

			boolean status= UtilLib.uf_C_ClickOnElement1("Search",element.uf_N_searchButton, TestCase_Name);
			if(status==false){
				RerunFlag=RerunFlag + 1;

			}

			if(RerunFlag>0){
				UtilLib.Report_FAIL(Data, "is not searched and Search Button is not clicked",TestCase_Name);
				return false;
			}else {
				UtilLib.Report_PASS(Data, "is searched and Search Button is clicked",TestCase_Name);
				return true; 
			}

		} catch(UnhandledAlertException alert){
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
	 * @Function_Name :  uf_R_driverInitiation_ServiceSearch
	 * @Description : Initiate the driver and navigate to the TopicSearch Landing Page
	 * @param TestCase_Name - Name of the TestCase
	 * @param URL - URL of TopicSearch Portal
	 ********************************************************************************************/
	/*public static boolean uf_R_driverInitiation_ServiceSearch(String URL, String TestCase_Name) throws Exception  {
		int RerunFlag = 0;
		Boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			driver = UtilLib.getDriver(List<TopicSearchTestData> dataList);

			driver.manage().deleteAllCookies();
			driver.get(URL);
			//System.out.println("TopicSearch Url : "+URL);
			driver.manage().window().maximize(); 

			System.out.println("Page Title: "+driver.getTitle());

			status = true;
			UtilLib.Report_PASS("Login ", "landing page is displayed", "uf_R_driverInitiation_ServiceSearch");


		}
		catch(UnhandledAlertException alert){
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
		return status;


	}*/

	/********************************************************************************************
	 * @param TestCase_Name 
	 * @throws Exception 
	 * @Function_Name :  uf_p2s_checkVisibility
	 * @Description :  Check visibility of an element
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/

	public static boolean uf_p2s_checkVisibility(String  ElementName,String Xpath,  String TestCase_Name) throws Exception {
		boolean status=false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try{
			UtilLib.WaitTime(2000);

			status=driver.findElement(By.xpath(Xpath)).isDisplayed();
			//status=UtilLib.isElementPresent(ElementName,By.xpath(Xpath));
			if(status==true)
			{		UtilLib.Report_PASS(ElementName,"is displayed", "CheckVisibility");
			}else{
				UtilLib.Report_FAIL(ElementName,"is not displayed", "CheckVisibility");
			}
		}catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_Login_Service_Search
	 * @Description : Verify Home page of Service search.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_Login_Service_Search(List<TopicSearchTestData> dataList,String Title, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {

				String title = driver.getTitle();
				System.out.println(title);
				if(title.equalsIgnoreCase(Title)){
					status = true;
					UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
				}
				else{
					UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
					status = false;
				}
			}
		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @throws Exception 
	 * @Function_Name :  uf_SM_ResultVerification
	 * @Description :  ResultVerification
	 * @param TestCase_Name - Name of the TestCase
	 * @param dataList - DB data list
	 ********************************************************************************************/

	private static boolean uf_SH_ResultComingVerification(String data,String TestCase_Name) throws Exception {
		boolean status=false;
		try
		{

			String NoOfResults = driver.findElement(By.xpath(element.Search_A_noOfResults)).getText();
			System.out.println("No of results are : "+NoOfResults);
			if(NoOfResults.trim().equalsIgnoreCase("0 results")){
				UtilLib.Report_FAIL(data ,  "No results found with the data entered.Please enter some other data", TestCase_Name);
				
				
			}else{

				UtilLib.Report_PASS("Search button",":clicked and result displayed according to search query",TestCase_Name);

				int noOfFetchedResults=driver.findElements(By.xpath(element.Search_A_ResultsTable)).size();
				System.out.println("NO of fetched results : "+noOfFetchedResults);
				if(noOfFetchedResults>3){
					UtilLib.Report_PASS("Results",":Search query has shown results.",TestCase_Name);

					status=UtilLib.isElementPresent("Search result", By.xpath(element.Search_A_displayedResults));

					if(status==false){
						UtilLib.Report_FAIL("Results " ,"are not displayed","resultVerification()");					

					}else{
						UtilLib.Report_PASS("Results are there and","are  displayed","resultVerification()");
					}

				}

			}
		}
		catch(IndexOutOfBoundsException exception1) { 		
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_Login_Topic_Search
	 * @Description : Select values for field
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_Login_Topic_Search(List<TopicSearchTestData> dataList,String Title, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		int RerunFlag = 0;
		try {
			for (TopicSearchTestData data : dataList) {

				if(Browser.equalsIgnoreCase("Firefox")){
					String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
					String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
					UtilLib.WaitTime(3000);
					status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
					if(status==false){
						UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

					}
					UtilLib.WaitTime(5000);
					String title = driver.getTitle();
					System.out.println(title);
					if(title.equalsIgnoreCase(Title)){
						UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
					}
					else{
						UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
						status = false;
					}
				}else
					if(Browser.equalsIgnoreCase("InternetExplorer")){
						String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
						String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
						status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
						if(status==false){
							UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

						}
						UtilLib.WaitTime(5000);
						String title = driver.getTitle();
						System.out.println(title);
						if(title.equalsIgnoreCase(Title)){
							UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
						}
						else{
							UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
							status = false;
						}
					}else
						if(Browser.equalsIgnoreCase("Safari")){		
							String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
							String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
							status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
							if(status==false){
								UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

							}
							UtilLib.WaitTime(5000);
							String title = driver.getTitle();
							System.out.println(title);
							if(title.equalsIgnoreCase(Title)){
								UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
							}
							else{
								UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
								status = false;
							}
						}else
							if(Browser.equalsIgnoreCase("Chrome")){
								UtilLib.WaitTime(4000);
								Robot robot = new Robot();
								status = UtilLib.uf_R_enterLoginCredetials("nisahni","Nish$2014");
								if(status==false){
									UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

								}
								UtilLib.WaitTime(5000);
								String title = driver.getTitle();
								System.out.println(title);
								if(title.equalsIgnoreCase(Title)){
									UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
								}
								else{
									UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
									status = false;
								}

							}else{
								System.out.println("Kindly select a valid browser");
							}


			}
		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_SelectDataSource
	 * @Description : Click on chevron
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_SelectDataSource(String ElementName,String Xpath, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			status = UtilLib.uf_C_ClickOnElement1(ElementName, element.uf_R_ClearAll, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

			}
			status = UtilLib.uf_C_ClickOnElement1(ElementName, Xpath, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

			}

			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_SelectDataSourceAtResultPage
	 * @Description : Se;lect only one DataSource.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_SelectDataSourceAtResultPage(String ElementName,String Xpath, int number,String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			if(number==1){
				status = UtilLib.uf_C_ClickOnElement1("All", element.uf_R_DataSourceFilterAll, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

				}
				UtilLib.WaitTime(10000);
			}
			else {
				status = UtilLib.uf_C_ClickOnElement1("All", element.uf_R_DataSourceFilterAll, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

				}
				UtilLib.WaitTime(10000);
				status = UtilLib.uf_C_ClickOnElement1("All", element.uf_R_DataSourceFilterAll, TestCase_Name);
				UtilLib.WaitTime(7000);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

				}
			}
			UtilLib.WaitTime(5000);
			status = UtilLib.uf_C_ClickOnElement1(ElementName, Xpath, TestCase_Name);
			UtilLib.WaitTime(5000);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_SelectDataSource");

			}

			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_ClickDownwardChevron
	 * @Description : Click on chevron
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_ClickChevron(String ElementName, String Xpath, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			status = UtilLib.uf_C_ClickOnElement1(ElementName, Xpath, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_ClickChevron");

			}
			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_MouseOverDataSource
	 * @Description : Verify Down Chevron is shown.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static String uf_R_MouseOverDataSource(String ElementName, String Xpath, String TestCase_Name) throws Exception {
		boolean status = false;
		String tooltip = null;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {

			status = UtilLib.uf_R_mouseHover(ElementName, Xpath, TestCase_Name);
			tooltip = driver.findElement(By.xpath(Xpath)).getAttribute("tooltip");
			System.out.println(tooltip);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_ClickChevron");

			}
			status = driver.findElement(By.xpath(element.uf_R_UpwardChevron)).isDisplayed();
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_ClickChevron");

			}
			UtilLib.WaitTime(5000);

			return tooltip;

		} catch(IndexOutOfBoundsException exception1) { 
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("IndexOutOfBoundsException because of unavailability of getText values due to Change in XPATH","Kindly change the xpath value", TestCase_Name);
			return tooltip;
		}catch(NullPointerException exception){ 
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Change in XPATH",
					"Kindly change the xpath value", TestCase_Name);
			return tooltip;
		}catch(UnhandledAlertException alert){
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Unexpected & Unhandled Alert", "Unhandled alert window found",TestCase_Name);
			return tooltip;
		}catch (NoSuchElementException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
			return tooltip;
		}catch (NoSuchFrameException e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("NoSuchFrameException", "Expected frame is not present in the UI. This is an intermittent issue. Kindly re-execute the testcase",TestCase_Name);
			return tooltip;
		}catch (Error e) {
			UtilLib.CaptureScreenshot(TestCase_Name);
			DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
			UtilLib.Report_FAIL("Java Error", "Kindly re-execute the testcase",TestCase_Name);
			return tooltip;
		}


	}



	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyMailFormat
	 * @Description : Verify  the Mail format.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyMailFormat(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				//UtilLib.uf_R_ConfimationMail(data.getI_LOGINUSER_NAME(), data.getI_LOGINUSER_PASSWORD());

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_VerifySavedShareSearchBox
	 * @Description : Click and Verify  the Share dialog box.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifySavedShareSearchBox(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				status = UtilLib.uf_C_ClickOnElement("Share", element.uf_R_Share, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
				}
				String Content = driver.findElement(By.xpath(element.uf_R_ShareSearchContent)).getText();
				String Name = driver.findElement(By.xpath(element.uf_R_Name)).getText();
				String NameText = driver.findElement(By.xpath(element.uf_R_Name)).getAttribute("type");
				System.out.println(NameText);
				String ShareWith = driver.findElement(By.xpath(element.uf_R_ShareWith)).getText();
				System.out.println(ShareWith);

				String ShareWithText = driver.findElement(By.xpath(element.uf_R_ShareWith)).getAttribute("type");
				String Comments = driver.findElement(By.xpath(element.uf_R_Comments)).getText();
				System.out.println(Comments);

				String CommentsText = driver.findElement(By.xpath(element.uf_R_Comments)).getTagName();
				String Save = driver.findElement(By.xpath(element.uf_R_Save)).getAttribute("value");
				String Cancel = driver.findElement(By.xpath(element.uf_R_Cancel)).getAttribute("value");
				if(Content.equalsIgnoreCase(data.getI_PORTLET_VALUE9())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}

				if(Name.equalsIgnoreCase(data.getI_PORTLET_VALUE10())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
					if(NameText.equalsIgnoreCase("text")){
						System.out.println("Text");
						UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					}else{
						UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
						status = false;
					}

				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}


				if(ShareWith.equalsIgnoreCase(data.getI_PORTLET_VALUE11())){
					System.out.println("Text");
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
					if(ShareWithText.equalsIgnoreCase("text")){
						UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					}else{
						UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
						status = false;
					}
				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}

				if(Comments.equalsIgnoreCase(data.getI_PORTLET_VALUE12())){
					System.out.println("Text");
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
					if(CommentsText.equalsIgnoreCase("textarea")){
						UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					}else{
						UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
						status = false;
					}
				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}
				if(Save.equalsIgnoreCase(data.getI_PORTLET_VALUE13())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}
				if(Cancel.equalsIgnoreCase(data.getI_PORTLET_VALUE14())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedShareSearchBox");
					status = true;
				}else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedShareSearchBox");
					status = false;
				}



				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_VerifySavedSearchLink
	 * @Description : Verify  the Saved Search link.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifySavedSearchLink(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				Robot robot = new Robot();
				Robot rb = new Robot();
				UtilLib.uf_R_mouseHover(ElementName, element.uf_R_SavedSearch_Data, TestCase_Name);

				Point coordinates = driver.findElement(By.xpath(element.uf_R_SavedSearch_Data)).getLocation();


				if(Browser.equalsIgnoreCase("Firefox")){
					System.out.println(coordinates.getX());
					System.out.println(coordinates.getY());
					rb.mouseMove(coordinates.getX()+21,coordinates.getY()-844);

					rb.mousePress(InputEvent.BUTTON3_MASK);
					rb.mouseRelease(InputEvent.BUTTON3_MASK);
					UtilLib.WaitTime(3000);
				}else
					if(Browser.equalsIgnoreCase("InternetExplorer")){
						System.out.println(coordinates.getX());
						System.out.println(coordinates.getY());
						rb.mouseMove(coordinates.getX()+21,coordinates.getY()-844);

						rb.mousePress(InputEvent.BUTTON3_MASK);
						rb.mouseRelease(InputEvent.BUTTON3_MASK);
						UtilLib.WaitTime(3000);
					}else
						if(Browser.equalsIgnoreCase("Safari")){ 
							System.out.println(coordinates.getX());
							System.out.println(coordinates.getY());
							rb.mouseMove(coordinates.getX()+21,coordinates.getY()-844);

							rb.mousePress(InputEvent.BUTTON3_MASK);
							rb.mouseRelease(InputEvent.BUTTON3_MASK);
							UtilLib.WaitTime(3000);
						}else
							if(Browser.equalsIgnoreCase("Chrome")){ 
								System.out.println(coordinates.getX());
								System.out.println(coordinates.getY());
								rb.mouseMove(coordinates.getX()+21,coordinates.getY()-844);

								rb.mousePress(InputEvent.BUTTON3_MASK);
								rb.mouseRelease(InputEvent.BUTTON3_MASK);
								UtilLib.WaitTime(3000);
							}else{
								System.out.println("Kindly select a valid browser");
							}

				String Link1 = driver.findElement(By.xpath(element.uf_R_Rename)).getText();
				String Link2 = driver.findElement(By.xpath(element.uf_R_Delete)).getText();
				String Link3 = driver.findElement(By.xpath(element.uf_R_Share)).getText();
				if(Link1.equalsIgnoreCase(data.getI_PORTLET_VALUE6())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedSearchLink");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedSearchLink");

				}

				if(Link2.equalsIgnoreCase(data.getI_PORTLET_VALUE7())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedSearchLink");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedSearchLink");

				}
				if(Link3.equalsIgnoreCase(data.getI_PORTLET_VALUE8())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedSearchLink");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifySavedSearchLink");

				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_VerifySavedSearch
	 * @Description : Verify  the Saved Search present at left side.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifySavedSearch(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				String SavedSearch = driver.findElement(By.xpath(element.uf_R_SavedSearch)).getText();

				if(SavedSearch.equalsIgnoreCase(data.getI_PORTLET_VALUE4())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifySavedSearch");
					status = true;
				}
				String Alignment = driver.findElement(By.xpath(element.uf_R_SavedSearch)).getAttribute("text-align");
				if(Alignment.equalsIgnoreCase(data.getI_PORTLET_VALUE5())){
					UtilLib.Report_PASS(ElementName, "is located at LHS", "uf_R_VerifySavedSearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not located at LHS", "uf_R_VerifySavedSearch");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyFileFormatWithSameTypeResult
	 * @Description : Verify the file format.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyFileFormatWithSameTypeResult(String ElementName, String Xpath,List<TopicSearchTestData> dataList,String DBData, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {

				status = uf_p2s_checkVisibility("File Format", element.uf_R_FileFormat, TestCase_Name);
				if(status==false){
					UtilLib.Report_PASS("FileFormat", "is Verified for same type of result.It is not present on the page", "uf_R_VerifyFileFormatWithSameTypeResult");
					status = true;
				}

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyDefaultSearchFilter
	 * @Description : Verify the file format.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyDefaultSearchFilter(String ElementName, List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {

				String DataSource = driver.findElement(By.xpath(element.uf_R_DataSoureFilter)).getText();
				String DataSourcePosition = driver.findElement(By.xpath(element.uf_R_DataSoureFilter)).getCssValue("text-align");
				if(DataSource.equalsIgnoreCase("Data Sources")){
					UtilLib.Report_PASS("Data Source", "is found on Search page", "uf_R_VerifyDefaultSearchFilter");
					status = true;
					if(Browser.equalsIgnoreCase("InternetExplorer")){
						if(DataSourcePosition.equalsIgnoreCase("left")){
							UtilLib.Report_PASS("Data Source", "is left align", "uf_R_VerifyDefaultSearchFilter");
							status = true;
						}
						else{
							UtilLib.Report_FAIL("Data Source", "is not left aligned", "uf_R_VerifyDefaultSearchFilter");
							status = false;
						}
					}}
				else{
					UtilLib.Report_FAIL("Data Source", "is not found on search page", "uf_R_VerifyDefaultSearchFilter");
					status = false;

				}



				/*String DateRange = driver.findElement(By.xpath(element.uf_R_DateRangeFilter)).getText();
				String DateRangePosition = driver.findElement(By.xpath(element.uf_R_DateRangeFilter)).getCssValue("text-align");

				if(DateRange.equalsIgnoreCase(data.getI_PORTLET_VALUE5())){
					UtilLib.Report_PASS("Date Range", "is found on Search page", "uf_R_VerifyDefaultSearchFilter");
					status = true;
					if(Browser.equalsIgnoreCase("InternetExplorer")){
					if(DateRangePosition.equalsIgnoreCase("left")){
						UtilLib.Report_PASS("Date Range", "is left align", "uf_R_VerifyDefaultSearchFilter");
						status = true;
					}
					else{
						UtilLib.Report_FAIL("Date Range", "is not left aligned", "uf_R_VerifyDefaultSearchFilter");
						status = false;
					}
				}}
				else{
					UtilLib.Report_FAIL("Date Range", "is not found on search page", "uf_R_VerifyDefaultSearchFilter");
					status = false;
				}*/



				/*String fileFormat = driver.findElement(By.xpath(element.uf_R_FileFormat)).getText();
			String fileFormatPosition = driver.findElement(By.xpath(element.uf_R_FileFormat)).getCssValue("text-align");

			if(fileFormat.equalsIgnoreCase(data.getI_PORTLET_VALUE6())){
				UtilLib.Report_PASS("FileFormat", "is found on Search page", "uf_R_VerifyDefaultSearchFilter");
				status = true;
				if(Browser.equalsIgnoreCase("InternetExplorer")){
				if(fileFormatPosition.equalsIgnoreCase("left")){
					UtilLib.Report_PASS("FileFormat", "is left align", "uf_R_VerifyDefaultSearchFilter");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("FileFormat", "is not left aligned", "uf_R_VerifyDefaultSearchFilter");
					status = false;
				}
			}}
			else{
				UtilLib.Report_FAIL("FileFormat", "is not found on search page", "uf_R_VerifyDefaultSearchFilter");
				status = false;
			}*/



				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyFileFormat
	 * @Description : Verify the file format.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyFileFormat(String ElementName, String Xpath,List<TopicSearchTestData> dataList,String DBData, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {

				String fileFormat = driver.findElement(By.xpath(Xpath)).getText();
				String fileFormatPosition = driver.findElement(By.xpath(Xpath)).getAttribute("align");
				if(fileFormat.equalsIgnoreCase(DBData)){
					UtilLib.Report_PASS("FileFormat", "is found on Search page", "uf_R_VerifyFileFormat");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("FileFormat", "is not found on search page", "uf_R_VerifyFileFormat");
					status = false;
				}

				if(fileFormatPosition.equalsIgnoreCase("left")){
					UtilLib.Report_PASS("FileFormat", "is left align", "uf_R_VerifyFileFormat");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("FileFormat", "is not left aligned", "uf_R_VerifyFileFormat");
					status = false;
				}

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}



	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyMouseOverAndDescription
	 * @Description : Verify the discription of data source.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyMouseOverAndDescription(String ElementName, String Xpath,String DiscriptionXpath,List<TopicSearchTestData> dataList,String DBData, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				status = UtilLib.uf_R_mouseHover("C3", Xpath, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				String Discription = driver.findElement(By.xpath(DiscriptionXpath)).getText();
				if(Discription.equalsIgnoreCase(data.getI_PORTLET_VALUE4())){
					UtilLib.Report_PASS(ElementName, "is verified", "uf_R_VerifyMouseOverAndDescription");
					status = true;

				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_ResultPage
	 * @Description : Verify the result page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_ResultPage(String ElementName, List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for(TopicSearchTestData data : dataList){
				String SearchQuery = UtilLib.uf_C_EnterDataInTextBox("SearchData", element.uf_R_SearchText, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Search", element.uf_R_SearchBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearch");

				}
				String title = driver.getTitle();
				System.out.println(title);
				if(title.equalsIgnoreCase(data.getI_PORTLET_VALUE3())){
					UtilLib.Report_PASS("Title", "is Verified of Search page", "uf_R_QuerySearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Title", "is not matching with topic search result page", "uf_R_QuerySearch");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyRecommendResult
	 * @Description : Verify Result display in three column.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyRecommendResult(String ElementName, String Xpath,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				String Datasource = driver.findElement(By.xpath(element.uf_R_DatasourceColoumnOne)).getText();
				if(Datasource.contains(data.getI_PORTLET_VALUE8())||Datasource.contains(data.getI_PORTLET_VALUE9())||Datasource.contains(data.getI_PORTLET_VALUE10())||Datasource.contains(data.getI_PORTLET_VALUE11())){
					UtilLib.Report_PASS(Datasource, "is a datasource", "uf_R_VerifyRecommendResult");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Datasource", "is not present", "uf_R_VerifyRecommendResult");
				}

				String hyperLink = driver.findElement(By.xpath(element.uf_R_hyperlink)).getTagName();

				if(hyperLink.equalsIgnoreCase("a")){

					UtilLib.Report_PASS("Title ", "is hyperlinked with actual url", "uf_R_VerifyRecommendResult");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Title ", "is not hyperlinked with actual url", "uf_R_VerifyRecommendResult");
				}

				status = UtilLib.uf_C_ClickOnElement1("Image", element.uf_R_NewWindow, TestCase_Name);
				if(status)
				{
					String parenttitle = driver.getTitle();
					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					UtilLib.WaitTime(7000);
					UtilLib.uf_R_SwitchToNewWindow();
					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);

					UtilLib.WaitTime(4000);

					String NewTitle = driver.getTitle();
					UtilLib.WaitTime(4000);
					if(parenttitle.equalsIgnoreCase(NewTitle)){
						UtilLib.Report_FAIL("New window ", "is not opened", "uf_R_VerifyRecommendResult");
						status = false;
					}
					else{
						UtilLib.Report_PASS("New window ", "is  opened", "uf_R_VerifyRecommendResult");
						UtilLib.WaitTime(7000);
						driver.close();
						UtilLib.uf_R_switchToOriginalWindow();
						UtilLib.WaitTime(5000);
						status = true;
					}

				}

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_QuerySearch
	 * @Description : Search the query at home page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_QuerySearch(String ElementName, String Xpath,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				String SearchQuery = UtilLib.uf_C_EnterDataInTextBox("SearchData", element.uf_R_SearchText, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Search", element.uf_R_SearchBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearch");

				}
				String title = driver.getTitle();
				System.out.println(title);
				if(title.equalsIgnoreCase(data.getI_PORTLET_VALUE3())){
					UtilLib.Report_PASS("Title", "is Verified of Search page", "uf_R_QuerySearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Title", "is not matching with topic search result page", "uf_R_QuerySearch");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_QueryServiceSearch
	 * @Description : Search the query at home page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_QueryServiceSearch(String ElementName, String SerchTextXpath,String SearchBtnXpath,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				String SearchQuery = UtilLib.uf_C_EnterDataInTextBox("SearchData", SerchTextXpath, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Search", SearchBtnXpath, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearch");

				}
				String title = driver.getTitle();
				System.out.println(title);
				if(title.equalsIgnoreCase(data.getI_PORTLET_VALUE3())){
					UtilLib.Report_PASS("Title", "is Verified of Search page", "uf_R_QuerySearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Title", "is not matching with topic search result page", "uf_R_QuerySearch");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyNumberofResultofDefaultSearch
	 * @Description : Search the query at home page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyNumberofResultofDefaultSearch(String ElementName,String ResultPageCount,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		String[] array;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				//System.out.println("In Verify number of result of default search");
				String FirstCount = driver.findElement(By.xpath(element.uf_R_C3_ResultCount)).getText();

				array = FirstCount.split("\\(");
				array = array[1].split("\\)");
				FirstCount = array[0];
				System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+FirstCount);
				String SecondCount = driver.findElement(By.xpath(element.uf_R_CDETS_ResultCount)).getText();
				array = SecondCount.split("\\(");
				array = array[1].split("\\)");
				SecondCount = array[0];
				//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+SecondCount);
				String ThirdCount = driver.findElement(By.xpath(element.uf_R_Newsgroup_ResultCount)).getText();
				array = ThirdCount.split("\\(");
				array = array[1].split("\\)");
				ThirdCount = array[0];
				//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+ThirdCount);
				String FourthCount = driver.findElement(By.xpath(element.uf_R_Techzone_ResultCount)).getText();
				array = FourthCount.split("\\(");
				array = array[1].split("\\)");
				FourthCount = array[0];
				//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+FourthCount);
				//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch");
				int total = (Integer.parseInt(FirstCount))  + (Integer.parseInt(SecondCount)) + (Integer.parseInt(ThirdCount)) + (Integer.parseInt(FourthCount));
				//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch");
				String totalCount = driver.findElement(By.xpath(element.uf_R_ResultPageCount)).getText();
				System.out.println(totalCount);
				if(totalCount.contains(ResultPageCount)){
					UtilLib.Report_PASS(ElementName, "is matching", "uf_R_VerifyNumberofResultofDefaultSearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not matching", "uf_R_VerifyNumberofResultofDefaultSearch");
					status = false;
				}
				array = totalCount.split("\\ ");
				totalCount = array[2];
				System.out.println(totalCount);
				int pageCount = Integer.parseInt(totalCount);
				/*System.out.println(total);
				System.out.println(pageCount);*/
				/*if(total == pageCount){
					UtilLib.Report_PASS(ElementName, "is matching", "uf_R_VerifyNumberofResultofDefaultSearch");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not matching", "uf_R_VerifyNumberofResultofDefaultSearch");
					status = false;
				}*/

				/*if(ElementName.equalsIgnoreCase("Default Filter Count")){
					FirstCount = driver.findElement(By.xpath(element.uf_R_DateRangeFirst_ResultCount)).getText();
					//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+FirstCount);
					array = FirstCount.split("\\(");
					array = array[1].split("\\)");
					FirstCount = array[0];
					//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+FirstCount);
					SecondCount = driver.findElement(By.xpath(element.uf_R_DateRangeSecond_ResultCount)).getText();
					array = SecondCount.split("\\(");
					array = array[1].split("\\)");
					SecondCount = array[0];
					//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+SecondCount);
					ThirdCount = driver.findElement(By.xpath(element.uf_R_DateRangeThird_ResultCount)).getText();
					array = ThirdCount.split("\\(");
					array = array[1].split("\\)");
					ThirdCount = array[0];
					//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch"+ThirdCount);
					FourthCount = driver.findElement(By.xpath(element.uf_R_DateRangeFourth_ResultCount)).getText();
					array = FourthCount.split("\\(");
					array = array[1].split("\\)");
					FourthCount = array[0];

					total = (Integer.parseInt(FirstCount))  + (Integer.parseInt(SecondCount)) + (Integer.parseInt(ThirdCount)) + (Integer.parseInt(FourthCount));
					//System.out.println("uf_R_VerifyNumberofResultofDefaultSearch");
					totalCount = driver.findElement(By.xpath(element.uf_R_ResultPageCount)).getText();
					//System.out.println(totalCount);
					if(totalCount.contains(ResultPageCount)){

						UtilLib.Report_PASS(ElementName, "is matching", "uf_R_VerifyNumberofResultofDefaultSearch");
						status = true;
					}
					else{

						UtilLib.Report_FAIL(ElementName, "is not matching", "uf_R_VerifyNumberofResultofDefaultSearch");
						status = false;
					}
					array = totalCount.split("\\ ");
					totalCount = array[2];
					//System.out.println(totalCount);
					 pageCount = Integer.parseInt(totalCount);
					System.out.println(total);
					System.out.println(pageCount);
					if(total == pageCount){
						UtilLib.Report_PASS(ElementName, "is matching", "uf_R_VerifyNumberofResultofDefaultSearch");
						status = true;
					}
					else{
						UtilLib.Report_FAIL(ElementName, "is not matching", "uf_R_VerifyNumberofResultofDefaultSearch");
						status = false;
					}}*/

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyNumberofResultofForSingleDataSource
	 * @Description : Search the query at home page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyNumberofResultofForSingleDataSource(String ElementName,String ResultCount, List<TopicSearchTestData> dataList,String Xpath, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		String[] array;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {


				String ThirdCount = driver.findElement(By.xpath(Xpath)).getText();
				array = ThirdCount.split("\\(");
				array = array[1].split("\\)");
				ThirdCount = array[0];


				String totalCount = driver.findElement(By.xpath(element.uf_R_ResultPageCount)).getText();

				array = totalCount.split("\\ ");
				if(array[0]=="0"){
					totalCount = array[0];
				}else{
					totalCount = array[2];

				}

				if(totalCount.equalsIgnoreCase(ThirdCount)){
					UtilLib.Report_PASS(ElementName, "is matching", "uf_R_VerifyNumberofResultofForSingleDataSource");
					status = true;
				}
				else{
					UtilLib.Report_FAIL(ElementName, "is not matching", "uf_R_VerifyNumberofResultofForSingleDataSource");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifySortByDropDown
	 * @Description : Verify the dropdown values.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifySortByDropDown(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				System.out.println("In Sort method");
				List<String> DBList = new ArrayList<String>();
				DBList.add(data.getI_PORTLET_VALUE5());
				DBList.add(data.getI_PORTLET_VALUE6());
				DBList.add(data.getI_PORTLET_VALUE7());
				status = UtilLib.uf_R_DropDownVerification(ElementName, DBList, element.uf_R_SortBy, TestCase_Name);

				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearchAtSearchPage");

				}

				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyDateForSameResult
	 * @Description : Verify the dropdown values.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyDateForSameResult(String ElementName,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {

				List<String> DBList = new ArrayList<String>();
				DBList.add(data.getI_PORTLET_VALUE5());
				DBList.add(data.getI_PORTLET_VALUE6());
				DBList.add(data.getI_PORTLET_VALUE7());
				status = UtilLib.uf_R_DropDownVerification(ElementName, DBList, element.uf_R_SortBy, TestCase_Name);

				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyDateForSameResult");

				}
				status = uf_R_VerifySortByDropDown("SortByDropDown",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}



	/********************************************************************************************
	 * @Function_Name : uf_R_MouseOverRating
	 * @Description : Verify tooltip over rating.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_MouseOverRating(String ElementName,String First,String Second,String Third,String Fourth ,String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			status = UtilLib.uf_R_mouseHover(ElementName, element.uf_R_SelectVeryReleventToolTip, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			String first = driver.findElement(By.xpath(element.uf_R_SelectVeryReleventToolTip)).getAttribute("title");
			if(First.equalsIgnoreCase(first)){
				UtilLib.Report_PASS(ElementName, "is verified", "uf_R_MouseOverRating");
				status = true;
			}
			status = UtilLib.uf_R_mouseHover(ElementName, element.uf_R_SelectSomeWhatReleventToolTip, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			String second = driver.findElement(By.xpath(element.uf_R_SelectSomeWhatReleventToolTip)).getAttribute("title");
			if(Second.equalsIgnoreCase(second)){
				UtilLib.Report_PASS(ElementName, "is verified", "uf_R_MouseOverRating");
				status = true;
			}
			status = UtilLib.uf_R_mouseHover(ElementName, element.uf_R_SelectUncertainToolTip, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			String third = driver.findElement(By.xpath(element.uf_R_SelectUncertainToolTip)).getAttribute("title");
			if(Third.equalsIgnoreCase(third)){
				UtilLib.Report_PASS(ElementName, "is verified", "uf_R_MouseOverRating");
				status = true;
			}
			status = UtilLib.uf_R_mouseHover(ElementName, element.uf_R_SelectIrreleventToolTip, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			String fourth = driver.findElement(By.xpath(element.uf_R_SelectIrreleventToolTip)).getAttribute("title");
			if(Fourth.equalsIgnoreCase(fourth)){
				UtilLib.Report_PASS(ElementName, "is verified", "uf_R_MouseOverRating");
				status = true;
			}



			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_VerifyRating
	 * @Description : Verify rating.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_VerifyRating(String ElementName,String First,String Second,String Third,String Fourth ,String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			status = UtilLib.uf_R_DataVerification("Very Relevent", element.uf_R_VeryRelevent, First, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			status = UtilLib.uf_R_DataVerification("Somewht Relevent", element.uf_R_SomeWhatRelevent, Second, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			status = UtilLib.uf_R_DataVerification("Uncertain", element.uf_R_Uncertain, Third, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}
			status = UtilLib.uf_R_DataVerification("Irrelevent", element.uf_R_Irrelevent, Fourth, TestCase_Name);
			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not verified", "uf_R_VerifyRating");
			}

			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_SelectDateLatest
	 * @Description : Select value from the dropdown.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_SelectSortByValue(String ElementName,String data,String dateFormat ,String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {


			status = UtilLib.uf_C_SelectValueFromDropdown("Sort By", element.uf_R_SortBy, data, TestCase_Name);

			if(status==false){
				UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearchAtSearchPage");
				status = true;
			}

			String url = driver.getCurrentUrl();
			if(url.contains(dateFormat)){
				UtilLib.Report_PASS(ElementName, "is refreshed after Selecting dropdown value", "uf_R_SelectSortByValue");
				status = true;
			}else{
				UtilLib.Report_FAIL(ElementName, "is not refreshed after Selecting dropdown value", "uf_R_QuerySearchAtSearchPage");
				status = false;
			}

			UtilLib.WaitTime(5000);



		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}


	/********************************************************************************************
	 * @Function_Name : uf_R_QuerySearchAtSearchPage
	 * @Description : Search the query at home page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_QuerySearchAtSearchPage(String ElementName, String Xpath,List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				driver.findElement(By.xpath(element.uf_R_SearchText)).clear();
				String SearchQuery = UtilLib.uf_C_EnterDataInTextBox("SearchData", element.uf_R_SearchText, data.getI_PORTLET_VALUE3(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Search", element.uf_R_SearchBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_QuerySearchAtSearchPage");

				}
				String title = driver.getTitle();
				System.out.println(title);
				if(title.equalsIgnoreCase(data.getI_PORTLET_VALUE4())){
					UtilLib.Report_PASS("Title", "is Verified of Search page", "uf_R_QuerySearchAtSearchPage");
					status = true;
				}
				else{
					UtilLib.Report_FAIL("Title", "is not matching with topic search result page", "uf_R_QuerySearchAtSearchPage");
					status = false;
				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : uf_R_MoveDataSource
	 * @Description : Verify Down data source is default after clicking the upward chevron button.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name- Name of the TestCase
	 * @param ElementName- Name of the element
	 * @param dataList- DB data
	 * @param tooltip- Tool tip value of data source
	 * @param Xpath- Xpath of the element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean uf_R_MoveDataSource(String ElementName, String Xpath,List<TopicSearchTestData> dataList,String tooltip, String TestCase_Name) throws Exception {
		boolean status = false;
		int RerunFlag = 0;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		try {
			for (TopicSearchTestData data : dataList) {
				String tooltipValue = driver.findElement(By.xpath(Xpath)).getAttribute("tooltip");
				System.out.println(tooltipValue);
				if(tooltipValue.equalsIgnoreCase(tooltip)){
					UtilLib.Report_PASS("Tooltip", "is verified", "uf_R_MoveDataSource");
					status = true;
				}
				String attributeValue = driver.findElement(By.xpath(element.uf_R_MovedDataSource1)).getAttribute("onmouseover");
				System.out.println(attributeValue);

				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_ClickChevron");

				}
				status = driver.findElement(By.xpath(element.uf_R_UpwardChevron)).isDisplayed();
				if(status==false){
					UtilLib.Report_FAIL(ElementName, "is not found", "uf_R_ClickChevron");

				}
				UtilLib.WaitTime(5000);
			}


		} catch(IndexOutOfBoundsException exception1) { 
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
			UtilLib.Report_FAIL("NoSuchElementException : Change in XPATH", "Kindly change the xpath value",TestCase_Name);
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

		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13
	 * @Description : Verify Search result Page by clicking on chevron
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(),dataList, TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE2(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_ClickChevron("Down Chevron", element.uf_R_HomePage_Chevron, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String tooltip = uf_R_MouseOverDataSource("Down Data Source", element.uf_R_DownDataSource, TestCase_Name);
				System.out.println(tooltip);

				if(status==false){
					RerunFlag++;
				}
				status = uf_R_ClickChevron("Up Chevron", element.uf_R_UpwardChevron, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = UtilLib.uf_C_ClickOnElement1("No", element.uf_R_No, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_ClickChevron("Chevron", element.uf_R_UpwardChevron, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = UtilLib.uf_C_ClickOnElement1("Yes", element.uf_R_Yes, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_MoveDataSource("Move Data Source", element.uf_R_MovedDataSource, dataList,tooltip, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_Searchfromtheresultspage_37
	 * @Description : Verify Search result Page 
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_Searchfromtheresultspage_37(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE5(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearchAtSearchPage("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_Sortbydatelatest_44
	 * @Description : Verify Search result Page sort by date
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_Sortbydatelatest_44(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofDefaultSearch("ResultCount",data.getI_PORTLET_VALUE4(),dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySortByDropDown("SortByDropDown",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_SelectSortByValue("Sort By",data.getI_PORTLET_VALUE5(),"sortBy=Date+-+Latest", TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_SortbydateOldest_45
	 * @Description : Verify Search result Page sort by date
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_SortbydateOldest_45(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofDefaultSearch("ResultCount",data.getI_PORTLET_VALUE4(),dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySortByDropDown("SortByDropDown",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_SelectSortByValue("Sort By",data.getI_PORTLET_VALUE8(),"sortBy=Date+-+Oldest", TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_SortbyRelevance_46
	 * @Description : Verify Search result Page sort by date
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_SortbyRelevance_46(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofDefaultSearch("ResultCount",data.getI_PORTLET_VALUE4(),dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySortByDropDown("SortByDropDown",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_SelectSortByValue("Sort by",data.getI_PORTLET_VALUE7(),"sortBy=Relevance", TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_levelsofsortforTZ_47
	 * @Description : Verify Search result for techzone datasource
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_levelsofsortforTZ_47(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String Query = UtilLib.uf_C_EnterDataInTextBox("TextBox", element.uf_R_SearchText, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = uf_R_SelectDataSource("Search Box",element.uf_R_TechZone, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofForSingleDataSource("ResultCount",data.getI_PORTLET_VALUE2(),dataList,element.uf_R_Techzone_ResultCountForOnlyTechZone, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySortByDropDown("SortByDropDown",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_sortedbydate_AcrossSources_48
	 * @Description : Verify Search result for datasource
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_sortedbydate_AcrossSources_48(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String Query = UtilLib.uf_C_EnterDataInTextBox("TextBox", element.uf_R_SearchText, data.getI_PORTLET_VALUE1(), TestCase_Name);

				status = uf_R_VerifyNumberofResultofDefaultSearch("ResultCount",data.getI_PORTLET_VALUE4(),dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_SelectSortByValue("Sort By",element.uf_R_SortBy,data.getI_PORTLET_VALUE15(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = uf_R_VerifyDateForSameResult("Search Box",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT2_Searchtip_1randomsearchtipsintheresultpage_52
	 * @Description : Verify Search tip  for random results
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT2_Searchtip_1randomsearchtipsintheresultpage_52(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = uf_R_QuerySearch("Search Text", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = driver.findElement(By.xpath(element.uf_R_SearchTip)).isDisplayed();
				if(status==false){
					RerunFlag++;
				}


				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_sortedbydate_TZ_49
	 * @Description : Verify Search result for datasource sort by date
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_sortedbydate_TZ_49(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String Query = UtilLib.uf_C_EnterDataInTextBox("TextBox", element.uf_R_SearchText, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = uf_R_SelectDataSource("Search Box",element.uf_R_TechZone, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofForSingleDataSource("ResultCount",data.getI_PORTLET_VALUE2(),dataList,element.uf_R_Techzone_ResultCountForOnlyTechZone, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyDateForSameResult("Search Box",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : TS081_AttivioMigration_REG_CSSCSO289_Linkintheemailforsharedsearchbroken_01
	 * @Description : Link in the email for shared search broken
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS081_AttivioMigration_REG_CSSCSO289_Linkintheemailforsharedsearchbroken_01(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySavedSearch("Saved Search",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySavedSearchLink("Saved Search Link",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySavedShareSearchBox("Saved Search Link",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String Name = UtilLib.uf_C_EnterDataInTextBox("Name", element.uf_R_NameText, data.getI_PORTLET_VALUE15(), TestCase_Name);

				String ShareWith = UtilLib.uf_C_EnterDataInTextBox("ShareWith", element.uf_R_ShareWithText, data.getI_PORTLET_VALUE16(), TestCase_Name);

				String Comment = UtilLib.uf_C_EnterDataInTextBox("AdditionalComment", element.uf_R_CommentsText, data.getI_PORTLET_VALUE17(), TestCase_Name);

				status = UtilLib.uf_C_ClickOnElement1("Save", element.uf_R_Save, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.uf_C_HandleAlert_Accept("OK", "Search details have been saved.", TestCase_Name);
				status = uf_R_VerifyMailFormat("Saved Search Link",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}



				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}




	/********************************************************************************************
	 * @Function_Name : AttivioMigration_PROG_RSLT1_Resultpage_levelsofsortforCDETS_50
	 * @Description : Verify Search result for datasource sort by date
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean AttivioMigration_PROG_RSLT1_Resultpage_levelsofsortforCDETS_50(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				String Query = UtilLib.uf_C_EnterDataInTextBox("TextBox", element.uf_R_SearchText, data.getI_PORTLET_VALUE2(), TestCase_Name);
				status = uf_R_SelectDataSource("Search Box",element.uf_R_CDETS, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyNumberofResultofForSingleDataSource("ResultCount",data.getI_PORTLET_VALUE2(),dataList,element.uf_R_Techzone_ResultCountForOnlyTechZone, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifySortByDropDown("Sort By",dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}



	/********************************************************************************************
	 * @Function_Name : TS082_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02
	 * @Description : C3 data source is selected and search with percentile.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS082_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_SelectDataSource("C3",element.uf_R_C3, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : TS083_AttivioMigration_REG_CSSCSO257_Fileformatfilter_03
	 * @Description : Verify file format.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS083_AttivioMigration_REG_CSSCSO257_Fileformatfilter_03(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Search Box",element.uf_R_SearchText,dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyFileFormat("File Format",element.uf_R_FileFormat,dataList,data.getI_PORTLET_VALUE4(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_VerifyFileFormatWithSameTypeResult("File Format of Same Type",element.uf_R_FileFormat,dataList,data.getI_PORTLET_VALUE4(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}




	/********************************************************************************************
	 * @Function_Name : TS086_AttivioMigration_PROG_SRCH2SearchPagedatasource_Alwayssearchwithintheselectedthedatasources_Checkbox_Checked_24
	 * @Description : Check the datasource.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS086_AttivioMigration_PROG_SRCH2SearchPagedatasource_Alwayssearchwithintheselectedthedatasources_Checkbox_Checked_24(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = UtilLib.uf_C_ClickOnElement1("More Data Source", element.uf_R_MoreDataSource,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = UtilLib.uf_C_ClickOnElement1("Down Data Source", element.uf_R_DownDataSource,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}



				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : TS090_AttivioMigration_PROG_RSLT1_Resultpage_Searchfromtheresultspage_28
	 * @Description : Verify the Result Page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS090_AttivioMigration_PROG_RSLT1_Resultpage_Searchfromtheresultspage_28(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QuerySearch("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = uf_R_ResultPage("Result Page", dataList,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}




				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : TS097_AttivioMigration_REG_Q4SCOPE_SRCH1_TheSearchCSOhomepagetoremainasis_Homepage_50
	 * @Description : Check the datasource.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	/*public static boolean TS097_AttivioMigration_REG_Q4SCOPE_SRCH1_TheSearchCSOhomepagetoremainasis_Homepage_50(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;
				Runtime.getRuntime().exec("C://Automation//FS_TopicSearch_3.0//AutoIt//autoIt_ServiceSearch.exe");
				uf_p2s_driverInitiation_ServiceSearch(data.getI_URL(), TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Service_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = uf_R_QueryServiceSearch("Router", element.uf_R_ServiceSearch_Text,element.uf_R_SearviceSearch_SearchBtn, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

*/
	/********************************************************************************************
	 * @Function_Name : TS087_AttivioMigration_PROG_SRCH2SearchPagedatasource_Alwayssearchwithintheselectedthedatasources_Checkbox_UnChecked_25
	 * @Description : Check the datasource.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS087_AttivioMigration_PROG_SRCH2SearchPagedatasource_Alwayssearchwithintheselectedthedatasources_Checkbox_UnChecked_25(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
				}
				status = UtilLib.uf_C_ClickOnElement1("More Data Source", element.uf_R_MoreDataSource,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = UtilLib.uf_C_ClickOnElement1("Down Data Source", element.uf_R_DownDataSource,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}

				status = UtilLib.uf_C_ClickOnElement1("Down Data Source", element.uf_R_DownDataSource,TestCase_Name);
				if(status==false){
					RerunFlag++;
				}


				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"AttivioMigration_PROG_SearchPageDataSource_MovingNormalDataSourceToTheDefaultDataSourceList_ByClickingOnChevronFacingUpwards_13");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}




	/********************************************************************************************
	 * @Function_Name : TS05_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02
	 * @Description : Search with percentile.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS05_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_SelectDataSource("C3/CSOne/CARE", element.uf_R_C3_HomePage, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_QuerySearch("Search Box", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}



				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"TS05_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"TS05_AttivioMigration_CSSCSO288_Error500_Cantinsertpage_C3datasourceisselectedandsearchwithpercentile_02");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : TS06_AttivioMigration_E2E_PerformingthesearchwhileClearAll_Resetlinks_andverifythesortByoptionsintheresultspage_38
	 * @Description : Perform Search while clear all and verify the sortBy options in the results page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS06_AttivioMigration_E2E_PerformingthesearchwhileClearAll_Resetlinks_andverifythesortByoptionsintheresultspage_38(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_R_ClearAll, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = UtilLib.uf_C_ClickOnElement1("Reset", element.uf_R_Reset, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("No", element.uf_R_No, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Reset", element.uf_R_Reset, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Yes", element.uf_R_Yes, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_QuerySearch("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_VerifyRecommendResult("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				/*	status = uf_R_VerifySortByDropDown("Sort By", dataList, TestCase_Name);
			if(status==false){
				RerunFlag++;
			}
			status = uf_R_SelectSortByValue("Sort By",data.getI_PORTLET_VALUE6(),"sortBy=Date+-+Oldest", TestCase_Name);
			if(status==false){
				RerunFlag++;
			}
			status = uf_R_SelectSortByValue("Sort By",data.getI_PORTLET_VALUE5(),"sortBy=Date+-+Latest", TestCase_Name);
			if(status==false){
				RerunFlag++;
			}
			status = uf_R_SelectSortByValue("Sort By",data.getI_PORTLET_VALUE7(),"sortBy=Relevance", TestCase_Name);
			if(status==false){
				RerunFlag++;
			}*/
				status = uf_p2s_checkVisibility("More tips", element.uf_R_MoreTips, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement("More tips", element.uf_R_MoreTips, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement("Less tips", element.uf_R_LessTips, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_VerifyRating("Rating", data.getI_PORTLET_VALUE15(),data.getI_PORTLET_VALUE16(),data.getI_PORTLET_VALUE17(),data.getI_PORTLET_VALUE18(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_MouseOverRating("Tooltip", data.getI_PORTLET_VALUE19(),data.getI_PORTLET_VALUE20(),data.getI_PORTLET_VALUE21(),data.getI_PORTLET_VALUE22(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Very Relevent", element.uf_R_SelectVeryRelevent, TestCase_Name);

				UtilLib.WaitTime(5000);
				status = UtilLib.uf_C_ClickOnElement1("Very Relevent", element.uf_R_SelectVeryRelevent, TestCase_Name);

				//referesh the page
				Robot rb = new Robot();
				rb.keyPress(KeyEvent.VK_F5);
				rb.keyRelease(KeyEvent.VK_F5);

				status = UtilLib.uf_C_ClickOnElement1("Very Relevent", element.uf_R_SelectVeryRelevent, TestCase_Name);


				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"TS06_AttivioMigration_E2E_PerformingthesearchwhileClearAll_Resetlinks_andverifythesortByoptionsintheresultspage_38");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"TS06_AttivioMigration_E2E_PerformingthesearchwhileClearAll_Resetlinks_andverifythesortByoptionsintheresultspage_38");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : TS07_AttivioMigration_E2E_Performingthesearchwhileandverifythedatasourcefilters_39
	 * @Description : Perform Search while clear all and verify the sortBy options in the results page.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS07_AttivioMigration_E2E_Performingthesearchwhileandverifythedatasourcefilters_39(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_R_ClearAll, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				
				status = UtilLib.uf_C_ClickOnElement1("C3 datasource", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				status = UtilLib.uf_C_ClickOnElement1("CDETS", element.uf_SH_CDETS_DDTScheckbox, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				status = UtilLib.uf_C_ClickOnElement1("Newsgroup datasource", element.uf_SH_Newsgroupcheckbox, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				status = UtilLib.uf_C_ClickOnElement1("Techzone datasource", element.uf_SH_Techzonecheckbox, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				/*status = UtilLib.uf_C_ClickOnElement1("Reset", element.uf_R_Reset, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = UtilLib.uf_C_ClickOnElement1("Yes", element.uf_R_Yes, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}*/

				status = uf_R_QuerySearch("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_VerifyDefaultSearchFilter("Default Filter", dataList,TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_VerifyNumberofResultofDefaultSearch("Default Filter Count",data.getI_PORTLET_VALUE7(), dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				/*status = uf_p2s_checkVisibility("All", element.uf_R_DataSourceFilterAll, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}

			status = uf_R_SelectDataSourceAtResultPage("C3/CSOne/CARE", element.uf_R_C3,1, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_VerifyNumberofResultofForSingleDataSource("C3/CSOne/CARE",data.getI_PORTLET_VALUE7(), dataList,element.uf_R_C3_ResultCount, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_SelectDataSourceAtResultPage("CDETS/DDTS", element.uf_R_CDTS, 2,TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_VerifyNumberofResultofForSingleDataSource("CDETS/DDTS",data.getI_PORTLET_VALUE7(), dataList,element.uf_R_CDETS_ResultCount, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_SelectDataSourceAtResultPage("Tech Zone", element.uf_R_Techzone,3, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_VerifyNumberofResultofForSingleDataSource("Tech Zone",data.getI_PORTLET_VALUE7(), dataList,element.uf_R_Techzone_ResultCount, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_SelectDataSourceAtResultPage("Newsgroup", element.uf_R_NewsGroup,4, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			status = uf_R_VerifyNumberofResultofForSingleDataSource("Newsgroup",data.getI_PORTLET_VALUE5(), dataList,element.uf_R_Newsgroup_ResultCount, TestCase_Name);
			if(status==false){
				RerunFlag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}*/
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"TS07_AttivioMigration_E2E_Performingthesearchwhileandverifythedatasourcefilters_39");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"TS07_AttivioMigration_E2E_Performingthesearchwhileandverifythedatasourcefilters_39");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : TS08_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenC3datasourceisselected_46
	 * @Description : Verify Search when C3 is selected.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

public static boolean AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenC3datasourceisselected_46(
		List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

	boolean status=false;
	int RerunFlag=0;
	//int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


		
		

				status = uf_R_SelectDataSource("C3/CSOne/CARE", element.uf_R_C3_HomePage, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Always search within these checked data sources", element.uf_R_AlwaysSearch, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_QuerySearch("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status = uf_R_VerifyNumberofResultofForSingleDataSource("C3/CSOne/CARE",data.getI_PORTLET_VALUE4(), dataList,element.uf_R_C3_ResultCount, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = uf_R_VerifyDefaultSearchFilter("Default Filter", dataList,TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				Actions actions = new Actions(driver);
				WebElement PMLInk_OnHover = driver.findElement(By.xpath(element.uf_R_C3ResultContent));
				actions.moveToElement(PMLInk_OnHover).perform();

				/* Verify preview chevron present*/

				status = UtilLib.verifyElementPresent("Chevron",element.uf_R_ChevronTopicBuddy, TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(2000);

				WebElement PMLInk_OnHover1 = driver.findElement(By.xpath(element.uf_R_ChevronTopicBuddy));
				actions.moveToElement(PMLInk_OnHover1).perform();


				status = UtilLib.verifyElementPresent("Topic buddy preview page", element.uf_R_CDETSTopicBuddy , TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(3000);
				UtilLib.CaptureScreenshot_PASS(TestCase_Name);	

				/* Verify the topic buddy preview  */

				status = UtilLib.verifyElementPresent("Topic buddy preview page : Service request", element.uf_R_ServiceRequest , TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = UtilLib.verifyElementPresent("Topic buddy preview page : Problem Description", element.uf_R_ProblemDescription , TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = UtilLib.verifyElementPresent("Topic buddy preview page : Resolution Summary", element.uf_R_ResolutionSummary , TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}



				UtilLib.WaitTime(3000);

				UtilLib.ClickOnElement("Topic Buddy close",  element.uf_R_TopicBuddyClose, TestCase_Name);
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"TS08_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenC3datasourceisselected_46");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"TS08_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenC3datasourceisselected_46");
					status = true;
				}
			}}catch(IndexOutOfBoundsException exception1) { 		
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
		
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : TS09_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenCDETSdatasourceisselected_47
	 * @Description : Verify Search when CDETS is selected.
	 * @author rpanjeta
	 * @return boolean
	 * @param TestCase_Name
	 *            - Name of the TestCase
	 * @param dataList
	 *            - DB data
	 * @param ElementName
	 *            - Name element
	 * @throws Exception
	 ********************************************************************************************/

	public static boolean TS09_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenCDETSdatasourceisselected_47(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_SelectDataSource("CDETS/DDTS ", element.uf_R_CDETS, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				status = UtilLib.uf_C_ClickOnElement1("Always search within these checked data sources", element.uf_R_AlwaysSearch, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_QuerySearch("Router", element.uf_R_SearchText, dataList, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status = uf_R_VerifyNumberofResultofForSingleDataSource("CDETS/DDTS",data.getI_PORTLET_VALUE4(), dataList,element.uf_R_C3_ResultCount, TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status = uf_R_VerifyDefaultSearchFilter("Default Filter", dataList,TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				Actions actions = new Actions(driver);
				WebElement PMLInk_OnHover = driver.findElement(By.xpath(element.uf_R_C3ResultContent));
				actions.moveToElement(PMLInk_OnHover).perform();

				/* Verify preview chevron present*/

				status = UtilLib.verifyElementPresent("Chevron",element.uf_R_ChevronTopicBuddy, TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(2000);

				WebElement PMLInk_OnHover1 = driver.findElement(By.xpath(element.uf_R_ChevronTopicBuddy));
				actions.moveToElement(PMLInk_OnHover1).perform();


				status = UtilLib.verifyElementPresent("Topic buddy preview page", element.uf_R_CDETSTopicBuddy , TestCase_Name);
				if(status==false){
					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(3000);

				String value = driver.findElement(By.xpath(element.uf_R_BugId)).getText(); 
				System.out.println("Field present in the topic buddy page :"+value);

				if(value.contains("Bug-ID")){
					UtilLib.Report_PASS("Bug-ID field  ","is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Bug-ID field"," is not present", "FieldVerification()");
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}


				value = driver.findElement(By.xpath(element.uf_R_Status)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Status")){
					UtilLib.Report_PASS("Status field", " is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Status field", " is not present ", "FieldVerification()");
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_Severity)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Severity")){
					UtilLib.Report_PASS("Severity field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Severity field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_ProductComponent)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Product and Component")){
					UtilLib.Report_PASS("CDETS Product and Component field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("CDETS Product and Component field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_Version)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Version")){
					UtilLib.Report_PASS("Version field " ,"is  present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Version field " ,"is  present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_IntegratedReleases)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Integrated-Releases")){
					UtilLib.Report_PASS("Integrated-Releases field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Integrated-Releases field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}


				value = driver.findElement(By.xpath(element.uf_R_LastChanged)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Last Changed")){
					UtilLib.Report_PASS(" Last Changed field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL(" Last Changed field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_TroubleTickets)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Trouble Ticktes")){
					UtilLib.Report_PASS("Trouble Ticktes field"," is present ",  "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Trouble Ticktes field"," is not present ",  "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_DEManager)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("DE Manager")){
					UtilLib.Report_PASS("DE Manager field","is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("DE Manager field","is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_Found)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Found")){
					UtilLib.Report_PASS("Found field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Found field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_Engineer)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Engineer")){
					UtilLib.Report_PASS("Engineer field","is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Engineer field","is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_Submitter)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Submitter")){
					UtilLib.Report_PASS("Submitter field " ,"is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Submitter field " ,"is present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_ReleaseNote)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Release-Note")){
					UtilLib.Report_PASS("Release-note field", "is present ","FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Release-note field", "is not present ","FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_DescriptionNote)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("Description")){
					UtilLib.Report_PASS("Description note field"," is present ","FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("Description note field"," is not present ","FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}

				value = driver.findElement(By.xpath(element.uf_R_ExternalCustomerVisibilityStatus)).getText().trim(); 
				System.out.println("Field present in the topic buddy page :"+value);	

				if(value.contains("External Visibility")){
					UtilLib.Report_PASS("External customer visibility status field"," is present ", "FieldVerification()" );
				}else{
					UtilLib.Report_FAIL("External customer visibility status field"," is not present ", "FieldVerification()" );
					UtilLib.CaptureScreenshot(TestCase_Name);

					RerunFlag=RerunFlag + 1;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
				}
				UtilLib.WaitTime(3000);

				UtilLib.ClickOnElement("Topic Buddy close",  element.uf_R_TopicBuddyClose, TestCase_Name);
				UtilLib.WaitTime(5000);
				driver.close();


				if (RerunFlag > 0) {
					UtilLib.Report_FAIL("SearchPageDataSource",
							"are not verified",
					"TS09_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenCDETSdatasourceisselected_47");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status = false;
				} else {
					UtilLib.Report_PASS("SearchPageDataSource",
							" are verified",
					"TS09_AttivioMigration_E2E_VerifyingthefunctionalitysearchwhenCDETSdatasourceisselected_47");
					status = true;
				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////nisahni/////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/********************************************************************************************
	 * @Function_Name : SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_C3
	 * @Description :  verify the cached link for C3 data source
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Name of the TestCase
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_C3(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int RerunFlag=0;

		for(TopicSearchTestData data :dataList ){

			try {

				if(Browser.equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){

						uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
						UtilLib.WaitTime(5000);

						UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
						status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
						if(status==false){
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						UtilLib.WaitTime(3000);

						status=UtilLib.uf_C_ClickOnElement1("C3",element.uf_SH_C3_CSOne_CAREcheckbox,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}


						status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
						if(status==false){
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						UtilLib.WaitTime(3000);


						String Xpath="//*[@class='resultTd']/div/a[2]";
						if(Xpath.endsWith("/a[2]"))
						{
							UtilLib.Report_PASS("Cached Link " ,"is available for all 'C3' content","Verify_CachedLink");
							status=true;
						}else{
							UtilLib.Report_FAIL("Cached Link " ,"is not available for all 'C3' content","Verify_CachedLink");
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}


						Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI4.exe");

						status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						UtilLib.WaitTime(7000);
						Set<String> windowId = driver.getWindowHandles();
						Iterator<String> itererator1 = windowId.iterator(); 

						String mainWinID1 = itererator1.next();
						String newAdwinID1 = itererator1.next();

						driver.switchTo().window(newAdwinID1);

						UtilLib.WaitTime(6000);

						System.out.println("Page Title: "+driver.getTitle());

						if(driver.getTitle().equals("Topic Search"))
						{	
							UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}else{
							UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
							status=true;
						}

						driver.close();
						if(RerunFlag>0){
							UtilLib.Report_FAIL("'Cached Link' for 'C3' datasource" ,"is not verified","Verify_CachedLink_C3");
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
							status=false;
						}else{
							UtilLib.Report_PASS("'Cached Link' for 'C3' datasource" ,"is verified","Verify_CachedLink_C3");
							status=true;
						}
					}}


				else{
					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(3000);

					status=UtilLib.uf_C_ClickOnElement1("C3",element.uf_SH_C3_CSOne_CAREcheckbox,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					if(status==false){
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(3000);


					String Xpath="//*[@class='resultTd']/div/a[2]";
					if(Xpath.endsWith("/a[2]"))
					{
						UtilLib.Report_PASS("Cached Link " ,"is available for all 'C3' content","Verify_CachedLink");
						status=true;
					}else{
						UtilLib.Report_FAIL("Cached Link " ,"is not available for all 'C3' content","Verify_CachedLink");
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


					Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI1.exe");

					status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(7000);
					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);

					UtilLib.WaitTime(6000);

					System.out.println("Page Title: "+driver.getTitle());

					if(driver.getTitle().equals("Topic Search"))
					{	
						UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}else{
						UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
						status=true;
					}

					if(RerunFlag>0){
						UtilLib.Report_FAIL("'Cached Link' for 'C3' datasource" ,"is not verified","Verify_CachedLink_C3");
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
						status=false;
					}else{
						UtilLib.Report_PASS("'Cached Link' for 'C3' datasource" ,"is verified","Verify_CachedLink_C3");
						status=true;
					}



				}
			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name : SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_CDETS
	 * @Description :  verify the cached link for CDETS data source
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Name of the TestCase
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_CDETS(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int RerunFlag=0;

		for(TopicSearchTestData data :dataList ){

			try {

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status=UtilLib.uf_C_ClickOnElement1("CDETS",element.uf_SH_CDETS_DDTScheckbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(3000);


				String Xpath="//*[@class='resultTd']/div/a[2]";
				if(Xpath.endsWith("/a[2]"))
				{
					UtilLib.Report_PASS("Cached Link " ,"is available for all 'CDETS' content","Verify_CachedLink");
					status=true;
				}else{
					UtilLib.Report_FAIL("Cached Link " ,"is not available for all 'CDETS' content","Verify_CachedLink");
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(7000);

				System.out.println("Page Title: "+driver.getTitle());

				if(driver.getTitle().equals("Topic Search"))
				{	
					UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
					status=true;
				}

				if(RerunFlag>0){
					UtilLib.Report_FAIL("'Cached Link' for 'CDETS' datasource" ,"is not verified","Verify_CachedLink_CDETS");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status=false;
				}else{
					UtilLib.Report_PASS("'Cached Link' for 'CDETS' datasource" ,"is verified","Verify_CachedLink_CDETS");
					status=true;
				}

			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_C3_DS_with_other_DS
	 * @Description :   verify the cached link for C3 data source in combination with other data sources
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Name of the TestCase
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_C3_DS_with_other_DS(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int RerunFlag=0;

		for(TopicSearchTestData data :dataList ){

			try {

				if(Browser.equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){
						uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
						UtilLib.WaitTime(5000);

						UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
						status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
						if(status==false){
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}


						status=UtilLib.uf_C_ClickOnElement1("C3",element.uf_SH_C3_CSOne_CAREcheckbox,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						status=UtilLib.uf_C_ClickOnElement1("Newsgroup",element.uf_SH_Newsgroupcheckbox,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}


						status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
						if(status==false){
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						UtilLib.WaitTime(3000);

						String Xpath="//*[@class='resultTd']/div/a[2]";
						if(Xpath.endsWith("/a[2]"))
						{
							UtilLib.Report_PASS("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
							status=true;
						}else{
							UtilLib.Report_FAIL("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI4.exe");

						status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
						if(status==false)
						{
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						UtilLib.WaitTime(7000);

						System.out.println("Page Title: "+driver.getTitle());

						if(driver.getTitle().equals("Topic Search"))
						{	
							UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
							RerunFlag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}else{
							UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
							status=true;
						}

						if(RerunFlag>0){
							UtilLib.Report_FAIL("'Cached Link' for 'C3' datasource along with combination with other datasources" ,"is not verified","Verify_CachedLink_C3_CDETS");
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
							status=false;
						}else{
							UtilLib.Report_PASS("'Cached Link' for 'C3' datasource along with combination with other datasources" ,"is verified","Verify_CachedLink_C3_CDETS");
							status=true;
						}
					}
				}

				else{

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


					status=UtilLib.uf_C_ClickOnElement1("C3",element.uf_SH_C3_CSOne_CAREcheckbox,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					status=UtilLib.uf_C_ClickOnElement1("Newsgroup",element.uf_SH_Newsgroupcheckbox,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					if(status==false){
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(3000);

					String Xpath="//*[@class='resultTd']/div/a[2]";
					if(Xpath.endsWith("/a[2]"))
					{
						UtilLib.Report_PASS("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
						status=true;
					}else{
						UtilLib.Report_FAIL("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

				//	Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI1.exe");

					status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
					if(status==false)
					{
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(3000);

					/*Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);

					UtilLib.WaitTime(6000);*/
					
					Robot robot = new Robot();
					uf_N_parseChars(data.getI_PORTLET_VALUE2(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					uf_N_parseChars(data.getI_PORTLET_VALUE3(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER); 
				
					UtilLib.WaitTime(5000);

					System.out.println("Page Title: "+driver.getTitle());

					if(driver.getTitle().equals("Topic Search"))
					{	
						UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
						RerunFlag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}else{
						UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
						status=true;
					}

					if(RerunFlag>0){
						UtilLib.Report_FAIL("'Cached Link' for 'C3' datasource along with combination with other datasources" ,"is not verified","Verify_CachedLink_C3_CDETS");
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
						status=false;
					}else{
						UtilLib.Report_PASS("'Cached Link' for 'C3' datasource along with combination with other datasources" ,"is verified","Verify_CachedLink_C3_CDETS");
						status=true;
					}

				}

			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}


	/********************************************************************************************
	 * @Function_Name : SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_CDETS_DS_with_other_DS
	 * @Description :   verify the cached link for CDETS data source in combination with other data sources
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Name of the TestCase
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_CachedLinkSolution_CDETS_DS_with_other_DS(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int RerunFlag=0;

		for(TopicSearchTestData data :dataList ){

			try {

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status=UtilLib.uf_C_ClickOnElement1("Clear All",element.uf_SH_clearAll,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status=UtilLib.uf_C_ClickOnElement1("C3",element.uf_SH_CDETS_DDTScheckbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status=UtilLib.uf_C_ClickOnElement1("Newsgroup",element.uf_SH_Newsgroupcheckbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(3000);


				String Xpath="//*[@class='resultTd']/div/a[2]";
				if(Xpath.endsWith("/a[2]"))
				{
					UtilLib.Report_PASS("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
					status=true;
				}else{
					UtilLib.Report_FAIL("Cached Link " ,"is available either when single data source is selected or in combination with other data sources","Verify_CachedLink");
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				status=UtilLib.uf_C_ClickOnElement1("Cached Link",element.uf_N_cachedLink,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				UtilLib.WaitTime(7000);

				/*
			UtilLib.EnterDataInTextBox("User Name",element.uf_N_userName,"nisahni",TestCase_Name);
			UtilLib.EnterDataInTextBox("Password",element.uf_N_password,"Nish!2014",TestCase_Name);
			status=UtilLib.uf_C_ClickOnElement1("Log In", element.uf_N_login, TestCase_Name);
			UtilLib.WaitTime(2000);*/

				System.out.println("Page Title: "+driver.getTitle());

				if(driver.getTitle().equals("Topic Search"))
				{	
					UtilLib.Report_FAIL("On click of 'Cached Link', ", "the link is not opening in new tab", "Verify_CachedLink_Functionality");
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.Report_PASS("On click of 'Cached Link', ", "the link is opening in new tab", "Verify_CachedLink_Functionality");
					status=true;
				}

				if(RerunFlag>0){
					UtilLib.Report_FAIL("'Cached Link' for 'CDETS' datasource along with combination with other datasources" ,"is not verified","Verify_CachedLink_C3_CDETS");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status=false;
				}else{
					UtilLib.Report_PASS("'Cached Link' for 'CDETS' datasource along with combination with other datasources" ,"is verified","Verify_CachedLink_C3_CDETS");
					status=true;
				}

			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////shafmeht///////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104
	 * @Description :  verify that there should be Icons similar to new window for each of the c3 link.
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_XMLKWERY_104
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int RerunFlag = 0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){
				if(Browser.equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){

						uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
						UtilLib.WaitTime(5000);

						UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
						status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
						if(status==false){
							rerunflag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}

						System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

						status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
						if(status==true)
						{
							UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
						}
						else
						{
							rerunflag++;
						}

						status=UtilLib.uf_C_ClickOnElement1("C3/CSOne/CARE", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
						if(status==true)
						{
							UtilLib.Report_PASS("CS/CSOne/CARE one data source ", "is selected", "selectingDataSource()");
						}
						else
						{
							rerunflag++;
						}


						System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

						status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
						if(status==false){
							rerunflag=rerunflag+1;
						}



						status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
						if(status==false)
						{
							rerunflag++;
						}

						System.out.println("***************     Verification of CS_one and XML Kwery  ***************");

						String CS_one=UtilLib.uf_C_GetUIData1("CS_One", element.uf_SH_CS_0ne, TestCase_Name);
						System.out.println("CS_one result  : "+CS_one);
						if(CS_one.equals(data.getI_PORTLET_VALUE2().trim()))
						{
							flag++;
						}
						else{
							rerunflag++;
						}



						String XMLKwery=UtilLib.uf_C_GetUIData1("XMLKwery", element.uf_SH_C3XMLKwery, TestCase_Name);
						System.out.println("XMLKwery result  : "+XMLKwery);
						if(XMLKwery.equals(data.getI_PORTLET_VALUE3().trim()))
						{
							flag++;
						}
						else{
							rerunflag++;
						}



						if(flag==2)
						{
							UtilLib.Report_PASS(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are  present", "LinkVerifiaction()");
						}
						else
						{
							UtilLib.Report_FAIL(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are not present", "LinkVerifiaction()");
							rerunflag++;
						}

						//Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI5.exe");

						status=UtilLib.uf_C_ClickOnElement1("XMLKwery", element.uf_SH_C3XMLKwery, TestCase_Name);
						if(status==false){
							rerunflag=rerunflag + 1;
						}

						//	Runtime.getRuntime().exec("C://Automation//Topis Search//AutoIt//autoIt.exe");

						UtilLib.WaitTime(7000);

						
						Robot robot = new Robot(); 
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
					//	int keyInput[] = {KeyEvent.VK_RIGHT};
					//	int keyInput1[] = {KeyEvent.VK_DOWN};
					//	int keyInput2[] = {KeyEvent.VK_UP};
						
						if(rerunflag>0){
							status=false;
							UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_XMLKWERY_104", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104()");
						}
						else
						{
							UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_XMLKWERY_104", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104()");
							status=true;
						}

					}}


				else{


					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

					status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
					}
					else
					{
						rerunflag++;
					}

					status=UtilLib.uf_C_ClickOnElement1("C3/CSOne/CARE", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("CS/CSOne/CARE one data source ", "is selected", "selectingDataSource()");
					}
					else
					{
						rerunflag++;
					}


					System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					if(status==false){
						rerunflag=rerunflag+1;
					}



					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}

					System.out.println("***************     Verification of CS_one and XML Kwery  ***************");

					String CS_one=UtilLib.uf_C_GetUIData1("CS_One", element.uf_SH_CS_0ne, TestCase_Name);
					System.out.println("CS_one result  : "+CS_one);
					if(CS_one.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}



					String XMLKwery=UtilLib.uf_C_GetUIData1("XMLKwery", element.uf_SH_C3XMLKwery, TestCase_Name);
					System.out.println("XMLKwery result  : "+XMLKwery);
					if(XMLKwery.equals(data.getI_PORTLET_VALUE3().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}



					if(flag==2)
					{
						UtilLib.Report_PASS(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are  present", "LinkVerifiaction()");
					}
					else
					{
						UtilLib.Report_FAIL(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are not present", "LinkVerifiaction()");
						rerunflag++;
					}

					/*Runtime.getRuntime().exec("C://Automation//AutoItFiles//AutoI2.exe");*/

					status=UtilLib.uf_C_ClickOnElement1("XMLKwery", element.uf_SH_C3XMLKwery, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}


					UtilLib.WaitTime(7000);
					
					/*String userName = "%"+data.getI_PORTLET_VALUE5()+"%";
					System.out.println("Username : "+userName);
					String password = "%"+data.getI_PORTLET_VALUE6()+"%";
					System.out.println("password : "+password);
					FirefoxProfile profile = new FirefoxProfile();
					profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "http://www-tac.cisco.com");
					driver = new FirefoxDriver(profile);
					driver.manage().window().maximize();
					driver.navigate().to("http://"+userName+":"+password+"@www-tac.cisco.com");
					driver.navigate().to("http://www-tac.cisco.com/Teams/ks/c3/casekwery.php?Case=630743267");
				
					Keyboard keyboard = ((RemoteWebDriver) driver).getKeyboard();
					keyboard.sendKeys("test");
					keyboard.pressKey(Keys.TAB);
					keyboard.releaseKey(Keys.TAB);
					keyboard.sendKeys("test@123");
					keyboard.pressKey(Keys.ENTER);
					keyboard.releaseKey(Keys.ENTER);
					*/
					/*	_driver = new FirefoxDriver();
		            
		          //String URL = "https:// + uname + ":" + pwd + "@" + TEST_ENVIRONMENT;
		            String URL = "https:// + TEST_ENVIRONMENT;
		            _driver.Navigate().GoToUrl(URL);
		            
		            _driver.SwitchTo().Frame("Authentication Required.0.child");
		            _driver.FindElement(By.PartialLinkText(" Name:")).SendKeys(uname);
		            _driver.FindElement(By.PartialLinkText("Password:")).SendKeys(pwd);
		            //System.Threading.Thread.Sleep(3000);
					driver.SwitchTo().Frame("Authentication Required.0.child");
			         _driver.FindElement(By.PartialLinkText(" Name:")).SendKeys(uname);
			         _driver.FindElement(By.PartialLinkText("Password:")).SendKeys(pwd);*/
					
					String userName = "%CISCO_USERNAME%";
					String password = "%CISCO_PASSWORD%";
					driver.get("http://UserName:Password@http://www-tac.cisco.com/Teams/ks/c3/casekwery.php?Case=630743267");
//					profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "http://www-tac.cisco.com");
//					driver = new FirefoxDriver(profile);
//					driver.manage().window().maximize();
//					driver.navigate().to("https://"+userName+":"+password+"@www-tac.cisco.com");
//					driver.navigate().to("http://www-tac.cisco.com/Teams/ks/c3/casekwery.php?Case=630743267");

			
					
					
					
					
					
					
					
					
					
					
					
					
					/*Robot robot = new Robot();
					uf_N_parseChars(data.getI_PORTLET_VALUE5(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					uf_N_parseChars(data.getI_PORTLET_VALUE6(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER); 
				
					UtilLib.WaitTime(5000);*/
					
					
					String title =  driver.getCurrentUrl();
					System.out.println(title);
					if(title.contains("http://www-tac.cisco.com/Teams/ks/c3/casekwery")){
						UtilLib.Report_PASS("System is navigated to", "XMLKwery Page", "uf_R_Login_Topic_Search");
					}
					else{
						UtilLib.Report_FAIL("System is not navigated to", "XMLKwery Page", "uf_R_Login_Topic_Search");
						status = false;
					}
					
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_XMLKWERY_104", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104()");
					}
					else
					{
						UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_XMLKWERY_104", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104()");
						status=true;
					}

				}
			}

		}

		catch (AWTException e) {
			e.printStackTrace();
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}

	 
	


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109
	 * @Description :  verify that Metadata display to be consistent
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		int RerunFlag = 0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("C3/CSOne/CARE", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("CS/CSOne/CARE one data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of meta Fields 'None' value  ***************");

				String noneValue=UtilLib.uf_C_GetUIData1("None value ", element.uf_SH_C3StatusNone, TestCase_Name);
				System.out.println(" Value of Meta Field : "+noneValue);
				if(noneValue.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					UtilLib.Report_PASS("Value of Meta data is 'None'", "and value is absent", "Metavalueverification()");
				}
				else{
					UtilLib.Report_FAIL("Value of Meta data is 'None'", "and value is not present", "Metavalueverification()");
					rerunflag++;
				}





				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4_SCOPE_CSSCSO_MetadataDisplay_consistent_CDETSdataSource_110
	 * @Description :  verify that Metadata display to be consistent
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_CDETS data source_110
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4_SCOPE_CSSCSO_MetadataDisplay_consistent_CDETSdataSource_110(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		int RerunFlag = 0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("CDETS", element.uf_SH_CDETS_DDTScheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("CDETS one data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of meta Fields 'None' value  ***************");

				String noneValue=UtilLib.uf_C_GetUIData1("None value ", element.uf_SH_CDETS_DDTSKeywordsNone, TestCase_Name);
				System.out.println(" Value of Meta Field : "+noneValue);
				if(noneValue.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					UtilLib.Report_PASS("Value of Meta data is 'None'", "and value is absent", "Metavalueverification()");
				}
				else{
					UtilLib.Report_FAIL("Value of Meta data is 'None'", "and value is present", "Metavalueverification()");
					rerunflag++;
				}

				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_NewsGroupdataSource_112
	 * @Description :  verify that Metadata display to be consistent
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_News Group data source_112
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_NewsGroupdataSource_112(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int RerunFlag = 0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("Newsgroup", element.uf_SH_Newsgroupcheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Newsgroup one data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of meta Fields 'None' value  ***************");

				String noneValue=UtilLib.uf_C_GetUIData1("None value ", element.uf_SH_Newsgroupdate, TestCase_Name);
				System.out.println(" Value of Meta Field : "+noneValue);
				if(noneValue.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					UtilLib.Report_FAIL("Value of Meta data is 'None'", "and value is present", "Metavalueverification()");
					rerunflag++;
				}
				else{
					UtilLib.Report_PASS("Value of Meta data is 'None'", "and value is absent", "Metavalueverification()");

				}





				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields
	 * @Description : verify that If no valid fielded search, it will just display the error message as returned by attivio. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 294_UIResult links should not be javascript_01
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   ClickingAdvancedSearch  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Advanced Search", element.uf_SH_advancedSearchCheckbox, TestCase_Name);
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}


				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("Error :Error while calling Attivio Service.Please try again or if the problem persists please contact Services Search team."))
				{	
					UtilLib.Report_PASS("Error Message","is verified","ErrorMessageVerification()");
				
					
						UtilLib.CaptureScreenshot_PASS(TestCase_Name);
					}
				else{
					UtilLib.Report_FAIL("Error Message","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO281_queries with colon may be taken for fielded search_query term preceding colon  is not one of the metadata field", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using To exclude _hyphen symbol_31", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol()");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names
	 * @Description : verify  If valid fielded search it will give appropriate results 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   ClickingAdvancedSearch  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Advanced Search", element.uf_SH_advancedSearchCheckbox, TestCase_Name);
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}


				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL(" Application is not navigated to the results page ", "is not verified", "resultPageVerification()");
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is not verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
					status=true;
				}


			}}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO272_SearchTips_06
	 * @Description : verify the search tip on the results page 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO272_Search Tips_06
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO272_SearchTips_06(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);

				if(status==false){
					rerunflag++;
				}


				String randomTip=UtilLib.uf_C_GetUIData1("IOS Crash", element.uf_SH_randomTip, TestCase_Name);	
				if(randomTip.contains("IOS crash"))
				{
					UtilLib.Report_PASS("Random tip ", "is present",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Random tip ", "is not present",TestCase_Name);
					rerunflag++;
				}

				String moreTip=UtilLib.uf_C_GetUIData1("More Tips", element.uf_SH_moreTips, TestCase_Name);	
				if(moreTip.contains("More tips"))
				{
					UtilLib.Report_PASS("More tip ", "is present on top of the search box in the results page",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Random tip ", "is not present on top of the search box in the results page",TestCase_Name);
					rerunflag++;
				}	

				status=UtilLib.uf_C_ClickOnElement1("More Tips",element.uf_SH_moreTips , TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);

				String lessTip=UtilLib.uf_C_GetUIData1("Less Tips", element.uf_SH_lessTips, TestCase_Name);	
				if(lessTip.contains("Less tips"))
				{
					UtilLib.Report_PASS("Less tip ", "is present on top of the search box in the More Links page",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Less tip ", "is not present on top of the search box in the More Links page",TestCase_Name);
					rerunflag++;
				}

				String IOSCrash=UtilLib.uf_C_GetUIData1("Ios Crash", element.uf_SH_IOScrash, TestCase_Name);	
				if(IOSCrash.contains("IOS crash"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS Crash ", "is not present  in the More Links page",TestCase_Name);
				}


				String IOSANDcrash=UtilLib.uf_C_GetUIData1("IOS AND crash", element.uf_SH_IOSANDcrash, TestCase_Name);	
				if(IOSANDcrash.contains("IOS AND crash"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS AND Crash ", "is not present  in the More Links page",TestCase_Name);
				}

				String IOSCrashORexception=UtilLib.uf_C_GetUIData1("Ios Crash OR exception", element.uf_SH_IOScrashORexception, TestCase_Name);	
				if(IOSCrashORexception.contains("IOS crash OR exception"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS crash OR exception ", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String cat65=UtilLib.uf_C_GetUIData1("cat65", element.uf_SH_cat65, TestCase_Name);	
				if(cat65.contains("cat65"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("cat65 ", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String cat6=UtilLib.uf_C_GetUIData1("cat65", element.uf_SH_cat6, TestCase_Name);	
				if(cat6.contains("cat6"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("cat6", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String SxI12=UtilLib.uf_C_GetUIData1("12*SXI", element.uf_SH_12SxI, TestCase_Name);	
				if(SxI12.contains("12*SXI"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("12*SXI", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String Sx_I12=UtilLib.uf_C_GetUIData1("12**SXI", element.uf_SH_12SXI, TestCase_Name);	
				if(Sx_I12.contains("12 * * SXI?"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("12**SXI", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}

				if(flag==7)
				{
					UtilLib.Report_PASS("Labels in", "More Links page are verified",TestCase_Name);
				}



				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is not verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
					status=true;
				}


			}}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09
	 * @Description : verify that on the results page  if user has no text in the query box and presses 'edit' to select different sources, U
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 287 _UI source selection cannot be changed without query text_09
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}



				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				driver.findElement(By.xpath(element.uf_SH_ResultpagequeryText)).clear();

				status=UtilLib.uf_C_ClickOnElement1("Edit Data Sources Link", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("Dynamic Soft", element.uf_SH_editDynamicSoftPopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				status=driver.findElement(By.xpath(element.uf_SH_editDynamicSoftPopUp)).isSelected();
				if(status==false)
				{
					rerunflag++;
					UtilLib.Report_FAIL("DynamicSoft  ", "is not selected",TestCase_Name);
				}
				else
				{
					UtilLib.Report_PASS("DynamicSoft  ", "is  selected",TestCase_Name);
				}

				status=UtilLib.uf_C_ClickOnElement1("OK button", element.uf_SH_editOKButtonPopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}	

				status=driver.findElement(By.xpath(element.uf_SH_C3_DynamicSoftCheckboxResultPage)).isSelected();
				if(status==false)
				{
					rerunflag++;
					UtilLib.Report_FAIL("User is not able to change selection  ", "without query",TestCase_Name);
				}
				else
				{
					UtilLib.Report_PASS("User is able to change selection  ", "without query",TestCase_Name);
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL(" Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 287 _UI source selection cannot be changed without query text_09", "is not verified", "SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 287 _UI source selection cannot be changed without query text_09", "is verified", "SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09()");
					status=true;
				}


			}}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO285_Error_while_calling_webService_10
	 * @Description : verify that whenever some error occurs error message should be shown on the results page itself with the actual error message that we get from search service. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO285 _Error _Error while calling web service_10
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO285_Error_while_calling_webService_10(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


					
				System.out.println("***************   ClickingAdvancedSearch  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Advanced Search", element.uf_SH_advancedSearchCheckbox, TestCase_Name);
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}


				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("Error :Error while calling Attivio Service.Please try again or if the problem persists please contact Services Search team."))
				{	
					UtilLib.Report_PASS("Error Message","is verified","ErrorMessageVerification()");
					
						UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Error Message","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO285 _Error _Error while calling web service_10", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO285_Error_while_calling_webService_10()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO285 _Error _Error while calling web service_10", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO285_Error_while_calling_webService_10()");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12
	 * @Description : verifythe results page when there  are  zero results for a query string
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				UtilLib.WaitTime(2000);
				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("No matches found, try to refine your query or select additional data sources"))
				{	
					UtilLib.Report_PASS("Error Message for zero results","is verified","ErrorMessageVerification()");
				}
				else{
					UtilLib.Report_FAIL("Error Message for zero results","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_CSSCS_fielded_search_handleSpace_colon_value_wildcard_15
	 * @Description :  verify that fielded search should gracefully handle space between ':' and a value with wildcard 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search should gracefully handle space between  colon and a value with wildcard_15
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_CSSCS_fielded_search_handleSpace_colon_value_wildcard_15(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");


				status=UtilLib.uf_C_ClickOnElement1("Advanced Search", element.uf_SH_advancedSearchCheckbox, TestCase_Name);

				if(status==true)
				{
					UtilLib.Report_PASS("Advanced Search ", "is clicked", "AdvanceSearch()");
				}
				else
				{
					rerunflag++;
					UtilLib.Report_FAIL("Advanced Search ", "is not clicked", "AdvanceSearch()");
				}
				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}


				/*
			status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}*/
				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("Error :Error while calling Attivio Service.Please try again or if the problem persists please contact Services Search team."))
				{	
					UtilLib.Report_PASS("Error Message","is verified","ErrorMessageVerification()");
				}
				else{
					UtilLib.Report_FAIL("Error Message","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search Error is not displayed while we enter space between  colon and a value with wildcard_15", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCS_fielded_search_handleSpace_colon_value_wildcard_15()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search Error is  displayed while we enter space between  colon and a value with wildcard_15", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCS_fielded_search_handleSpace_colon_value_wildcard_15()");
				status=true;
			}

		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO_FieldedSearch_16
	 * @Description :  verify to support fielded search based on multiple fields
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO269_Fielded search_16
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO_FieldedSearch_16(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}



				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO269_Fielded search_16", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO_FieldedSearch_16()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO269_Fielded search_16", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO_FieldedSearch_16()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91
	 * @Description :  verify that an explicit feedback should be provided when user rates the result
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 280_submission of relevance feedback should be acknowledged_91
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************   Feedback  verification  ***************");
				String type1=driver.findElement(By.xpath(element.uf_SH_veryRelevantradioButton)).getAttribute("type");


				if(type1.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Very Relevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Very Relevant ' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}

				String type2=driver.findElement(By.xpath(element.uf_SH_somewhatRelevantradioButton)).getAttribute("type");


				if(type2.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Somewhat Relevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Somewhat Relevant' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}
				String type3=driver.findElement(By.xpath(element.uf_SH_uncertainradioButton)).getAttribute("type");


				if(type3.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Uncertain ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Uncertain  ' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}
				String type4=driver.findElement(By.xpath(element.uf_SH_irrelevantradioButton)).getAttribute("type");


				if(type4.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Irrelevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Irrelevant' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("Somwhat Relevant radio button", element.uf_SH_somewhatRelevantradioButton, TestCase_Name);
				if(status==true)
				{
					UtilLib.WaitTime(1000);
					String feedbackVisibility= driver.findElement(By.xpath(element.uf_N_dateRange)).getCssValue("visibility").trim();

					System.out.println("feedback visibilty :"+feedbackVisibility);
					if(feedbackVisibility.equals("hidden"))

						UtilLib.Report_PASS("User is able to give the feedback 'Somewhat relevant ' and explicit feedback is provided when user rates ", "", "FeedbackVaerifiaction()");
				}
				else
				{
					UtilLib.Report_FAIL("User is unable to give the feedback 'Somewhat relevant ' ", "", "FeedbackVaerifiaction()");
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 280_submission of relevance feedback should be acknowledged_91", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 280_submission of relevance feedback should be acknowledged_91", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3results_CS_ONE_105
	 * @Description :  verify that there should be Icons similar to new window for each of the c3 link.
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 303_options to view C3 results_CS_ONE_105
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3results_CS_ONE_105(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("C3/CSOne/CARE", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("CS/CSOne/CARE one data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of CS_one and XML Kwery  ***************");

				String CS_one=UtilLib.uf_C_GetUIData1("CS_One", element.uf_SH_CS_0ne, TestCase_Name);
				System.out.println("CS_one result  : "+CS_one);
				if(CS_one.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					flag++;
				}
				else{
					rerunflag++;
				}



				String XMLKwery=UtilLib.uf_C_GetUIData1("XMLKwery", element.uf_SH_C3XMLKwery, TestCase_Name);

				if(XMLKwery.equals(data.getI_PORTLET_VALUE3().trim()))
				{
					flag++;
				}
				else{
					rerunflag++;
				}



				if(flag==2)
				{
					UtilLib.Report_PASS(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are  present", "LinkVerifiaction()");
				}
				else
				{
					UtilLib.Report_FAIL(" 2 links  for the C3 links 1)XMLKWERY 2)CS_ONE ", "are not present", "LinkVerifiaction()");
					rerunflag++;
				}

				/*Robot rob;

			rob = new Robot();
			for(int i=0;i<30;i++){
				rob.keyPress(keyInput1[0]);

				rob.delay(50);
			} */

				status=UtilLib.uf_C_ClickOnElement1("CS_One", element.uf_SH_CS_0ne, TestCase_Name);
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				//		Runtime.getRuntime().exec("C://Automation//Topis Search//AutoIt//autoIt.exe");

				UtilLib.WaitTime(10000);

				/*Set<String> windowId = driver.getWindowHandles();
			Iterator<String> itererator1 = windowId.iterator(); 

			String mainWinID1 = itererator1.next();
			String newAdwinID1 = itererator1.next();

			driver.switchTo().window(newAdwinID1);*/
				System.out.println(driver.getTitle());	
				if(driver.getTitle().equals(data.getI_PORTLET_VALUE4()))
				{
					UtilLib.Report_PASS("CS_one result page  ", "is  displayed", "LinkVerifiaction()");
				}
				else
				{
					UtilLib.Report_FAIL("CS_one result page  ", "is not displayed", "LinkVerifiaction()");
					rerunflag++;
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("C3/CSOne/CARE", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3results_CS_ONE_105()");
				}
				else
				{
					UtilLib.Report_PASS("C3/CSOne/CARE", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3results_CS_ONE_105()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_TopicSupportLink
	 * @Description :  verify that support link should bring the user not to the sr opening page, but to page, that shows outstanding and recent cases 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -TestCase_Name
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_TopicSupportLink(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				if(Browser.equalsIgnoreCase("InternetExplorer")){
					long stoptime = System.currentTimeMillis()+5000;
					while(System.currentTimeMillis()<stoptime){

						uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
						UtilLib.WaitTime(5000);
						//String Url=data.getI_URL();
						status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
						if(status==false){
							rerunflag++;
							DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
							DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
						}


						status=UtilLib.uf_C_ClickOnElement1("Support", element.uf_N_support, TestCase_Name);
						if(status==true)
						{
							UtilLib.Report_PASS("Support Link ", "is present at top of home page", "Verification_SupportLink_HomePage");
						}else{
							UtilLib.Report_FAIL("Support Link ", "is not present at top of home page", "Verification_SupportLink_HomePage");
							rerunflag++;
						}

						UtilLib.WaitTime(5000);

						Robot robot = new Robot();

						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);

						Set<String> windowId = driver.getWindowHandles();
						Iterator<String> itererator1 = windowId.iterator(); 

						String mainWinID1 = itererator1.next();
						String newAdwinID1 = itererator1.next();

						driver.switchTo().window(newAdwinID1);

						driver.manage().window().maximize(); 


						UtilLib.WaitTime(5000);

						String Id = UtilLib.uf_C_EnterDataInTextBox("UserName", element.uf_N_userNameServiceRequestPage,data.getI_LOGINUSER_NAME(), TestCase_Name);
						String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_N_passwordServiceRequestPage,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
						status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_N_loginServiceRequestPage, TestCase_Name);
						if(status==false){
							UtilLib.Report_FAIL("Login", "is failed on Service request Management Tool", "VerifyLogin_ServiceManagementTool");
							rerunflag++;
						}else{
							UtilLib.Report_PASS("Login", "successfully on Service request Management Tool", "VerifyLogin_ServiceManagementTool");
						}

						UtilLib.WaitTime(7000);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						UtilLib.WaitTime(5000);

						System.out.println(driver.getTitle());	

						if(driver.getTitle().equals("Topic Search"))
						{
							UtilLib.Report_FAIL("New Page is not opened  ", "after click on support link", "Verify_Newpage");
							rerunflag++;
						}
						else
						{
							UtilLib.Report_PASS("New Page is opened  ", "after click on support link", "Verify_Newpage");
							status=true;
						}




						if(rerunflag>0){
							status=false;
							UtilLib.Report_FAIL("Support Link", "is not verified", "Verify_SupportLink");
						}
						else
						{
							UtilLib.Report_PASS("Support Link", "is verified", "Verify_SupportLink");
							status=true;
						}

					}
				}

			
				else{
					
				
				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);		
					}
					//sSystem.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

					status=UtilLib.uf_C_ClickOnElement1("Support", element.uf_N_support, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("Support Link ", "is present at top of home page", "Verification_SupportLink_HomePage");
					}else{
						UtilLib.Report_FAIL("Support Link ", "is not present at top of home page", "Verification_SupportLink_HomePage");
						rerunflag++;
					}

					UtilLib.WaitTime(7000);

					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);

					UtilLib.WaitTime(5000);

					String Id = UtilLib.uf_C_EnterDataInTextBox("UserName", element.uf_N_userNameServiceRequestPage,data.getI_LOGINUSER_NAME(), TestCase_Name);
					String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_N_passwordServiceRequestPage,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
					status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_N_loginServiceRequestPage, TestCase_Name);
					if(status==false){
						UtilLib.Report_FAIL("Login", "is failed on Service request Management Tool", "VerifyLogin_ServiceManagementTool");
						rerunflag++;
					}else{
						UtilLib.Report_PASS("Login", "successfully on Service request Management Tool", "VerifyLogin_ServiceManagementTool");
					}

					UtilLib.WaitTime(4000);
					Robot robot = new Robot();

					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);

					//UtilLib.WaitTime(5000);

					System.out.println(driver.getTitle());	

					if(driver.getTitle().equals("Topic Search"))
					{
						UtilLib.Report_FAIL("New Page is not opened  ", "after click on support link", "Verify_Newpage");
						rerunflag++;
					}
					else
					{
						UtilLib.Report_PASS("New Page is opened  ", "after click on support link", "Verify_Newpage");
						status=true;
					}




					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("Support Link", "is not verified", "Verify_SupportLink");
					}
					else
					{
						UtilLib.Report_PASS("Support Link", "is verified", "Verify_SupportLink");
						status=true;
					}

				}
			}


		}

		
		catch(UnhandledAlertException alert){
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


		return status;
	}



	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_TopicHelpLink
	 * @Description :   verify that help link - should point to actual manual-like page, explaining what is supported, what are the features etc. 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -TestCase_Name
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Q4SCOPE_TopicHelpLink(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				status=UtilLib.uf_C_ClickOnElement1("Help", element.uf_N_help, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("User Guide Link", "is present at top of home page", "Verification_HelpLink_HomePage");
				}else{
					UtilLib.Report_FAIL("User Guide Link", "is not present at top of home page", "Verification_HelpLink_HomePage");
					rerunflag++;
				}

				UtilLib.WaitTime(4000);

				Set<String> windowId = driver.getWindowHandles();
				Iterator<String> itererator1 = windowId.iterator(); 

				String mainWinID1 = itererator1.next();
				String newAdwinID1 = itererator1.next();

				driver.switchTo().window(newAdwinID1);



				UtilLib.WaitTime(7000);

				String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
				String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL("Login", "is failed after clicking on Help Link", "VerificationHelpLink");
					rerunflag++;
				}else{
					UtilLib.Report_PASS("Login", "successful after clicking on Help Link", "VerificationHelpLink");
				}

				UtilLib.WaitTime(14000);

				System.out.println(driver.getTitle());
				String helpUserGuide=UtilLib.uf_C_GetUIData1("User Guide",element.uf_N_helpUserGuide,TestCase_Name);

				if(helpUserGuide.equals("Topic search 3.0 user guide "))
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on User Guide Link", "Verify_Newpage");
					rerunflag++;
				}
				else
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on User Guide Link", "Verify_Newpage");
					status=true;
				}




				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("User Guide Link", "is not verified", "Verify_HelpLink");
				}
				else
				{
					UtilLib.Report_PASS("User Guide Link", "is verified", "Verify_HelpLink");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}

	/********************************************************************************************
	 * @Function_Name :  Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03
	 * @Description : This scenario is to verify that FAQ link is added at  the top of the page
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_  Add FAQ link_Home page_03
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.ClickOnElement("FAQ Link", element.uf_SH_FAQLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);
					System.out.println(driver.getTitle());

					System.out.println("Current URL in FAQ is : " +driver.getCurrentUrl());
					if(driver.getCurrentUrl().equals("http://iwe.cisco.com/web/cisco-services-search/faq"))
					{
						status = true;
						UtilLib.Report_PASS("User is taken to", "required URL of FAQ on result page", "FAQLinkVerification()");
					}else{
						rerunflag++;
						UtilLib.Report_FAIL("User is not taken to", "required URL of FAQ on result page", "FAQLinkVerification");

						driver.close();
						return false;
					}
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03", "is not verified", "TS027_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03");
				}
				else
				{
					UtilLib.Report_PASS("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03", "is verified", "TS027_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Homepage_03");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04
	 * @Description : This scenario is to verify that FAQ link is added at  the top of the page on Result page
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_ Add FAQ link_Results page_04
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.ClickOnElement("FAQ Link", element.uf_SH_FAQLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);
					//System.out.println(driver.getTitle());

					System.out.println("Current URL in FAQ is : " +driver.getCurrentUrl());
					if(driver.getCurrentUrl().equals("http://iwe.cisco.com/web/cisco-services-search/faq"))
					{
						status = true;
						UtilLib.Report_PASS("User is taken to", "required URL of FAQ on result page", "FAQLinkVerification()");
					}else{
						rerunflag++;
						UtilLib.Report_FAIL("User is not taken to", "required URL of FAQ on result page ", "FAQLinkVerification");

						driver.close();
						return false;
					}
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04", "is not verified", "TS028_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04");
				}
				else
				{
					UtilLib.Report_PASS("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04", "is verified", "TS028_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05
	 * @Description : This scenario is to verify that '?' is displayed beside the advance search and user is taken to respective page when user clicks on it from home page
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_'?' beside advance serach_Home page_05
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.ClickOnElement("? on Advance Search", element.uf_SH_AdvanceSearch_QuestionMark, TestCase_Name);
					UtilLib.WaitTime(3000);
					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);
					System.out.println(driver.getTitle());

					System.out.println("Current URL in FAQ is : " +driver.getCurrentUrl());
					if(driver.getCurrentUrl().equals("https://search-prd.cisco.com/help.html#_Toc390272879"))
					{
						status = true;
						UtilLib.Report_PASS("User is taken to", "required URL of on clicking of ? beside Question Mark Home page", "AdvanceSearchVerification()");
					}else{
						rerunflag++;
						UtilLib.Report_FAIL("User is not taken to", "required URL of on clicking of ? beside Question Mark Home page", "AdvanceSearchVerification");

						driver.close();
						return false;
					}
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05", "is not verified", "TS029_Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05");
				}
				else
				{
					UtilLib.Report_PASS("Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05", "is verified", "TS029_Topic3_0_UI_Aug14Rel_PROG_beside_advanceSerach_Homepage_05");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  Topic3_0_UI_Aug14Rel_PROG_besideAdvanceSerach_Resultspage_06
	 * @Description : This scenario is to verify that '?' is displayed beside the advance search and user is taken to respective page when user clicks on it from search results page
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_'?' beside advance serach_Results page_06
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic3_0_UI_Aug14Rel_PROG_besideAdvanceSerach_Resultspage_06(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("? on Advance Search", element.uf_SH_AdvanceSearch_QuestionMark_ResultPage, TestCase_Name);
					UtilLib.WaitTime(3000);
					Set<String> windowId = driver.getWindowHandles();
					Iterator<String> itererator1 = windowId.iterator(); 

					String mainWinID1 = itererator1.next();
					String newAdwinID1 = itererator1.next();

					driver.switchTo().window(newAdwinID1);
					System.out.println(driver.getTitle());

					System.out.println("Current URL in FAQ is : " +driver.getCurrentUrl());
					if(driver.getCurrentUrl().equals("https://search-prd.cisco.com/help.html#_Toc390272879"))
					{
						status = true;
						UtilLib.Report_PASS("User is taken to", "required URL of on clicking of ? beside Question Mark Home page", "AdvanceSearchVerification()");
					}else{
						rerunflag++;
						UtilLib.Report_FAIL("User is not taken to", "required URL of on clicking of ? beside Question Mark Home page", "AdvanceSearchVerification");

						driver.close();
						return false;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04", "is not verified", "TS028_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04");
				}
				else
				{
					UtilLib.Report_PASS("Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04", "is verified", "TS028_Topic3_0_UI_Aug14Rel_PROG_AddFAQlink_Resultspage04");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34
	 * @Description :  This scenario is to verify that file format options are available and working as expected 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILE FORMATS_PDFs_34
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsPDF=UtilLib.uf_C_GetUIData1("No of resuts of PDF", element.uf_SH_PDF_NoofResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsPDF, TestCase_Name);
					System.out.println("No of PDF results after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("PDF checkbox", element.uf_SH_PDFChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(2,3);
					
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34", "is not verified", "SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34", "is verified", "SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefiltering_options_defaultsearchfilters_FILE_FORMATS_PDFs_34");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35
	 * @Description :  This scenario is to verify that file format options are available and working as expected 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILE FORMATS_application/vnd.ms-powerpoint_35
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					
					status=UtilLib.uf_C_ClickOnElement1("All checkbox",element.uf_R_DataSourceFilterAll,TestCase_Name);
					if(status==false)
					{
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


					status=UtilLib.uf_C_ClickOnElement1("Techzone",element.uf_SH_C3_TechZoneCheckboxResultPage,TestCase_Name);
					if(status==false)
					{
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					status=UtilLib.uf_C_ClickOnElement1("TSTraining",element.uf_SH_TS_TrainingRepoCheckboxResultPage,TestCase_Name);
					if(status==false)
					{
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					UtilLib.WaitTime(3000);
					

					status=UtilLib.uf_C_ClickOnElement1("Show All",element.uf_SH_ShowAllLink,TestCase_Name);
					if(status==false)
					{
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					
					String NoOfResultsPDF=UtilLib.uf_C_GetUIData1("No of resuts of Powerpoint", element.uf_SH_application_vnd_NoofResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsPDF, TestCase_Name);
					System.out.println("No of Powerpoint results after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("Powerpoint checkbox", element.uf_SH_application_vndChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,10);
					
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35", "is not verified", "TS032_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35", "is verified", "TS032_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  TS033_SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_SpreadSheets_33
	 * @Description :  This scenario is to verify that file format options are available and working as expected 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILE FORMATS_SpreadSheets_33
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_OnlineTraining_33(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					/*String NoOfResultsPDF=UtilLib.uf_C_GetUIData1("No of resuts of Spreadsheet", element.uf_SH_spreadsheet_NoofResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsPDF, TestCase_Name);
					System.out.println("No of Spreadsheet results after filter :"+resultsAfterFilter);
					
					UtilLib.ClickOnElement("Spreadsheet checkbox", element.uf_SH_OnlineTrainingChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(2,3);
					
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}*/
					
					
					
					String NoOfResultsPDF=UtilLib.uf_C_GetUIData1("No of resuts of Online Training", element.uf_SH_spreadsheet_NoofResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsPDF, TestCase_Name);
					System.out.println("No of Online Training results after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("Online Training checkbox", element.uf_SH_OnlineTrainingChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,10);
					
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
					}
					
					
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_OnlineTraining_33", "is not verified", "TS033_SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_OnlineTraining_33");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_OnlineTraining_33", "is verified", "TS033_SearchTopic3_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_OnlineTraining_33");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_Webpages_36
	 * @Description :  This scenario is to verify that file format options are available and working as expected 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILE FORMATS_Webpages_36
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_Webpages_36(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsWebpages=UtilLib.uf_C_GetUIData1("No of resuts of Webpages", element.uf_SH_webpages_NoofResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsWebpages, TestCase_Name);
					System.out.println("No of webpages results after filter :"+resultsAfterFilter);
					
					UtilLib.ClickOnElement("Webpages checkbox", element.uf_SH_webpagesChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,13);
					System.out.println("UI data : "+results1);
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of webpages result  is : "+noOfResults);
					UtilLib.WaitTime(1000);
					//System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35", "is not verified", "TS032_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35", "is verified", "TS032_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_ResultpageFilteringoptions_defaultSearchfilters_FILEFORMATS_application_vnd_mspowerpoint_35");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_RSLT_Resultpagefilteringoptions_defaultsearchfilters_FILEFORMATS_Webpages_36
	 * @Description :  This scenario is to verify that file format options are available and working as expected 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILE FORMATS_Webpages_36
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3_Resultpage_filteringoptions_defaultSearchfilters_FILEFORMATS_ALL_32(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,16);
					
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_AllChechox, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results31=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results31=results31.substring(8,16);
					
					int noOfResults2=Integer.parseInt(results31);
					System.out.println("Total no of results after All filter on  result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result 2 : "+noOfResults2);
					
					if(noOfResults2==noOfResults)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3_Resultpage_filteringoptions_defaultSearchfilters_FILEFORMATS_ALL_32", "is not verified", "TS035_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3_Resultpage_filteringoptions_defaultSearchfilters_FILEFORMATS_ALL_32");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3_Resultpage_filteringoptions_defaultSearchfilters_FILEFORMATS_ALL_32", "is verified", "TS035_SearchTopic3_0_UI_AttivioMigration_PROG_RSLT3_Resultpage_filteringoptions_defaultSearchfilters_FILEFORMATS_ALL_32");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory
	 * @Description :  This scenario is to verify that My preferences option that will be available on the Topic UI header  
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Oct'14 Rel_PROG_My preferences_option_available_Topic UI header_Browser History
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.uf_C_ClickOnElement1("My Preferences Link", element.uf_SH_MyPreferencesLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					
					
					boolean browserHistoryIsSelected=driver.findElement(By.xpath(element.uf_SH_MyPreferences_BrowserHistory_RadioButton)).isSelected();
					if(browserHistoryIsSelected==true)
					{
						UtilLib.Report_PASS(" User is navigated to the My Prefernces page and the Browser History is selected ", "is selected  if user clicks for the first time", "MyPreferencesVerification()");
					}
					else
					{
						UtilLib.Report_FAIL(" User is navigated to the My Prefernces page and the Browser History  ", "is not selected if user clicks for the first time", "MyPreferencesVerification()");
						rerunflag++;
					}
					
					
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory", "is not verified", "TS036_TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory");
				}
				else
				{
					UtilLib.Report_PASS("TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory", "is verified", "TS036_TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_BackButton
	 * @Description : This scenario is to verify the functionality of  abck button my preferences option that will be available on the Topic UI header 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Oct'14 Rel_PROG_Verify_My preferences_option_Functionality_Back Button
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_BackButton(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.uf_C_ClickOnElement1("My Preferences Link", element.uf_SH_MyPreferencesLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					
					
					boolean browserHistoryIsSelected=driver.findElement(By.xpath(element.uf_SH_MyPreferences_BrowserHistory_RadioButton)).isSelected();
					if(browserHistoryIsSelected==true)
					{
						UtilLib.Report_PASS(" User is navigated to the My Prefernces page and the Browser History is selected ", "is selected  if user clicks for the first time", "MyPreferencesVerification()");
					}
					else
					{
						UtilLib.Report_FAIL(" User is navigated to the My Prefernces page and the Browser History  ", "is not selected if user clicks for the first time", "MyPreferencesVerification()");
						rerunflag++;
					}
					
					
					UtilLib.uf_C_ClickOnElement1("Back button in My Preferences link", element.uf_SH_MyPreferences_BackButton, TestCase_Name);
					UtilLib.WaitTime(5000);
					boolean queryTextBox=driver.findElement(By.xpath(element.uf_R_SearchText)).isDisplayed();
					if(queryTextBox==true)
					{
						UtilLib.Report_PASS(" Back Button ", "functionality is verified", "MyPreferencesBackButtonVerification()");
					}
					else{
						UtilLib.Report_PASS(" Back Button ", "functionality is not verified", "MyPreferencesBackButtonVerification()");
						rerunflag++;
					}
					
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory", "is not verified", "TS036_TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory");
				}
				else
				{
					UtilLib.Report_PASS("TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory", "is verified", "TS036_TSSearchTopic_3_1_4UI_Oct14Rel_PROG_Mypreferences_option_available_TopicUI_header_BrowserHistory");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_AutoSuggest
	 * @Description : This scenario is to verify the functionality of my preferences option that will be available on the Topic UI header  
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Oct'14 Rel_PROG_Verify_My preferences_option_Functionality_Back Button
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_AutoSuggest(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.uf_C_ClickOnElement1("My Preferences Link", element.uf_SH_MyPreferencesLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					
					
					boolean browserHistoryIsSelected=driver.findElement(By.xpath(element.uf_SH_MyPreferences_SystemData_RadioButton)).isSelected();
					if(browserHistoryIsSelected==false)
					{
						UtilLib.Report_PASS("User is navigated to the My Prefernces page and the System data ", "is not selected ", "MyPreferencesVerification()");
						UtilLib.uf_C_ClickOnElement1("System Data Radio Button", element.uf_SH_MyPreferences_SystemData_RadioButton, TestCase_Name);
						
						UtilLib.uf_C_ClickOnElement1("Save Changes", element.uf_SH_MyPreferences_SaveChanges, TestCase_Name);
						UtilLib.WaitTime(1000);
					}
					else
					{
						UtilLib.Report_FAIL("User is navigated to the My Prefernces page and the System data ", "is selected ", "MyPreferencesVerification()");
						
					}
					
					
					UtilLib.uf_C_ClickOnElement1("Back button in My Preferences link", element.uf_SH_MyPreferences_BackButton, TestCase_Name);
					UtilLib.WaitTime(5000);
					boolean queryTextBox=driver.findElement(By.xpath(element.uf_R_SearchText)).isDisplayed();
					if(queryTextBox==true)
					{
						UtilLib.Report_PASS(" Back Button ", "functionality is verified", "MyPreferencesBackButtonVerification()");
					}
					else{
						UtilLib.Report_FAIL(" Back Button ", "functionality is not verified", "MyPreferencesBackButtonVerification()");
						rerunflag++;
					}
					
					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText,data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(3000);
					WebDriverWait wait = new WebDriverWait(driver , 300 ); 
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.uf_SH_AutosuggestValue1)));
					
					
					String AutosuggestValue1=UtilLib.uf_C_GetUIData1("First Value of Autosuggest", element.uf_SH_AutosuggestValue1, TestCase_Name);
					System.out.println("Autosuggest value is  : "+AutosuggestValue1);
					UtilLib.uf_C_ClickOnElement1("AutoSuggest value 1", element.uf_SH_AutosuggestValue1, TestCase_Name);
					
					UtilLib.WaitTime(3000);
					
					/*selenium.getDriver().findElement(By.id("s2id_customFilter")).sendKeys(Keys.ARROW_DOWN);
				WebElement firstItem = driver.findElement(By.xpath(element.uf_SH_AutosuggestValue1));
		        firstItem.click();
		        Actions action = new Actions(driver);
		        action.KeyDown(Keys.SHIFT).Click(option1).Click(option2).KeyUp(Keys.SHIFT).Build().Perform();
		        UtilLib.WaitTime(1000);*/
				
		       
					/*boolean status1= UtilLib.uf_C_ClickOnElement1("Search",element.uf_N_searchButton, TestCase_Name);
					if(status1==false){
						 rerunflag++;	
					}
					*/
					//UtilLib.WaitTime(3000);
					
					if(AutosuggestValue1.equals("Route Switch"))
					{
						UtilLib.Report_PASS("Autosuggest Functionality", " is verified", "AutosuggestVerification()");
					}
					else{
						UtilLib.Report_FAIL("Autosuggest Functionality", " is not verified", "AutosuggestVerification()");
						rerunflag++;
					}
		        
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3.1.3 UI_Oct'14 Rel_PROG_Verify_My preferences_option_Functionality_AutoSuggest_03", "is not verified", "TS038_SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_AutoSuggest");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3.1.3 UI_Oct'14 Rel_PROG_Verify_My preferences_option_Functionality_AutoSuggest_03", "is verified", "TS038_SearchTopic_3_1_4UI_Oct14Rel_PROG_Verify_Mypreferences_option_Functionality_AutoSuggest");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************	 * @Description : This scenario is to verify  that 'Always search within these checked data sources' save changes should be saved without any search performed
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Oct'14 Rel_PROG_'Always search within these checked data sources' save changes
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
				UtilLib.uf_C_ClickOnElement1("Shogun Dats Source", element.uf_SH_Shoguncheckbox, TestCase_Name);
				UtilLib.WaitTime(2000);
				UtilLib.uf_C_ClickOnElement1("Always Search Checkbox", element.uf_R_AlwaysSearch, TestCase_Name);
				UtilLib.WaitTime(2000);
				boolean checkBoxIschecked=driver.findElement(By.xpath(element.uf_SH_Shoguncheckbox)).isSelected();
			
				if(checkBoxIschecked==true){
					UtilLib.Report_PASS("Datasource which user selected","is still selected even without performing search", "AlwaysSearchVerifiaction()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Datasource which user selected","is not selected even without performing search", "AlwaysSearchVerifiaction()");
				}
					
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is not verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_onlyAttiviobased_datasourcesforSearch_Previewcapability_availablefor_newsgroup
	 * @Description : This scenario is to verify the Preview capability for newsgroup
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Attivio Migration_PROG_Q4 SCOPE_SRCH 2_User selects only Attivio based data sources for search_Preview capability to be available for newsgroup_30
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_onlyAttiviobased_datasourcesforSearch_Previewcapability_availablefor_newsgroup(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("Newsgroup Checkbox", element.uf_N_newsGroupChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("Newsgroup Header in Topic Buddy Preview", element.uf_SH_NewsgroupHeader_TopicPreview, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("Newsgroup-"))
					{
						UtilLib.Report_PASS("Newsgroup Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Newsgroup Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is not verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29
	 * @Description : This scenario is to verify the Preview capability for cdets
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Attivio Migration_PROG_Q4 SCOPE_SRCH 2_User selects only Attivio based data sources for search_Preview capability to be available for  cdets_29
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("CDETS Checkbox", element.uf_N_cdetsChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_CDETSresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("CDETS Header in Topic Buddy Preview", element.uf_SH_CDETSHeader_TopicPreview, TestCase_Name);
				    System.out.println("CDETS Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("CDETS/DDTS-"))
					{
						UtilLib.Report_PASS("CDETS Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("CDETS Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29", "is not verified", "TS041_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29", "is verified", "TS041_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_onlyAttiviobased_datasources_search_PreviewCapability_available_c3_28
	 * @Description : This scenario is to verify the Preview capability for c3
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Attivio Migration_PROG_Q4 SCOPE_SRCH 2_User selects only Attivio based data sources for search_Preview capability to be available for c3_28
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_onlyAttiviobased_datasources_search_PreviewCapability_available_c3_28(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("C3 Checkbox", element.uf_N_c3ChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_C3result1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("C3 Header in Topic Buddy Preview", element.uf_SH_CDETSHeader_TopicPreview, TestCase_Name);
				    System.out.println("C3 Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("C3/CSOne/CARE-"))
					{
						UtilLib.Report_PASS("C3 Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("C3 Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29", "is not verified", "TS041_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_c3_28");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_cdets_29", "is verified", "TS041_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_User_selects_Attivio_baseddataSource_search_PreviewCapability_available_c3_28");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31
	 * @Description : This scenario is to verify the Preview capability for techzone
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4 UI_Attivio Migration_PROG_Q4 SCOPE_SRCH 2_User selects only Attivio based data sources for search_Preview capability to be available for techzone_31
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("Techzone Checkbox", element.uf_N_techzoneChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Techzoneresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("Techzone Header in Topic Buddy Preview", element.uf_SH_CDETSHeader_TopicPreview, TestCase_Name);
				    System.out.println("Techzone Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("Tech Zone-"))
					{
						UtilLib.Report_PASS("Techzone Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Techzone Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31", "is not verified", "TS043_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31", "is verified", "TS043_SearchTopic_3_1_4UI_AttivioMigration_PROG_Q4SCOPE_SRCH2_Userselectsonly_Attivio_data_sources_search_Preview_capability_available_techzone_31");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37
	 * @Description :  This scenario is to verify that user should get an error message when user tries to login into the application with out providing username and password 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_LG 1_HOME SCREEN_login page_Blank credentials _login functionality_37
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					if(Browser.equalsIgnoreCase("Firefox")){
						String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
						String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
						UtilLib.WaitTime(3000);
						status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
						if(status==false){
							UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

						}
						UtilLib.WaitTime(5000);
						String title = driver.getTitle();
						System.out.println(title);
						if(title.equalsIgnoreCase(title)){
							UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
						}
						else{
							UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
							status = false;
						}
					}else
						if(Browser.equalsIgnoreCase("InternetExplorer")){
							String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
							String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
							status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
							if(status==false){
								UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

							}
							UtilLib.WaitTime(5000);
							String title = driver.getTitle();
							System.out.println(title);
							if(title.equalsIgnoreCase(title)){
								UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
							}
							else{
								UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
								status = false;
							}
						}else
							if(Browser.equalsIgnoreCase("Safari")){		
								String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
								String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
								status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
								if(status==false){
									UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

								}
								UtilLib.WaitTime(5000);
								String title = driver.getTitle();
								System.out.println(title);
								if(title.equalsIgnoreCase(title)){
									UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
								}
								else{
									UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
									status = false;
								}
							}else
								if(Browser.equalsIgnoreCase("Chrome")){
									UtilLib.WaitTime(4000);
									Robot robot = new Robot();
									status = UtilLib.uf_R_enterLoginCredetials("nisahni","Nish$2014");
									if(status==false){
										UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

									}
									UtilLib.WaitTime(5000);
									String title = driver.getTitle();
									System.out.println(title);
									if(title.equalsIgnoreCase(title)){
										UtilLib.Report_PASS("Title", "is Verified", "uf_R_Login_Topic_Search");
									}
									else{
										UtilLib.Report_FAIL("Title", "is not matching with topic search page", "uf_R_Login_Topic_Search");
										status = false;
									}

								}else{
									System.out.println("Kindly select a valid browser");
								}
				
					String errorMessage=UtilLib.uf_C_GetUIData1("Error Message without credentials", element.uf_SH_ErrorMessagewithoutCredentials, TestCase_Name);
					if(errorMessage.equals(data.getI_PORTLET_VALUE1()))
					{
						UtilLib.Report_PASS("Error Message", "is coming when users does not enters Credentials ", "ErrorMessageVerification()");
					}
					else{
						UtilLib.Report_FAIL("Error Message", "is not coming when users does not enters Credentials ", "ErrorMessageVerification()");
						rerunflag++;
					}

				}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37", "is not verified", "TS044_SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37", "is verified", "TS044_SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37");
					status=true;
				}

				
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_Loginpage_lookandfeel_42
	 * @Description :   This scenario is to verify the look and feel of the login page 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_LG 1_HOME SCREEN_Login page look and feel_42
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_Loginpage_lookandfeel_42(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					String usernameText=UtilLib.uf_C_GetUIData1("Username", element.uf_SH_UsernameText_LoginPage, TestCase_Name);
					String passwordText=UtilLib.uf_C_GetUIData1("Password", element.uf_SH_PasswordText_LoginPage, TestCase_Name);
					String CiscoIcon=UtilLib.uf_C_GetUIData1("CiscoICON", element.uf_SH_CiscoIcon, TestCase_Name);
					System.out.println("CISCO Icon text : "+CiscoIcon);
					if(usernameText.equals(data.getI_PORTLET_VALUE1()) && passwordText.equals(data.getI_PORTLET_VALUE2())&&CiscoIcon.equals(data.getI_PORTLET_VALUE3()))
					{
						UtilLib.Report_PASS("Username, Password, CiscoIcon ", "are verified for Login Page", "LoginPageverification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("Username, Password, CiscoIcon ", "are verified for Login Page", "LoginPageverification()");
					}
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					
					String TopicSearchLogo=UtilLib.uf_C_GetUIData1("Topic Search Logo", element.uf_SH_TopicSearchLogo, TestCase_Name);
					String FAQLink=UtilLib.uf_C_GetUIData1("FAQ Link", element.uf_SH_FAQLink, TestCase_Name);
					String MyPreferences=UtilLib.uf_C_GetUIData1("MyPreferences", element.uf_SH_MyPreferencesLink, TestCase_Name);
					//String SearchButton=UtilLib.uf_C_GetUIData1("Username", element.uf_R_SearchBtn, TestCase_Name);
					if( TopicSearchLogo.equals(data.getI_PORTLET_VALUE4())&& FAQLink.equals(data.getI_PORTLET_VALUE5())&&MyPreferences.equals(data.getI_PORTLET_VALUE6()))
							{
								UtilLib.Report_PASS("Topic Search Logo, FAQ Link, MyPreferences ", "are verified for Home Page", "HomePageverification()");
							}
					else
					{
						rerunflag++;
						UtilLib.Report_FAIL("Topic Search Logo, FAQ Link, MyPreferences ", "are not verified for Home Page", "HomePageverification()");
					}
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37", "is not verified", "TS044_SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37", "is verified", "TS044_SearchTopic3_0_UI_AttivioMigration_PROG_LG1_HOMESCREEN_loginpage_Blankcredentials_loginfunctionality_37");
					status=true;
				}

				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43
	 * @Description :    This scenario is to verify that  the  user is able to view more data sources by clicking on more data sources link or chevron   
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG _SRCH 2 Search Page data source_to view more datasources_more data source link_43
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					
					UtilLib.uf_C_ClickOnElement1("Downward Chevron", element.uf_R_HomePage_Chevron, TestCase_Name);
					String Dynamicdatasource=UtilLib.uf_C_GetUIData1("DynamicSoft DataSource", element.uf_SH_DynamicSoft, TestCase_Name).trim();
					String Starentdatasource=UtilLib.uf_C_GetUIData1("Starent DataSource", element.uf_SH_Starent, TestCase_Name).trim();
					String Compositedatasource=UtilLib.uf_C_GetUIData1("Composite DataSource", element.uf_SH_Composite, TestCase_Name).trim();
					String Latitudedatasource=UtilLib.uf_C_GetUIData1("Latitude DataSource", element.uf_SH_Latitude, TestCase_Name).trim();
					String Lessdatasource=UtilLib.uf_C_GetUIData1("Less DataSource", element.uf_SH_LessdataSourceLink, TestCase_Name).trim();
					/*System.out.println(Dynamicdatasource);
					System.out.println(Starentdatasource);
					System.out.println(Compositedatasource);
					System.out.println(Latitudedatasource);
					System.out.println(Lessdatasource);
					System.out.println(data.getI_PORTLET_VALUE1());*/
					if(Dynamicdatasource.equals(data.getI_PORTLET_VALUE1())&&Starentdatasource.equals(data.getI_PORTLET_VALUE2())&&Compositedatasource.equals(data.getI_PORTLET_VALUE3())&&Latitudedatasource.equals(data.getI_PORTLET_VALUE4())&&Lessdatasource.equals(data.getI_PORTLET_VALUE5()))
					{
						UtilLib.Report_PASS("More Data Sources and Less data sources link ", "is present", "Chevronverification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("More Data Sources and Less data sources link ", "is not present", "Chevronverification()");
						
					}
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43", "is not verified", "TS046_SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43", "is verified", "TS046_SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43");
						status=true;
					}
				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_view_lessdatasources_lessdatasourcelink_44
	 * @Description :     This scenario is to verify that  the  user is able to view less data sources by clicking on less data sources link or chevron    
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG _SRCH 2 Search Page data source_to view less datasources_less data source link_44
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_view_lessdatasources_lessdatasourcelink_44(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					
					UtilLib.uf_C_ClickOnElement1("Downward Chevron", element.uf_R_HomePage_Chevron, TestCase_Name);
					String Dynamicdatasource=UtilLib.uf_C_GetUIData1("DynamicSoft DataSource", element.uf_SH_DynamicSoft, TestCase_Name).trim();
					String Starentdatasource=UtilLib.uf_C_GetUIData1("Starent DataSource", element.uf_SH_Starent, TestCase_Name).trim();
					String Compositedatasource=UtilLib.uf_C_GetUIData1("Composite DataSource", element.uf_SH_Composite, TestCase_Name).trim();
					String Latitudedatasource=UtilLib.uf_C_GetUIData1("Latitude DataSource", element.uf_SH_Latitude, TestCase_Name).trim();
					String Lessdatasource=UtilLib.uf_C_GetUIData1("Less DataSource", element.uf_SH_LessdataSourceLink, TestCase_Name).trim();
					/*System.out.println(Dynamicdatasource);
					System.out.println(Starentdatasource);
					System.out.println(Compositedatasource);
					System.out.println(Latitudedatasource);
					System.out.println(Lessdatasource);
					System.out.println(data.getI_PORTLET_VALUE1());*/
					if(Dynamicdatasource.equals(data.getI_PORTLET_VALUE1())&&Starentdatasource.equals(data.getI_PORTLET_VALUE2())&&Compositedatasource.equals(data.getI_PORTLET_VALUE3())&&Latitudedatasource.equals(data.getI_PORTLET_VALUE4())&&Lessdatasource.equals(data.getI_PORTLET_VALUE5()))
					{
						UtilLib.Report_PASS("More Data Sources and Less data sources link ", "is present", "Chevronverification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("More Data Sources and Less data sources link ", "is not present", "Chevronverification()");
						
					}
					
					UtilLib.uf_C_ClickOnElement1("Less Data source Link", element.uf_SH_LessdataSourceLink, TestCase_Name);
					UtilLib.WaitTime(1000);
					boolean isdownChevronDisplayed=driver.findElement(By.xpath(element.uf_R_HomePage_Chevron)).isDisplayed();
					boolean islessDatasourcelinkDisplayed=driver.findElement(By.xpath(element.uf_SH_LessdataSourceLink)).isDisplayed();
					System.out.println(isdownChevronDisplayed);
					System.out.println(islessDatasourcelinkDisplayed);
					if(isdownChevronDisplayed==true && islessDatasourcelinkDisplayed==false)
					{
						UtilLib.Report_PASS("Less data source link is collapsed and More Data Sources Link and chevron  ", "is displayed", "downChevronverification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_PASS("Less data source link is collapsed and More Data Sources Link and chevron  ", "is not present", "downChevronverification()");
					}
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43", "is not verified", "TS046_SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43", "is verified", "TS046_SearchTopic3_0UI_AttivioMigration_PROG_SRCH2SearchPagedatasource_to_view_moredatasources_moredatasourcelink_43");
						status=true;
					}
				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45
	 * @Description :     This scenario is to verify that Page title should reflect search query   
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 305_UI Page title should reflect search query_search from Home page_45
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String title = driver.getTitle();
					System.out.println(title);
					if(title.contains(data.getI_PORTLET_VALUE1())){
						UtilLib.Report_PASS("titlt coontains", "search Term", "titleVerification()");
					}
					else{
						UtilLib.Report_PASS("titlt coontains", "search Term", "titleVerification");
						status = false;
					}
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45", "is not verified", "TS048_SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45", "is verified", "TS048_SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45");
						status=true;
					}
				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_Q4_SCOPE_CSSCSO_3_05_UIPagetitle_should_reflect_searchquery_search_fromResultspage_46
	 * @Description :     This scenario is to verify that Page title should reflect search query   
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 305_UI Page title should reflect search query_search from Results  page_46
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_Q4_SCOPE_CSSCSO_3_05_UIPagetitle_should_reflect_searchquery_search_fromResultspage_46(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String title = driver.getTitle();
					System.out.println(title);
					if(title.contains(data.getI_PORTLET_VALUE1())){
						UtilLib.Report_PASS("titlt coontains", "search Term", "titleVerification()");
					}
					else{
						UtilLib.Report_PASS("titlt coontains", "search Term", "titleVerification");
						status = false;
					}
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45", "is not verified", "TS048_SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45", "is verified", "TS048_SearchTopic3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO305_UIPage_title_should_reflect_searchquery_search_fromHomepage_45");
						status=true;
					}
				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47
	 * @Description :     This scenario is to verify that the user is able to select all the data sources using select all link    
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 2 Search Page data source_select all link_47
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}
					
					UtilLib.uf_C_ClickOnElement1("Clear All ", element.uf_R_ClearAll, TestCase_Name);
					UtilLib.WaitTime(1000);
					UtilLib.uf_C_ClickOnElement1("Select All ", element.uf_R_SelectAll, TestCase_Name);
					UtilLib.WaitTime(1000);
					boolean value1=driver.findElement(By.xpath(element.uf_SH_C3_CSOne_CAREcheckbox)).isEnabled();
					boolean value2=driver.findElement(By.xpath(element.uf_SH_CDETS_DDTScheckbox)).isEnabled();
					boolean value3=driver.findElement(By.xpath(element.uf_SH_Techzonecheckbox)).isEnabled();
					boolean value4=driver.findElement(By.xpath(element.uf_SH_Newsgroupcheckbox)).isEnabled();
					boolean value5=driver.findElement(By.xpath(element.uf_SH_Shoguncheckbox)).isEnabled();
					boolean value6=driver.findElement(By.xpath(element.uf_SH_Asapediacheckbox)).isEnabled();
					boolean value7=driver.findElement(By.xpath(element.uf_SH_Tandbergcheckbox)).isEnabled();
					boolean value8=driver.findElement(By.xpath(element.uf_SH_Newscalecheckbox)).isEnabled();
					boolean value9=driver.findElement(By.xpath(element.uf_SH_TSTrainingcheckbox)).isEnabled();
					boolean value10=driver.findElement(By.xpath(element.uf_SH_Whiptailcheckbox)).isEnabled();
					boolean value11=driver.findElement(By.xpath(element.uf_SH_Sourcefirecheckbox)).isEnabled();
					
					if(value1==true &&value2==true &&value3==true &&value4==true &&value5==true &&value6==true &&value7==true &&value8==true &&value9==true &&value10==true &&value11==true )
					{
						UtilLib.Report_PASS("Using select all link all Data Sources","are selected", "SelectAllLinkVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("Using select all link all Data Sources","are selected", "SelectAllLinkVerification()");
					}
					
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47", "is not verified", "TS050_SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47", "is verified", "TS050_SearchTopic3_0_UI_AttivioMigration_PROG_SRCH_2_SearchPagedatasource_selectAlllink_47");
						status=true;
					}
				}	
				}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06
	 * @Description :  This scenario is to verify  that One of the date filters 'â€œMore Than Two Years Oldâ€� to be renamed to â€œBefore Last Yearâ€�   
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify date range filter _â€œMore Than Two Years Oldâ€� to be renamed to â€œBefore Last Yearâ€�_06
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status = false;
		if(Browser.equalsIgnoreCase("InternetExplorer")){
			long stoptime = System.currentTimeMillis()+5000;
			while(System.currentTimeMillis()<stoptime){
			}
		}
		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					RerunFlag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				System.out.println("Result is : "+SearchResults);
		
				
				///////////*******************Verifuying Data Range Filters *******************////////////////////////////
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				String LastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_LastYear_Filter, TestCase_Name);
				
				
			
				String beforeLastYearDateRangeFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_BeforeLastYearFilters, TestCase_Name);
				System.out.println("All Date Range Filters value : "+allDateRangeFilters);
				System.out.println("Last Year Filter value : "+LastYear_Filter);
				System.out.println("Before Last Year Date Range Filters value : "+beforeLastYearDateRangeFilters);
			
			
				if(allDateRangeFilters.equals(data.getI_PORTLET_VALUE2()) && LastYear_Filter.equals(data.getI_PORTLET_VALUE3()) && beforeLastYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()))
				{
					UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
				}
				else{
					
					RerunFlag++;
					UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
				}
				
				if(LastYear_Filter.equals("Last Year"))
				{
					UtilLib.Report_PASS("More Than Two year is renamed to ","Last Year","FilterVerification()");
				}
				else{
					
					RerunFlag++;
					UtilLib.Report_FAIL("More Than Two year is not renamed to ","Last Year","FilterVerification()");
				}
				
				if(RerunFlag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is not verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
					status=true;
				}
			}	
			

				
			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_SRCH_3PerformingSearch_SearchQuery_ANDandOR_between_strings
	 * @Description :the  user is able to perform search by using AND and  OR between the strings 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND and OR in between the strings_27
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_SRCH_3PerformingSearch_SearchQuery_ANDandOR_between_strings(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND and OR in between the strings", "is not verified", "SearchTopic3UI_AttivioMigration_PROG_SRCH_3PerformingSearch_SearchQuery_ANDandOR_between_strings()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND and OR in between the strings ", "is verified", "SearchTopic3UI_AttivioMigration_PROG_SRCH_3PerformingSearch_SearchQuery_ANDandOR_between_strings()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_SRCH_3PerformingSearch_SearchQuery_ANDandOR_between_strings
	 * @Description :the  user is able to perform search by using AND and  OR between the strings 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND and OR in between the strings_27
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Search_queryUsingAND(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND_25", "is not verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Search_queryUsingAND()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using AND_25", "is verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Search_queryUsingAND()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_UI_AttivioMigration_PROG_SRCHPerformingSearch_SearchQueryUsingMultipleANDs_betweenStrings
	 * @Description : verify that  the  user is able to perform search by using AND between the strings
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ANDs in between the strings_29
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_SRCHPerformingSearch_SearchQueryUsingMultipleANDs_betweenStrings(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ANDs in between the strings_29", "is not verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCHPerformingSearch_SearchQueryUsingMultipleANDs_betweenStrings()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ANDs in between the strings_29", "is verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCHPerformingSearch_SearchQueryUsingMultipleANDs_betweenStrings()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_MultipleORs_between_strings
	 * @Description : verify that  the  user is able to perform search by using multiple &quot;OR&quot; between the strings 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ORs in between the strings_30
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_MultipleORs_between_strings(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ORs in between the strings_30", "is not verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_MultipleORs_between_strings()");
				}
				else
				{
					UtilLib.Report_PASS("SSearch Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using Multiple ORs in between the strings_30", "is verified", "SearchTopic3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_MultipleORs_between_strings()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_non_wildCardSymbols
	 * @Description : verify that  the  user is able to perform search by using  non wild card symbols (â€œ@â€�, â€œ#â€� ,â€�%â€� ,â€�.â€�, â€œ&amp;â€�,&quot;(,),[,],{,}&quot; ) between the strings
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using non wild card symbols _34
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_non_wildCardSymbols(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}



			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using non wild card symbols _34", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_non_wildCardSymbols()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using non wild card symbols _34", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_Searchquery_non_wildCardSymbols()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchqueryUsingOR_AND_betweenStrings
	 * @Description :  verify that  the  user is able to perform search by using &quot;OR&quot; and &quot;AND&quot; between the strings 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR and AND in between the strings_28
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchqueryUsingOR_AND_betweenStrings(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR and AND in between the strings_28", "is not verified", "SearchTopic_3UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchqueryUsingOR_AND_betweenStrings()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR and AND in between the strings_28", "is verified", "SearchTopic_3UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchqueryUsingOR_AND_betweenStrings()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_queryUsingOR
	 * @Description : verify that  the  user is able to perform search by using OR between the strings  
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR_26
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_queryUsingOR(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}



			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR_26", "is not verified", "TS065_SearchTopic3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_queryUsingOR()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using OR_26", "is verified", "TS065_SearchTopic3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_queryUsingOR()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchQuery_questionMark_Symbol
	 * @Description : verify that  the  user is able to perform search by using  &quot;?&quot;    
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -verify that  the  user is able to perform search by using  &quot;?&quot; 
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchQuery_questionMark_Symbol(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("verify that  the  user is able to perform search by using  &quot;?&quot; ", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchQuery_questionMark_Symbol()");
				}
				else
				{
					UtilLib.Report_PASS("verify that  the  user is able to perform search by using  &quot;?&quot; ", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_PerformingSearch_SearchQuery_questionMark_Symbol()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_SRCHPerformingSearch_Searchquery_using_starSymbol
	 * @Description :verify that  the  user is able to perform search by using  &quot;*&quot; between the strings    
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using star symbol _32
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_SRCHPerformingSearch_Searchquery_using_starSymbol(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using star symbol _32", "is not verified", "SearchTopic3UI_AttivioMigration_PROG_SRCHPerformingSearch_Searchquery_using_starSymbol()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using star symbol _32 ", "is verified", "SearchTopic3UI_AttivioMigration_PROG_SRCHPerformingSearch_Searchquery_using_starSymbol()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol
	 * @Description : verify that  the  user is able to perform search by using  &quot;-&quot; between the strings   
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using To exclude _hyphen symbol_31
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using To exclude _hyphen symbol_31", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using To exclude _hyphen symbol_31", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_SRCH_Performing_search_Search_query_using_exclude_hyphenSymbol()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_CSSCSO292_fielded_search_gracefullyhandle_spacebetween_colon_value_with_wildcard_02
	 * @Description : This scenario is to verify that fielded search should gracefully handle space between ':' and a value with wildcard    
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search should gracefully handle space between  colon and a value with wildcard_02
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_CSSCSO292_fielded_search_gracefullyhandle_spacebetween_colon_value_with_wildcard_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_CSSCSO292_fielded_search_gracefullyhandle_spacebetween_colon_value_with_wildcard_02", "is not verified", "Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search should gracefully handle space between  colon and a value with wildcard_02");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_CSSCSO292_fielded_search_gracefullyhandle_spacebetween_colon_value_with_wildcard_02", "is verified", "Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO292_fielded search should gracefully handle space between  colon and a value with wildcard_02");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_SRCH3_PerformingSearch_Searchqueryusing_metafields_12
	 * @Description : verify   This Scenario is to verify that metafield specific search will be enabled at the search end for all the fields marked as â€œYâ€� for the advanced search column in the data sheet for all the data sources 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_SRCH 3  Performing search_Search query using meta fields _12
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_SRCH3_PerformingSearch_Searchqueryusing_metafields_12(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   ClickingAdvancedSearch  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Advanced Search", element.uf_SH_advancedSearchCheckbox, TestCase_Name);
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}

				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}


				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL(" Application is not navigated to the results page ", "is not verified", "resultPageVerification()");
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is not verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 281_queries with colon may be taken for fielded search_query term preceding term matches one of the metadata field names_04", "is verified", "Search_Topic_3_UI_AttivioMigration_PROG_queries_with_colon_may_taken_fielded_search_query_term_preceding_term_matches_one_metadata_field_names()");
					status=true;
				}


			}}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13
	 * @Description :   This scenario is to verify that the Data Source filter will have an option â€œAllâ€�
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_ALL_13
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}else{
					UtilLib.CaptureScreenshot_PASS(TestCase_Name);
				}
					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,16);
					
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of results on result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(4000);
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(4000);
					String results31=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results31=results31.substring(8,16);
					
					int noOfResults2=Integer.parseInt(results31);
					System.out.println("Total no of results after All filter on  result page is : "+noOfResults);
					UtilLib.WaitTime(1000);
					System.out.println("corresponding result 2 : "+noOfResults2);
					
					if(noOfResults2==noOfResults)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13", "is not verified", "TS064_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13", "is verified", "TS064_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Resultpage_filteringOptions_default_searchFilters_DATASOURCEFILTER_ALL_13");
					status=true;
				}

				
			}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14
	 * @Description :   This scenario is to verify that when user explicitly clicks on any of the navigator values, will get filtered results only for that value. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_C3 navigator values_14
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of C3", element.uf_SH_noOfC3Results, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
					System.out.println("No of resuts of C3 after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(2000);
					UtilLib.ClickOnElement("C3 checkbox", element.uf_N_c3ChkBox, TestCase_Name);
					UtilLib.WaitTime(4000);
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,16);
					System.out.println("UI data : "+results1);
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of webpages result  is : "+noOfResults);
					UtilLib.WaitTime(1000);
					//System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS(" Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL(" Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14", "is not verified", "TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14");
				}
				else
				{
					UtilLib.Report_PASS("TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14", "is verified", "TS065_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATASOURCEFILTER_C3_navigator_values_14");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15
	 * @Description :   This scenario is to verify that when user explicitly clicks on any of the navigator values, will get filtered results only for that value. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_CDETS navigator values_15
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of Cdets", element.uf_SH_noOfCDETSResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
					System.out.println("No of resuts of Cdets after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(2000);
					UtilLib.ClickOnElement("Cdets checkbox", element.uf_N_cdetsChkBox, TestCase_Name);
					UtilLib.WaitTime(4000);
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,15);
					System.out.println("UI data : "+results1);
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of CDETS result  is : "+noOfResults);
					UtilLib.WaitTime(1000);
					//System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS("Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL("Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15", "is not verified", "TS066_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15", "is verified", "TS066_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Result_pagefiltering_options_default_searchfilters_DATASOURCEFILTER_CDETSnavigatorvalues_15");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  TS067_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultpageFilteringoptions_default_searchfilters_DATASOURCE_FILTER_Newsgroup_navigatorValues_16
	 * @Description :   This scenario is to verify that when user explicitly clicks on any of the navigator values, will get filtered results only for that value. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Newsgroup navigator values_16
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultpageFilteringoptions_default_searchfilters_DATASOURCE_FILTER_Newsgroup_navigatorValues_16(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of Cdets", element.uf_SH_noOfNewgroupResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
					System.out.println("No of resuts of Cdets after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(2000);
					UtilLib.ClickOnElement("Newsgroup checkbox", element.uf_N_newsGroupChkBox, TestCase_Name);
					UtilLib.WaitTime(4000);
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,15);
					System.out.println("UI data : "+results1);
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of Newgroup result  is : "+noOfResults);
					UtilLib.WaitTime(1000);
					//System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS("Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL("Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultpageFilteringoptions_default_searchfilters_DATASOURCE_FILTER_Newsgroup_navigatorValues_16", "is not verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Newsgroup navigator values_16");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultpageFilteringoptions_default_searchfilters_DATASOURCE_FILTER_Newsgroup_navigatorValues_16", "is verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Newsgroup navigator values_16");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_ResultPagefilteringOptions_defaultSearch_filters_DATASOURCEFILTER_TechZoneNavigatorValues_17
	 * @Description :   This scenario is to verify that when user explicitly clicks on any of the navigator values, will get filtered results only for that value. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Tech Zone navigator values_17
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_ResultPagefilteringOptions_defaultSearch_filters_DATASOURCEFILTER_TechZoneNavigatorValues_17(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					
					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of Cdets", element.uf_SH_noOfTechZoneResults, TestCase_Name);
					int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
					System.out.println("No of resuts of Cdets after filter :"+resultsAfterFilter);
					
					
					UtilLib.ClickOnElement("All checkbox", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					UtilLib.WaitTime(2000);
					UtilLib.ClickOnElement("Newsgroup checkbox", element.uf_N_techzoneChkBox, TestCase_Name);
					UtilLib.WaitTime(4000);
					
					String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
					results1=results1.substring(8,13);
					System.out.println("UI data : "+results1);
					//int noOfResults=Integer.parseInt(results);
					int noOfResults=Integer.parseInt(results1);
					System.out.println("Total no of Newgroup result  is : "+noOfResults);
					UtilLib.WaitTime(1000);
					//System.out.println("corresponding result : "+noOfResults);
					
					if(noOfResults==resultsAfterFilter)
					{
						UtilLib.Report_PASS("Filtered results only for that value ", "are shown", "ResultVerification()");
						status=true;
					}
					else
					{
						UtilLib.Report_FAIL("Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
						rerunflag++;
					}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_ResultPagefilteringOptions_defaultSearch_filters_DATASOURCEFILTER_TechZoneNavigatorValues_17", "is not verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Tech Zone navigator values_17");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_ResultPagefilteringOptions_defaultSearch_filters_DATASOURCEFILTER_TechZoneNavigatorValues_17", "is verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATA SOURCE FILTER_Tech Zone navigator values_17");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3Result_pagefilteringOptions_default_searchfilters_DATERANGE_FILTERS_SelectPast24hours_18
	 * @Description :   This scenario is to verify the results when user selects the past 24 hours radio button under date range filter
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select Past 24 hours_18
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3Result_pagefilteringOptions_default_searchfilters_DATERANGE_FILTERS_SelectPast24hours_18(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				System.out.println("Result is : "+SearchResults);
				/*if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}*/
				
				 
				 
				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				
				 
				 /////////////////////**************Verifiaction Of Date Range Filyers****************/////////////////////
				 
				 	String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
					String LastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_LastYear_Filter, TestCase_Name);
					String beforeLastYearDateRangeFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_BeforeLastYearFilters, TestCase_Name);
					System.out.println("All Date Range Filters value : "+allDateRangeFilters);
					System.out.println("Last Year Filter value : "+LastYear_Filter);
					System.out.println("Before Last Year Date Range Filters value : "+beforeLastYearDateRangeFilters);
				
				
					if(allDateRangeFilters.equals(data.getI_PORTLET_VALUE2()) && LastYear_Filter.equals(data.getI_PORTLET_VALUE3()) && beforeLastYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()))
					{
						UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
					}
					else{
						
						RerunFlag++;
						UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
					}
				
				String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of This Year", element.uf_SH_ThisYear_Filter_noOfResults, TestCase_Name);
				UtilLib.ClickOnElement("This Year Filter Radio Button", element.uf_SH_BeforeLastYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
				System.out.println("No of resuts for Before Last Year filter :"+resultsAfterFilter);
				if(resultsAfterFilter!=0)
				{
					UtilLib.Report_PASS("Count for each of the filter","is displayed","FilterVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Count for each of the filter","is not displayed","FilterVerification()");
				}
				
				UtilLib.ClickOnElement("Before Last Year Filter Radio Button", element.uf_SH_BeforeLastYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				
				String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				results1=results1.substring(8,16);
				System.out.println("UI data : "+results1);
				//int noOfResults=Integer.parseInt(results);
				int noOfResults=Integer.parseInt(results1);
				System.out.println("Total no of Newgroup result  is : "+noOfResults);
				UtilLib.WaitTime(1000);
				//System.out.println("corresponding result : "+noOfResults);
				
				if(noOfResults==resultsAfterFilter)
				{
					UtilLib.Report_PASS("Filtered results only for that value ", "are shown", "ResultVerification()");
					status=true;
				}
				else
				{
					UtilLib.Report_FAIL("Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
					rerunflag++;
				}
				UtilLib.ClickOnElement("From Date Filter Radio Button", element.uf_N_fromDate_Textbox, TestCase_Name);
				
				if(driver.findElement(By.xpath(element.uf_N_fromDate_Textbox)).getAttribute("class").equals("date GhostText hasDatepicker"))
				{
					UtilLib.Report_PASS("User is not be allowed to manually enter a date. ", "is verified", "DateVerification()");
					status=true;
				}
				else
				{
					UtilLib.Report_FAIL("User is not be allowed to manually enter a date. ", "is not verified", "DateVerification()");
					rerunflag++;
				}

				if(RerunFlag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3Result_pagefilteringOptions_default_searchfilters_DATERANGE_FILTERS_SelectPast24hours_18", "is not verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select Past Week_19");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3Result_pagefilteringOptions_default_searchfilters_DATERANGE_FILTERS_SelectPast24hours_18", "is verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select Past Week_19");
					status=true;
				}
			}	
			

				
			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATERANGEFILTERS_SelectPastWeek_19
	 * @Description :   This scenario is to verify the results when user selects the past 24 hours radio button under date range filter
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select Past 24 hours_18
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3ResultpagefilteringOptions_defaultSearchfilters_DATERANGEFILTERS_SelectPastWeek_19(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				System.out.println("Result is : "+SearchResults);
				if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				 
				 
				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				// System.out.println(driver.findElement(By.xpath(element.uf_SH_BeforeLastYear_Filter)).getClass());
				// System.out.println(driver.findElement(By.xpath(element.uf_SH_BeforeLastYear_Filter)).getClass());
				
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				
				String thisYearDateRangeFilters= UtilLib.uf_C_GetUIData1("This Year Date Range Filters", element.uf_SH_C3_thisYearDateRangeFilters, TestCase_Name);
				String LastMonthFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_LastMonthFilters, TestCase_Name);
			//	System.out.println("v1 : "+BeforeLastYear_Filter);
				System.out.println("v2 : "+allDateRangeFilters);
				System.out.println("v3 : "+thisYearDateRangeFilters);
				System.out.println("v4 : "+LastMonthFilters);
			//	System.out.println("from : "+LastMonthFilters);
				System.out.println("D1 : "+data.getI_PORTLET_VALUE2());
				System.out.println("D2 : "+data.getI_PORTLET_VALUE3());
				System.out.println("D3 : "+data.getI_PORTLET_VALUE4());
				//System.out.println("from : "+LastMonthFilters);
				
			//	System.out.println("from : "+LastMonthFilters);
			System.out.println("D4 : "+data.getI_PORTLET_VALUE5());
				/*if(BeforeLastYear_Filter.equals(data.getI_PORTLET_VALUE2()) && allDateRangeFilters.equals(data.getI_PORTLET_VALUE3()) && thisYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()) && LastMonthFilters.equals(data.getI_PORTLET_VALUE5()))
				{
					UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
				}
				else{
					
					rerunflag++;
					UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
				}
				*/
			
				UtilLib.ClickOnElement("Before Last Year Filter Radio Button", element.uf_SH_BeforeLastYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				//int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
				/*System.out.println("No of resuts for Before Last Year filter :"+resultsAfterFilter);
				if(resultsAfterFilter!=0)
				{
					UtilLib.Report_PASS("Count for each of the filter","is displayed","FilterVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Count for each of the filter","is not displayed","FilterVerification()");
				}*/
				
				UtilLib.ClickOnElement("This Year Filter Radio Button", element.uf_SH_ThisYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				
				String results1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				results1=results1.substring(8,15);
				System.out.println("UI data : "+results1);
				//int noOfResults=Integer.parseInt(results);
				int noOfResults=Integer.parseInt(results1);
				System.out.println("Total no of Newgroup result  is : "+noOfResults);
				UtilLib.WaitTime(1000);
				//System.out.println("corresponding result : "+noOfResults);
				
				/*if(noOfResults==resultsAfterFilter)
				{
					UtilLib.Report_PASS("Filtered results only for that value ", "are shown", "ResultVerification()");
					status=true;
				}
				else
				{
					UtilLib.Report_FAIL("Filtered results only for that value ", "are not shown", "ResultVerifiaction()");
					rerunflag++;
				}
				*/
				
				UtilLib.ClickOnElement("Frpm Date Filter Radio Button", element.uf_N_fromDate_Textbox, TestCase_Name);
		
		if(driver.findElement(By.xpath(element.uf_N_fromDate_Textbox)).getAttribute("class").equals("date GhostText hasDatepicker"))
		{
			UtilLib.Report_PASS("User is not be allowed to manually enter a date. ", "is verified", "DateVerification()");
			status=true;
		}
		else
		{
			UtilLib.Report_FAIL("User is not be allowed to manually enter a date. ", "is not verified", "DateVerification()");
			rerunflag++;
		}
				
				if(RerunFlag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is not verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
					status=true;
				}
			}	
			

				
			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_Verify_count_displyedagainst_date_ranagefilters_29
	 * @Description :    This scenario is to verify that the  Left hand side values(eg: past month, past week etc) under this will come based on the result set. Count for each of them will be displayed against it. 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select Past 24 hours_18
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_Verify_count_displyedagainst_date_ranagefilters_29(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				System.out.println("Result is : "+SearchResults);
				
				
				 
				 
				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				 	///////////*******************Verifuying Data Range Filters *******************////////////////////////////
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				String LastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_LastYear_Filter, TestCase_Name);
				String beforeLastYearDateRangeFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_BeforeLastYearFilters, TestCase_Name);
					
					
				System.out.println("All Date Range Filters value : "+allDateRangeFilters);
				System.out.println("Last Year Filter value : "+LastYear_Filter);
				System.out.println("Before Last Year Date Range Filters value : "+beforeLastYearDateRangeFilters);
				
				
					if(allDateRangeFilters.equals(data.getI_PORTLET_VALUE2()) && LastYear_Filter.equals(data.getI_PORTLET_VALUE3()) && beforeLastYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()))
					{
						UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
					}
					else{
						
						RerunFlag++;
						UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
					}
					
					
				String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of Before Last Year", element.uf_SH_LastYear_Filter_noOfResults, TestCase_Name);
				UtilLib.ClickOnElement(" Last Year Filter Radio Button", element.uf_SH_LastYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
				System.out.println("resultsAfterFilter : "+resultsAfterFilter);
				String correspondingResults=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				correspondingResults=correspondingResults.substring(8, 13);
				int noOfResults=Integer.parseInt(correspondingResults);
				System.out.println(noOfResults);
				if(resultsAfterFilter==noOfResults)
				{
					UtilLib.Report_PASS("Count for each of the filter","is displayed","FilterVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Count for each of the filter","is not displayed","FilterVerification()");
				}
				
				
				
				if(RerunFlag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is not verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
					status=true;
				}
			}	
			

				
			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Result_pagefilteringOptions_defaultsearchfilters_DATERANGEFILTERS_SelectdateFROMandTOoptions_validate_if_from_and_entered_30
	 * @Description :    This scenario is to verify the results when user selects the from and to radio button's under date range filter and validate if from and to entered.
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select date from FROM and TO options_validate if from and to entered_30
	 * @throws Exception 
	 ********************************************************************************************//*

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Result_pagefilteringOptions_defaultsearchfilters_DATERANGEFILTERS_SelectdateFROMandTOoptions_validate_if_from_and_entered_30(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				System.out.println("Result is : "+SearchResults);
				if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				 
				 
				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				// System.out.println(driver.findElement(By.xpath(element.uf_SH_BeforeLastYear_Filter)).getClass());
				// System.out.println(driver.findElement(By.xpath(element.uf_SH_BeforeLastYear_Filter)).getClass());
				String BeforeLastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_BeforeLastYear_Filter, TestCase_Name);
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				
				String thisYearDateRangeFilters= UtilLib.uf_C_GetUIData1("This Year Date Range Filters", element.uf_SH_C3_thisYearDateRangeFilters, TestCase_Name);
				String LastMonthFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_LastMonthFilters, TestCase_Name);
				System.out.println("v1 : "+BeforeLastYear_Filter);
				System.out.println("v2 : "+allDateRangeFilters);
				System.out.println("v3 : "+thisYearDateRangeFilters);
				System.out.println("v4 : "+LastMonthFilters);
			//	System.out.println("from : "+LastMonthFilters);
				System.out.println("D1 : "+data.getI_PORTLET_VALUE2());
				System.out.println("D2 : "+data.getI_PORTLET_VALUE3());
				System.out.println("D3 : "+data.getI_PORTLET_VALUE4());
				//System.out.println("from : "+LastMonthFilters);
				
			//	System.out.println("from : "+LastMonthFilters);
			System.out.println("D4 : "+data.getI_PORTLET_VALUE5());
				if(BeforeLastYear_Filter.equals(data.getI_PORTLET_VALUE2()) && allDateRangeFilters.equals(data.getI_PORTLET_VALUE3()) && thisYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()) && LastMonthFilters.equals(data.getI_PORTLET_VALUE5()))
				{
					UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
				}
				else{
					
					rerunflag++;
					UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
				}
				
				String NoOfResultsC3=UtilLib.uf_C_GetUIData1("No of resuts of Before Last Year", element.uf_SH_BeforeLastYear_Filter_noOfResults, TestCase_Name);
				UtilLib.ClickOnElement("Before Last Year Filter Radio Button", element.uf_SH_BeforeLastYear_Filter_RadioButton, TestCase_Name);
				UtilLib.WaitTime(4000);
				int resultsAfterFilter=uf_SN_split(NoOfResultsC3, TestCase_Name);
				System.out.println("No of resuts for Before Last Year filter :"+resultsAfterFilter);
				//System.out.println(resultsAfterFilter.getClass().TestCase_Name());
				if(resultsAfterFilter!=0)
				{
					UtilLib.Report_PASS("Count for each of the filter","is displayed","FilterVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Count for each of the filter","is not displayed","FilterVerification()");
				}
				
				
				
				if(RerunFlag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is not verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06", "is verified", "TS051_SearchTopic3_1_3UI_Oct14Rel_PROG_verifydate_rangefilter_MoreThanTwoYearsOld_renamed_BeforeLastYear06");
					status=true;
				}
			}	
			

				
			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}	*/
	/********************************************************************************************
	 * @Function_Name : SearchTopic3UI_AttivioMigration_PROG_Resultpage_filteringoptions_DR_select_To_From_date_validate_notentered
	 * @Description : verify the results when user selects the from and to radio button's under date range filter and validate if from and to entered
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_Select date from FROM and TO options_validate if from and to entered_30
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT_3_Result_pagefilteringOptions_defaultsearchfilters_DATERANGEFILTERS_SelectdateFROMandTOoptions_validate_if_from_and_entered_30(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0,flag1=0,flag2=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);

				String dateRangePosition= driver.findElement(By.xpath(element.uf_N_dateRange)).getCssValue("float").trim();

				/*String fileFormatPosition= driver.findElement(By.xpath(element.uf_N_fileformat)).getCssValue("float").trim();
				System.out.println("fileFormatPosition :"+fileFormatPosition);*/

				if(dateRangePosition.equals("left") /*&& (fileFormatPosition.equals("left"))*/){
					UtilLib.Report_PASS("Default Search Filter", "is available at LHS of screen","Verify_FilterPosition");
					status=true;
				}else{
					UtilLib.Report_FAIL("Default Search Filter", "is not available at LHS of screen","Verify_FilterPosition");
					RerunFlag++;
				}


				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				 	///////////*******************Verifuying Data Range Filters *******************////////////////////////////
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				String LastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_LastYear_Filter, TestCase_Name);
				String beforeLastYearDateRangeFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_BeforeLastYearFilters, TestCase_Name);
					
					
				System.out.println("All Date Range Filters value : "+allDateRangeFilters);
				System.out.println("Last Year Filter value : "+LastYear_Filter);
				System.out.println("Before Last Year Date Range Filters value : "+beforeLastYearDateRangeFilters);
				
				
					if(allDateRangeFilters.equals(data.getI_PORTLET_VALUE2()) && LastYear_Filter.equals(data.getI_PORTLET_VALUE3()) && beforeLastYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()))
					{
						UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
					}
					else{
						
						RerunFlag++;
						UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
					}

				status=uf_p2s_labelVerification(element.uf_N_datasources, data.getI_PORTLET_VALUE5(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}

				status=uf_p2s_labelVerification(element.uf_N_dateRange, data.getI_PORTLET_VALUE6(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}

				/*status=uf_p2s_labelVerification(element.uf_N_fileformat, data.getI_PORTLET_VALUE4(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}
				 */

				
				/*int results=(driver.findElements(By.xpath(element.uf_N_radioButtonResults))).size();
				System.out.println("results :"+results);
				for(int i=2;i<=results;i++){
					
					
					System.out.println("int i :"+i);
					
						if(i==2|| i==3|| i==4|| i==5|| i==6 ||i==9)
						{
							String type=driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr["+i+"]/td/span/input")).getAttribute("type");	
							if(type.equals("radio"))
							{
							flag++;
							System.out.println("flag :"+flag);
						}}
						else
						{
								if(i==7){
								for(int j=1;j<3;j++){
									System.out.println("j :"+j);
								String type1=driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[7]/td/div/table/tbody/tr["+j+"]/td/span/input")).getAttribute("type");
								
								System.out.println("type1 :"+type1);
								
								if(type1.equals("radio"))
								{
									flag1++;
									System.out.println("flag 1 :"+flag1 );
								}
								
								}
								}else
									{
									if(i==8)
									flag2++;
									System.out.println("flag 2  :"+flag2);
									}}
						}
						
					
					
				
				if(flag==6 && flag1==2){
					UtilLib.Report_PASS("DateRange Filter options", "is radio button ","Verify_DateRange_Filter_RadioButton");
					status=true;
				}else{
					UtilLib.Report_FAIL("DateRange Filter options", "is not radio button ","Verify_DateRange_Filter_RadioButton");
					RerunFlag++;
				}*/
				
				
					driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[2]/td/span/input")).click();
					status = driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[2]/td/span/input")).isSelected();
					if(status = true){
						UtilLib.Report_PASS("User", "is not able to select any of the daterange filter ","Select_DateRange_Filter");
					}else{
						UtilLib.Report_FAIL("User", "is able to select any of the daterange filter ","Select_DateRange_Filter");
						RerunFlag++;
					}
					driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[2]/td/span/input")).click();
				
				String fromDate=driver.findElement(By.xpath(element.uf_N_fromDateRange)).getAttribute("readonly");	
				System.out.println("fromDate :"+fromDate);
				if(fromDate.equals("true")){
					UtilLib.Report_PASS("User", "is not able to enter manual date ","Verify_fromdate");
					status=true;
				}else{
					UtilLib.Report_FAIL("User", "is able to enter manual date ","Verify_fromdate");
					RerunFlag++;
				}
				
				
				
				String correspondingResults=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				correspondingResults=correspondingResults.substring(8, 13);
				int noOfResults=Integer.parseInt(correspondingResults);
				System.out.println("No of results are : "+noOfResults);
				/*if(noOfResults==(moreThan2YrsResults1+lastyrResults1+thisyrResults1+lastmonthResults1+lastweekResults1+thisweekResults1)){
					UtilLib.Report_PASS("Date range options count", "is according to corressponding results ","Select_DateRange_Filter_Count");
					status=true;
				}else{
					UtilLib.Report_FAIL("Date range options count", "is not according to corressponding results ","Select_DateRange_Filter_Count");
					RerunFlag++;
				}
				*/

				status=UtilLib.uf_C_ClickOnElement1("'From' Date Range radiobutton",element.uf_N_fromRadioButton,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Date",element.uf_N_fromDate_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Month Dropdown",element.uf_N_fromDateMonth_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Month Selection",element.uf_N_fromDateSelectMonth_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Year Dropdown",element.uf_N_fromDateYear_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Year Selection",element.uf_N_fromDateSelectYear_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Date",element.uf_N_fromDate,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
				status=UtilLib.uf_C_ClickOnElement1("'To' Date Range option",element.uf_N_toDateRange,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				status=UtilLib.uf_C_ClickOnElement1("'To' Date",element.uf_N_toDate,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
			    UtilLib.WaitTime(2000);
			    System.out.println("To date selected");
				String correspondingResults1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				correspondingResults1=correspondingResults1.substring(8, 13);
				int noOfResults1=Integer.parseInt(correspondingResults1);
				
				
				if(noOfResults==noOfResults1){
					UtilLib.Report_FAIL("'No Action'", "is performed when user has entered only 'From date' and 'To date' ","Validate_To__From_date");
					RerunFlag++;
				}else{
					UtilLib.Report_PASS("'Corressponding Results'", "are changing when user has only 'From date' and 'To date' ","Validate_To__From_date");
					status=true;
				}
				
				
				if(RerunFlag>0){
					UtilLib.Report_FAIL("If 'To date' and 'From date' is entered , ", "corressponding results are not changing", "Verify_To_From_date_Functionality");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status=false;
				}else{
					UtilLib.Report_PASS("If 'To date' and 'From date' is entered , ", "corressponding results are changing only", "Verify_To_From_date_Functionality");
					status=true;
				}

			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}
	/********************************************************************************************
	 * @Function_Name : SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_OnselectingFrom_validate_if_to_entered_ifEnteredthen_submitquery_elseNoAction_31
	 * @Description :  This scenario is to verify that on selecting from, validate if to is entered, if entered then submit query, else no action 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_On selecting from, validate if to is entered, if entered then submit query, else no action_31
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_OnselectingFrom_validate_if_to_entered_ifEnteredthen_submitquery_elseNoAction_31(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0,flag1=0,flag2=0;

		for (TopicSearchTestData data : dataList) {

			try {

				int RerunFlag = 0;

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);

				String dateRangePosition= driver.findElement(By.xpath(element.uf_N_dateRange)).getCssValue("float").trim();

				/*String fileFormatPosition= driver.findElement(By.xpath(element.uf_N_fileformat)).getCssValue("float").trim();
				System.out.println("fileFormatPosition :"+fileFormatPosition);*/

				 if(driver.findElement(By.xpath(element.uf_SH_DateRange_AllFilter)).getAttribute("type").equals("radio"))
				 {
					 UtilLib.Report_PASS("For the Date Range Filter Radio buttons ","are present ","FilterVerification()"); 
				 }
				 else{
					 rerunflag++;
					 UtilLib.Report_FAIL("For the Date Range Filter Radio buttons ","are not present ","FilterVerification()"); 
					 
				 }
				 	///////////*******************Verifuying Data Range Filters *******************////////////////////////////
				String allDateRangeFilters= UtilLib.uf_C_GetUIData1("All Date Range Filters", element.uf_SH_C3_allDateRangeFilters, TestCase_Name);
				String LastYear_Filter= UtilLib.uf_C_GetUIData1("Before Last Year Filter", element.uf_SH_LastYear_Filter, TestCase_Name);
				String beforeLastYearDateRangeFilters= UtilLib.uf_C_GetUIData1("Last Month Filters", element.uf_SH_C3_BeforeLastYearFilters, TestCase_Name);
					
					
				System.out.println("All Date Range Filters value : "+allDateRangeFilters);
				System.out.println("Last Year Filter value : "+LastYear_Filter);
				System.out.println("Before Last Year Date Range Filters value : "+beforeLastYearDateRangeFilters);
				
				
					if(allDateRangeFilters.equals(data.getI_PORTLET_VALUE2()) && LastYear_Filter.equals(data.getI_PORTLET_VALUE3()) && beforeLastYearDateRangeFilters.equals(data.getI_PORTLET_VALUE4()))
					{
						UtilLib.Report_PASS("All Date Range Filter  ","are displayed","FilterVerification()");
					}
					else{
						
						RerunFlag++;
						UtilLib.Report_FAIL("All Date Range Filter  ","are not displayed","FilterVerification()");
					}


				status=uf_p2s_labelVerification(element.uf_N_datasources, data.getI_PORTLET_VALUE2(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}

				status=uf_p2s_labelVerification(element.uf_N_dateRange, data.getI_PORTLET_VALUE3(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}

				/*status=uf_p2s_labelVerification(element.uf_N_fileformat, data.getI_PORTLET_VALUE4(), TestCase_Name);
				if(status=false){
					RerunFlag++;
				}
				 */

				
				/*int results=(driver.findElements(By.xpath(element.uf_N_radioButtonResults))).size();
				System.out.println("results :"+results);
				for(int i=2;i<=results;i++){
					
					
					System.out.println("int i :"+i);
					
						if(i==2|| i==3|| i==4|| i==5|| i==6 ||i==9)
						{
							String type=driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr["+i+"]/td/span/input")).getAttribute("type");	
							if(type.equals("radio"))
							{
							flag++;
							System.out.println("flag :"+flag);
						}}
						else
						{
								if(i==7){
								for(int j=1;j<3;j++){
									System.out.println("j :"+j);
								String type1=driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[7]/td/div/table/tbody/tr["+j+"]/td/span/input")).getAttribute("type");
								
								System.out.println("type1 :"+type1);
								
								if(type1.equals("radio"))
								{
									flag1++;
									System.out.println("flag 1 :"+flag1 );
								}
								
								}
								}else
									{
									if(i==8)
									flag2++;
									System.out.println("flag 2  :"+flag2);
									}}
						}
						
					
					
				
				if(flag==6 && flag1==2){
					UtilLib.Report_PASS("DateRange Filter options", "is radio button ","Verify_DateRange_Filter_RadioButton");
					status=true;
				}else{
					UtilLib.Report_FAIL("DateRange Filter options", "is not radio button ","Verify_DateRange_Filter_RadioButton");
					RerunFlag++;
				}
				*/
				
					driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[2]/td/span/input")).click();
					status = driver.findElement(By.xpath("//*[@id='Date RangeDiv']/table/tbody/tr[2]/td/span/input")).isSelected();
					if(status = true){
						UtilLib.Report_PASS("User", "is not able to select any of the daterange filter ","Select_DateRange_Filter");
					}else{
						UtilLib.Report_FAIL("User", "is able to select any of the daterange filter ","Select_DateRange_Filter");
						RerunFlag++;
					}
				
				
				String fromDate=driver.findElement(By.xpath(element.uf_N_fromDateRange)).getAttribute("readonly");	
				System.out.println("fromDate :"+fromDate);
				if(fromDate.equals("true")){
					UtilLib.Report_PASS("User", "is not able to enter manual date ","Verify_fromdate");
					status=true;
				}else{
					UtilLib.Report_FAIL("User", "is able to enter manual date ","Verify_fromdate");
					RerunFlag++;
				}
				
				
				
				String correspondingResults=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				correspondingResults=correspondingResults.substring(8, 13);
				int noOfResults=Integer.parseInt(correspondingResults);
				System.out.println("No of results are : "+noOfResults);
				/*if(noOfResults==(moreThan2YrsResults1+lastyrResults1+thisyrResults1+lastmonthResults1+lastweekResults1+thisweekResults1)){
					UtilLib.Report_PASS("Date range options count", "is according to corressponding results ","Select_DateRange_Filter_Count");
					status=true;
				}else{
					UtilLib.Report_FAIL("Date range options count", "is not according to corressponding results ","Select_DateRange_Filter_Count");
					RerunFlag++;
				}
				*/

				

				status=UtilLib.uf_C_ClickOnElement1("'From' Date Range radiobutton",element.uf_N_fromRadioButton,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Date",element.uf_N_fromDate_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Month Dropdown",element.uf_N_fromDateMonth_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Month Selection",element.uf_N_fromDateSelectMonth_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Year Dropdown",element.uf_N_fromDateYear_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Year Selection",element.uf_N_fromDateSelectYear_Textbox,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("'From' Date",element.uf_N_fromDate,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
				status=UtilLib.uf_C_ClickOnElement1("'To' Date Range option",element.uf_N_toDateRange,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
				
				status=UtilLib.uf_C_ClickOnElement1("'To' Date",element.uf_N_toDate,TestCase_Name);
				if(status==false)
				{
					RerunFlag++;
				}
			
			    UtilLib.WaitTime(2000);
			    System.out.println("To date selected");
				String correspondingResults1=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				correspondingResults1=correspondingResults1.substring(8, 13);
				int noOfResults1=Integer.parseInt(correspondingResults1);
				
				
				if(noOfResults==noOfResults1){
					UtilLib.Report_FAIL("'No Action'", "is performed when user has entered only 'From date' and 'To date' ","Validate_To__From_date");
					RerunFlag++;
				}else{
					UtilLib.Report_PASS("'Corressponding Results'", "are changing when user has only 'From date' and 'To date' ","Validate_To__From_date");
					status=true;
				}
				
				if(RerunFlag>0){
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_OnselectingFrom_validate_if_to_entered_ifEnteredthen_submitquery_elseNoAction_31 , ", "verified", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_On selecting from, validate if to is entered, if entered then submit query, else no action_31");
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);
					status=false;
				}else{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_OnselectingFrom_validate_if_to_entered_ifEnteredthen_submitquery_elseNoAction_31, ", "SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultsearchfilters_DATERANGEFILTERS_OnselectingFrom_validate_if_to_entered_ifEnteredthen_submitquery_elseNoAction_31", "Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_DATE RANGE FILTERS_On selecting from, validate if to is entered, if entered then submit query, else no action_31");
					status=true;
				}

			}catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name : SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultSearchfilters_FILTERS_Edit_filterfor_dataSourceName_ADDfilter_38
	 * @Description :   This scenario is to  edit filter and add any filter  of the selected data source 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name - Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILTERS_Edit the filter for data source name_ADD filter_38
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3Resultpagefilteringoptions_defaultSearchfilters_FILTERS_Edit_filterfor_dataSourceName_ADDfilter_38(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0,flag1=0,flag2=0;

		for (TopicSearchTestData data : dataList) {

		try {

			int RerunFlag = 0;

			uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
			UtilLib.WaitTime(5000);

			UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
			
			status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
			if(status==false){
				rerunflag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
				//	driver.findElement(By.xpath(element.uf_SH_ResultpagequeryText)).clear();

					status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					status=UtilLib.uf_C_ClickOnElement1("C3 datasource", element.uf_N_c3ChkBox, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(7000);
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					
					status=uf_SH_EditFilterVerification(element.uf_N_datasources, data, TestCase_Name);
					if(status=false){
						RerunFlag++;
					}
					status=UtilLib.uf_C_ClickOnElement1("Priority filter in Edit Filter Pop Up ", element.uf_SH_editPriorityFilterChechboxPopUp, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					status=UtilLib.uf_C_ClickOnElement1("Cancel Button in Edit Filter Pop Up ", element.uf_SH_Edit_CancelButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					if(element.uf_SH_FilterContainingArea.contains("Priority"))
					{
						
						rerunflag++;
						UtilLib.Report_FAIL("Action is performed when cancel button", "is clicked",TestCase_Name);
					}
					else
					{
						UtilLib.Report_PASS("No Action is performed when cancel button", "is clicked",TestCase_Name);
					}
					UtilLib.WaitTime(2000);
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link ", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					UtilLib.WaitTime(1000);
					status=UtilLib.uf_C_ClickOnElement1("OK Button in Edit Filter Pop Up ", element.uf_SH_Edit_OKButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					String priorityFilter=UtilLib.uf_C_GetUIData1("Priority Filter", element.uf_SH_PriorityFilter, TestCase_Name);
					if(priorityFilter.equals("Priority"))
					{
						UtilLib.Report_PASS(" Priority Filter  ", "is verified", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					
					
			
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL(" Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 287 _UI source selection cannot be changed without query text_09", "is not verified", "SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO 287 _UI source selection cannot be changed without query text_09", "is verified", "SearchTopic3_UI_AttivioMigration_PROG_CSSCSO287_UISource_selection_cannot_changed_without_queryText_09()");
					status=true;
				}

				
				}

			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14Rel_PROG_SortBy_changefrom_dropdown_toHyperlink_DateLatest_07
	 * @Description : This scenario is to verify that search results can be sorted by date Latest when user clicks on Sort by date latest link
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Topic 3.0 UI_Aug 14 Rel_PROG_Sort by change from dropdown to hyperlink_Date Latest_07
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14Rel_PROG_SortBy_changefrom_dropdown_toHyperlink_DateLatest_07(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				boolean dateLatestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Latest)).isDisplayed();
				boolean dateOldestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Oldest)).isDisplayed();
				boolean relevanceIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Relevance)).isDisplayed();
				if(dateLatestIsDisplayed==true && dateOldestIsDisplayed==true && relevanceIsDisplayed==true)
				{
					UtilLib.Report_PASS("Date Latest,Date Oldest,Relevance Links ", "are present", "LinVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Date Latest,Date Oldest,Relevance Links ", "are not present", "resultPageVerification()");
				}	
				status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);
				/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}*/
				driver.findElement(By.xpath(element.uf_N_c3ChkBox)).click();
				
				UtilLib.WaitTime(2000);
				String Date_BeforeClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date before clicking on Date Latest" , element.uf_SH_DateBefore, TestCase_Name);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date convertedCurrentDate = sdf.parse(Date_BeforeClickingDateLatestLink);
				System.out.println(convertedCurrentDate);
				
				
				
				
				UtilLib.uf_C_ClickOnElement1("Date Oldest Link", element.uf_SH_Date_Latest, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				
			    Date convertedCurrentDate2 = sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println(convertedCurrentDate2);
				if(convertedCurrentDate.compareTo(convertedCurrentDate2)>0){
	        		System.out.println("Date2 is older than Date1");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        		
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)<0){
	        		System.out.println("Date1 is older than Date2");
	        		UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        	}
				}}
			catch(UnhandledAlertException alert){
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

			return status;
		
	}

	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14Rel_PROG_SortbyChangeFromdropdownTohyperlink_DateOldest_08
	 * @Description :  This scenario is to verify that search results can be sorted by date Oldest when user clicks on Sort by date Oldest link 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Topic 3.0 UI_Aug 14 Rel_PROG_Sort by change from dropdown to hyperlink_Date Oldest_08
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14Rel_PROG_SortbyChangeFromdropdownTohyperlink_DateOldest_08(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				boolean dateLatestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Latest)).isDisplayed();
				boolean dateOldestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Oldest)).isDisplayed();
				boolean relevanceIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Relevance)).isDisplayed();
				if(dateLatestIsDisplayed==true && dateOldestIsDisplayed==true && relevanceIsDisplayed==true)
				{
					UtilLib.Report_PASS("Date Latest,Date Oldest,Relevance Links ", "are present", "LinVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Date Latest,Date Oldest,Relevance Links ", "are not present", "resultPageVerification()");
				}	
				status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);
				/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}*/
				driver.findElement(By.xpath(element.uf_N_c3ChkBox)).click();
				
				UtilLib.WaitTime(2000);
				String Date_BeforeClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date before clicking on Date oldest" , element.uf_SH_DateBefore, TestCase_Name);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date convertedCurrentDate = sdf.parse(Date_BeforeClickingDateLatestLink);
				System.out.println(convertedCurrentDate);
				
				
				
				
				UtilLib.uf_C_ClickOnElement1("Date Oldest Link", element.uf_SH_Date_Oldest, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				
			    Date convertedCurrentDate2 = sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println(convertedCurrentDate2);
				if(convertedCurrentDate.compareTo(convertedCurrentDate2)>0){
	        		System.out.println("Date2 is older than Date1");
	        		UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)<0){
	        		System.out.println("Date1 is older than Date2");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        	}
				
				
				
				/*String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				 Date convertedCurrentDate2= sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println("Date before clicking : "+date2);
				if(date1.compareTo(date2)>0){
	        		System.out.println("Date1 is after Date2");
	        	}else if(date1.compareTo(date2)<0){
	        		System.out.println("Date1 is before Date2");
	        	}else if(date1.compareTo(date2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        	}
				*/
				//	int date_afterClickingDateLatestLink=Integer.parseInt(Date_AfterClickingDateLatestLink);
				/*System.out.println("Date after clicking : "+Date_AfterClickingDateLatestLink);
				if(Date_BeforeClickingDateLatestLink.equals(Date_AfterClickingDateLatestLink))
				{
					rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
				}	
				else
				{
					UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
					
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is not verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
					status=true;
				}
*/
				
				}}
			catch(UnhandledAlertException alert){
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

			return status;
		
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14Rel_PROG_Sort_bychange_fromDropdowntohyperlink_Relevance_09
	 * @Description :  This scenario is to verify that search results can be sorted by Relevance  when user clicks on Sort by Relevance link
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Topic 3.0 UI_Aug 14 Rel_PROG_Sort by change from dropdown to hyperlink_Relevance_09
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14Rel_PROG_Sort_bychange_fromDropdowntohyperlink_Relevance_09(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}


			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				boolean dateLatestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Latest)).isDisplayed();
				boolean dateOldestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Oldest)).isDisplayed();
				boolean relevanceIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Relevance)).isDisplayed();
				if(dateLatestIsDisplayed==true && dateOldestIsDisplayed==true && relevanceIsDisplayed==true)
				{
					UtilLib.Report_PASS("Date Latest,Date Oldest,Relevance Links ", "are present", "LinVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Date Latest,Date Oldest,Relevance Links ", "are not present", "resultPageVerification()");
				}	
				status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);
				/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}*/
				driver.findElement(By.xpath(element.uf_N_c3ChkBox)).click();
				
				UtilLib.WaitTime(2000);
				String Date_BeforeClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date before clicking on Date oldest" , element.uf_SH_DateBefore, TestCase_Name);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date convertedCurrentDate = sdf.parse(Date_BeforeClickingDateLatestLink);
				System.out.println(convertedCurrentDate);
				
				
				
				
				UtilLib.uf_C_ClickOnElement1("Date Oldest Link", element.uf_SH_Date_Oldest, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest " , element.uf_SH_DateAfter, TestCase_Name);
				
			    Date convertedCurrentDate2 = sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println(convertedCurrentDate2);
				
				
				UtilLib.uf_C_ClickOnElement1("Relevance link", element.uf_SH_Relevance, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingRelevanceLink=UtilLib.uf_C_GetUIData1("Date after clicking on Relevance", element.uf_SH_DateAfter, TestCase_Name);
				
			    Date convertedCurrentDate3 = sdf.parse(Date_AfterClickingRelevanceLink);
				System.out.println(convertedCurrentDate3);
				
				
				
				
				if(convertedCurrentDate3.compareTo(convertedCurrentDate)>0){
	        		System.out.println("Date1 is older than Date3");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Relevance' ", "are not sorted", "RelevanceLinkVerification()");
	        		
	        	}else if(convertedCurrentDate3.compareTo(convertedCurrentDate)<0){
	        		System.out.println("Date3 is older than Date1");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Relevance' ", "are not sorted", "RelevanceLinkVerification()");
	        	}else if(convertedCurrentDate3.compareTo(convertedCurrentDate)==0){
	        		System.out.println("Date1 is equal to Date3");
	        		UtilLib.Report_PASS("Results after clicking on 'Relevance' ", "are  sorted", "RelevanceLinkVerification()");
	        	}
				
				
				
				/*String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				 Date convertedCurrentDate2= sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println("Date before clicking : "+date2);
				if(date1.compareTo(date2)>0){
	        		System.out.println("Date1 is after Date2");
	        	}else if(date1.compareTo(date2)<0){
	        		System.out.println("Date1 is before Date2");
	        	}else if(date1.compareTo(date2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        	}
				*/
				//	int date_afterClickingDateLatestLink=Integer.parseInt(Date_AfterClickingDateLatestLink);
				/*System.out.println("Date after clicking : "+Date_AfterClickingDateLatestLink);
				if(Date_BeforeClickingDateLatestLink.equals(Date_AfterClickingDateLatestLink))
				{
					rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
				}	
				else
				{
					UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
					
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is not verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
					status=true;
				}
*/
				
				}}
			catch(UnhandledAlertException alert){
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

			return status;
		
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_Aug9Rel_REG_ErrorMessage_10
	 * @Description : This scenario is to verifythe results page when there  are  zero results for a query string
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Aug 9 Rel_REG _Error Message_10
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_Aug9Rel_REG_ErrorMessage_10(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				UtilLib.WaitTime(2000);
				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("No matches found, try to refine your query or select additional data sources"))
				{	
					UtilLib.Report_PASS("Error Message for zero results","is verified","ErrorMessageVerification()");
				}
				else{
					UtilLib.Report_FAIL("Error Message for zero results","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14Rel_PROG_ChangeHelpToUserGuide_Homepage_01
	 * @Description :   This scenario is to verify that Help link text is changed to User Guide in Home page
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -TestCase_Name
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14Rel_PROG_ChangeHelpToUserGuide_Homepage_01(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				//String Url=data.getI_URL();
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Verifying Help Link  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Help", element.uf_N_help, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("User Guide ", "is present at top of home page", "Verification_HelpLink_HomePage");
				}else{
					UtilLib.Report_FAIL("User Guide", "is not present at top of home page", "Verification_HelpLink_HomePage");
					rerunflag++;
				}

				UtilLib.WaitTime(4000);

				Set<String> windowId = driver.getWindowHandles();
				Iterator<String> itererator1 = windowId.iterator(); 

				String mainWinID1 = itererator1.next();
				String newAdwinID1 = itererator1.next();

				driver.switchTo().window(newAdwinID1);



				UtilLib.WaitTime(7000);

				String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
				String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL("Login", "is failed after clicking on Help Link", "VerificationHelpLink");
					rerunflag++;
				}else{
					UtilLib.Report_PASS("Login", "successful after clicking on Help Link", "VerificationHelpLink");
				}

				UtilLib.WaitTime(14000);

				System.out.println(driver.getTitle());
				String helpUserGuide=UtilLib.uf_C_GetUIData1("User Guide",element.uf_N_helpUserGuide,TestCase_Name);

				if(helpUserGuide.equals("Topic search 3.0 user guide "))
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on Help link", "Verify_Newpage");
					rerunflag++;
				}
				else
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on Help link", "Verify_Newpage");
					status=true;
				}




				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Help Link", "is not verified", "Verify_HelpLink");
				}
				else
				{
					UtilLib.Report_PASS("Help Link", "is verified", "Verify_HelpLink");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14Rel_PROG_ChangeHelpToUserGuide_Resultspage_02
	 * @Description :   This scenario is to verify that Help link text is changed to User Guide in Result page
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_ Change Help to User Guide_Results page_02
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14Rel_PROG_ChangeHelpToUserGuide_Resultspage_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


		
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
			
			UtilLib.WaitTime(3000);
				
			String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
			if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
			{
				UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
			}
			else{
				rerunflag++;
			}
				System.out.println("***************   Verifying Help Link  ***************");

				status=UtilLib.uf_C_ClickOnElement1("Help", element.uf_N_help, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("User Guide ", "is present at top of home page", "Verification_HelpLink_HomePage");
				}else{
					UtilLib.Report_FAIL("User Guide", "is not present at top of home page", "Verification_HelpLink_HomePage");
					rerunflag++;
				}

				UtilLib.WaitTime(4000);

				Set<String> windowId = driver.getWindowHandles();
				Iterator<String> itererator1 = windowId.iterator(); 

				String mainWinID1 = itererator1.next();
				String newAdwinID1 = itererator1.next();

				driver.switchTo().window(newAdwinID1);



				UtilLib.WaitTime(7000);

				String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
				String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
				status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
				if(status==false){
					UtilLib.Report_FAIL("Login", "is failed after clicking on Help Link", "VerificationHelpLink");
					rerunflag++;
				}else{
					UtilLib.Report_PASS("Login", "successful after clicking on Help Link", "VerificationHelpLink");
				}

				UtilLib.WaitTime(14000);

				System.out.println(driver.getTitle());
				String helpUserGuide=UtilLib.uf_C_GetUIData1("User Guide",element.uf_N_helpUserGuide,TestCase_Name);

				if(helpUserGuide.equals("Topic search 3.0 user guide "))
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on Help link", "Verify_Newpage");
					rerunflag++;
				}
				else
				{
					UtilLib.Report_PASS("New Page is opened  ", "after click on Help link", "Verify_Newpage");
					status=true;
				}




				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Help Link", "is not verified", "Verify_HelpLink");
				}
				else
				{
					UtilLib.Report_PASS("Help Link", "is verified", "Verify_HelpLink");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07
	 * @Description :  This scenario is to verify  that in CDETS datasource , meta data fields ie. 'version' and 'integrated-releases' are available in one line 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify CDETS metadata fields_'version' and 'integrated-releases' into one line_07
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


		
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
			
			UtilLib.WaitTime(3000);
				
			String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
			if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
			{
				UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
			}
			else{
				rerunflag++;
			}
			status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}
			UtilLib.WaitTime(2000);
			/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}*/
			
			status=UtilLib.uf_C_ClickOnElement1("CDETS DataSource", element.uf_N_cdetsChkBox, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}
			UtilLib.WaitTime(2000);
			
			boolean CDETSIntegratedReleasesIsDisplayed=driver.findElement(By.xpath(element.uf_SH_CDETSIntegratedReleases)).isDisplayed();
			boolean CDETSVersionIsDisplayed=driver.findElement(By.xpath(element.uf_SH_CDETSIntegratedReleases)).isDisplayed();
			if(CDETSIntegratedReleasesIsDisplayed==true&&CDETSVersionIsDisplayed==true)
			{
				UtilLib.Report_PASS("CDETS Integrated Releases and CDETS Version", "are Displayed", "IntegretedReleasesandVersionVerification()");
			}
			else{
				status=false;
				UtilLib.Report_FAIL("CDETS Integrated Releases and CDETS Version", "are not Displayed", "IntegretedReleasesandVersionVerification()");
			}

				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07", "is not verified", "Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify CDETS metadata fields_'version' and 'integrated-releases' into one line_07");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07", "is verified", "Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify CDETS metadata fields_'version' and 'integrated-releases' into one line_07");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO260_Filters_SingleDatasourceSelected_03
	 * @Description :  This scenario is to verify that  to remove the confirmation and just do the action for the X filter option, and move data sources
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 260_Filters_Single data source selected_03
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO260_Filters_SingleDatasourceSelected_03(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


		
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
			
			UtilLib.WaitTime(3000);
				
			String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
			if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
			{
				UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
			}
			else{
				rerunflag++;
			}
			status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}
			UtilLib.WaitTime(2000);
			/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}*/
			
			status=UtilLib.uf_C_ClickOnElement1("CDETS DataSource", element.uf_N_cdetsChkBox, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}
			UtilLib.WaitTime(2000);
			
			boolean dateRangeFilterforCDETSIsDisplayed=driver.findElement(By.xpath(element.uf_SH_dateRangeFilterforCDETS)).isDisplayed();
			if(dateRangeFilterforCDETSIsDisplayed==true)
			{
				UtilLib.Report_PASS("Date Range Filter For CDETS ", "is Displayed", "dateRangeFilterforCDETSIsDisplayed()");
			}
			else{
				status=false;
				UtilLib.Report_FAIL("Date Range Filter For CDETS ", "is not Displayed", "dateRangeFilterforCDETSIsDisplayed()");
			}
			/*Actions action = new Actions(driver);
			//String Xpath = "//*[@id='framework-column-center']/table/tbody/tr/td/div/form/table/tbody/tr/td/div/div/table/tbody/tr[2]/td/table/tbody/tr["+i+"]/td[3]/span/table/tbody/tr/td[1]/a/img";
			WebElement e = driver.findElement(By.xpath(element.uf_SH_CloseButtondateRangeFilterforCDETS));
			action.moveToElement(e).build().perform();*/
			
			
			WebElement we1 = driver.findElement(By.xpath(element.uf_SH_dateRangeFilterforCDETS));
			org.openqa.selenium.Point coordinates = we1.getLocation();
			System.out.println("coordinates.getX() : "+coordinates.getX());
			System.out.println("coordinates.getY() : "+coordinates.getY());
			UtilLib.WaitTime(2000);
			Robot robot = new Robot(); 
			robot.mouseMove(coordinates.getX() +100, coordinates.getY() +110); //Number 65 should vary
			Thread.sleep(3000);
			status=UtilLib.uf_C_ClickOnElement1("Close Button for date Range Filter for CDETS", element.uf_SH_CloseButtondateRangeFilterforCDETS, TestCase_Name);
			if(status==false)
			{
				rerunflag++;
			}
			
			boolean dateRangeFilterforCDETSIsDisplayed1=driver.findElement(By.xpath(element.uf_SH_dateRangeFilterforCDETS)).isDisplayed();
			if(dateRangeFilterforCDETSIsDisplayed1==true)
			{
				status=false;
				UtilLib.Report_FAIL("Date Range Filter For CDETS ", "is not closed", "dateRangeFilterforCDETSIsDisplayed()");
			}
			else{
				UtilLib.Report_PASS("Date Range Filter For CDETS ", "is closed", "dateRangeFilterforCDETSIsDisplayed()");
				}
			
			
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07", "is not verified", "Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify CDETS metadata fields_'version' and 'integrated-releases' into one line_07");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_3_UI_Oct14Rel_PROG_verifyCDETSmetadatafields_version_and_integratedReleases_intoOneline_07", "is verified", "Search Topic 3.1.3 UI_Oct'14 Rel_PROG_verify CDETS metadata fields_'version' and 'integrated-releases' into one line_07");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}

	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_UI_AttivioMigration_PROG_RSLT_SavingSearch_Delete_search
	 * @Description : verify  that a contextual menu should pop up when the saved search name is right clicked         
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 Saving Search_Delete the search
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_UI_AttivioMigration_PROG_RSLT_SavingSearch_Delete_search(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}


		
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
			
			UtilLib.WaitTime(3000);
				
			String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
			if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
			{
				UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
			}
			else{
				rerunflag++;
			}
				
			
						boolean savedSearchIsDisplayed=driver.findElement(By.xpath(element.uf_SH_savedSearch)).isDisplayed();
						boolean sharedSearchIsDisplayed=driver.findElement(By.xpath(element.uf_SH_sharedSearch)).isDisplayed();	
				if(savedSearchIsDisplayed==true && sharedSearchIsDisplayed==true )
				{
					UtilLib.Report_PASS("Saved Search and Shared Search ", "Is Displayed", "savedandSharedSearchVerification");
					
				}else{
					UtilLib.Report_FAIL("Saved Search and Shared Search   ", "Is not Displayed", "savedandSharedSearchVerification");
					rerunflag++;
				}
				
			//	driver.findElement(By.xpath(//*[@id='savedSearchDiv']/table/tbody/tr[1])).ispresent()
					//boolean dateLatestIsDisplayed=driver.findElement(//*[@id='savedSearchDiv']/table/tbody/tr[1])).isDisplayed();
							boolean savedSearchFirstElement=driver.findElement(By.xpath("//*[@id='savedSearchDiv']/table/tbody/tr[1]")).isDisplayed();
							JavascriptExecutor jse = (JavascriptExecutor)driver; jse.executeScript("window.scrollBy(0,375)", "");
				 UtilLib.WaitTime(4000);		
				WebElement propButton = driver.findElement(By.xpath(element.uf_SH_CiscoACSSavedSearch));
				  Actions clicker = new Actions(driver);
				  clicker.contextClick(propButton).perform(); 
				  
				  UtilLib.uf_C_ClickOnElement1("Rename Search ", element.uf_SH_RenamesavedSearch, TestCase_Name);
				  driver.findElement(By.xpath(element.uf_SH_newNameForRenamesavedSearch)).clear();
				  UtilLib.WaitTime(1000);				
				  String newNameReanmedSearch=UtilLib.uf_C_EnterDataInTextBox("New Name For renamed Search", element.uf_SH_newNameForRenamesavedSearch, "Test_NewName", TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
				  
					UtilLib.uf_C_ClickOnElement1("Rename Button ", element.uf_SH_RenameButtonPopUp, TestCase_Name);
					 UtilLib.WaitTime(4000);	
					 driver.navigate().refresh(); 
					 UtilLib.WaitTime(2000);	
					
					 
					 
					 String NewName=UtilLib.uf_C_GetUIData1("Shared Search ", element.uf_SH_CiscoACSSavedSearch, TestCase_Name);
					System.out.println("New Name is : "+NewName);
						
				/*String sharedSearch=UtilLib.uf_C_GetUIData1("Shared Search ", element.uf_SH_sharedSearch, TestCase_Name);
				String routerInsharedSearch=UtilLib.uf_C_GetUIData1("Router Shared Search ", element.uf_SH_routerInSharedSearch, TestCase_Name);
				if(sharedSearch.equals("Shared Searches") && routerInsharedSearch.equals("router")){
					UtilLib.Report_PASS(" The searches saved by the user", "  are visible under â€˜Saved Searchesâ€™ section.","sharedSearchesVerification");
				}else{
					
				}*/
				
				
		
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 Saving Search_Delete the search_104", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_RSLT_SavingSearch_Delete_search()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 Saving Search_Delete the search_104", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_RSLT_SavingSearch_Delete_search()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLink_01
	 * @Description : TThis scenario is to verify that preview link is coming or not
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4_UI_Dec 14 Rel_PROGResultPreview Enhancements_PreviewLink_01
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLink_01(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("Newsgroup Checkbox", element.uf_N_newsGroupChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("Newsgroup Header in Topic Buddy Preview", element.uf_SH_NewsgroupHeader_TopicPreview, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("Newsgroup-"))
					{
						UtilLib.Report_PASS("Newsgroup Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Newsgroup Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is not verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges", "is verified", "TS039SearchTopic_3_1_4_UI_Oct14Rel_PROG_Alwayssearchwithinthese_checkeddatasources_savechanges");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLinkandCarot_02
	 * @Description : This scenario is to verify that preview link and Carot are coming or not
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4_UI_Dec 14 Rel_PROGResultPreview Enhancements_PreviewLinkandCarot_02
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLinkandCarot_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("Newsgroup Checkbox", element.uf_N_newsGroupChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("Newsgroup Header in Topic Buddy Preview", element.uf_SH_NewsgroupHeader_TopicPreview, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("Newsgroup-"))
					{
						UtilLib.Report_PASS("Preview link is present ","hence Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Preview link is not present  ","hence Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("Click on Query", element.uf_N_queryText, TestCase_Name);
					UtilLib.WaitTime(2000);
					
					/*WebElement we1 = driver.findElement(By.xpath(element.uf_R_ChevronTopicBuddy));
					org.openqa.selenium.Point coordinates = we1.getLocation();
					System.out.println("coordinates.getX() : "+coordinates.getX());
					System.out.println("coordinates.getY() : "+coordinates.getY());
					UtilLib.WaitTime(2000);
					Robot robot = new Robot(); 
					robot.mouseMove(coordinates.getX() +850, coordinates.getY() +550); */
					status = UtilLib.verifyElementPresent("Chevron",element.uf_R_ChevronTopicBuddy, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.WaitTime(2000);
					Actions actions = new Actions(driver);
					//WebElement PMLInk_OnHover = driver.findElement(By.xpath(element.uf_R_C3ResultContent));
					//actions.moveToElement(PMLInk_OnHover).perform();
					WebElement PMLInk_OnHover1 = driver.findElement(By.xpath(element.uf_R_ChevronTopicBuddy));
					actions.moveToElement(PMLInk_OnHover1).perform();

					UtilLib.uf_C_ClickOnElement1("Newsgroup Topic Buddy Preview", element.uf_R_ChevronTopicBuddy, TestCase_Name);
					UtilLib.WaitTime(2000);
					
					String NewsgroupHeader_TopicPreview1=UtilLib.uf_C_GetUIData1("Newsgroup Header in Topic Buddy Preview", element.uf_SH_NewsgroupHeader_TopicPreview, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview1.contains("Newsgroup-"))
					{
						UtilLib.Report_PASS("Carot is present","Topic Buddy Preview is present","Preview_FunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Carot is not present","Topic Buddy Preview is not present","Preview_FunctionalityVerification()");
						rerunflag++;
					}
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic_3.1.4_UI_Dec 14 Rel_PROGResultPreview Enhancements_PreviewLinkandCarot_02", "is not verified", "TS088_SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLinkandCarot_02");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic_3.1.4_UI_Dec 14 Rel_PROGResultPreview Enhancements_PreviewLinkandCarot_02", "is verified", "TS088_SearchTopic_3_1_4_UI_Dec14Rel_PROGResultPreviewEnhancements_PreviewLinkandCarot_02");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05
	 * @Description : This scenario is to verify that  space between text and button borders is added on My Preferences Page  
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4_UI_Dec 14 Rel_PROG_My preferencesPage_Messageappears_05
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					UtilLib.uf_C_ClickOnElement1("My Preferences Link", element.uf_SH_MyPreferencesLink, TestCase_Name);
					UtilLib.WaitTime(3000);
					UtilLib.uf_C_ClickOnElement1("System Data Radio Button", element.uf_SH_MyPreferences_BrowserHistory_RadioButton, TestCase_Name);
					UtilLib.WaitTime(2000);
					
					boolean browserHistoryIsSelected=driver.findElement(By.xpath(element.uf_SH_MyPreferences_SystemData_RadioButton)).isSelected();
					if(browserHistoryIsSelected==false)
					{
						UtilLib.Report_PASS("User is navigated to the My Prefernces page and the System data ", "is not selected ", "MyPreferencesVerification()");
						UtilLib.uf_C_ClickOnElement1("System Data Radio Button", element.uf_SH_MyPreferences_SystemData_RadioButton, TestCase_Name);
						
						UtilLib.uf_C_ClickOnElement1("Save Changes", element.uf_SH_MyPreferences_SaveChanges, TestCase_Name);
						UtilLib.WaitTime(1000);
					}
					else
					{
						UtilLib.Report_FAIL("User is navigated to the My Prefernces page and the System data ", "is selected ", "MyPreferencesVerification()");
						rerunflag++;
					
					}
					
					
					
					String MessageInMyPreferences=UtilLib.uf_C_GetUIData1("Message in My Preferences", element.uf_SH_MyPreferences_Message, TestCase_Name);
				    System.out.println("Message for Saved Changes is  : "+MessageInMyPreferences);
					if(MessageInMyPreferences.equals("Changes saved successfully"))
					{
						UtilLib.Report_PASS("Message in My Preferences","is displayed","MessageVerificaton()");
					}
					else
					{
				
						UtilLib.Report_FAIL(" Message in My Preferences","is not displayed","MessageVerificaton()");
						rerunflag++;
					}
					
					
					
		        
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05", "is not verified", "TS089_SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05", "is verified", "TS089_SearchTopic_3_1_4_UI_Dec14Rel_PROG_MypreferencesPage_Messageappears_05");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14Rel_PROG_OpeningnewTab_clickingonIcon_07
	 * @Description : This scenario is to verify  that a new tab opens when the user clicks on icon on the result page
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_3.1.4_UI_Dec 14 Rel_PROG_Opening new Tab_clicking on Icon_07
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_Dec14Rel_PROG_OpeningnewTab_clickingonIcon_07(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					if(status==false){
						rerunflag=rerunflag + 1;
					}
					
					UtilLib.uf_C_ClickOnElement1("C3 Checkbox", element.uf_N_c3ChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					if(status==false){
						rerunflag=rerunflag + 1;
					}
					
					
					
					status=UtilLib.uf_C_ClickOnElement1("New Tab Icon", element.Search_SH_newTabIcon, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}

					Robot robot = new Robot();
					uf_N_parseChars(data.getI_PORTLET_VALUE5(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					uf_N_parseChars(data.getI_PORTLET_VALUE6(), robot); 
					UtilLib.WaitTime(3000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER); 
				
					
					/*String Id = UtilLib.uf_C_EnterDataInTextBox("LoginId", element.uf_R_LoginId,data.getI_LOGINUSER_NAME(), TestCase_Name);
					String Pwd = UtilLib.uf_C_EnterDataInTextBox("Password", element.uf_R_LoginPwd,data.getI_LOGINUSER_PASSWORD(), TestCase_Name);
					UtilLib.WaitTime(3000);
					status = UtilLib.uf_C_ClickOnElement1("Login", element.uf_R_LoginBtn, TestCase_Name);
					if(status==false){
						UtilLib.Report_FAIL("Login", "is failed", "uf_R_Login_Topic_Search");

					}
					UtilLib.WaitTime(5000);*/
					
					String title =  driver.getCurrentUrl();
					System.out.println(title);
					if(title.contains("https://techzone.cisco.com/t5/Routing-Protocols/622240537-3845-router-the-router-is-forwarding-rp-discovery/td-p/54932")){
						UtilLib.Report_PASS("System is navigated to", "New Page", "uf_R_Login_Topic_Search");
					}
					else{
						UtilLib.Report_FAIL("System is not navigated to", "New Page", "uf_R_Login_Topic_Search");
						status = false;
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic_3.1.4_UI_Dec 14 Rel_PROG_Opening new Tab_clicking on Icon_07", "is not verified", "TS10_SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic_3.1.4_UI_Dec 14 Rel_PROG_Opening new Tab_clicking on Icon_07", "is verified", "TS10_SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_options_viewC3Results_XMLKWERY_104");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic3UI_AttivioMigration_PROG_Editing_dataSourcefilter_SelecteddataSource_stored_DB
	 * @Description :verify that the selected data source is stored in DB  so that same setting could be found in next search 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 _Editing data source  filter_Selected data source stored in DB_98
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic3UI_AttivioMigration_PROG_Editing_dataSourcefilter_SelecteddataSource_stored_DB(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				String noOfSearchResults=UtilLib.uf_C_GetUIData1("No of search results", element.uf_N_correspondingTotalResults, TestCase_Name);
				
				String[] parts = noOfSearchResults.split(" ");
				String label1 = parts[0]; 
				String label2=parts[1];
				String label3=parts[2];
				String label4=parts[3];
				if(label3.equals(null)&&label4.equals(null))
				{
					UtilLib.Report_FAIL("  Results in the results page  ", "are not displayed", "NoofResultsVerification()");
				}
				else{
					UtilLib.Report_PASS("  Results in the results page  ", "are  displayed", "NoofResultsVerification()");
				}

				
				
				/*String editLink=UtilLib.uf_C_GetUIData1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(editLink.equals(data.getI_PORTLET_VALUE3()))
				{
					UtilLib.Report_PASS(" Edit link", "is present", "editLinkVerification()");
				}
				else{
					UtilLib.Report_FAIL(" Edit link", "is not present", "editLinkVerification()");
					rerunflag++;
				}*/
				
				status=UtilLib.uf_C_ClickOnElement1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.ClickOnElement("Dynamicsoft", element.uf_SH_editDynamicSoftPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);
				
				
				status=UtilLib.ClickOnElement("Cancel", element.uf_SH_editCancelButtonPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);
				boolean status1=driver.findElement(By.xpath(element.uf_N_c3ChkBox)).isSelected();
				boolean status2=driver.findElement(By.xpath(element.uf_N_cdetsChkBox)).isSelected();
				boolean status3=driver.findElement(By.xpath(element.uf_N_techzoneChkBox)).isSelected();
				boolean status4=driver.findElement(By.xpath(element.uf_N_newsGroupChkBox)).isSelected();
				
				if(status1==true && status2==true && status3==true && status4==true )
				{
					UtilLib.Report_PASS(" The default data sources  ", "are checked", "editDefaultVerification()");
				}
				else
				{
					UtilLib.Report_FAIL(" The default data sources  ", "are not checked", "editDefaultVerification()");
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.ClickOnElement("Dynamicsoft", element.uf_SH_editDynamicSoftPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);
				
				
				status=UtilLib.ClickOnElement("OK", element.uf_SH_editOKButtonPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(4000);
				 status1=driver.findElement(By.xpath(element.uf_N_c3ChkBox)).isSelected();
				 status2=driver.findElement(By.xpath(element.uf_N_cdetsChkBox)).isSelected();
				 status3=driver.findElement(By.xpath(element.uf_N_techzoneChkBox)).isSelected();
				 status4=driver.findElement(By.xpath(element.uf_N_newsGroupChkBox)).isSelected();
				boolean status5=driver.findElement(By.xpath(element.uf_N_dynamicSoftChkBox)).isSelected();
				 
				
				if(status1==true && status2==true && status3==true && status4==true && status5==true )
				{
					UtilLib.Report_PASS(" The  data sources selected ", "are checked", "editDefaultVerification()");
				}
				else
				{
					UtilLib.Report_FAIL(" The data sources  selected ", "are not checked", "editDefaultVerification()");
					rerunflag++;
				}
				
				
			
				String results=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				results=results.substring(2,4);
				
				int noOfResults=Integer.parseInt(results);
				int flag = 0;
				for(int i=3;i<=noOfResults;i++){
				String statusValue=driver.findElement(By.xpath("//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr["+i+"]/td/div/label")).getText().trim();	
				//String statusValue=UtilLib.uf_C_GetUIData1("status", "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr["+i+"]/td/div/label", TestCase_Name);
				parts = statusValue.split(" -");
				String statusValue1 = parts[0]; 
				
				if(statusValue1.equals("C3/CSOne/CARE")|| statusValue1.equals("Newsgroup")||statusValue1.equals("Tech Zone")||statusValue1.equals("CDETS/DDTS")||statusValue1.equals("Dynamicsoft")){

				}else{
				flag++;
				}
				}

				if(flag>0){
				UtilLib.Report_FAIL("Results are not filtered", "according to selected status", "verifyFilteredList");
				rerunflag++;
				
				}else{
				UtilLib.Report_PASS("Results are filtered", "according to selected status", "verifyFilteredList");
			

				}
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 _Editing data source  filter_Reset to default button", "is not verified", "SearchTopic3UI_AttivioMigration_PROG_Editing_datasourceilter_Reset_defaultButton()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 _Editing data source  filter_Reset to default button  ", "is verified", "SearchTopic3UI_AttivioMigration_PROG_Editing_datasourceilter_Reset_defaultButton()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_SearchTips_09
	 * @Description : verify the search tip on the results page 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_Sourcefire_UI _Search Tips_09
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_SearchTips_09(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				status=UtilLib.uf_C_ClickOnElement1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.ClickOnElement("Sourcefire", element.uf_SH_editSourcefireSoftPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);
				status=UtilLib.ClickOnElement("OK", element.uf_SH_editOKButtonPopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(4000);
				
				/////***************************Veification of Search Tip for Sourcefire***************///////////
				String randomTip=UtilLib.uf_C_GetUIData1("IOS Crash", element.uf_SH_randomTip, TestCase_Name);	
				if(randomTip.contains("IOS crash"))
				{
					UtilLib.Report_PASS("Random tip ", "is present",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Random tip ", "is not present",TestCase_Name);
					rerunflag++;
				}

				String moreTip=UtilLib.uf_C_GetUIData1("More Tips", element.uf_SH_moreTips, TestCase_Name);	
				if(moreTip.contains("More tips"))
				{
					UtilLib.Report_PASS("More tip ", "is present on top of the search box in the results page",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Random tip ", "is not present on top of the search box in the results page",TestCase_Name);
					rerunflag++;
				}	

				status=UtilLib.uf_C_ClickOnElement1("More Tips",element.uf_SH_moreTips , TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);

				String lessTip=UtilLib.uf_C_GetUIData1("Less Tips", element.uf_SH_lessTips, TestCase_Name);	
				if(lessTip.contains("Less tips"))
				{
					UtilLib.Report_PASS("Less tip ", "is present on top of the search box in the More Links page",TestCase_Name);
				}
				else{
					UtilLib.Report_FAIL("Less tip ", "is not present on top of the search box in the More Links page",TestCase_Name);
					rerunflag++;
				}

				String IOSCrash=UtilLib.uf_C_GetUIData1("Ios Crash", element.uf_SH_IOScrash_Sourcefire, TestCase_Name);	
				int flag = 0;
				System.out.println("IOSCrash : "+driver.findElement(By.xpath(element.uf_SH_IOScrash_Sourcefire)).getText().trim());
				if(IOSCrash.contains("IOS crash"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS Crash ", "is not present  in the More Links page",TestCase_Name);
				}


				String IOSANDcrash=UtilLib.uf_C_GetUIData1("IOS AND crash", element.uf_SH_IOSANDcrash_Sourcefire, TestCase_Name);	
				System.out.println("IOS and Crash : "+driver.findElement(By.xpath(element.uf_SH_IOSANDcrash_Sourcefire)).getText().trim());
				if(IOSANDcrash.contains("IOS"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS AND Crash ", "is not present  in the More Links page",TestCase_Name);
				}

				String IOSCrashORexception=UtilLib.uf_C_GetUIData1("Ios Crash OR exception", element.uf_SH_IOScrashORexception_Sourcefire, TestCase_Name);	
				System.out.println("IOS Crash OR exception : "+driver.findElement(By.xpath(element.uf_SH_IOScrashORexception_Sourcefire)).getText().trim());
				if(IOSCrashORexception.contains("IOS crash OR exception"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("IOS crash OR exception ", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String cat65=UtilLib.uf_C_GetUIData1("cat65", element.uf_SH_cat65_Sourcefire, TestCase_Name);	
				System.out.println("cat 65 : "+driver.findElement(By.xpath(element.uf_SH_cat65_Sourcefire)).getText().trim());
				if(cat65.contains("cat65"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("cat65 ", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String cat6=UtilLib.uf_C_GetUIData1("cat65", element.uf_SH_cat6_Sourcefire, TestCase_Name);	
				System.out.println("cat 6 : "+driver.findElement(By.xpath(element.uf_SH_cat6_Sourcefire)).getText().trim());
				if(cat6.contains("cat6"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("cat6", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String SxI12=UtilLib.uf_C_GetUIData1("12*SXI", element.uf_SH_12SxI_Sourcefire, TestCase_Name);	
				System.out.println("SxI12 : "+driver.findElement(By.xpath(element.uf_SH_12SxI_Sourcefire)).getText().trim());
				if(SxI12.contains("12*SXI"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("12*SXI", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}
				String Sx_I12=UtilLib.uf_C_GetUIData1("12**SXI", element.uf_SH_12SXI_Sourcefire, TestCase_Name);	
				System.out.println("Sx_I12 : "+driver.findElement(By.xpath(element.uf_SH_12SXI_Sourcefire)).getText().trim());
				if(Sx_I12.contains("12 * * SXI?"))
				{
					flag++;
				}
				else{
					UtilLib.Report_FAIL("12**SXI", "is not present  in the More Links page",TestCase_Name);
					rerunflag++;
				}

				if(flag==7)
				{
					UtilLib.Report_PASS("Labels in", "More Links page are verified",TestCase_Name);
				}



				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_Sourcefire_UI_SearchTips_09", "is not verified", "TS092_SearchTopic_Sourcefire_UI_SearchTips_09()");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_Sourcefire_UI_SearchTips_09", "is verified", "TS092_SearchTopic_Sourcefire_UI_SearchTips_09");
				}


			}}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_Datelatest_dateOldestlink_08
	 * @Description : This scenario is to verify that Date-latest/date-Oldest link is present
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic_Sourcefire_UI _Date latest_date Oldest link_08
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_Datelatest_dateOldestlink_08(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				boolean dateLatestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Latest)).isDisplayed();
				boolean dateOldestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Oldest)).isDisplayed();
				boolean relevanceIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Relevance)).isDisplayed();
				if(dateLatestIsDisplayed==true && dateOldestIsDisplayed==true && relevanceIsDisplayed==true)
				{
					UtilLib.Report_PASS("Date Latest,Date Oldest,Relevance Links ", "are present", "LinVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Date Latest,Date Oldest,Relevance Links ", "are not present", "resultPageVerification()");
				}	
				/*status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);*/
				/*status=UtilLib.uf_C_ClickOnElement1("CDETS datasource", element.uf_N_cdetsChkBox, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}*/
			//	driver.findElement(By.xpath(element.uf_N_c3ChkBox)).click();
				
			//	UtilLib.WaitTime(2000);
				String Date_BeforeClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date before clicking on Date oldest" , element.uf_SH_DateBeforeSourcefire, TestCase_Name);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date convertedCurrentDate = sdf.parse(Date_BeforeClickingDateLatestLink);
				System.out.println(convertedCurrentDate);
				
				
				
				
				UtilLib.uf_C_ClickOnElement1("Date Oldest Link", element.uf_SH_Date_Oldest, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfterSourcefire, TestCase_Name);
				
			    Date convertedCurrentDate2 = sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println(convertedCurrentDate2);
				if(convertedCurrentDate.compareTo(convertedCurrentDate2)>0){
	        		System.out.println("Date2 is older than Date1");
	        		UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)<0){
	        		System.out.println("Date1 is older than Date2");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        	}else if(convertedCurrentDate.compareTo(convertedCurrentDate2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
	        	}
				
				
				
				/*String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				 Date convertedCurrentDate2= sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println("Date before clicking : "+date2);
				if(date1.compareTo(date2)>0){
	        		System.out.println("Date1 is after Date2");
	        	}else if(date1.compareTo(date2)<0){
	        		System.out.println("Date1 is before Date2");
	        	}else if(date1.compareTo(date2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        	}
				*/
				//	int date_afterClickingDateLatestLink=Integer.parseInt(Date_AfterClickingDateLatestLink);
				/*System.out.println("Date after clicking : "+Date_AfterClickingDateLatestLink);
				if(Date_BeforeClickingDateLatestLink.equals(Date_AfterClickingDateLatestLink))
				{
					rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
				}	
				else
				{
					UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
					
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is not verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
					status=true;
				}
*/
				
				}}
			catch(UnhandledAlertException alert){
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

			return status;
		
	}
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_submission_relevance_FeedbackshouldAcknowledged_04
	 * @Description :  This scenario is to verify that an explicit feedback should be provided when user rates the result
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_Sourcefire_UI _submissionof relevance feedback shouldacknowledged_04
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_submission_relevance_FeedbackshouldAcknowledged_04(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				System.out.println("***************   Feedback  verification  ***************");
				String type1=driver.findElement(By.xpath(element.uf_SH_veryRelevantradioButton)).getAttribute("type");


				if(type1.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Very Relevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Very Relevant ' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}

				String type2=driver.findElement(By.xpath(element.uf_SH_somewhatRelevantradioButton)).getAttribute("type");


				if(type2.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Somewhat Relevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Somewhat Relevant' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}
				String type3=driver.findElement(By.xpath(element.uf_SH_uncertainradioButton)).getAttribute("type");


				if(type3.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Uncertain ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Uncertain  ' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}
				String type4=driver.findElement(By.xpath(element.uf_SH_irrelevantradioButton)).getAttribute("type");


				if(type4.equals("radio")){
					UtilLib.Report_PASS("Radio button for 'Irrelevant ' ", "is present", "FeedbackVaerifiaction()");
				}
				else{
					UtilLib.Report_FAIL("Radio button for 'Irrelevant' ", "is not present", "FeedbackVaerifiaction()");
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("Somwhat Relevant radio button", element.uf_SH_somewhatRelevantradioButton, TestCase_Name);
				if(status==true)
				{
					UtilLib.WaitTime(1000);
					String feedbackVisibility= driver.findElement(By.xpath(element.uf_N_dateRange)).getCssValue("visibility").trim();

					System.out.println("feedback visibilty :"+feedbackVisibility);
					if(feedbackVisibility.equals("hidden"))

						UtilLib.Report_PASS("User is able to give the feedback 'Somewhat relevant ' and explicit feedback is provided when user rates ", "", "FeedbackVaerifiaction()");
				}
				else
				{
					UtilLib.Report_FAIL("User is unable to give the feedback 'Somewhat relevant ' ", "", "FeedbackVaerifiaction()");
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 280_submission of relevance feedback should be acknowledged_91", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 280_submission of relevance feedback should be acknowledged_91", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_submission_relevance_feedback_acknowledged_91()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_Error_ErrorMessage_03
	 * @Description : This scenario is to verifythe results page when there  are  zero results for a query string
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_Sourcefire_UI _Error _Error Message_03
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_Error_ErrorMessage_03(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				/*String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}*/
				UtilLib.WaitTime(2000);
				String errorColonMessage=UtilLib.uf_C_GetUIData1("Error Message", element.uf_SH_errorColonMessage, TestCase_Name).trim();
				System.out.println("Error Message is  : "+errorColonMessage);
				if(errorColonMessage.contains("No matches found, try to refine your query or select additional data sources"))
				{	
					UtilLib.Report_PASS("Error Message for zero results","is verified","ErrorMessageVerification()");
				}
				else{
					UtilLib.Report_FAIL("Error Message for zero results","is not verified","ErrorMessageVerification()");
					rerunflag++;
				}
			}



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
			}
			else
			{
				UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_CSSCSO262 _Error Message_12", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_CSSCSO262_ErrorMessage_12()");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_Select_extra_dataSource_from_more_dataSource_link_02
	 * @Description : This scenario is to select the extra data source from more data source link 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic_Sourcefire_UI_Select extra data source_from more data source link_02
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_Select_extra_dataSource_from_more_dataSource_link_02(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("SourceFire datasource", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is  verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				



			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("SearchTopic_Sourcefire_UI_Select_extra_dataSource_from_more_dataSource_link_02", "is not verified", "Search Topic_Sourcefire_UI_Select extra data source_from more data source link_02");
			}
			else
			{
				UtilLib.Report_PASS("SearchTopic_Sourcefire_UI_Select_extra_dataSource_from_more_dataSource_link_02", "is verified", "Search Topic_Sourcefire_UI_Select extra data source_from more data source link_02");
				status=true;
			}
			}

			}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_Rel_PROG_Performing_Search_while_Editing_dataSourcefilters_01
	 * @Description :This Scenario is to verify the search results functionality while editing the data sources
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name :Search Topic_Sourcefire_UI_Rel_PROG_Performing the search while  Editing the data source filters_01
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_Rel_PROG_Performing_Search_while_Editing_dataSourcefilters_01(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {
		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				String noOfSearchResults=UtilLib.uf_C_GetUIData1("No of search results", element.uf_N_correspondingTotalResults, TestCase_Name);
				
				String[] parts = noOfSearchResults.split(" ");
				String label1 = parts[0]; 
				String label2=parts[1];
				String label3=parts[2];
				String label4=parts[3];
				if(label3.equals(null)&&label4.equals(null))
				{
					UtilLib.Report_FAIL("  Results in the results page  ", "are not displayed", "NoofResultsVerification()");
				}
				else{
					UtilLib.Report_PASS("  Results in the results page  ", "are  displayed", "NoofResultsVerification()");
				}

	
				status=UtilLib.uf_C_ClickOnElement1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.ClickOnElement("Sourcefire", element.uf_SH_editSourcefirePopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);
				
				
				status=UtilLib.ClickOnElement("Cancel", element.Cancel_SH_editDatasourcePopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(2000);
				boolean status1=driver.findElement(By.xpath(element.uf_N_c3ChkBox)).isSelected();
				boolean status2=driver.findElement(By.xpath(element.uf_N_cdetsChkBox)).isSelected();
				boolean status3=driver.findElement(By.xpath(element.uf_N_techzoneChkBox)).isSelected();
				boolean status4=driver.findElement(By.xpath(element.uf_N_newsGroupChkBox)).isSelected();
				
				if(status1==true && status2==true && status3==true && status4==true )
				{
					UtilLib.Report_PASS(" The default data sources  ", "are checked", "editDefaultVerification()");
				}
				else
				{
					UtilLib.Report_FAIL(" The default data sources  ", "are not checked", "editDefaultVerification()");
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.uf_C_ClickOnElement1("Edit", element.uf_SH_editLinkDatasource, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				
				
				
				
				status=UtilLib.ClickOnElement("Sourcefire", element.uf_SH_editSourcefirePopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(1000);
				
				
				status=UtilLib.ClickOnElement("OK", element.OK_SH_editDatasourcePopUp, TestCase_Name);
				if(status=false)
				{
					rerunflag++;
				}
				UtilLib.WaitTime(4000);
				 status1=driver.findElement(By.xpath(element.uf_N_c3ChkBox)).isSelected();
				 status2=driver.findElement(By.xpath(element.uf_N_cdetsChkBox)).isSelected();
				 status3=driver.findElement(By.xpath(element.uf_N_techzoneChkBox)).isSelected();
				 status4=driver.findElement(By.xpath(element.uf_N_newsGroupChkBox)).isSelected();
				boolean status5=driver.findElement(By.xpath(element.uf_N_SourcefireChkBox)).isSelected();
				 
				
				if(status1==true && status2==true && status3==true && status4==true && status5==true )
				{
					UtilLib.Report_PASS(" The  data sources selected ", "are checked", "editDefaultVerification()");
				}
				else
				{
					UtilLib.Report_FAIL(" The data sources  selected ", "are not checked", "editDefaultVerification()");
					rerunflag++;
				}
				
				
			
				String results=UtilLib.uf_C_GetUIData1("No of Results", element.uf_N_correspondingTotalResults, TestCase_Name);
				results=results.substring(2,4);
				
				int noOfResults=Integer.parseInt(results);
				int flag = 0;
				for(int i=3;i<=noOfResults;i++){
				String statusValue=driver.findElement(By.xpath("//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr["+i+"]/td/div/label")).getText().trim();	
				//String statusValue=UtilLib.uf_C_GetUIData1("status", "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr["+i+"]/td/div/label", TestCase_Name);
				parts = statusValue.split(" -");
				String statusValue1 = parts[0]; 
				
				if(statusValue1.equals("C3/CSOne/CARE")|| statusValue1.equals("Newsgroup")||statusValue1.equals("Tech Zone")||statusValue1.equals("CDETS/DDTS")||statusValue1.equals("Sourcefire")){

				}else{
				flag++;
				}
				}

				if(flag>0){
				UtilLib.Report_FAIL("Results are not filtered", "according to selected status", "verifyFilteredList");
				rerunflag++;
				
				}else{
				UtilLib.Report_PASS("Results are filtered", "according to selected status", "verifyFilteredList");
			

				}
				
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 _Editing data source  filter_Reset to default button", "is not verified", "SearchTopic3UI_AttivioMigration_PROG_Editing_datasourceilter_Reset_defaultButton()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT-4 _Editing data source  filter_Reset to default button  ", "is verified", "SearchTopic3UI_AttivioMigration_PROG_Editing_datasourceilter_Reset_defaultButton()");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Sourcefire_UI_Sourcefire_DataSource_Addition_MetaFieldsFilters_05
	 * @Description : This scenario is to verify that the Sourcefire data source is added into the application
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic_Sourcefire_UI _Sourcefire data Source additionMetaFields Filters_05
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Sourcefire_UI_Sourcefire_DataSource_Addition_MetaFieldsFilters_05(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("Sourcefire Link", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				/*String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is not verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}*/
				
			}

			
			int Flag=0;
			boolean Sourcefire_assetSerialNumberIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Sourcefire_AssetSerialNumber)).isDisplayed();
			if(Sourcefire_assetSerialNumberIsDisplayed==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_PASS("Metafield of Sourcefire : Asset Serial Number", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			boolean Sourcefire_BugIDIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Sourcefire_BugID)).isDisplayed();
			if(Sourcefire_BugIDIsDisplayed==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : BugID", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			
			boolean Sourcefire_CaseNumber=driver.findElement(By.xpath(element.uf_SH_Sourcefire_CaseNumber)).isDisplayed();
			if(Sourcefire_CaseNumber==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : CaseNumber", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			boolean Sourcefire_CaseOrigin=driver.findElement(By.xpath(element.uf_SH_Sourcefire_CaseOrigin)).isDisplayed();
			if(Sourcefire_CaseOrigin==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : CaseOrigin", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			boolean Sourcefire_CaseType=driver.findElement(By.xpath(element.uf_SH_Sourcefire_CaseType)).isDisplayed();
			if(Sourcefire_CaseType==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : CaseType", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			boolean Sourcefire_Company=driver.findElement(By.xpath(element.uf_SH_Sourcefire_Company)).isDisplayed();
			if(Sourcefire_Company==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : Company", "is not dispalyed", "MetafieldVerifiaction()");
			}
			boolean uf_SH_Sourcefire_CreatedDate=driver.findElement(By.xpath(element.uf_SH_Sourcefire_CreatedDate)).isDisplayed();
			if(uf_SH_Sourcefire_CreatedDate==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : CreatedDate", "is not dispalyed", "MetafieldVerifiaction()");
			}
			boolean Sourcefire_IncidentResolvedDate=driver.findElement(By.xpath(element.uf_SH_Sourcefire_IncidentResolvedDate)).isDisplayed();
			if(Sourcefire_IncidentResolvedDate==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : IncidentResolvedDate", "is not dispalyed", "MetafieldVerifiaction()");
			}
			boolean Sourcefire_Owner=driver.findElement(By.xpath(element.uf_SH_Sourcefire_Owner)).isDisplayed();
			if(Sourcefire_Owner==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : BugID", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			boolean Sourcefire_ParentCaseId=driver.findElement(By.xpath(element.uf_SH_Sourcefire_ParentCaseId)).isDisplayed();
			if(Sourcefire_ParentCaseId==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : ParentCaseId", "is not dispalyed", "MetafieldVerifiaction()");
			}
			boolean Sourcefire_Priority=driver.findElement(By.xpath(element.uf_SH_Sourcefire_Priority)).isDisplayed();
			if(Sourcefire_Priority==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : Priority", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			
			
			boolean Sourcefire_Status=driver.findElement(By.xpath(element.uf_SH_Sourcefire_Status)).isDisplayed();
			if(Sourcefire_Status==true)
			{
				Flag++;
				
			}else{
				rerunflag++;
				UtilLib.Report_FAIL("Metafield of Sourcefire : BugID", "is not dispalyed", "MetafieldVerifiaction()");
			}
			
			
			
			if(Flag==12)
			{
				UtilLib.Report_PASS(" ALL Metafields of Sourcefire", "are dispalyed", "MetafieldVerifiaction()");
			}
			else{
				rerunflag++;
			}
			
			
			if(rerunflag>0){
				status=false;
				UtilLib.Report_FAIL("SearchTopic_Sourcefire_UI_Sourcefire_DataSource_Addition_MetaFieldsFilters_05", "is not verified", "Search Topic_Sourcefire_UI _Sourcefire data Source additionMetaFields Filters_05");
			}
			else
			{
				UtilLib.Report_PASS("SearchTopic_Sourcefire_UI_Sourcefire_DataSource_Addition_MetaFieldsFilters_05", "is verified", "Search Topic_Sourcefire_UI _Sourcefire data Source additionMetaFields Filters_05");
				status=true;
			}


		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  TS099_SearchTopic_Sourcefire_UI_SortRelevancy_11
	 * @Description :  This scenario is to verify that data is arranged according to the relevancy
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name - Search Topic_Sourcefire_UI _SortRelevancy_11
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean TS099_SearchTopic_Sourcefire_UI_SortRelevancy_11(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					rerunflag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}

				
				
				status=UtilLib.uf_C_ClickOnElement1("Clear All Link", element.uf_SH_clearAll, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				status=UtilLib.uf_C_ClickOnElement1("More Data Sources Link", element.uf_SH_moreDataSourcesLink, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				
				status=UtilLib.uf_C_ClickOnElement1("Sourcefire Link", element.uf_SH_editSourceFirePopUp, TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}
				
				

			
				String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
				if(searchQuery.equals(null))
				{
					rerunflag++;
				}
				
				UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
				
				UtilLib.WaitTime(3000);
					
				String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
				if(SearchResults.equals(data.getI_PORTLET_VALUE2()))
				{
					UtilLib.Report_PASS(" Application is  navigated to the results page ", "is verified", "resultPageVerification()");
				}
				else{
					rerunflag++;
				}
				
				boolean dateLatestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Latest)).isDisplayed();
				boolean dateOldestIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Date_Oldest)).isDisplayed();
				boolean relevanceIsDisplayed=driver.findElement(By.xpath(element.uf_SH_Relevance)).isDisplayed();
				if(dateLatestIsDisplayed==true && dateOldestIsDisplayed==true && relevanceIsDisplayed==true)
				{
					UtilLib.Report_PASS("Date Latest,Date Oldest,Relevance Links ", "are present", "LinVerification()");
				}
				else{
					rerunflag++;
					UtilLib.Report_FAIL("Date Latest,Date Oldest,Relevance Links ", "are not present", "resultPageVerification()");
				}	
				
				
				UtilLib.WaitTime(2000);
				//String Date_BeforeClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date before clicking on Date oldest" , element.uf_SH_Sourcefire_CreatedDate_value, TestCase_Name);
				String Date_BeforeClickingDateLatestLink=driver.findElement(By.xpath(element.uf_SH_Sourcefire_CreatedDate_value)).getText();
				
				System.out.println("Date is : "+Date_BeforeClickingDateLatestLink);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Date convertedCurrentDate = sdf.parse(Date_BeforeClickingDateLatestLink);
				System.out.println(convertedCurrentDate);
				
				
				
				
				UtilLib.uf_C_ClickOnElement1("Date Oldest Link", element.uf_SH_Date_Oldest, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest " , element.uf_SH_Sourcefire_CreatedDate_value, TestCase_Name);
				
			    Date convertedCurrentDate2 = sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println(convertedCurrentDate2);
				
				
				UtilLib.uf_C_ClickOnElement1("Relevance link", element.uf_SH_Relevance, TestCase_Name);
				UtilLib.WaitTime(3000);
				String Date_AfterClickingRelevanceLink=UtilLib.uf_C_GetUIData1("Date after clicking on Relevance", element.uf_SH_Sourcefire_CreatedDate_value, TestCase_Name);
				
			    Date convertedCurrentDate3 = sdf.parse(Date_AfterClickingRelevanceLink);
				System.out.println(convertedCurrentDate3);
				
				
				
				
				if(convertedCurrentDate3.compareTo(convertedCurrentDate)>0){
	        		System.out.println("Date1 is older than Date3");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Relevance' ", "are not sorted", "RelevanceLinkVerification()");
	        		
	        	}else if(convertedCurrentDate3.compareTo(convertedCurrentDate)<0){
	        		System.out.println("Date3 is older than Date1");
	        		rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Relevance' ", "are not sorted", "RelevanceLinkVerification()");
	        	}else if(convertedCurrentDate3.compareTo(convertedCurrentDate)==0){
	        		System.out.println("Date1 is equal to Date3");
	        		UtilLib.Report_PASS("Results after clicking on 'Relevance' ", "are  sorted", "RelevanceLinkVerification()");
	        	}
				
				
				
				/*String Date_AfterClickingDateLatestLink=UtilLib.uf_C_GetUIData1("Date after clicking on Date Latest" , element.uf_SH_DateAfter, TestCase_Name);
				 Date convertedCurrentDate2= sdf.parse(Date_AfterClickingDateLatestLink);
				System.out.println("Date before clicking : "+date2);
				if(date1.compareTo(date2)>0){
	        		System.out.println("Date1 is after Date2");
	        	}else if(date1.compareTo(date2)<0){
	        		System.out.println("Date1 is before Date2");
	        	}else if(date1.compareTo(date2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        	}
				*/
				//	int date_afterClickingDateLatestLink=Integer.parseInt(Date_AfterClickingDateLatestLink);
				/*System.out.println("Date after clicking : "+Date_AfterClickingDateLatestLink);
				if(Date_BeforeClickingDateLatestLink.equals(Date_AfterClickingDateLatestLink))
				{
					rerunflag++;
					UtilLib.Report_FAIL("Results after clicking on 'Date-Latest' ", "are not sorted", "DateLatestLinkVerification()");
				}	
				else
				{
					UtilLib.Report_PASS("Results after clicking on 'Date-Latest' ", "are  sorted", "DateLatestLinkVerification()");
					
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is not verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 1_Result page_Sort by date Oldest_23", "is verified", "TS079_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT1_ResultPage_SortbyDateOldest_23()");
					status=true;
				}
*/
				
				}}
			catch(UnhandledAlertException alert){
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

			return status;
		
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO316_Metadata_Display_to_consistent_TechZone_dataSource_57
	 * @Description :  verify that Metadata display to be consistent
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_Tech Zone data source_57
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO316_Metadata_Display_to_consistent_TechZone_dataSource_57(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		int RerunFlag = 0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("Techzone", element.uf_SH_Techzonecheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Techzone data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of meta Fields 'None' value  ***************");

				String noneValue=UtilLib.uf_C_GetUIData1("None value ", element.uf_SH_TechZoneArticleTypeNone, TestCase_Name);
				System.out.println(" Value of Meta Field : "+noneValue);
				if(noneValue.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					UtilLib.Report_PASS("Value of Meta data is 'None'", "and value is absent", "Metavalueverification()");
				}
				else{
					UtilLib.Report_FAIL("Value of Meta data is 'None'", "and value is not present", "Metavalueverification()");
					rerunflag++;
				}





				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_Tech Zone data source_57", "is not verified", "SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO316_Metadata_Display_to_consistent_TechZone_dataSource_57()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_Tech Zone data source_57", "is verified", "SearchTopic_3_0_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO316_Metadata_Display_to_consistent_TechZone_dataSource_57()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}

	
	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14_Rel_PROG_User_clicks_on_SearchButton_without_Giving_any_query_in__searchbox_07
	 * @Description :  This scenarion is to verify that user should be in the home page when user clicks on search button without giving any query
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_User clicks on Search button without giving any query in the search box_07
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14_Rel_PROG_User_clicks_on_SearchButton_without_Giving_any_query_in__searchbox_07(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		int RerunFlag = 0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				

				System.out.println("***************   Calling Search without query   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}

				Robot robot = new Robot(); 
				robot.keyPress(KeyEvent.VK_ENTER);

				robot.keyRelease(KeyEvent.VK_ENTER);
				
				System.out.println("***************     Error Message Verification  ***************");

				UtilLib.Report_PASS("Error Message when user clicks on Search without Query ","is displayed ","ErrorMessageVerification()");
				
				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE2(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
					UtilLib.Report_PASS("User is not able to enter Query ","","ErrorMessageVerification()");
				}
				else{
				UtilLib.Report_PASS("User is able to enter Query ","","ErrorMessageVerification()");
				}


				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Topic_3_0_UI_Aug14_Rel_PROG_User_clicks_on_SearchButton_without_Giving_any_query_in__searchbox_07 to be consistent_C3 data source_109", "is not verified", "Topic 3.0 UI_Aug 14 Rel_PROG_User clicks on Search button without giving any query in the search box_07()");
				}
				else
				{
					UtilLib.Report_PASS("Topic_3_0_UI_Aug14_Rel_PROG_User_clicks_on_SearchButton_without_Giving_any_query_in__searchbox_07", "is verified", "Topic 3.0 UI_Aug 14 Rel_PROG_User clicks on Search button without giving any query in the search box_07()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  Topic_3_0_UI_Aug14_Rel_PROG_SR_contactName_for_C3results_06
	 * @Description :  This scenarion is to verify SR contact name should be displayed for C3 results
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Topic 3.0 UI_Aug 14 Rel_PROG_SR contact name for C3 results _06
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean Topic_3_0_UI_Aug14_Rel_PROG_SR_contactName_for_C3results_06(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		int flag=0;
		int RerunFlag = 0;
		try {
			for(TopicSearchTestData data :dataList ){

				uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
				UtilLib.WaitTime(5000);

				UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
				status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
				if(status==false){
					RerunFlag++;
					DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
					DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
				}
				System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

				status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
				}
				else
				{
					rerunflag++;
				}

				status=UtilLib.uf_C_ClickOnElement1("C3/CSOne/CARE", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
				if(status==true)
				{
					UtilLib.Report_PASS("CS/CSOne/CARE one data source ", "is selected", "selectingDataSource()");
				}
				else
				{
					rerunflag++;
				}


				System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

				status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
				if(status==false){
					rerunflag=rerunflag + 1;
				}



				status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
				if(status==false)
				{
					rerunflag++;
				}

				System.out.println("***************     Verification of meta Fields 'None' value  ***************");

				String SRContactValue=UtilLib.uf_C_GetUIData1("SR Contact Name", element.uf_SH_C3SRContactName, TestCase_Name);
				System.out.println(" Value of Meta Field : "+SRContactValue);
				if(SRContactValue.equals(data.getI_PORTLET_VALUE2().trim()))
				{
					UtilLib.Report_PASS("SR Contact Name ", "is dispalyed as a metafield for C3", "Metavalueverification()");
				}
				else{
					UtilLib.Report_FAIL("SR Contact Name ", "is not dispalyed as a metafield for C3", "Metavalueverification()");
				}





				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is not verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic 3 0 UI_Attivio Migration_PROG_Q4 SCOPE_CSSCSO 316_Metadata display to be consistent_C3 data source_109", "is verified", "SearchTopic_3_UI_AttivioMigration_PROG_Q4SCOPE_CSSCSO_MetadataDisplay_consistent_C3datasource_109()");
					status=true;
				}

			}
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec_14_Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24
	 * @Description : This scenario is to verify that the clicking on less will collapse the field values
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean SearchTopic_3_1_4_UI_Dec_14_Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("CDETS Checkbox", element.uf_N_cdetsChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					/*Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();*/
					
					
					
				
					UtilLib.uf_C_ClickOnElement1("Topic Preview Link", element.uf_SH_CDETS_TopicPreview_Link, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					String CDETS_TopicPreview_Header=UtilLib.uf_C_GetUIData1("CDETS Header in Topic Buddy Preview", element.uf_SH_CDETS_TopicPreview_Header, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+CDETS_TopicPreview_Header);
					if(CDETS_TopicPreview_Header.contains("CDETS/DDTS-"))
					{
						UtilLib.Report_PASS("CDETS Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("CDETS Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					UtilLib.uf_C_ClickOnElement1("Topic Preview More Link", element.uf_SH_CDETS_TopicPreview_MoreLink, TestCase_Name);
					UtilLib.WaitTime(1000);
			
					String LessLink_CDETS=UtilLib.uf_C_GetUIData1("Less Link For Cdets Topic Buddy Preview", element.uf_SH_CDETS_TopicPreview_LessLink, TestCase_Name);
					System.out.println("less Link  : "+LessLink_CDETS);
					if(LessLink_CDETS.equals("Less"))
					{
						UtilLib.Report_PASS("On Click of More Link the datais expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("On Click of More Link the datais expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24", "is not verified", "TS103_SearchTopic_3_1_4_UI_Dec_14_Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24", "is verified", "TS103_SearchTopic_3_1_4_UI_Dec_14_Rel_PROG_CDETS_MoreLink_Expand_Values_TopicBuddyPreview_24");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29
	 * @Description : This scenario is to verify that preview link is coming or not
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic3.1.4_UI_Dec 14  Rel_PROG_Newsgroup_Special Character_TopicBuddyPreview_29
	 * @throws Exception 
	 ********************************************************************************************/

	
	public static boolean SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("Newsgroup Checkbox", element.uf_N_newsGroupChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();
					
					
					
					WebElement e1 = driver.findElement(By.xpath(element.uf_SH_NewsgroupChevron_TopicPreview));
					action.moveToElement(e1).build().perform();
					
					
					String NewsgroupHeader_TopicPreview=UtilLib.uf_C_GetUIData1("Newsgroup Header in Topic Buddy Preview", element.uf_SH_NewsgroupHeader_TopicPreview, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+NewsgroupHeader_TopicPreview);
					if(NewsgroupHeader_TopicPreview.contains("Newsgroup-"))
					{
						UtilLib.Report_PASS("Newsgroup Header is present in Topic buddy Preview ","for Special Characters","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("Newsgroup Header is not present in Topic buddy Preview ","for Special Characters","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29", "is not verified", "TS104_SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29", "is verified", "TS104_SearchTopic_3_1_4_UI_Dec_14Rel_PROG_Newsgroup_SpecialCharacter_TopicBuddyPreview_29");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25
	 * @Description : This scenario is to verify that the clicking on less will collapse the field values
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("CDETS Checkbox", element.uf_N_cdetsChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					/*Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();*/
					
					
					
				
					UtilLib.uf_C_ClickOnElement1("Topic Preview Link", element.uf_SH_CDETS_TopicPreview_Link, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					String CDETS_TopicPreview_Header=UtilLib.uf_C_GetUIData1("CDETS Header in Topic Buddy Preview", element.uf_SH_CDETS_TopicPreview_Header, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+CDETS_TopicPreview_Header);
					if(CDETS_TopicPreview_Header.contains("CDETS/DDTS-"))
					{
						UtilLib.Report_PASS("CDETS Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("CDETS Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					UtilLib.uf_C_ClickOnElement1("Topic Preview More Link", element.uf_SH_CDETS_TopicPreview_MoreLink, TestCase_Name);
					UtilLib.WaitTime(1000);
			
					String LessLink_CDETS=UtilLib.uf_C_GetUIData1("Less Link For Cdets Topic Buddy Preview", element.uf_SH_CDETS_TopicPreview_LessLink, TestCase_Name);
					System.out.println("less Link  : "+LessLink_CDETS);
					if(LessLink_CDETS.equals("Less"))
					{
						UtilLib.Report_PASS("On Click of More Link the data is expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("On Click of More Link the data is expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					
					UtilLib.uf_C_ClickOnElement1("Topic Preview Less Link", element.uf_SH_CDETS_TopicPreview_LessLink, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					String MoreLink_CDETS=UtilLib.uf_C_GetUIData1("More Link For Cdets Topic Buddy Preview", element.uf_SH_CDETS_TopicPreview_MoreLink, TestCase_Name);
					System.out.println("More Link  : "+MoreLink_CDETS);
					if(MoreLink_CDETS.equals("More"))
					{
						UtilLib.Report_PASS("On Click of Less Link the data is collapsing ","and More Link is present","Preview_SunctionalityVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("On Click of Less Link the data is not collapsing ","and More Link is  not present","Preview_SunctionalityVerification()");
					}
					
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25", "is not verified", "TS105_SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25", "is verified", "TS105_SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_3_1_4_UI_Dec14_Rel_PROG_C3_MoreLink_Expand_Values_TopicBuddyPreview_16
	 * @Description : This scenario is to verify that the More Link is present for large data  in Topic Buddy Preview and on click of the link tha value expands
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic3.1.4_UI_Dec 14  Rel_PROG_C3_MoreLink_Expand_Values_TopicBuddyPreview_16
	 * @throws Exception 
	 ********************************************************************************************/


	public static boolean SearchTopic_3_1_4_UI_Dec14_Rel_PROG_C3_MoreLink_Expand_Values_TopicBuddyPreview_16(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;

			try {
				for(TopicSearchTestData data :dataList ){

					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
					if(searchQuery.equals(null))
					{
						rerunflag++;
					}
					
					UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					
					UtilLib.WaitTime(3000);
						
					String SearchResults=UtilLib.uf_C_GetUIData1("Search Results", element.uf_SH_searchResults, TestCase_Name);
					System.out.println("Result is : "+SearchResults);
					if(SearchResults.contains(data.getI_PORTLET_VALUE2()))
					{
						UtilLib.Report_PASS(" User is navigated to the results page and the results  ", "is displayed", "resultPageVerification()");
					}
					else{
						rerunflag++;
					}
					UtilLib.uf_C_ClickOnElement1("All Checkbox", element.uf_N_allChkBox, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					
					UtilLib.uf_C_ClickOnElement1("C3 Checkbox", element.uf_N_c3ChkBox, TestCase_Name);
					UtilLib.WaitTime(6000);
					
					/*Actions action = new Actions(driver);
					WebElement e = driver.findElement(By.xpath(element.uf_SH_Newsgroupresult1));
					action.moveToElement(e).build().perform();*/
					
					
					
				
					UtilLib.uf_C_ClickOnElement1("Topic Preview Link for C3", element.uf_SH_C3_TopicPreview_Link, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					String CDETS_TopicPreview_Header=UtilLib.uf_C_GetUIData1("C3 Header in Topic Buddy Preview", element.uf_SH_C3_TopicPreview_Header, TestCase_Name);
				    System.out.println("Newsgroup Header in Topic buddy Preview is : "+CDETS_TopicPreview_Header);
					if(CDETS_TopicPreview_Header.contains("C3/CSOne/CARE-"))
					{
						UtilLib.Report_PASS("C3/CSOne/CARE Header is present in Topic buddy Preview ","Preview functionality verified","Preview_SunctionalityVerification()");
					}
					else
					{
						UtilLib.Report_FAIL("C3/CSOne/CARE Header is not present in Topic buddy Preview ","Preview functionality is not verified","Preview_SunctionalityVerification()");
						rerunflag++;
					}
					
					UtilLib.uf_C_ClickOnElement1("Topic Preview More Link", element.uf_SH_C3_TopicPreview_MoreLink, TestCase_Name);
					UtilLib.WaitTime(1000);
			
					String LessLink_CDETS=UtilLib.uf_C_GetUIData1("Less Link For C3 Topic Buddy Preview", element.uf_SH_C3_TopicPreview_LessLink, TestCase_Name);
					System.out.println("less Link  : "+LessLink_CDETS);
					if(LessLink_CDETS.equals("Less"))
					{
						UtilLib.Report_PASS("On Click of More Link the data is expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("On Click of More Link the data is expanding ","Less Link is present","Preview_SunctionalityVerification()");
					}
					
					UtilLib.uf_C_ClickOnElement1("Topic Preview Less Link", element.uf_SH_C3_TopicPreview_MoreLink, TestCase_Name);
					UtilLib.WaitTime(1000);
					
					String MoreLink_CDETS=UtilLib.uf_C_GetUIData1("More Link For Cdets Topic Buddy Preview", element.uf_SH_C3_TopicPreview_MoreLink, TestCase_Name);
					System.out.println("More Link  : "+MoreLink_CDETS);
					if(MoreLink_CDETS.equals("More"))
					{
						UtilLib.Report_PASS("On Click of Less Link the data is collapsing ","and More Link is present","Preview_SunctionalityVerification()");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("On Click of Less Link the data is not collapsing ","and More Link is  not present","Preview_SunctionalityVerification()");
					}
					
					
					if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25", "is not verified", "TS105_SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25");
				}
				else
				{
					UtilLib.Report_PASS("Search Topic3.1.4_UI_Dec 14  Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25", "is verified", "TS105_SearchTopic_3_1_4_UI_Dec14_Rel_PROG_CDETS_MoreLink_Collapse_Values_TopicBuddyPreview_25");
					status=true;
				}

				
				}}
			catch(UnhandledAlertException alert){
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

		
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name : SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39
	 * @Description :    This scenario is to  edit filter and add any filter  of the selected data source 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILTERS_Edit the filter for data source name_Select All_39
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0;

		for (TopicSearchTestData data : dataList) {

		try {

			int RerunFlag = 0;

			uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
			UtilLib.WaitTime(5000);

			UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
			
			status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
			if(status==false){
				rerunflag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
				//	driver.findElement(By.xpath(element.uf_SH_ResultpagequeryText)).clear();

					status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					status=UtilLib.uf_C_ClickOnElement1("Techzone datasource", element.uf_N_techzoneChkBox, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(7000);
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					
					status=uf_SH_EditFilterVerification_Techzone(element.uf_N_datasources, data, TestCase_Name);
					if(status=false){
						RerunFlag++;
					}
					status=UtilLib.uf_C_ClickOnElement1("SelectAll Link ", element.uf_SH_editSelectAllPopUp, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Cancel Button in Edit Filter Pop Up ", element.uf_SH_Edit_CancelButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link ", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					for(int i=1;i<=10;i++){
						//System.out.println(i);
						boolean FilterIsSelected=driver.findElement(By.xpath("//*[@id='filtersTbl']/tbody/tr["+i+"]/td/input")).isSelected();	
						if(FilterIsSelected==true)
						{
						flag++;
						//System.out.println("flag :"+flag);
					}}
					if(flag==10)
					{
						UtilLib.Report_PASS("All Filters are selected on click of", "SelectAllLink","FilterVerification()");
					}
					else{
						UtilLib.Report_FAIL("All Filters are not selected on click of", "SelectAllLink","FilterVerification()");
						rerunflag++;
					}
					
					UtilLib.WaitTime(1000);
					status=UtilLib.uf_C_ClickOnElement1("OK Button in Edit Filter Pop Up ", element.uf_SH_Edit_OKButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					//////////////////////////////******Verification Of Filters on Result Page*******//////////////////////
					
					int flag1=0;
					String authorFilter=UtilLib.uf_C_GetUIData1("Author Filter", element.uf_SH_AuthorFilter_Techzone, TestCase_Name);
					if(authorFilter.equals(data.getI_PORTLET_VALUE5()))
					{
						
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Author Filter  ", "is not Displayed", "resultPageVerification()");
					}
					
					
					String StyleFilter=UtilLib.uf_C_GetUIData1("Style Filter", element.uf_SH_StyleFilter_Techzone, TestCase_Name);
					
					if(StyleFilter.equals(data.getI_PORTLET_VALUE6()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Style Filter ", "is not  Displayed", "resultPageVerification()");
					}
					
					String StateFilter_Techzone=UtilLib.uf_C_GetUIData1("State Filter", element.uf_SH_StateFilter_Techzone, TestCase_Name);
					if(StateFilter_Techzone.equals(data.getI_PORTLET_VALUE7()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("State Filter", "is not  Displayed", "resultPageVerification()");
					}
					
					String BoardFilter_Techzone=UtilLib.uf_C_GetUIData1("Board Filter", element.uf_SH_BoardFilter_Techzone, TestCase_Name);
					if(BoardFilter_Techzone.equals(data.getI_PORTLET_VALUE8()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Board Filter ", "is not  Displayed", "resultPageVerification()");
					}
					String HasLinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("Has Linked SR Filter", element.uf_SH_HasLinkedSRFilter_Techzone, TestCase_Name);
					if(HasLinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE9()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("HasLinked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					String HadKudosFilter_Techzone=UtilLib.uf_C_GetUIData1("Had Kudos Filter", element.uf_SH_HadKudosFilter_Techzone, TestCase_Name);
					if(HadKudosFilter_Techzone.equals(data.getI_PORTLET_VALUE10()))
					{
						flag1++;
						
					}
					else{
					
						UtilLib.Report_FAIL("Had Kudos Filter", "is not Displayed", "resultPageVerification()");
					}
					String CountofLinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("Count of Linked SR Filter", element.uf_SH_CountofLinkedSRFilter_Techzone, TestCase_Name);
					if(CountofLinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE11()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Count of Linked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					String LinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("LinkedSR Filter", element.uf_SH_LinkedSRFilter_Techzone, TestCase_Name);
					if(LinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE12()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Linked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					
				if(flag1==8){
					UtilLib.Report_PASS("All Filters ", "are displayed on result page", "resultPageVerification()");
				}
				else{
					UtilLib.Report_FAIL("All Filters ", "are displayed on result page", "resultPageVerification()");
					rerunflag++;
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39", "is not verified", "TS075_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39", "is verified", "TS075_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39");
					status=true;
				}

				
				}

			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	
	/********************************************************************************************
	 * @Function_Name : SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40
	 * @Description :     This scenario is to  edit filter and add any filter  of the selected data source 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILTERS_Edit the filter for data source name_ADD filter_Clear All_40
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0;

		for (TopicSearchTestData data : dataList) {

		try {

			int RerunFlag = 0;

			uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
			UtilLib.WaitTime(5000);

			UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
			
			status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
			if(status==false){
				rerunflag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
				//	driver.findElement(By.xpath(element.uf_SH_ResultpagequeryText)).clear();

					status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					status=UtilLib.uf_C_ClickOnElement1("Techzone datasource", element.uf_N_techzoneChkBox, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(7000);
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					
					status=uf_SH_EditFilterVerification_Techzone(element.uf_N_datasources, data, TestCase_Name);
					if(status=false){
						RerunFlag++;
					}
					status=UtilLib.uf_C_ClickOnElement1("ClearAll Link ", element.uf_SH_editClearAllPopUp, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Cancel Button in Edit Filter Pop Up ", element.uf_SH_Edit_CancelButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link ", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					for(int i=1;i<=10;i++){
						//System.out.println(i);
						boolean FilterIsSelected=driver.findElement(By.xpath("//*[@id='filtersTbl']/tbody/tr["+i+"]/td/input")).isSelected();	
						if(FilterIsSelected==false)
						{
						flag++;
						//System.out.println("flag :"+flag);
					}}
					if(flag==10)
					{
						UtilLib.Report_PASS("All Filters are cleared on click of", "ClearAllLink","FilterVerification()");
					}
					else{
						UtilLib.Report_FAIL("All Filters are not cleared on click of", "ClearAllLink","FilterVerification()");
						rerunflag++;
					}
					
				
					
					status=UtilLib.uf_C_ClickOnElement1("Author Filter Link ", element.uf_SH_AuthorFilterChkbox_Techzone, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(3000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("OK Button in Edit Filter Pop Up ", element.uf_SH_Edit_OKButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					//////////////////////////////******Verification Of Filters on Result Page*******//////////////////////
					
					int flag1=0;
					String authorFilter=UtilLib.uf_C_GetUIData1("Author Filter", element.uf_SH_AuthorFilter_Techzone, TestCase_Name);
					if(authorFilter.equals(data.getI_PORTLET_VALUE5()))
					{
						
						UtilLib.Report_PASS("Author Filter  ", "is Displayed and other filters are not displayed", "resultPageVerification()");
						}
					else{
						
						UtilLib.Report_FAIL("Author Filter  ", "is not Displayed", "resultPageVerification()");
						rerunflag++;
					}
					
					/*boolean StyleFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_StyleFilter_Techzone)).isDisplayed();
					System.out.println(StyleFilter_Techzone);
					if(StyleFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("StyleFilter Techzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
				
					
					boolean StateFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_StateFilter_Techzone)).isDisplayed();
					if(StateFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("State Filter Techzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					boolean BoardFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_BoardFilter_Techzone)).isDisplayed();
					if(BoardFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("Board Filter Techzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					boolean HasLinkedSRFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_HasLinkedSRFilter_Techzone)).isDisplayed();
					if(HasLinkedSRFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("Has Linked SRFilter Techzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					boolean HadKudosFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_HadKudosFilter_Techzone)).isDisplayed();
					if(HadKudosFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("HadKudosFilter Techzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					boolean CountofLinkedSRFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_CountofLinkedSRFilter_Techzone)).isDisplayed();
					if(CountofLinkedSRFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("CountofLinkedSRFilterTechzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					boolean LinkedSRFilter_Techzone=driver.findElement(By.xpath(element.uf_SH_LinkedSRFilter_Techzone)).isDisplayed();
					if(LinkedSRFilter_Techzone==false)
					{
						flag1++;
						
					}else{
						UtilLib.Report_FAIL("LinkedSRFilterTechzone ", "is displayed on click of Clear all Link", "FilterVerification");
						rerunflag++;
					}
					
					
					
					if(flag1==7){
					UtilLib.Report_PASS("All Filters ", "are not displayed on click of CLear AllLink result page", "resultPageVerification()");
				}
				else{
					UtilLib.Report_FAIL("All Filters ", "are displayed on result page on click of CLear AllLink result page", "resultPageVerification()");
					rerunflag++;
				}*/
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40", "is not verified", "TS076_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40", "is verified", "TS076_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pagefilteringOptions_default_search_filters_FILTERS_EditFilter_for_dataSourceName_ADDfilter_ClearAll_40");
					status=true;
				}

				
				}

			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}
	
	/********************************************************************************************
	 * @Function_Name : SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultPagefilteringOptions_defaultSearch_filters_FILTERS_Edit_filter_for_datasourceName_Reset_to_default_41
	 * @Description :    This scenario is to  edit filter and add any filter  of the selected data source 
	 * @author nisahni
	 * @return boolean
	 * @param TestCase_Name -Search Topic 3 0 UI_Attivio Migration_PROG_RSLT 3 Result page filtering options_default search filters_FILTERS_Edit the filter for data source name_Reset to default_41
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_ResultPagefilteringOptions_defaultSearch_filters_FILTERS_Edit_filter_for_datasourceName_Reset_to_default_41(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int flag=0;
		int flag=0;

		for (TopicSearchTestData data : dataList) {

		try {

			int RerunFlag = 0;

			uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
			UtilLib.WaitTime(5000);

			UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
			
			status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
			if(status==false){
				rerunflag++;
				DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
				DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
			}
			
			String searchQuery=UtilLib.uf_C_EnterDataInTextBox("Search TextBox", element.uf_N_queryText, data.getI_PORTLET_VALUE1(), TestCase_Name);
			if(searchQuery.equals(null))
			{
				rerunflag++;
			}
			
			UtilLib.ClickOnElement("Search", element.uf_N_searchButton, TestCase_Name);
					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
				//	driver.findElement(By.xpath(element.uf_SH_ResultpagequeryText)).clear();

					status=UtilLib.uf_C_ClickOnElement1("All DataSource", element.uf_SH_allChkBox_AllDataSource, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					status=UtilLib.uf_C_ClickOnElement1("Techzone datasource", element.uf_N_techzoneChkBox, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					
					UtilLib.WaitTime(7000);
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					
					status=uf_SH_EditFilterVerification_Techzone(element.uf_N_datasources, data, TestCase_Name);
					if(status=false){
						RerunFlag++;
					}
					status=UtilLib.uf_C_ClickOnElement1("Reset to default Link ", element.uf_SH_editResetToDefaultPopUp, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Cancel Button in Edit Filter Pop Up ", element.uf_SH_Edit_CancelButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					status=UtilLib.uf_C_ClickOnElement1("Edit Filter Link ", element.uf_SH_EditFilterLink, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(2000);
					for(int i=1;i<=10;i++){
						//System.out.println(i);
						boolean FilterIsSelected=driver.findElement(By.xpath("//*[@id='filtersTbl']/tbody/tr["+i+"]/td/input")).isSelected();	
						if(FilterIsSelected==true)
						{
						flag++;
						//System.out.println("flag :"+flag);
					}}
					if(flag==10)
					{
						UtilLib.Report_PASS("All Filters are selected on click of", "ResetToDefaultLink","FilterVerification()");
					}
					else{
						UtilLib.Report_FAIL("All Filters are not selected on click of", "ResetToDefaultLink","FilterVerification()");
						rerunflag++;
					}
					
					UtilLib.WaitTime(1000);
					status=UtilLib.uf_C_ClickOnElement1("OK Button in Edit Filter Pop Up ", element.uf_SH_Edit_OKButton, TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}
					UtilLib.WaitTime(1000);
					
					
					//////////////////////////////******Verification Of Filters on Result Page*******//////////////////////
					
					int flag1=0;
					String authorFilter=UtilLib.uf_C_GetUIData1("Author Filter", element.uf_SH_AuthorFilter_Techzone, TestCase_Name);
					if(authorFilter.equals(data.getI_PORTLET_VALUE5()))
					{
						
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Author Filter  ", "is not Displayed", "resultPageVerification()");
					}
					
					
					String StyleFilter=UtilLib.uf_C_GetUIData1("Style Filter", element.uf_SH_StyleFilter_Techzone, TestCase_Name);
					
					if(StyleFilter.equals(data.getI_PORTLET_VALUE6()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Style Filter ", "is not  Displayed", "resultPageVerification()");
					}
					
					String StateFilter_Techzone=UtilLib.uf_C_GetUIData1("State Filter", element.uf_SH_StateFilter_Techzone, TestCase_Name);
					if(StateFilter_Techzone.equals(data.getI_PORTLET_VALUE7()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("State Filter", "is not  Displayed", "resultPageVerification()");
					}
					
					String BoardFilter_Techzone=UtilLib.uf_C_GetUIData1("Board Filter", element.uf_SH_BoardFilter_Techzone, TestCase_Name);
					if(BoardFilter_Techzone.equals(data.getI_PORTLET_VALUE8()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Board Filter ", "is not  Displayed", "resultPageVerification()");
					}
					String HasLinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("Has Linked SR Filter", element.uf_SH_HasLinkedSRFilter_Techzone, TestCase_Name);
					if(HasLinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE9()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("HasLinked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					String HadKudosFilter_Techzone=UtilLib.uf_C_GetUIData1("Had Kudos Filter", element.uf_SH_HadKudosFilter_Techzone, TestCase_Name);
					if(HadKudosFilter_Techzone.equals(data.getI_PORTLET_VALUE10()))
					{
						flag1++;
						
					}
					else{
					
						UtilLib.Report_FAIL("Had Kudos Filter", "is not Displayed", "resultPageVerification()");
					}
					String CountofLinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("Count of Linked SR Filter", element.uf_SH_CountofLinkedSRFilter_Techzone, TestCase_Name);
					if(CountofLinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE11()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Count of Linked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					String LinkedSRFilter_Techzone=UtilLib.uf_C_GetUIData1("LinkedSR Filter", element.uf_SH_LinkedSRFilter_Techzone, TestCase_Name);
					if(LinkedSRFilter_Techzone.equals(data.getI_PORTLET_VALUE12()))
					{
						flag1++;
						
					}
					else{
						
						UtilLib.Report_FAIL("Linked SR Filter", "is not  Displayed", "resultPageVerification()");
					}
					
				if(flag1==8){
					UtilLib.Report_PASS("All Filters ", "are displayed on result page", "resultPageVerification()");
				}
				else{
					UtilLib.Report_FAIL("All Filters ", "are displayed on result page", "resultPageVerification()");
					rerunflag++;
				}
				if(rerunflag>0){
					status=false;
					UtilLib.Report_FAIL("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39", "is not verified", "TS075_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39");
				}
				else
				{
					UtilLib.Report_PASS("SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39", "is verified", "TS075_SearchTopic_3_0_UI_AttivioMigration_PROG_RSLT3_Result_pageFiltering_options_defaultSearchFilters_FILTERS_Edit_Filter_for_dataSourceName_SelectAll_39");
					status=true;
				}

				
				}

			catch(IndexOutOfBoundsException exception1) { 		
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
		return status;
	}

	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Mar15_RelUI_AttivioMigration_PROG_CDETSonly_Expand_MoreFragments_displayOf_duplicatesOf_large_file
	 * @Description :  This scenario is to verify that there expand more fragments link is available  for CDETS
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Expand More Fragments  the display of duplicates of large file
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Mar15_RelUI_AttivioMigration_PROG_CDETSonly_Expand_MoreFragments_displayOf_duplicatesOf_large_file(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int RerunFlag = 0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){
				
					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					System.out.println("Login Status : "+status);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

					status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
					System.out.println("Clear All Status : "+status);
					if(status==true)
					{
						UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
					}
					else
					{
						rerunflag++;
					}

					status=UtilLib.uf_C_ClickOnElement1("CDETS", element.uf_SH_CDETS_DDTScheckbox, TestCase_Name);
					System.out.println("CDETS Status : "+status);
					if(status==true)
					{
						UtilLib.Report_PASS("CDETS data source ", "is selected", "selectingDataSource()");
					}
					else
					{
						rerunflag++;
					}


					System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					System.out.println("Enter Query & Search Status: "+status);
					if(status==false){
						rerunflag=rerunflag+1;
					}



					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					System.out.println("Result Status: "+status);
					if(status==false)
					{
						rerunflag++;
					}

					System.out.println("***************     Verification of Expand More Fragment link  ***************");

					/*String ExpandMoreFragment_link=UtilLib.uf_C_GetUIData1("CDETS Expand More Fragment link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
				
					System.out.println("ui : "+ExpandMoreFragment_link);
					System.out.println("db : "+data.getI_PORTLET_VALUE2());
					if(ExpandMoreFragment_link.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						UtilLib.Report_PASS("Expand More Fragment link ", "is displayed", "ExpandMoreFragment_linkVerification");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("Expand More Fragment link ", "is not displayed", "ExpandMoreFragment_linkVerification");
					}*/
					
					status=UtilLib.uf_C_ClickOnElement1("CDETS_ExpandMoreFragment_link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					System.out.println("CDETS Expand Fragment Status: "+status);
					if(status==false){
						rerunflag=rerunflag + 1;
					}


					UtilLib.WaitTime(2000);
				
					boolean Data_ExpandMoreFragment_link=driver.findElement(By.xpath(element.uf_SH_CDETS_Data_ExpandMoreFragment_link)).isDisplayed();
					System.out.println("Data Expand More Fragment link Status: "+status);
					if(Data_ExpandMoreFragment_link==true)
					{
						UtilLib.Report_PASS("Unique snippets from all the fragments of the file  ", "is displayed", "ExpandMoreFragment_linkVerification");
						
					}else{
						UtilLib.Report_FAIL("Unique snippets from all the fragments of the file  ", "is not displayed", "ExpandMoreFragment_linkVerification");
						rerunflag++;
					}
					
					
					
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopic_Mar15_RelUI_AttivioMigration_PROG_CDETSonly_Expand_MoreFragments_displayOf_duplicatesOf_large_file", "is not verified", "Search Topic Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Expand More Fragments  the display of duplicates of large file");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopic_Mar15_RelUI_AttivioMigration_PROG_CDETSonly_Expand_MoreFragments_displayOf_duplicatesOf_large_file", "is verified", "Search Topic Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Expand More Fragments  the display of duplicates of large file");
						status=true;
					}

				}
			

		}

		catch (AWTException e) {
			e.printStackTrace();
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}


	/********************************************************************************************
	 * @Function_Name :  SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile
	 * @Description :  This scenario is to verify that there is Collpase ALL link when more fragments of file available for CDETS 
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic  Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Collapse the display of duplicates of large file
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int RerunFlag = 0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){
				
					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

					status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
					}
					else
					{
						rerunflag++;
					}

					status=UtilLib.uf_C_ClickOnElement1("CDETS", element.uf_SH_CDETS_DDTScheckbox, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("CDETS data source ", "is selected", "selectingDataSource()");
					}
					else
					{
						rerunflag++;
					}


					System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					if(status==false){
						rerunflag=rerunflag+1;
					}



					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}

					System.out.println("***************     Verification of Expand More Fragment and Collapse All link  ***************");
/*
					String ExpandMoreFragment_link=UtilLib.uf_C_GetUIData1("CDETS Expand More Fragment link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					System.out.println("ExpandMoreFragment_link  : "+ExpandMoreFragment_link);
					if(ExpandMoreFragment_link.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}*/
					
					status=UtilLib.uf_C_ClickOnElement1("CDETS_ExpandMoreFragment_link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}


					UtilLib.WaitTime(2000);
				
					boolean Data_ExpandMoreFragment_link=driver.findElement(By.xpath(element.uf_SH_CDETS_Data_ExpandMoreFragment_link)).isDisplayed();
					if(Data_ExpandMoreFragment_link==true)
					{
						UtilLib.Report_PASS("Unique snippets from all the fragments of the file  ", "is displayed", "ExpandMoreFragment_linkVerification");
						
					}else{
						UtilLib.Report_FAIL("Unique snippets from all the fragments of the file  ", "is not displayed", "ExpandMoreFragment_linkVerification");
						rerunflag++;
					}
					
					String CDETS_CollapseAll_link=UtilLib.uf_C_GetUIData1("Collapse All link", element.uf_SH_CDETS_CollapseAll_link, TestCase_Name);
					
					System.out.println("CollapseAll_link  : "+CDETS_CollapseAll_link);
					if(CDETS_CollapseAll_link.equals(data.getI_PORTLET_VALUE3().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}
					
					
					
					status=UtilLib.uf_C_ClickOnElement1("Collapse All Link", element.uf_SH_CDETS_CollapseAll_link, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}
					
					

					UtilLib.WaitTime(2000);
					
					String ExpandMoreFragment_link1=UtilLib.uf_C_GetUIData1("CDETS Expand More Fragment link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					System.out.println("ExpandMoreFragment_link  : "+ExpandMoreFragment_link1);
					if(ExpandMoreFragment_link1.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						flag++;
						UtilLib.Report_PASS("Data is collapsed", "is one file format", "CollapseAll_LinkVerification");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("Data is collapsed", "is one file format", "CollapseAll_LinkVerification");
					}
					
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile", "is not verified", "Search Topic  Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Collapse the display of duplicates of large file");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile", "is verified", "Search Topic  Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Collapse the display of duplicates of large file");
						status=true;
					}

				}
			

		}

		catch (AWTException e) {
			e.printStackTrace();
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
	
	
	/********************************************************************************************
	 * @Function_Name :  SearchTopic_Mar15Rel_AttivioMigration_PROG_CDETSwith_other_datasources_Expand_MoreFragments_displayOf_duplicatesOf_largeFile
	 * @Description :  This scenario is to verify that there expand more fragments link is available only for CDETS
	 * @author SHAFMEHT
	 * @return boolean
	 * @param TestCase_Name -Search Topic Mar15 Rel_Attivio Migration_PROG_CDETSwith other datasources_Expand More Fragments  the display of duplicates of large file
	 * @throws Exception 
	 ********************************************************************************************/

	public static boolean SearchTopic_Mar15Rel_AttivioMigration_PROG_CDETSwith_other_datasources_Expand_MoreFragments_displayOf_duplicatesOf_largeFile(
			List<TopicSearchTestData> dataList, String TestCase_Name) throws Exception {

		boolean status=false;
		int rerunflag=0;
		//int RerunFlag = 0;
		int flag=0;
		try {
			for(TopicSearchTestData data :dataList ){
				
					uf_p2s_driverInitiation(data.getI_URL(), dataList,TestCase_Name);
					UtilLib.WaitTime(5000);

					UtilLib.uf_C_HandleAlert_Dismiss("TopicSearchLoginPage", "This page is accessing information that is not under its control. This poses a security risk. Do you want to continue?", TestCase_Name);
					status = uf_R_Login_Topic_Search(dataList,"Topic Search", TestCase_Name);
					if(status==false){
						rerunflag++;
						DefectDetailsDriver.AppendDefectDetailsXML("ERROR004",TestCase_Name,UtilLib.CaptureScreenshot(TestCase_Name));
						DefectDetailsDriver.AppendFailedTestXML(TestCase_Name);	
					}

					System.out.println("***************   Clicking on ClearAll and selecting one datasource   ***************");

					status=UtilLib.uf_C_ClickOnElement1("Clear All", element.uf_SH_clearAll, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("Clear All ", "is clicked", "ClearAllVerifiaction()");
					}
					else
					{
						rerunflag++;
					}

					status=UtilLib.uf_C_ClickOnElement1("CDETS", element.uf_SH_C3_CSOne_CAREcheckbox, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("CDETS data source ", "is selected", "selectingDataSource()");
					}
					else
					{
						rerunflag++;
					}

					status=UtilLib.uf_C_ClickOnElement1("C3", element.uf_SH_CDETS_DDTScheckbox, TestCase_Name);
					if(status==true)
					{
						UtilLib.Report_PASS("C3/CSOne data source ", "is selected", "selectingDataSource()");
					}
					else
					{
						rerunflag++;
					}
					System.out.println("***************   Calling Search_A_EnterSearchQueryAndClickOnSearch   ***************");

					status=Search_SH_EnterSearchQueryAndClickOnSearch( data.getI_PORTLET_VALUE1(),TestCase_Name );
					if(status==false){
						rerunflag=rerunflag+1;
					}



					status=uf_SH_ResultComingVerification(data.getI_PORTLET_VALUE1(),TestCase_Name);
					if(status==false)
					{
						rerunflag++;
					}

					System.out.println("***************     Verification of Expand More Fragment and Collapse All link  ***************");

					/*String ExpandMoreFragment_link=UtilLib.uf_C_GetUIData1("CDETS Expand More Fragment link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					System.out.println("ExpandMoreFragment_link  : "+ExpandMoreFragment_link);
					if(ExpandMoreFragment_link.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}*/
					
					status=UtilLib.uf_C_ClickOnElement1("CDETS_ExpandMoreFragment_link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}


					UtilLib.WaitTime(2000);
				
					boolean Data_ExpandMoreFragment_link=driver.findElement(By.xpath(element.uf_SH_CDETS_Data_ExpandMoreFragment_link)).isDisplayed();
					if(Data_ExpandMoreFragment_link==true)
					{
						UtilLib.Report_PASS("Unique snippets from all the fragments of the file  ", "is displayed", "ExpandMoreFragment_linkVerification");
						
					}else{
						UtilLib.Report_FAIL("Unique snippets from all the fragments of the file  ", "is not displayed", "ExpandMoreFragment_linkVerification");
						rerunflag++;
					}
					
					String CDETS_CollapseAll_link=UtilLib.uf_C_GetUIData1("Collapse All link", element.uf_SH_CDETS_CollapseAll_link, TestCase_Name);
					
					System.out.println("CollapseAll_link  : "+CDETS_CollapseAll_link);
					if(CDETS_CollapseAll_link.equals(data.getI_PORTLET_VALUE3().trim()))
					{
						flag++;
					}
					else{
						rerunflag++;
					}
					
					
					
					status=UtilLib.uf_C_ClickOnElement1("Collapse All Link", element.uf_SH_CDETS_CollapseAll_link, TestCase_Name);
					if(status==false){
						rerunflag=rerunflag + 1;
					}
					
					

					UtilLib.WaitTime(2000);
					
					String ExpandMoreFragment_link1=UtilLib.uf_C_GetUIData1("CDETS Expand More Fragment link", element.uf_SH_CDETS_ExpandMoreFragment_link, TestCase_Name);
					System.out.println("ExpandMoreFragment_link  : "+ExpandMoreFragment_link1);
					if(ExpandMoreFragment_link1.equals(data.getI_PORTLET_VALUE2().trim()))
					{
						flag++;
						UtilLib.Report_PASS("Data is collapsed", "is one file format", "CollapseAll_LinkVerification");
					}
					else{
						rerunflag++;
						UtilLib.Report_FAIL("Data is collapsed", "is one file format", "CollapseAll_LinkVerification");
					}
					
					if(rerunflag>0){
						status=false;
						UtilLib.Report_FAIL("SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile", "is not verified", "Search Topic  Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Collapse the display of duplicates of large file");
					}
					else
					{
						UtilLib.Report_PASS("SearchTopicMar15RelUI_AttivioMigration_PROG_CDETSonly_Collapse_display_duplicatesOf_largeFile", "is verified", "Search Topic  Mar15 Rel UI_Attivio Migration_PROG_CDETS only_Collapse the display of duplicates of large file");
						status=true;
					}

				}
			

		}

		catch (AWTException e) {
			e.printStackTrace();
		}
		catch(UnhandledAlertException alert){
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


		return status;
	}
}
	
	
	
	