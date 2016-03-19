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
        Piece bishop = new Bishop();
        Piece rook = new Rook();

        return bishop.isMoveValid(move, board) || rook.isMoveValid(move, board);
    }
}
