import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
//オセロ盤の状態を描画するクラス


public class Board extends JPanel{

	Point point;

	//格子の表示に関する変数
	private static final int w_size = 80; //格子1マスの横幅
	private static final int h_size = 80; //格子1マスの縦幅
	private static final int stone_size = 60; //石の大きさ

	private static final int board_size = 8; //マスの数

	private static final int top_x = 280;
	private static final int top_y = 50;

	StateManager statemanager;

	public Board(StateManager statemanager){
		this.statemanager = statemanager;
		setFocusable(true);
	}

	//ゲーム状況の表示
	public void paintComponent(Graphics g){

		//背景の表示
		g.setColor(Color.gray);
		g.fillRect(200, 0, 800, 800);

		//オセロ盤の表示
		g.setColor(new Color(0,255,51));
		g.fillRect(270,40,660,660);

		//格子の表示
		for(int i = 0; i < board_size+1; i++){
			g.setColor(Color.black);
			g.drawLine(top_x, top_y+i*h_size, top_x+w_size*board_size, top_y+i*h_size);
			g.drawLine(top_x+i*w_size, top_y, top_x+i*w_size, top_y+h_size*board_size);
		}
		//格子点の表示
		    g.fillOval(top_x+w_size*2-5, top_y+h_size*2-5, 10, 10);
		    g.fillOval(top_x+w_size*6-5, top_y+h_size*2-5, 10, 10);
		    g.fillOval(top_x+w_size*2-5, top_y+h_size*6-5, 10, 10);
		    g.fillOval(top_x+w_size*6-5, top_y+h_size*6-5, 10, 10);

		//石の表示
		//statemanagaerクラスで管理しているゲーム状況に応じて石を出力する
		for(int i = 0; i < board_size+1; i++){
			for(int j = 0; j < board_size+1; j++){
				if(statemanager.get_stone_table(i, j) == -1){
					g.setColor(Color.black);
					g.fillOval(top_x+10 + (i-1)*w_size, top_y+15 + (j-1)*h_size, stone_size, stone_size);
					g.setColor(Color.white);
					g.fillOval(top_x+10 + (i-1)*w_size, top_y+10 + (j-1)*h_size, stone_size, stone_size);
				}else if(statemanager.get_stone_table(i, j) == 1){
				//黒石の表示
					g.setColor(Color.white);
					g.fillOval(top_x+10+ (i-1)*w_size, top_y+10+5 + (j-1)*h_size, stone_size, stone_size);
					g.setColor(Color.black);
					g.fillOval(top_x+10+ (i-1)*w_size, top_y+10 + (j-1)*h_size, stone_size, stone_size);
				}else{
				//特になし
				}
			}
		}

		//設置可能場所の表示

		for(int i = 0; i < board_size+1; i++){
			for(int j = 0; j < board_size+1; j++){
				g.setColor(new Color(255,0,0,70));
				if(statemanager.get_select_table(i, j) == 1){
					g.fillRect(top_x +(i-1)*w_size, top_y+(j-1)*h_size, w_size, h_size);
				}

			}
		}


	}

}
