package DrawVenn;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DrawVenn.DrawGraph.Slider1Listener;
import DrawVenn.DrawGraph.Slider2Listener;

public class DrawVenn extends DrawCanvas{
	JSlider slider1, slider2;
	JLabel label1, label2;

	//力学モデル
	double kinetic = 0;
	double prev_kinetic = 0;
	double k_c = 10000.0; // nodeList.size(); //クーロン力定数
	double k_s = 0.015; //ばね定数
	double k_g = 1.0; //Bandが引き込む力
	//double len = 1; //バネ自然長
	double bandLength = 0; //bandの一辺の長さ
	double m = 1.0; //ノード重さ
	double dt = 0.1; //微小時間
	double att = 0.8; //減衰定数
	
	public DrawVenn(Graph graph, int width, int height) {
		super("DrawVenn", width, height);
		
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

	    //ノードリスト、グループリスト作成
		Set<String> nodeList = new HashSet<String>();
		Set<String> groupList = new HashSet<String>();
		for(Edge<String> edge : graph) {
			groupList.add(edge.getFrom());
			nodeList.add(edge.getTo());
		}

		//隣接リストならぬ所属リストを作成
		Map<String, List<String>> belList = new HashMap<String, List<String>>();
		Map<String, List<String>> memberList = new HashMap<String, List<String>>();
		for(String node : nodeList) {
			belList.put(node, new ArrayList<String>());
		}
		for(String group : groupList) {
			memberList.put(group, new ArrayList<String>());
		}
		for(Edge<String> edge : graph) {
			belList.get(edge.getTo()).add(edge.getFrom());
			memberList.get(edge.getFrom()).add(edge.getTo());
		}

		//質点リスト、図形作成
		Map<String, MassPoint> objectList = new HashMap<String, MassPoint>();
		Random r = new Random();
		for(String node : nodeList) {
			objectList.put(node, new MassPoint(m, r.nextInt(width), r.nextInt(height)));
			figureList.put(node, new Textbox(node, (int)objectList.get(node).x, (int)objectList.get(node).y));
		}
		for(String group : groupList) {
			figureList.put(group, new Band((int)(5.0 * Math.sqrt(memberList.get(group).size())), r.nextInt(width-200)+100, r.nextInt(height-200)+100, 100));
			for(int i = 0; i < ((Band)figureList.get(group)).n; i++) {
				String name = group+"_"+i;
				Band band = (Band)figureList.get(group);
				objectList.put(name, new MassPoint(m / ((Band)figureList.get(group)).n, band.getPoint(i).x, band.getPoint(i).y));
				//objectList.put(name, new MassPoint(m / 2, band.getPoint(i).x, band.getPoint(i).y));
				objectList.get(name).isGroup = true;
				objectList.get(name).groupName = group;
				objectList.get(name).groupNumber = i;
			}
		}
		repaint();
		
		do {
			//系全体のパラメータ
			prev_kinetic = kinetic;
			kinetic = 0;

			/*
			//当たり判定デバッグ
			for(int i = 0; i<width; i+=5) {
				for(int j = 0; j< height; j+=5) {
					figureList.remove(i+"_"+j);
					if(Calc.isInsidePolygon(new DoublePoint(i, j), (Band)figureList.get("2n"))) {
						figureList.put(i+"_"+j, new Circle(i, j, 2));
						
					}
				}
			}*/
			
			//質点ループ
			for(String node1 : objectList.keySet()) {
				MassPoint p = objectList.get(node1);
				double f = 0;
				p.fx = p.fy = 0;

				//外壁からクーロン力を与える
				double margin = 10;
				p.fx += k_c / (Math.pow(p.x + margin, 2) + 1) ;
				p.fx -= k_c / (Math.pow(width - p.x + margin, 2) + 1);
				p.fy += k_c / (Math.pow(p.y + margin, 2) + 1);
				p.fy -= k_c / (Math.pow(height - p.y + margin, 2) + 1);

				if(!p.isGroup) {
					//ノードのグループについての処理
					boolean isDrawn = false;
					for(String group : groupList) {
						Polygon pol = (Polygon)figureList.get(group);
						
						if(belList.get(node1).contains(group)) {
							if(Calc.isInsidePolygon(p, pol)){
								//所属グループに入っている場合								
								//各線分から斥力を受ける
								for(int i = pol.n - 1, j = 0; i >= 0; j = i--) {
									MassPoint p1 = objectList.get(group+"_"+i);
									MassPoint p2 = objectList.get(group+"_"+j);
									DoublePoint c = Calc.getPerpendicular(p, p1, p2);
									f = k_c * p.m * 0.001 * Calc.getDistance(p1, p2) / (Math.pow(c.getDistanceFromOrigin(), 2) + 1);
									p.fx -= f * c.getVectorX();
									p.fy -= f * c.getVectorY();
									/*
									f = k_c * p.m * p1.m / Math.pow(Calc.getDistance(p, p1), 2);
									p.fx -= f * Calc.getVectorX(p, p1);
									p.fy -= f * Calc.getVectorY(p, p1);
									*/
								}
							}else {
								//所属グループに入ってない場合
								if(!isDrawn) {
									p.fx += k_g * Calc.getDistance(p.x, p.y, pol.x, pol.y) * Calc.getVectorX(p.x, p.y, pol.x, pol.y);
									p.fy += k_g * Calc.getDistance(p.x, p.y, pol.x, pol.y) * Calc.getVectorY(p.x, p.y, pol.x, pol.y);
									isDrawn = true;
									System.out.println(node1+" is drawn:");
								}
							}

						} else {
							if(Calc.isInsidePolygon(p, pol)){
								//非所属グループに入っているなら吐き出す
								p.fx -= k_g * Calc.getVectorX(p.x, p.y, pol.x, pol.y); 
								p.fy -= k_g * Calc.getVectorY(p.x, p.y, pol.x, pol.y);
							}else {
								//非所属グループに入っていない各線分から斥力を受ける
								/*
								for(int i = pol.n - 1, j = 0; i >= 0; j = i--) {
									DoublePoint c = Calc.getPerpendicular((DoublePoint)p, pol.getPoint(i), pol.getPoint(j));
									p.fx -= k_c / (Math.pow(c.getDistanceFromOrigin(), 2) + 1) * c.getVectorX(); 
									p.fy -= k_c / (Math.pow(c.getDistanceFromOrigin(), 2) + 1) * c.getVectorY(); 
									//System.out.println(node1+":"+p.fx+","+p.fy);
								}*/
							}
							
						}
					}

					//クーロン力
					if(!isDrawn) {
						for(String node2 : nodeList) {
							MassPoint n = objectList.get(node2);
							if(node1 != node2 && Calc.getDistance(p, n) != 0) {
								f = k_c * p.m * n.m / Math.pow(Calc.getDistance(p, n), 2);
								p.fx -= f * Calc.getVectorX(p, n);
								p.fy -= f * Calc.getVectorY(p, n);
							}
						}
					}
					//System.out.println(node1+" node:"+p.fx+","+p.fy);
				
					
				}else if(p.isGroup){
					Band pol = (Band)figureList.get(p.groupName);

					//線分ループ
					for(int i = pol.n - 1, j = 0; i >= 0; j = i--) {
						MassPoint p1 = objectList.get(p.groupName+"_"+i);
						MassPoint p2 = objectList.get(p.groupName+"_"+j);

						if(p.groupNumber == i || p.groupNumber == j) {
							//自分を含む線分
							//バネ引力
							f = k_s * (Calc.getDistance(p1, p2) - bandLength) * (p.groupNumber == i ? 1.0 : -1.0);
							p.fx += f * Calc.getVectorX(p1, p2); 
							p.fy += f * Calc.getVectorY(p1, p2); 
							//System.out.println(node1+" bane:"+p.fx+","+p.fy);
							
							//ノードとの影響
							for(String node : nodeList) {
								MassPoint n = objectList.get(node);

								if(belList.get(node).contains(p.groupName)) {
									if(Calc.isInsidePolygon(n, (Polygon)pol)){
										//入っている所属ノード
										if(Calc.getPerpendicular(n, p1, p2).getDistanceFromOrigin() < 5) {
											//DoublePoint c = Calc.getPerpendicular(n, p1, p2);
											//f = k_c * ((p1.m + p2.m) / 2) * n.m / (Math.pow(c.getDistanceFromOrigin(), 2) + 1) * (p.groupNumber == i ? 1.0 : -1.0);
											//p.fx -= f * c.getVectorX();
											//p.fy -= f * c.getVectorY();
											DoublePoint nf = new DoublePoint(n.x - pol.x, n.y - pol.y);
											double dot = n.fx * nf.x + n.fy * nf.y;
											f = (dot > 0 ? dot : 0) * k_s;
											p.fx += f * nf.getVectorX();
											p.fy += f * nf.getVectorY();
										}
										
										f = k_c * p.m * n.m / (Math.pow(Calc.getDistance(p, n), 2) + 1);
										p.fx -= f * Calc.getVectorX(p, n);
										p.fy -= f * Calc.getVectorY(p, n);
									}
								}else{
									if(!Calc.isInsidePolygon(n, (Polygon)pol)){
										//外にある所属してないノード
									}							
								}
							}
							
						}else {							
							//自分を含まない点から斥力
							if(p.groupNumber != i) {
								f = k_c * p.m * p1.m / (Math.pow(Calc.getDistance(p, p1), 2) + 1);
								p.fx -= f * Calc.getVectorX(p, p1);
								p.fy -= f * Calc.getVectorY(p, p1);
							}
							
						}
					}
					
				}
								
			}

			//速度計算、位置反映
			for(String node1 : objectList.keySet()) {
				//bandを引っ張っていきたい
			}
			for(String node1 : objectList.keySet()) {
				MassPoint p = objectList.get(node1);
				p.vx = (p.vx + p.fx / p.m * dt) * att;
				p.vy = (p.vy + p.fy / p.m * dt) * att;				
				p.move(p.vx * dt, p.vy * dt);
				p.x = Calc.clamp(p.x, 0, width);
				p.y = Calc.clamp(p.y, 0, height);
				figureList.put(node1+"arrow", new Arrow((int)p.x, (int)p.y, (int)p.vx, (int)p.vy));				
				//図形更新
				if(!p.isGroup) {
					figureList.get(node1).setX((int)p.x);
					figureList.get(node1).setY((int)p.y);					
				}else if(p.isGroup) {
					((Band)figureList.get(p.groupName)).setPoint(p.groupNumber, p);
					figureList.put(node1+"name", new Textbox(node1, (int)p.x, (int)p.y));
				}
				
				//運動エネルギー
				kinetic += p.m * (Math.pow(p.vx, 2) + Math.pow(p.vy, 2));
			}
						
			try {
				Thread.sleep(200);
				kinetic /=  nodeList.size();
				System.out.println(kinetic);
				System.out.println("repaint");
 				repaint();
			} catch(InterruptedException e){
	            e.printStackTrace();
	        }
			
		}while(kinetic > 1);
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

