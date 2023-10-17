package lk.ijse.dep.service;
//player is a abstract class because player class implement in the ai player and human player so it is idea
public  abstract class Player {
    //protected represent  #
    protected Board board;
    //if the method create in the class name it is constructor
    public Player(Board newBoard) {
        board=newBoard;
    }
    //abstact class can  have concrete method
    public abstract void movePiece(int col);
}
