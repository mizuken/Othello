//ゲームの盤面状況を記録するクラス
/*
 *記録するとなった場合
 *ゲームのターン数、打ったプレイヤーの状態
 *打った場所、打つことによって得られるひっくり返る枚数を保存
 */

public class Record {

	Record record;
	StateManager statemanager;
	Point save_point = new Point(); //どの場面でのことを考える

	public int table_size = 8;

	public int record_table[][] = new int[table_size+2][table_size+2]; //table
	public int record_turn = 0;//記録したターン数


   public Record(){
	   init_record();
   }


   //テーブル初期化関数(すべて0にする)
   public void init_record(){
		for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				this.record_table[i][j] = 0;
			}
		}
   }

   public void copy_table(Record record){
	   for(int i = 0; i < table_size+2; i++){
			for(int j = 0 ; j < table_size+2; j++){
				this.record_table[i][j] = record.get_table(i, j);
			}
		}
   }
   //その時点の盤面情報を保存する
   public void set_table(int x,int y,int data){
	   this.record_table[x][y] = data;
   }
   //その点での盤面情報を取得する
   public int get_table(int x, int y){
	   return this.record_table[x][y];
   }

   //記録されたターン数の入手
   public int get_record_turn(){
	   return this.record_turn;
   }
   //ターン数を記録する
   public void set_record_ture(int turn){
	   this.record_turn = turn;
   }

   //打った場所の入手
   public Point get_save_point(){
	   return this.save_point;
   }
   //打った場所の保存
   public void set_save_point(Point point){
	   this.save_point = point;
   }
   //内容の表示
   public void print_record(){
	   for(int i = 0; i < table_size+2; i++){
		   for(int j = 0; j < table_size+2; j++){
			   System.out.print(String.format("[%1$2d]",this.get_table(i, j)));
		   }
		   System.out.print("\n");
	   }
   }
}
