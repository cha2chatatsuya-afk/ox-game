//OXゲームのコンソールアプリケーション（機能追加Ver）

//縦横3行の枠で人間vsCOMで交互にOXを入力する
//縦・横・斜めで同じ記号（O　or　X）が揃ったら勝敗を表示する
//追加機能　１：人対COM　２：最初に相手の強さを選んでゲーム開始（強さの中身は自由）　３：終了したらゲーム終了して最初に戻る

//疑問点
// クラス内メソッドでstaticを入れると引数が必要だが、入れないとメソッドの引数を削除しないとエラーがでる。この理由が理解できていない

package ox_game;
import java.util.*;

public class Main {
    // 定数の定義

    // 行列の数を指定する
    public static final int BOARD_MAX = 3;
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

        // 盤面の用意と初期化
        Board board = new Board();

        // 処理
        while (winFlag == false && drawFlag == false) {

            // 盤面の表示
            board.printBoard();
            

            // 人間の入力を受け取り、盤面に駒を置く
            markHumanPlace(board, scanner);

            // 勝利判定
            winFlag = board.checkWinAnswer(HUMAN);
            

            if (winFlag) {
                break;
            }
            // 引き分け判定
            drawFlag = board.checkDrawAnswer();
            if (drawFlag) {
                break;
            }

            // COMの入力
            markComPlace(board, comLevel);

            // 勝利判定
            winFlag = board.checkWinAnswer(COM);

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
            if (board.checkWinAnswer(HUMAN)) {
                System.out.println("あなたの勝ちです!!");
            } else {
                System.out.println("COMの勝ちです!!");
            }

        } else if (drawFlag) {
            System.out.println("引き分けです!!");
        }
    }

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

            System.out.println("行を入力してください。1~" + BOARD_MAX);
            String gyo = scanner.next();

            System.out.println("列を入力してください。1~" + BOARD_MAX);
            String retsu = scanner.next();

            // 入力チェック（範囲外かどうか）
            if (validNumber(gyo) == false || validNumber(retsu) == false) {
                System.out.println("正しい数値を入力してください。1~" + BOARD_MAX);
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
            board.placeMark(retsuNum, gyoNum, Board.HUMAN);
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
            int gyo = random.nextInt(BOARD_MAX);
            int retsu = random.nextInt(BOARD_MAX);

            // 空いている場合
            if (board.isEmpty(gyo, retsu)) {
                board.placeMark(gyo, retsu, COM);
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
                board.placeMark(1, 1, COM);
                placeCheck = true;
            } else {
                int gyo = random.nextInt(BOARD_MAX);
                int retsu = random.nextInt(BOARD_MAX);

                // 空いている場合
                if (board.isEmpty(gyo, retsu)) {
                    board.placeMark(gyo, retsu, COM);
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
    public static int[] findWinningPosition(Board board, int player) {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (board.isEmpty(i,j)) {
                    board.placeMark(i, j, player);

                    boolean winPossibiity = board.checkWinAnswer(player);

                    board.isEmpty(i,j);

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
    public static void placeComMark(Board board, int[] position) {
        board.placeMark(position[0], position[1], COM);
    }

}
