package com.yychatclient.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnect {
	     public Socket s;//��̬��Ա�������
	     public static HashMap hmSocket=new HashMap<String,Socket>();
          public ClientConnect()
          {
        	try {
				s=new Socket("127.0.0.1",3456);//��������ַ�����ص�ַ
			    } catch (IOException e) 
        	       {
				  // TODO Auto-generated catch block
				   e.printStackTrace();
			       }
          }        	
//           public boolean loginValidate(User user){
//        	   //�������������,���Ͷ���
//        	   //�ֽ�����������װ���������������
//        	   boolean loginsuccess=false;
//        	   ObjectOutputStream oos;
//        	   ObjectInputStream ois;
//        	   Message mess=null;
//			try{
//        	   oos=new ObjectOutputStream(s.getOutputStream());
//        	   oos.writeObject(user);
//        	   
//        	   ois=new ObjectInputStream(s.getInputStream());
//        	   mess=(Message)ois.readObject();
//        	   
//        	   if(mess.getMessageType().equals(Message.message_LoginSuccess)){
//        		   loginsuccess=true;
//        		   System.out.println(user.getUserName()+" ��¼�ɹ�������");
//        		   hmSocket.put(user.getUserName(),s);
//        		   new ClientReceiverThread(s).start();
//        	   }
//        	   
//        	   }catch (IOException | ClassNotFoundException e){
//        		   e.printStackTrace();
//			}
//        	   return loginsuccess;
//           }
      	public boolean registerUserIntoDB(User user) {
      		boolean registerSuccess=false;
//			�������������,���Ͷ���
     	   //�ֽ�����������װ���������������
//     	   boolean loginsuccess=false;
     	   ObjectOutputStream oos;
     	   ObjectInputStream ois;
     	   Message mess=null;
			try{
     	   oos=new ObjectOutputStream(s.getOutputStream());
     	   oos.writeObject(user);
     	   
     	   ois=new ObjectInputStream(s.getInputStream());
     	   mess=(Message)ois.readObject();
     	   
     	   if(mess.getMessageType().equals(Message.message_RegisterSuccess)){
     		  registerSuccess=true;
     		 s.close();
     	   }
     	   
     	   }catch (IOException | ClassNotFoundException e){
     		   e.printStackTrace();
			}
     	   return registerSuccess;
		}
		public Message loginValidateFromDB(User user) {
//			�������������,���Ͷ���
     	   //�ֽ�����������װ���������������
//     	   boolean loginsuccess=false;
     	   ObjectOutputStream oos;
     	   ObjectInputStream ois;
     	   Message mess=null;
			try{
     	   oos=new ObjectOutputStream(s.getOutputStream());
     	   oos.writeObject(user);
     	   
     	   ois=new ObjectInputStream(s.getInputStream());
     	   mess=(Message)ois.readObject();
     	   
     	   if(mess.getMessageType().equals(Message.message_LoginSuccess)){
//     		   loginsuccess=true;
     		   System.out.println(user.getUserName()+" ��¼�ɹ�������");
     		   hmSocket.put(user.getUserName(),s);
     		   new ClientReceiverThread(s).start();
     	   }
     	   
     	   }catch (IOException | ClassNotFoundException e){
     		   e.printStackTrace();
			}
     	   return mess;
		}
          }

