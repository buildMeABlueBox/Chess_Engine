package chess;

/**
 * Created by Abhijit on 3/1/16.
 */
public class King extends Piece{

    public King() {
    }

    public King(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement king movement
        return false;
    }
}
