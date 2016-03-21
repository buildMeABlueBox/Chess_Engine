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
 * @author Abhijit
 *
 * Each Piece has:
 * -A color associated with it (WHITE or BLACK)
 * -A location on the board.
 *
 */
public abstract class Piece {
    private Color pieceColor;
    private Square location;
    private PieceType pieceType;

    public Piece(){

    }

    public Piece(Color pieceColor, PieceType pieceType){
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public abstract boolean isMoveValid(Move move, Square[][] board);

    public Square getLocation() {
        return location;
    }

    public void setLocation(Square location) {
        this.location = location;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }
}
