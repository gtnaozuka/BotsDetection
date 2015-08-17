package util;

import java.util.ArrayList;

public class Distances {

    @SuppressWarnings("empty-statement")
    public static double euclideanDistance(ArrayList<Features> a, ArrayList<Features> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); sum += Math.pow(a.get(i).getValue() - b.get(i).getValue(), 2), i++);
        return Math.sqrt(sum / a.size());
    }
}
