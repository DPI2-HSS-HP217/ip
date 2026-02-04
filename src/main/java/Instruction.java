import java.io.IOException;

@FunctionalInterface
interface Instruction {
    boolean run(String[] args) throws NephilimException, IOException;
}
