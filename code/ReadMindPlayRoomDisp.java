package ReadMindPackage;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
//그린거 지우기 repaint()
public class ReadMindPlayRoomDisp extends JFrame implements MouseListener, 
		MouseMotionListener, ActionListener{
	//멤버
	private String roommembersu;
	private JPanel user1 = new JPanel(new BorderLayout());
	private JPanel user2 = new JPanel(new BorderLayout());
	private JPanel user3 = new JPanel(new BorderLayout());
	private JPanel user4 = new JPanel(new BorderLayout());
	private JPanel user5 = new JPanel(new BorderLayout());
	private JPanel user6 = new JPanel(new BorderLayout());
	private String roomnum;
	private String rnum = new String(" 번 방 ");
	private String roomtitle;
	private JButton blackbt = new JButton();
	private JButton redbt = new JButton();
	private JButton greenbt = new JButton();
	private JButton bluebt = new JButton();
	private JButton ergbt = new JButton();
	private JButton resetbt = new JButton("모두 지우기");
	private JTextField exampletf = new JTextField(15);
	private JButton enterbt = new JButton("입력");
	private JButton exitbt = new JButton("나가기");
	private JTextArea chattingArea = new JTextArea(5,1);
	private JScrollPane areasp = new JScrollPane(chattingArea);
	private JTextField chattingField = new JTextField();
	private JScrollPane fieldsp = new JScrollPane(chattingField);
	private JButton inputbt = new JButton("입력");
	private ReadMindCanvasControl cs = new ReadMindCanvasControl();
	private Color color = Color.black;
	private String id;
	private String name;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String image;
	private JDialog chattingd = new JDialog(this, "채팅창", false);
	private JButton gamestartbt = new JButton("게임시작");
	private JButton chattingshowbt = new JButton("채팅창 보이기");
	private boolean check;
	private JLabel examplela = new JLabel();
	private JLabel examplela2 = new JLabel("제시어 : ", SwingConstants.RIGHT);
	
	//생성자
	public ReadMindPlayRoomDisp(){}
	public ReadMindPlayRoomDisp(JFrame waitingroom, String id, String name, String roomnum, String roomtitle, String roommembersu, Socket s, ObjectInputStream ois, ObjectOutputStream oos, String image, boolean a){
		super("리드마인드");
		this.id = id;
		this.name = name;
		this.roomnum = roomnum;
		this.roomtitle = roomtitle;
		this.roommembersu = roommembersu;
		this.oos = oos;
		this.ois = ois;
		this.image = image;
		this.check = a;
		ReadMindPlayRoomThread t = new ReadMindPlayRoomThread(this, user2, user3, user4, user5, user6, chattingArea, cs, s, ois, oos, id, check, gamestartbt, examplela2, blackbt, redbt, greenbt, bluebt, ergbt, resetbt);
		t.start();
		setDisplay();
		eventset();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//메서드
	public void setDisplay(){ //화면구성 메서드
		JPanel eastPanel = new JPanel(new GridLayout(3,1));
			EtchedBorder eb = new EtchedBorder();
			user2.setPreferredSize(new Dimension(200, 150));
			user2.setBorder(eb);
			eastPanel.add(user2);
			user4.setPreferredSize(new Dimension(200, 150));
			user4.setBorder(eb);
			eastPanel.add(user4);
			user6.setPreferredSize(new Dimension(200, 150));
			user6.setBorder(eb);
			eastPanel.add(user6);
		add("East", eastPanel);
		JPanel westPanel = new JPanel(new GridLayout(3,1));
			ImageIcon im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/" + image + ".png");
			JLabel imla = new JLabel(im);
			user1.add("West", imla);
			JPanel user1Panel = new JPanel(new GridLayout(2,1));
				user1Panel.add(new JLabel(name));
				user1Panel.add(new JLabel(id));
			user1.add("Center", user1Panel);
			user1.setPreferredSize(new Dimension(200, 150));
			user1.setBorder(eb);
			westPanel.add(user1);
			user3.setPreferredSize(new Dimension(200, 150));
			user3.setBorder(eb);
			westPanel.add(user3);
			user5.setPreferredSize(new Dimension(200, 150));
			user5.setBorder(eb);
			westPanel.add(user5);
		add("West", westPanel);
		JPanel centerPanel = new JPanel(new BorderLayout());
			JLabel title = new JLabel(roomnum + rnum + roomtitle);
			centerPanel.add("North", title);
			JPanel ppp1 = new JPanel(new BorderLayout());
				ppp1.add(cs);
				cs.setEnabled(false);
				ppp1.setBorder(eb);
			centerPanel.add("Center", ppp1);
			JPanel p6 = new JPanel(new BorderLayout());
				JPanel p1 = new JPanel(new FlowLayout());
						blackbt.setBackground(Color.black);
						blackbt.setPreferredSize(new Dimension(50, 30));
						p1.add(blackbt);
						redbt.setBackground(Color.red);
						redbt.setPreferredSize(new Dimension(50, 30));
						p1.add(redbt);
						greenbt.setBackground(Color.green);
						greenbt.setPreferredSize(new Dimension(50, 30));
						p1.add(greenbt);
						bluebt.setBackground(Color.blue);
						bluebt.setPreferredSize(new Dimension(50, 30));
						p1.add(bluebt);
						p1.add(resetbt);
						resetbt.setEnabled(false);
						p1.add(gamestartbt);
						gamestartbt.setEnabled(false);
						p1.add(chattingshowbt);
				p6.add("Center", p1);
				JPanel buttonPanel = new JPanel(new BorderLayout());
					JPanel p2 = new JPanel(new BorderLayout());
						p2.add("West", examplela2);
						p2.add("Center", exampletf);
						p2.add("East", enterbt);
					buttonPanel.add("Center", p2);
					if(check == true) {
						exampletf.setVisible(false);
						enterbt.setVisible(false);
					}
					JPanel p3 = new JPanel(new FlowLayout());
						p3.add(exitbt);
					buttonPanel.add("East", p3);
				p6.add("South",buttonPanel);
			centerPanel.add("South", p6);
		add("Center", centerPanel);
		
		
		chattingd.add("Center", areasp);
		JPanel d1 = new JPanel(new BorderLayout());
			d1.add("Center", fieldsp);
			d1.add("East", inputbt);
		chattingd.add("South", d1);
		chattingd.setSize(400,500);
		chattingd.setLocation(1450, 250);
		chattingd.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == inputbt) {
			if(chattingField.getText().length() == 0) {
				return;
			}
			String msg = "300:" + id + ":" + roomnum + ":" + chattingField.getText();
			chattingField.setText("");
			try {
				oos.writeObject(msg);
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
		if(e.getSource() == gamestartbt) {
			gamestartbt.setEnabled(false);
			try {
				oos.writeObject("500");
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
		if(e.getSource() == enterbt) {
			if(exampletf.getText().length() == 0) {
				exampletf.setText("");
				return;
			}
			String msg = exampletf.getText();
			try {
				exampletf.setText("");
				oos.writeObject("503:" + msg + ":" + id);
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
		
		if(e.getSource() == exitbt) {
			try {
				oos.writeObject("201:" + roomnum);
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
		
		
		
		
		if(e.getSource() == chattingshowbt) {
			chattingd.setVisible(true);
		}
		if(e.getSource() == chattingField) {
			inputbt.doClick();
		}
		
		if(e.getSource() == blackbt) {
			try {
				oos.writeObject("505:black");
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == redbt) {
			try {
				oos.writeObject("505:red");
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == greenbt) {
			try {
				oos.writeObject("505:green");
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == bluebt) {
			try {
				oos.writeObject("505:blue");
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == resetbt) {
			String msg = "603";
			try {
				oos.writeObject(msg);
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		if(e.getSource() == exampletf) {
			enterbt.doClick();
		}
	}
	
	private void eventset() {
		exitbt.addActionListener(this);
		inputbt.addActionListener(this);
		chattingField.addActionListener(this);
		chattingshowbt.addActionListener(this);
		gamestartbt.addActionListener(this);
		enterbt.addActionListener(this);
		blackbt.addActionListener(this);
		redbt.addActionListener(this);
		greenbt.addActionListener(this);
		bluebt.addActionListener(this);
		resetbt.addActionListener(this);
		exampletf.addActionListener(this);
		cs.addMouseListener(this);
		cs.addMouseMotionListener(this);
	}
		
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getSource() == cs) {
			try {
				oos.writeObject("600:" + e.getX() + ":" + e.getY());
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		if(e.getSource() == cs) {
			try {
				oos.writeObject("601:" + e.getX() + ":" + e.getY());
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == cs) {
			try {
				oos.writeObject("602:" + e.getX() + ":" + e.getY());
			}catch(IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}
