//OXゲームのコンソールアプリケーション（機能追加Ver）

//縦横3行の枠で人間vsCOMで交互にOXを入力する
//縦・横・斜めで同じ記号（O　or　X）が揃ったら勝敗を表示する
//追加機能　１：人対COM　２：最初に相手の強さを選んでゲーム開始（強さの中身は自由）　３：終了したらゲーム終了して最初に戻る

package ox_game;
public class Main {
    public static void main(String[] args) {
        Game game =new Game();
        game.start();
    }

    
}
