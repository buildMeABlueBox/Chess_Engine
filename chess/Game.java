package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

import java.util.Scanner;

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
////        game.initialize();
////        game.printBoard();
//        game.requestInput();
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
        placePieces(white);
        placePieces(black);
    }

    private void placePieces(Player player) {
        int num;
        Color color = player.getPlayerColor();
        int i;

        Piece[] pieces  = player.getPieces().toArray(new Piece[player.getPieces().size()]);
        //place 8 pawns
        for(i = 0; i<board.length; i++){
            num = color == Color.WHITE? getRowNum(2): getRowNum(7);
            board[num][i].setPiece(pieces[i]);
        }
        //place 2 rooks
        num = color == Color.WHITE? getRowNum(1): getRowNum(8);
        board[num][getNumFromChar('a')].setPiece(pieces[i++]);
        board[num][getNumFromChar('h')].setPiece(pieces[i++]);

        //place 2 bishops
        board[num][getNumFromChar('f')].setPiece(pieces[i++]);
        board[num][getNumFromChar('c')].setPiece(pieces[i++]);

        //place 2 knights
        board[num][getNumFromChar('g')].setPiece(pieces[i++]);
        board[num][getNumFromChar('b')].setPiece(pieces[i++]);

        //place 1 queen
        board[num][getNumFromChar('d')].setPiece(pieces[i++]);

        //place 1 king
        board[num][getNumFromChar('e')].setPiece(pieces[i]);

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
        //int count = 0;
        //int i = 0, j;
        for(Square[] sqArr: board){
            //j = i++%2;
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
        System.out.print(color);
        switch (piece.getPieceType()){
            case ROOK:
                System.out.print( "R ");
                break;
            case KING:
                System.out.print( "K ");
                break;
            case QUEEN:
                System.out.print( "Q ");
                break;
            case KNIGHT:
                System.out.print( "N ");
                break;
            case PAWN:
                System.out.print( "p ");
                break;
            case BISHOP:
                System.out.print( "B ");
                break;
        }
    }


    /**
     * The way the board is shown is from top (8) to bottom (1),
     * but if you try to access board[2][i] thinking you'd get
     * row 2, you are actually getting row 6. So to counter this,
     * just get the difference.
     */
    private int getRowNum(int rowNumWanted){
        return 8-rowNumWanted;
    }

    private int getNumFromChar(char c){
        switch(c){
            default:
                System.err.println("shouldn't be seeing this from getNumFromChar.. something went wrong..");
                return -1;

            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
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

    private void requestInput(){
        Player  player = white.getPlayerTurn() ? white : black;
        System.out.println(player.getPlayerColor().toString().substring(0, 1) +
                player.getPlayerColor().toString().substring(1).toLowerCase()+ "'s move:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        checkInput(input);


    }

    /**
     * Checks input. Heres how it checks:
     * - checks if length of input is 2 or 3 (2 for just coordinates 3 in case person asks for draw)
     * - if the length is 3, the last part of the input better be draw
     * - checks coordinates are in follow the form of File, Rank.
     *
     * If any of these do not match, the user is then asked for the input again.
     * @param input
     */
    private void checkInput(String input) {
        Player  player = white.getPlayerTurn() ? white : black;
        String[] individualInputs = input.split(" ");
        switch(individualInputs.length){
            default:
                invalidInput();
                return;
            case 2:
                if(!isFileRank(individualInputs[0])) {
                    invalidInput();
                    return;
                }
                if(!isFileRank(individualInputs[1])) {
                    invalidInput();
                    return;
                }
                //input is all clear
                return;

            case 3:
                //check first two parts of input to see if they follow proper form
                if(!isFileRank(individualInputs[0])) {;
                    invalidInput();
                    return;
                }
                if(!isFileRank(individualInputs[1])) {
                    invalidInput();
                    return;
                }

                //check third part to see if its going under a promotion (only if the size is 1)
                if(individualInputs[2].length() == 1){
                    //size of 3rd individual input might be indicating promotion. if thats the case, check to see that the ending location has an 8 or a 1 in it.
                    if(!checkPromotionChar(individualInputs[2].charAt(0))){
                        //isn't a valid promotion
                        invalidInput();
                        return;
                    }
                    if(individualInputs[1].charAt(1) != '8' && individualInputs[1].charAt(1) != '1'){
                        //isn't 8 or 1 (indicating the piece -- hopefully a pawn -- isn't at the end of the board yet).
                        invalidInput();
                        return;
                    }
                    return;
                }

                //see if the user requested a draw.
                if(!individualInputs[2].equalsIgnoreCase("draw")){
                    //player did not ask for draw. its some random gibberish..
                    invalidInput();
                    return;
                }

                //input is all clear
                return;
        }
    }

    /**
     * makes sure that user only inputs things like:
     *
     * e2 e5
     * or
     * e2 e5 draw
     * @param individualInput
     * @return
     */
    private boolean isFileRank(String individualInput) {
        if(individualInput.length() != 2){
            //input isn't file,rank size.
            return false;
        }
        if(getNumFromChar(individualInput.charAt(0)) == -1){
            //letter isn't a b c d e f g or h
            return false;
        }

        if(Character.isDigit(individualInput.charAt(1)) && individualInput.charAt(1) != '9'){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPromotionChar(char c){
        //TODO: MIGHT want to toUpperCase c? what if they put n instead of N. is that technically wrong?
        switch(c){
            default:
                return false;
            case 'N':
                return true;
            case 'Q':
                return true;
            case 'B':
                return true;
            case 'R':
                return true;
        }
    }

    private void invalidInput(){
        System.out.println("\ninvalid input. Please try again.\n");
        requestInput();
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
