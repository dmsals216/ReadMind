package ReadMindPackage;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import java.net.*;
import java.util.*;

public class ReadMindServer extends JFrame{
	private JTextArea serverta = new JTextArea();
	private JScrollPane tasp = new JScrollPane(serverta);
	private int examplesu;
	
	public ReadMindServer(int examplesu) {
		super("ReadMindServer");
		this.examplesu = examplesu;
		dispset();
		setVisible(true);
		openserver();
	}
	
	public void openserver() {
		ReadMindServerThread t = new ReadMindServerThread(serverta, examplesu);
		t.start();
		Calendar now = Calendar.getInstance();
		serverta.setText(now.get(Calendar.YEAR) + "년" + (now.get(Calendar.MONTH)+1) + "월"
		+ now.get(Calendar.DATE) + "일    " + now.get(Calendar.HOUR) + "시"
		+ now.get(Calendar.MINUTE) + "분" + now.get(Calendar.SECOND) + "초   " 
		+ " 서버가 시작되었습니다. \n");
	}
	
	private void dispset() {
		add("Center", tasp);
		serverta.setEnabled(false);
		setSize(400,500);
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension my = getSize();
		setLocation(scr.width / 2 - my.width / 2, scr.height / 2 - my.height / 2);
	}
}
