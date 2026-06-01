//OXゲームのコンソールアプリケーション（機能追加Ver）

//縦横3行の枠で人間vsCOMで交互にOXを入力する
//縦・横・斜めで同じ記号（O　or　X）が揃ったら勝敗を表示する

//追加機能　１：人対COM　２：最初に相手の強さを選んでゲーム開始（強さの中身は自由）　３：終了したらゲーム終了して最初に戻る

//修正点
// Levelの入力チェック　
// markComPlaceをスイッチ文で書き換え（該当しない場合を記載する）
// placeHardメソッドで、判定メソッドと、記号の配置メソッドを分ける

import java.util.*;

public class Main {
    // 定数の定義
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

    // COMレベル
    public static final int LEVEL_EASY = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_HARD = 3;

    public static void main(String[] args) {
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
        if(!validLevel(level)){
            System.out.println("正しい数値を入力してください。1: 弱い　2:ふつう　3:強い");
            return;
        }
        // 入力✓
        

        int comLevel = Integer.parseInt(level);
        if (!validLevelCheck(comLevel)) {
            System.out.println("正しい数値を入力してください。1: 弱い　2:ふつう　3:強い");
            return;
        }

        // 盤面の用意と初期化
        int board[][] = new int[BOARD_MAX][BOARD_MAX];

        // 処理
        while (winFlag == false && drawFlag == false) {

            // 盤面の表示
            printBoard(board);

            // 人間の入力を受け取り、盤面に駒を置く
            markHumanPlace(board, scanner);

            // 勝利判定
            winFlag = checkWinAnswer(board, HUMAN);

            if (winFlag) {
                break;
            }
            // 引き分け判定
            drawFlag = checkDrawAnswer(board);
            if (drawFlag) {
                break;
            }

            // COMの入力
            markComPlace(board, comLevel);

            // 勝利判定
            winFlag = checkWinAnswer(board, COM);

            if (winFlag) {
                break;
            }
            // 引き分け（ゲーム継続）判定
            drawFlag = checkDrawAnswer(board);

            if (drawFlag) {
                break;
            }
        }

        // 後処理
        // 最終盤面を表示
        printBoard(board);

        // 勝ち or 引き分けを表示
        if (winFlag) {
            System.out.println("ゲーム終了");

            // 勝者表示
            if (checkWinAnswer(board, HUMAN)) {
                System.out.println("あなたの勝ちです!!");
            } else {
                System.out.println("COMの勝ちです!!");
            }

        } else if (drawFlag) {
            System.out.println("引き分けです!!");
        }
    }

    /** 盤面の表示 */
    public static void printBoard(int[][] board) {
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
    public static String convertDisplay(int value) {
        if (value == HUMAN) {
            return DISPLAY_HUMAN;
        } else if (value == COM) {
            return DISPLAY_COM;
        } else {
            return DISPLAY_EMPTY;
        }
    }

    /**
     * 人間の入力と配置を行うメソッド
     * 
     * @param board
     * @param scanner
     */
    public static void markHumanPlace(int[][] board, Scanner scanner) {
        // 入力✓用のフラグ
        boolean inputCheck = false;

        while (inputCheck == false) {
            System.out.println("あなたの番です。");

            System.out.println("行を入力してください。1~" + BOARD_MAX);
            String gyo = scanner.next();

            System.out.println("列を入力してください。1~" + BOARD_MAX);
            String retsu = scanner.next();

            // 入力チェック（範囲外かどうか）
            if (validNumber(gyo) == false || validNumber(retsu) == false) {
                System.out.println("正しい数値を入力してください。1~" + BOARD_MAX);
                continue;
            }

            int GyoNum = Integer.parseInt(gyo) - 1;
            int RetsuNum = Integer.parseInt(retsu) - 1;

            // 入力チェック（空欄かどうか）
            if (board[GyoNum][RetsuNum] != EMPTY) {
                System.out.println("既に埋まっています。別の場所を指定してください。");
                continue;
            }

            // 盤面に O or X を置く
            board[GyoNum][RetsuNum] = HUMAN;
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
        for (int i = 1; i <= BOARD_MAX; i++) {
            if (input.equals(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 勝利判定を行うメソッド
     * 
     * @param board
     * @param player
     * @return 判定
     */
    public static boolean checkWinAnswer(int[][] board, int player) {
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
    public static boolean checkDrawAnswer(int[][] board) {
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
     * COMが入力するメソッド
     * 
     * @param board
     * @param level
     */
    public static void markComPlace(int[][] board, int level) {
        // 入力チェック
        validLevelCheck(level);
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
     * 入力レベルのチェック
     * 
     * @param level
     * @return 判定
     */
    public static boolean validLevelCheck(int level) {
        for (int i = 1; i < LEVEL_HARD + 1; i++) {
            if (level == i) {
                return true;
            }
        }
        return false;
    }
    
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
    public static void placeEasy(int[][] board) {
        Random random = new Random();
        boolean placeCheck = false;

        while (placeCheck == false) {
            int gyo = random.nextInt(BOARD_MAX);
            int retsu = random.nextInt(BOARD_MAX);

            // 空いている場合
            if (board[gyo][retsu] == EMPTY) {
                board[gyo][retsu] = COM;
                placeCheck = true;
            }
        }
    }

    /**
     * レベルNormalの配置メソッド
     * 
     * @param board
     */
    public static void placeNormal(int[][] board) {
        Random random = new Random();
        boolean placeCheck = false;

        while (placeCheck == false) {
            // 中央が空いている場合
            if (board[1][1] == EMPTY) {
                board[1][1] = COM;
                placeCheck = true;
            } else {
                int gyo = random.nextInt(BOARD_MAX);
                int retsu = random.nextInt(BOARD_MAX);

                // 空いている場合
                if (board[gyo][retsu] == EMPTY) {
                    board[gyo][retsu] = COM;
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
    public static void placeHard(int[][] board) {
        int[] comWinPosition = findWinningPosition(board, COM);

        if (!Arrays.equals(comWinPosition, new int[] { NOT_FOUND, NOT_FOUND })) {
            placeComMark(board, comWinPosition);
            return;
        }

        int[] humanWinPosition = findWinningPosition(board, HUMAN);

        if (!Arrays.equals(humanWinPosition, new int[] { NOT_FOUND, NOT_FOUND })) {
            placeComMark(board, humanWinPosition);
            return;
        }
        placeNormal(board);
        return;
    }

    /**
     * 勝利できるかどうかを判定するメソッド
     * 
     * @param board
     * @param player
     * @return 勝利する座標を返す
     */
    public static int[] findWinningPosition(int[][] board, int player) {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;

                    boolean winPossibiity = checkWinAnswer(board, player);

                    board[i][j] = EMPTY;

                    if (winPossibiity) {
                        return new int[] { i, j };
                    }
                }
            }
        }
        return new int[] { NOT_FOUND, NOT_FOUND };
    }
    /**
     * 指定した座標にCOMの駒を置くメソッド
     * @param board
     * @param position
     */
    public static void placeComMark(int[][] board, int[] position) {
        board[position[0]][position[1]] = COM;
    }

}
