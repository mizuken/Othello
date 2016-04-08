//ゲームの状態を管理するクラス

public class StateManager {

	Board board; //ボードクラス変数
	Player player1; //1P
	Player player2; //2P

	//オセロ盤の変数

	//オセロ盤の状態を表す変数
	 private static final int BLACK = 1;
	 private static final int WHITE = -1;
	 private static final int EMPTY = 0;
	 private static final int WALL = 2;

	 private static final int OK = 1;
	 private static final int NG = -1;

	//オセロ盤を扱う二次元配列の設定
	 private static final int table_size = 8;
	 public int[][] stone_table = new int[table_size+2][table_size+2];
	 public int[][] select_table = new int[table_size+2][table_size+2];

	public StateManager(){
		set_stone_table();
		player1 = new Player(); //プレイヤー１の定義
		player2 = new Player(); //プレイヤー２の定義
		System.out.println("set_stone_table");
	}


	//石の状態を設定する
	public void set_stone_table(){
		//まずボードと壁を設定
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				//端には壁の設定
				if(i == 0 || i == table_size+2){
					System.out.println("wall");
					//this.stone_table[i][j] = WALL;
				}else if ( i > 0 && i < table_size+2){
					if(j == 0 || j == table_size+2){
						//System.out.println("wall");
						this.stone_table[i][j] = WALL;
					}else{
						//System.out.println("empty");
						this.stone_table[i][j] = EMPTY;
					}
				}
			}
		}
		//次に最初の4つの石を設定
		this.stone_table[(table_size)/2][(table_size)/2] = WHITE;
		this.stone_table[(table_size)/2][(table_size)/2 +1] = BLACK;
		this.stone_table[(table_size)/2 + 1][(table_size)/2] = BLACK;
		this.stone_table[(table_size)/2 + 1][(table_size)/2 + 1] = WHITE;
	}

	//与えられた座標のオセロ盤の情報を手に入れる
	public int get_stone_table(int x,int y){
		switch(this.stone_table[x][y]){
			case BLACK:
				return BLACK;
			case WHITE:
				return WHITE;
			case EMPTY:
				return EMPTY;
			default:
				//System.out.println("error");
				return -2;
		}
	}

	//ゲームの状態を初期状態に戻す
	public void inti(){

	}

}
