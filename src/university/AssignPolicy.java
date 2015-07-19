package university;

/**
 * Holds the different strategies for connecting entity objects.
 * 
 * University entity objects are interconnected in a complex matter.
 * To keep the system in a consistent state some methods that introduce 
 * connections between entity objects receive an AssignPolicy that tells 
 * them whether they should take care of the reverse connection or not.
 * 
 * @author Mavrov
 */
public enum AssignPolicy {
    /**
     * Use BOTH_WAYS to connect one object to another and let them take care
     * of the reverse connection automatically.
     */
    BOTH_WAYS,
    /**
     * Use ONE_WAY to connect one object to another and manually resolve
     * the reverse connection.
     */
    ONE_WAY
}
