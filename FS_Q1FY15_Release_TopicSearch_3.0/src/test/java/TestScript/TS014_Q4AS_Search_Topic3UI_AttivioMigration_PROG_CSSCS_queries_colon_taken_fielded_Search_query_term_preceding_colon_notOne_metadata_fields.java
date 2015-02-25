package TestScript;


import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import resources.utilFiles.DBAutomationConnection;
import resources.utilFiles.DBAutomationFunction;
import resources.utilFiles.POM_Lib;
import resources.utilFiles.TopicSearchTestData;
import resources.utilFiles.UtilLib;

public class TS014_Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields {

	public static String TestCase_Name = "TS014_Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields";
	private DBAutomationFunction arr_data = new DBAutomationFunction();
	private DBAutomationConnection db = new DBAutomationConnection();
	Connection con = null;
	public List<TopicSearchTestData> retObjArr;
	TopicSearchTestData data = new TopicSearchTestData();
	String TestExecutionStartTime = "";
	String TestExecutionStopTime = "";
	String Status = "PASSED";
	boolean ExecutionStatus = false;

	@BeforeClass
	public void setUp() throws Exception{
		con = db.getConnection();
	}



	@BeforeMethod
	public void initiatebrowser() throws Exception, IOException, TransformerException {
		TestExecutionStartTime = UtilLib.FindSysTimeExecution();
		System.out.println("'"+TestCase_Name+"' Testcase Execution started at "+UtilLib.FindSysTimeExecution());
	}


	@Test	
	public void ts002_Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields() throws Exception {
		String TEST_SCENARIO = TestCase_Name;
		String TEST_PHASE = "REG";
		String EXECUTION = "Y";
		
		retObjArr = new ArrayList<TopicSearchTestData>();
		retObjArr= arr_data.getDbData(TEST_SCENARIO,TEST_PHASE, EXECUTION, con);
		System.out.println(retObjArr.get(0).getI_URL());
		ExecutionStatus=POM_Lib.Q4AS_Search_Topic3UI_AttivioMigration_PROG_CSSCS_queries_colon_taken_fielded_Search_query_term_preceding_colon_notOne_metadata_fields(retObjArr,TestCase_Name);
		AssertJUnit.assertTrue(ExecutionStatus);

	}

	@AfterClass
	public void tearDown() throws Exception {
		TestExecutionStopTime = UtilLib.FindSysTimeExecution();
		System.out.println("'"+TestCase_Name+"' Testcase Execution ended at "+UtilLib.FindSysTimeExecution());
		UtilLib.CloseDriver();
		String ExecutedBy = System.getProperty("user.name");
		String TestExecutionTime = UtilLib.TestTimeSummary(TestExecutionStartTime, TestExecutionStopTime);	
		arr_data.updateStatus(ExecutedBy,TestCase_Name,ExecutionStatus,TestExecutionStartTime,TestExecutionTime);

	}

}


