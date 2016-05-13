//オセロボード上での座標を設定する


public class Point {

	StateManager statemanager;
	private int x;
	private int y;

	private int weight = 0; //その座標の盤面評価
	private int release = 0; //その座標に置いた時の開放度
	private int return_num = 0; //ひっくり返せる枚数

	private int sum = 0; //評価値の合計

	public Point(){
		//初期座標は0.0に設定する
		this.x = 0;
		this.y = 0;
	}

	//座標を変更する
	public void set_point(int x, int y){
		this.x = x;
		this.y = y;

	}
	//x座標を取得する
	public int get_x_point(){
		return this.x;
	}
	//y座標を取得する
	public int get_y_point(){
		return this.y;
	}

	//重さを設定する
	public void set_weight(int weight){
		this.weight = weight;
	}
	//重さを取得する
	public int get_weight(){
		return this.weight;
	}

	//開放度を設定する
		public void set_release(int release){
			this.release = release;
		}
	 //開放度を取得する
		public int get_release(){
			return this.release;
		}

		//ひっくり返せる枚数を設定する
		public void set_return_num(int num){
			this.return_num = num;
		}
	 //開放度を取得する
		public int get_return_num(){
			return this.return_num;
		}


		//評価値を設定する
		public void set_sum(int sum){
			this.sum += sum;
		}
		//評価値をリセットする
		public void reset_sum(){
			this.sum = 0;
		}
	 //評価値合計を取得する
		public int get_sum(){
			return this.sum;
		}
}
