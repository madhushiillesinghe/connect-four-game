package lk.ijse.dep.service;
//player abstarct class implement the human player
public  class HumanPlayer  extends Player {
        //player and human player have a board but this time super class is newboard
        public HumanPlayer(Board newBoard) {
            super(newBoard);
        }
        //player class movepiece abastract class implement
        @Override
        public void movePiece(int col) {
            if(board.isLegalMoves(col)) {
                board.updateMove(col, Piece.BLUE);
                board.getBoardUI().update(col, true);

                Piece winner = board.findWinner().getWinningPiece();
                if (winner == Piece.BLUE) {
                    board.getBoardUI().notifyWinner(board.findWinner());
                } else {
                    if (!(board.existLegalMoves()) ){
                        board.getBoardUI().notifyWinner(board.findWinner());
                    }
                }
            }
        }
}

