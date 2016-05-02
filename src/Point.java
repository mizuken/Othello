//オセロボード上での座標を設定する
/*
 *  0 1 2 3 4 5 6 7
 * 0
 * 1
 * 2
 * 3　　　　　白黒
 * 4　　　　　黒白
 * 5
 * 6
 * 7
 */

//ｘとｙを入れ替えることで整合性をたもつ

public class Point {

	StateManager statemanager;
	private int x;
	private int y;

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


}
