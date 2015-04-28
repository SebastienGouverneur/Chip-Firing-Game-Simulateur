
import org.graphstream.algorithm.generator.GridGenerator;

public class CustomGeneratorGrid extends GridGenerator {

    CustomGeneratorGrid(boolean cross, boolean tore, boolean generateXY, boolean directed) {
        super(cross, tore, generateXY, directed);
    }

    protected String nodeName(int x, int y) {
        return Integer.toString(x) + Integer.toString(y);
    }
}
