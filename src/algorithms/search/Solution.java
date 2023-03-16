package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple wrapper object for returning solutions to {@link ISearchable}s
 */
public class Solution implements Serializable {
    private ArrayList<AState> path;

    /**
     * Each {@link Solution} has an array of {@link AState} it builds
     */
    public Solution() {
        path = new ArrayList<>();
    }

    /**
     * @param state The {@link AState} to add to the path of the {@link Solution}
     */
    public void addStateToPath(AState state)
    {
        path.add(0,state);
    }

    /**
     * @return The finished path from the start of an {@link ISearchable} to its goal
     */
    public ArrayList<AState> getSolutionPath(){
        return path;
    }

    @Override
    public String toString() {
        return Integer.toString(path.size());
    }
}
