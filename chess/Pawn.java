package chess;

import static chess.ChessUtil.*;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Pawn extends Piece {
    public boolean getWasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }

    boolean wasMoved;
    public Pawn() {
        wasMoved = false;
    }

    public Pawn(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    /**
     * Pawn can move:
     * -1 space up
     * -2 spaces up if it hasn't moved from its original location
     * -
     */
    public boolean isMoveValid(Move move, Square[][] board) {
        Square beginLocation = move.getbeginLocation();
        Square endLocation = move.getEndLocation();

        int endLocationRow = endLocation.getRow();
        int beginLocationRow = beginLocation.getRow();

        int beginLocationCol = beginLocation.getCol();
        int endLocationCol = endLocation.getCol();

        if(tryingToMoveBackwards(grabPiece(board, move.getbeginLocation()), move)){
            // pawn is trying to move backwards
            return false;
        }

        if(wasMoved){
            //piece was moved before and now is trying to move up (can't have piece up)
            if(endLocationCol == beginLocationCol && endLocationRow-beginLocationRow <= 1 && grabPiece(board, endLocation) == null){
                //same column, difference in row is 1, no piece in place of moving.
                return true;
            }

            if(endLocation == getDiagonalSquare(board, beginLocation, true) && grabPiece(board, endLocation) != null) {
                //end location is right diagonal square and there is a piece to be killed.
                return true;

            } else if(endLocation == getDiagonalSquare(board, beginLocation, false) && grabPiece(board, endLocation) != null) {
                //end location is left diagonal square and there is a piece to be killed.
                return true;
            } else {
                //anything else means it isn't a valid move for a pawn.
                return false;
            }
        }
        if(endLocationCol == beginLocationCol && endLocationRow-beginLocationRow <= 2 && grabPiece(board, endLocation) == null){
            //moving 1 or 2 spaces up, same column, no piece in the way
            return true;
        }
        //trying to kill piece of opponent diagonally when the piece wasn't moved.

        if(endLocation == getDiagonalSquare(board, beginLocation, true) && grabPiece(board, endLocation) != null) {
            //end location is right diagonal square and there is a piece to be killed.
            return true;

        } else if(endLocation == getDiagonalSquare(board, beginLocation, false) && grabPiece(board, endLocation) != null) {
            //end location is left diagonal square and there is a piece to be killed.
            return true;
        } else {
            //anything else means it isn't a valid move for a pawn.
            return false;
        }
    }
}
