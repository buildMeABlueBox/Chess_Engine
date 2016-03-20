package chess;


public class Chess {


    public static void main(String[] args) {
        Game chessGame = new Game();

        chessGame.startGame();

        while(keepPlaying(chessGame.getStatus())){
            chessGame.resume();
        }

        printResult(chessGame.getStatus());
    }

    public static void printResult(GameStatus gameStatus){
        switch(gameStatus){
            default:
                System.err.println("called from default: " + gameStatus.toString());
            case BLACK_WINS:
                System.out.println("Checkmate \n\n Black wins");
                break;
            case WHITE_WINS:
                System.out.println("Checkmate \n\n White wins");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            case STALEMATE:
                System.out.println("Stalemate");
        }
    }

    public static boolean keepPlaying(GameStatus gameStatus){
        switch(gameStatus){
            case WHITE_IN_CHECK:
                System.out.println("check\n");
                return true;

            case BLACK_IN_CHECK:
                System.out.println("check\n");
                return true;

            case PENDING:
                return true;

            default:
                //all other cases are false.
                return false;
        }
    }
}
