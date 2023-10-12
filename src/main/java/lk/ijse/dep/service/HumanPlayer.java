package lk.ijse.dep.service;
public  class HumanPlayer  extends Player {
        public HumanPlayer(Board newBoard) {
            super(newBoard);
        }
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

