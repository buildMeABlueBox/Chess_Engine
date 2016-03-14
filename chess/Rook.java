package chess;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Rook extends Piece {
    public Rook() {

    }

    public Rook(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {

        //TODO: implement Rook movement
        return false;
    }
}
