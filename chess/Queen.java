package chess;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Queen extends Piece {
    public Queen() {
    }

    public Queen(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement specific to queen movement.
        return false;
    }
}
