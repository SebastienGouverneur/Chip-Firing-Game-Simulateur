
import org.graphstream.algorithm.generator.BananaTreeGenerator;

class CustomBananaTreeGenerator extends BananaTreeGenerator {

    public CustomBananaTreeGenerator(int i) {
        super(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.graphstream.algorithm.generator.Generator#begin()
     */
    @Override
    public void begin() {
        addNode("101");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.graphstream.algorithm.generator.Generator#nextEvents()
     */
    @Override
    public boolean nextEvents() {
        addNode(getNodeId(currentStarIndex, 0));

        for (int i = 1; i < k; i++) {
            addNode(getNodeId(currentStarIndex, i));
            addEdge(String.format("E%04d", edgeId++),
                    getNodeId(currentStarIndex, 0),
                    getNodeId(currentStarIndex, i));
        }

        addEdge(String.format("E%04d", edgeId++),
                getNodeId(currentStarIndex, 1), "101");

        currentStarIndex++;

        if (setCoordinates) {
            flushCoords();
        }

        return true;
    }

    /**
     * Format node id.
     *
     * @param star index of the star
     * @param index index of the node in the star
     * @return unique node id
     */
    @Override
    protected String getNodeId(int star, int index) {
        return String.format("%d%d", star, index);
    }

    /**
     * Set coordinates of nodes.
     */
    @Override
    protected void flushCoords() {
        sendNodeAttributeChanged(sourceId, "101", "x", null, 0);
        sendNodeAttributeChanged(sourceId, "101", "y", null, 0);

        double r1 = 8.0;

        for (int i = 0; i < currentStarIndex; i++) {
            double a = i * 2 * Math.PI / currentStarIndex;
            double rx = r1 * Math.cos(a);
            double ry = r1 * Math.sin(a);

            sendNodeAttributeChanged(sourceId, getNodeId(i, 0), "x", null, rx);
            sendNodeAttributeChanged(sourceId, getNodeId(i, 0), "y", null, ry);

            for (int j = 1; j < k; j++) {
                double b = a - (j - 1) * 2 * Math.PI / (k - 1);
                double r2 = 0.8 * r1 * Math.sin(Math.PI / currentStarIndex);

                sendNodeAttributeChanged(sourceId, getNodeId(i, j), "x", null,
                        rx - r2 * Math.cos(b));
                sendNodeAttributeChanged(sourceId, getNodeId(i, j), "y", null,
                        ry - r2 * Math.sin(b));
            }
        }
    }
}

