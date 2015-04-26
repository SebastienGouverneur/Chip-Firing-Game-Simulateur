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

    public PatternUpdate() {
        this.orderedPartitionText = new String();
        regex = Pattern.compile("\\(\\{\\w+(\\,\\w+)*\\}(\\,\\{\\w+(\\,\\w+)*\\})*\\)");
    }

    public PatternUpdate(Pattern regex) {
        this.orderedPartitionText = new String();
        this.regex = regex;
    }

    private boolean isValid(String orderedPartition) {
        orderedPartitionText = orderedPartition;
        Matcher m = regex.matcher(orderedPartitionText);

        if (m.matches()) {
            System.out.println("Pattern reconnu");
            return true;
        } else {
            System.out.println("Pattern non reconnu");
            return false;
        }
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

    private boolean isFair(String orderedPartition, int nbVertex, String unitPart) {
        Set<Integer> presentNode = new HashSet();

        orderedPartitionText = orderedPartition;
        Pattern p = Pattern.compile(unitPart + "+");
        Matcher m = p.matcher(orderedPartition);

        while (m.find()) {
            if (!presentNode.add(Integer.parseInt(m.group()))) {
                return false;
            }
        }

        return presentNode.size() == nbVertex;
    }

    public boolean initPattern(String orderedPartition, int nbVertex) {

        if (!(isValid(orderedPartition) && isFair(orderedPartition, nbVertex))) {
            return false;
//            throw new Exception("MalformedPatternException", new Throwable("MalformedPatternException"));
        }

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
        
        return true;
    }

    public void initPattern(String orderedPartition, int nbVertex, String unitPart) throws Exception {

        if (!(isValid(orderedPartition) && isFair(orderedPartition, nbVertex, unitPart))) {
            throw new Exception("MalformedPatternException", new Throwable("MalformedPatternException"));
        }

        Pattern p = Pattern.compile("\\{" + unitPart + "+(\\," + unitPart + "+)*\\}");
        Matcher m = p.matcher(orderedPartition);

        for (int numIter = 0; m.find(); numIter++) {
            LinkedList<String> parallelGroup = new LinkedList<>();

            Pattern p2 = Pattern.compile(unitPart + "+");
            Matcher m2 = p.matcher(m.group());

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
}
