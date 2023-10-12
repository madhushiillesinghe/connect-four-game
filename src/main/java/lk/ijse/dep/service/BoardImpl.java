package lk.ijse.dep.service;

import java.util.List;
import java.util.ArrayList;

public class BoardImpl implements Board {
    public int cols;
    public Piece piece;
    private BoardUI boardUI;
    private Piece[][] pieces;



    public BoardImpl( BoardUI boardUI) {
        this.boardUI = boardUI;
        this.pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                this.pieces[i][j] = Piece.EMPTY;
            }
        }
    }


    @Override
    public BoardUI getBoardUI() {
        return this.boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            if (pieces[col][row] == Piece.EMPTY) {
                return row;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMoves(int col) {
        int row = findNextAvailableSpot(col);
        if (row != -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existLegalMoves() {
        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int row = 0; row < NUM_OF_ROWS; row++) {
                if (pieces[col][row] == Piece.EMPTY)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        cols=col;
        piece=move;
        int row = findNextAvailableSpot(col);
        if (row != -1) {
            pieces[col][row] = move;
        }
    }

    public void updateMove(int col, int row, Piece move) {
            pieces[col][row] = move;
    }

    @Override
    public Winner findWinner() {
        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int row = 0; row < NUM_OF_ROWS - 3; row++) {
                if (pieces[col][row] != Piece.EMPTY &&
                        pieces[col][row] == pieces[col][row + 1] &&
                        pieces[col][row] == pieces[col][row + 2] &&
                        pieces[col][row] == pieces[col][row + 3]) {
                    return new Winner(pieces[col][row], col, row, col, (row + 3));
                }
            }
        }


        for (int col = 0; col < NUM_OF_COLS - 3; col++) {
            for (int row = 0; row < NUM_OF_ROWS; row++) {
                if (pieces[col][row] != Piece.EMPTY &&
                        pieces[col][row] == pieces[col + 1][row] &&
                        pieces[col][row] == pieces[col + 2][row] &&
                        pieces[col][row] == pieces[col + 3][row]) {
                    return new Winner(pieces[col][row], col, row, (col+3), row);
                }
            }
        }
        return new Winner(Piece.EMPTY);
    }
//Algorythm methods
public BoardImpl(Piece[][] pieces, BoardUI boardUI){
    this.pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];

    //copies existing 2D array to newly created array here
    for (int i = 0;i < NUM_OF_COLS;i++){
        for (int j = 0;j < NUM_OF_ROWS;j++){
            this.pieces[i][j] = pieces[i][j];
        }
    }
    this.boardUI = boardUI;
}
    public boolean getStatus() {
        if (!existLegalMoves()) return false;

        Winner winner = findWinner();
        return winner.getWinningPiece() == Piece.EMPTY;
    }


    public BoardImpl getRandomLegalNextMove() {
        final List<BoardImpl> legalMoves = getAllLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        final int random;
        random =Board.RANDOM_GENERATOR.nextInt(legalMoves.size());
        return legalMoves.get(random);
    }
    @Override
    public BoardImpl getBoardImpl() {
        return this;
    }

    public List<BoardImpl> getAllLegalNextMoves() {
        Piece nextPiece = piece == Piece.BLUE ? Piece.GREEN : Piece.BLUE;
        List<BoardImpl> nextMoves = new ArrayList<>();
        for (int col = 0; col < NUM_OF_COLS; col++) {
            if (findNextAvailableSpot(col) > -1) {
                BoardImpl legalMove = new BoardImpl(this.pieces, this.boardUI);
                legalMove.updateMove(col, nextPiece);
                nextMoves.add(legalMove);
            }
        }
        return nextMoves;
    }
}


