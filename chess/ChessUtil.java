package chess;

import java.util.ArrayList;
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

    public static Move requestInput(Player player, Square[][] board){
        System.out.println(player.getPlayerColor().toString().substring(0, 1) +
                player.getPlayerColor().toString().substring(1).toLowerCase()+ "'s move:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        return getUserInput(input, player, board);
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
    private static Move getUserInput(String input, Player player, Square[][] board) {
        String[] individualInputs = input.split(" ");
        Piece piece;

        switch(individualInputs.length){
            default:
                return invalidInput(player, board);

            case 1:
                if(individualInputs[0].equalsIgnoreCase("draw")){
                    return null;
                } else if(individualInputs[0].equalsIgnoreCase("resign")){
                    Color color = player.getPlayerColor();
                    if(color == Color.WHITE){
                        System.out.println("\nBlack Wins");
                    } else {
                        System.out.println("\nWhite Wins");
                    }
                    System.exit(0);
                }
                return invalidInput(player, board);
            case 2:
                String beginLocationString = individualInputs[0];
                String endLocationString = individualInputs[1];
                if(!isFileRank(beginLocationString)) {
                    return invalidInput(player, board);
                }
                if(!isFileRank(endLocationString)) {
                    return invalidInput(player, board);
                }
                //input is all clear
                char c = ' ';

                Move move = new Move(getSquare(board, beginLocationString),getSquare(board, endLocationString),false, c);
                piece = grabPieceByLocation(board, move.getbeginLocation());
                if(piece == null || piece.getPieceColor() != player.getPlayerColor()){
                    return invalidInput(player,board);
                }
                return move;

            case 3:
                String beginString = individualInputs[0];
                String endString = individualInputs[1];
                String promotionOrDraw = individualInputs[2];
                Move len3move;
                //check first two parts of input to see if they follow proper form
                if(!isFileRank(beginString)) {;
                    return invalidInput(player, board);
                }
                if(!isFileRank(endString)) {
                    return invalidInput(player, board);
                }

                //check third part to see if its going under a promotion (only if the size is 1)
                if(promotionOrDraw.length() == 1){
                    //size of 3rd individual input might be indicating promotion. if thats the case, check to see that the ending location has an 8 or a 1 in it.
                    char promotionChar = promotionOrDraw.charAt(0);
                    if(!checkPromotionChar(promotionChar)){
                        //isn't a valid promotion
                        return invalidInput(player, board);
                    }
                    if(endString.charAt(1) != '8' && endString.charAt(1) != '1'){
                        //isn't 8 or 1 (indicating the piece -- hopefully a pawn -- isn't at the end of the board yet).
                        return invalidInput(player, board);
                    }

                    len3move = new Move(getSquare(board, beginString),getSquare(board, endString),false, promotionChar);
                    piece = grabPieceByLocation(board, len3move.getbeginLocation());
                    if(piece == null || piece.getPieceColor() != player.getPlayerColor()){
                        return invalidInput(player,board);
                    }
                    return len3move;
                }

                //see if the user requested a draw.
                if(!individualInputs[2].equalsIgnoreCase("draw?")){
                    //player did not ask for draw. its some random gibberish..
                    return invalidInput(player, board);
                }

                //input is all clear and asked for draw
                len3move = new Move(getSquare(board, beginString),getSquare(board, endString),true,' ');
                piece = grabPieceByLocation(board, len3move.getbeginLocation());
                if(piece == null || piece.getPieceColor() != player.getPlayerColor()){
                    return invalidInput(player,board);
                }
            return len3move;
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
     *
     * p for pawn,
     * R for rook,
     * B for bishop,
     * K for king,
     * N for knight,
     * Q for queen
     * @param promotionChar - character pawn will be promoted to
     */
    public static PieceType getPieceType(char promotionChar){
        switch (promotionChar){
            default:
                return PieceType.QUEEN;
            case 'R':
                return PieceType.ROOK;
            case 'Q':
                return PieceType.QUEEN;
            case 'N':
                return PieceType.KNIGHT;
            case 'B':
                return PieceType.BISHOP;
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
        System.out.println();
        int rowNum = 8;
        for(Square[] sqArr: board){
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
     * given a beginning or end location input, get the file of the input.
     *
     * @param input
     * @return first letter in input
     */
    public char getFile(String input){
        return input.charAt(0);
    }

    /**
     * given a beginning or end location input, get the rank of the input.
     * @param input
     * @return second letter in input
     */
    public int getRank(String input){
        return Character.getNumericValue(input.charAt(1));
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

    public static Move invalidInput(Player player, Square [][] board){
        System.out.println("\nIllegal move, try again\n");
        return requestInput(player, board);
    }

    private static Square getSquare(Square[][] board, String locationString){
        int x = getNumFromChar(locationString.charAt(0));
        int y = getRowNum(Character.getNumericValue(locationString.charAt(1)));
        return board[y][x];
    }

    public static Piece grabPieceByLocation(Square[][] board, Square location){
        int row = location.getRow();
        int col = location.getCol();
        return board[row][col].getPiece();
    }

    public static Piece grabPieceByColorAndType(Square[][] board, Color color, PieceType type){
        for(Square[] squares : board){
            for(Square square : squares){
                Piece piece = square.getPiece();
                if(piece != null && piece.getPieceColor() == color && piece.getPieceType() == type){
                    return square.getPiece();
                }
            }
        }
        return null;
    }

    /**
     * calls isMoveValid to the specific piece.
     * @param piece
     */
    public static boolean callSpecificMoveisValid(Piece piece, Square[][] board, Move move){
        switch (piece.getPieceType()){
            default:
                System.err.print("Shouldn't be seeing this in callSpecificMoveisValid..");
                System.exit(1);
            case ROOK:
                Rook rook = (Rook) piece;
                return rook.isMoveValid(move,board);
            case KING:
                King king = (King) piece;
                return king.isMoveValid(move,board);
            case QUEEN:
                Queen queen = (Queen) piece;
                return queen.isMoveValid(move,board);
            case KNIGHT:
                Knight knight = (Knight) piece;
                return knight.isMoveValid(move,board);
            case PAWN:
                Pawn pawn = (Pawn) piece;
                return pawn.isMoveValid(move,board);
            case BISHOP:
                Bishop bishop = (Bishop) piece;
                return bishop.isMoveValid(move,board);
        }
    }


    /**
     * @param beginningSquare
     * @param rightSquare
     * @return diagonal square to the square that was passed in. which diagonal? depends on whether the right square was wanted. if it wasn't, return the left diagonal.
     * returns null if square is out of bounds.
     */
    public static Square getDiagonalSquare(Square[][] board, Square beginningSquare, boolean rightSquare){
        int diagonalRow;
        int diagonalCol;

        if(beginningSquare.getPiece() == null){
            return null;
        }

        if(rightSquare){

            if(beginningSquare.getPiece().getPieceColor() == Color.WHITE) {
                diagonalRow = beginningSquare.getRow() - 1;
                diagonalCol = beginningSquare.getCol() + 1;
            } else {
                diagonalRow = beginningSquare.getRow() + 1;
                diagonalCol = beginningSquare.getCol() - 1;
            }
            if(diagonalCol > 7 || diagonalRow > 7){
                return null;
            }

            if(diagonalCol< 0 || diagonalRow < 0){
                return null;
            }
            return board[diagonalRow][diagonalCol];
        } else {
            if(beginningSquare.getPiece().getPieceColor() == Color.WHITE) {
                diagonalRow = beginningSquare.getRow() - 1;
                diagonalCol = beginningSquare.getCol() - 1;
            } else {
                diagonalRow = beginningSquare.getRow() + 1;
                diagonalCol = beginningSquare.getCol() + 1;
            }

            if(diagonalCol < 1 || diagonalRow < 1){
                return null;
            }
            if(diagonalCol > 7 || diagonalRow > 7){
                return null;
            }

            return board[diagonalRow][diagonalCol];
        }
    }
    public static GameStatus findGameStatus(Square[][] board, Piece piece){
        //TODO: implement fully.
        Color currentPlayerColor = piece.getPieceColor();
        Color possibleCheckedPlayerColor = currentPlayerColor == Color.BLACK? Color.WHITE : Color.BLACK;
//        if(opposingKingInCheckMate(board,piece)){
//            if(possibleCheckedPlayerColor == Color.BLACK){
//                return GameStatus.WHITE_WINS;
//            } else {
//                return GameStatus.BLACK_WINS;
//            }
//        }
        if(opposingKingInCheck(board, piece)){
            if(possibleCheckedPlayerColor == Color.BLACK) {
                return GameStatus.BLACK_IN_CHECK;
            } else {
                return GameStatus.WHITE_IN_CHECK;
            }
        }
        else return GameStatus.PENDING;
    }

    private static boolean opposingKingInCheckMate(Square[][] board, Piece piece) {
        //get color of player who is putting other player's king in checkmate (if white is playing, get white)
        Color playerColor = piece.getPieceColor();

        //get color of player who will be checkmated (if white is playing, then black will be checkmated)
        Color opposingPlayerColor = playerColor == Color.BLACK? Color.WHITE : Color.BLACK;

        //get the opposing player's pieces (if white is playing, get black's pieces)
        ArrayList<Piece> opposingPlayerPieces = grabOpposingPlayerPieces(board, opposingPlayerColor);

        //get the king that will be in check mate (if white is playing, then get black's king)
        King possibleKingInCheckMate = (King) grabPieceByColorAndType(board, opposingPlayerColor, PieceType.KING);

        //get the possible locations that the king can move to
        ArrayList<Square> possibleKingEndingLocations = possibleKingInCheckMate.getPossibleMoves(board, possibleKingInCheckMate.getLocation());
        int possEndLocSize = possibleKingEndingLocations.size();
        int counter = 0;
        //store the location of where the king is
        Square kingOriginalLocation = possibleKingInCheckMate.getLocation();
        Piece holderPiece = null;


        for(Square possEndLoc : possibleKingEndingLocations){
            //hold piece of where king is about to move to.
            if(possEndLoc.getPiece() != null){
                holderPiece = possEndLoc.getPiece();
                possEndLocSize--;
            }
            //move king to the possible location and see if any piece can kill it.
            movePiece(board, possibleKingInCheckMate, new Move(possibleKingInCheckMate.getLocation(), possEndLoc, false, ' '));
            for(Piece p : opposingPlayerPieces){
                if(p.isMoveValid(new Move(p.getLocation(), possEndLoc, false, ' '),board)){
                    counter++;
                    break;
                }
            }
            //move king back to original position
            movePiece(board, possibleKingInCheckMate, new Move(possibleKingInCheckMate.getLocation(), kingOriginalLocation, false, ' '));

            //if the ending location the king was going to wasn't null, put the piece there
            if(holderPiece!=null) {
                board[possEndLoc.getRow()][possEndLoc.getCol()].setPiece(holderPiece);
            }
            holderPiece = null;
        }
        movePiece(board, possibleKingInCheckMate, new Move(possibleKingInCheckMate.getLocation(), kingOriginalLocation, false, ' '));
        if(possEndLocSize == 0){
            return false;
        }
        return possEndLocSize == counter;
    }

    public static boolean tryingToMoveBackwards(Piece piece, Move move){
        if(piece == null){
            return false;
        }
        if(piece.getPieceColor() == Color.WHITE){
            //white movement
            if( colsSame(move) && move.getEndLocation().getRow() > move.getbeginLocation().getRow() ){
                //trying to move backwards
                return true;
            }else{
                return false;
            }
        } else {
            //black movement

            if( colsSame(move) && move.getEndLocation().getRow() < move.getbeginLocation().getRow() ){
                return true;
            }else{
                return false;
            }
        }
    }


    public static boolean colsSame(Move move){
        return move.getbeginLocation().getCol() == move.getEndLocation().getCol();
    }

    public static boolean rowsSame(Move move){
        return move.getbeginLocation().getRow() == move.getEndLocation().getRow();
    }

    public static boolean isValidDistance(Move move, boolean rowsSame){
        if(rowsSame){
            return sevenOrLess(move.getEndLocation().getCol()-move.getbeginLocation().getCol());
        } else {
            return sevenOrLess(move.getEndLocation().getRow()-move.getbeginLocation().getRow());
        }
    }

    public static boolean zeroOrMore(int x){
        return x >= 0;
    }

    public static boolean sevenOrLess(int x){
        return x <= 7;
    }

    public static boolean capturingSameColor(Piece piece, Move move){
        Piece pieceAtBeginningLocation = move.getbeginLocation().getPiece();
        if(pieceAtBeginningLocation == null){
            return false;
        }
        Color beginPieceColor = pieceAtBeginningLocation.getPieceColor();
        Color endPieceColor = piece.getPieceColor();

        return beginPieceColor==endPieceColor;
    }

    public static boolean isWithinBounds(int row, int col){
        return zeroOrMore(row) && zeroOrMore(col) && sevenOrLess(row) && sevenOrLess(col);
    }

    /**
     * @param board
     * @param color - color of piece (not opposing piece)
     * @return list of opposing pieces by opposing player
     */
    public static ArrayList<Piece> grabOpposingPlayerPieces(Square[][] board, Color color){
        ArrayList<Piece> opposingPlayerPieces = new ArrayList<Piece>();
        Color opposingColor = color == Color.BLACK? Color.WHITE : Color.BLACK;
        for(Square[] sqArr : board){
            for(Square sq : sqArr){
                Piece piece = sq.getPiece();
                if(piece != null && piece.getPieceColor() == opposingColor){
                    opposingPlayerPieces.add(sq.getPiece());
                }
            }
        }
        return opposingPlayerPieces;
    }

    /**
     *
     * @param board
     * @param piece - piece that was moved and might be checking square
     * @return true if the piece that was moved can move to the king's position
     */
    public static boolean opposingKingInCheck(Square[][] board, Piece piece){
        //get opposing color king
        ArrayList<Piece> opposingPieces = grabOpposingPlayerPieces(board, piece.getPieceColor());
        Piece oppKing = null;
        for(Piece oppPiece : opposingPieces){
            if(oppPiece.getPieceType() == PieceType.KING){
                oppKing = oppPiece;
                break;
            }
        }
        if(oppKing == null){
            //will never happen.
            return false;
        }

        Square kingLocation = oppKing.getLocation();
        if(piece.isMoveValid(new Move(piece.getLocation(),kingLocation,false,' '),board)){
            return true;
        }
        return false;
    }

    /**
     * check for reaching end of board for a pawn
     * @param color - color of pawn
     * @param endLocationRow - should be 0 or 7
     * @return true if it is ready for promotion
     */
    public static boolean reachingEnd(Color color, int endLocationRow){
        if(color == Color.WHITE){
            return endLocationRow == 0;

        } else {
            return endLocationRow == 7;
        }
    }

    public static boolean statusIsCheck(GameStatus status){
        return status == GameStatus.BLACK_IN_CHECK || status == GameStatus.WHITE_IN_CHECK;
    }

    /**
     *
     * @param board
     * @param move - contains move of piece moving that might block check
     * @param status - status of game
     * @return true if the move of the piece blocks the king from being checked or moves the king out of check.
     */
    public static boolean isSavedFromCheck(Square[][] board, Move move, GameStatus status){

        //grab piece that's trying to move
        Piece piece = grabPieceByLocation(board, move.getbeginLocation());
        if(piece == null){
            return false;
        }
        //see if the way the piece is moving is valid
        boolean moveIsValid = callSpecificMoveisValid(piece, board, move);
        if(!moveIsValid){
            return false;
        }
        //move piece to location.
        Square pieceOrigLocation = piece.getLocation();
        movePiece(board, piece, move);
        Move moveBackToOriginalLocation = new Move(piece.getLocation(),pieceOrigLocation,false, ' ');
        Piece checkedKing;

        //king is black or white depending on status
        checkedKing = status == GameStatus.BLACK_IN_CHECK? grabPieceByColorAndType(board, Color.BLACK, PieceType.KING) : grabPieceByColorAndType(board, Color.WHITE, PieceType.KING);

        if(checkedKing==null){
            //never should happen
            return false;
        }

        boolean kingIsInCheck = putInCheck(board, new Move(piece.getLocation(), checkedKing.getLocation(), false, ' '));

        //for some reason when putInCheck is called, the king's color changes to the opposite color. this reverts it.
        if(status == GameStatus.BLACK_IN_CHECK){
            checkedKing.setPieceColor(Color.BLACK);
        } else {
            checkedKing.setPieceColor(Color.WHITE);
        }

        if(kingIsInCheck){
            //moving the piece was a bad move.
            movePiece(board, piece, moveBackToOriginalLocation);
            return false;
        } else {
            movePiece(board, piece, moveBackToOriginalLocation);
            return true;
        }
    }

    public static void movePiece(Square[][] board, Piece piece, Move move) {
        int endLocationRow = move.getEndLocation().getRow();
        int endLocationCol = move.getEndLocation().getCol();
        int beginLocationRow = move.getbeginLocation().getRow();
        int beginLocationCol = move.getbeginLocation().getCol();
        if(piece.getPieceType()!= PieceType.KING && piece.getPieceType()!= PieceType.PAWN){
            board[beginLocationRow][beginLocationCol].setPiece(null);
        }
        if(piece.getPieceType() == PieceType.PAWN){
            if(isEnpassant(board,move)){
                board[beginLocationRow][beginLocationCol].setPiece(null);
                board[endLocationRow][endLocationCol].setPiece(piece);
                board[endLocationRow][endLocationCol].getPiece().setLocation(board[endLocationRow][endLocationCol]);
                int row = piece.getPieceColor() == Color.BLACK? endLocationRow-1 : endLocationRow+1;
                board[row][endLocationCol].setPiece(null);
                return;
            }
            board[beginLocationRow][beginLocationCol].setPiece(null);
            Pawn pawn = (Pawn) piece;
            pawn.setWasMoved(true);
            if(reachingEnd(piece.getPieceColor(), endLocationRow)){
                //pawn is heading to end of board.. check for promotion.. if no promotion listed.. set piece to queen.
                if(move.getPromotionTo() == ' '){
                    piece.setPieceType(PieceType.QUEEN);
                } else {
                    piece.setPieceType(getPieceType(move.getPromotionTo()));
                }

            }else {
                piece = pawn;
            }
        }
        if(piece.getPieceType() == PieceType.ROOK){
            Rook rook = (Rook) piece;
            rook.setWasMoved(true);
            piece = rook;
        }
        if(piece.getPieceType() == PieceType.KING){
            //check if move is castling
            if(isTryingToCastle(board, move)){
                if(isValidCastle(board, move)){
                //move rook and king

                    //move king
                    board[beginLocationRow][beginLocationCol].setPiece(null);
                    board[endLocationRow][endLocationCol].setPiece(piece);
                    piece.setLocation(board[endLocationRow][endLocationCol]);

                    //king moved to bottom east
                    if(endLocationRow == 7 && endLocationCol == 6){
                        board[7][5].setPiece(board[7][7].getPiece());
                        board[7][7].setPiece(null);
                        board[7][5].getPiece().setLocation(board[7][5]);
                        return;
                    } else if(endLocationRow == 7 & endLocationCol == 2){
                        board[7][3].setPiece(board[7][0].getPiece());
                        board[7][0].setPiece(null);
                        board[7][3].getPiece().setLocation(board[7][3]);
                        return;
                    } else if(endLocationRow == 0 && endLocationCol == 2){
                        board[0][3].setPiece(board[0][0].getPiece());
                        board[0][0].setPiece(null);
                        board[0][3].getPiece().setLocation(board[0][3]);
                        return;
                    } else if(endLocationRow == 0 && endLocationCol == 6){
                        board[0][5].setPiece(board[0][7].getPiece());
                        board[0][7].setPiece(null);
                        board[0][5].getPiece().setLocation(board[0][5]);
                        return;
                    }

                }
            }
        }
        board[endLocationRow][endLocationCol].setPiece(piece);
        piece.setLocation(board[endLocationRow][endLocationCol]);
    }

    public static boolean isTryingToCastle(Square[][] board, Move move) {
        //grab king and check color.
        King king = (King) grabPieceByLocation(board, move.getbeginLocation());
        if(king == null || king.wasMoved()){
            return false;
        }
        Color kingColor = king.getPieceColor();
        Square blackRightSquare = board[0][6];
        Square blackLeftSquare = board[0][2];
        Square whiteRightSquare = board[7][2];
        Square whiteLeftSquare = board[7][6];

        if(satisfiesCastleLocations(kingColor, Color.BLACK, move.getEndLocation(), blackLeftSquare)){
            //black king trying to move to the black left square
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.BLACK, move.getEndLocation(), blackRightSquare)){
            //black king trying to move to the black right square
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.WHITE, move.getEndLocation(), whiteRightSquare)){
            //white king trying to move to right white square
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.WHITE, move.getEndLocation(), whiteLeftSquare)){
            //white king trying to move to white left square
            return true;
        }

        return false;
    }

    private static boolean satisfiesCastleLocations(Color kingColor, Color playerColor, Square endLocation, Square constantLoc){
        return kingColor == playerColor && endLocation == constantLoc;
    }

    public static boolean isValidCastle(Square[][] board, Move move) {
        King king = (King) grabPieceByLocation(board, move.getbeginLocation());
        if(king.wasMoved()){
            return false;
        }
        Color kingColor = king.getPieceColor();
        Rook blackLeftRook = (Rook)board[0][0].getPiece();
        Rook blackRightRook = (Rook)board[0][7].getPiece();
        Rook whiteLeftRook = (Rook)board[7][0].getPiece();
        Rook whiteRightRook = (Rook)board[7][7].getPiece();
        Square blackRightSquare = board[0][6];
        Square blackLeftSquare = board[0][2];
        Square whiteRightSquare = board[7][6];
        Square whiteLeftSquare = board[7][2];

        if(satisfiesCastleLocations(kingColor, Color.BLACK, move.getEndLocation(), blackLeftSquare)){
            //black king trying to move to the black left square
            //check if squares are empty and king and rook were never moved.
            if(grabPieceByLocation(board, board[0][3]) != null || grabPieceByLocation(board, board[0][2]) != null ||  grabPieceByLocation(board, board[0][1]) != null
                    || blackLeftRook == null ||  blackLeftRook.wasMoved()){
                //piece between left rook and king
                return false;
            }
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.BLACK, move.getEndLocation(), blackRightSquare)){
            //black king trying to move to the black right square
            //check if squares are empty and king and rook were never moved.
            if(grabPieceByLocation(board, board[0][5]) != null || grabPieceByLocation(board, board[0][6]) != null
                    || blackRightRook == null ||  blackRightRook.wasMoved()){
                //piece between right rook and king
                return false;
            }
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.WHITE, move.getEndLocation(), whiteRightSquare)){
            //white king trying to move to right white square
            //check if squares are empty and king and rook were never moved.
            if(grabPieceByLocation(board, board[7][5]) != null || grabPieceByLocation(board, board[7][6]) != null
                    || whiteRightRook == null ||  whiteRightRook.wasMoved()){
                //piece between right rook and king
                return false;
            }
            return true;
        }
        if(satisfiesCastleLocations(kingColor, Color.WHITE, move.getEndLocation(), whiteLeftSquare)){
            //white king trying to move to white left square
            //check if squares are empty and king and rook were never moved.
            if(grabPieceByLocation(board, board[7][3]) != null || grabPieceByLocation(board, board[7][2]) != null ||  grabPieceByLocation(board, board[7][1]) != null
                    || whiteLeftRook == null ||  whiteLeftRook.wasMoved()){
                //piece between left rook and king
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * gets all the pieces for the opposing player and for each piece, calls their isMoveValid to the ending location of where the square will be
     * if it returns true for any of the pieces, then that means the opposing player can move their piece there and kill the king. Which shouldn't
     * happen.
     *
     * if a king is killing a piece by moving to its end location, change the color of that piece to the opposite color.
     * if a king is moving and not killing a piece, put a new piece there with the opposite color.
     *
     * @param board
     * @param move - contains the ending location of the square where the king will be
     * @return true if isMoveValid for any of the pieces is true. false otherwise
     */
    public static boolean putInCheck(Square[][] board, Move move){
        ArrayList<Piece> opposingPlayerPieces = grabOpposingPlayerPieces(board,move.getbeginLocation().getPiece().getPieceColor());
        Piece endLocPiece = grabPieceByLocation(board, move.getEndLocation());
        if(endLocPiece!= null) {
            //piece is at the spot where king wants to move (king will kill piece)

            //color of player
            Color pieceColorMem = move.getEndLocation().getPiece().getPieceColor();

            //color of opposing piece
            Color oppPiece = pieceColorMem == Color.BLACK? Color.WHITE : Color.BLACK;

            //set the piece color to the color of player
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(pieceColorMem);

            //see if opposing pieces can kill piece
            for (Piece piece : opposingPlayerPieces) {
                if (piece.isMoveValid(new Move(piece.getLocation(), move.getEndLocation(), false, ' '), board)) {
                    //set piece that was going to be killed back to its original piece color
                    board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(oppPiece);
                    return true;
                }
            }
            //set piece that was going to be killed back to its original piece color
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(oppPiece);
            return false;
        } else {
            //piece is null. so create a piece to be placed in the ending location and give it the opposite players color.
            endLocPiece = new Pawn();
            //get original piece color
            Color beginLocPieceCol = move.getbeginLocation().getPiece().getPieceColor();
            //set color to the piece
            endLocPiece.setPieceColor(beginLocPieceCol);
            //place piece in the end location
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(endLocPiece);
            //see for any of the opposing color pieces, if they can kill the piece that's been placed there.
            for (Piece piece : opposingPlayerPieces) {
                if (piece.isMoveValid(new Move(piece.getLocation(), move.getEndLocation(), false, ' '), board)) {
                    board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(null);
                    return true;
                }
            }
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(null);
            return false;
        }
    }

    public static boolean isEnpassant(Square[][] board, Move move){
        Square endingLocation = move.getEndLocation();
        Square rightDiagonal = getDiagonalSquare(board, move.getbeginLocation(), true);
        Square leftDiagonal = getDiagonalSquare(board, move.getbeginLocation(), false);
        Color pawnThatisKillingColor = move.getbeginLocation().getPiece().getPieceColor();
        Color pawnToBeKilledColor = pawnThatisKillingColor == Color.BLACK? Color.WHITE : Color.BLACK;
        int row =0 , col = 0;

        //ending location has to be diagonal square.
        if(endingLocation != rightDiagonal && endingLocation != leftDiagonal){
            return false;
        }
        if(endingLocation.getPiece() != null){
            return false;
        }
        //get row and column for piece that is about to be killed
        row = pawnToBeKilledColor == Color.BLACK? move.getEndLocation().getRow()+1:move.getEndLocation().getRow()-1;
        col = endingLocation.getCol();

        //pawn that's about to be killed
        Piece pawnToBeKilled = grabPieceByLocation(board, board[row][col]);

        if(pawnToBeKilled == null || pawnToBeKilled.getPieceColor() != pawnToBeKilledColor){
            return false;
        }

        return true;
    }
}
