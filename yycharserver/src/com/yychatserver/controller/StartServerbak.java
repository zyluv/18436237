/*package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Connection;
import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
	ServerSocket ss;
	Socket s;
	String userName;
	String passWord;
	Message mess;
	ObjectOutputStream oos;
	
	
	public StartServer(){
		try {//�����쳣
			ss= new ServerSocket(3456);
			System.out.println("�������Ѿ�����������3456�˿�");
			while(true){//?Thread���߳�
				s= ss.accept();//���տͻ�����������
				System.out.println("���ӳɹ�:"+s);
				
				//����User����
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User user=(User)ois.readObject();
				userName=user.getUserName();
				passWord=user.getPassWord();
				System.out.println(userName);
				System.out.println(passWord);
				
				//�����ݿ���ʵ���û��ĵ�¼��֤
				//1��������������
				Class.forName("com.mysql.jdbc.Driver");				
				
				//2����ȡ���Ӷ���
				//String url="jdbc:mysql://127.0.0.1:3306/yychat";//Ĭ��GBK
				String url="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
				String dbuser="root";
				String dbpass="";
				Connection conn=DriverManager.getConnection(url,dbuser,dbpass);
				
				//3������PreparedStatement��������ִ��SQL���,��׼
				String user_Login_Sql="select * from user where username=? and password=?";
				PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
				ptmt.setString(1, userName);
				ptmt.setString(2, passWord);
				
				//4��ִ�в�ѯ�����ؽ����
				ResultSet rs=ptmt.executeQuery();
				
				//5�����ݽ�������ж��Ƿ��ܵ�¼
				boolean loginSuccess=rs.next();				
				System.out.println("loginSuccessΪ��"+loginSuccess);
				
				boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);
				
				//ʵ��������֤����
				mess=new Message();//ʹ����������Message����mess�Ƕ��������
				mess.setSender("Server");
				mess.setReceiver(userName);
				//if(passWord.equals("123456")){//����Ƚ�
				if(loginSuccess){//����Ƚ�
					//���߿ͻ���������֤ͨ������Ϣ�����Դ���Message��				
					mess.setMessageType(Message.message_LoginSuccess);//"1"Ϊ��֤ͨ��
					
					//�������ݱ��к�����Ϣ�����º����б�1����������ѯ������Ϣ�������͵��ͻ���
					String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
					ptmt=conn.prepareStatement(friend_Relation_Sql);
					ptmt.setString(1, userName);
					rs=ptmt.executeQuery();
					String friendString="";
					while(rs.next()){//�ƶ�������е�ָ��,�Ѻ��ѵ�����һ������ȡ����
						//rs.getString(1);
						friendString=friendString+rs.getString("slaveuser")+" ";
					}
					
					String friendString=YychatDbUtil.getFriendString(userName);
					
					mess.setContent(friendString);
					System.out.println(userName+"��relation���ݱ��к��ѣ�"+friendString);					
					
				}else {
					mess.setMessageType(Message.message_LoginFailure);//"0"Ϊ��֤��ͨ��		
				}				
				sendMessage(s,mess);
				
				//���������������Ϣ���ɲ����ԣ������ԣ�Ӧ���½�һ�������߳�
				//if(passWord.equals("123456")){
				if(loginSuccess){
					//�����������û�ͼ�경��1���ڴ˴����Լ���¼�ɹ�����Ϣ���͵��ڸ��û�֮ǰ��¼�������û�
					//������Ҫ���͵���Ϣ
					mess.setMessageType(Message.message_NewOnlineFriend);//����
					mess.setSender("Server");
					mess.setContent(userName);//������Ϣ������,thisָ������
					
					//�õ��Ѿ������û�������
					Set onlineFriendSet=hmSocket.keySet();
					Iterator it=onlineFriendSet.iterator();
					String friendName;
					while (it.hasNext()) {//��ȫ�������û��������û����ߵ���Ϣ
						friendName=(String)it.next();
						mess.setReceiver(friendName);
						//��friendName������Ϣ
						Socket s1=(Socket)hmSocket.get(friendName);
						sendMessage(s1, mess);						
					}
					
					
					hmSocket.put(userName, s);
					new ServerReceiverThread(s).start();//����,ÿ���û�����һ����Ӧ�ķ����߳�
				}				
			}
			
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();//�����쳣
		}
	}
	public void sendMessage(Socket s,Message mess) throws IOException {
		ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}
}
*/