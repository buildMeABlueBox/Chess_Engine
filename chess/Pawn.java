package chess;

/**
 * Created by Abhijit on 3/1/16.
 */
public class Pawn extends Piece {
    public Pawn() {
    }

    public Pawn(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement pawn movement.
        return false;
    }
}
