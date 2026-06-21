package ox_game;

import java.util.Scanner;


public class Game {
    public void start() {
        // 準備
        // Scannerの準備
        Scanner scanner = new Scanner(System.in);

        // 勝利フラグ、引き分けフラグ（ゲーム終了フラグ）の用意
        boolean winFlag = false;
        boolean drawFlag = false;

        // タイトル画面表示
        System.out.println("《○×ゲーム》");
        System.out.println("COMの強さを選んでください");
        System.out.println("1: 弱い　2:ふつう　3:強い");

        // COMの強さを選択する
        String level = scanner.next();

        if(!Com.validLevel(level)){
            System.out.println("正しい数値を入力してください。1: 弱い　2:ふつう　3:強い");
            return;
        }
        // 入力✓
        int comLevel = Integer.parseInt(level);

        // 盤面の用意と初期化
        Board board = new Board();

        // 処理
        while (winFlag == false && drawFlag == false) {

            // 盤面の表示
            board.printBoard();
            
            // 人間の入力を受け取り、盤面に駒を置く
            HumanPlayer.markHumanPlace(board, scanner);

            // 勝利判定
            winFlag = board.checkWinAnswer(Board.HUMAN);
            

            if (winFlag) {
                break;
            }
            // 引き分け判定
            drawFlag = board.checkDrawAnswer();
            if (drawFlag) {
                break;
            }

            // COMの入力
            Com.markComPlace(board, comLevel);

            // 勝利判定
            winFlag = board.checkWinAnswer(Board.COM);

            if (winFlag) {
                break;
            }
            // 引き分け（ゲーム継続）判定
            drawFlag = board.checkDrawAnswer();

            if (drawFlag) {
                break;
            }
        }

        // 後処理
        // 最終盤面を表示
        board.printBoard();

        // 勝ち or 引き分けを表示
        if (winFlag) {
            System.out.println("ゲーム終了");

            // 勝者表示
            if (board.checkWinAnswer(Board.HUMAN)) {
                System.out.println("あなたの勝ちです!!");
            } else {
                System.out.println("COMの勝ちです!!");
            }

        } else if (drawFlag) {
            System.out.println("引き分けです!!");
        }
    }
}
