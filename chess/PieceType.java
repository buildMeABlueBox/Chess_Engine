package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * Enumeration to use when identifying a piece. much easier
 * to use enums than to create an object of type piece and
 * then look at the name of the object.
 *
 * example: if(piece.getName.equals("rook")
 * vs.
 * if(piece.PieceType == PieceType.ROOK)
 *
 * latter is better.
 *
 */
public enum PieceType {
    KING,
    QUEEN,
    ROOK,
    KNIGHT,
    BISHOP,
    PAWN
}
