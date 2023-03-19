import java.util.ArrayList;
public class Test{
public static void main(String[]args){
int[][] board = {{0, 0, 0, 2, 0, 0, 2, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {2, 0, 0, 0, 0, 0, 0, 0, 0, 2}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 1, 0, 0, 0}};
int[] bestMove = minimax(board, 1, 2);
for(int i=0; i<bestMove.length; i++)
System.out.println(bestMove[i]);
}

public static int evaluate(int[][] board, int player) {
    int score = 0;
    int numQueens = 0;
    int numMoves = 0;
    int numArrows = 0;

    // assign a score for each queen based on its position
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            if (board[i][j] == player) {
                numQueens++;
                int queenScore = getQueenScore(i, j);
                score += queenScore;
            }
        }
    }

    // calculate the number of available moves for each queen
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            if (board[i][j] == player) {
                numMoves += getMoves(board, i, j).size();
            }
        }
    }

    // count the number of arrows on the board
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            if (board[i][j] == 3) {
                numArrows++;
            }
        }
    }

    // apply weights and penalties to each factor and combine the scores
    score += numQueens * 10;
    score += numMoves * 5;
    score -= numArrows * 3;

    return score;
}

public static int getQueenScore(int row, int col) {
    // assign a score based on the position of the queen
    int centerRow = Math.abs(row - 5);
    int centerCol = Math.abs(col - 5);
    int distance = Math.max(centerRow, centerCol);
    return 10 - distance;
}

public static ArrayList<Integer[]> getAllMoves(int[][] board, int player){
ArrayList<Integer[]> queens = getQueens(board, player);
ArrayList<Integer[]> allMoves = new ArrayList<Integer[]>();
for(Integer[] queen: queens)
allMoves.addAll(getMoves(board, queen[0], queen[1]));
return allMoves;}

public static ArrayList<Integer[]> getMoves(int[][] board, int i, int j){
ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
int x;

for(x=1; j+x<10; x++){
if(board[i][j+x]==0){
Integer[] move = {i, j+x};
moves.add(move);}}

for(x=1; i+x<10; x++){
if(board[i+x][j]==0){
Integer[] move = {i+x, j};
moves.add(move);}}

for(x=1; j-x>-1; x++){
if(board[i][j-x]==0){
Integer[] move = {i, j-x};
moves.add(move);}}

for(x=1; i-x>-1; x++){
if(board[i-x][j]==0){
Integer[] move = {i-x, j};
moves.add(move);}}

for(x=1; j+x<10 && i+x<10; x++){
if(board[i+x][j+x]==0){
Integer[] move = {i+x, j+x};
moves.add(move);}}

for(x=1; j+x<10 && i-x>-1; x++){
if(board[i-x][j+x]==0){
Integer[] move = {i-x, j+x};
moves.add(move);}}

for(x=1; j-x>-1 && i-x>-1; x++){
if(board[i-x][j-x]==0){
Integer[] move = {i-x, j-x};
moves.add(move);}}

for(x=1; j-x>-1 && i+x<10; x++){
if(board[i+x][j-x]==0){
Integer[] move = {i+x, j-x};
moves.add(move);}}

return moves;}

public static boolean isGameOver(int[][] board, int currentPlayer) {
    boolean canMove = false;
    
    // check if current player can move any of their queens
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            if (board[i][j] == currentPlayer) {
                ArrayList<Integer[]> moves = getMoves(board, i, j);
                if (!moves.isEmpty()) {
                    canMove = true;
                    break;
                }
            }
        }
        if (canMove) {
            break;
        }
    }
    
    if (!canMove) {
        // current player cannot move any queens, game is over
        return true;
    }
    
    // check if other player can move any of their queens
    canMove = false;
    int otherPlayer = (currentPlayer == 1) ? 2 : 1;
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            if (board[i][j] == otherPlayer) {
                ArrayList<Integer[]> moves = getMoves(board, i, j);
                if (!moves.isEmpty()) {
                    canMove = true;
                    break;
                }
            }
        }
        if (canMove) {
            break;
        }
    }
    
    if (!canMove) {
        // other player cannot move any queens, game is over
        return true;
    }
    
    // game is not over
    return false;
}

public static ArrayList<Integer[]> getQueens(int[][] board, int player){
ArrayList<Integer[]> queens = new ArrayList<Integer[]>();
for(int i=0; i<10; i++){
for(int j=0; j<10; j++){
if(board[i][j]==player){
Integer[] queen = {i, j};
queens.add(queen);}}}
return queens;}

public static int[][] cloneBoard(int[][] board) {
    int[][] clonedBoard = new int[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            clonedBoard[i][j] = board[i][j];
        }
    }
    return clonedBoard;
}

public static String encodeMove(int[][] board) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            sb.append(board[i][j]);
        }
    }
    return sb.toString();
}

public static ArrayList<Integer[]> getArrows(int[][] board, int i, int j) {
    ArrayList<Integer[]> arrows = new ArrayList<Integer[]>();
    // Check for possible arrow positions in each direction
    int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
    for (int d = 0; d < 8; d++) {
        int r = i + dr[d];
        int c = j + dc[d];
        while (r >= 0 && r < 10 && c >= 0 && c < 10 && board[r][c] == 0) {
            arrows.add(new Integer[]{i, j, r, c});
            r += dr[d];
            c += dc[d];
        }
    }
    return arrows;
}

public static int[] minimax(int[][] board, int player, int depth) {
    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = null;

    ArrayList<Integer[]> moves = getAllMoves(board, player);
    for (Integer[] move : moves) {
        ArrayList<Integer[]> arrows = getArrows(board, move[0], move[1]);
        for (Integer[] arrow : arrows) {
            int[][] newBoard = cloneBoard(board);
            newBoard[move[0]][move[1]] = 0;
            newBoard[arrow[0]][arrow[1]] = 3;
            newBoard[arrow[2]][arrow[3]] = player;

            int[] scores = minimaxHelper(newBoard, player, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            int score = scores[2];
            if (score > bestScore) {
                bestScore = score;
                bestMove = new int[]{move[0], move[1], arrow[0], arrow[1], arrow[2], arrow[3]};
            }
        }
    }

    return bestMove;
}

public static int[] minimaxHelper(int[][] board, int player, int depth, int alpha, int beta) {
    ArrayList<Integer[]> moves = getAllMoves(board, player);
    int[] bestMove = new int[6];
    int bestScore;
    if (player == 1) {
        bestScore = Integer.MIN_VALUE;
    } else {
        bestScore = Integer.MAX_VALUE;
    }

    if (depth == 0 || moves.size() == 0) {
        bestScore = evaluate(board, player);
    } else {
        for (Integer[] move : moves) {
            ArrayList<Integer[]> arrows = getArrows(board, move[0], move[1]);
            for (Integer[] arrow : arrows) {
                int[][] newBoard = cloneBoard(board);
                newBoard[move[0]][move[1]] = 0;
                newBoard[arrow[0]][arrow[1]] = 3;
                newBoard[arrow[2]][arrow[3]] = player;
                int[] score = minimaxHelper(newBoard, 3 - player, depth - 1, alpha, beta);
                if (player == 1 && score[2] > bestScore) {
                    bestScore = score[2];
                    bestMove[0] = move[0];
                    bestMove[1] = move[1];
                    bestMove[2] = arrow[0];
                    bestMove[3] = arrow[1];
                    bestMove[4] = arrow[2];
                    bestMove[5] = arrow[3];
                    alpha = Math.max(alpha, bestScore);
                } else if (player == 2 && score[2] < bestScore) {
                    bestScore = score[2];
                    bestMove[0] = move[0];
                    bestMove[1] = move[1];
                    bestMove[2] = arrow[0];
                    bestMove[3] = arrow[1];
                    bestMove[4] = arrow[2];
                    bestMove[5] = arrow[3];
                    beta = Math.min(beta, bestScore);
                }
                if (beta <= alpha) {
                    break;
                }
            }
        }
    }
    return new int[] {bestMove[0], bestMove[1], bestScore};
}

public static void printBoard(int[][] board){
for(int i=0; i<10; i++){
for(int j=0; j<10; j++)
System.out.print(board[i][j]);
System.out.println();}}

}