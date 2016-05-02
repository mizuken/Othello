//ゲームの状態を管理するクラス

public class StateManager {

	Board board; //ボードクラス変数
	Player player1; //1P
	Player player2; //2P
	Point point; //現在マウスが置かれている場所の座標をオセロ座標で保つ


	//オセロ盤の変数

	//オセロ盤の状態を表す変数
	 private static final int BLACK = 1;
	 private static final int WHITE = -1;
	 private static final int EMPTY = 0;
	 private static final int WALL = 2;

	 private static final int POSS  = 1;
	 private static final int IMPOSS = 0;


	//オセロ盤を扱う二次元配列の設定
	 private static final int table_size = 8;
	 public int[][] stone_table = new int[table_size+2][table_size+2];
	 public int[][] select_table = new int[table_size+2][table_size+2];

	 private int state_x;
	 private int state_y;

	//ターンを扱う変数
	 private int turn = EMPTY;
	 private int turn_count = 0;

	 private boolean DECIDE = false; //これがfalseのときはまだ次の石の場所が決まっていない

	public StateManager(){
		set_stone_table();
		init_select_table();
		System.out.println("Turn : "+get_turn_count()+" Game Start!");
		player1 = new Player(); //プレイヤー１の定義
		player2 = new Player(); //プレイヤー２の定義
		point = new Point();
		up_turn_count(); //ターンを１にしてゲームスタート
		this.turn = BLACK; //黒が先手でスタート
		check_select_table();
		//System.out.println("set_stone_table");
	}

	//現在のマウス位置状況をオセロ座標的に取得する
	public int get_x_point(){
		return this.state_x;
	}
	public int get_y_point(){
		return this.state_y;
	}

	//石の状態を設定する
	public void set_stone_table(){
		System.out.println("石の初期状態のセット");
		//まずボードと壁を設定
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				//端には壁の設定 つまり 0か9の配列子があれば壁になる
				if(i == 0 || j == 0 || i == table_size+1 || j == table_size+1){
					this.stone_table[i][j] = WALL;
				}else{
					this.stone_table[i][j] = EMPTY;
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
		switch(this.stone_table[y][x]){
			case BLACK:
				//System.out.println("This is Black");
				return BLACK;
			case WHITE:
				//System.out.println("This is White");
				return WHITE;
			case EMPTY:
				//System.out.println("This is Empty");
				return EMPTY;
			default:
				//System.out.println("error");
				return -2;
		}
	}

	//選択状態の初期化
	public void init_select_table(){
		for(int i = 0; i < table_size+2;i++){
			for(int j = 0; j < table_size+2; j++){
				this.select_table[i][j] = IMPOSS; //すべてを選択不可能状態に
			}
		}
	}

	//石のおける選択状態の変更をする
	public void set_select_table(int x, int y){
		this.select_table[y][x] = POSS; //選択できるようにする
	}
	//石のおける状態を取得する
	public int get_select_table(int x, int y){
		return this.select_table[y][x];
	}


	//現在のマウス位置状況をオセロ座標に変換する
	public void set_point(int set_x, int set_y){
		int x,y;
		x = (set_x - 280) / 80;
		y = (set_y - 50) / 80;
		if(x+1 >= 1 && x+1 <= 8 && y+1 >= 1 && y+1 <= 8){
		point.set_point(x+1,y+1); // オセロ座標は１～８までしかもたない.配列としては（ｙ、x）の位置を保存している
		}
	}


	//石のテーブルを変更する
	public void put_stone(int put_x,int put_y){
	    //put_x put_yはそれぞれオセロ座標的位置されているはず
		//打った場所は打てる場所であれば打つ
		System.out.println("put_stone関数の開始");
		if(check_reverse_stone(put_x,put_y) == true){
			System.out.println("チェックを通過した");
			if(this.turn == BLACK){
					this.stone_table[put_y][put_x] = BLACK;
					System.out.println("set_black");
			}else if(this.turn == WHITE){
					this.stone_table[put_y][put_x] = WHITE;
					System.out.println("set_white");
			}else{
				//System.out.println("karadayo");
			}
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
		this.turn_count--; //ターン数を一つ減らす
	}
	public int get_turn_count(){
		return this.turn_count; //現在のターン数を返す
	}


	public void decide_stone_pos(){
		this.DECIDE = true;
	}
	public void undecide_stone_stone_pos(){
		this.DECIDE = false;
	}
	public boolean get_decide_pos(){
		return this.DECIDE;
	}
	//石をひっくり返す処理を考える

	//オセロ盤上のすべてのマスについておけるかどうか検索して、その結果をselect_tableに保存する関数
	public void check_select_table(){
		//まずはselect_tableの初期化
		 init_select_table();
		for(int i = 1; i < table_size+1; i++){
			for(int j = 1; j < table_size+1; j++){
				//まずそのマスが空であるかどうかを考える
				if(get_stone_table(i, j) == EMPTY){ //空でなければ考える必要がないから
					if(check_reverse_stone(i,j) == true){
						//石を置くことが可能である
						set_select_table(i,j); //(j,i)をPOSSにする
					}else{
						//石を置くことができない

					}
				}
			}
		}
	}

	//すべての座標についてオセロが打てる場所が一つでもあるかないかを確認する
	public boolean isPass(){
		boolean pass = false;
		for(int i = 1; i < table_size+1;i++){
			for(int j = 1; j < table_size+1; j++){
				 if(check_reverse_stone(i,j) == true){
					 pass = true;
					 return pass;
				 }
			}
		}
		return pass;
	}
	//x,yにはオセロ座標が挿入されその位置がオセロが置ける場所かどうか判断する関数
	public boolean check_reverse_stone(int x, int y){
		//どこかひっくり返せえればtrueどこもひっくり返せなければfalse
		boolean possibility = false;//可能性を扱う
		//System.out.println(x+","+y+"についてこれからチェックを行う"+get_stone_table(x,y));
		//debug_stone_arround(x,y);
		//そもそも空じゃないと捜索をする必要がなる
		if(get_stone_table(x,y) == EMPTY){
			//System.out.println("検索開始"+y+x);
			//if(possibility == false){System.out.println("まだこの時点ではだめ");}
			//置いたマスから八方向に対して検索をかける
			//どこか一つおけるならtrueになるはず
			for(int s = -1; s < 2; s++){
				for(int t = -1; t < 2; t++){
					possibility = check_reverse_line(x,y,s,t);
					if(possibility == true){
						s = 2;
						t = 2;
					}
				}
			}
		}
		return possibility; //捜索した結果trueがどこかで返れば反映される
	}

	//一方向に探索して、可能性を探る部分関数
	public boolean check_reverse_line(int x, int y, int vec_x,int vec_y){
		//x,yには探索する場所の座標、vec_x,vec_yで方向を定める
		//System.out.println("vec="+vec_x+vec_y);
		int i = x + vec_x;
		int j = y + vec_y;
		//最初の石は相手の石でなければならない
		if(get_stone_table(i,j) == this.turn*-1){
			//探索を進めていきもし壁に当たるか空白が出てくれば探索は終了する
			//System.out.println("この方向は探索対象です");
			i += vec_x;
			j += vec_y;
			while(get_stone_table(i,j) != WALL || get_stone_table(i,j) != EMPTY){
				//相手の駒が続けばまだ検索を続ける
				if(get_stone_table(i,j) == this.turn*-1){
					i += vec_x;
					j += vec_y;
					//System.out.println("相手の石です");
				}else if(get_stone_table(i,j) == this.turn){
					//自分の駒であるのでここで探索は終了し、この場所はおけることを返す
					//System.out.println("自分の石です");
					return true;
				}else if(get_stone_table(i,j) == EMPTY){
					//System.out.println("This is EMPTY");
					return false;
				}else if(get_stone_table(i,j) == WALL){
					//System.out.println("This is WALL");
					return false;
				}else{
					System.out.print("何か不具合が起きています:");
					System.out.println("x:"+x+"y:"+y+"vecx:"+vec_x+"vecy:"+vec_y);
				}
			}
		}//相手の駒ではないのでこの方向はだめ
		return false;
	}

	//////////////////////////////////debug関数////////////////////////////////////
	public void print_stone_table(){
		System.out.println("This is Stone_table");
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				System.out.print(String.format("[%1$2d]",stone_table[j][i]));
			}
			System.out.print("\n");
		}
	}

	public void print_select_table(){
		System.out.println("This is Select_table");
		for(int i = 0; i < table_size+2 ; i++){
			for(int j = 0 ; j < table_size+2; j++){
				System.out.print(String.format("[%1$2d]",select_table[j][i]));
			}
			System.out.print("\n");
		}
	}
	public void debug_stone_arround(int x, int y){
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2;j++){
				System.out.print(String.format("[%1$2d]",stone_table[y+j][x+i]));
			}
			System.out.print("\n");
		}
	}
	////////////////////////////////////////////////////////////////////////////////


	//ゲームの状態を初期状態に戻す
	public void inti(){

	}

}
