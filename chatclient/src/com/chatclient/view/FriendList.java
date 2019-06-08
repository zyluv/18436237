package com.chatclient.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychatclient.control.ClientConnect;

public class FriendList extends JFrame implements ActionListener,MouseListener{//��������
	public static HashMap hmFriendChat1=new HashMap<String,FriendChat1>();//��ֵ��
	
	CardLayout cardLayout;//��Ƭ����
	//�����������ñ���
	JPanel myFriendPanel;
	
	JPanel addFriendPanel;
	JButton addFriendJButton;
	JButton myFriendJButton;
	
	JScrollPane myFriendJScrollPane;
	JPanel myFriendListJPanel;
	static final int FRIENDCOUNT=51;
	JLabel[] myFriendJLabel=new JLabel[FRIENDCOUNT];//��������?
	//myFriendJLabel[0]...myFriendJLabel[50] ÿһ���������ñ���
	
	JPanel myStrangerBlackListJPanel;
	JButton myStrangerJButton;
	JButton blackListJButton;
	
	JPanel myStrangerPanel;
	
	JPanel myFriendStrangerPanel;
	JButton myFriendJButton1;
	JButton myStrangerJButton1;
	
	JButton blackListJButton1;
	
	String userName;
	
	//public FriendList(String userName){
	public FriendList(String userName,String friendString){
		this.userName=userName;//�ֲ���������Ա������ֵ
		
		//��һ�ſ�Ƭ����������
		myFriendPanel=new JPanel(new BorderLayout());//�߽粼��
		//System.out.println(myFriendPanel.getLayout());
		
		//����º���ʵ�鲽�裺1��������Ӻ��ѵİ�ť����Ӽ�����
		addFriendJButton=new JButton("��Ӻ���");
		addFriendJButton.addActionListener(this);//����������
		
		myFriendJButton=new JButton("�ҵĺ���");		
		addFriendPanel=new JPanel(new GridLayout(2, 1));//Ĭ�ϲ�����ʽ,ʹ�����񲼾�
		addFriendPanel.add(addFriendJButton);
		addFriendPanel.add(myFriendJButton);
		myFriendPanel.add(addFriendPanel,"North");
		//myFriendPanel.add(myFriendJButton,"North");
		
		//�в�
		/*JScrollPane myFriendJScrollPane;
		JPanel myFriendListJPanel;
		static final int FRIENDCOUNT=51;
		JLabel[] myFriendJLabel;//��������*/
		
		//�������ݱ��к�����Ϣ�����º����б�2��ʹ�ú�������(friendString)�����º����б��ͼ��
		myFriendListJPanel=new JPanel();
		updateFriendIcon(friendString);
		
		/*myFriendListJPanel=new JPanel(new GridLayout(FRIENDCOUNT-1,1));
		for(int i=1;i<FRIENDCOUNT;i++){
			myFriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"1"��ǩ
			myFriendJLabel[i].setEnabled(false);//δ��������ͼ��
			//�����Լ���ͼ��
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);//�����������
			myFriendListJPanel.add(myFriendJLabel[i]);
		}*/
		
		//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
		//myFriendJScrollPane =new JScrollPane();
		//myFriendJScrollPane.add(myFriendListJPanel);
		myFriendJScrollPane =new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendJScrollPane);
		
		myStrangerBlackListJPanel=new JPanel(new GridLayout(2,1));//���񲼾�
		myStrangerJButton=new JButton("�ҵ�İ����");
		//����¼�������
		myStrangerJButton.addActionListener(this);
		
		blackListJButton=new JButton("������");
		myStrangerBlackListJPanel.add(myStrangerJButton);
		myStrangerBlackListJPanel.add(blackListJButton);
		myFriendPanel.add(myStrangerBlackListJPanel,"South");
		
		//�ڶ��ſ�Ƭ
		myStrangerPanel = new JPanel(new BorderLayout());
		
		myFriendStrangerPanel=new JPanel(new GridLayout(2,1));
		myFriendJButton1=new JButton("�ҵĺ���");//��Ӽ�����
		myFriendJButton1.addActionListener(this);
		myStrangerJButton1=new JButton("�ҵ�İ����");
		myFriendStrangerPanel.add(myFriendJButton1);
		myFriendStrangerPanel.add(myStrangerJButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");	
		
		blackListJButton1=new JButton("������");
		myStrangerPanel.add(blackListJButton1,"South");
		
		cardLayout=new CardLayout();
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");		 
		this.add(myStrangerPanel,"2");
		
		this.setSize(150,500);
		this.setTitle(this.userName+" �ĺ����б�");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void updateFriendIcon(String friendString) {
		myFriendListJPanel.removeAll();//�Ƴ�ȫ������ͼ��
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		
		myFriendListJPanel.setLayout(new GridLayout(count,1));
		for(int i=0;i<count;i++){
			myFriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/dd3.png"),JLabel.LEFT);//"1"��ǩ
			//myFriendJLabel[i].setEnabled(false);//δ��������ͼ��
			//�����Լ���ͼ��
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);//�����������
			myFriendListJPanel.add(myFriendJLabel[i]);
		}
	}
	
	public static void main(String[] args) {
		//FriendList friendList=new FriendList();

	}
	
	
	public void setEnableFriendIcon(String friendString){
		//ȡ����������
		//friendString=friendString.trim();
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		System.out.println("���Ѹ���"+count);
		for(int i=1;i<count;i++){	
			
			System.out.println("friendName["+i+"]:"+friendName[i]);
			myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);//�������ߺ���ͼ��
		}		
	}
	
	public void setEnableNewFriendIcon(String newOnlinefriendString){
		myFriendJLabel[Integer.parseInt(newOnlinefriendString)].setEnabled(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//����º��ѣ�ʵ�鲽��2�����Ӵ����¼��Ĵ���
		if(arg0.getSource()==addFriendJButton){
			String addFriendName=JOptionPane.showInputDialog(null,"��������ѵ����֣�","��Ӻ���",JOptionPane.DEFAULT_OPTION);
			Message mess=new Message();
			mess.setSender(userName);
			mess.setReceiver("Server");
			mess.setContent(addFriendName);
			mess.setMessageType(Message.message_AddFriend);
			//����º��ѣ�ʵ�鲽��4��������Ϣ����������
			Socket s=(Socket)ClientConnect.hmSocket.get(userName);
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(mess);//���͵���������			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(arg0.getSource()==myStrangerJButton){
			cardLayout.show(this.getContentPane(), "2");
		}
		if(arg0.getSource()==myFriendJButton1){
			cardLayout.show(this.getContentPane(), "1");
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver=jlbl.getText();
			//new FriendChat(this.userName,receiver);
			//new Thread(new FriendChat(this.userName,receiver)).start();//�����߳�
			
			//˼·������ȥ���Һ���������棬���û�ҵ������½����ҵ��Ļ�����ʾ��
			FriendChat1 friendChat1=(FriendChat1)hmFriendChat1.get(userName+"to"+receiver);
			if(friendChat1==null){//Ϊ��˵���ö��󻹲�����
				friendChat1=new FriendChat1(this.userName,receiver);//friendChat1��һ�����������ñ��������ö���
				hmFriendChat1.put(userName+"to"+receiver,friendChat1);//�������HashMap��
			}else{//��Ϊ�գ�ֱ����ʾ�ö���
				friendChat1.setVisible(true);
			}			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.red);		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.black);	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
