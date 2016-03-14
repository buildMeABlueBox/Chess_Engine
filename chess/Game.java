package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * The game controller class.
 * A game has:
 * - a board
 * - 2 players
 * - an GameStatus
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

//    public static void main(String[] args){
//        Game game = new Game();
//        game.initialize();
//        game.printBoard();
//    }

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
        createSquares();
        initializeBoard();

    }


    /**
     * - assigns color to each square
     * - places pieces in correct order on correct square
     */
    private void initializeBoard() {
        assignColorsToSquares();

        //TODO: create pieces for p1 and p2? or should pieces already be made and be within player class? Makes more sense for latter.
    }

    /**
     * Creates square objects in each square
     * placeholder in the board.
     */
    private void createSquares(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j<board.length; j++){
                board[i][j] = new Square();
            }
        }
    }

    /**
     * This assigns colors (white or black) to squares.
     */
    private void assignColorsToSquares(){
        int count = 0;
        int i = 0, j;
        for(Square[] sqArr: board){
            j = i++%2;
            for(Square sq : sqArr){
                switch(j){
                    case 0:
                        //perfect example of refactoring with lambdas.. but too lazy to figure out how to do it... heh heh..
                        if(count++%2 == 1){
                            sq.setColor(Color.BLACK);
                        } else {
                            sq.setColor(Color.WHITE);
                        }
                        break;

                    case 1:
                        if(count++%2 == 0){
                            sq.setColor(Color.BLACK);
                        } else {
                            sq.setColor(Color.WHITE);
                        }
                        break;
                }
            }
        }
    }

    /**
     * prints the board.
     *
     * prints ## on black squares and leaves white squares empty.
     * prints the pieces where they would be on the board as well.
     *
     */
    public void printBoard(){
        int rowNum = 8;
        int count = 0;
        int i = 0, j;
        for(Square[] sqArr: board){
            j = i++%2;
            for(Square sq : sqArr){
                Piece piece = sq.getPiece();
                if(piece != null){
                    //the square has a piece. get the type and print it out.
                    printPiece(piece);
                    continue;
                }
                //square doesn't have a piece so print out ## or "  " depending on square color.
                printSquare(sq);
            }
            System.out.print(rowNum-- + "\n");
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }

    /**
     * prints ## if sq is black or "   " if white.
     * @param sq
     */
    private void printSquare(Square sq) {
        if(sq.getColor() == Color.WHITE){
            System.out.print("   ");
        } else {
            System.out.print("## ");
        }
    }

    /**
     * for printing the board. If a piece is white it has
     * the prefix w, it has b if the piece is black.
     * Then you print out the letter of the piece
     *
     * p for pawn,
     * R for rook,
     * B for bishop,
     * K for king,
     * N for knight,
     * Q for queen
     * @param piece
     */
    private void printPiece(Piece piece){
        Color pieceColor = piece.getPieceColor();
        char color = pieceColor == Color.WHITE? 'w' : 'b';
        switch (piece.getPieceType()){
            case ROOK:
                System.out.print(color + "R");
                break;
            case KING:
                System.out.print(color+ "K");
                break;
            case QUEEN:
                System.out.print(color + "Q");
                break;
            case KNIGHT:
                System.out.print(color + "N");
                break;
            case PAWN:
                System.out.print(color + "p");
                break;
            case BISHOP:
                System.out.print(color + "B");
                break;
        }
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
