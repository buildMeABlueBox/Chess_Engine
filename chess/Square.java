package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * A board is comprised of squares. A square has:
 * - A color (WHITE so print nothing, BLACK means print ## -- unless there is a piece on the square of course..)
 * - A column (int even though it is given in letters from a- h but thats why we have the function getNumFromChar in chessUtil class)
 * - A row (int)
 * - A reference to the piece.
 */
public class Square {
    private int row;
    private int col;
    private Color color;
    private Piece piece;

    public Square(){

    }

    public Square(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getCol(){
        return col;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }
}
