package ReadMindPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ReadMindWaitingRoomDisp implements ActionListener, MouseListener{
	private JFrame disp = new JFrame("����");
	private String id;
	private String name;
	private String image;
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private JList roomlist = new JList();
	private JScrollPane roomsp = new JScrollPane(roomlist);
	private JList userlist = new JList();
	private JScrollPane userlistsp = new JScrollPane(userlist);
	private JButton exitbt = new JButton("�α׾ƿ�");
	private JTextArea chattingArea = new JTextArea(5, 1);
	private JTextField chattingField = new JTextField();
	private JScrollPane areasp = new JScrollPane(chattingArea);
	private JScrollPane fieldsp = new JScrollPane(chattingField);
	private JButton makeroombt = new JButton("�� �����");
	private JButton inputbt = new JButton("�Է�");
	private JButton userbt = new JButton("���� �ٲٱ�");
	private ReadMindWaitingRoomThread t;
	private JDialog makeroomd = new JDialog(disp, "�� �����", false);
	private JTextField ditf = new JTextField(15);
	private JButton makebt = new JButton("�����");
	private JButton canclebt = new JButton("���");
	
	public ReadMindWaitingRoomDisp() {}
	public ReadMindWaitingRoomDisp(String id, String name, String image, Socket s, ObjectOutputStream oos, ObjectInputStream ois) {
		this.s = s;
		this.oos = oos;
		this.ois = ois;
		this.id = id;
		this.name = name;
		this.image = image;
		t = new ReadMindWaitingRoomThread(disp, id, s, oos, ois, name, chattingArea, userlist, roomlist, image);
		t.start();
		getUserList();
		getRoomList();
		dialogset();
		dispset();
		eventset();
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setVisible(true);
	}
	
	private void dispset() {
		JPanel centerPanel = new JPanel(new BorderLayout());
			JPanel ccenterPanel = new JPanel(new BorderLayout());
				JPanel roomlistPanel = new JPanel(new BorderLayout());
					roomlistPanel.add("North", new JLabel("�� ���"));
					roomlistPanel.add("Center", roomsp);
				ccenterPanel.add("Center", roomlistPanel);
				JPanel p1 = new JPanel(new BorderLayout());
					ImageIcon im2 = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/main2.png");
					JLabel imla2 = new JLabel(im2);
					p1.add("Center", imla2);
					JPanel p2 = new JPanel(new FlowLayout());
						p2.add(makeroombt);
						p2.add(exitbt);
					p1.add("South", p2);
				ccenterPanel.add("East", p1);
			JPanel chattingPanel = new JPanel(new BorderLayout());
				chattingPanel.add("Center", areasp);
				chattingArea.setEditable(false);
				JPanel fieldPanel = new JPanel(new BorderLayout());
					fieldPanel.add("East", inputbt);
					fieldPanel.add(fieldsp);
				chattingPanel.add("South", fieldPanel);
				
			centerPanel.add("Center", ccenterPanel);
			centerPanel.add("South", chattingPanel);
		disp.add("Center", centerPanel);
		JPanel userListPanel = new JPanel(new BorderLayout());
			JPanel userPanel = new JPanel(new BorderLayout());
			ImageIcon im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/" + image + ".png");
				JPanel userpicturePanel = new JPanel();
					JLabel imla = new JLabel(im);
					userpicturePanel.add(imla);
				userPanel.add("West", userpicturePanel);
				JPanel userinfoPanel = new JPanel(new GridLayout(2, 1));
					userinfoPanel.add(new JLabel(name));
					userinfoPanel.add(new JLabel(id));
				userPanel.add("Center", userinfoPanel);
				JPanel userbtPanel = new JPanel(new FlowLayout());
					userbtPanel.add(userbt);
				userPanel.add("South", userbtPanel);
				userPanel.setPreferredSize(new Dimension(230, 150));
			userListPanel.add("South", userPanel);
			JPanel listPanel = new JPanel(new BorderLayout());
				listPanel.add("North", new JLabel("������ ���"));
				listPanel.add("Center", userlistsp);
				userlist.setVisibleRowCount(15);
			userListPanel.add("Center", listPanel);
		disp.add("East", userListPanel);
		disp.pack();
		disp.setResizable(false);
		disp.setLocationRelativeTo(null);
	}
	
	private void dialogset() {
		JPanel p1 = new JPanel(new BorderLayout());
			JPanel p3 = new JPanel(new GridLayout(1, 1));
				p3.add(new JLabel("������ : ", SwingConstants.RIGHT));
			p1.add("West", p3);
			JPanel p4 = new JPanel(new GridLayout(1, 1));
				JPanel p5 = new JPanel(new FlowLayout());
					p5.add(ditf);
				p4.add(p5);
			p1.add("Center", p4);
		makeroomd.add("Center", p1);
		JPanel p2 = new JPanel(new FlowLayout());
			p2.add(makebt);
			p2.add(canclebt);
		makeroomd.add("South", p2);
		makeroomd.pack();
		makeroomd.setLocationRelativeTo(null);
		
		
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		//=============================================�α׾ƿ� ��ư================================
		if(e.getSource() == exitbt) {
			String sendmsg = new String("200:" + id);
			try {
				oos.writeObject(sendmsg);
				disp.setVisible(false);
				new ReadMindLoginDisp();
				return;
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		//=============================================================ä���Է�===============================
		if(e.getSource() == inputbt) {
			if(chattingField.getText().length() == 0) {
				return;
			}
			String sendmsg = "300:" + id + ":0:" + chattingField.getText();
			try {
				oos.writeObject(sendmsg);
				chattingField.setText("");
				return;
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		//==================================================================��������̾�α׹�ư=============================
		if(e.getSource() == makeroombt) {
			makeroomd.setVisible(true);
			return;
		}
		
		//===========================================================================�������ҹ�ư=============================
		if(e.getSource() == canclebt) {
			ditf.setText("");
			makeroomd.setVisible(false);
			return;
		}
		
		//=========================================================================�����!!@~@@!@~~========================
		if(e.getSource() == makebt) {
			if(ditf.getText().length() == 0) {
				JOptionPane.showMessageDialog(makeroomd, "�������� �Է��ϼ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String roomtitle = new String(ditf.getText());
			try {
				oos.writeObject("402:" + roomtitle);
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
		
		if(e.getSource() == chattingField) {
			inputbt.doClick();
			return;
		}
		if(e.getSource() == ditf) {
			makebt.doClick();
			return;
		}
	}
	//===================================================================================���콺�̺�Ʈ==========================
	@Override
	public void mouseClicked(MouseEvent e) {
		//=================================================================================�����========================================
		if(e.getSource() == roomlist) {
			if(e.getClickCount() == 2) {
				String roomnum = (String)roomlist.getSelectedValue();
				roomnum = roomnum.substring(0, 1);
				int n1 = Integer.parseInt(roomnum) -1;
				String sendmsg = "403:" + n1;
				try {
					oos.writeObject(sendmsg);
				}catch(IOException ee) {
					ee.printStackTrace();
				}
			}
		}
		
		
		if(e.getSource() == userlist){
			if(e.getClickCount() == 2) {
				String msg = "405:" + (String)userlist.getSelectedValue();
				try {
					oos.writeObject(msg);
				}catch(IOException ee) {
					ee.printStackTrace();
				}
			}
		}
		
	}
	//=======================================================================================��������Ʈ��������================================
	private void getUserList() {
		try {
			oos.writeObject("400");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//======================================================================================���ϰ�������=====================================
	private void getRoomList() {
		try {
			oos.writeObject("401");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void eventset() {
		exitbt.addActionListener(this);
		inputbt.addActionListener(this);
		makeroombt.addActionListener(this);
		makebt.addActionListener(this);
		canclebt.addActionListener(this);
		chattingField.addActionListener(this);
		ditf.addActionListener(this);
		
		roomlist.addMouseListener(this);
		userlist.addMouseListener(this);
	}
	


	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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