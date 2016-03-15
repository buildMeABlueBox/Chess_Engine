package chess;

import java.util.Scanner;

/**
 * Created by Abhijit on 3/15/16.
 *
 * Utility class for all methods that are stateless.
 * final class so runtime is more efficient.
 */
public final class ChessUtil {

    private ChessUtil(){
        //private constructor so cannot be instantiated.
    }

    public static boolean checkPromotionChar(char c){
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

    public static void requestInput(Player player){
        System.out.println(player.getPlayerColor().toString().substring(0, 1) +
                player.getPlayerColor().toString().substring(1).toLowerCase()+ "'s move:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        checkInput(input, player);
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
    private static void checkInput(String input, Player player) {
        String[] individualInputs = input.split(" ");
        switch(individualInputs.length){
            default:
                invalidInput(player);
                return;
            case 2:
                if(!isFileRank(individualInputs[0])) {
                    invalidInput(player);
                    return;
                }
                if(!isFileRank(individualInputs[1])) {
                    invalidInput(player);
                    return;
                }
                //input is all clear
                return;

            case 3:
                //check first two parts of input to see if they follow proper form
                if(!isFileRank(individualInputs[0])) {;
                    invalidInput(player);
                    return;
                }
                if(!isFileRank(individualInputs[1])) {
                    invalidInput(player);
                    return;
                }

                //check third part to see if its going under a promotion (only if the size is 1)
                if(individualInputs[2].length() == 1){
                    //size of 3rd individual input might be indicating promotion. if thats the case, check to see that the ending location has an 8 or a 1 in it.
                    if(!checkPromotionChar(individualInputs[2].charAt(0))){
                        //isn't a valid promotion
                        invalidInput(player);
                        return;
                    }
                    if(individualInputs[1].charAt(1) != '8' && individualInputs[1].charAt(1) != '1'){
                        //isn't 8 or 1 (indicating the piece -- hopefully a pawn -- isn't at the end of the board yet).
                        invalidInput(player);
                        return;
                    }
                    return;
                }

                //see if the user requested a draw.
                if(!individualInputs[2].equalsIgnoreCase("draw")){
                    //player did not ask for draw. its some random gibberish..
                    invalidInput(player);
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
     * @return true if it is in the proper file-rank system
     */
    private static boolean isFileRank(String individualInput) {
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

    /**
     * The way the board is shown is from top (8) to bottom (1),
     * but if you try to access board[2][i] thinking you'd get
     * row 2, you are actually getting row 6. So to counter this,
     * just get the difference.
     */
    public static int getRowNum(int rowNumWanted){
        return 8-rowNumWanted;
    }

    public static int getNumFromChar(char c){
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
    private static void printPiece(Piece piece){
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
     * prints the board.
     *
     * prints ## on black squares and leaves white squares empty.
     * prints the pieces where they would be on the board as well.
     *
     */
    public static void printBoard(Square[][] board){
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
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    /**
     * prints ## if sq is black or "   " if white.
     * @param sq
     */
    private static void printSquare(Square sq) {
        if(sq.getColor() == Color.WHITE){
            System.out.print("   ");
        } else {
            System.out.print("## ");
        }
    }

    private static void invalidInput(Player player){
        System.out.println("\ninvalid input. Please try again.\n");
        requestInput(player);
    }
}
