package algorithms.search;


import java.io.Serializable;

/**
 * An object that will be used for solving the search for {@link ISearchable} problems
 */
public abstract class AState implements Serializable {

    /**
     * The previous {@link AState} in the {@link Solution} attempt for a given {@link ISearchingAlgorithm} run on an {@link ISearchable}
     */
    private AState prev;

    /**
     * @param prev The {@link AState} that we reached the current {@link AState} through
     */
    public AState(AState prev) {
        this.prev = prev;
    }

    /**
     * @return The {@link AState} that we reached the current {@link AState} through
     */
    public AState getPrev() {
        return prev;
    }

    /**
     * @param prev The {@link AState} that we reached the current {@link AState} through
     */
    public void setPrev(AState prev) {
        this.prev = prev;
    }

    /**
     * @param obj The object to compare
     * @return True the inherited equals function is
     */
    public abstract boolean equals(Object obj);

    /**
     * @return the String representation of the state
     */
    public abstract String toString();

    /**
     * @return the hashcode of the state, honestly we probably won't use this
     */
    @Override
    public abstract int hashCode();


}
