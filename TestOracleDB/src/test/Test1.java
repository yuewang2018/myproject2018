package test;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class Test1 {
	public static String siti_pdb_2_connection_string="jdbc:oracle:thin:@slcas773774-scan.usdv1.oraclecloud.com:1521:idcspdyn2";
	public static String siti_pdb_2_username="sys";
	public static String siti_pdb_2_password="xxxxxxx";
	
	public static String siti_tdb_2_connection_string="jdbc:oracle:thin:@slcs18-scan1.us.oracle.com:1521:syntdb21";
	public static String siti_tdb_2_username="sys";
	public static String siti_tdb_2_password="xxxxxxx";
	
    public static Connection connection = null;
    
    public static void build_connection(String connectionString, String userName, String password)
    {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
        	if (userName.equalsIgnoreCase("sys"))
        		connection = DriverManager.getConnection(connectionString, "sys as sysdba", password);
        	else
        		connection = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection == null)
            System.out.println("Failed to make connection!");
    }
    
	public static void get_tenan_info(String tenant_name) throws SQLException
	{	
		 String query = "select lower(U_VC_TENANT_NAME) as U_VC_TENANT_NAME1,U_VC_STATUS,U_VC_SCHEMA_TYPE,U_VC_SCHEMA_SIZE,U_VC_SCHEMA_NAME,U_VC_SCHEMA_PWD,U_VC_DATABASE_URL from global_idaas.db_schema_map where lower(U_VC_TENANT_NAME)=?";
		 PreparedStatement prepStmt = connection.prepareStatement(query);
		 prepStmt.setString(1, tenant_name.toLowerCase());
	    
		    try {
				ResultSet rs = prepStmt.executeQuery();
	            while (rs.next()) {
		            String U_VC_TENANT_NAME1 = rs.getString("U_VC_TENANT_NAME1");
		            String U_VC_STATUS = rs.getString("U_VC_STATUS");
		            String U_VC_SCHEMA_TYPE = rs.getString("U_VC_SCHEMA_TYPE");
		            String U_VC_SCHEMA_SIZE = rs.getString("U_VC_SCHEMA_SIZE");
		            String U_VC_SCHEMA_NAME = rs.getString("U_VC_SCHEMA_NAME");
		            String U_VC_SCHEMA_PWD = rs.getString("U_VC_SCHEMA_PWD");
		            String U_VC_DATABASE_URL = rs.getString("U_VC_DATABASE_URL");
		            
		            System.out.println("U_VC_TENANT_NAME" + "\t\t" + 
		            		"U_VC_STATUS" + "\t" + 
		            		"U_VC_SCHEMA_TYPE" + "\t" + 
		            		"U_VC_SCHEMA_SIZE" + "\t" + 
		            		"U_VC_SCHEMA_NAME" + "\t" + 
		            		"U_VC_SCHEMA_PWD" + "\t" + 
		            		"U_VC_DATABASE_URL" + "\t"
		            		);
		            
		            System.out.println(U_VC_TENANT_NAME1 + "\t" + 
		            		U_VC_STATUS + "\t" + 
		            		U_VC_SCHEMA_TYPE + "\t" + 
		            		U_VC_SCHEMA_SIZE + "\t" + 
		            		U_VC_SCHEMA_NAME + "\t" + 
		            		U_VC_SCHEMA_PWD + "\t" + 
		            		U_VC_DATABASE_URL + "\t"
		            		);
		        }
		    } catch (SQLException e ) {
		        System.out.print(e);
		    } finally {
		        if (prepStmt != null) { prepStmt.close(); }
		    }
	}
	
    public static void close_connection()
    {
        try {
        	connection.close();
        }catch (SQLException e) {
            System.out.println("Close connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }
    
    public static void get_tenant_info_entry(String tenant_name) throws SQLException
    {
		build_connection(siti_pdb_2_connection_string,siti_pdb_2_username,siti_pdb_2_password);
		get_tenan_info(tenant_name);
		close_connection();		
    }
    
    public static void get_tenant_application_entry(String tenant_name,String applicationName) throws SQLException
    {
		build_connection(siti_pdb_2_connection_string,siti_pdb_2_username,siti_pdb_2_password);
		get_tenan_info(tenant_name);
		close_connection();	
		
		// 
		
    }
    
	public static void main(String[] args) throws SQLException {
		get_tenant_application_entry("wysitei","app1");
    }

}
