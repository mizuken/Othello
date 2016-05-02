//########################################################################################//
//オセロゲーム課題により制作（4/4~）
//########################################################################################//

//ゲーム全体の管理クラス
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class OthelloMain extends JFrame {

	static StateManager statemanager; //状態を管理する
	static Board board; //オセロ版をあつかう変数
	static Player player1 = new Player(); // 1P
	static Player player2 = new Player(); // 2p
	static Point point;

	private int main_turn = 0; //ターン状態を扱う変数
	int i = 0;
	//メニュー
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem startItem;

	//ゲーム画面の実装
	public OthelloMain(){
		super("OthelloGame");

		statemanager = new StateManager(); //状態を設定

		//オセロ盤の実装
		JPanel jp_othellobord = new JPanel();
		jp_othellobord.setLayout(new FlowLayout());

		board = new Board(statemanager); //状態を入れたオセロ盤の描画

		//メニューバーの実装
		JPanel jp_menubar = new JPanel();
		jp_menubar.setLayout(new FlowLayout());
		menuBar = new JMenuBar(); // メニューバーを作る
		setJMenuBar(menuBar);
		gameMenu = new JMenu("Menu");
		startItem = new JMenuItem("start");
		gameMenu.add(startItem);
		menuBar.add(gameMenu);

		//メニューボタンの実装
		JPanel jp_buttonber = new JPanel();
		jp_buttonber.setLayout(new GridLayout(4,1));

		getContentPane().add(board,BorderLayout.CENTER);//オセロ盤の表示
		getContentPane().add(jp_buttonber,BorderLayout.WEST);
		System.out.println("OthelloMainメソッド");
		repaint();

		//現在のプレイヤーを人間としてどちらも設定する
		//player1 = new Player();
		//player2 = new Player();

		//クリックされたときや動かしているときの処理
		board.requestFocusInWindow();
		board.addMouseListener(
				new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0){
							//クリックした位置をオセロ座標で保存する
							statemanager.set_point(e.getX(), e.getY()); //ステート内部オセロ座標としてクリック位置を保存した
							System.out.println(statemanager.point.get_x_point()+"と"+ statemanager.point.get_y_point()+"に保存した");
							//クリックした位置にオセロを置く
							//その位置はおける場所なのか。置けない場所ならばその処理は行わない
							if((statemanager.get_select_table(statemanager.point.get_x_point(), statemanager.point.get_y_point()) == 1)){
								//置ける場所である
								//ステート内部オセロ座標を用いてオセロの設置
								statemanager.put_stone(statemanager.point.get_x_point(),statemanager.point.get_y_point());
								statemanager.set_turn(statemanager.get_turn()*-1);//ターンの切り替え
								System.out.println(statemanager.get_turn());
								statemanager.decide_stone_pos();
								statemanager.check_select_table();
								repaint();
							}else{
								//置けない場所である
								System.out.println("そこは置けません");
							}

							//置けない場所である

						}
					}
					public void mouseEntered(MouseEvent e){

					}
				});

		board.addMouseMotionListener(
				new MouseAdapter(){
					public void mouseMoved(MouseEvent e){
						//System.out.println("move_x="+e.getX()+"move_y="+e.getY());
						//まず現在のおける状態を確認する
						//statemanager.check_select_table();
						repaint();
					}
				});
		board.setFocusable(true);

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}



	//mainクラス
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		OthelloMain app = new OthelloMain();
		app.pack();
		app.setSize(1000,800);
		app.setVisible(true);

		//ここでオセロゲームの管理
		/*
		 1.先行黒、2.後攻白でスタート
		 2.ターン数はゲームの性質上60が限界。また60になればゲーム終了
		 */
		//ターン数が６０まではやり続ける
		while(statemanager.get_turn_count() != 10){
			System.out.println("game_now");
			if(statemanager.get_turn() == 1){
				//先手の処理（黒色）
				//passする必要があるのかないのか
				System.out.println("turn_black");
				if(statemanager.isPass() != false){
					//人間であるかないか
					if(player1.get_player_state(player1) == 0){
						while(statemanager.get_decide_pos() == false){
							//入力待ち状態
							//System.out.print(".");
						}
					}else{
						//CPUの処理
					}
					System.out.println("打たれました:"+statemanager.get_turn_count());
				}else{
				//打つ処理が終了したので次のターンへ
					System.out.println("パスされました:"+statemanager.get_turn_count());
				}
				statemanager.up_turn_count();
				statemanager.undecide_stone_stone_pos(); //未決定状態にする

			}else if (statemanager.get_turn() == -1){
				//後手の処理(白色)
				System.out.println("turn_white");
				if(statemanager.isPass() != false){
				//人間であるかないか
					if(player2.get_player_state(player2) == 0){
						while(statemanager.get_decide_pos() == false){
							//入力待ち状態
						}
					}else{
						//CPUの処理
					}
					System.out.println("打たれました:"+statemanager.get_turn_count());
				}else{
				//打つ処理が終了したので次のターンへ
					System.out.println("パスされました:"+statemanager.get_turn_count());
				}
				statemanager.up_turn_count();
				statemanager.undecide_stone_stone_pos(); //未決定状態にする

			}else{
				//例外
				System.out.println("Erorr point");
			}

		}//ゲームを実行する


	}//mainクラス



}
