package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KChips {

    private final Set<Set<List<Integer>>> returnValue;
    int nbNode;
    int amountOfChips;
    
    public KChips(int nbNode, int amountOfChips) {
        returnValue = new HashSet<>();
        Set<List<Integer>> res = accelAsc(nbNode, amountOfChips);
        for (List<Integer> original : res) {
            returnValue.add(generatePerm(original));
        }
    }   
    
    public Set<Set<List<Integer>>> getAllDistribution() {
        return returnValue;
    }
    
    private Set<List<Integer>> accelAsc(int n, int partSize) {
        Set<List<Integer>> res = new HashSet<>();
        List<Integer> a = new ArrayList<>(n + 1);

        if (partSize > n || partSize < 1 || n < 1) {
            return new HashSet<>();
        }

        for (int i = 0; i <= n + 1; i++) {
            a.add(0);
        }

        int k = 1;
        int y = n - 1;

        while (k > 0) {
            int x = a.get(k - 1) + 1;

            k -= 1;
            while (2 * x <= y) {
                a.set(k, x);
                y -= x;
                k += 1;
            }

            int l = k + 1;
            while (x <= y) {
                a.set(k, x);
                a.set(l, y);
                if (k + 2 <= partSize) {
                    List<Integer> arrayList = new ArrayList<>(a.subList(0, k + 2));
                    for (int i = k + 2; i < partSize; i++) {
                        arrayList.add(0);
                    }
                    res.add(arrayList);
                }
                x += 1;
                y -= 1;
            }

            a.set(k, x + y);
            y = x + y - 1;
            if (k + 1 <= partSize) {
                List<Integer> arrayList = new ArrayList<>(a.subList(0, k + 1));
                for (int i = k + 1; i < partSize; i++) {
                    arrayList.add(0);
                }
                res.add(arrayList);
            }
        }
        
        return res;
    }

    private Set<List<Integer>> generatePerm(List<Integer> original) {
        if (original.isEmpty()) {
            Set<List<Integer>> result = new HashSet<>();
            result.add(new ArrayList<Integer>());
            return result;
        }
        Integer firstElement = original.remove(0);
        Set<List<Integer>> s = new HashSet<>();
        Set<List<Integer>> permutations = generatePerm(original);
        for (List<Integer> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<Integer> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                s.add(temp);
            }
        }

        return s;
    }

}

