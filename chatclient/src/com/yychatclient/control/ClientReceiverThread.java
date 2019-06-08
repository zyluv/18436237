package com.yychatclient.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.chatclient.view.ClientLogin;
import com.chatclient.view.FriendChat1;
import com.chatclient.view.FriendList;
import com.yychat.model.Message;

public class ClientReceiverThread extends Thread{

	private Socket s;
	
	public ClientReceiverThread(Socket s){
		this.s=s;
	}
	public void run(){
		ObjectInputStream ois;
		while(true){
			try {
				ois = new ObjectInputStream(s.getInputStream());
				Message mess=(Message)ois.readObject();//����������Ϣ,�߳�����
				String showMessage=mess.getSender()+"��"+mess.getReceiver()+"˵��"+mess.getContent();
				System.out.println(showMessage);
				
				//����º��ѣ�ʵ�鲽��9��������Ӻ�����Ϣ
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_NoUser)){
					JOptionPane.showMessageDialog(null, "��Ӻ���ʧ�ܣ��û������ڣ�");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_AlreadyFriend)){
					JOptionPane.showMessageDialog(null, "��Ӻ���ʧ�ܣ������ظ���Ӻ��ѣ�");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendSuccess)){
					JOptionPane.showMessageDialog(null, "��Ӻ��ѳɹ���");
					String allFriendName=mess.getContent();
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getSender());
					friendList.updateFriendIcon(allFriendName);
					friendList.revalidate();//�ػ�����б����				
				}
				
				if(mess.getMessageType().equals(Message.message_Common)){
					//jta.append(showMessage+"\r\n");
					//�ں��ѽ���FriendChat1����ʾ������Ϣ
					//1������õ�����������棬˼·���������������棬
					FriendChat1 friendChat1=(FriendChat1)FriendList.hmFriendChat1.get(mess.getReceiver()+"to"+mess.getSender());//���ǽ�����+��������
									
					//2������ʾ��Ϣ
					friendChat1.appendJta(showMessage);
				}
				
				
				//��3�����ͻ��˽��շ����������������ߺ�����Ϣ��Ȼ�����ø���Ϣ�������ߺ��ѵ�ͼ��
				if(mess.getMessageType().equals(Message.message_OnlineFriend)){
					System.out.println("���ߺ���"+mess.getContent());
					
					//����Ҫ�õ������б����
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getReceiver());
					//�����Ӧͼ��
					friendList.setEnableFriendIcon(mess.getContent());
				}
				
				//�����������û�ͼ�경��2��������Ϣ������ͼ��
				if(mess.getMessageType().equals(Message.message_NewOnlineFriend)){
					System.out.println("���û������ˣ��û�����"+mess.getContent());
					//����Ҫ�õ������б����
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getReceiver());
					System.out.println("FriendList��������"+mess.getReceiver());
					//�����Ӧͼ��
					friendList.setEnableNewFriendIcon(mess.getContent());//�������û�������	
					//friendList.setEnableFriendIcon(mess.getContent());	
					
				}
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}
}
