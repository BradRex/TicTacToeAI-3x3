import java.util.ArrayList;
import java.util.List;

//Contains static methods to implement a minimax algorithm for the 3x3 noughts and crosses.
public class AIMiniMax
{
    /*This method iterates through the nodes immediately available to the AI player and will retrieve the score
     *for each state and returns a PointsAndScores list of all nodes and their respective values.*/
    public static List<PointsAndScores> minimax(Board board)
    {
        //Create an array list of PointsAndScores objects.
        List<PointsAndScores> nodesAndValues = new ArrayList<>();

        //Retrieve a List of all points available to the AI player from this game state.
        List<Point> availablePoints = board.getAvailablePoints();

        /*For each available node (point), retrieve the nodes minimum score, create a node-score pair (PointsAndScores object)
         *and add the PointsAndScores object to the nodesAndValues List.*/
        for(Point point : availablePoints)
        {
            /*This block will construct the new board state by placing the current move, pass the new board state to
             *the min method, add the node-score pair to the list and then reverse the changes made to the board state.*/
            board.placeAMove(point, 1);
            int nodeScore = min(board);
            PointsAndScores nodeWithValue = new PointsAndScores(nodeScore, point);
            nodesAndValues.add(nodeWithValue);
            board.board[point.x][point.y] = 0;
        }

        //Return the PointsAndScores list.
        return nodesAndValues;
    }

    //Method for calculating the minimum value of a given board state. This is for when the AI Players turn is being
    //evaluated.
    public static int min(Board board)
    {
        //Will return a positive score of 1, if the board state represents a win for the AI Player.
        if(board.hasXWon()){return 1;}
        //Will return a negative score of -1, if the board state represents a win for the Human Player.
        if(board.hasOWon()){return -1;}

        //Retrieve a List of all points available to the Human player from this game state.
        List<Point> availablePoints = board.getAvailablePoints();
        //Will return 0, if the board is full and neither the AI or Human player has won.
        if(availablePoints.isEmpty()){return 0;}

        /*A score variable to store the score of the least valuable node (move) found in the list of points.
         *Therefore, it is set to the largest Integer value to guarantee it will be changed (to either a -1, 0 or 1).*/
        int minScore = Integer.MAX_VALUE;

        /*For each available node (point), retrieve the nodes maximum value, reassign the minScore if the nodeScore
         *is a new minimum score. This represents the minimisation stage which limits how well a Human can play.*/
        for(Point point : availablePoints)
        {
            /*This block will construct the new board state by placing the current move, pass the new board state to
             *the max method, reassign the minScore if necessary and then revert the changes made to the board state.*/
            board.placeAMove(point, 2);
            int nodeScore = max(board);
            if(nodeScore < minScore){minScore = nodeScore;}
            board.board[point.x][point.y] = 0;
        }

        //Return the minimum node score found from this board state.
        return minScore;
    }

    //Method for calculating the maximum value of a given board state. This is for when the Human Players turn is being
    //evaluated.
    public static int max(Board board)
    {
        //Will return a positive score of 1, if the board state represents a win for the AI Player.
        if(board.hasXWon()){return 1;}
        //Will return a negative score of -1, if the board state represents a win for the Human Player.
        if(board.hasOWon()){return -1;}

        //Retrieve a List of all points available to the Human player from this game state.
        List<Point> availablePoints = board.getAvailablePoints();
        //Will return 0, if the board is full and neither the AI or Human player has won.
        if(availablePoints.isEmpty()){return 0;}

        /*A score variable to store the score of the most valuable node (move) found in the list of points.
         *Therefore, it is set to the minimum Integer value to guarantee it will be changed (to either a -1, 0 or 1).*/
        int maxScore = Integer.MIN_VALUE;

        //For each available node (point), retrieve the nodes maximum value, reassign the minScore if the nodeScore
        //is a new minimum score. This represents the maximisation stage which maximises how the AI can play.
        for(Point point : availablePoints)
        {
            /*This block will construct the new board state by placing the current move, pass the new board state to
             *the min method, reassign the maxScore if necessary and then revert the changes made to the board state. */
            board.placeAMove(point, 1);
            int nodeScore = min(board);
            if(nodeScore > maxScore){maxScore = nodeScore;}
            board.board[point.x][point.y] = 0;
        }

        //Return the maximum node score found from this board state.
        return maxScore;
    }
}
