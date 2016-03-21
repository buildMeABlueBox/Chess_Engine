package chess;
import static chess.ChessUtil.*;
/**
 * Created by Abhijit on 3/1/16.
 */
public class Rook extends Piece {
    private boolean wasMoved;
    public Rook() {

    }

    public Rook(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
        wasMoved = false;
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        boolean pieceBlocking;

        //if rows and columns are both different, return false

        if(!rowsSame(move) && !colsSame(move)){
            return false;
        }

        //check if rows or columns are same.
        if(rowsSame(move)){
            if(!isValidDistance(move, true)){
                return false;
            }
            //moving left to right
            pieceBlocking = isPieceInBetween(board, move, true);

            if(pieceBlocking) {
                return false;
            }

        } else {
            //cols Same .. moving up and down

            if(!isValidDistance(move, false)){
                return false;
            }
            pieceBlocking = isPieceInBetween(board, move, false);

            if(pieceBlocking){
                return false;
            }
        }
        Piece piece = grabPieceByLocation(board, move.getEndLocation());

        if(piece != null && capturingSameColor(piece, move)){
            return false;
        }
        return true;
    }


    /**
     * Takes difference between ending location and beginning location and sees
     * that for every square between those, there isn't a piece
     * @param move
     * @return true if there is a piece between the board squares
     */
    private boolean isPieceInBetween(Square[][] board, Move move, boolean rowsSame) {
        int beginCol, endCol, beginRow, endRow, biggerNum, smallerNum;

        if(rowsSame){
            beginRow = endRow = move.getbeginLocation().getRow();
            beginCol = move.getbeginLocation().getCol();
            endCol = move.getEndLocation().getCol();

            biggerNum = beginCol > endCol? beginCol : endCol;
            smallerNum = beginCol > endCol? endCol : beginCol;

            for(int i =biggerNum-1; i>smallerNum; i--){
                //not checking the ending column because a piece might be there but if it is, its to be killed.
                if(board[beginRow][i].getPiece() != null){
                    return true;
                }
            }
        } else {
            //rows different
            beginCol = endCol = move.getbeginLocation().getCol();
            beginRow = move.getbeginLocation().getRow();
            endRow = move.getEndLocation().getRow();

            biggerNum = beginRow > endRow? beginRow : endRow;
            smallerNum = beginRow > endRow? endRow : beginRow;

            for(int i = biggerNum-1; i>smallerNum; i--){
                if(board[i][beginCol].getPiece() != null){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean wasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }
}
