package chess;

import static chess.ChessUtil.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * A player has:
 * -pieces
 * -a color
 * -an indicator to tell whether it is their turn.
 */
public class Player {
    private List<Piece> pieces = new ArrayList<Piece>();
    private Color playerColor;
    private boolean isTurn;

    // All player's List<Pieces> will be the color of the player(white or black).
    public Player(Color color){
        this.playerColor = color;

        //add pieces into list. -- DO NOT CHANGE ORDER (I've stupidly placed the pieces on the board with respect to the order they've been added into the list.. moving the rook below bishop means the bishop will be in the rooks place and vice versa..).
        for(int i = 0; i<8; i++){
            //add 8 pawns.
            pieces.add(new Pawn(color, PieceType.PAWN));
        }

        pieces.add(new Rook(color, PieceType.ROOK));                //2 rooks
        pieces.add(new Rook(color, PieceType.ROOK));

        pieces.add(new Bishop(color, PieceType.BISHOP));            //2 bishops
        pieces.add(new Bishop(color, PieceType.BISHOP));

        pieces.add(new Knight(color, PieceType.KNIGHT));            //2 knights
        pieces.add(new Knight(color, PieceType.KNIGHT));

        pieces.add(new Queen(color, PieceType.QUEEN));              //1 queen

        pieces.add(new King(color, PieceType.KING));                //1 king

        //white plays first
        if(color == Color.WHITE){
            this.isTurn = true;
        } else {
            this.isTurn = false;
        }
    }

    /**
     * -Check if move is Valid
     * -Moves piece if it is
     * -gets a list of pieces of opponent the moved piece can kill after it has been moved
     * -checks if king of opponent can be killed
     * -returns status depending on whether it is a check or checkmate.
     *
     */
    public GameStatus playTurn(Square[][] board, Move move){
        //TODO: implement.

        return GameStatus.BLACK_WINS;
    }

    public boolean getPlayerTurn(){
        return isTurn;
    }

    public void setPlayerTurn(boolean b){
        isTurn = b;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }
}
