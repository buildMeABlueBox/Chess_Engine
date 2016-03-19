package chess;

import java.util.ArrayList;
import static chess.ChessUtil.*;

/**
 * Created by Abhijit on 3/1/16.
 */
public class King extends Piece{

    public King() {
    }

    public King(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    @Override
    /**
     * moves 1 space up down left right
     * can castle
     * only moves if it isn't put in check afterwards.
     */
    public boolean isMoveValid(Move move, Square[][] board) {
        //TODO: implement king movement
//        if(isTryingToCastle(move)){
//            if(isValidCastle(move)){
//                //move rook and king
//
//                return true;
//            } else {
//                return false;
//            }
//        }

        ArrayList<Square> possibleMoves = getPossibleMoves(board, move.getbeginLocation());
        Piece piece = grabPiece(board, move.getEndLocation());
        if(piece == null) {
            return possibleMoves.contains(move.getEndLocation()) && !putInCheck(board,move);
        } else {
            return possibleMoves.contains(move.getEndLocation()) && !capturingSameColor(piece, move) && !putInCheck(board,move);
        }
    }

    /**
     * gets all the pieces for the opposing player and for each piece, calls their isMoveValid to the ending location of where the square will be
     * if it returns true for any of the pieces, then that means the opposing player can move their piece there and kill the king. Which shouldn't
     * happen.
     *
     * if a king is killing a piece by moving to its end location, change the color of that piece to the opposite color.
     * if a king is moving and not killing a piece, put a new piece there with the opposite color.
     *
     * @param board
     * @param move
     * @return true if isMoveValid for any of the pieces is true. false otherwise
     */
    private boolean putInCheck(Square[][] board, Move move){
        ArrayList<Piece> opposingPlayerPieces = grabOpposingPlayerPieces(board,move);
        Piece endLocPiece = grabPiece(board, move.getEndLocation());
        if(endLocPiece!= null) {
            //piece is at the spot where king wants to move (king will kill piece)

            //color of player
            Color pieceColorMem = move.getEndLocation().getPiece().getPieceColor();

            //color of opposing piece
            Color oppPiece = pieceColorMem == Color.BLACK? Color.WHITE : Color.BLACK;

            //set the piece color to the color of player
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(pieceColorMem);

            //see if opposing pieces can kill piece
            for (Piece piece : opposingPlayerPieces) {
                if (piece.isMoveValid(new Move(piece.getLocation(), move.getEndLocation(), false, ' '), board)) {
                    //set piece that was going to be killed back to its original piece color
                    board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(oppPiece);
                    return true;
                }
            }
            //set piece that was going to be killed back to its original piece color
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].getPiece().setPieceColor(oppPiece);
            return false;
        } else {
            //piece is null. so create a piece to be placed in the ending location and give it the opposite players color.
            endLocPiece = new Pawn();
            //get original piece color
            Color beginLocPieceCol = move.getbeginLocation().getPiece().getPieceColor();
            //set color to the piece
            endLocPiece.setPieceColor(beginLocPieceCol);
            //place piece in the end location
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(endLocPiece);
            //see for any of the opposing color pieces, if they can kill the piece that's been placed there.
            for (Piece piece : opposingPlayerPieces) {
                if (piece.isMoveValid(new Move(piece.getLocation(), move.getEndLocation(), false, ' '), board)) {
                    board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(null);
                    return true;
                }
            }
            board[move.getEndLocation().getRow()][move.getEndLocation().getCol()].setPiece(null);
            return false;
        }
    }

    private ArrayList<Square> getPossibleMoves(Square[][] board, Square beginLocation) {
        ArrayList<Square> possibleMoves = new ArrayList<Square>();
        int beginRow, beginCol, row, col;

        beginRow = beginLocation.getRow();
        beginCol = beginLocation.getCol();

        //north west
        row = beginRow-1;
        col = beginCol-1;
        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //north east
        row = beginRow-1;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //north
        row = beginRow-1;
        col = beginCol;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south
        row = beginRow+1;
        col = beginCol;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //east
        row = beginRow;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //west
        row = beginRow;
        col = beginCol-1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south east
        row = beginRow+1;
        col = beginCol+1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }

        //south west
        row = beginRow+1;
        col = beginCol-1;

        if(isWithinBounds(row,col)){
            possibleMoves.add(board[row][col]);
        }


        return possibleMoves;
    }
}
