package com.yychatserver.controller;

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
				
				 //ע�����û�,����7���ڷ����������û���ע��
				if(user.getUserMessageType().equals("USER_REGISTER")){
					//ע�����û�,����8������ע���û������в�ѯ,
					//seekUserResultΪtrue��ͬ���û�,falseû��ͬ���û�
					boolean seekUserResult=YychatDbUtil.seekUser(userName);
					mess=new Message();//ʹ����������Message����mess�Ƕ��������
					mess.setSender("Server");
					mess.setReceiver(userName);
					if(seekUserResult){
						//���ؿͻ���ע��ʧ��
						mess.setMessageType(Message.message_RegisterFailure);
					}else{
						//ע�����û�,����9�����û��ͬ���û��������û������ֺ�����д�뵽user���У����ؿͻ���ע��ɹ�
						YychatDbUtil.addUser(userName,passWord);
						mess.setMessageType(Message.message_RegisterSuccess);
					}
					sendMessage(s,mess);
					s.close();//ע����ɣ��رշ������˵�socket����
				}
				
				
				if(user.getUserMessageType().equals("USER_LOGIN")){
					//�����ݿ���ʵ���û��ĵ�¼��֤				
					
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
