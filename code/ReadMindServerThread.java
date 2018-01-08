package ReadMindPackage;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;


public class ReadMindServerThread extends Thread {
	private int roomsu = 50;
	private String[] rooms = new String[roomsu];
	private ServerSocket ss;
	private Socket s;
	private JTextArea serverta;
	private ArrayList<ReadMindServerThread2> clientList = new ArrayList<ReadMindServerThread2>();
	private int examplesu;
	
	public ReadMindServerThread() {}
	public ReadMindServerThread(JTextArea ta, int examplesu) {
		this.serverta = ta;
		this.examplesu = examplesu;
	}
	
	public synchronized void run() {
		for(int i = 0; i < roomsu; i++) {
			rooms[i] = "";
		}
		try {
			ss = new ServerSocket(5000);
			while(true) {
				s = ss.accept();
				ReadMindServerThread2 t = new ReadMindServerThread2(s, serverta, clientList, roomsu, rooms, examplesu);
				t.start();
				clientList.add(t);
			}
		}catch(IOException e) {
			e.getStackTrace();
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
}