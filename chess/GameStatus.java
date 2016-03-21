package chess;

/**
 * Created by Abhijit on 2/29/16.
 */

/**
 * Status of a game that is in play.
 *
 * Has:
 * -PENDING (meaning the game is still resuming so there is no result yet.)
 * -WHITE_WINS (white wins)
 * -BLACK_WINS (black wins)
 * -Draw (Draw - both players agreed to having a draw)
 * -Stalemate (A stalemate -- a type of Draw, but not by player's choice of offering)
 * -WHITE_IN_CHECK
 * -BLACK_IN_CHECK
 *
 *@author Abhijit
 *
 */
public enum GameStatus {
    PENDING,
    WHITE_WINS,
    BLACK_WINS,
    DRAW,
    STALEMATE,
    WHITE_IN_CHECK,
    BLACK_IN_CHECK
}

