package chess;

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

    public Move(){

    }
    public Move(String userInput){
        String[] inputs = userInput.split(" ");
        askedForDraw = (inputs.length == 3 && inputs[2].
                        equalsIgnoreCase("draw?"));

        //TODO: initialize beginLocation and endLocation
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
