package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * This is the base class implementation of a piece.
 * It defines the fields and behaviors that all pieces
 * have. Better to use an abstract class than interface
 * because interface makes all fields final and static
 * which is not what we want.
 *
 * Each Piece has:
 * -A color associated with it (WHITE or BLACK)
 * -A location on the board.
 *
 *
 * piece is such a funny word. pie.. ce. he he..
 */
public abstract class Piece {
    Color pieceColor;
    Square location;

    public abstract boolean isMoveValid(Move move, Square[][] board);

    public Color getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }
}
