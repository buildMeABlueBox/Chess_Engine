package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * A board is comprised of squares. A square has:
 * - A color (WHITE so print nothing, BLACK means print ## -- unless there is a piece on the square of course..)
 * - A column
 * - A Rank (row)
 * - A reference to the piece.
 */
public class Square {
    //TODO: Confirm that this isn't a dilemma.
    /**
     * I don't like having an Enum for columns but an int for rows. It'd be too confusing when debugging
     * if both rows and columns are ints since you'd be looking at the board and debugger back and forth and you'd be
     * doing conversions of  7 == 'g' in your head. But you can't use an enum with names like 1,2,3,.. so right now it
     * makes sense to have an enum and an int.. right? Because having it this way you can clearly read that
     * row == 8 or col == a. Yea i think that makes sense.
     */
    private int row;
    private Column col;
    private Color color;
    //private boolean hasPiece;
    private Piece piece;

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Column getCol(){
        return col;
    }

    public void setCol(Column col){
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

//    public boolean getHasPiece(){
//        return hasPiece;
//    }
//
//    public void setHasPiece(boolean b){
//        this.hasPiece = b;
//    }
    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }
}
