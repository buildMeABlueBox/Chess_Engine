package chess;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Bishop extends Piece {

    public Bishop(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    public Bishop(){
        
    }


    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement Bishop movement
        return false;
    }
}
