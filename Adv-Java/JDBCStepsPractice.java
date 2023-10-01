package offline;
import java.sql.*;

public class JDBCStepsPractice {

	public static void main(String[] args) throws SQLException {

			//Step-1 :Register the driver
			String driver = "oracle.jdbc.driver.OracleDriver" ;
			try {
				Class.forName(driver);
			
			System.out.println("Driver registration Succefully");
			 
			 //Step-2 :get the Connection
			 String url ="jdbc:oracle:thin:@localhost:1521:xe";
			 String username ="system";
			 String password ="admin";
			 
			 Connection con =DriverManager.getConnection(url,username,password);
			 System.out.println("Connection Established");
			 
			 //Step-3 :create the Statement object
			 Statement stmt =con.createStatement();
			 String query ="insert into product values(108,'laptop',45000,2023)";
			 
			 
			 
			 
			 //Step-4 :execute the query
			 stmt.executeUpdate(query);
			 
			 System.out.println("Query Executed Successfully");
			 
			 
			 //Step-5 :close the Connection
			 con.close();
			 System.out.println("Connection Closed Succefully");
					 
				
					 
		} 
		 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Driver Not Found");
		}
	}
}
				 
		 
		
		
		
		
		
		
		
		
		

	


