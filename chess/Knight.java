package chess;

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
        //TODO: implement knight movement
        return false;
    }
}
