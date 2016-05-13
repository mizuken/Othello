//プレイヤーの管理を扱うクラス


public class Player {

	//プレイヤーの状態を表す
	private static final int HUMAN = 0;
	private static final int CPU1 = 1; //randam
	private static final int CPU2 = 2; //max_score
	private static final int CPU3 = 3; //

	 private static final int BLACK = 1;
	 private static final int WHITE = -1;
	 private static final int EMPTY = 0;
	 private static final int WALL = 2;

	 private static final int POSS  = 1;
	 private static final int IMPOSS = -1;


	private int player_state;

	private Record record;
	private Record temp_record;
	private int player_select_table[][] = new int[10][10];

	private int score[][] = {
			{0,    0,    0,    0,     0,     0,      0,     0,    0,   0},
			{0, 100, -50,  50,  50,    50,    50,  -50, 100,  0},
			{0, -50, -75, -25, -25,  -25,   -25, -75,  -50,  0},
			{0,  50,  -25,  25,    0,     0,    25, -25,   50,  0},
			{0,  50,  -25,    0,    0,     0,      0, -25,   50,  0},
			{0,  50,  -25,    0,    0,     0,      0, -25,   50,  0},
			{0,  50,  -25,  25,    0,     0,    25, -25,   50,  0},
			{0, -50, -75, -25, -25,  -25,  -25, -75,  -50,  0},
			{0, 100, -50,  50,  50,    50,   50,   -50, 100,  0},
			{0,     0,    0,    0,    0,     0,     0,     0,    0,   0}
			};

	private int first_score[][] = {
			{0,    0,    0,    0,     0,     0,      0,     0,    0,   0},
			{0,   30, -12,   0,    -1,    -1,    0,    -12,  30,  0},
			{0,  -12, -15, -3,    -3,     -3,   -3,   -15,  -12, 0},
			{0,    0,   -3,   0,    -1,     -1,    0,     -3,    0,   0},
			{0,  -1,  -3,    -1,    -1,     -1,      -1, -3,   -1,  0},
			{0,  -1,  -3,    -1,    -1,     -1,      -1, -3,   -1,  0},
			{0,    0,   -3,   0,    -1,     -1,    0,     -3,    0,   0},
			{0,  -12, -15, -3,    -3,     -3,   -3,   -15,  -12, 0},
			{0,   30, -12,   0,    -1,    -1,    0,    -12,  30,  0},
			{0,     0,    0,    0,    0,     0,     0,     0,    0,   0}
			};

	StateManager statemanager;

	public Player(){
		set_player_state(HUMAN);
		record = new Record();
		temp_record = new Record();
		temp_record.init_record();
	}

	//プレイヤー状態を設定する
	public void set_player_state(int state){
		this.player_state = state;
	}
	//プレイヤーの状態を確認する
	public int get_player_state(Player player){
		return player.player_state;
	}

	public void set_statemanager(StateManager statemanager){
		this.statemanager = statemanager;
	}

	//空きマスの個数を取得する
	public int get_num_of_set_point(){
		int num = 0;
		//System.out.println("start get num");
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(statemanager.get_select_table(i,j) == 1){
					num++;
				}
			}
		}
		return num;
	}

	public void set_calculate_record(){
			//石盤の記録
			for(int i =0; i < 10; i++){
				for(int j = 0; j < 10; j++){
					this.record.record_table[i][j] = this.statemanager.stone_record.record_table[i][j];
					this.player_select_table[i][j] = this.statemanager.select_table[i][j];
				}
			}
	}


	//x,yにはオセロ座標が挿入されその位置がオセロが置ける場所かどうか判断する関数
	public int check_player_select_table(){
		int score = 0;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(check_reverse_stone(i,j) == true){
					score++;
				}
			}
		}
		return score;
	}

	public boolean check_reverse_stone(int x, int y){
		//どこかひっくり返せえればtrueどこもひっくり返せなければfalse
		if(this.record.get_table(x, y) == EMPTY){
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
		//print_stone_table();
		//空でなければ検索する意味がない
		return false;
	}

	public boolean check_reverse_line(int x, int y, int vec_x,int vec_y){
		//探索方向の決定・設定
		int i = x + vec_x;
		int j = y + vec_y;
		//相手の石が続いている限り探索を続ける
		while(this.record.get_table(i, j) ==  this.statemanager.get_turn()*-1){
			i = i + vec_x;
			j = j + vec_y;
			//自分のがくれば正しい
			if(this.record.get_table(i, j) == this.statemanager.get_turn()){
				return true;
			}else if(this.record.get_table(i,j) == WALL || this.record.get_table(i, j) == EMPTY){
				return false;
			}else{
				//exception
			}
		}
		return false;
	}

	public int think_release_count(int x, int y){
		int count = 0;
		for(int i = -1; i <= 1; i++){
			for(int j = -1 ; j <= 1; j++){
				if(this.record.record_table[x+i][y+j] == EMPTY){
					count++;
				}
			}
		}
		return count;
	}

	public int think_release_level(int x, int y){
		//考える場所は必ず打てる場所であるはず
		//まずそこに置いた時のひっくり返った相手の場面を想定する
		//ひっくり返す
		int p = 0;
		int temp_p = 0;
		int vec_x = 0;
		int vec_y = 0;
		for(int i = -1; i <= 1; i++){
			for(int j = -1 ; j <= 1; j++){
				temp_p = 0;
				//相手の駒がある方向を錯そうしてひっくり返す
				if(this.record.record_table[x+i][y+j] == this.statemanager.get_turn() * -1){
					vec_x = i;
					vec_y = j;
					while(this.record.record_table[x+vec_x][y+vec_y] == this.statemanager.get_turn() * -1){
						this.record.record_table[x+vec_x][y+vec_y] = this.statemanager.get_turn();
						temp_p += think_release_count(x+vec_x,y+vec_y);
						vec_x += i;
						vec_y += j;
						if(this.record.record_table[x+vec_x][y+vec_y] == this.statemanager.get_turn()){
							this.record.record_table[x][y] = this.statemanager.get_turn();
							temp_p += think_release_count(x+vec_x,y+vec_y);
							p += temp_p;
							break; //自分の石だったのでOK
						}else if (this.record.record_table[x+vec_x][y+vec_y] == WALL || this.record.record_table[x+vec_x][y+vec_y] == EMPTY){
							vec_x = i;
							vec_y = j;
							while(this.record.record_table[x+vec_x][y+vec_y] != WALL ||this.record.record_table[x+vec_x][y+vec_y] != EMPTY  ){
								this.record.record_table[x+vec_x][y+vec_y] = this.statemanager.get_turn() * -1;
								vec_x += i;
								vec_y += j;
								if(this.record.record_table[x+vec_x][y+vec_y] == WALL||this.record.record_table[x+vec_x][y+vec_y] == EMPTY ){
									break;
								}
							}
							break;
						}
					}
				}
			}
		}
		//この時点でrecordには相手のターンの状態になるはず
		//print_stone_table();
		System.out.println("x"+x+"y"+y+"p"+p);
		return p;
	}

	//局面での重みを計算する(大きいほど有利である)
	public int calculate_weight(){
		int weight = 0;
		//まずその場で打てるマスの個数を加える
		weight += get_num_of_set_point();
		return weight;
	}

	//打つ場所そのものの評価値を返す
	public int get_board_weight( int turn){
		int count  = 0;
		for(int i = 0; i < 10; i++){
			for(int j = 0 ; j < 10; j++){
				if(this.record.record_table[i][j] == turn)
    				 count += first_score[i][j];
				}
			}
		return count;
	}
	public int b (int x,int y){
		return this.first_score[x][y];
	}

	//###############################################################//
	//重みに基づいて，一番重いものを置く場所として設定する
	public void cpu_set_by_weight(Point point){

		//Point cpu_point = new Point();
		int pos_point_num = get_num_of_set_point(); //打てる個数の所持
		int count = 0;
		int decide = 0;

		//候補となるポイントを確保しておく
		Point num_point[] = new Point[pos_point_num];
		for(int s = 0; s < pos_point_num; s++){
			num_point[s] = new Point();
		}

		//打てる場所の座標を候補ポイントに格納する
		count = 0;
		for(int i = 0; i <10; i++){
			for(int j = 0; j < 10; j++){
				//打てる場所なら座標を保存する
				if(statemanager.get_select_table(i,j) == 1){
					num_point[count].set_point(i, j);
					count++;
				}
			}
		}

		//重さの計算
		for(int s = 0; s < pos_point_num; s++){
			set_calculate_record();
			//開放度の設定
			num_point[s].set_release(think_release_level(num_point[s].get_x_point(),num_point[s].get_y_point()));
			this.statemanager.set_turn(this.statemanager.get_turn()*-1);
			//ひっくり返せる枚数の設定
			num_point[s].set_return_num(check_player_select_table());
			this.statemanager.set_turn(this.statemanager.get_turn()*-1);

			//局面全体の評価

			//int w =   -get_board_weight(this.statemanager.get_turn() );
					// - get_board_weight(this.statemanager.get_turn() * -1);
			int w = - b(num_point[s].get_x_point(),num_point[s].get_y_point());
			num_point[s].set_weight(w);
			System.out.print("s"+s);
			System.out.println(" num:"+num_point[s].get_return_num()+" release:"+num_point[s].get_release()+" weight:"+num_point[s].get_weight());
			num_point[s].set_sum(num_point[s].get_return_num());
			//num_point[s].set_sum(num_point[s].get_release());
			num_point[s].set_sum(num_point[s].get_weight());
			System.out.println("sum"+num_point[s].get_sum());
		}
		//一番重いものを打つ場所として決定する
		for(int s = 0; s < pos_point_num; s++){
			if(num_point[s].get_sum() < num_point[decide].get_sum()){
				decide  = s;
			}
		}
		System.out.println("decide:"+num_point[decide].get_sum());
		point.set_point(num_point[decide].get_x_point(), num_point[decide].get_y_point());

	}
	//###############################################################//

//#################################################################
	//ランダムオセロ関数
	public void randum_set(Point point){
		int rand = 0;
		int t = get_num_of_set_point();

		rand = (int)(Math.random()*get_num_of_set_point()); //ランダムな値設定

		Point num_point[] = new Point[t];
		for(int s = 0; s < t; s++){
			num_point[s] = new Point();
		}

		System.out.println("rand:"+rand);
		int set = 0;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(statemanager.get_select_table(i,j) == 1){
					num_point[set].set_point(i, j);
					set++;
				}
			}
		}

		point.set_point(num_point[rand].get_x_point(), num_point[rand].get_y_point());
	}
//##################################################################
	public void print_stone_table(){
		System.out.println("This is Stone_table");
		for(int i = 0; i < 10 ; i++){
			for(int j = 0 ; j < 10; j++){
				System.out.print(String.format("[%1$2d]",this.record.record_table[i][j]));
			}
			System.out.print("\n");
		}
	}

}
