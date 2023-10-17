package lk.ijse.dep.service;

import java.util.Random;
//fully abstact use the interface
public interface Board  {
    public int NUM_OF_ROWS=5;
    public  int NUM_OF_COLS=6;
    Random RANDOM_GENERATOR =new Random() ;

     //interface cant use the concrete method and infinity the public abstact
     BoardUI getBoardUI();
     int findNextAvailableSpot(int col);
     boolean isLegalMoves(int col);
     boolean existLegalMoves();
     void updateMove(int col,Piece move);
     void updateMove(int col,int row,Piece move);
     Winner findWinner();
     BoardImpl getBoardImpl();
}
