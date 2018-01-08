package ReadMindPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ReadMindWaitingRoomThread extends Thread{
	private Socket s;
	private String id;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String name;
	private JTextArea ta;
	private JList userlist;
	private JList roomlist;
	private JFrame disp;
	private String image;
	private String[] roominfolist;
	
	public ReadMindWaitingRoomThread() {}
	public ReadMindWaitingRoomThread(JFrame disp, String id, Socket s, ObjectOutputStream oos, ObjectInputStream ois, String name, JTextArea ta, JList userlist, JList roomlist, String image){
		this.disp = disp;
		this.s = s;
		this.id = id;
		this.oos = oos;
		this.ois = ois;
		this.name = name;
		this.ta = ta;
		this.userlist = userlist;
		this.roomlist = roomlist;
		this.image = image;
	}

	
	
	public synchronized void run() {
		try {
			while(true) {
				Object getObject = ois.readObject();
				if(getObject instanceof String) {
					String getmsg = (String)getObject;
					String[] getmsgs = getmsg.split(":");
					ReadMindProtocol protocol = new ReadMindProtocol();
					
					//======================================================채팅 들어왔을때===================================
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
					
					//===============================================================유저리스트 가져오기================================
					if(getmsgs[0].equals(protocol.USERLIST)) {
						userlist.removeAll();
						String[] id = new String[getmsgs.length -1];
						for(int i = 0; i < getmsgs.length-1; i++) {
							id[i] = getmsgs[i+1];
						}
						userlist.setListData(id);
					}
					
					//==================================================================방생성======================================
					if(getmsgs[0].equals(protocol.MAKEROOM)) {
						String[] roominfo = getmsgs[1].split("-");
						new ReadMindPlayRoomDisp(disp, id, name, roominfo[0], roominfo[1], roominfo[2], s, ois, oos, image, true);
						disp.setVisible(false);
						return;
					}
					
					//=====================================================================방목록 가져오기.----========================
					if(getmsgs[0].equals(protocol.ROOMLIST)) {
						roominfolist = new String[getmsgs.length-1];
						for(int i = 1; i < getmsgs.length; i++) {
							String[] roominfo = getmsgs[i].split("-");
							String roominfo1 = roominfo[0] + "번방     " + roominfo[1] + "          (" + roominfo[2] + "/6)";
							roominfolist[i-1] = roominfo1;
						}
						roomlist.removeAll();
						roomlist.setListData(roominfolist);
					}
					
					//=========================================================================방 들어가기============================
					if(getmsgs[0].equals(protocol.INTOROOM)) {
						if(getmsgs[2].equals("0")) {
							String[] roominfo = getmsgs[1].split("-");
							new ReadMindPlayRoomDisp(disp, id, name, roominfo[0], roominfo[1], roominfo[2], s, ois, oos, image, false);
							disp.setVisible(false);
							return;
						}else if(getmsgs[2].equals("1")) {
							JOptionPane.showMessageDialog(disp, "방이 꽉찾습니다.", "메세지", JOptionPane.INFORMATION_MESSAGE);
						}else if(getmsgs[2].equals("2")) {
							JOptionPane.showMessageDialog(disp, "게임중입니다.", "메세지", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					//===========================================================================방정보 수정=========================
					if(getmsgs[0].equals(protocol.INTOGETROOMLIST)) {
						int a = Integer.parseInt(getmsgs[1]);
						int b = Integer.parseInt(getmsgs[2]);
						roominfolist[a] = roominfolist[a].substring(0, roominfolist[a].length() - 4) + b + "/6)";
						roomlist.removeAll();
						roomlist.setListData(roominfolist);
					}
					//==============================================================================유저리스트가져오기==========================
					if(getmsgs[0].equals(protocol.USERINFO)) {
						String[] a = getmsgs[1].split("!");
						ImageIcon im = new ImageIcon("C:/myProject/src/ReadMindPackage/ReadMindImage/images/" + a[2] + ".png");
						JLabel imla = new JLabel(im);
						JLabel id = new JLabel("아이디 : " + a[0]);
						JLabel name = new JLabel("이름 : " + a[1]);
						JDialog d = new JDialog(disp, a[0], false);
							JPanel p1 = new JPanel(new BorderLayout());
							p1.add(imla);
						d.add("West", p1);
							JPanel p2 = new JPanel(new GridLayout(2,1));
							p2.add(name);
							p2.add(id);
						d.add("Center", p2);
						d.setSize(230, 150);
						d.setLocationRelativeTo(null);
						d.setVisible(true);
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
