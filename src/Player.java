//プレイヤーの管理を扱うクラス


public class Player {

	//プレイヤーの状態を表す
	private static final int HUMAN = 0;
	private static final int CPU1 = 1;
	private static final int CPU2 = 2;

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


}
