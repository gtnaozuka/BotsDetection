package util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Distances {

    @SuppressWarnings("empty-statement")
    public static double euclideanDistance(LinkedHashMap<String, Double> a, LinkedHashMap<String, Double> b) {
        double sum = 0.0;

        Iterator itA = a.entrySet().iterator();
        Iterator itB = b.entrySet().iterator();
        while (itA.hasNext()) {
            Double valueA = ((Map.Entry<String, Double>) itA.next()).getValue();
            Double valueB = ((Map.Entry<String, Double>) itB.next()).getValue();
            sum += Math.pow(valueA - valueB, 2);
        }
        
        return Math.sqrt(sum / a.size());
    }
}
