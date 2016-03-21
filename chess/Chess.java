package chess;

/**
 * Main chess class that controls the game.
 *@author Abhijit
 */
public class Chess {


    public static void main(String[] args) {
        Game chessGame = new Game();

        chessGame.startGame();

        while(keepPlaying(chessGame.getStatus())){
            chessGame.resume();
        }

        printResult(chessGame.getStatus());
    }

    /**
     * prints the result of the game
     *
     * @param gameStatus - status of game
     */
    public static void printResult(GameStatus gameStatus){
        switch(gameStatus){
            default:
                System.err.println("\ncalled from default: " + gameStatus.toString());
            case BLACK_WINS:
                System.out.println("Checkmate \n\nBlack wins");
                break;
            case WHITE_WINS:
                System.out.println("Checkmate \n\nWhite wins");
                break;
            case DRAW:
                System.out.println("\nDraw");
                break;
            case STALEMATE:
                System.out.println("\nStalemate");
        }
    }

    /**
     *
     * @param gameStatus - status of the game
     * @return true if status is one where you should keep playing
     */
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
