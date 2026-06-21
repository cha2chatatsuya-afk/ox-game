package ox_game;

public class Board {
    // 行列の数を指定する
    public static final int BOARD_MAX = 3;

    // 盤面出力（空欄、人間、COM、枠）
    public static final String DISPLAY_EMPTY = " ";
    public static final String DISPLAY_HUMAN = "○";
    public static final String DISPLAY_COM = "×";

    /** 盤面の縦仕切り */
    public static final String DISPLAY_VERTICAL = "|";
    /** 盤面の横仕切り */
    public static final String DISPLAY_SIDE = "---+---+---";
    public static final String DISPLAY_SPACE = " ";

    // 盤面実態（空欄、人間、COM）
    public static final int EMPTY = 0;
    public static final int HUMAN = 1;
    public static final int COM = 2;
    public static final int NOT_FOUND = -1;

    private int board[][];//盤面実態（配列）

    //コンストラクタ
    public Board(){
        board = new int[BOARD_MAX][BOARD_MAX];
    }

    /** 盤面の表示 */
    public void printBoard() {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                System.out.print(DISPLAY_SPACE + convertDisplay(board[i][j]) + DISPLAY_SPACE);

                if (j < BOARD_MAX - 1) {
                    System.out.print(DISPLAY_VERTICAL);
                }
            }
            System.out.println();

            if (i < BOARD_MAX - 1) {
                System.out.println(DISPLAY_SIDE);
            }
        }
    }

    /** 画面表示への変換 */
    public String convertDisplay(int value) {
        if (value == HUMAN) {
            return DISPLAY_HUMAN;
        } else if (value == COM) {
            return DISPLAY_COM;
        } else {
            return DISPLAY_EMPTY;
        }
    }

    /**
     * 勝利判定を行うメソッド
     * 
     * @param board
     * @param player
     * @return 判定
     */
    public boolean checkWinAnswer(int player) {
        // 横の確認
        for (int i = 0; i < BOARD_MAX; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // 縦の確認
        for (int j = 0; j < BOARD_MAX; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // 左上 → 右下
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        // 右上 → 左下
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    /**
     * 引き分け判定を行うメソッド
     * 
     * @param board
     * @return 判定
     */
    public boolean checkDrawAnswer() {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 空欄かどうか確認するメソッド
     * @param row
     * @param column
     * @return 判定
     */
    public boolean isEmpty(int row, int column){
        return board [row][column] ==EMPTY;
    }

    /**
     * 盤面にマークするメソッド
     * @param row
     * @param column
     * @param mark
     */
    public void placeMark(int row, int column, int mark){
        board[row][column] = mark;
    }
    
    /**
     * 勝利できるかどうかを判定するメソッド
     * 
     * @param board
     * @param player
     * @return 勝利する座標を返す
     */
    public static int[] findWinningPosition(Board board, int player) {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (board.isEmpty(i,j)) {
                    board.placeMark(i, j, player);

                    boolean winPossibiity = board.checkWinAnswer(player);

                    board.placeMark(i, j, EMPTY);

                    if (winPossibiity) {
                        return new int[] { i, j };
                    }
                }
            }
        }
        return new int[] { NOT_FOUND, NOT_FOUND };
    }

}

