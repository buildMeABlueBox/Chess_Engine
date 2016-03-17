package chess;
import static chess.ChessUtil.*;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * This class holds the information the player enters to move
 * it's pieces. It has:
 *
 * - A beginning location to pick the piece from .
 * - An ending location to place the piece.
 * - A boolean for whether the player asked for a draw or not.
 */
public class Move {
    private Square beginLocation;
    private Square endLocation;
    private boolean askedForDraw;
    private char    promotionTo;

    public Move(){

    }
    public Move(Square beginningLocation, Square endLocation, boolean askedForDraw, char promotionTo){
        beginLocation = beginningLocation;
        this.endLocation = endLocation;
        this.askedForDraw = askedForDraw;
        this.promotionTo = promotionTo;
    }

    public Square getbeginLocation(){
        return beginLocation;
    }

    public void setBeginLocation(Square beginLocation){
        this.beginLocation = beginLocation;
    }

    public Square getEndLocation(){
        return endLocation;
    }

    public void setEndLocation(Square endLocation){
        this.endLocation = endLocation;
    }

    public boolean getDrawRequest(){
        return askedForDraw;
    }

    public void setDrawRequestion(boolean b){
        this.askedForDraw = b;
    }

}
