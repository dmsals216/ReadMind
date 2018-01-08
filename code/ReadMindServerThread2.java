package ReadMindPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextArea;

public class ReadMindServerThread2 extends Thread {
	boolean gamecheck = false;
	int number = -1;
	int roomsu;
	String iden;
	String name;
	String image;
	String clientinfo;
	String[] rooms;
	private Socket s;
	private JTextArea serverta;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int examplesu;
	private ArrayList<ReadMindServerThread2> clientList = new ArrayList<ReadMindServerThread2>();
	
	public ReadMindServerThread2() {}
	public ReadMindServerThread2(Socket s, JTextArea serverta, ArrayList<ReadMindServerThread2> clientList, int roomsu, String[] rooms, int examplesu) {
		this.s = s;
		this.serverta = serverta;
		this.clientList = clientList;
		this.roomsu = roomsu;
		this.rooms = rooms;
		this.examplesu = examplesu;
	}
	
	public synchronized void run() {
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			while(true) {
				Object getObject = ois.readObject();
				if(getObject instanceof String) {
					String getmsg = (String)getObject;
					String[] getmsgs = getmsg.split(":");
					ReadMindProtocol protocol = new ReadMindProtocol();
					
					
					//=====================================================================id check ��=========================================
					if(getmsgs[0].equals(protocol.ID_CHECK)) {
						boolean check = idcheck(getmsgs[1]);
						oos.writeObject(check);
					}
					
					//=======================================================================ȸ�����Խ�=======================================
					if(getmsgs[0].equals(protocol.NEW_MEMBER)) {
						boolean check = newmember(getmsgs);
						oos.writeObject(check);
					}
					
					//=======================================================================�α��ν�============================================
					if(getmsgs[0].equals(protocol.LOGIN)) {
						String check = login(getmsgs);
						oos.writeObject(check);
					}
					
					//=======================================================================�α׾ƿ���=============================================
					if(getmsgs[0].equals(protocol.EXIT)) {
						exit(getmsgs);
					}
					
					//=======================================================================ä�� ������========================================
					if(getmsgs[0].equals(protocol.CHATTING)) {
						chatting(getmsgs);
					}
					
					//=========================================================================������Ϻ�����=========================================
					if(getmsgs[0].equals(protocol.USERLIST)) {
						userList();
					}
					
					//==============================================================================�游���===========================================
					if(getmsgs[0].equals(protocol.MAKEROOM)) {
						makeroom(getmsgs);
					}

					//=============================================================================�溸����============================================
					if(getmsgs[0].equals(protocol.ROOMLIST)) {
						roomList();
					}
					
					//=============================================================================�����============================================
					if(getmsgs[0].equals(protocol.INTOROOM)) {
						intoroom(getmsgs);
					}
					
					//===============================================================================���ӽ���============================================
					if(getmsgs[0].equals(protocol.GAMESTART)) {
						gamestart();
					}
					
					//==============================================================================�����Է�============================================
					if(getmsgs[0].equals(protocol.GAMEENTER)) {
						gameenter(getmsg);
					}
					
					//==============================================================================������==============================================
					if(getmsgs[0].equals(protocol.PRESS)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								try {
									t.oos.writeObject(getmsg);
								}catch(IOException ee) {
									ee.printStackTrace();
								}
							}
						}
					}
					
					//==============================================================================�巡��===================================================
					if(getmsgs[0].equals(protocol.DRAG)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								try {
									t.oos.writeObject(getmsg);
								}catch(IOException ee) {
									ee.printStackTrace();
								}
							}
						}
					}
					
					
					//================================================================================������==================================================
					if(getmsgs[0].equals(protocol.DRAG)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								try {
									t.oos.writeObject(getmsg);
								}catch(IOException ee) {
									ee.printStackTrace();
								}
							}
						}
					}
					
					///====================================================����=====
					if(getmsgs[0].equals(protocol.RESET)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								try {
									t.oos.writeObject(getmsg);
								}catch(IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					
					
					//==========================================================================================���ӹ� ������==================================
					if(getmsgs[0].equals(protocol.EXIT_GAME)) {
						// iden, name, image
						try {
							oos.writeObject("201:" + iden + ":" + name + ":" + image);
						}catch(IOException ee) {
							ee.printStackTrace();
						}
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								t.oos.writeObject("202:" + iden);
							}
						}
						int a = Integer.parseInt(getmsgs[1]) - 1;
						int b = Integer.parseInt(rooms[a].substring(rooms[a].length() - 1));
						if(b-1 == 0) rooms[a] = ""; 
						else rooms[a] = rooms[a].substring(0, rooms[a].length()-1) + (b-1);
						number = 0;
						roomList();
					}
					if(getmsgs[0].equals(protocol.USERINFO)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number != -1) {
								if(t.iden.equals(getmsgs[1])) {
									oos.writeObject("405:" + t.clientinfo);
								}
							}
						}
					}
					if(getmsgs[0].equals(protocol.GAMEEND)) {
						gamecheck = false;
					}
					if(getmsgs[0].equals(protocol.SETCOLOR)) {
						for(ReadMindServerThread2 t : clientList) {
							if(t.number == number) {
								t.oos.writeObject(getmsg);
							}
						}
					}
				}
			}
		}catch(IOException e) {
			clientList.remove(this);
			return;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
	//===========================================================================================gameenter-=====================================
	private void gameenter(String msg) {
		for(ReadMindServerThread2 t : clientList) {
			if(t.number == number) {
				try {
					t.oos.writeObject(msg);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	//=================================================================================================gamestart==========================================
	private void gamestart() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("Select * from examples");
			Random r1 = new Random();
			String msg = "";
			for(int j = 0; j < 5; j++) {
				int r = r1.nextInt(examplesu - 1);
				for(int i = 0; i < r + 1; i++) {
					rs.next();
				}
				msg += ":" + rs.getString("exam");
				rs.beforeFirst();
			}
			for(ReadMindServerThread2 t : clientList) {
				if(t.number == number) {
					t.oos.writeObject("500" + msg);
					t.gamecheck = true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	//================================================================================================�����=====================
	private void intoroom(String[] msg) {
		int a = Integer.parseInt(msg[1]);
		int b = Integer.parseInt(rooms[a].substring(rooms[a].length() - 1));
		rooms[a] = rooms[a].substring(0, rooms[a].length()-1) + (b+1);
		String ids = "";
		int c = 0;
		try {
			if(b == 6) {
				oos.writeObject("403:" + rooms[a] + ":1");
				return;
			}
			for(ReadMindServerThread2 t : clientList) {
				if(t.number == a + 1) {
					if(t.gamecheck == true) {
						c = 1;
					}
				}
			}
			if(c == 1) {
				oos.writeObject("403:" + rooms[a] + ":2");
				return;
			}
			
			oos.writeObject("403:" + rooms[a] + ":0");
			for(ReadMindServerThread2 t : clientList) {
				if(t.number == a + 1) {
					ids += ":" + t.clientinfo; 
					t.oos.writeObject("501:" + clientinfo);
				}
			}
			number = a + 1;
			roomList();
			oos.writeObject("502" + ids);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//================================================================================================�溸����======================
	private void roomList() {
		String roomss = "401";
		for(int i = 0; i < roomsu; i++) {
			if(rooms[i].equals("")) {
				continue;
			}
			roomss += ":" + rooms[i];
		}
		for(ReadMindServerThread2 t : clientList) {
			if(t.number == 0) {
				try {
					t.oos.writeObject(roomss);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//====================================================================================================�游���===========================
	private void makeroom(String[] msgs) {
		int i = 0;
		String roomss = "401";
		for(i = 0; i < roomsu; i++) {
			if(rooms[i].equals("")) {
				rooms[i] = (i+1) + "-" + msgs[1] + "-1";
				
				try {
					oos.writeObject("402:" + rooms[i]);
				}catch(IOException e) {
					e.printStackTrace();
				}
				roomss += ":" + rooms[i];
				number = i + 1;
				break;
			}
		}
		roomList();
	}
	
	
	
	
	//===============================================================================================������Ϻ�����=================================
	private void userList() {
		String ids = "400";
		for(ReadMindServerThread2 t : clientList) {
			if(t.number != -1) {
				ids += ":" + t.iden;
			}
		}
		for(ReadMindServerThread2 t : clientList) {
			if(t.number == 0) {
				try {
					t.oos.writeObject(ids);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//=================================================================================ä�ú�����======================================
	private void chatting(String[] msgs) {
		String id = msgs[1];
		String roomnum = msgs[2];
		String msg = msgs[3];
		for(ReadMindServerThread2 t : clientList) {
			if(t.number == Integer.parseInt(roomnum)) {
				String sendmsg = "300:" + id + ":" + msg;
				try {
					t.oos.writeObject(sendmsg);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	//===============================================================================�α׾ƿ���===========================================
	private void exit(String[] msgs) {
		String id = msgs[1];
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("Update members set usercheck = -1 where userid = ?");
			stmt.setString(1, id);
			stmt.executeQuery();
			number = -1;
			iden = "";
			serverta.setText(serverta.getText() + id + "���� �α׾ƿ��ϼ̽��ϴ�.\n");
			userList();
			clientList.remove(this);
			stop();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//=============================================================================�α��� ��===============================================
	private String login(String[] msgs) {
		
		String id = msgs[1];
		String pw = msgs[2];
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("Select userpw, username, usercheck, userexp, userimage from members where userid = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				if(pw.equals(rs.getString("userpw"))) {
					if(rs.getInt("usercheck") == -1) {
						number = 0;
						iden = id;
						name = rs.getString("username");
						image = rs.getString("userimage");
						clientinfo = iden + "!" + name + "!" + image;
						Connection con2 = ReadMindConnUtil.getConnection();
						PreparedStatement stmt2 = con2.prepareStatement("Update members set usercheck = 0 where userid = ?");
						stmt2.setString(1, id);
						stmt2.executeQuery();
						serverta.setText(serverta.getText() + id + "���̷α����ϼ̽��ϴ�.\n");
						return "1:" + id + ":" + rs.getString("username") + ":" + rs.getInt("userexp") + ":" + rs.getString("userimage");
					}
					else return "2";
				}
			}return "3";
		}catch(SQLException e) {
			e.printStackTrace();
			return "4";
		}
	}
	
	
	//===========================================================================ȸ�����Խ� ��� �ֱ�=============================================
	private boolean newmember(String[] msgs) {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("Insert into members values (?, ?, ?, ?, -1, 0, 'c0')");
			stmt.setString(1, msgs[1]);
			stmt.setString(2, msgs[2]);
			stmt.setString(3, msgs[3]);
			stmt.setString(4, msgs[4]);
			stmt.executeQuery();
			serverta.setText(serverta.getText() + msgs[1] + "���� ȸ�������ϼ̽��ϴ�.\n");
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	//==========================================================================���̵� üũ�� ��񿬵�===============================================
	private boolean idcheck(String id) {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("Select userid from members where userid = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return false;
			}return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}