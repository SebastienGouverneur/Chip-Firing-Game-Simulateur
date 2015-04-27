package core;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUpdate {

    private final LinkedHashMap<Integer, LinkedList<String>> patternUpdate = new LinkedHashMap<>();
    private final Pattern regex;
    private String orderedPartitionText;
    private boolean isValidPattern;

    public PatternUpdate(String orderedPartition, int nbVertex) {
        this.isValidPattern = false;
        this.orderedPartitionText = new String();
        regex = Pattern.compile("\\(\\{\\w+(\\,\\w+)*\\}(\\,\\{\\w+(\\,\\w+)*\\})*\\)");

        orderedPartitionText = orderedPartition;
        Matcher m = regex.matcher(orderedPartitionText);

        isValidPattern = m.matches() && isFair(orderedPartition, nbVertex) ;
        
        if (isValidPattern) { initPattern(orderedPartition, nbVertex); }
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
        
        if (m.matches() && isFair(orderedPartition, nbVertex)) { initPattern(orderedPartition, nbVertex); }
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
}
