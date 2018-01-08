package ReadMindPackage;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class ReadMindNewMemberDisp extends JFrame implements ActionListener, KeyListener{
	private JTextField nametf = new JTextField(15);
	private JTextField idtf = new JTextField(10);
	private JButton idcheckbt = new JButton("�ߺ� Ȯ��");
	private boolean idcheck = false;
	//=========== 0�̸� false 1�̸� true
	private JPasswordField pwtf1 = new JPasswordField(10);
	private JLabel pwlabel = new JLabel("8 ~ 16���ڷ� �Է��Ͻÿ�.");
	private JPasswordField pwtf2 = new JPasswordField(10);
	private JLabel pwchecklabel = new JLabel(" ");
	private boolean pwcheck = false;
	private String[] phonenum = { "010", "011", "012", "017", "018", "019", "02", "031", "032", "033" };
	private JComboBox <String> tel1 = new JComboBox<String>(phonenum);
	private JTextField tel2 = new JTextField(4);
	private JTextField tel3 = new JTextField(4);
	private JButton agree = new JButton("����");
	private JButton no = new JButton("���");
	
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	//=========================================================================������================================
	public ReadMindNewMemberDisp(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
		super("ȸ������");
		this.s = s;
		this.oos = oos;
		this.ois = ois;
		dispset();
		eventset();
		setVisible(true);
	}
	//=======================================================================ȭ�鼳��=================================
	private void dispset() {
		JPanel eastPanel = new JPanel(new GridLayout(5,1));
			eastPanel.add(new JLabel("��    �� : ", SwingConstants.RIGHT));
			eastPanel.add(new JLabel("���̵� : ", SwingConstants.RIGHT));
			eastPanel.add(new JLabel("��й�ȣ : ", SwingConstants.RIGHT));
			eastPanel.add(new JLabel("��й�ȣ Ȯ�� : ", SwingConstants.RIGHT));
			eastPanel.add(new JLabel("��ȭ��ȣ : ", SwingConstants.RIGHT));
		add("West", eastPanel);
		JPanel centerPanel = new JPanel(new GridLayout(5,1));
			JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				p1.add(nametf);
			centerPanel.add(p1);
			JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				p2.add(idtf);
				p2.add(idcheckbt);
			centerPanel.add(p2);
			JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				p3.add(pwtf1);
				p3.add(pwlabel);
			centerPanel.add(p3);
			JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				p4.add(pwtf2);
				p4.add(pwchecklabel);
			centerPanel.add(p4);
			JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				p5.add(tel1);
				p5.add(new JLabel("-", SwingConstants.CENTER));
				p5.add(tel2);
				p5.add(new JLabel("-", SwingConstants.CENTER));
				p5.add(tel3);
			centerPanel.add(p5);
		add("Center", centerPanel);
		JPanel southPanel = new JPanel(new FlowLayout());
			southPanel.add(agree);
			southPanel.add(no);
		add("South", southPanel);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	
	
	//=============================================================================Ű���� �Էµɶ����� �̺�Ʈ==============================
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == idtf) {
			idcheck = false;
			return;
		}
		if(e.getSource() == pwtf1) {
			pwcheck = false;
			String pw1 = new String(pwtf1.getPassword());
			String pw2 = new String(pwtf2.getPassword());
			if(!pw1.equals(pw2)) {
				pwchecklabel.setText("���� ��ġ���� �ʽ��ϴ�.");
			}
			if(pw1.equals(pw2)) {
				pwchecklabel.setText("���� ��ġ�մϴ�.");
				pwcheck = true;
			}
			if(pwtf1.getPassword().length < 8 || pwtf1.getPassword().length > 16) {
				pwlabel.setText("8 ~ 16���ڷ� �Է��Ͻÿ�.");
				return;
			}
			if(pwtf1.getPassword().length >= 8 && pwtf1.getPassword().length <= 16) {
				pwlabel.setText("��й�ȣ ��밡��");
				return;
			}
		}
		if(e.getSource() == pwtf2) {
			pwcheck = false;
			String pw1 = new String(pwtf1.getPassword());
			String pw2 = new String(pwtf2.getPassword());
			if(!pw1.equals(pw2)) {
				pwchecklabel.setText("���� ��ġ���� �ʽ��ϴ�.");
				return;
			}
			if(pw1.equals(pw2)) {
				pwchecklabel.setText("���� ��ġ�մϴ�.");
				pwcheck = true;
				return;
			}
		}
	}
	
	
	
	
	//================================================================================�����̺�Ʈ===============================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		//============================================================================���̵�üũ ============================
		if(e.getSource() == idcheckbt) {
			String id = idtf.getText();
			if(id.length() == 0) {
				JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(id.length() < 4 || id.length() > 15) {
				JOptionPane.showMessageDialog(this, "4 ~ 15���� ���� �Է��Ͻÿ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String sendmsg = "102:" + id;
			try {
				oos.writeObject(sendmsg);
				boolean check = (boolean)ois.readObject();
				if(check == true) {
					JOptionPane.showMessageDialog(this, "��� ������ ���̵��Դϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					idcheck = true;
					return;
				}else if(check == false) {
					JOptionPane.showMessageDialog(this, "����� �� ���� ���̵��Դϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}catch(IOException ee) {
				ee.printStackTrace();
			}catch(ClassNotFoundException ee) {
				ee.printStackTrace();
			}
		}
		
		
		
		//===============================================================================��ҹ�ư==============================
		if(e.getSource() == no) {
			setVisible(false);
			return;
		}
		
		
		//==============================================================================ȸ������ ��ư================================
		if(e.getSource() == agree) {
			String name = new String(nametf.getText());
			String id = new String(idtf.getText());
			String pw = new String(pwtf1.getPassword());
			String tel = new String((String)tel1.getSelectedItem() + "-" + tel2.getText() + "-" + tel3.getText());
			//================================================================================ȸ�����Խ� Ȯ���ؾ��Ұ͵� ================
			if(name.length() == 0) {
				JOptionPane.showMessageDialog(this, "�̸��� �Է��ϼ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(idcheck == false) {
				JOptionPane.showMessageDialog(this, "���̵� �ߺ�Ȯ���� �ϼ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(pwcheck == false) {
				JOptionPane.showMessageDialog(this, "��й�ȣ�� Ȯ���� �ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(tel2.getText().length() == 0 || tel3.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "��ȭ��ȣ�� �Է��� �ּ���.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//==================================================================================������ ������======================
			String sendmsg = new String("101:" + name + ":" + id + ":" + pw + ":" + tel);
			try {
				oos.writeObject(sendmsg);
				boolean check = (boolean)ois.readObject();
				if(check == true) {
					JOptionPane.showMessageDialog(this, "ȸ�������� �Ϸ�ƽ��ϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					return;
				}
				if(check == false) {
					JOptionPane.showMessageDialog(this, "ȸ�������� �ȵƽ��ϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}catch(IOException ee) {
				ee.printStackTrace();
			}catch(ClassNotFoundException ee) {
				ee.printStackTrace();
			}
		}
		
		
		
		if(e.getSource() == nametf) {
			agree.doClick();
			return;
		}
		
		if(e.getSource() == idtf) {
			agree.doClick();
			return;
		}
		
		if(e.getSource() == pwtf1) {
			agree.doClick();
			return;
		}
		
		if(e.getSource() == pwtf2) {
			agree.doClick();
			return;
		}
		
		if(e.getSource() == tel2) {
			agree.doClick();
			return;
		}
		
		if(e.getSource() == tel3) {
			agree.doClick();
			return;
		}
				
	}
	
	
	//=====================================�̺�Ʈ ����========================
	private void eventset() {
		idcheckbt.addActionListener(this);
		agree.addActionListener(this);
		no.addActionListener(this);
		nametf.addActionListener(this);
		idtf.addActionListener(this);
		pwtf1.addActionListener(this);
		pwtf2.addActionListener(this);
		tel2.addActionListener(this);
		tel3.addActionListener(this);
		
		
		idtf.addKeyListener(this);
		pwtf1.addKeyListener(this);
		pwtf2.addKeyListener(this);
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}