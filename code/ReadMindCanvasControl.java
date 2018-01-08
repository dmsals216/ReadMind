package ReadMindPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;

class DrawInfo implements Serializable{
	private int x;
	private int y;
	private int x1;
	private int y1;
	private Color color;
	

	public DrawInfo(int x, int y, int x1, int y1, Color color){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;

		
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setX1(int x1){
		this.x1 = x1;		
	}

	public void setY1(int y1){
		this.y1 = y1;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getX1(){
		return x1;
	}

	public int getY1(){
		return y1;
	}

	public Color getColor(){
		return color;
	}

}

public class ReadMindCanvasControl extends Canvas implements MouseListener, MouseMotionListener{
	private int x,y,x1,y1;
	private Color color = new Color(0, 0, 0);
	private ArrayList<DrawInfo> vc = new ArrayList<>();
	//
	public ReadMindCanvasControl(){
		setBackground(Color.WHITE);
		eventSet();
	
	}
	//
	public void eventSet(){
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(color);
		g.drawLine(x, y, x1, y1);
		
		for(int i = 0; i<vc.size(); i++){
			DrawInfo imsi = vc.get(i);
			g.setColor(imsi.getColor());
				g.drawLine(imsi.getX(), imsi.getY(), imsi.getX1(), imsi.getY1());
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		DrawInfo di = new DrawInfo(x ,y , x1, y1, color);
		vc.add(di);
		x = x1;
		y = y1;
		this.repaint();
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
		x = e.getX();
		y = e.getY();
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		DrawInfo di = new DrawInfo(x ,y , x1, y1, color);
		vc.add(di);
		this.repaint();
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void setFirst() {
		DrawInfo d1 = new DrawInfo(0,0,0,0,color);
		vc.removeAll(vc);
		vc.add(d1);
		this.repaint();
	}
	
	public void drag(String[] msg) {
		x1 = Integer.parseInt(msg[1]);
		y1 = Integer.parseInt(msg[2]);
		DrawInfo di = new DrawInfo(x ,y , x1, y1, color);
		vc.add(di);
		x = x1;
		y = y1;
		this.repaint();
	}
	
	public void press(String[] msg) {
		x = Integer.parseInt(msg[1]);
		y = Integer.parseInt(msg[2]);
	}
	
	public void release(String[] msg) {
		x1 = Integer.parseInt(msg[1]);
		y1 = Integer.parseInt(msg[2]);
		DrawInfo di = new DrawInfo(x ,y , x1, y1, color);
		vc.add(di);
		this.repaint();
	}
	
	public static void main(String[] args){
		new ReadMindCanvasControl();
	}
	
}