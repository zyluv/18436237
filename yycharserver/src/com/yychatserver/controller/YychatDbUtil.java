package com.yychatserver.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.yychat.model.Message;

public class YychatDbUtil {
	public static final String MYSQLDRIVER="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://127.0.0.1:3306/yychat";
	public static final String DBUSER= "root";
	public static final String DBPASS= "";
	
	public static void loadDriver(){
		
		try {
			Class.forName(MYSQLDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		loadDriver();
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(URL,DBUSER,DBPASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public static boolean loginValidate(String userName,String passWord) {
		boolean loginSuccess=false;
		Connection conn=getConnection();
		
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		 try {
			ptmt=conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			rs=ptmt.executeQuery();
			loginSuccess=rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB(conn,ptmt,rs);
		}
		return loginSuccess;
	}
	public static String getFriendString(String userName) {
		Connection conn=getConnection();
		String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype=1";
		PreparedStatement ptmt=null;
		String friendString="";
		ResultSet rs=null;
		
		try {
			ptmt=conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1,userName);
			rs=ptmt.executeQuery();
			
			while(rs.next()) {
				
				friendString=friendString+rs.getString("slaveuser")+" ";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB(conn,ptmt,rs);
		}
		return friendString;
	}
	public static boolean seekUser(String userName){
		boolean seekUserResult = false;
		Connection conn=getConnection();
		String user_Login_Sql="select * from user where username=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		 try {
			ptmt=conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			rs=ptmt.executeQuery();
			seekUserResult=rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB(conn,ptmt,rs);
		}
			return seekUserResult;
		}
	public static void addUser(String userName,String passWord) {
		Connection conn=getConnection();
		String user_Login_Sql="insert into user(username,password,registertimestamp) values(?,?,?)";
		PreparedStatement ptmt=null;
		 try {
			ptmt=conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			Date date=new Date();
			java.sql.Timestamp timestamp=new java.sql.Timestamp(date.getTime());
			ptmt.setTimestamp(3, timestamp);
			
			int count=ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB(conn,ptmt);
		}
	}
	public static void closeDB(Connection conn,PreparedStatement ptmt) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ptmt!=null) {
			try {
				ptmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void closeDB(Connection conn,PreparedStatement ptmt,ResultSet rs) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ptmt!=null) {
			try {
				ptmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	Class.forName(MYSQLDRIVER);
//	System.out.println("�Ѿ����������ݿ�������");
//	//2���������ݿ�
//	System.out.println(URL);
//	//�����û��������������url
//	//String url="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
//	String dbUser="root";
//	String dbPass="";				
//	Connection conn=DriverManager.getConnection(url,dbUser,dbPass);
//	
//	//3������PreparedStatement��������ִ��SQL���
//	String user_Login_Sql="select * from user where username=? and password=?";
//	PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
//	ptmt.setString(1, userName);
//	ptmt.setString(2, passWord);
//	
//	//4��ִ�в�ѯ�����ؽ����
//	ResultSet rs=ptmt.executeQuery();
//	
//	//5�����ݽ�������ж��Ƿ��ܵ�¼
//	boolean loginSuccess=rs.next();
//	//ʵ��������֤����
//	
//
////6����yychatserver��Ŀ��������ݿ�������Build Path->Add External JARs->mysql-connector-java-5.1.6-bin
////��������				
//	mess=new Message();//��֤����
//	mess.setSender("Server");
//	mess.setReceiver(userName);
}
