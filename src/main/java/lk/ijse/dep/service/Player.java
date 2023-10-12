package lk.ijse.dep.service;

public  abstract class Player {
    protected Board board;
    public Player(Board newBoard) {
        board=newBoard;
    }

    public abstract void movePiece(int col);

}
