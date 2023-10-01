package offline;
import java.util.*;
import java.sql.*;


public class CRUD_operations {
	
	static Scanner sc=new Scanner(System.in);
	static String Driver="oracle.jdbc.driver.OracleDriver";
	static String url ="jdbc:oracle:thin:@localhost:1521:XE";
	static Connection con=null;
	
	public static void main(String[] args) {
		
		
		
		try {
			
			Class.forName(Driver);
			con=DriverManager.getConnection(url,"system" ,"admin");
			
			while(true) {
				System.out.println("Account Operations");
				System.out.println("1.Create Account");
				System.out.println("2.Display Account details");
				System.out.println("3.Deposit");
				System.out.println("4.Withdrawl");
				System.out.println("5.Money Transfer");
				System.out.println("6.Delete Account");
				System.out.println("7.Quit");
				
				System.out.println("Enter Choice : ");
				int ch=sc.nextInt();
				
				if(ch==1) {
					createAccount();
				}else if(ch==2) {
					displayDetails();
				}else if(ch==3) {
					deposit();
				}else if(ch==4) {
					withdraw();
				}else if(ch==5) {
					moneyTransfer();
				}else if(ch==6) {
					deleteAccount();
				}else if(ch==7) {
					System.out.println("End of Operations");
					
				}else {
					System.out.println("Invalid Choice");
				}
			}
		}
			catch(Exception e) {
				System.out.println("Exception :" +e.getMessage());
			}finally {
				if(con!=null) {
					try {
						con.close();
					}catch(SQLException e){
						e.printStackTrace();
						
					}
				}
			}
			
		}
	
	static void createAccount()throws SQLException{
		System.out.println("Enter account Details : ");
		int num=sc.nextInt();
		String name=sc.next();
		int balance=sc.nextInt();
		String query="insert into account values(?,?,?)";
		
		PreparedStatement ps=con.prepareStatement(query);
		
		ps.setInt(1,num);
		ps.setString(2, name);
		ps.setInt(3,balance);
		int count=ps.executeUpdate();
		
		if (count>0) {
			System.out.println("Account created successfully");
		}else {
			System.out.println("Faioled to create Account");
		}
	}
	
	static void displayDetails() throws SQLException{
		System.out.println("Enter account number: ");
		int num=sc.nextInt();
		String query ="select * from account where num=?";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, num);
		
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			String name=rs.getString(2);
			int balance =rs.getInt(3);
			System.out.println("Error : Invalid Account Number");
		}
	}
	
	static void deposit() throws SQLException{
		System.out.println("Enter account Number");
		int num=sc.nextInt();
		
		System.out.println("Enter account Number to Deposit : ");
		int amt=sc.nextInt();
		String query="update account set balance =balance+? where num=?";
		
		PreparedStatement ps=con.prepareStatement(query);
		
		ps.setInt(1, amt);
		ps.setInt(2, num);
		
		int count=ps.executeUpdate();
		
		if(count>0) {
			System.out.println(amt+" deposited to acc num "+num +" Successfully");
		}else {
			System.out.println("Error: Invalid account Number ");
		}
		
		
	}
	
	static void withdraw() throws SQLException{
		System.out.println("Enter account Number: ");
		int num=sc.nextInt();
		
		System.out.println("Enter amount to withdraw : ");
		int amt=sc.nextInt();
		
		String query1="select balance from account where num=?";
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setInt(1,num);
		ResultSet rs=ps1.executeQuery();
		
		if(rs.next()) {
			int balance =rs.getInt(1);
			
			if(amt <= balance) {
				System.out.println("Collect Cash : "+amt);
				
				String query2 ="update account setbalance=balance-? where num=?";
				PreparedStatement ps2 =con.prepareStatement(query2);
				ps2.setInt(1, amt);
				ps2.setInt(2, num);
				
				int count=ps2.executeUpdate();
				
				System.out.println("Account Updated Successfully");
				
			}else {
				System.out.println("Error :Low balance in Account ");
			}
		}else {
			System.out.println("Error : Invalid account number given");
		}
		
	}
	
	static void moneyTransfer() throws SQLException{
		
		System.out.println("Enter Acc Num from which transfer amount : ");
		int src = sc.nextInt();
		
		System.out.println("Enter Amount to Transfer : ");
		int amt = sc.nextInt();

		System.out.println("Enter Acc Num to which transfer amount : ");
		int dest = sc.nextInt();
		
		con.setAutoCommit(false);
		
		String q1 = "select balance from account where num=?";
		PreparedStatement ps1 = con.prepareStatement(q1);
		ps1.setInt(1, src);
		ResultSet rs1 = ps1.executeQuery();
		if(rs1.next())
		{
			int balance = rs1.getInt(1);
			if(balance>=amt)
			{
		String q2 = "update account set balance=balance-? where num=?";
				PreparedStatement ps2 = con.prepareStatement(q2);
				ps2.setInt(1, amt);
				ps2.setInt(2, src);
				ps2.executeUpdate();
		System.out.println("Amount deducted from source account");
				
		String q3 = "update account set balance=balance+? where num=?";
				PreparedStatement ps3 = con.prepareStatement(q3);
				ps3.setInt(1, amt);
				ps3.setInt(2, dest);
				int count = ps3.executeUpdate();
				if(count>0)
				{
					System.out.println("Amt Transferred");
					con.commit();
				}
				else
				{
				System.out.println("Error : Destination account missing");
					con.rollback();
			System.out.println("Dont worry - Transaction will rollback");
				}
			}
			else
			{
			System.out.println("Error : Insufficient funds in source account");
			}
		}
		else
		{
		System.out.println("Error : Source account number is not valid");
		}
		
		
	}
	
	
	static void deleteAccount() throws SQLException{
		System.out.println("Enter account number to delete :");
		int num=sc.nextInt();
		
		String query="delete from account where num=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, num);
		int count=ps.executeUpdate();
		if(count>0) {
			System.out.println("Account Deleted Successfully");
		}else {
			System.out.println("Failed to Delete ACcount");
		}
		
	}
	
		
		
	}
	
	
	
	
	
	
	
	
	
	



