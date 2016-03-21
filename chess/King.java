package chess;

import java.util.ArrayList;
import static chess.ChessUtil.*;

/**
 * Created by Abhijit on 3/1/16.
 */
public class King extends Piece{
    private boolean wasMoved;

    public King() {
    }

    public King(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
        wasMoved = false;
    }

    @Override
    /**
     * moves 1 space up down left right
     * can castle
     * only moves if it isn't put in check afterwards.
     */
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement king movement
        if(isTryingToCastle(board, move)){
            if(isValidCastle(board, move)){
                //move rook and king

                return true;
            } else {
                return false;
            }
        }

        ArrayList<Square> possibleMoves = getPossibleMoves(board, move.getbeginLocation());
        Piece piece = grabPieceByLocation(board, move.getEndLocation());
        if(piece == null) {
            return possibleMoves.contains(move.getEndLocation()) && !putInCheck(board,move);
        } else {
            return possibleMoves.contains(move.getEndLocation()) && !capturingSameColor(piece, move) && !putInCheck(board,move);
        }
    }

    public ArrayList<Square> getPossibleMoves(Square[][] board, Square beginLocation) {
        ArrayList<Square> possibleMoves = new ArrayList<Square>();
        int beginRow, beginCol, row, col;

        beginRow = beginLocation.getRow();
        beginCol = beginLocation.getCol();

        //north west
        row = beginRow-1;
        col = beginCol-1;
        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //north east
        row = beginRow-1;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //north
        row = beginRow-1;
        col = beginCol;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south
        row = beginRow+1;
        col = beginCol;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //east
        row = beginRow;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //west
        row = beginRow;
        col = beginCol-1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south east
        row = beginRow+1;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south west
        row = beginRow+1;
        col = beginCol-1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }


        return possibleMoves;
    }

    public boolean wasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }
}
