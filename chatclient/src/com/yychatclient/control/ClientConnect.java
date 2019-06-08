package com.yychatclient.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnect {
	
	//public static Socket s;//��̬��Ա�������
	public Socket s;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
	public ClientConnect(){
		try {
			s= new Socket("127.0.0.1",3456);//������ַ���ز��ַ
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ע�����û�,����5��:����registerUserIntoDB����������user���󵽷������ˣ����ҽ��շ��������ص�message����
	public boolean registerUserIntoDB(User user){
		boolean registerSuccess=false;
		
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);//���͵���������			
			
			//������֤ͨ����mess
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();	
			//���շ������˷������ģ��Ƿ�ע��ɹ��ģ�message����
			if(mess.getMessageType().equals(Message.message_RegisterSuccess))
				registerSuccess=true;
			s.close();//�رտͻ��˵�socket����
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return registerSuccess;
	}
	
	public Message loginValidateFromDB(User user){//�����ݿ��¼��֤�ĺ���		
		//������������󣬷��Ͷ���
		//�ֽ���������� ��װ �������������
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);//���͵���������			
			
			//������֤ͨ����mess
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();		
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				System.out.println(user.getUserName()+" ��¼�ɹ���");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mess;		
	}
	
	/*public boolean loginValidate(User user){//��¼��֤
		boolean loginSuccess=false;
		//������������󣬷��Ͷ���
		//�ֽ���������� ��װ �������������
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);//���͵���������			
			
			//������֤ͨ����mess
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();		
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				loginSuccess=true;
				System.out.println(user.getUserName()+" ��¼�ɹ���");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loginSuccess;		
	}*/
}
