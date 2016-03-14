package chess;

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
    private List<Piece> pieces;
    private Color playerColor;
    private boolean isTurn;

    // All player's List<Pieces> will be the color of the player(white or black).
    public Player(Color color){
        this.playerColor = color;

//        for(Piece piece : pieces){
//            piece.setPieceColor(color);
//        }

        //white plays first
        if(color == Color.WHITE){
            this.isTurn = true;
        } else {
            this.isTurn = false;
        }
    }

    /**
     * -Print Board
     * -Ask player for move
     * -Check if move is Valid
     * -Keep until valid move is entered or player resigns
     * -Update Board accordingly
     *
     */
    public void playTurn(){
        //TODO: implement.
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
