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
    private boolean doubleJumped;
    private boolean wasMoved;
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
     */
    public boolean isMoveValid(Move move, Square[][] board) {
        Piece piece;
        Square beginLocation = move.getbeginLocation();
        Square endLocation = move.getEndLocation();

        int endLocationRow = endLocation.getRow();
        int beginLocationRow = beginLocation.getRow();

        int beginLocationCol = beginLocation.getCol();
        int endLocationCol = endLocation.getCol();

        piece = grabPieceByLocation(board, endLocation);

        if(tryingToMoveBackwards(grabPieceByLocation(board, move.getbeginLocation()), move)){
            // pawn is trying to move backwards
            return false;
        }

        if(isEnpassant(board, move)) {
            return true;
        }

        if(wasMoved){
            //piece was moved before and now is trying to move up (can't have piece up)
            if(endLocationCol == beginLocationCol && endLocationRow-beginLocationRow <= 1 && piece == null){
                //same column, difference in row is 1, no piece in place of moving.
                doubleJumped = false;
                return true;
            }

            if(piece != null && endLocation == getDiagonalSquare(board, beginLocation, true) && !capturingSameColor(piece,move)) {
                //end location is right diagonal square and there is a piece to be killed.
                doubleJumped = false;
                return true;

            } else if(piece != null && endLocation == getDiagonalSquare(board, beginLocation, false) && !capturingSameColor(piece,move)) {
                //end location is left diagonal square and there is a piece to be killed.
                doubleJumped = false;
                return true;
            } else {
                //anything else means it isn't a valid move for a pawn.
                return false;
            }
        }
        if(endLocationCol == beginLocationCol && endLocationRow-beginLocationRow <= 2 && piece == null){
            //moving 1 or 2 spaces up, same column, no piece in the way
            if(endLocationRow-beginLocationRow == 2){
                doubleJumped = true;
            }
            return true;
        }
        //trying to kill piece of opponent diagonally when the piece wasn't moved.

        if(piece != null && endLocation == getDiagonalSquare(board, beginLocation, true) &&  !capturingSameColor(piece,move)) {
            //end location is right diagonal square and there is a piece to be killed.
            doubleJumped = false;
            return true;

        } else if(piece != null && endLocation == getDiagonalSquare(board, beginLocation, false) &&  !capturingSameColor(piece,move)) {
            //end location is left diagonal square and there is a piece to be killed.
            doubleJumped = false;
            return true;
        } else {
            //anything else means it isn't a valid move for a pawn.
            return false;
        }
    }

    public boolean doubleJumped() {
        return doubleJumped;
    }

    public void setDoubleJumped(boolean doubleJumped) {
        this.doubleJumped = doubleJumped;
    }
}
