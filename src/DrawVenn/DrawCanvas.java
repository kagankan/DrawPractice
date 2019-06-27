package DrawVenn;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

public class DrawCanvas extends Canvas{
	public HashMap<String, Figure> figureList;
	JFrame frame;
	int width;
	int height;
	
	public DrawCanvas(String title, int width, int height) {
		figureList = new HashMap<String,Figure>(); // 図形を管理するリスト
		
		this.width = width;
		this.height = height;
		setBackground(Color.white);
		
		frame = new JFrame(title);
		//frame.setSize(width,height);//ウィンドウサイズ
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ウィンドウを閉じたらプログラムを終了する
	    frame.add(this);
	    frame.pack();
	    frame.setVisible(true); // ウィンドウを⾒せる
	}
	
	public void paint(Graphics g) { // figureList中にある図形を再描画時に描画
		Image back = createImage(width, height);
		Graphics buffer = back.getGraphics();
				
		Iterator<String> itr = figureList.keySet().iterator(); // 反復⼦
		while (itr.hasNext()) {
			figureList.get(itr.next()).draw(buffer); // 各図形インスタンスの描画メソッドを呼ぶ
		}

		g.drawImage(back, 0, 0, this);
	}

}
