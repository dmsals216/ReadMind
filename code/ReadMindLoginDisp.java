package ReadMindPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ReadMindLoginDisp implements ActionListener{
	private ImageIcon im;
	private JFrame loginDisp = new JFrame("�α���");
	private JLabel IDL = new JLabel("  ��  ��  �� :", SwingConstants.RIGHT);
	private JLabel PWL = new JLabel("  ��й�ȣ :", SwingConstants.RIGHT);
	private JTextField IDt = new JTextField(15);
	private JPasswordField PSt = new JPasswordField(15);
	private JButton bt1 = new JButton("�α���");
	private JButton bt2 = new JButton("ȸ������");
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//==========================������=============================
	public ReadMindLoginDisp(){
		networkset();
		dispset();
		eventset();
		loginDisp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginDisp.setResizable(false);
		loginDisp.setVisible(true);
	}
	//=======================ȭ�鼳��===============================
	private void dispset(){ // ȭ�� ���� �޼���
		im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/main.png");
		JPanel mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(im.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		mainPanel.setLayout(new BorderLayout());
		JPanel loginPanel = new JPanel(new BorderLayout());
			JPanel lopanel = new JPanel();
				JPanel p1 = new JPanel(new GridLayout(2,1));
					p1.add(IDL);
					p1.add(PWL);
			lopanel.add("East", p1);
				JPanel p2 = new JPanel(new GridLayout(2,1));
					p2.add(IDt);
					p2.add(PSt);
			lopanel.add("Center", p2);
		loginPanel.add("Center", lopanel);
			JPanel btpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				btpanel.add(bt1);
				btpanel.add(bt2);
		loginPanel.add("South", btpanel);
		loginPanel.setBackground(new Color(255, 0, 0, 0));
		lopanel.setBackground(new Color(255, 0, 0, 0));
		p1.setBackground(new Color(255, 0, 0, 0));
		p2.setBackground(new Color(255, 0, 0, 0));
		btpanel.setBackground(new Color(255, 0, 0, 0));
		mainPanel.add("South", loginPanel);
		loginDisp.setContentPane(mainPanel);
		loginDisp.setSize(865, 640);
		loginDisp.setLocationRelativeTo(null);
	}
	//================================Action�̺�Ʈ========================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		//============================ȸ�����Թ�ưŬ��==========================
		if(e.getSource() == bt2) {
			IDt.setText("");
			PSt.setText("");
			new ReadMindNewMemberDisp(s, ois, oos);
			return;
		}
		
		//==============================================================�α��ν�==========================
		if(e.getSource() == bt1) {
			String id = new String(IDt.getText());
			String pw = new String(PSt.getPassword());
			if(id.length() == 0) {
				JOptionPane.showMessageDialog(loginDisp, "���̵� �Է��� �ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(pw.length() == 0) {
				JOptionPane.showMessageDialog(loginDisp, "��й�ȣ�� �Է��� �ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String sendmsg = "100:" + id + ":" + pw;
			try {
				oos.writeObject(sendmsg);
				String getmsg = (String)ois.readObject();
				String[] getmsgs = getmsg.split(":");
				if(getmsgs[0].equals("1")) {
					String id1 = new String(getmsgs[1]);
					String name = new String(getmsgs[2]);
					String image = new String(getmsgs[4]);
					new ReadMindWaitingRoomDisp(id1, name, image, s, oos, ois);
					loginDisp.setVisible(false);
					return;
				}else if(getmsgs[0].equals("2")) {
					JOptionPane.showMessageDialog(loginDisp, "�α��� �����Դϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(getmsgs[0].equals("3")) {
					JOptionPane.showMessageDialog(loginDisp, "���̵�� ��й�ȣ�� Ȯ�����ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}catch(IOException ee) {
				ee.printStackTrace();
			}catch(ClassNotFoundException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == IDt) {
			bt1.doClick();
			return;
		}
		if(e.getSource() == PSt) {
			bt1.doClick();
			return;
		}
	}
	
	
	
	//=============================�̺�Ʈ ����===================================
	private void eventset() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		IDt.addActionListener(this);
		PSt.addActionListener(this);
	}
	
	
	
	
	//==============================��Ʈ��ũ����=================================
	private void networkset() {
		try {
			s = new Socket("localhost", 5000);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		}catch(IOException e) {
			JOptionPane.showMessageDialog(loginDisp, "������ �������� �ʽ��ϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
	}
	
	public static void main(String[] args) {
		new ReadMindLoginDisp();
	}
}