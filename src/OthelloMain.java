//########################################################################################//
//オセロゲーム課題により制作（4/4~）
//########################################################################################//

//ゲーム全体の管理クラス
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class OthelloMain extends JFrame implements ActionListener {

	static StateManager statemanager; //状態を管理する
	static Board board; //オセロ版をあつかう変数
	static Player player1  = new Player(); // 1P
	static Player player2  = new Player(); // 2p
	static Point point = new Point();

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

		player1.set_statemanager(statemanager);
		player2.set_statemanager(statemanager);
		player1.set_player_state(1);
		player2.set_player_state(1);
		//クリックされたときや動かしているときの処理
		board.requestFocusInWindow();
		board.addMouseListener(
				new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0){
							//クリックした位置をオセロ座標で保存する
							statemanager.set_point(e.getY(), e.getX()); //ステート内部オセロ座標としてクリック位置を保存した
							//その位置はおける場所なのか。置けない場所ならばその処理は行わない
							if((statemanager.get_select_table(statemanager.point.get_x_point(), statemanager.point.get_y_point()) == 1)){
								//置ける場所である
								statemanager.put_stone(statemanager.point.get_x_point(),statemanager.point.get_y_point());
								//のちにひっくり返す
								statemanager.reverse_record.init_record(); //ひっくり返せる場所の初期化
								System.out.println("score:"+statemanager.decide_reverse_stone(statemanager.point.get_x_point(),statemanager.point.get_y_point()));
								statemanager.do_reverse_stone();
								statemanager.decide_stone_pos();
								statemanager.check_select_table();
								statemanager.set_turn(statemanager.get_turn()*-1);//ターンの切り替え
								repaint();
							}else{
								//置けない場所である
								System.out.println("そこは置けません");
								// statemanager.check_reverse_stone(statemanager.point.get_x_point(),statemanager.point.get_y_point());
							}
						}
					if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
							//右
							System.out.println("右クリック");
							int reroad = statemanager.get_turn_count()-1;
							if(reroad > 0){
							statemanager.get_stone_record(reroad);
							statemanager.down_turn_count();
							statemanager.check_select_table();
							statemanager.start_reroad();
							statemanager.set_turn(statemanager.get_turn()*-1);
							repaint();
							}
						}
						//System.out.println("クリック処理の終了");

					}
					public void mouseEntered(MouseEvent e){

					}
				});

		board.addMouseMotionListener(
				new MouseAdapter(){
					public void mouseMoved(MouseEvent e){
						//repaint();
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
		System.out.println("--------------game-start------------------");
		//statemanager.print_score();
		statemanager.save_stone_record(statemanager.get_turn_count());
		statemanager.up_turn_count();
		while(statemanager.get_turn_count() != 64){

			System.out.println("| game_now turn:"+statemanager.get_turn_count()+"|");
			statemanager.set_score();
			statemanager.check_select_table();

			if(statemanager.get_reroad() != true){
				statemanager.save_stone_record(statemanager.get_turn_count()); //ボードの保存
				System.out.println("save!");
			}else{
				//System.out.println("not save!");
			}
			statemanager.finish_reroad(); // reroadのリセット

			if(statemanager.get_turn() == 1){
				//先手の処理（黒色）
				//System.out.println("turn_black");
				board.repaint();
				if(statemanager.isPass() == true){
					//人間であるかないか
					//System.out.println("black_ok");
					//相手がパスならば選択テーブルの変更
					if(statemanager.get_pass_player() == statemanager.get_turn()*-1){
						System.out.println("get white pass");
						statemanager.set_pass_table();
					}

					if(player1.get_player_state(player1) == 0){
						//System.out.println("wait_black_putting");
						while(true){
							//入力待ち状態
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO 自動生成された catch ブロック
								board.repaint();
								e.printStackTrace();
							}
							if(statemanager.get_decide_pos() == true){
								//System.out.println("break");
								board.repaint();
								break;
							}else if(statemanager.get_reroad() == true){
								System.out.println("reroad");
								break;
							}
						}
					}else{
						//CPUの処理
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						statemanager.check_select_table();
						//ここにCPUによる違いを決める
						player1.randum_set(point);
						System.out.println("x:"+point.get_x_point()+"y:"+point.get_y_point());
						statemanager.put_stone(point.get_x_point(),point.get_y_point());
						//のちにひっくり返す
						statemanager.reverse_record.init_record(); //ひっくり返せる場所の初期化
						System.out.println("score:"+statemanager.decide_reverse_stone(point.get_x_point(),point.get_y_point()));
						statemanager.do_reverse_stone();
						statemanager.decide_stone_pos();
						statemanager.check_select_table();
						statemanager.set_turn(statemanager.get_turn()*-1);//ターンの切り替え

					}
					statemanager.set_pass_player(0); //passしていない

				}else{
				//打つ処理が終了したので次のターンへ
					System.out.println("black_pass:");
					statemanager.set_pass_player(statemanager.get_turn());
				}
				statemanager.undecide_stone_pos(); //未決定状態にする

				System.out.println("turn_black_finish:");
			}else
			if (statemanager.get_turn() == -1){
				//後手の処理(白色)
				//System.out.println("turn_white");
				board.repaint();
				if(statemanager.isPass() == true){
				//人間であるかないか
					//System.out.println("white_ok");
					//相手がパスならば選択テーブルの変更

					if(statemanager.get_pass_player() == statemanager.get_turn()*-1){
						System.out.println("get black pass");
						statemanager.set_pass_table();
					}

					if(player2.get_player_state(player2) == 0){
						//System.out.println("wait_white_putting");
						while(true){
							//入力待ち状態
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}

							if(statemanager.get_decide_pos() == true){
								//System.out.println("break_white");
								break;
							}else if (statemanager.get_reroad() == true){
								System.out.println("reroad");
								break;
							}
						}

					}else{
						//CPUの処理
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						statemanager.check_select_table();
						player2.randum_set(point);
						System.out.println("x:"+point.get_x_point()+"y:"+point.get_y_point());
						statemanager.put_stone(point.get_x_point(),point.get_y_point());
						//のちにひっくり返す
						statemanager.reverse_record.init_record(); //ひっくり返せる場所の初期化
						System.out.println("score:"+statemanager.decide_reverse_stone(point.get_x_point(),point.get_y_point()));
						statemanager.do_reverse_stone();
						statemanager.decide_stone_pos();
						statemanager.check_select_table();
						statemanager.set_turn(statemanager.get_turn()*-1);//ターンの切り替え
						board.repaint();

					}

					statemanager.set_pass_player(0);//passはしてない

				}else{
				//打つ処理が終了したので次のターンへ
					System.out.println("pass_white:");
					statemanager.set_pass_player(statemanager.get_turn());
				}

				statemanager.undecide_stone_pos(); //未決定状態にする
				System.out.println("turn_white_finish:");

			}else{
				//例外
				System.out.println("Erorr point");
			}
			//ターン終了処理
			//2:ボードの記録(un_do実装):3:ゲーム終了の判定

			statemanager.print_score(); //スコアの記録と表示
			//終了判定
			if((statemanager.get_black_score() + statemanager.get_white_score()) == 64){
				//二つの石の合計が64
				if(statemanager.get_black_score() > statemanager.get_white_score()){
					System.out.println("black is winner");
				}else if (statemanager.get_black_score() < statemanager.get_white_score()){
					System.out.println("white is winner");
				}else{
					System.out.println("This Game is draw");
				}
				break;
			}else
			if(statemanager.get_black_score() == 0 || statemanager.get_white_score() == 0){
				//どちらかの石の数が0になった場合
				if(statemanager.get_black_score() == 0){
					System.out.println("white is winner");
				}else if(statemanager.get_white_score() == 0){
					System.out.println("black is winner");
				}else{
					System.out.println("Error at score");
				}
				break;
			}
			//次のターンへ
			if(statemanager.get_reroad() != true){
				statemanager.up_turn_count();//ターンを上げる
			}
		}//ゲームを実行する

		System.out.println("game_finished");

	}//mainクラス

	public void actionPerformed(ActionEvent e){

	  }

}
