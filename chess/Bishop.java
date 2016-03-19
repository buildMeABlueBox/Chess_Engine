package chess;
import java.util.ArrayList;

import static chess.ChessUtil.*;
/**
 * Created by Abhijit on 3/1/16.
 */
public class Bishop extends Piece {

    public Bishop(Color pieceColor, PieceType pieceType) {
        super(pieceColor, pieceType);
    }

    public Bishop(){
        
    }


    @Override
    public boolean isMoveValid(Move move, Square[][] board) {
        if(!rowsAndColsDifferent(move)){
            //either rows or columns or both are the same. wrong. bishop can't move left/right up/down. only diagonal.
            return false;
        }

        ArrayList<Square> possibleMoves = getPossibleMoves(board, move.getbeginLocation());
        if(possibleMoves.contains(move.getEndLocation())){
            if(!noPieceInBetween(board, move)){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean noPieceInBetween(Square[][] board, Move move){
        int beginRow, beginCol, endRow, endCol, possRow, possCol;
        beginRow = move.getbeginLocation().getRow();
        beginCol = move.getbeginLocation().getCol();
        endRow = move.getEndLocation().getRow();
        endCol = move.getEndLocation().getCol();

        boolean north;
        boolean east;

        north = beginRow > endRow;
        east = beginCol < endCol;

        possRow = beginRow;
        possCol = beginCol;

        if(north && east){
            //north east
            possRow = beginRow;
            possCol = beginCol;
            possCol++;
            possRow--;
            while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
                //north east diagonal row - 1 col + 1
                if(board[possRow--][possCol++].getPiece() != null){
                    return false;
                }
                else
                    return true;
            }
        } else if(north && !east){
            //north west
            possRow = beginRow;
            possCol = beginCol;
            possCol--;
            possRow--;
            while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
                //north west diagonal row - 1 col - 1
                if(board[possRow--][possCol--].getPiece() != null){
                    return false;
                }
                else
                    return true;
            }

        } else if (!north && !east){
            //south west
            possRow = beginRow;
            possCol = beginCol;
            possRow++;
            possCol--;
            while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possCol) && sevenOrLess(possCol)){
                //south west diagonal -- row + 1 col -1
                if(board[possRow++][possCol--].getPiece() != null){
                    return false;
                }
                else
                    return true;
            }

        } else if( !north && east){
            //south east

            possRow = beginRow;
            possCol = beginCol;

            possCol++;
            possRow++;
            while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
                //south east diagonal -- row + 1 col + 1
                if(board[possRow++][possCol++].getPiece() != null){
                    return false;
                }
                else
                    return true;
            }

        }
        return false;
    }

    private ArrayList<Square> getPossibleMoves(Square[][] board, Square beginLocation){
        ArrayList<Square> possibleMoves = new ArrayList<Square>();
        int possRow = beginLocation.getRow();
        int possCol = beginLocation.getCol();

        possRow++;
        possCol--;
        while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
            //south west diagonal -- row + 1 col -1
            possibleMoves.add(board[possRow++][possCol--]);
        }

        possRow = beginLocation.getRow();
        possCol = beginLocation.getCol();

        possCol++;
        possRow++;
        while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
            //south east diagonal -- row + 1 col + 1
            possibleMoves.add(board[possRow++][possCol++]);
        }

        possRow = beginLocation.getRow();
        possCol = beginLocation.getCol();
        possCol--;
        possRow--;
        while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
            //north west diagonal row - 1 col - 1
            possibleMoves.add(board[possRow--][possCol--]);
        }

        possRow = beginLocation.getRow();
        possCol = beginLocation.getCol();
        possCol++;
        possRow--;
        while(zeroOrMore(possRow) && zeroOrMore(possCol) && sevenOrLess(possRow) && sevenOrLess(possCol)){
            //north east diagonal row - 1 col + 1
            possibleMoves.add(board[possRow--][possCol++]);
        }
        return possibleMoves;
    }
    private boolean rowsAndColsDifferent(Move move){
        //beginning and ending rows don't match && beginning and ending columns don't match
        return move.getbeginLocation().getCol() != move.getEndLocation().getCol() && move.getbeginLocation().getRow() != move.getEndLocation().getRow();
    }
}
