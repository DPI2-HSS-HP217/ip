package nephilim;

import java.io.IOException;

/**
 * A functional interface with one method that takes in a String array of arguments and returns
 * a boolean.
 */
@FunctionalInterface
interface Instruction {
    String run(String[] args) throws NephilimException, IOException;
}
