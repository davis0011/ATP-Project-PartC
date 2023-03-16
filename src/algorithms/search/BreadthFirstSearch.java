package algorithms.search;

import java.util.ArrayList;

/**
 * A searching algorithm that uses BFS to find the goal {@link AState} from the start {@link AState}, {@link BreadthFirstSearch}
 * is more consistent than {@link DepthFirstSearch} but can sometimes solve the {@link ISearchable} in more time and more
 * evaluated nodes, but it is equivalent in matters of O complexity
 * @implNote The {@link ArrayList} used as the queue is accessed in the course of the algorithm in such a way that it behaves
 * like a {@link java.util.Queue}
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{

    private int nodesEvaluated;
    private ArrayList<AState> queue;
    private ISearchable searchable;

    /**@implNote 1. Start by putting the start {@link AState} on top of a queue.<br>
    2. Take the top item of the stack and add it to the visited list.<br>
    3. Create a list of that {@link AState}s neighbours. Add the ones which aren't marked as visited to the back of the queue.<br>
    4. Keep repeating steps 2 and 3 until the stack is empty.<br>
     * @param searchable An instance of {@link ISearchable}
     * @return A {@link Solution} object containing the path from start to goal
     */
    @Override
    public Solution solve(ISearchable searchable) {
        nodesEvaluated = 0;
        this.searchable = searchable;
        // make variables here

        AState next = searchable.getStart();
        AState goal = searchable.getGoal();
        queue = new ArrayList<>();

        while(next != null){
            if(next.equals(goal)){
                Solution toReturn;
                toReturn = buildSolution(next, searchable.getStart());
                searchable.reset();
                return toReturn;
            }
            AState[] states = searchable.getAllPossibleStates(next);
            for (AState s : states){
                s.setPrev(next);
            }
            addToQueue(states);

            searchable.paintVisited(next);
            next = queue.remove(queue.size()-1);
            nodesEvaluated++;
        }
        searchable.reset();
        return null;
    }

    /**
     * @param states The neighbours of an {@link AState} that was inspected during the BFS search.
     * @implNote Since nodes get painted when we visit them, we can know if we should add a node to the queue.<br>
     * The same is true if the node is in the queue already, so we need to check that too
     */
    private void addToQueue(AState[] states){
        for (int i = 0; i < states.length; i++){
            if (searchable.wasVisited((MazeState) states[i])){
                continue;
            }
            if (queue.contains(states[i])){
                continue;
            }
            queue.add(0,states[i]);
        }
    }

    /**
     * @return The number of {@link AState}s the {@link BreadthFirstSearch} needed to check before it found the goal
     */
    @Override
    public String getNumberOfNodesEvaluated() {
        return Integer.toString(nodesEvaluated);
    }

    /**
     * @return The name of the algorithm that {@link BreadthFirstSearch} uses
     */
    public String getName(){
        return "Breadth First Search";
    }
}

