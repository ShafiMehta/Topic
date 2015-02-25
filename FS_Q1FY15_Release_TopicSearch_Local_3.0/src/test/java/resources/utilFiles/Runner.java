package resources.utilFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

public class Runner {

	static String ExecutionStartTime = "01-JAN-1990 00:00:00 AM";
	static String ExecutionStopTime = "01-JAN-1990 00:00:00 AM";

	@Test
	public static void run() throws TransformerException, SAXException,
			IOException, ParserConfigurationException {

		ExecutionStartTime = UtilLib.FindSysTimeExecution();
		System.out.println("ExecutionStartTime " + ExecutionStartTime);

		// Delete Existing
		DefectDetailsDriver.DeleteTestDefectXML();
		DefectDetailsDriver.DeleteFailedTestXML();
		UtilLib.DeletePrimaryTestngXML();
		// DefectDetailsDriver.CreateFailedTestXMLFile();
		// Create the run time primaryTestng.xml file
		UtilLib.AppendPrimaryXML();

		// ======Primary Execution
		System.out
				.println("************************************ Primary Execution Initialized **********************************************");
		ArrayList<String> testNGSuiteArray = new ArrayList<String>();
		List<String> suitesList = testNGSuiteArray;
		TestNG testng = new TestNG();
		TestListenerAdapter listener = new TestListenerAdapter();
		testng.setOutputDirectory(Object_Definition_Library
				.getEnvVar("PrimaryExecutionResultPath"));
		suitesList.add(Object_Definition_Library
				.getEnvVar("PrimaryExecutionXMLPath"));
		testng.setTestSuites(suitesList);
		testng.addListener(listener);
		testng.run();
		// generate FailedTest.xml and Delete Existing TestDefects.xml
		System.out
				.println("************************************ Primary Execution Completed Successfully **********************************************");
				
		// ======Second Execution
		
			
		File f = new File (Object_Definition_Library.getEnvVar("SecondaryExecutionXMLPath"));
		if(f.exists()){
			System.out.println("FailedTest.xml found!");
			System.out
			
			.println("************************************ Secondary Execution Initialized **********************************************");
			ArrayList<String> testNGSuiteArray1 = new ArrayList<String>();
			List<String> suitesList1 = testNGSuiteArray1;
			TestNG testng1 = new TestNG();
			TestListenerAdapter listener1 = new TestListenerAdapter();
			testng1.setOutputDirectory(Object_Definition_Library
			.getEnvVar("SecondaryExecutionResultPath"));
			suitesList1.add(Object_Definition_Library
			.getEnvVar("SecondaryExecutionXMLPath"));
			testng1.setTestSuites(suitesList1);
			testng1.addListener(listener1);
			testng1.run(); 
			System.out
			.println("************************************ Secondary Execution Completed Successfully **********************************************");
		}else{
			System.out.println("All TestScripts passed.");
		}

		
		ExecutionStopTime = UtilLib.FindSysTimeExecution();
		System.out.println("ExecutionStopTime " + ExecutionStopTime);

		// =======Defect recovery

		/*
		 * ArrayList<String> DefectNamesArray =
		 * DefectDetailsDriver.RetrieveDefectFromXML(); ArrayList<String>
		 * DefectDetailsArray = new ArrayList<String>(); ArrayList<String>
		 * DefectScreenshot =
		 * DefectDetailsDriver.RetreiveScreenShotpathFromTestDefectXML();
		 * for(int i=0;i<DefectNamesArray.size();i++){
		 * System.out.println("the "+i+"th defect is "+DefectNamesArray.get(i));
		 * DefectDetailsArray =
		 * DefectDetailsDriver.RequestDetailsFromDefectDetailsHubXML
		 * (DefectNamesArray.get(i)); for(int
		 * j=0;j<DefectDetailsArray.size();j++){
		 * System.out.println("the "+j+"th defect details is "
		 * +DefectDetailsArray.get(j)); }
		 * 
		 * //=======Defect logging
		 * 
		 * System.out.println("DEFECT SCREENSHOT = "+DefectScreenshot.get(i)); }
		 */

		// DefectDetailsDriver.RaiseDefect();
	
}
}