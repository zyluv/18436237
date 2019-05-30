package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	String sender;
	Message mess;
	ObjectInputStream ois;
	ObjectOutputStream oos;
         public ServerReceiverThread(Socket s){
        	 this.s=s;
         } 
         public void run(){
        	 
        	 //Message mess;
        	 while(true){
        	 try{
        	 ois=new ObjectInputStream(s.getInputStream());
        	 mess=(Message)ois.readObject();
        	 sender=mess.getSender();
        	 System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent());
        	 
        	 //普通聊天信息转发
        	 if(mess.getMessageType().equals(Message.message_Common)){
        		 Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
            	 sendMessage(s1,mess);
            	 System.out.println("服务器转发聊天信息了："+mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent());
        	 }       	
        	         	        	 
        	 //激活在线好友
        	 if(mess.getMessageType().equals(Message.message_Friendlist)){
        		 Set friendSet=StartServer.hmSocket.keySet();
        		 Iterator it=friendSet.iterator();
        		 String friendName;
        		 String friendString=" ";
        		 while(it.hasNext()){
        			 friendName=(String)it.next();
        			 if(!friendName.equals(mess.getSender())){
        				 friendString=friendName+" "+friendString;        				 
        			 }
        			 System.out.println("全部好友的名字"+friendString);
        			 
        			 mess.setContent(friendString);
        			 mess.setMessageType(Message.message_Client);
        			 mess.setSender("Server");
        			 mess.setReceiver(sender);
        			 sendMessage(s, mess);
        		 }        		 
        	 }
        	 
        
        	    }catch (IOException | ClassNotFoundException e){
        	    	e.printStackTrace();
        	    }
        	 }
         }
		private void sendMessage(Socket s,Message mess) throws IOException {			
			oos=new ObjectOutputStream(s.getOutputStream());
        	 oos.writeObject(mess);
		}
}
