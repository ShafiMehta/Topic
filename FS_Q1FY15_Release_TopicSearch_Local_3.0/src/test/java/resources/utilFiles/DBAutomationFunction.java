/*package resources.utilFiles;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class DBAutomationFunction {
	
	private DBAutomationConnection db = new DBAutomationConnection();
	Connection con = null;

           

	public Object[][] getDBArray(String TestScrtipt_Name, Connection con) throws Exception{
		Object[][] tabArray= null;

		try {
			//global declaration
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+TestScrtipt_Name+"'";
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rset.last();
			rowcount = rset.getRow();
			rset.first();

			int i=0;
			tabArray= new String[rowcount][colcount];
			for (i=0;i<rowcount;i++)
			{
				for (int j=0;j<colcount ;j++)
				{
					tabArray[i][j]=rset.getString(j+1);
					System.out.println("i="+i+"\tj="+j+"\tval="+tabArray[i][j]);
				}
				rset.next();
			}
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return tabArray;
	}

	public List<TopicSearchTestData> getDBArray_LocalDB(String Data, Connection con) throws Exception{
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();
		try {
			//global declaration
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+Data+"'";
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				if (null ==rset.getString("TOPICSRCH_DATA_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_DATA_ID"));
				}
				if (null ==rset.getString("TOPICSRCH_TEST_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_TEST_ID"));
				}
				
				if (null ==rset.getString("PORTFOLIO")){
					td.setPORTFOLIO("");
				}else{
					td.setPORTFOLIO(rset.getString("PORTFOLIO"));
				}
				
				if (null ==rset.getString("FLOW")){
					td.setFLOW("");
				}else{
					td.setFLOW(rset.getString("FLOW"));
				}
				
				if (null ==rset.getString("APPLICATION_TRACK")){
					td.setAPPLICATION_TRACK("");
				}else{
					td.setAPPLICATION_TRACK(rset.getString("APPLICATION_TRACK"));
				}
				
				if (null ==rset.getString("SUB_TRACK")){
					td.setSUB_TRACK("");
				}else{
					td.setSUB_TRACK(rset.getString("SUB_TRACK"));
				}
				
				if (null ==rset.getString("TEST_CASE_ID")){
					td.setTEST_CASE_ID("");
				}else{
					td.setTEST_CASE_ID(rset.getString("TEST_CASE_ID"));
				}
				
				
				
				if (null ==rset.getString("EXECUTION")){
					td.setEXECUTION("");
				}else{
					td.setEXECUTION(rset.getString("EXECUTION"));
				}
				
				if (null ==rset.getString("TESTSCRIPT_STATUS")){
					td.setTESTSCRIPT_STATUS("");
				}else{
					td.setTESTSCRIPT_STATUS(rset.getString("TESTSCRIPT_STATUS"));
				}
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				if (null ==rset.getString("TEST_METHOD_NAME")){
					td.setTEST_METHOD_NAME("");
				}else{
					td.setTEST_METHOD_NAME(rset.getString("TEST_METHOD_NAME"));
				}
				
				if (null ==rset.getString("BROWSER_TYPE")){
					td.setBROWSER_TYPE("");
				}else{
					td.setBROWSER_TYPE(rset.getString("BROWSER_TYPE"));
				}
				
				
				
				td.setRELEASE_NAME(rset.getString("RELEASE_NAME"));
				td.setRELEASE_CYCLE(rset.getString("RELEASE_CYCLE"));
				td.setO_LAST_EXECUTION_DATE(rset.getString("O_LAST_EXECUTION_DATE"));
	
				td.setO_LAST_EXECUTED_BY(rset.getString("O_LAST_EXECUTED_BY"));

				td.setO_LAST_EXECUTION_STATUS(rset.getString("O_LAST_EXECUTION_STATUS"));

				td.setO_LAST_EXECUTION_DURATION(rset.getString("O_LAST_EXECUTION_DURATION"));
				td.setO_EXECUTION_ERROR_MESSAGES(rset.getString("O_EXECUTION_ERROR_MESSAGES"));
			
				
				
				
				
				
				td.setI_URL(rset.getString("I_URL"));
				td.setI_LOGINUSER_NAME(rset.getString("I_LOGINUSER_NAME"));
				td.setI_LOGINUSER_PASSWORD(rset.getString("I_LOGINUSER_PASSWORD"));
			
				if (null ==rset.getString("I_PORTLET_VALUE1")){
					td.setI_PORTLET_VALUE1("");
				}else{
					td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				}
				
				
				
				
				
				td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				td.setI_PORTLET_VALUE2(rset.getString("I_PORTLET_VALUE2"));
				td.setI_PORTLET_VALUE3(rset.getString("I_PORTLET_VALUE3"));
				td.setI_PORTLET_VALUE4(rset.getString("I_PORTLET_VALUE4"));
				td.setI_PORTLET_VALUE5(rset.getString("I_PORTLET_VALUE5"));
				td.setI_PORTLET_VALUE6(rset.getString("I_PORTLET_VALUE6"));
				td.setI_PORTLET_VALUE7(rset.getString("I_PORTLET_VALUE7"));
				td.setI_PORTLET_VALUE8(rset.getString("I_PORTLET_VALUE8"));
				td.setI_PORTLET_VALUE9(rset.getString("I_PORTLET_VALUE9"));
				td.setI_PORTLET_VALUE10(rset.getString("I_PORTLET_VALUE10"));
				
				td.setI_PORTLET_VALUE11(rset.getString("I_PORTLET_VALUE11"));
				td.setI_PORTLET_VALUE12(rset.getString("I_PORTLET_VALUE12"));
				td.setI_PORTLET_VALUE13(rset.getString("I_PORTLET_VALUE13"));
				td.setI_PORTLET_VALUE14(rset.getString("I_PORTLET_VALUE14"));
				td.setI_PORTLET_VALUE15(rset.getString("I_PORTLET_VALUE15"));
				td.setI_PORTLET_VALUE16(rset.getString("I_PORTLET_VALUE16"));
				td.setI_PORTLET_VALUE17(rset.getString("I_PORTLET_VALUE17"));
				td.setI_PORTLET_VALUE18(rset.getString("I_PORTLET_VALUE18"));
				td.setI_PORTLET_VALUE19(rset.getString("I_PORTLET_VALUE19"));
				td.setI_PORTLET_VALUE20(rset.getString("I_PORTLET_VALUE20"));
				
				td.setI_PORTLET_VALUE21(rset.getString("I_PORTLET_VALUE21"));
				td.setI_PORTLET_VALUE22(rset.getString("I_PORTLET_VALUE22"));
				td.setI_PORTLET_VALUE23(rset.getString("I_PORTLET_VALUE23"));
				td.setI_PORTLET_VALUE24(rset.getString("I_PORTLET_VALUE24"));
				td.setI_PORTLET_VALUE25(rset.getString("I_PORTLET_VALUE25"));
				td.setI_PORTLET_VALUE26(rset.getString("I_PORTLET_VALUE26"));
				td.setI_PORTLET_VALUE27(rset.getString("I_PORTLET_VALUE27"));
				td.setI_PORTLET_VALUE28(rset.getString("I_PORTLET_VALUE28"));
				td.setI_PORTLET_VALUE29(rset.getString("I_PORTLET_VALUE29"));
				td.setI_PORTLET_VALUE30(rset.getString("I_PORTLET_VALUE30"));
				
				td.setI_PORTLET_VALUE31(rset.getString("I_PORTLET_VALUE31"));
				td.setI_PORTLET_VALUE32(rset.getString("I_PORTLET_VALUE32"));
				td.setI_PORTLET_VALUE33(rset.getString("I_PORTLET_VALUE33"));
				td.setI_PORTLET_VALUE34(rset.getString("I_PORTLET_VALUE34"));
				
				td.setI_PORTLET_VALUE35(rset.getString("I_PORTLET_VALUE35"));
				td.setI_PORTLET_VALUE36(rset.getString("I_PORTLET_VALUE36"));
				td.setI_PORTLET_VALUE37(rset.getString("I_PORTLET_VALUE37"));
				td.setI_PORTLET_VALUE38(rset.getString("I_PORTLET_VALUE38"));
				td.setI_PORTLET_VALUE39(rset.getString("I_PORTLET_VALUE39"));
				td.setI_PORTLET_VALUE40(rset.getString("I_PORTLET_VALUE40"));
				td.setI_PORTLET_VALUE41(rset.getString("I_PORTLET_VALUE41"));
				td.setI_PORTLET_VALUE42(rset.getString("I_PORTLET_VALUE42"));
				td.setI_PORTLET_VALUE43(rset.getString("I_PORTLET_VALUE43"));
				td.setI_PORTLET_VALUE44(rset.getString("I_PORTLET_VALUE44"));
				
				td.setI_PORTLET_VALUE45(rset.getString("I_PORTLET_VALUE45"));
				td.setI_PORTLET_VALUE46(rset.getString("I_PORTLET_VALUE46"));
				td.setI_PORTLET_VALUE47(rset.getString("I_PORTLET_VALUE47"));
				td.setI_PORTLET_VALUE48(rset.getString("I_PORTLET_VALUE48"));
				
				td.setI_PORTLET_VALUE49(rset.getString("I_PORTLET_VALUE49"));
				
				td.setI_PORTLET_VALUE50(rset.getString("I_PORTLET_VALUE50"));
				td.setI_PORTLET_VALUE51(rset.getString("I_PORTLET_VALUE51"));
				td.setI_PORTLET_VALUE52(rset.getString("I_PORTLET_VALUE52"));
				td.setI_PORTLET_VALUE53(rset.getString("I_PORTLET_VALUE53"));
				td.setI_PORTLET_VALUE54(rset.getString("I_PORTLET_VALUE54"));
				td.setI_PORTLET_VALUE55(rset.getString("I_PORTLET_VALUE55"));
				td.setI_PORTLET_VALUE56(rset.getString("I_PORTLET_VALUE56"));
				td.setI_PORTLET_VALUE57(rset.getString("I_PORTLET_VALUE57"));
				td.setI_PORTLET_VALUE58(rset.getString("I_PORTLET_VALUE58"));
				td.setI_PORTLET_VALUE59(rset.getString("I_PORTLET_VALUE59"));
				
				td.setI_PORTLET_VALUE60(rset.getString("I_PORTLET_VALUE60"));
				td.setI_PORTLET_VALUE61(rset.getString("I_PORTLET_VALUE61"));
				td.setI_PORTLET_VALUE62(rset.getString("I_PORTLET_VALUE62"));
				td.setI_PORTLET_VALUE63(rset.getString("I_PORTLET_VALUE63"));
				td.setI_PORTLET_VALUE64(rset.getString("I_PORTLET_VALUE64"));
				td.setI_PORTLET_VALUE65(rset.getString("I_PORTLET_VALUE65"));
				td.setI_PORTLET_VALUE66(rset.getString("I_PORTLET_VALUE66"));
				td.setI_PORTLET_VALUE67(rset.getString("I_PORTLET_VALUE67"));
				td.setI_PORTLET_VALUE68(rset.getString("I_PORTLET_VALUE68"));
				td.setI_PORTLET_VALUE69(rset.getString("I_PORTLET_VALUE69"));
				
				td.setI_PORTLET_VALUE70(rset.getString("I_PORTLET_VALUE70"));
				td.setI_PORTLET_VALUE71(rset.getString("I_PORTLET_VALUE71"));
				td.setI_PORTLET_VALUE72(rset.getString("I_PORTLET_VALUE72"));
				td.setI_PORTLET_VALUE73(rset.getString("I_PORTLET_VALUE73"));
				td.setI_PORTLET_VALUE74(rset.getString("I_PORTLET_VALUE74"));
				td.setI_PORTLET_VALUE75(rset.getString("I_PORTLET_VALUE75"));
				td.setI_PORTLET_VALUE76(rset.getString("I_PORTLET_VALUE76"));
				td.setI_PORTLET_VALUE77(rset.getString("I_PORTLET_VALUE77"));
				td.setI_PORTLET_VALUE78(rset.getString("I_PORTLET_VALUE78"));
				td.setI_PORTLET_VALUE79(rset.getString("I_PORTLET_VALUE79"));
				
				td.setI_PORTLET_VALUE80(rset.getString("I_PORTLET_VALUE80"));
				
				
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("Size inside : "+testDataList.size());
			rset.close();                 
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return testDataList;
	}




	@SuppressWarnings("unused")
	public List<TopicSearchTestData> getDbData(String TestScrtipt_Name, String Test_Phase, String Execution, Connection con){
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();

		try {                                        
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+TestScrtipt_Name+"' AND TEST_PHASE='"+Test_Phase+"' AND EXECUTION='"+Execution+"'";
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				if (null ==rset.getString("TOPICSRCH_DATA_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_DATA_ID"));
				}
				if (null ==rset.getString("TOPICSRCH_TEST_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_TEST_ID"));
				}
				
				if (null ==rset.getString("PORTFOLIO")){
					td.setPORTFOLIO("");
				}else{
					td.setPORTFOLIO(rset.getString("PORTFOLIO"));
				}
				
				if (null ==rset.getString("FLOW")){
					td.setFLOW("");
				}else{
					td.setFLOW(rset.getString("FLOW"));
				}
				
				if (null ==rset.getString("APPLICATION_TRACK")){
					td.setAPPLICATION_TRACK("");
				}else{
					td.setAPPLICATION_TRACK(rset.getString("APPLICATION_TRACK"));
				}
				
				if (null ==rset.getString("SUB_TRACK")){
					td.setSUB_TRACK("");
				}else{
					td.setSUB_TRACK(rset.getString("SUB_TRACK"));
				}
				
				if (null ==rset.getString("TEST_CASE_ID")){
					td.setTEST_CASE_ID("");
				}else{
					td.setTEST_CASE_ID(rset.getString("TEST_CASE_ID"));
				}
				
				
				
				if (null ==rset.getString("EXECUTION")){
					td.setEXECUTION("");
				}else{
					td.setEXECUTION(rset.getString("EXECUTION"));
				}
				
				if (null ==rset.getString("TESTSCRIPT_STATUS")){
					td.setTESTSCRIPT_STATUS("");
				}else{
					td.setTESTSCRIPT_STATUS(rset.getString("TESTSCRIPT_STATUS"));
				}
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				if (null ==rset.getString("TEST_METHOD_NAME")){
					td.setTEST_METHOD_NAME("");
				}else{
					td.setTEST_METHOD_NAME(rset.getString("TEST_METHOD_NAME"));
				}
				
				if (null ==rset.getString("BROWSER_TYPE")){
					td.setBROWSER_TYPE("");
				}else{
					td.setBROWSER_TYPE(rset.getString("BROWSER_TYPE"));
				}
				
				
				
				td.setRELEASE_NAME(rset.getString("RELEASE_NAME"));
				td.setRELEASE_CYCLE(rset.getString("RELEASE_CYCLE"));
				td.setO_LAST_EXECUTION_DATE(rset.getString("O_LAST_EXECUTION_DATE"));
	
				td.setO_LAST_EXECUTED_BY(rset.getString("O_LAST_EXECUTED_BY"));

				td.setO_LAST_EXECUTION_STATUS(rset.getString("O_LAST_EXECUTION_STATUS"));

				td.setO_LAST_EXECUTION_DURATION(rset.getString("O_LAST_EXECUTION_DURATION"));
				td.setO_EXECUTION_ERROR_MESSAGES(rset.getString("O_EXECUTION_ERROR_MESSAGES"));
			
				
				
				
				
				
				td.setI_URL(rset.getString("I_URL"));
				td.setI_LOGINUSER_NAME(rset.getString("I_LOGINUSER_NAME"));
				td.setI_LOGINUSER_PASSWORD(rset.getString("I_LOGINUSER_PASSWORD"));
			
				if (null ==rset.getString("I_PORTLET_VALUE1")){
					td.setI_PORTLET_VALUE1("");
				}else{
					td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				}
				
				
				
				
				
				td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				td.setI_PORTLET_VALUE2(rset.getString("I_PORTLET_VALUE2"));
				td.setI_PORTLET_VALUE3(rset.getString("I_PORTLET_VALUE3"));
				td.setI_PORTLET_VALUE4(rset.getString("I_PORTLET_VALUE4"));
				td.setI_PORTLET_VALUE5(rset.getString("I_PORTLET_VALUE5"));
				td.setI_PORTLET_VALUE6(rset.getString("I_PORTLET_VALUE6"));
				td.setI_PORTLET_VALUE7(rset.getString("I_PORTLET_VALUE7"));
				td.setI_PORTLET_VALUE8(rset.getString("I_PORTLET_VALUE8"));
				td.setI_PORTLET_VALUE9(rset.getString("I_PORTLET_VALUE9"));
				td.setI_PORTLET_VALUE10(rset.getString("I_PORTLET_VALUE10"));
				
				td.setI_PORTLET_VALUE11(rset.getString("I_PORTLET_VALUE11"));
				td.setI_PORTLET_VALUE12(rset.getString("I_PORTLET_VALUE12"));
				td.setI_PORTLET_VALUE13(rset.getString("I_PORTLET_VALUE13"));
				td.setI_PORTLET_VALUE14(rset.getString("I_PORTLET_VALUE14"));
				td.setI_PORTLET_VALUE15(rset.getString("I_PORTLET_VALUE15"));
				td.setI_PORTLET_VALUE16(rset.getString("I_PORTLET_VALUE16"));
				td.setI_PORTLET_VALUE17(rset.getString("I_PORTLET_VALUE17"));
				td.setI_PORTLET_VALUE18(rset.getString("I_PORTLET_VALUE18"));
				td.setI_PORTLET_VALUE19(rset.getString("I_PORTLET_VALUE19"));
				td.setI_PORTLET_VALUE20(rset.getString("I_PORTLET_VALUE20"));
				
				td.setI_PORTLET_VALUE21(rset.getString("I_PORTLET_VALUE21"));
				td.setI_PORTLET_VALUE22(rset.getString("I_PORTLET_VALUE22"));
				td.setI_PORTLET_VALUE23(rset.getString("I_PORTLET_VALUE23"));
				td.setI_PORTLET_VALUE24(rset.getString("I_PORTLET_VALUE24"));
				td.setI_PORTLET_VALUE25(rset.getString("I_PORTLET_VALUE25"));
				td.setI_PORTLET_VALUE26(rset.getString("I_PORTLET_VALUE26"));
				td.setI_PORTLET_VALUE27(rset.getString("I_PORTLET_VALUE27"));
				td.setI_PORTLET_VALUE28(rset.getString("I_PORTLET_VALUE28"));
				td.setI_PORTLET_VALUE29(rset.getString("I_PORTLET_VALUE29"));
				td.setI_PORTLET_VALUE30(rset.getString("I_PORTLET_VALUE30"));
				
				td.setI_PORTLET_VALUE31(rset.getString("I_PORTLET_VALUE31"));
				td.setI_PORTLET_VALUE32(rset.getString("I_PORTLET_VALUE32"));
				td.setI_PORTLET_VALUE33(rset.getString("I_PORTLET_VALUE33"));
				td.setI_PORTLET_VALUE34(rset.getString("I_PORTLET_VALUE34"));
				
				td.setI_PORTLET_VALUE35(rset.getString("I_PORTLET_VALUE35"));
				td.setI_PORTLET_VALUE36(rset.getString("I_PORTLET_VALUE36"));
				td.setI_PORTLET_VALUE37(rset.getString("I_PORTLET_VALUE37"));
				td.setI_PORTLET_VALUE38(rset.getString("I_PORTLET_VALUE38"));
				td.setI_PORTLET_VALUE39(rset.getString("I_PORTLET_VALUE39"));
				td.setI_PORTLET_VALUE40(rset.getString("I_PORTLET_VALUE40"));
				td.setI_PORTLET_VALUE41(rset.getString("I_PORTLET_VALUE41"));
				td.setI_PORTLET_VALUE42(rset.getString("I_PORTLET_VALUE42"));
				td.setI_PORTLET_VALUE43(rset.getString("I_PORTLET_VALUE43"));
				td.setI_PORTLET_VALUE44(rset.getString("I_PORTLET_VALUE44"));
				
				td.setI_PORTLET_VALUE45(rset.getString("I_PORTLET_VALUE45"));
				td.setI_PORTLET_VALUE46(rset.getString("I_PORTLET_VALUE46"));
				td.setI_PORTLET_VALUE47(rset.getString("I_PORTLET_VALUE47"));
				td.setI_PORTLET_VALUE48(rset.getString("I_PORTLET_VALUE48"));
				
				td.setI_PORTLET_VALUE49(rset.getString("I_PORTLET_VALUE49"));
				
				td.setI_PORTLET_VALUE50(rset.getString("I_PORTLET_VALUE50"));
				td.setI_PORTLET_VALUE51(rset.getString("I_PORTLET_VALUE51"));
				td.setI_PORTLET_VALUE52(rset.getString("I_PORTLET_VALUE52"));
				td.setI_PORTLET_VALUE53(rset.getString("I_PORTLET_VALUE53"));
				td.setI_PORTLET_VALUE54(rset.getString("I_PORTLET_VALUE54"));
				td.setI_PORTLET_VALUE55(rset.getString("I_PORTLET_VALUE55"));
				td.setI_PORTLET_VALUE56(rset.getString("I_PORTLET_VALUE56"));
				td.setI_PORTLET_VALUE57(rset.getString("I_PORTLET_VALUE57"));
				td.setI_PORTLET_VALUE58(rset.getString("I_PORTLET_VALUE58"));
				td.setI_PORTLET_VALUE59(rset.getString("I_PORTLET_VALUE59"));
				
				td.setI_PORTLET_VALUE60(rset.getString("I_PORTLET_VALUE60"));
				td.setI_PORTLET_VALUE61(rset.getString("I_PORTLET_VALUE61"));
				td.setI_PORTLET_VALUE62(rset.getString("I_PORTLET_VALUE62"));
				td.setI_PORTLET_VALUE63(rset.getString("I_PORTLET_VALUE63"));
				td.setI_PORTLET_VALUE64(rset.getString("I_PORTLET_VALUE64"));
				td.setI_PORTLET_VALUE65(rset.getString("I_PORTLET_VALUE65"));
				td.setI_PORTLET_VALUE66(rset.getString("I_PORTLET_VALUE66"));
				td.setI_PORTLET_VALUE67(rset.getString("I_PORTLET_VALUE67"));
				td.setI_PORTLET_VALUE68(rset.getString("I_PORTLET_VALUE68"));
				td.setI_PORTLET_VALUE69(rset.getString("I_PORTLET_VALUE69"));
				
				td.setI_PORTLET_VALUE70(rset.getString("I_PORTLET_VALUE70"));
				td.setI_PORTLET_VALUE71(rset.getString("I_PORTLET_VALUE71"));
				td.setI_PORTLET_VALUE72(rset.getString("I_PORTLET_VALUE72"));
				td.setI_PORTLET_VALUE73(rset.getString("I_PORTLET_VALUE73"));
				td.setI_PORTLET_VALUE74(rset.getString("I_PORTLET_VALUE74"));
				td.setI_PORTLET_VALUE75(rset.getString("I_PORTLET_VALUE75"));
				td.setI_PORTLET_VALUE76(rset.getString("I_PORTLET_VALUE76"));
				td.setI_PORTLET_VALUE77(rset.getString("I_PORTLET_VALUE77"));
				td.setI_PORTLET_VALUE78(rset.getString("I_PORTLET_VALUE78"));
				td.setI_PORTLET_VALUE79(rset.getString("I_PORTLET_VALUE79"));
				
				td.setI_PORTLET_VALUE80(rset.getString("I_PORTLET_VALUE80"));
				
				
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("Size inside : "+testDataList.size());
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}

		return testDataList;
	}
	
	@SuppressWarnings("unused")
	public ArrayList<TopicSearchTestData> getDbData(String TEST_PHASE, Connection con){
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();
		
		try {  
			String query = null;
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(TEST_PHASE.equalsIgnoreCase("Reg_Fun")||TEST_PHASE.equalsIgnoreCase("Fun_Reg")){
				String[] phase = TEST_PHASE.split("_");
				query ="select TEST_SCRIPT_NAME from CITS_CC_FS_EF_TESTDATA where TEST_PHASE='"+phase[0]+"'OR TEST_PHASE='"+phase[1]+"'";
				
			}
			else if(TEST_PHASE.equalsIgnoreCase("Active")){
			query ="select TEST_SCRIPT_NAME from CITS_CC_FS_EF_TESTDATA where EXECUTION='Y'";
				
			}else{
			query ="select TEST_SCRIPT_NAME from CITS_CC_FS_EF_TESTDATA where TEST_PHASE='"+TEST_PHASE+"'";
			}
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				
			
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("Size inside : "+testDataList.size());
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}

		return testDataList;
	}


	@SuppressWarnings("unused")
	public void updateStatus(String ExecutedBy, String TestScriptName,boolean status,String ExecutionDateTime,String ExecutionDuration){
		try{
			con = db.getConnection();
			if(null!=con){
			Statement stmt = con.createStatement();
			ResultSet rset3 = stmt.executeQuery("Update  CITS_CC_FS_TOPICSRCH_TESTDATA SET O_LAST_EXECUTED_BY= '"+ExecutedBy+"', O_LAST_EXECUTION_STATUS='"+status+"', O_LAST_EXECUTION_DATE = '"+ExecutionDateTime+"',O_LAST_EXECUTION_DURATION='Total Exeution Time : "+ExecutionDuration+"' where TEST_SCRIPT_NAME ='"+TestScriptName+"'");
			ResultSet rset4 = stmt.executeQuery("commit");
			System.out.println("Successfully updated the test status");
			rset4.close();
			}
		}
		catch (Exception e) 
		{
			System.out.println("Failed to Update status of the test");
			System.out.println(e);
			e.printStackTrace();
		}
		finally{
			try {
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
*/

package resources.utilFiles;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class DBAutomationFunction {
	
	private DBAutomationConnection db = new DBAutomationConnection();
	Connection con = null;

           

	public Object[][] getDBArray(String TestScript_Name, Connection con) throws Exception{
		Object[][] tabArray= null;

		try {
			//global declaration
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+TestScript_Name+"'";
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rset.last();
			rowcount = rset.getRow();
			rset.first();

			int i=0;
			tabArray= new String[rowcount][colcount];
			for (i=0;i<rowcount;i++)
			{
				for (int j=0;j<colcount ;j++)
				{
					tabArray[i][j]=rset.getString(j+1);
					System.out.println("i="+i+"\tj="+j+"\tval="+tabArray[i][j]);
				}
				rset.next();
			}
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return tabArray;
	}

	@SuppressWarnings("unused")
	public List<TopicSearchTestData> getDBArray_LocalDB(String Data, Connection con) throws Exception{
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();
		int colcount = 0;
		int rowcount = 0;
		try {
			//global declaration
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+Data+"'";
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			colcount = rsmd.getColumnCount();
			

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				if (null ==rset.getString("TOPICSRCH_DATA_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_DATA_ID"));
				}
				if (null ==rset.getString("TOPICSRCH_TEST_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_TEST_ID"));
				}
				
				if (null ==rset.getString("PORTFOLIO")){
					td.setPORTFOLIO("");
				}else{
					td.setPORTFOLIO(rset.getString("PORTFOLIO"));
				}
				
				if (null ==rset.getString("FLOW")){
					td.setFLOW("");
				}else{
					td.setFLOW(rset.getString("FLOW"));
				}
				
				if (null ==rset.getString("APPLICATION_TRACK")){
					td.setAPPLICATION_TRACK("");
				}else{
					td.setAPPLICATION_TRACK(rset.getString("APPLICATION_TRACK"));
				}
				
				if (null ==rset.getString("SUB_TRACK")){
					td.setSUB_TRACK("");
				}else{
					td.setSUB_TRACK(rset.getString("SUB_TRACK"));
				}
				
				if (null ==rset.getString("TEST_CASE_ID")){
					td.setTEST_CASE_ID("");
				}else{
					td.setTEST_CASE_ID(rset.getString("TEST_CASE_ID"));
				}
				
				
				
				if (null ==rset.getString("EXECUTION")){
					td.setEXECUTION("");
				}else{
					td.setEXECUTION(rset.getString("EXECUTION"));
				}
				
				if (null ==rset.getString("TESTSCRIPT_STATUS")){
					td.setTESTSCRIPT_STATUS("");
				}else{
					td.setTESTSCRIPT_STATUS(rset.getString("TESTSCRIPT_STATUS"));
				}
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				if (null ==rset.getString("TEST_METHOD_NAME")){
					td.setTEST_METHOD_NAME("");
				}else{
					td.setTEST_METHOD_NAME(rset.getString("TEST_METHOD_NAME"));
				}
				
				if (null ==rset.getString("BROWSER_TYPE")){
					td.setBROWSER_TYPE("");
				}else{
					td.setBROWSER_TYPE(rset.getString("BROWSER_TYPE"));
				}
				
				
				
				td.setRELEASE_NAME(rset.getString("RELEASE_NAME"));
				td.setRELEASE_CYCLE(rset.getString("RELEASE_CYCLE"));
				td.setO_LAST_EXECUTION_DATE(rset.getString("O_LAST_EXECUTION_DATE"));
	
				td.setO_LAST_EXECUTED_BY(rset.getString("O_LAST_EXECUTED_BY"));

				td.setO_LAST_EXECUTION_STATUS(rset.getString("O_LAST_EXECUTION_STATUS"));

				td.setO_LAST_EXECUTION_DURATION(rset.getString("O_LAST_EXECUTION_DURATION"));
				td.setO_EXECUTION_ERROR_MESSAGES(rset.getString("O_EXECUTION_ERROR_MESSAGES"));
			
				
				
				
				
				
				td.setI_URL(rset.getString("I_URL"));
				td.setI_LOGINUSER_NAME(rset.getString("I_LOGINUSER_NAME"));
				td.setI_LOGINUSER_PASSWORD(rset.getString("I_LOGINUSER_PASSWORD"));
			
				if (null ==rset.getString("I_PORTLET_VALUE1")){
					td.setI_PORTLET_VALUE1("");
				}else{
					td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				}
				
				
				
				
				
				/*td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				td.setI_PORTLET_VALUE2(rset.getString("I_PORTLET_VALUE2"));
				td.setI_PORTLET_VALUE3(rset.getString("I_PORTLET_VALUE3"));
				td.setI_PORTLET_VALUE4(rset.getString("I_PORTLET_VALUE4"));
				td.setI_PORTLET_VALUE5(rset.getString("I_PORTLET_VALUE5"));
				td.setI_PORTLET_VALUE6(rset.getString("I_PORTLET_VALUE6"));
				td.setI_PORTLET_VALUE7(rset.getString("I_PORTLET_VALUE7"));
				td.setI_PORTLET_VALUE8(rset.getString("I_PORTLET_VALUE8"));
				td.setI_PORTLET_VALUE9(rset.getString("I_PORTLET_VALUE9"));
				td.setI_PORTLET_VALUE10(rset.getString("I_PORTLET_VALUE10"));
				
				td.setI_PORTLET_VALUE11(rset.getString("I_PORTLET_VALUE11"));
				td.setI_PORTLET_VALUE12(rset.getString("I_PORTLET_VALUE12"));
				td.setI_PORTLET_VALUE13(rset.getString("I_PORTLET_VALUE13"));
				td.setI_PORTLET_VALUE14(rset.getString("I_PORTLET_VALUE14"));
				td.setI_PORTLET_VALUE15(rset.getString("I_PORTLET_VALUE15"));
				td.setI_PORTLET_VALUE16(rset.getString("I_PORTLET_VALUE16"));
				td.setI_PORTLET_VALUE17(rset.getString("I_PORTLET_VALUE17"));
				td.setI_PORTLET_VALUE18(rset.getString("I_PORTLET_VALUE18"));
				td.setI_PORTLET_VALUE19(rset.getString("I_PORTLET_VALUE19"));
				td.setI_PORTLET_VALUE20(rset.getString("I_PORTLET_VALUE20"));
				
				td.setI_PORTLET_VALUE21(rset.getString("I_PORTLET_VALUE21"));
				td.setI_PORTLET_VALUE22(rset.getString("I_PORTLET_VALUE22"));
				td.setI_PORTLET_VALUE23(rset.getString("I_PORTLET_VALUE23"));
				td.setI_PORTLET_VALUE24(rset.getString("I_PORTLET_VALUE24"));
				td.setI_PORTLET_VALUE25(rset.getString("I_PORTLET_VALUE25"));
				td.setI_PORTLET_VALUE26(rset.getString("I_PORTLET_VALUE26"));
				td.setI_PORTLET_VALUE27(rset.getString("I_PORTLET_VALUE27"));
				td.setI_PORTLET_VALUE28(rset.getString("I_PORTLET_VALUE28"));
				td.setI_PORTLET_VALUE29(rset.getString("I_PORTLET_VALUE29"));
				td.setI_PORTLET_VALUE30(rset.getString("I_PORTLET_VALUE30"));
				
				td.setI_PORTLET_VALUE31(rset.getString("I_PORTLET_VALUE31"));
				td.setI_PORTLET_VALUE32(rset.getString("I_PORTLET_VALUE32"));
				td.setI_PORTLET_VALUE33(rset.getString("I_PORTLET_VALUE33"));
				td.setI_PORTLET_VALUE34(rset.getString("I_PORTLET_VALUE34"));
				
				td.setI_PORTLET_VALUE35(rset.getString("I_PORTLET_VALUE35"));
				td.setI_PORTLET_VALUE36(rset.getString("I_PORTLET_VALUE36"));
				td.setI_PORTLET_VALUE37(rset.getString("I_PORTLET_VALUE37"));
				td.setI_PORTLET_VALUE38(rset.getString("I_PORTLET_VALUE38"));
				td.setI_PORTLET_VALUE39(rset.getString("I_PORTLET_VALUE39"));
				td.setI_PORTLET_VALUE40(rset.getString("I_PORTLET_VALUE40"));
				td.setI_PORTLET_VALUE41(rset.getString("I_PORTLET_VALUE41"));
				td.setI_PORTLET_VALUE42(rset.getString("I_PORTLET_VALUE42"));
				td.setI_PORTLET_VALUE43(rset.getString("I_PORTLET_VALUE43"));
				td.setI_PORTLET_VALUE44(rset.getString("I_PORTLET_VALUE44"));
				
				td.setI_PORTLET_VALUE45(rset.getString("I_PORTLET_VALUE45"));
				td.setI_PORTLET_VALUE46(rset.getString("I_PORTLET_VALUE46"));
				td.setI_PORTLET_VALUE47(rset.getString("I_PORTLET_VALUE47"));
				td.setI_PORTLET_VALUE48(rset.getString("I_PORTLET_VALUE48"));
				
				td.setI_PORTLET_VALUE49(rset.getString("I_PORTLET_VALUE49"));
				
				td.setI_PORTLET_VALUE50(rset.getString("I_PORTLET_VALUE50"));
				td.setI_PORTLET_VALUE51(rset.getString("I_PORTLET_VALUE51"));
				td.setI_PORTLET_VALUE52(rset.getString("I_PORTLET_VALUE52"));
				td.setI_PORTLET_VALUE53(rset.getString("I_PORTLET_VALUE53"));
				td.setI_PORTLET_VALUE54(rset.getString("I_PORTLET_VALUE54"));
				td.setI_PORTLET_VALUE55(rset.getString("I_PORTLET_VALUE55"));
				td.setI_PORTLET_VALUE56(rset.getString("I_PORTLET_VALUE56"));
				td.setI_PORTLET_VALUE57(rset.getString("I_PORTLET_VALUE57"));
				td.setI_PORTLET_VALUE58(rset.getString("I_PORTLET_VALUE58"));
				td.setI_PORTLET_VALUE59(rset.getString("I_PORTLET_VALUE59"));
				
				td.setI_PORTLET_VALUE60(rset.getString("I_PORTLET_VALUE60"));
				td.setI_PORTLET_VALUE61(rset.getString("I_PORTLET_VALUE61"));
				td.setI_PORTLET_VALUE62(rset.getString("I_PORTLET_VALUE62"));
				td.setI_PORTLET_VALUE63(rset.getString("I_PORTLET_VALUE63"));
				td.setI_PORTLET_VALUE64(rset.getString("I_PORTLET_VALUE64"));
				td.setI_PORTLET_VALUE65(rset.getString("I_PORTLET_VALUE65"));
				td.setI_PORTLET_VALUE66(rset.getString("I_PORTLET_VALUE66"));
				td.setI_PORTLET_VALUE67(rset.getString("I_PORTLET_VALUE67"));
				td.setI_PORTLET_VALUE68(rset.getString("I_PORTLET_VALUE68"));
				td.setI_PORTLET_VALUE69(rset.getString("I_PORTLET_VALUE69"));
				
				td.setI_PORTLET_VALUE70(rset.getString("I_PORTLET_VALUE70"));
				td.setI_PORTLET_VALUE71(rset.getString("I_PORTLET_VALUE71"));
				td.setI_PORTLET_VALUE72(rset.getString("I_PORTLET_VALUE72"));
				td.setI_PORTLET_VALUE73(rset.getString("I_PORTLET_VALUE73"));
				td.setI_PORTLET_VALUE74(rset.getString("I_PORTLET_VALUE74"));
				td.setI_PORTLET_VALUE75(rset.getString("I_PORTLET_VALUE75"));
				td.setI_PORTLET_VALUE76(rset.getString("I_PORTLET_VALUE76"));
				td.setI_PORTLET_VALUE77(rset.getString("I_PORTLET_VALUE77"));
				td.setI_PORTLET_VALUE78(rset.getString("I_PORTLET_VALUE78"));
				td.setI_PORTLET_VALUE79(rset.getString("I_PORTLET_VALUE79"));
				
				td.setI_PORTLET_VALUE80(rset.getString("I_PORTLET_VALUE80"));
				*/
				
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("No of test cases that will be executed is : "+testDataList.size());
			rset.close();                 
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return testDataList;
	}




	@SuppressWarnings("unused")
	public List<TopicSearchTestData> getDbData(String TestScrtipt_Name,String TEST_PHASE,String EXECUTION,Connection con){
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();

		try {                                        
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query ="select * from CITS_CC_FS_TOPICSRCH_TESTDATA where TEST_SCRIPT_NAME='"+TestScrtipt_Name+"'AND TEST_PHASE='"+TEST_PHASE+"'AND EXECUTION_LOCAL='"+EXECUTION+"'";
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				if (null ==rset.getString("TOPICSRCH_DATA_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_DATA_ID"));
				}
				if (null ==rset.getString("TOPICSRCH_TEST_ID")){
					td.setTOPICSRCH_DATA_ID("");
				}else{
					td.setTOPICSRCH_DATA_ID(rset.getString("TOPICSRCH_TEST_ID"));
				}
				
				if (null ==rset.getString("PORTFOLIO")){
					td.setPORTFOLIO("");
				}else{
					td.setPORTFOLIO(rset.getString("PORTFOLIO"));
				}
				
				if (null ==rset.getString("FLOW")){
					td.setFLOW("");
				}else{
					td.setFLOW(rset.getString("FLOW"));
				}
				
				if (null ==rset.getString("APPLICATION_TRACK")){
					td.setAPPLICATION_TRACK("");
				}else{
					td.setAPPLICATION_TRACK(rset.getString("APPLICATION_TRACK"));
				}
				
				if (null ==rset.getString("SUB_TRACK")){
					td.setSUB_TRACK("");
				}else{
					td.setSUB_TRACK(rset.getString("SUB_TRACK"));
				}
				
				if (null ==rset.getString("TEST_CASE_ID")){
					td.setTEST_CASE_ID("");
				}else{
					td.setTEST_CASE_ID(rset.getString("TEST_CASE_ID"));
				}
				
				
				
				if (null ==rset.getString("EXECUTION")){
					td.setEXECUTION("");
				}else{
					td.setEXECUTION(rset.getString("EXECUTION"));
				}
				
				if (null ==rset.getString("TESTSCRIPT_STATUS")){
					td.setTESTSCRIPT_STATUS("");
				}else{
					td.setTESTSCRIPT_STATUS(rset.getString("TESTSCRIPT_STATUS"));
				}
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				if (null ==rset.getString("TEST_METHOD_NAME")){
					td.setTEST_METHOD_NAME("");
				}else{
					td.setTEST_METHOD_NAME(rset.getString("TEST_METHOD_NAME"));
				}
				
				if (null ==rset.getString("BROWSER_TYPE")){
					td.setBROWSER_TYPE("");
				}else{
					td.setBROWSER_TYPE(rset.getString("BROWSER_TYPE"));
				}
				if (null ==rset.getString("BROWSER_Version")){
					td.setBROWSER_Version("");
				}else{
					td.setBROWSER_Version(rset.getString("BROWSER_Version"));
				}
				if (null ==rset.getString("OS_TYPE")){
					td.setOS_TYPE("");
				}else{
					td.setOS_TYPE(rset.getString("OS_TYPE"));
				}
				if (null ==rset.getString("TEST_PHASE")){
					td.setTEST_PHASE("");
				}else{
					td.setTEST_PHASE(rset.getString("TEST_PHASE"));
				}
				
				td.setRELEASE_NAME(rset.getString("RELEASE_NAME"));
				td.setRELEASE_CYCLE(rset.getString("RELEASE_CYCLE"));
				td.setO_LAST_EXECUTION_DATE(rset.getString("O_LAST_EXECUTION_DATE"));
	
				td.setO_LAST_EXECUTED_BY(rset.getString("O_LAST_EXECUTED_BY"));

				td.setO_LAST_EXECUTION_STATUS(rset.getString("O_LAST_EXECUTION_STATUS"));

				td.setO_LAST_EXECUTION_DURATION(rset.getString("O_LAST_EXECUTION_DURATION"));
				td.setO_EXECUTION_ERROR_MESSAGES(rset.getString("O_EXECUTION_ERROR_MESSAGES"));
			
				
				
				
				
				
				td.setI_URL(rset.getString("I_URL"));
				td.setI_LOGINUSER_NAME(rset.getString("I_LOGINUSER_NAME"));
				td.setI_LOGINUSER_PASSWORD(rset.getString("I_LOGINUSER_PASSWORD"));
			
				if (null ==rset.getString("I_PORTLET_VALUE1")){
					td.setI_PORTLET_VALUE1("");
				}else{
					td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				}
				
				
				
				
				
				td.setI_PORTLET_VALUE1(rset.getString("I_PORTLET_VALUE1"));
				td.setI_PORTLET_VALUE2(rset.getString("I_PORTLET_VALUE2"));
				td.setI_PORTLET_VALUE3(rset.getString("I_PORTLET_VALUE3"));
				td.setI_PORTLET_VALUE4(rset.getString("I_PORTLET_VALUE4"));
				td.setI_PORTLET_VALUE5(rset.getString("I_PORTLET_VALUE5"));
				td.setI_PORTLET_VALUE6(rset.getString("I_PORTLET_VALUE6"));
				td.setI_PORTLET_VALUE7(rset.getString("I_PORTLET_VALUE7"));
				td.setI_PORTLET_VALUE8(rset.getString("I_PORTLET_VALUE8"));
				td.setI_PORTLET_VALUE9(rset.getString("I_PORTLET_VALUE9"));
				td.setI_PORTLET_VALUE10(rset.getString("I_PORTLET_VALUE10"));
				
				td.setI_PORTLET_VALUE11(rset.getString("I_PORTLET_VALUE11"));
				td.setI_PORTLET_VALUE12(rset.getString("I_PORTLET_VALUE12"));
				td.setI_PORTLET_VALUE13(rset.getString("I_PORTLET_VALUE13"));
				td.setI_PORTLET_VALUE14(rset.getString("I_PORTLET_VALUE14"));
				td.setI_PORTLET_VALUE15(rset.getString("I_PORTLET_VALUE15"));
				td.setI_PORTLET_VALUE16(rset.getString("I_PORTLET_VALUE16"));
				td.setI_PORTLET_VALUE17(rset.getString("I_PORTLET_VALUE17"));
				td.setI_PORTLET_VALUE18(rset.getString("I_PORTLET_VALUE18"));
				td.setI_PORTLET_VALUE19(rset.getString("I_PORTLET_VALUE19"));
				td.setI_PORTLET_VALUE20(rset.getString("I_PORTLET_VALUE20"));
				
				td.setI_PORTLET_VALUE21(rset.getString("I_PORTLET_VALUE21"));
				td.setI_PORTLET_VALUE22(rset.getString("I_PORTLET_VALUE22"));
				td.setI_PORTLET_VALUE23(rset.getString("I_PORTLET_VALUE23"));
				td.setI_PORTLET_VALUE24(rset.getString("I_PORTLET_VALUE24"));
				td.setI_PORTLET_VALUE25(rset.getString("I_PORTLET_VALUE25"));
				td.setI_PORTLET_VALUE26(rset.getString("I_PORTLET_VALUE26"));
				td.setI_PORTLET_VALUE27(rset.getString("I_PORTLET_VALUE27"));
				td.setI_PORTLET_VALUE28(rset.getString("I_PORTLET_VALUE28"));
				td.setI_PORTLET_VALUE29(rset.getString("I_PORTLET_VALUE29"));
				td.setI_PORTLET_VALUE30(rset.getString("I_PORTLET_VALUE30"));
				
				td.setI_PORTLET_VALUE31(rset.getString("I_PORTLET_VALUE31"));
				td.setI_PORTLET_VALUE32(rset.getString("I_PORTLET_VALUE32"));
				td.setI_PORTLET_VALUE33(rset.getString("I_PORTLET_VALUE33"));
				td.setI_PORTLET_VALUE34(rset.getString("I_PORTLET_VALUE34"));
				
				td.setI_PORTLET_VALUE35(rset.getString("I_PORTLET_VALUE35"));
				td.setI_PORTLET_VALUE36(rset.getString("I_PORTLET_VALUE36"));
				td.setI_PORTLET_VALUE37(rset.getString("I_PORTLET_VALUE37"));
				td.setI_PORTLET_VALUE38(rset.getString("I_PORTLET_VALUE38"));
				td.setI_PORTLET_VALUE39(rset.getString("I_PORTLET_VALUE39"));
				td.setI_PORTLET_VALUE40(rset.getString("I_PORTLET_VALUE40"));
				td.setI_PORTLET_VALUE41(rset.getString("I_PORTLET_VALUE41"));
				td.setI_PORTLET_VALUE42(rset.getString("I_PORTLET_VALUE42"));
				td.setI_PORTLET_VALUE43(rset.getString("I_PORTLET_VALUE43"));
				td.setI_PORTLET_VALUE44(rset.getString("I_PORTLET_VALUE44"));
				
				td.setI_PORTLET_VALUE45(rset.getString("I_PORTLET_VALUE45"));
				td.setI_PORTLET_VALUE46(rset.getString("I_PORTLET_VALUE46"));
				td.setI_PORTLET_VALUE47(rset.getString("I_PORTLET_VALUE47"));
				td.setI_PORTLET_VALUE48(rset.getString("I_PORTLET_VALUE48"));
				
				td.setI_PORTLET_VALUE49(rset.getString("I_PORTLET_VALUE49"));
				
				td.setI_PORTLET_VALUE50(rset.getString("I_PORTLET_VALUE50"));
				td.setI_PORTLET_VALUE51(rset.getString("I_PORTLET_VALUE51"));
				td.setI_PORTLET_VALUE52(rset.getString("I_PORTLET_VALUE52"));
				td.setI_PORTLET_VALUE53(rset.getString("I_PORTLET_VALUE53"));
				td.setI_PORTLET_VALUE54(rset.getString("I_PORTLET_VALUE54"));
				td.setI_PORTLET_VALUE55(rset.getString("I_PORTLET_VALUE55"));
				td.setI_PORTLET_VALUE56(rset.getString("I_PORTLET_VALUE56"));
				td.setI_PORTLET_VALUE57(rset.getString("I_PORTLET_VALUE57"));
				td.setI_PORTLET_VALUE58(rset.getString("I_PORTLET_VALUE58"));
				td.setI_PORTLET_VALUE59(rset.getString("I_PORTLET_VALUE59"));
				
				td.setI_PORTLET_VALUE60(rset.getString("I_PORTLET_VALUE60"));
				td.setI_PORTLET_VALUE61(rset.getString("I_PORTLET_VALUE61"));
				td.setI_PORTLET_VALUE62(rset.getString("I_PORTLET_VALUE62"));
				td.setI_PORTLET_VALUE63(rset.getString("I_PORTLET_VALUE63"));
				td.setI_PORTLET_VALUE64(rset.getString("I_PORTLET_VALUE64"));
				td.setI_PORTLET_VALUE65(rset.getString("I_PORTLET_VALUE65"));
				td.setI_PORTLET_VALUE66(rset.getString("I_PORTLET_VALUE66"));
				td.setI_PORTLET_VALUE67(rset.getString("I_PORTLET_VALUE67"));
				td.setI_PORTLET_VALUE68(rset.getString("I_PORTLET_VALUE68"));
				td.setI_PORTLET_VALUE69(rset.getString("I_PORTLET_VALUE69"));
				
				td.setI_PORTLET_VALUE70(rset.getString("I_PORTLET_VALUE70"));
				td.setI_PORTLET_VALUE71(rset.getString("I_PORTLET_VALUE71"));
				td.setI_PORTLET_VALUE72(rset.getString("I_PORTLET_VALUE72"));
				td.setI_PORTLET_VALUE73(rset.getString("I_PORTLET_VALUE73"));
				td.setI_PORTLET_VALUE74(rset.getString("I_PORTLET_VALUE74"));
				td.setI_PORTLET_VALUE75(rset.getString("I_PORTLET_VALUE75"));
				td.setI_PORTLET_VALUE76(rset.getString("I_PORTLET_VALUE76"));
				td.setI_PORTLET_VALUE77(rset.getString("I_PORTLET_VALUE77"));
				td.setI_PORTLET_VALUE78(rset.getString("I_PORTLET_VALUE78"));
				td.setI_PORTLET_VALUE79(rset.getString("I_PORTLET_VALUE79"));
				
				td.setI_PORTLET_VALUE80(rset.getString("I_PORTLET_VALUE80"));
				
				
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("No of test cases that will be executed is : "+testDataList.size());
			if(testDataList.size()==0){
				return null;
			}
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}

		return testDataList;
	}
	
	@SuppressWarnings("unused")
	public ArrayList<TopicSearchTestData> getDbData(String TEST_PHASE, Connection con){
		ArrayList<TopicSearchTestData> testDataList = new ArrayList<TopicSearchTestData>();
		
		try {  
			String query = null;
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(TEST_PHASE.equalsIgnoreCase("REG_FUN")||TEST_PHASE.equalsIgnoreCase("FUN_REG")){
				String[] phase = TEST_PHASE.split("_");
				query ="select TEST_SCRIPT_NAME from CITS_CC_FS_TOPICSRCH_TESTDATA where EXECUTION_LOCAL = 'Y' AND TEST_PHASE='"+phase[0]+"'OR TEST_PHASE='"+phase[1]+"'";
				
			}
			else if(TEST_PHASE.equalsIgnoreCase("Active")){
			query ="select TEST_SCRIPT_NAME from CITS_CC_FS_TOPICSRCH_TESTDATA where EXECUTION_LOCAL='Y'";
				
			}else{
			query ="select TEST_SCRIPT_NAME from CITS_CC_FS_TOPICSRCH_TESTDATA where EXECUTION_LOCAL='Y' AND TEST_PHASE='"+TEST_PHASE+"'";
			}
			System.out.println(query);
			ResultSet rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int colcount = rsmd.getColumnCount();
			int rowcount = 0;

			rowcount = rset.getRow();
			//rset.first();

			//int i=0;
			while(rset.next()){
				TopicSearchTestData td = new TopicSearchTestData();
				
				
				if (null ==rset.getString("TEST_SCRIPT_NAME")){
					td.setTEST_SCRIPT_NAME("");
				}else{
					td.setTEST_SCRIPT_NAME(rset.getString("TEST_SCRIPT_NAME"));
				}
				
				
			
				
				
				testDataList.add(td);  // adding item to list

			}
			System.out.println("No of test cases that will be executed is : "+testDataList.size());
			rset.close();           
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}

		return testDataList;
	}

	@SuppressWarnings("unused")
	public void updateStatus(String ExecutedBy, String TestScriptName,boolean status,String ExecutionDateTime,String ExecutionDuration){
		try{
			con = db.getConnection();
			if(null!=con){
			Statement stmt = con.createStatement();
			ResultSet rset3 = stmt.executeQuery("Update  CITS_CC_FS_TOPICSRCH_TESTDATA SET O_LAST_EXECUTED_BY= '"+ExecutedBy+"', O_LAST_EXECUTION_STATUS='"+status+"', O_LAST_EXECUTION_DATE = '"+ExecutionDateTime+"',O_LAST_EXECUTION_DURATION='Total Exeution Time : "+ExecutionDuration+"' where TEST_SCRIPT_NAME ='"+TestScriptName+"' AND EXECUTION='Y'");
			ResultSet rset4 = stmt.executeQuery("commit");
			System.out.println("Successfully updated the test status");
			rset4.close();
			}
		}
		catch (Exception e) 
		{
			System.out.println("Failed to Update status of the test");
			System.out.println(e);
			e.printStackTrace();
		}
		finally{
			try {
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
