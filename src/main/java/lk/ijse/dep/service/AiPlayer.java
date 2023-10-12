package lk.ijse.dep.service;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class AiPlayer extends Player {
    public AiPlayer (Board board){
        super(board);

    }
    @Override
    public void movePiece(int col) {
//        do{
//            col=(int) (Math.random()*6);
//        }while (!(col>-1 && col<6)|| !(board.isLegalMoves(col))) ;

        Mcts mcts = new Mcts(board.getBoardImpl());
        col = mcts.starMCTS();
        if (board.isLegalMoves(col)) {
            board.updateMove(col, Piece.GREEN);
            board.getBoardUI().update(col, false);

            Piece winner = board.findWinner().getWinningPiece();
            if (winner == Piece.GREEN) {
                board.getBoardUI().notifyWinner(board.findWinner());
            } else {
                if (!(board.existLegalMoves())) {
                    board.getBoardUI().notifyWinner(board.findWinner());
                }
            }
        }
    }
    static class Mcts {
        private BoardImpl board;
        public Mcts(BoardImpl board){
            this.board=board;
        }

        public  int starMCTS(){
            System.out.println("MCTS  working");
            int count=0;

            Node newNode=new Node(board);

            while (count<4000){
                count++;

                //Select Node
                Node promisingNode = selectPromisingNode(newNode);

                //Expand Node
                Node selected=promisingNode;

                if (selected.board.getStatus()){
                    selected= expandNodeAndReturnRandom(promisingNode);
                }

                //Simulate
                Piece resultPiece=simulateLightPlayout(selected);

                //BackPropagate
                backPropagation(resultPiece,selected);
            }

            Node best= newNode.getChildWithMaxScore();

            System.out.println("Best move scored " + best.score + " and was visited " + best.visits + " times");

            return best.board.cols;
        }
        //backpropagation step
        private void backPropagation(Piece resultPiece, Node selected) {

            Node node=selected;

            while (node!=null){
                node.visits++;

                if (node.board.piece==resultPiece){
                    node.score++;
                }
                node = node.parent;
            }
        }
        //simulate to decide whether the best node inorder to select for the move
        private Piece simulateLightPlayout(Node promisingNode) {

            Node node=new Node(promisingNode.board);
            node.parent=promisingNode.parent;

            Winner winner=node.board.findWinner();

            if (winner.getWinningPiece()==Piece.BLUE){
                node.parent.score=Integer.MIN_VALUE;

                return node.board.findWinner().getWinningPiece();
            }

            while (node.board.getStatus()){
                BoardImpl nextMove=node.board.getRandomLegalNextMove();
                Node child = new Node(nextMove);
                child.parent=node;
                node.addChild(child);
                node=child;

            }
            return node.board.findWinner().getWinningPiece();
        }
        //Expands the tree via creating child nodes by dividing the parent node
        private Node expandNodeAndReturnRandom(Node node) {
            BoardImpl board= node.board;
            List<BoardImpl> legalMoves= board.getAllLegalNextMoves();

            for (BoardImpl move : legalMoves) {
                Node child = new Node(move);
                child.parent = node;
                node.addChild(child);

            }

            int random = Board.RANDOM_GENERATOR.nextInt(node.child.size());

            return node.child.get(random);
        }
        //select & return the best node with highest UCT value after backpropagation
        private Node selectPromisingNode(Node tree) {
            Node node=tree;
            while (node.child.size()!=0){
                node=UCT.findBestNodeWithUCT(node);
            }
            return node;
        }
    }

    public static class UCT {
        public static double uct(int nodeVisit, int totalVisit, double winScore) {
            if (nodeVisit == 0) {
                return Integer.MAX_VALUE;
            }
            return ((double) winScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
        }
        public static Node findBestNodeWithUCT(Node node){
            int parentVisit = node.visits;
            return Collections.max(node.child,Comparator.comparing(c ->uct(parentVisit,c.score,c.visits)));
        }
    }
    static class Node{
        public BoardImpl board;
        // count visit node back
        public int visits;
        public int score;
        List<Node> child=new ArrayList<>();
        Node parent =null;

        public  Node(BoardImpl board){
            this.board=board;
        }
        public Node getChildWithMaxScore() {
            Node result = child.get(0);
            for (int i = 1; i < child.size(); i++) {
                if(child.get(i).score>result.score){
                    result=child.get(i);
                }
            }
            return result;
        }
        //add new node to children arrayList
        void addChild(Node node) {
            child.add(node);
        }

    }
}