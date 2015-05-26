package core;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.graphstream.graph.Node;

public class PatternUpdate {

    private LinkedHashMap<Integer, LinkedList<String>> patternUpdate = new LinkedHashMap<>();
    private String orderedPartitionText;
    private final Pattern regex;
    private boolean isValidPattern;

    private LinkedHashMap<Integer, LinkedList<String>> getPatternUpdate() {
        return patternUpdate;
    }

    private void setPatternUpdate(LinkedHashMap<Integer, LinkedList<String>> patternUpdate) {
        this.patternUpdate = patternUpdate;
    }

    private String getOrderedPartitionText() {
        return orderedPartitionText;
    }

    private void setOrderedPartitionText(String orderedPartitionText) {
        this.orderedPartitionText = orderedPartitionText;
    }

    public PatternUpdate(String orderedPartition, MyGraph graph) {
        this.isValidPattern = false;
        this.orderedPartitionText = new String();
        orderedPartitionText = orderedPartition;
        regex = Pattern.compile("\\(\\{\\w+(\\,\\w+)*\\}(\\,\\{\\w+(\\,\\w+)*\\})*\\)");

        Matcher m = regex.matcher(orderedPartitionText);
        isValidPattern = m.matches() && isFair(orderedPartitionText, graph);

        if (isValidPattern) {
            initPattern(orderedPartitionText, graph.getNodeCount());
        }
    }

    public PatternUpdate(String regex, String orderedPartition, MyGraph graph) {
        this.isValidPattern = false;
        this.orderedPartitionText = new String();
        this.regex = Pattern.compile(regex);

        orderedPartitionText = orderedPartition;
        Matcher m = this.regex.matcher(orderedPartitionText);

        isValidPattern = m.matches() && isFair(orderedPartition, graph);

        if (m.matches() && isFair(orderedPartition, graph)) {
            initPattern(orderedPartition, graph.getNodeCount());
        }
    }

    public boolean isValid() {
        return isValidPattern;
    }

    private boolean isFair(String orderedPartition, MyGraph graph) {
        Set<String> presentNode = new HashSet();

        orderedPartitionText = orderedPartition;
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(orderedPartition);

        while (m.find()) {
            if (!presentNode.add(m.group()) || graph.getNode(m.group()) == null) {
                return false;
            }
        }

        return presentNode.size() == graph.getNodeCount();
    }

    private void initPattern(String orderedPartition, int nbVertex) {
        Pattern p = Pattern.compile("\\{\\w+(\\,\\w+)*\\}");
        Matcher m = p.matcher(orderedPartition);
        orderedPartitionText = orderedPartition;

        for (int numIter = 0; m.find(); numIter++) {
            LinkedList<String> parallelGroup = new LinkedList<>();

            Pattern p2 = Pattern.compile("\\w+");
            Matcher m2 = p2.matcher(m.group());

            while (m2.find()) {
                parallelGroup.add(m2.group());
            }

            patternUpdate.put(numIter, parallelGroup);
        }
    }

    public LinkedList<String> getStep(int num) {
        return patternUpdate.get(num);
    }

    public Set<Entry<Integer, LinkedList<String>>> getAllStep() {
        return patternUpdate.entrySet();
    }

    public String getOrderedPartionText() {
        return orderedPartitionText;
    }

    public static String buildSequentialPattern(MyGraph graph) {
        if (graph.getNodeCount() < 1) {
            return "({})";
        }

        String orderedPattern = "(";

        for (Node node : graph.getNodeSet()) {
            orderedPattern += "{";
            orderedPattern += node.getId();
            orderedPattern += "},";
        }

        return orderedPattern.substring(0, orderedPattern.length() - 1) + ")";
    }

    public static String buildParallelPattern(MyGraph graph) {
        if (graph.getNodeCount() < 1) {
            return "({})";
        }

        String orderedPattern = "({";

        for (Node node : graph.getNodeSet()) {
            orderedPattern += (node.getId() + ",");
        }

        return orderedPattern.substring(0, orderedPattern.length() - 1) + "})";
    }
}
