package ReadMindPackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ReadMindManageClient extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	//[j][0] == 제시어들
	private String[][] exampledata;
	private JSplitPane sp1, sp2;
	private int examplesu;
	//==========================회원목록창===============================
	private String[] membercol = { "이름", "아이디", "비밀번호", "전화번호" };
	private DefaultTableModel membermodel = new DefaultTableModel(membercol, 0){
         public boolean isCellEditable(int i, int c) {
            return false;
         }
      };
	private JTable membertable = new JTable(membermodel);
	private JScrollPane mt = new JScrollPane(membertable);
	private JButton getmembertablebt = new JButton("새로고침");
	private JButton delmembertablebt = new JButton("삭제");
	//========================제시어목록창=================================
	private String[] examplecol = { "제시어" };
	private DefaultTableModel examplemodel = new DefaultTableModel(examplecol, 0){
         public boolean isCellEditable(int i, int c) {
            return false;
         }
      };
	private JTable exampletable = new JTable(examplemodel);
	private JScrollPane et = new JScrollPane(exampletable);
	private JButton delexampletablebt = new JButton("삭제");
	private JButton addexampletablebt = new JButton("추가");
	private JButton suexampletablebt = new JButton("수정");
	//=========================서버관리창=============================
	private JButton serverstartbt = new JButton("서버가동");
	//========================다이얼로그(제시어 추가)============================
	private JDialog addexampledialog = new JDialog(this, "제시어 추가", false);
	private JButton exampleaddbt = new JButton("추가");
	private JButton examplexbt = new JButton("취소");
	private JTextField example1 = new JTextField();
	private JTextField example2 = new JTextField();
	private JTextField example3 = new JTextField();
	private JTextField example4 = new JTextField();
	private JTextField example5 = new JTextField();
	private JTextField example6 = new JTextField();
	private JTextField example7 = new JTextField();
	private JTextField example8 = new JTextField();
	private JTextField example9 = new JTextField();
	private JTextField example10 = new JTextField();
	//========================다이얼로그(제시어 수정)=========================
	private JDialog suexampledialog = new JDialog(this, "제시어 수정", false);
	private JButton examplesubt = new JButton("수정");
	private JButton examplesuxbt = new JButton("취소");
	private JTextField examplesutf = new JTextField(10);
	//========================생성자============================
	public ReadMindManageClient() {
		super("관리자 모드");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispset();
		dialogset();
		modelset();
		eventset();
		setVisible(true);
	}
	//=============================화면설정==============================
	private void dispset() {
		JPanel p1 = new JPanel(new BorderLayout());
			p1.add("North", new JLabel("회원 관리",SwingConstants.CENTER));
			getmembertableset();
			p1.add("Center", mt);
			JPanel p3 = new JPanel(new FlowLayout());
				p3.add(delmembertablebt);
				p3.add(getmembertablebt);
			p1.add("South", p3);
		JPanel p2 = new JPanel(new BorderLayout());
			p2.add("North", new JLabel("제시어 관리",SwingConstants.CENTER));
			getexampletableset();
			p2.add("Center", et);
			JPanel p4 = new JPanel(new FlowLayout());
				p4.add(addexampletablebt);
				p4.add(suexampletablebt);
				p4.add(delexampletablebt);
			p2.add("South", p4);
		JPanel p5 = new JPanel(new BorderLayout());
			p5.add("North", new JLabel("서버 관리",SwingConstants.CENTER));
			JPanel p6 = new JPanel(new FlowLayout());
				p6.add(serverstartbt);
			p5.add("Center", p6);
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p2,p5);
		sp1.setDividerLocation(380);
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, sp1);
		sp2.setDividerLocation(540);
		add(sp2);
		setSize(800, 500);
		setLocationRelativeTo(null);
	}
	//=========================다이얼로그 설정====================
	private void dialogset() {
		//====================다이얼로그(제시어 추가)=============
		addexampledialog.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new BorderLayout());
			JPanel p3 = new JPanel(new GridLayout(10,1));
				p3.add(new JLabel("1. ",JLabel.RIGHT));
				p3.add(new JLabel("2. ", JLabel.RIGHT));
				p3.add(new JLabel("3. ", JLabel.RIGHT));
				p3.add(new JLabel("4. ", JLabel.RIGHT));
				p3.add(new JLabel("5. ", JLabel.RIGHT));
				p3.add(new JLabel("6. ", JLabel.RIGHT));
				p3.add(new JLabel("7. ", JLabel.RIGHT));
				p3.add(new JLabel("8. ", JLabel.RIGHT));
				p3.add(new JLabel("9. ", JLabel.RIGHT));
				p3.add(new JLabel("10. ", JLabel.RIGHT));
			p1.add("West", p3);
			JPanel p4 = new JPanel(new GridLayout(10,1));
				p4.add(example1);
				p4.add(example2);
				p4.add(example3);
				p4.add(example4);
				p4.add(example5);
				p4.add(example6);
				p4.add(example7);
				p4.add(example8);
				p4.add(example9);
				p4.add(example10);
			p1.add("Center", p4);
		addexampledialog.add("Center", p1);
		JPanel p2 = new JPanel(new FlowLayout());
			p2.add(exampleaddbt);
			p2.add(examplexbt);
		addexampledialog.add("South", p2);
		addexampledialog.setSize(300,400);
		addexampledialog.setLocationRelativeTo(null);
		//=====================다이얼로그(제시어 수정)========================
		suexampledialog.setLayout(new BorderLayout());
		JPanel pa1 = new JPanel(new FlowLayout());
			pa1.add(examplesutf);
		suexampledialog.add("Center", pa1);
		JPanel pa2 = new JPanel(new FlowLayout());
			pa2.add(examplesubt);
			pa2.add(examplesuxbt);
		suexampledialog.add("South", pa2);
		suexampledialog.pack();
		suexampledialog.setLocationRelativeTo(null);
	}
	//=============================마우스이벤트============================
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == membertable) {
			if(e.getClickCount() == 2) {
				
			}
			return;
		}
		if(e.getSource() == exampletable) {
			if(e.getClickCount() == 2) {
				suexampledialog.setVisible(true);
			}
			return;
		}
	}
	//==============================액션이벤트=============================
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == getmembertablebt) {
			getmembertableset();
			return;
		}
		if(e.getSource() == delmembertablebt) {
			if(membertable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "회원을 선택하지 않으셨습니다.","메세지", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			delmembertableset();
			return;
		}
		if(e.getSource() == addexampletablebt) {
			addexampledialog.setVisible(true);
			return;
		}
		if(e.getSource() == exampleaddbt) {
			addexampletableset();
			exampledialogset();
			addexampledialog.setVisible(false);
			return;
		}
		if(e.getSource() == examplexbt) {
			exampledialogset();
			addexampledialog.setVisible(false);
			return;
		}
		if(e.getSource() == suexampletablebt) {
			if(exampletable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "제시어를 선택하지 않으셨습니다.","메세지", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			suexampledialog.setVisible(true);
			return;
		}
		if(e.getSource() == delexampletablebt) {
			if(exampletable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "제시어를 선택하지 않으셨습니다.","메세지", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			delexampletableset();
			return;
		}
		if(e.getSource() == examplesubt) {
			suexampletableset();
			examplesutf.setText("");
			suexampledialog.setVisible(false);
			return;
		}
		if(e.getSource() == examplesuxbt) {
			examplesutf.setText("");
			suexampledialog.setVisible(false);
			return;
		}
		if(e.getSource() == serverstartbt) {
			serverstartbt.setEnabled(false);
			new ReadMindServer(examplesu);
			return;
		}
	}
	//===========================회원정보 가져오기================================
	private void getmembertableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("Select username, userid, userpw, usertel from members order by username asc");
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
			int col = membermodel.getColumnCount();
			String[][] data = new String[row][col];
			int j = 0;
			membermodel.setRowCount(0);
			while(rs.next()) {
				for(int i = 0; i < col; i++) {
					data[j][i] = rs.getString(i+1);
				}
				membermodel.addRow(data[j]);
				j++;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//=========================회원정보 삭제하기===============================
	private void delmembertableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("delete members where userid = ?");
			int select = membertable.getSelectedRow();
			String selectid = (String)membermodel.getValueAt(select, 1);
			stmt.setString(1, selectid);
			stmt.executeUpdate();
			membermodel.removeRow(select);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//=======================제시어 가져오기======================================
	private void getexampletableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select exam from examples order by exam asc");
			rs.last();
			examplesu = rs.getRow();
			int row = rs.getRow();
			rs.beforeFirst();
			int col = examplemodel.getColumnCount();
			exampledata = new String[row][col];
			int j = 0;
			examplemodel.setRowCount(0);
			while(rs.next()) {
				for(int i = 0; i < col; i++) {
					exampledata[j][i] = rs.getString(i+1);
				}
				examplemodel.addRow(exampledata[j]);
				j++;
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	//=====================제시어 추가하기================================
	private void addexampletableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("insert into examples values (?)");
			String[] exam = new String[10];
			exam[0] = example1.getText();
			exam[1] = example2.getText();
			exam[2] = example3.getText();
			exam[3] = example4.getText();
			exam[4] = example5.getText();
			exam[5] = example6.getText();
			exam[6] = example7.getText();
			exam[7] = example8.getText();
			exam[8] = example9.getText();
			exam[9] = example10.getText();
			for(int i = 0; i < 10; i++) {
				int check = 0;
				if(exam[i].equals("")) {
					continue;
				}else {
					for(int j = 0; j < examplemodel.getRowCount(); j++) {
						if(exam[i].equals(exampledata[j][0])) {
							check ++;
						}
					}
					if(check == 1) continue;
					stmt.setString(1, exam[i]);
					stmt.executeQuery();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		getexampletableset();
	}
	//===========================제시어 수정하기===============================
	private void suexampletableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("update examples set exam = ? where exam = ?");
			int select = exampletable.getSelectedRow();
			String selectexam = (String)examplemodel.getValueAt(select, 0);
			String changeexam = examplesutf.getText();
			stmt.setString(1, changeexam);
			stmt.setString(2, selectexam);
			stmt.executeUpdate();
			getexampletableset();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//===========================제시어 삭제하기===============================
	private void delexampletableset() {
		try {
			Connection con = ReadMindConnUtil.getConnection();
			PreparedStatement stmt = con.prepareStatement("delete examples where exam = ?");
			int select = exampletable.getSelectedRow();
			String selectexam = (String)examplemodel.getValueAt(select, 0);
			stmt.setString(1, selectexam);
			stmt.executeUpdate();
			examplemodel.removeRow(select);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//===========================exampledialog설정===================
	private void exampledialogset() {
		example1.setText("");
		example2.setText("");
		example3.setText("");
		example4.setText("");
		example5.setText("");
		example6.setText("");
		example7.setText("");
		example8.setText("");
		example9.setText("");
		example10.setText("");
	}
	//===================table, tablemodel설정=======================
	private void modelset() {
		membertable.getTableHeader().setReorderingAllowed(false);
		exampletable.getTableHeader().setReorderingAllowed(false);
		membertable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		exampletable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	//=====================이벤트 설정========================
	private void eventset() {
		delmembertablebt.addActionListener(this);
		getmembertablebt.addActionListener(this);
		addexampletablebt.addActionListener(this);
		suexampletablebt.addActionListener(this);
		delexampletablebt.addActionListener(this);
		serverstartbt.addActionListener(this);
		exampleaddbt.addActionListener(this);
		examplexbt.addActionListener(this);
		examplesubt.addActionListener(this);
		examplesuxbt.addActionListener(this);
		
		membertable.addMouseListener(this);
		exampletable.addMouseListener(this);
	}
	
	public static void main(String[] args) {
		new ReadMindManageClient();
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