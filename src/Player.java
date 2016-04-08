//プレイヤーの管理を扱うクラス


public class Player {

	//プレイヤーの状態を表す
	private static final int HUMAN = 0;
	private static final int CPU = 1;

	private int player_state;

	StateManager statemanager;

	public Player(){
		set_player_state(HUMAN);
	}

	//プレイヤー状態を設定する
	public void set_player_state(int state){
		if(state == HUMAN){
			this.player_state = HUMAN;
		}else if (state == CPU){
			this.player_state = CPU;
		}
	}
	//プレイヤーの状態を確認する
	public int get_player_state(Player player){
		return player.player_state;
	}
	
	
}
