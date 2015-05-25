import controler.ControlerMainFrame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.ModelMainFrame;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import view.ViewMainFrame;


public class Main {

    public static void main(String[] args) throws InterruptedException {

//        accelAsc(100, 4);
        
//        System.out.println(accelAsc(3, 3));
//        List<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        System.out.println(generatePerm(a));

        try {
            String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
       
        ModelMainFrame modelMainFrame = new ModelMainFrame();
        ViewMainFrame viewMainFrame = new ViewMainFrame(modelMainFrame);
        ControlerMainFrame controlerMainFrame = new ControlerMainFrame(viewMainFrame, modelMainFrame);
    }

    private static Set<List<Integer>> accelAsc(int n, int partSize) {
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

        for (List<Integer> original : res) {
            System.out.println(generatePerm(original));
        }
        
        return res;
    }

    public static Set<List<Integer>> generatePerm(List<Integer> original) {
        if (original.isEmpty()) {
            Set<List<Integer>> result = new HashSet<>();
            result.add(new ArrayList<Integer>());
            return result;
        }
        Integer firstElement = original.remove(0);
        Set<List<Integer>> returnValue = new HashSet<>();
        Set<List<Integer>> permutations = generatePerm(original);
        for (List<Integer> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<Integer> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }

        return returnValue;
    }

}
