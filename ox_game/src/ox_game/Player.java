package ox_game;

public class Player {
    //フィールド
    private int mark;//盤面実態
    private String display;//盤面出力

    //コンストラクタ
    public Player(int mark, String display){
        this.mark = mark;
        this.display = display;
    }
    //メソッド
    public int getMark(){
        return mark;
    }
    public String getDisplay(){
        return display;
    }
}

