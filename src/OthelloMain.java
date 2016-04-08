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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class OthelloMain extends JFrame {

	StateManager statemanager; //状態を管理する
	Board board; //オセロ版をあつかう変数

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

		//クリックされたときや動かしているときの処理
		board.requestFocusInWindow();
		board.addMouseListener(
				new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						if((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0){
							System.out.println("click_x="+e.getX()+"click_y="+e.getY());
						}
					}
				});
		board.addMouseMotionListener(
				new MouseAdapter(){
					public void mouseMoved(MouseEvent e){
						System.out.println("move_x="+e.getX()+"move_y="+e.getY());
					}
				});
		board.setFocusable(true);
		this.addWindowListener(new WindowAdapter(){

		});
	}


	//mainクラス
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		OthelloMain app = new OthelloMain();
		app.pack();
		app.setSize(1000,800);
		app.setVisible(true);
		System.out.println("mainメソッド");
	}

}
