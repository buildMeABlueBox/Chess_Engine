package chess;

import java.util.ArrayList;
import static chess.ChessUtil.*;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Knight extends Piece{
    public Knight() {
    }

    public Knight(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        ArrayList<Square> possibleLocations = getPossibleLocations(board, move.getbeginLocation());
        Piece pieceAtEnd = grabPieceByLocation(board, move.getEndLocation());
        Piece pieceAtBeginning = grabPieceByLocation(board, move.getbeginLocation());
        if(pieceAtEnd == null){
            return possibleLocations.contains(move.getEndLocation());
        }else {
            return ((possibleLocations.contains(move.getEndLocation())) && (pieceAtBeginning.getPieceColor() != pieceAtEnd.getPieceColor()));
        }
    }

    /**
     * @param board - current state of board
     * @param beginLocation - beginning location of the knight
     * @return an array list of squares where the knight can possibly move so you can check if the end location is one of them.
     */
    private ArrayList<Square> getPossibleLocations(Square[][] board, Square beginLocation) {
        ArrayList<Square> possibleLocations = new ArrayList<Square>();
        int beginRow = beginLocation.getRow();
        int beginCol = beginLocation.getCol();

        if(sevenOrLess(beginRow+1) && zeroOrMore(beginCol-2)){
            possibleLocations.add(board[beginRow+1][beginCol-2]);
        }
        if(sevenOrLess(beginRow+1) && sevenOrLess(beginCol+2)){
            possibleLocations.add(board[beginRow+1][beginCol+2]);
        }
        if(zeroOrMore(beginRow-1) && zeroOrMore(beginCol-2)){
            possibleLocations.add(board[beginRow-1][beginCol-2]);
        }
        if(zeroOrMore(beginRow-1) && sevenOrLess(beginCol+2)){
            possibleLocations.add(board[beginRow-1][beginCol+2]);
        }
        if(sevenOrLess(beginRow+2) && sevenOrLess(beginCol+1)){
            possibleLocations.add(board[beginRow+2][beginCol+1]);
        }
        if(sevenOrLess(beginRow+2) && zeroOrMore(beginCol-1)){
            possibleLocations.add(board[beginRow+2][beginCol-1]);
        }
        if(zeroOrMore(beginRow-2) && sevenOrLess(beginCol+1)){
            possibleLocations.add(board[beginRow-2][beginCol+1]);
        }
        if(zeroOrMore(beginRow-2) && zeroOrMore(beginCol-1)){
            possibleLocations.add(board[beginRow-2][beginCol-1]);
        }

        return possibleLocations;
    }


}
