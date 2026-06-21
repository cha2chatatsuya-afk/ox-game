package ox_game;

import java.util.Scanner;

public class HumanPlayer {

    /**
     * 人間の入力と配置を行うメソッド
     * 
     * @param board
     * @param scanner
     */
    public static void markHumanPlace(Board board, Scanner scanner) {
        // 入力✓用のフラグ
        boolean inputCheck = false;

        while (inputCheck == false) {
            System.out.println("あなたの番です。");

            System.out.println("行を入力してください。1~" + Board.BOARD_MAX);
            String gyo = scanner.next();

            System.out.println("列を入力してください。1~" + Board.BOARD_MAX);
            String retsu = scanner.next();

            // 入力チェック（範囲外かどうか）
            if (validNumber(gyo) == false || validNumber(retsu) == false) {
                System.out.println("正しい数値を入力してください。1~" + Board.BOARD_MAX);
                continue;
            }

            int gyoNum = Integer.parseInt(gyo) - 1;
            int retsuNum = Integer.parseInt(retsu) - 1;

            // 入力チェック（空欄かどうか）
            if (!board.isEmpty(gyoNum, retsuNum)) {
                System.out.println("既に埋まっています。別の場所を指定してください。");
                continue;
            }

            // 盤面に O or X を置く
            board.placeMark(gyoNum, retsuNum, Board.HUMAN);
            inputCheck = true;
        }
    }

    /**
     * 入力値が枠内かを確認するメソッド
     * 
     * @param input
     * @return 判定
     */
    public static boolean validNumber(String input) {
        for (int i = 1; i <= Board.BOARD_MAX; i++) {
            if (input.equals(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }
}
