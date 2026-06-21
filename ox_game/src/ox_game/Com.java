package ox_game;

import java.util.*;
public class Com {
    // COMレベル
    public static final int LEVEL_EASY = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_HARD = 3;
    
    //コンストラクタ
    
    /**
     * COMが入力するメソッド
     * 
     * @param board
     * @param level
     */
    public static void markComPlace(Board board, int level) {
        switch (level) {
            case LEVEL_EASY:
                placeEasy(board);
                break;

            case LEVEL_NORMAL:
                placeNormal(board);
                break;

            case LEVEL_HARD:
                placeHard(board);
                break;

            default:
                placeEasy(board);
                break;
        }
    }

    /**
     * 入力したLevelが設定値内か確認する
     * @param input
     * @return 判定
     */
    public static boolean validLevel(String input){
        for(int i=LEVEL_EASY; i<=LEVEL_HARD;i++){
            if(input.equals(String.valueOf(i))){
                return true;
            }
        }
        return false;
    }
    /**
     * レベルEasyの配置メソッド
     * 
     * @param board
     */
    public static void placeEasy(Board board) {
        Random random = new Random();
        boolean placeCheck = false;

        while (placeCheck == false) {
            int gyo = random.nextInt(Board.BOARD_MAX);
            int retsu = random.nextInt(Board.BOARD_MAX);

            // 空いている場合
            if (board.isEmpty(gyo, retsu)) {
                board.placeMark(gyo, retsu, Board.COM);
                placeCheck = true;
            }
        }
    }

    /**
     * レベルNormalの配置メソッド
     * 
     * @param board
     */
    public static void placeNormal(Board board) {
        Random random = new Random();
        boolean placeCheck = false;

        while (placeCheck == false) {
            // 中央が空いている場合
            if (board.isEmpty(1, 1)) {
                board.placeMark(1, 1, Board.COM);
                placeCheck = true;
            } else {
                int gyo = random.nextInt(Board.BOARD_MAX);
                int retsu = random.nextInt(Board.BOARD_MAX);

                // 空いている場合
                if (board.isEmpty(gyo, retsu)) {
                    board.placeMark(gyo, retsu, Board.COM);
                    placeCheck = true;
                }
            }

        }
    }

    /**
     * レベルHardの配置メソッド
     * 
     * @param board
     */
    public static void placeHard(Board board) {
        int[] comWinPosition = Board.findWinningPosition(board, Board.COM);

        if (!Arrays.equals(comWinPosition, new int[] { Board.NOT_FOUND, Board.NOT_FOUND })) {
            placeComMark(board, comWinPosition);
            return;
        }

        int[] humanWinPosition = Board.findWinningPosition(board, Board.HUMAN);

        if (!Arrays.equals(humanWinPosition, new int[] { Board.NOT_FOUND, Board.NOT_FOUND })) {
            placeComMark(board, humanWinPosition);
            return;
        }
        placeNormal(board);
        return;
    }
    /**
     * 指定した座標にCOMの駒を置くメソッド
     * @param board
     * @param position
     */
    public static void placeComMark(Board board, int[] position) {
        board.placeMark(position[0], position[1], Board.COM);
    }
}
