package com.chatclient.view;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychat.model.User;
import com.yychatclient.control.ClientConnect;

public class ClientLogin extends JFrame implements ActionListener{
	public static HashMap hmFriendlist=new HashMap<String,FriendList>();
	
    JLabel jlbl1;
    
    JTabbedPane jtp1;//��ǩģ��  
    JPanel jp2,jp3,jp4;//���������
    JLabel jlbl2,jlbl3,jlbl4,jlbl5;//�ı�
    JLabel a,b,c,d;
    JLabel a3,b3,c3,d3;
    JTextField jtf1,j2,j4;
    JPasswordField jpf1,j3,j5;//��ȡ����
    JButton jb4,a1,a2;//����
    JCheckBox jcb1,jcb2,jcb3,jcb4,jcb5,jcb6;
    
    JButton jb1,jb2,jb3;//����
    JPanel jp1;//���������
    
	public ClientLogin() 
	{
		jlbl1=new JLabel(new ImageIcon("images/tou.gif"));
		this.add(jlbl1,"North");
		
        jp2=new JPanel(new GridLayout(3,3));
        jp3=new JPanel(new GridLayout(3,3));jp4=new JPanel(new GridLayout(3,3));
        
        a3=new JLabel("�����ַ",JLabel.CENTER);b3=new JLabel("������֤��",JLabel.CENTER);//��3���
		c3=new JLabel("���������ַ",JLabel.CENTER);
		c3.setForeground(Color.blue);
		d3=new JLabel("�������뱣��",JLabel.CENTER);
		j4=new JTextField();
		j5=new JPasswordField();
		a2=new JButton(new ImageIcon("images/clear.gif"));
        jcb5=new JCheckBox("�����¼");jcb6=new JCheckBox("��ס����");
        jp4.add(a3); jp4.add(j4); jp4.add(a2);
        jp4.add(b3); jp4.add(j5); jp4.add(c3);
        jp4.add(jcb5); jp4.add(jcb6); jp4.add(d3);
        
        a=new JLabel("�ֻ�����",JLabel.CENTER);b=new JLabel("������֤��",JLabel.CENTER);//��2���
		c=new JLabel("�����ֻ�����",JLabel.CENTER);
		c.setForeground(Color.blue);
		d=new JLabel("�������뱣��",JLabel.CENTER);
		j2=new JTextField();
		j3=new JPasswordField();
		a1=new JButton(new ImageIcon("images/clear.gif"));
        jcb3=new JCheckBox("�����¼");jcb4=new JCheckBox("��ס����");
        jp3.add(a); jp3.add(j2); jp3.add(a1);
        jp3.add(b); jp3.add(j3); jp3.add(c);
        jp3.add(jcb3); jp3.add(jcb4); jp3.add(d);
        
		jlbl2=new JLabel("YY����",JLabel.CENTER);jlbl3=new JLabel("YY����",JLabel.CENTER);
		jlbl4=new JLabel("��������",JLabel.CENTER);
		jlbl4.setForeground(Color.blue);
		jlbl5=new JLabel("�������뱣��",JLabel.CENTER);
		jtf1=new JTextField();
		jpf1=new JPasswordField();
		jb4=new JButton(new ImageIcon("images/clear.gif"));
        jcb1=new JCheckBox("�����¼");jcb2=new JCheckBox("��ס����");
        jp2.add(jlbl2); jp2.add(jtf1); jp2.add(jb4);
        jp2.add(jlbl3); jp2.add(jpf1); jp2.add(jlbl4);
        jp2.add(jcb1); jp2.add(jcb2); jp2.add(jlbl5);
               
        jtp1=new JTabbedPane();
        jtp1.add(jp2,"YY����");jtp1.add(jp3,"�ֻ�����");jtp1.add(jp4,"��������");
        this.add(jtp1);
        				
		
		jb1=new JButton(new ImageIcon("images/denglu.gif"));
		jb2=new JButton(new ImageIcon("images/zhuce.gif"));
		jb3=new JButton(new ImageIcon("images/quxiao.gif"));
		jp1=new JPanel();
		jp1.add(jb1);jp1.add(jb2);jp1.add(jb3);
		this.add(jp1,"South");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);//��ʾ���
	}
	
	public static void main(String[] args) 
	     {
		ClientLogin clientLogin=new ClientLogin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb2) {
			String userName = jtf1.getText().trim();
			String passWord=new String(jpf1.getPassword());
			//����User����
			User user=new User();//������ڶ��ڴ棬���ñ����������ڴ�
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setUserMessageType("USER_REGISTER");
			
			boolean registerSuccess=new ClientConnect().registerUserIntoDB(user);
			if(registerSuccess){
				JOptionPane.showMessageDialog(this,"ע��ɹ�");
			}else {
				JOptionPane.showMessageDialog(this,"ע��ʧ�ܣ��ظ����û�����");
			}

		
		}
		if(e.getSource()==jb1) {
			String userName = jtf1.getText().trim();
			String passWord=new String(jpf1.getPassword());
			//����User����
			User user=new User();//������ڶ��ڴ棬���ñ����������ڴ�
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setUserMessageType("USER_LOGIN");
			
			//boolean loginSuccess=new ClientConnect().loginValidate(user);
			//if(loginSuccess){
				//new FriendList(userName);
			Message mess=new ClientConnect().loginValidateFromDB(user);
			if(mess.getMessageType().equals(Message.message_LoginSuccess)) {
				String friendString=mess.getContent();
//				FriendList friendlist=new FriendList(userName);
				FriendList friendlist=new FriendList(userName,friendString);
				hmFriendlist.put(userName,friendlist);
				
				
				Message mess1=new Message();
				mess1.setSender(userName);
				mess1.setReceiver("Server");
				mess1.setMessageType(Message.message_Friendlist);
				Socket s=(Socket)ClientConnect.hmSocket.get(userName);
				ObjectOutputStream oos;
				try{
					oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(mess1);
				}catch (IOException e1){
					e1.printStackTrace();
				}
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this,"�������");
			}
		}		
	}
}
