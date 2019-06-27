package DrawVenn;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawGraph extends DrawCanvas{
	JSlider slider1, slider2;
	JLabel label1, label2;
	
    //力学モデル
	double kinetic = 0;
	double prev_kinetic = 0;
	double k_c = 10000.0; // vertexList.size(); //クーロン力定数
	double k_s = 0.01; //ばね定数
	//double k = 	0.5 * Math.sqrt(width * height / vertexList.size());
	double len = 1; //バネ自然長
	double m = 1.0; //ノード重さ
	double dt = 0.2; //微小時間
	double att = 0.85; //減衰定数

	
	public DrawGraph(Graph graph, int width, int height) {
		super("DrawGraph", width, height);
		
		//スライダー
		JPanel pane = new JPanel();
	    frame.getContentPane().add(pane, BorderLayout.NORTH);

	    slider1 = new JSlider(1000, 50000, 10000);
		slider1.addChangeListener(new Slider1Listener());
		label1 = new JLabel("k_c：" + slider1.getValue());
	    pane.add(label1);
	    pane.add(slider1);
	    slider2 = new JSlider(0, 1000, 10);
		slider2.addChangeListener(new Slider2Listener());
		label2 = new JLabel("k_s：" + (slider2.getValue() / 1000.0));
	    pane.add(label2);
	    pane.add(slider2);
	    frame.setVisible(true);
	    
		//ノードリスト作成
		Set<String> vertexList = new HashSet<String>();
		for(Edge<String> edge : graph) {
			vertexList.add(edge.getFrom());
			vertexList.add(edge.getTo());
		}
		
		//隣接リスト作成
		Map<String, List<String>> adjacentList = new HashMap<String, List<String>>();
		for(String vertex : vertexList) {
			adjacentList.put(vertex, new ArrayList<String>());
		}
		for(Edge<String> edge : graph) {
			adjacentList.get(edge.getFrom()).add(edge.getTo());
			adjacentList.get(edge.getTo()).add(edge.getFrom());
		}
		
		//質点リスト、図形作成
		Map<String, MassPoint> objectList = new HashMap<String, MassPoint>();
		Random r = new Random();
		for(String vertex : vertexList) {
			objectList.put(vertex, new MassPoint(m, r.nextInt(width), r.nextInt(height)));
			figureList.put(vertex, new Textbox(vertex, (int)objectList.get(vertex).x, (int)objectList.get(vertex).y));
		}

		//図形作成
		for(Edge<String> edge : graph) {
			String from = edge.getFrom();
			String to = edge.getTo();
			figureList.put(from+"-"+to, new Line(figureList.get(from).getX(), figureList.get(from).getY(), figureList.get(to).getX(), figureList.get(to).getY()));
		}
		repaint();


		do {
			//系全体のパラメータ
			prev_kinetic = kinetic;
			kinetic = 0;
			
			for(String vertex1 : vertexList) {
				//vertex1のMassPoint
				MassPoint p1 = objectList.get(vertex1);
				p1.fx = p1.fy = 0.0;
				
				//外壁からクーロン力を与える
				p1.fx += k_c / Math.pow(p1.x + 1, 2) ;
				p1.fx -= k_c / Math.pow(width - p1.x + 1, 2);
				p1.fy += k_c / Math.pow(p1.y + 1, 2);
				p1.fy -= k_c / Math.pow(height - p1.y + 1, 2);

				//他のノードとの影響
				for(String vertex2 : vertexList) {
					MassPoint p2 = objectList.get(vertex2);
					//クーロン力
					if(vertex1 != vertex2 && Calc.getDistance(p1.x, p1.y, p2.x, p2.y) != 0) {
						p1.fx -= k_c / Math.pow(Calc.getDistance(p1.x, p1.y, p2.x, p2.y), 2) * Calc.getVectorX(p1.x, p1.y, p2.x, p2.y);
						p1.fy -= k_c / Math.pow(Calc.getDistance(p1.x, p1.y, p2.x, p2.y), 2) * Calc.getVectorY(p1.x, p1.y, p2.x, p2.y);
						//force[0] -= k_c / Calc.getDistance(x1, y1, x2, y2) * Calc.getVectorX(x1, y1, x2, y2);
						//force[1] -= k_c / Calc.getDistance(x1, y1, x2, y2) * Calc.getVectorY(x1, y1, x2, y2);
					}
				
					// つながっているノードとバネ
					if(adjacentList.get(vertex1).contains(vertex2)) {
						//p1.fx += k_s * Math.pow(Calc.getDistance(p1.x, p1.y, p2.x, p2.y) - len, 2) * Calc.getVectorX(p1.x, p1.y, p2.x, p2.y); 
						//p1.fy += k_s * Math.pow(Calc.getDistance(p1.x, p1.y, p2.x, p2.y) - len, 2) * Calc.getVectorY(p1.x, p1.y, p2.x, p2.y); 
						p1.fx += k_s * (Calc.getDistance(p1.x, p1.y, p2.x, p2.y) - len) * Calc.getVectorX(p1.x, p1.y, p2.x, p2.y); 
						p1.fy += k_s * (Calc.getDistance(p1.x, p1.y, p2.x, p2.y) - len) * Calc.getVectorY(p1.x, p1.y, p2.x, p2.y); 
					}
				}
				
				//焼きなまし
				/*
				double p = Math.exp(-100/prev_kinetic);
				if(r.nextDouble() < p * 0.1) {
					force[0] = 100.0 * (r.nextDouble() - 0.5);
					force[1] = 100.0 * (r.nextDouble() - 0.5);
				}*/

			}

			//速度計算、位置反映
			for(String vertex1 : vertexList) {
				MassPoint p1 = objectList.get(vertex1);
				p1.vx = (p1.vx + p1.fx / p1.m * dt) * att;
				p1.vy = (p1.vy + p1.fy / p1.m * dt) * att;				
				p1.move(p1.vx * dt, p1.vy * dt);
				p1.x = Calc.clamp(p1.x, 0, width);
				p1.y = Calc.clamp(p1.y, 0, height);
				
				//図形更新
				figureList.get(vertex1).setX((int)p1.x);
				figureList.get(vertex1).setY((int)p1.y);

				//運動エネルギー
				kinetic += p1.m * (Math.pow(p1.vx, 2) + Math.pow(p1.vy, 2));
			}
			
			try {
				Thread.sleep(100);
				kinetic /= vertexList.size();
				System.out.println(kinetic);
				System.out.println("repaint");
				for(Edge<String> edge : graph) {
					String from = edge.getFrom();
					String to = edge.getTo();
					figureList.put(from+"-"+to, new Line(figureList.get(from).getX(), figureList.get(from).getY(), figureList.get(to).getX(), figureList.get(to).getY()));
				}
 				repaint();
			} catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}while(kinetic > 0);
	}
	
    class Slider1Listener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
        	label1.setText("k_c：" + slider1.getValue());
        	k_c = slider1.getValue();
        }
    }
    class Slider2Listener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
        	k_s = slider2.getValue() / 1000.0;
        	label2.setText("k_s：" + k_s);
        }

    }
}

