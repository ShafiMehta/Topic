package resources.utilFiles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBAutomationConnection {
	DB_config db_config = new DB_config();

	@SuppressWarnings({ "static-access", "unused" })
	public Connection getConnection() throws IOException {

		DB_config db_config = new DB_config();

		String i_DBUser = db_config.i_DBUser;
		String i_DBPasswd= db_config.i_DBPasswd;
		String i_SID_ServiceName=db_config.i_SID_ServiceName;
		String i_Host=db_config.i_Host;
		String i_Port=db_config.i_Port;
		String i_Error="Either Login Failed or The Order link is not visible";
		Connection con = null;
		String url = "jdbc:oracle:thin:@//"+i_Host+":"+i_Port+"/";
		String db = i_SID_ServiceName;
		String driver = "oracle.jdbc.driver.OracleDriver";      
		String user = i_DBUser;
		String pass = i_DBPasswd;

		try {
		Class.forName(driver); 
		DriverManager.setLoginTimeout(10);
		//  con = DriverManager.getConnection(url+ db, user, pass);
		// con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lnxdb-stg-564.cisco.com)(PORT=1523))(CONNECT_DATA=(SID=PDBQA)(Server=Dedicated)))",user, pass);
		con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lnxdb-dev-vm-233.cisco.com)(PORT=1522))(CONNECT_DATA=(SID=OPSRDEV)(Server=Dedicated)))",user, pass);
		}
		catch (Exception e) {
		System.out.println(e);
		System.out.println(i_Error);
		}
		return con;

		}
	
	public Connection getConnection_LocalDB() throws IOException {

		DB_config db_config = new DB_config();

		String i_DBUser = db_config.i_DBUser_LocalDB;
		String i_DBPasswd= db_config.i_DBPasswd_LocalDB;
		String i_Database= db_config.i_Database_LocalDB;
		String i_SID_ServiceName=db_config.i_SID_ServiceName;
		String i_Host=db_config.i_Host;
		String i_Port=db_config.i_Port;
		String i_Error="Either Login Failed or The Order link is not visible";
		Connection con = null;
		String url = "jdbc:oracle:thin:@//"+i_Host+":"+i_Port+"/";
		String db = i_SID_ServiceName;
		String driver = "oracle.jdbc.driver.OracleDriver";      
		String user = i_DBUser;
		String pass = i_DBPasswd;

		try {
		Class.forName(driver); 
		DriverManager.setLoginTimeout(10);
		//  con = DriverManager.getConnection(url+ db, user, pass);
		// con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lnxdb-stg-564.cisco.com)(PORT=1523))(CONNECT_DATA=(SID=PDBQA)(Server=Dedicated)))",user, pass);
		con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lnxdb-dev-vm-233.cisco.com)(PORT=1522))(CONNECT_DATA=(SID="+i_Database+")(Server=Dedicated)))",user, pass);
		}
		catch (Exception e) {
		System.out.println(e);
		System.out.println(i_Error);
		}
		return con;

		}



		public static void main(String args[]) throws IOException{
		DBAutomationConnection dac = new DBAutomationConnection();
		dac.getConnection();
		}
}