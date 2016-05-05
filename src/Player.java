//プレイヤーの管理を扱うクラス


public class Player {

	//プレイヤーの状態を表す
	private static final int HUMAN = 0;
	private static final int CPU1 = 1; //randam
	private static final int CPU2 = 2; //max_score
	private static final int CPU3 = 3; //

	private int player_state;

	StateManager statemanager;

	public Player(){
		set_player_state(HUMAN);
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

	//局面での重みを計算する(大きいほど有利である)
	public int calculate_weight(){
		int weight = 0;
		//まずその場で打てるマスの個数を加える
		weight += get_num_of_set_point();

		return weight;
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

		//System.out.println("end get num;"+num);
		return num;
	}

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


}
