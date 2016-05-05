//ゲームの状態を管理するクラス

public class StateManager {

	Board board; //ボードクラス変数
	//Player player1; //1P
	//Player player2; //2P
	Point point; //現在マウスが置かれている場所の座標をオセロ座標で保つ

	//オセロ盤の変数
	private int black_score = 0; //黒石の数
	private int white_score = 0; //白石の数
	private int winner = 0;

	//オセロ盤の状態を表す変数
	 private static final int BLACK = 1;
	 private static final int WHITE = -1;
	 private static final int EMPTY = 0;
	 private static final int WALL = 2;

	 private static final int POSS  = 1;
	 private static final int IMPOSS = -1;

	 private static final int DRAW = 0;

	//オセロ盤を扱う二次元配列の設定
	 private static final int table_size = 8;
	// public int[][] stone_table = new int[table_size+2][table_size+2];
	 public int[][] select_table = new int[table_size+2][table_size+2];

	 //ターンを扱う変数
	 private int turn = EMPTY;
	 private int turn_count = 0; //現在ターン数
	 int max_turn = 64; //最大ターン数

	 private boolean DECIDE = false; //これがfalseのときはまだ次の石の場所が決まっていない
	 private boolean REROAD = false; //レコードの再度呼び出しのための変数

	 private int pass_player = EMPTY; //パスしたプレイヤーの記録

	Record stone_record; // 現在表示中の石の状態
	//Record select_record = new Record(); //石を置ける状態
	Record reverse_record; //ひっくり返す石の状態
	Record save_record[] = new Record[64]; // ターンごとに記録をしておく


	public StateManager(){

		stone_record = new Record(); // 現在表示中の石の状態
		reverse_record = new Record(); //ひっくり返す石の状態
		for(int i =0; i < 64; i++){
			save_record[i] = new Record();
		}
		set_stone_table();
		init_select_table();
		check_select_table();
		//player1 = new Player(); //プレイヤー１の定義
		//player2 = new Player(); //プレイヤー２の定義
		point = new Point();
		//up_turn_count(); //ターンを１にしてゲームスタート
		this.turn = BLACK; //黒が先手でスタート

		/////////////////

	}

	//石の状態を設定する
	public void set_stone_table(){
		//まずボードと壁を設定
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				//端には壁の設定 つまり 0か9の配列子があれば壁になる
				if(i == 0 || j == 0 || i == table_size+1 || j == table_size+1){
					stone_record.set_table(i, j, WALL); //record実装
				}else{
					stone_record.set_table(i, j, EMPTY); //record実装
				}
			}
		}

		//record実装
		stone_record.set_table((table_size)/2, (table_size)/2, WHITE);
		stone_record.set_table((table_size)/2, (table_size)/2 + 1, BLACK);
		stone_record.set_table((table_size)/2 + 1, (table_size)/2, BLACK);
		stone_record.set_table((table_size)/2 + 1, (table_size)/2 + 1, WHITE);

	}


	//与えられた座標のオセロ盤の情報を手に入れる
	public int get_stone_table(int x,int y){
		switch(stone_record.get_table(y, x)){
			case BLACK:
				return BLACK;
			case WHITE:
				return WHITE;
			case EMPTY:
				return EMPTY;
			case WALL:
				return WALL;
			default:
				return -2;
		}
	}

	//選択状態の初期化
	public void init_select_table(){
		//System.out.println("init_select_table");
		for(int i = 0; i < table_size+2;i++){
			for(int j = 0; j < table_size+2; j++){
				this.select_table[i][j] = 0; //すべてを0に初期化
				//select_record.set_table(i,j,IMPOSS);
			}
		}
	}

	//現在のマウス位置状況をオセロ座標に変換する
	public void set_point(int set_y, int set_x){ //クリック位置が入る
		int x,y;
		x = (set_x - 280) / 80;
		y = (set_y - 50) / 80;
		if(x+1 >= 1 && x+1 <= 8 && y+1 >= 1 && y+1 <= 8){
		point.set_point(x+1,y+1); // オセロ座標は１～８までしかもたない.配列としては（ｙ、x）の位置を保存している
		}
	}


	//石のテーブルを変更する
	//設定した場所に石を置く
	public void put_stone(int put_x,int put_y){
	    //put_x put_yはそれぞれオセロ座標的位置されているはず
			if(this.turn == BLACK){
					stone_record.set_table(put_y, put_x, BLACK);
					//System.out.println("set_black");
			}else if(this.turn == WHITE){
					stone_record.set_table(put_y, put_x, WHITE);
					//System.out.println("set_white");
			}else{
				System.out.println("Error at put_stone");
			}

	}

	//ターンの確認
	public void set_turn(int turn){
		this.turn = turn; //ターン状況をセットする　（黒１白ー１）
	}
	public int get_turn(){
		return this.turn;
	}
	public void up_turn_count(){
		this.turn_count++; //ターン数を一つ増やす
	}
	public void down_turn_count(){
		if(this.turn_count > 0){
			this.turn_count--; //ターン数を一つ減らす
		}
	}
	public int get_turn_count(){
		return this.turn_count; //現在のターン数を返す
	}
	public void set_turn_cound(int turn){
		this.turn_count = turn;
	}

	public void decide_stone_pos(){
		this.DECIDE = true;
	}
	public void undecide_stone_pos(){
		this.DECIDE = false;
	}
	public boolean get_decide_pos(){
		return this.DECIDE;
	}

	public void start_reroad(){
		this.REROAD = true;
	}
	public void finish_reroad(){
		this.REROAD = false;
	}
	public boolean get_reroad(){
		return this.REROAD;
	}

	//石をひっくり返す処理を考える
	//オセロ盤上のすべてのマスについておけるかどうか検索して、その結果をselect_tableに保存する関数
	public void check_select_table(){
		//まずはselect_tableの初期化
		 init_select_table();
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0; j < table_size+2; j++){
				if(stone_record.get_table(i,j) == EMPTY){
					if(check_reverse_stone(i,j) == true){
						//石を置くことが可能である
						set_select_table(i,j,POSS); //(j,i)をPOSSにする

					}else{
						//石を置くことができない
						set_select_table(i,j,IMPOSS);

					}
				}
			}
		}
		//board.repaint();
	}

	//石のおける選択状態の変更をする
	public void set_select_table(int x, int y, int data){
		this.select_table[x][y] = data; //選択できるようにする
	}
	//石のおける状態を取得する
	public int get_select_table(int x, int y){
		return this.select_table[y][x];
	}

	//すべての座標についてオセロが打てる場所が一つでもあるかないかを確認する
	public boolean isPass(){
		for(int i = 0; i < table_size+2;i++){
			for(int j = 0; j < table_size+2; j++){
				 if(check_reverse_stone(i,j) == true) return true;
			}
		}
		return false;
	}

	//相手がパスなので打てる場所を空白の場所に設定する
	public void set_pass_table(){
		for(int i = 0; i < table_size+2;i++){
			for(int j = 0; j < table_size+2; j++){
				 if(stone_record.get_table(i,j) == EMPTY) set_select_table(i,j,POSS);//からなら可能に
			}
		}
		board.repaint();
	}

	//x,yにはオセロ座標が挿入されその位置がオセロが置ける場所かどうか判断する関数
	public boolean check_reverse_stone(int x, int y){
		//どこかひっくり返せえればtrueどこもひっくり返せなければfalse
		if(stone_record.get_table(x, y) == EMPTY){
			boolean p = false;
			if(check_reverse_line(x,y, 0, 1) == true) p = true; //右
			if(check_reverse_line(x,y, 0,-1) == true) p = true; //左
			if(check_reverse_line(x,y, 1, 0) == true) p = true; //上
			if(check_reverse_line(x,y,-1, 0) == true) p = true; //下
			if(check_reverse_line(x,y,-1,-1) == true) p = true; //右上
			if(check_reverse_line(x,y,-1, 1) == true) p = true; //左上
			if(check_reverse_line(x,y, 1,-1) == true) p = true; //左下
			if(check_reverse_line(x,y, 1, 1) == true) p = true; //右下
			return p;
		}
		//空でなければ検索する意味がない
		return false;
	}

	public boolean check_reverse_line(int x, int y, int vec_x,int vec_y){
		//探索方向の決定・設定
		int i = x + vec_x;
		int j = y + vec_y;
		//相手の石が続いている限り探索を続ける
		while(stone_record.get_table(i, j) ==  get_turn()*-1){
			i = i + vec_x;
			j = j + vec_y;
			//自分のがくれば正しい
			if(stone_record.get_table(i, j) == get_turn()){
				return true;
			}else if(stone_record.get_table(i,j) == WALL || stone_record.get_table(i, j) == EMPTY){
				return false;
			}else{
				//exception
			}
		}
		return false;
	}

	//x,yにはオセロを置いたとき返せる枚数を返す．かつ，ひっくり返すを実行する．
	public int decide_reverse_stone(int x, int y){
		//どこかひっくり返せえればtrueどこもひっくり返せなければスコアは0のはず
		int score = 0;
			score += decide_reverse_line( x, y,  0,  1);
			score += decide_reverse_line( x, y,  0, -1);
			score += decide_reverse_line( x, y,  1,  0);
			score += decide_reverse_line( x, y, -1,  0);
			score += decide_reverse_line( x, y, -1, -1);
			score += decide_reverse_line( x, y, -1,  1);
			score += decide_reverse_line( x, y,  1, -1);
			score += decide_reverse_line( x, y,  1,  1);
		return score;
	}

	public int decide_reverse_line(int x, int y, int vec_x,int vec_y){
		//探索方向の決定・設定
		int score = 0;
		int i = y + vec_x;
		int j = x + vec_y;
		//相手の石が続いている限り探索を続ける
		while(stone_record.get_table(i, j) ==  get_turn()*-1){
			set_reverse_stone(i,j,POSS);
			score += 1;
			i = i + vec_x;
			j = j + vec_y;
			//自分のがくれば正しい
			if(stone_record.get_table(i, j) == get_turn()){
				return score;
			}else if(stone_record.get_table(i,j) == WALL || stone_record.get_table(i, j) == EMPTY){
				score = 0;
				i = y ;
				j = x ;
				while( stone_record.get_table(i, j) != WALL){
					set_reverse_stone(i,j,IMPOSS); //結果のリセット
					i = i + vec_x;
					j = j + vec_y;
				}
				break; //リセットしてブレイク
			}
		}

		return score;
	}


	//ひっくり返せる場所を保存する
	public void set_reverse_stone(int x, int y,int data){
		reverse_record.set_table(y,x,data); //ひっくり返せるから１
	}
	//石を置いた結果石をひっくり返す
	public void do_reverse_stone(){
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				if(reverse_record.get_table(i, j) == POSS){
					put_stone(i,j); //石を置く
					//System.out.println("do_reverse"+i+"."+j+".");
				}
			}
		}
		reverse_record.init_record(); //ひっくり返したのでテーブルの初期化
	}

	//////////////////////////////////記録関数/////////////////////////////////////

	//現在のゲーム状況を記録する
	//そのターンの石の状態の保存
	public void save_stone_record(int turn){
		//石盤の記録
		for(int i =0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				this.save_record[turn].record_table[i][j] = this.stone_record.record_table[i][j];

			}
		}
	}

	//記録したものから読みだして現在の状況にする
	public void get_stone_record(int turn){
		if(turn > 0){
			for(int i =0; i < 10; i++){
				for(int j = 0; j < 10; j++){
					this.stone_record.record_table[i][j] = this.save_record[turn].record_table[i][j];

				}
			}
		}
	}

	//石の点数を扱う
	public int get_black_score(){
		return this.black_score;
	}
	public void set_black_score(int score){
		this.black_score = score;
	}
	public int get_white_score(){
		return this.white_score;
	}
	public void set_white_score(int score){
		this.white_score = score;
	}
	public int get_winner(){
		return this.winner;
	}
	public void set_winner(int winner){
		this.winner = winner;
	}
	//ボード上の点数を算出し，勝者を決める
	public void set_score(){
		int black = 0;
		int white = 0;
		for(int i = 0; i < table_size+2;i++){
			for(int j = 0; j < table_size+2; j++){
				if(get_stone_table(i,j) == BLACK){
					black++;
				}else if(get_stone_table(i,j) == WHITE){
					white++;
				}
			}
		}
		set_black_score(black);
		set_white_score(white);
		if(black > white){
			set_winner(BLACK);
		}else if (black < white){
			set_winner(WHITE);
		}else{
			set_winner(DRAW);
		}
	}

	public void print_score(){
		set_score();
		System.out.println("-----------------------------");
		System.out.println("black:"+get_black_score()+" vs white:" +get_white_score());
		switch(get_winner()){
			case BLACK:
				System.out.println("advantage : black");
				break;
			case WHITE:
				System.out.println("advantage : white");
				break;
			case DRAW:
				System.out.println("draw");
				break;
			default:
				break;
		}
		System.out.println("-----------------------------");
	}

	public void set_pass_player(int player){
		this.pass_player = player;
	}
	public int get_pass_player(){
		return this.pass_player;
	}

	//////////////////////////////////debug関数////////////////////////////////////
	public void print_stone_table(){
		System.out.println("This is Stone_table");
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				System.out.print(String.format("[%1$2d]",stone_record.get_table(i, j)));
			}
			System.out.print("\n");
		}
	}

	public void print_save_table(int turn ){
		System.out.println("This is save_table:"+turn);
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				System.out.print(String.format("[%1$2d]",save_record[turn].get_table(i, j)));
			}
			System.out.print("\n");
		}
	}

	public void print_reverse_table(){
		System.out.println("This is reverse_table");
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				System.out.print(String.format("[%1$2d]",reverse_record.get_table(i, j)));
			}
			System.out.print("\n");
		}
	}
	public void print_select_table(){
		System.out.println("This is Select_table");
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				if(select_table[i][j] == POSS){System.out.print("[○]");}
				else{System.out.print("[×]");}
				//System.out.print(String.format("[%1$2d]",select_table[i][j]));
			}
			System.out.print("\n");
		}
	}

	public void debug_stone_arround(int x, int y){
		for(int i = -1; i < 2; i++){
 			for(int j = -1; j < 2;j++){
				System.out.print(String.format("[%1$2d]",stone_record.get_table(y+j, x+i)));
			}
			System.out.print("\n");
		}
	}
	////////////////////////////////////////////////////////////////////////////////

}
