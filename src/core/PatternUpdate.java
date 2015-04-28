package core;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class PatternUpdate {

    private LinkedHashMap<Integer, LinkedList<String>> patternUpdate = new LinkedHashMap<>();
    private Pattern regex;
    private String orderedPartitionText;
    private boolean isValidPattern;

    private PatternUpdate(LinkedHashMap<Integer, LinkedList<String>> patternUpdate) {
        this.orderedPartitionText = "(";

        for (Entry<Integer, LinkedList<String>> entry : patternUpdate.entrySet()) {
            this.orderedPartitionText += "{";

            for (String parallelStep : entry.getValue()) {
                this.orderedPartitionText += parallelStep + ",";
            }

            this.orderedPartitionText = this.orderedPartitionText.substring(0, this.orderedPartitionText.length() - 1);
            this.orderedPartitionText += "},";

            if (this.orderedPartitionText.substring(this.orderedPartitionText.length() - 3).equals("{},")) {
                this.orderedPartitionText = this.orderedPartitionText.substring(0, this.orderedPartitionText.length() - 3);
            }
        }

        this.orderedPartitionText = this.orderedPartitionText.substring(0, this.orderedPartitionText.length() - 1);
        this.orderedPartitionText += ")";
        isValidPattern = true;
    }

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

    public PatternUpdate(String orderedPartition, int nbVertex) {
        this.isValidPattern = false;
        this.orderedPartitionText = new String();
        regex = Pattern.compile("\\(\\{\\w+(\\,\\w+)*\\}(\\,\\{\\w+(\\,\\w+)*\\})*\\)");

        orderedPartitionText = orderedPartition;
        Matcher m = regex.matcher(orderedPartitionText);

        isValidPattern = m.matches() && isFair(orderedPartition, nbVertex);

        if (isValidPattern) {
            initPattern(orderedPartition, nbVertex);
        }
    }

    public PatternUpdate(String regex, String orderedPartition, int nbVertex) {
        this.isValidPattern = false;
        this.orderedPartitionText = new String();
        this.regex = Pattern.compile(regex);
        this.isValidPattern = false;
        this.orderedPartitionText = new String();

        orderedPartitionText = orderedPartition;
        Matcher m = this.regex.matcher(orderedPartitionText);

        isValidPattern = m.matches() && isFair(orderedPartition, nbVertex);

        if (m.matches() && isFair(orderedPartition, nbVertex)) {
            initPattern(orderedPartition, nbVertex);
        }
    }

    public boolean isValid() {
        return isValidPattern;
    }

    private boolean isFair(String orderedPartition, int nbVertex) {
        Set<Integer> presentNode = new HashSet();

        orderedPartitionText = orderedPartition;
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(orderedPartition);

        while (m.find()) {
            if (!presentNode.add(Integer.parseInt(m.group()))) {
                return false;
            }
        }

        return presentNode.size() == nbVertex;
    }

    private void initPattern(String orderedPartition, int nbVertex) {
        Pattern p = Pattern.compile("\\{\\w+(\\,\\w+)*\\}");
        Matcher m = p.matcher(orderedPartition);
        orderedPartitionText = orderedPartition;

        for (int numIter = 0; m.find(); numIter++) {
            LinkedList<String> parallelGroup = new LinkedList<>();

            Pattern p2 = Pattern.compile("\\w+");
            Matcher m2 = p2.matcher(m.group());
            System.out.println(m.group());

            while (m2.find()) {
                System.out.println(m2.group());
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

    public static String buildSequentialPattern(Graph graph) {
        String orderedPattern = "(";

        for (Node node : graph.getNodeSet()) {
            orderedPattern += "{";
            orderedPattern += node.getId();
            orderedPattern += "},";
        }

        return orderedPattern.substring(0, orderedPattern.length() - 1) + ")";
    }

    public static String buildParallelPattern(Graph graph) {
        String orderedPattern = "({";

        for (Node node : graph.getNodeSet()) {
            orderedPattern += (node.getId() + ",");
        }

        return orderedPattern.substring(0, orderedPattern.length() - 1) + "})";
    }
}
