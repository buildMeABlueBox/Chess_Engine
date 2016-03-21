package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

import static chess.ChessUtil.*;

/**
 * The game controller class.
 * @author Abhijit
 * A game has:
 * - a board
 * - 2 players
 * - an GameStatus
 *
 */
public class Game {
    private final Square[][] board = new Square[8][8];
    private final Player white = new Player(Color.WHITE);
    private final Player black = new Player(Color.BLACK);
    private GameStatus status;
    private Pawn previouslyMovedWhitePawn;
    private Pawn previouslyMovedBlackPawn;

    public Game(){
        status = GameStatus.PENDING;
    }

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
        Player playerWhoWillPlayThisTurn = white.getPlayerTurn()? white : black;
        Player playerWhoWillPlayNextTurn = playerWhoWillPlayThisTurn.getPlayerColor() == Color.WHITE? black : white;

        Move move = requestInput(playerWhoWillPlayThisTurn, board);
        if(move == null && playerWhoWillPlayNextTurn.hasOfferedDraw()){
            status = GameStatus.DRAW;
            return;
        } else {
            playerWhoWillPlayNextTurn.setOfferedDraw(false);
        }

        status = playerWhoWillPlayThisTurn.playTurn(board, move, status);

        setTurn(playerWhoWillPlayNextTurn, playerWhoWillPlayThisTurn);

        if(previouslyMovedWhitePawn != null && playerWhoWillPlayThisTurn.getPlayerColor() == Color.WHITE){
            previouslyMovedWhitePawn.setDoubleJumped(false);
        }
        if(previouslyMovedBlackPawn != null && playerWhoWillPlayThisTurn.getPlayerColor() == Color.BLACK){
            previouslyMovedBlackPawn.setDoubleJumped(false);
        }
        if(move != null && grabPieceByLocation(board, move.getEndLocation()).getPieceType() == PieceType.PAWN && grabPieceByLocation(board, move.getEndLocation()).getPieceColor() == Color.WHITE){
            previouslyMovedWhitePawn = (Pawn) grabPieceByLocation(board, move.getEndLocation());
        }
        if(move != null && grabPieceByLocation(board, move.getEndLocation()).getPieceType() == PieceType.PAWN && grabPieceByLocation(board, move.getEndLocation()).getPieceColor() == Color.BLACK){
            previouslyMovedBlackPawn = (Pawn) grabPieceByLocation(board, move.getEndLocation());
        }

    }

    /**
     * Starts the game by:
     * -creating new square objects for each square on the board
     * -initializing the board (assigns colors to squares and places pieces on square)
     */
    private void initialize(){
        createSquares();
        initializeBoard();
        printBoard(board);
    }


    /**
     * - assigns color to each square
     * - places pieces in correct order on correct square
     */
    private void initializeBoard() {
        assignColorsToSquares();
        placePiecesInStartingPosition(white);
        placePiecesInStartingPosition(black);
    }

    /**
     * places pieces on the players board in the starting position of a chess game.
     * @param player - current player
     */
    private void placePiecesInStartingPosition(Player player) {
        int num;
        Color color = player.getPlayerColor();
        int i;

        Piece[] pieces  = player.getPieces().toArray(new Piece[player.getPieces().size()]);
        //place 8 pawns
        for(i = 0; i<board.length; i++){
            num = color == Color.WHITE? getRowNum(2): getRowNum(7);
            board[num][i].setPiece(pieces[i]);
            pieces[i].setLocation(board[num][i]);
        }
        //place 2 rooks
        num = color == Color.WHITE? getRowNum(1): getRowNum(8);
        pieces[i].setLocation(board[num][getNumFromChar('a')]);
        board[num][getNumFromChar('a')].setPiece(pieces[i++]);

        pieces[i].setLocation(board[num][getNumFromChar('h')]);
        board[num][getNumFromChar('h')].setPiece(pieces[i++]);

        //place 2 bishops
        pieces[i].setLocation(board[num][getNumFromChar('f')]);
        board[num][getNumFromChar('f')].setPiece(pieces[i++]);

        pieces[i].setLocation(board[num][getNumFromChar('c')]);
        board[num][getNumFromChar('c')].setPiece(pieces[i++]);

        //place 2 knights
        pieces[i].setLocation(board[num][getNumFromChar('g')]);
        board[num][getNumFromChar('g')].setPiece(pieces[i++]);

        pieces[i].setLocation(board[num][getNumFromChar('b')]);
        board[num][getNumFromChar('b')].setPiece(pieces[i++]);

        //place 1 queen
        pieces[i].setLocation(board[num][getNumFromChar('d')]);
        board[num][getNumFromChar('d')].setPiece(pieces[i++]);

        //place 1 king
        pieces[i].setLocation(board[num][getNumFromChar('e')]);
        board[num][getNumFromChar('e')].setPiece(pieces[i]);

    }

    /**
     * Creates square objects in each square
     * placeholder in the board.
     */
    private void createSquares(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j<board.length; j++){
                board[i][j] = new Square(i,j);
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

    public Pawn getPreviouslyMovedWhitePawn() {
        return previouslyMovedWhitePawn;
    }

    public void setPreviouslyMovedWhitePawn(Pawn previouslyMovedPawn) {
        this.previouslyMovedWhitePawn = previouslyMovedPawn;
    }

    public Pawn getPreviouslyMovedBlackPawn() {
        return previouslyMovedBlackPawn;
    }

    public void setPreviouslyMovedBlackPawn(Pawn previouslyMovedBlackPawn) {
        this.previouslyMovedBlackPawn = previouslyMovedBlackPawn;
    }
}
