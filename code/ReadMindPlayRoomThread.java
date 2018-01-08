package ReadMindPackage;

import java.awt.Color;
import java.awt.GridLayout;
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
import javax.swing.JTextArea;

public class ReadMindPlayRoomThread extends Thread{
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private JPanel user2;
	private String user2info = "";
	private JPanel user3;
	private String user3info = "";
	private JPanel user4;
	private String user4info = "";
	private JPanel user5;
	private String user5info = "";
	private JPanel user6;
	private String user6info = "";
	private JTextArea ta;
	private ReadMindCanvasControl cs;
	private String id;
	private ReadMindPlayRoomDisp frame;
	private boolean check;
	private JButton gamestartbt;
	private String example;
	private String example1;
	private String example2;
	private String example3;
	private String example4;
	private String example5;
	private JLabel examplela;
	private int gamesu = 5;
	private JButton blackbt;
	private JButton redbt;
	private JButton greenbt;
	private JButton bluebt;
	private JButton ergbt;
	private JFrame waittingroom;
	private JButton resetbt;
	
	public ReadMindPlayRoomThread() {}
	public ReadMindPlayRoomThread(ReadMindPlayRoomDisp frame, JPanel user2, JPanel user3, JPanel user4, JPanel user5, JPanel user6, JTextArea ta, ReadMindCanvasControl cs, Socket s, ObjectInputStream ois, ObjectOutputStream oos, String id, boolean check, JButton gamestartbt, JLabel examplela, JButton blackbt, JButton redbt, JButton greenbt, JButton bluebt, JButton ergbt, JButton resetbt){
		this.frame = frame;
		this.s = s;
		this.oos = oos;
		this.ois = ois;
		this.user2 = user2;
		this.user3 = user3;
		this.user4 = user4;
		this.user5 = user5;
		this.user6 = user6;
		this.ta = ta;
		this.cs = cs;
		this.id = id;
		this.check = check;
		this.gamestartbt = gamestartbt;
		this.examplela = examplela;
		this.blackbt = blackbt;
		this.redbt = redbt;
		this.greenbt = greenbt;
		this.bluebt = bluebt;
		this.ergbt = ergbt;
		this.resetbt = resetbt;
	}
	
	public synchronized void run() {
		while(true) {
			try {
				Object getObject = ois.readObject();
				if(getObject instanceof String) {
					String getmsg = (String)getObject;
					String[] getmsgs = getmsg.split(":");
					ReadMindProtocol protocol = new ReadMindProtocol();
					
					//=================================================================================한명이 들어왔을때===============================
					if(getmsgs[0].equals(protocol.GAMEIN)) {
						String[] clientinfo = getmsgs[1].split("!");
						//id, name, image
						if(check == true) {
							gamestartbt.setEnabled(true);
						}
						ImageIcon im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/" + clientinfo[2] + ".png");
						JLabel imla = new JLabel(im);
						if(user2info.length() == 0) {
							user2info = clientinfo[0];
							user2.add("West", imla);
							JPanel p1 = new JPanel(new GridLayout(2,1));
							p1.add(new JLabel(clientinfo[1]));
							p1.add(new JLabel(clientinfo[0]));
							user2.add("Center", p1);
							frame.setSize(1200,601);
						}else if(user3info.length() == 0) {
							user3info = clientinfo[0];
							user3.add("West", imla);
							JPanel p1 = new JPanel(new GridLayout(2,1));
							p1.add(new JLabel(clientinfo[1]));
							p1.add(new JLabel(clientinfo[0]));
							user3.add("Center", p1);
							frame.setSize(1200,602);
						}else if(user4info.length() == 0) {
							user4info = clientinfo[0];
							user4.add("West", imla);
							JPanel p1 = new JPanel(new GridLayout(2,1));
							p1.add(new JLabel(clientinfo[1]));
							p1.add(new JLabel(clientinfo[0]));
							user4.add("Center", p1);
							frame.setSize(1200,603);
						}else if(user5info.length() == 0) {
							user5info = clientinfo[0];
							user5.add("West", imla);
							JPanel p1 = new JPanel(new GridLayout(2,1));
							p1.add(new JLabel(clientinfo[1]));
							p1.add(new JLabel(clientinfo[0]));
							user5.add("Center", p1);
							frame.setSize(1200,604);
						}else if(user6info.length() == 0) {
							user6info = clientinfo[0];
							user6.add("West", imla);
							JPanel p1 = new JPanel(new GridLayout(2,1));
							p1.add(new JLabel(clientinfo[1]));
							p1.add(new JLabel(clientinfo[0]));
							user6.add("Center", p1);
							frame.setSize(1200,605);
						}
						frame.pack();
					}
					
					///=====================================================채팅============================
					if(getmsgs[0].equals(protocol.CHATTING)) {
						String imsi = new String(getmsgs[1] + "  :  " + getmsgs[2]);
						if(ta.getText().equals("")) {
							ta.setText(ta.getText() + imsi);
							ta.setCaretPosition(ta.getText().length());
						}else {
							ta.setText(ta.getText() + "\n" + imsi);
							ta.setCaretPosition(ta.getText().length());
						}
					}
					
					//=========================================================================처음들어왔을때 ================================
					if(getmsgs[0].equals(protocol.GAMEROOMUSER)) {
						String[] imsi = getmsg.split(":");
						for(int i = 1; i < imsi.length; i++) {
							String[] imsi2 = imsi[i].split("!");
							ImageIcon im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/" + imsi2[2] + ".png");
							JLabel imla = new JLabel(im);
							if(user2info.length() == 0) {
								user2info = imsi2[0];
								user2.add("West", imla);
								JPanel p1 = new JPanel(new GridLayout(2,1));
								p1.add(new JLabel(imsi2[1]));
								p1.add(new JLabel(imsi2[0]));
								user2.add("Center", p1);
								frame.setSize(1200,601);
							}else if(user3info.length() == 0) {
								user3info = imsi2[0];
								user3.add("West", imla);
								JPanel p1 = new JPanel(new GridLayout(2,1));
								p1.add(new JLabel(imsi2[1]));
								p1.add(new JLabel(imsi2[0]));
								user3.add("Center", p1);
								frame.setSize(1200,602);
							}else if(user4info.length() == 0) {
								user4info = imsi2[0];
								user4.add("West", imla);
								JPanel p1 = new JPanel(new GridLayout(2,1));
								p1.add(new JLabel(imsi2[1]));
								p1.add(new JLabel(imsi2[0]));
								user4.add("Center", p1);
								frame.setSize(1200,603);
							}else if(user5info.length() == 0) {
								user5info = imsi2[0];
								user5.add("West", imla);
								JPanel p1 = new JPanel(new GridLayout(2,1));
								p1.add(new JLabel(imsi2[1]));
								p1.add(new JLabel(imsi2[0]));
								user5.add("Center", p1);
								frame.setSize(1200,604);
							}else if(user6info.length() == 0) {
								user6info = imsi2[0];
								user6.add("West", imla);
								JPanel p1 = new JPanel(new GridLayout(2,1));
								p1.add(new JLabel(imsi2[1]));
								p1.add(new JLabel(imsi2[0]));
								user6.add("Center", p1);
								frame.setSize(1200,605);
							}
							frame.pack();
						}
					}
					if(getmsgs[0].equals(protocol.GAMESTART)) {
						example1 = getmsgs[1];
						example2 = getmsgs[2];
						example3 = getmsgs[3];
						example4 = getmsgs[4];
						example5 = getmsgs[5];
						if(check == true) { 
							examplela.setText("제시어 : " + example1);
							resetbt.setEnabled(true);
							cs.setEnabled(true);
						}
					}
					if(getmsgs[0].equals(protocol.GAMEENTER)) {
						if(gamesu == 5) {
							example = example1;
							if(getmsgs[1].equals(example1)) {
								gamesu--;
								String msg = getmsgs[2] + "님이 정답을 맞췄습니다. \n" + gamesu + "번 남았습니다.";
								JOptionPane.showMessageDialog(frame, msg, "메세지", JOptionPane.INFORMATION_MESSAGE);
								cs.setFirst();
								if(check == true) examplela.setText("제시어 : " + example2);
							}
						}else if(gamesu == 4) {
							example = example2;
							if(getmsgs[1].equals(example2)) {
								gamesu--;
								String msg = getmsgs[2] + "님이 정답을 맞췄습니다. \n" + gamesu + "번 남았습니다.";
								JOptionPane.showMessageDialog(frame, msg, "메세지", JOptionPane.INFORMATION_MESSAGE);
								cs.setFirst();
								if(check == true) examplela.setText("제시어 : " + example3);
							}
						}else if(gamesu == 3) {
							example = example3;
							if(getmsgs[1].equals(example3)) {
								gamesu--;
								String msg = getmsgs[2] + "님이 정답을 맞췄습니다. \n" + gamesu + "번 남았습니다.";
								JOptionPane.showMessageDialog(frame, msg, "메세지", JOptionPane.INFORMATION_MESSAGE);
								cs.setFirst();
								if(check == true) examplela.setText("제시어 : " + example4);
							}
						}else if(gamesu == 2) {
							example = example4;
							if(getmsgs[1].equals(example4)) {
								gamesu--;
								String msg = getmsgs[2] + "님이 정답을 맞췄습니다. \n" + gamesu + "번 남았습니다.";
								JOptionPane.showMessageDialog(frame, msg, "메세지", JOptionPane.INFORMATION_MESSAGE);
								cs.setFirst();
								if(check == true) examplela.setText("제시어 : " + example5);
							}
						}else if(gamesu == 1) {
							example = example5;
							if(getmsgs[1].equals(example5)) {
								String msg = getmsgs[2] + "님이 정답을 맞췄습니다. \n게임이 끝났습니다.";
								JOptionPane.showMessageDialog(frame, msg, "메세지", JOptionPane.INFORMATION_MESSAGE);
								cs.setFirst();
							}
							if(check == true) {
								gamestartbt.setEnabled(true);
								examplela.setText("제시어 : ");
								cs.setEnabled(false);
							}
							try{
								oos.writeObject("504");
							}catch(IOException ee) {
								ee.printStackTrace();
							}
							gamesu = 5;
						}
					}
					if(getmsgs[0].equals(protocol.DRAG)) {
						if(check == true) continue;
						cs.drag(getmsgs);
					}
					if(getmsgs[0].equals(protocol.PRESS)) {
						if(check == true) continue;
						cs.press(getmsgs);
					}
					if(getmsgs[0].equals(protocol.RELEASE)) {
						if(check == true) continue;
						cs.release(getmsgs);
					}
					if(getmsgs[0].equals(protocol.RESET)) {
						cs.setFirst();
					}
					if(getmsgs[0].equals(protocol.EXIT_GAME)) {
						frame.setVisible(false);
						new ReadMindWaitingRoomDisp(getmsgs[1], getmsgs[2], getmsgs[3], s, oos, ois);
						return;
					}
					if(getmsgs[0].equals(protocol.EXIT_GAMEA)) {
						if(getmsgs[1].equals(user2info)) {
							user2info = "";
							user2.removeAll();
						}else if(getmsgs[1].equals(user3info)) {
							user3info = "";
							user3.removeAll();
						}else if(getmsgs[1].equals(user4info)) {
							user4info = "";
							user4.removeAll();
						}else if(getmsgs[1].equals(user5info)) {
							user5info = "";
							user5.removeAll();
						}else if(getmsgs[1].equals(user6info)) {
							user6info = "";
							user6.removeAll();
						}
						frame.setSize(1200,600);
						frame.pack();
					}
					if(getmsgs[0].equals(protocol.SETCOLOR)) {
						if(getmsgs[1].equals("black")) {
							cs.setColor(Color.black);
						}if(getmsgs[1].equals("red")) {
							cs.setColor(Color.red);
						}if(getmsgs[1].equals("green")) {
							cs.setColor(Color.green);
						}if(getmsgs[1].equals("blue")) {
							cs.setColor(Color.blue);
						}
					}
				}
			}catch(IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}