package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * The game controller class.
 * A game has:
 * - a board
 * - 2 players
 * - an end endGameGameStatus
 * - a current checkStatus (if white or black is in check)
 *
 * --- add more if needed.
 */
public class Game {
    private final Square[][] board = new Square[8][8];
    private final Player white = new Player(Color.WHITE);
    private final Player black = new Player(Color.BLACK);
    private GameStatus status;

    public void startGame(){
        initialize();
    }

    /**
     * Resumes the game that is being played.
     *
     * If it's white's turn, white plays.
     * If it's black's turn, black plays.
     */
    public void resume(){

        if(white.getPlayerTurn()){
            //it's white's turn.
            white.playTurn();
            setTurn(black, white);
        } else {
            //it's black's turn.
            black.playTurn();
            setTurn(white, black);
        }
    }

    /**
     * Starts the game by:
     * -assigning pieces to players
     * -assigning color to each square
     *
     *
     */
    private void initialize(){

        //TODO: Assign pieces.
        initializeBoard(white, black);

    }


    /**
     * - assigns color to each square
     * - places pieces in correct order on correct square
     *
     * @param p1
     * @param p2
     */
    private void initializeBoard(Player p1, Player p2) {
        //TODO: assign color to each square on board.

        //TODO: create pieces for p1 and p2? or should pieces already be made and be within player class? Makes more sense for latter.
    }

    /**
     * prints the board.
     *
     * prints ## on black squares and leaves white squares empty.
     * prints the pieces where they would be on the board as well.
     *
     */
    private void printBoard(){
        int i = 8;
        for(Square[] sq : board){
            for(Square s: sq){
                //TODO: print piece name unless there is no piece -- in which case print "  " for white square or "##"  for black square.
            }
            //prints the row
            System.out.print(" "+ i-- +"\n");
        }
        //prints the column
        System.out.println("a  b  c  d  e  f  g  h");
    }


    /**
     * sets the turn of the player who will play to true
     * while setting the player who wont play to false
     * @param playerWhoWillPlay - set this player's turn to true
     * @param playerWhoWontPlay - set this player's turn to false
     */
    private void setTurn(Player playerWhoWillPlay, Player playerWhoWontPlay){
        playerWhoWillPlay.setPlayerTurn(true);
        playerWhoWontPlay.setPlayerTurn(false);
    }

    public Square[][] getBoard() {
        return board;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
