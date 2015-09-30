package artificialimmunesystem;

import botsdetection.BotsDetection;
import util.User;
import java.util.ArrayList;
import java.util.Random;
import util.Classification;
import util.Distances;
import util.FileOperations;

public class NegativeSelection {

    private final ArrayList<User>[] users;
    private final ArrayList<Classification> classifications;
    private int tRealBots, tFakeBots, tRealHumans, tFakeHumans;
    private double tAccuracy, tPrecision, tRecall;

    public static final int QTD_DETECTORS = 5;
    public static final double THRESHOLD = 0.5;

    public NegativeSelection(ArrayList<User>[] users) {
        this.users = users;
        this.classifications = new ArrayList<>();
        this.tRealBots = this.tFakeBots = this.tRealHumans = this.tFakeHumans = 0;
        this.tAccuracy = this.tPrecision = this.tRecall = 0.0;
    }

    public void run() {
        ArrayList<User> detectors = generateDetectors();
        applyDetectors(detectors);
    }

    private ArrayList<User> generateDetectors() {
        ArrayList<User> detectors = new ArrayList<>();

        Random r = new Random();
        int i = 0, middle = (int) (users[User.DATABASE].size() * 0.5);
        while (i < QTD_DETECTORS) {
            int first = r.nextInt(middle);
            int last = r.nextInt(middle) + middle + 1;

            ArrayList<User> generators = new ArrayList<>(users[User.DATABASE].subList(first, last));
            for (User g : generators) {
                for (User s : users[User.SELF]) {
                    if (Distances.euclideanDistance(g.getFeatures(), s.getFeatures()) > THRESHOLD) {
                        detectors.add(g);
                        i++;
                        break;
                    }
                }
            }
        }

        return detectors;
    }

    private void applyDetectors(ArrayList<User> detectors) {
        int realBots = 0, fakeBots = 0, realHumans = 0, fakeHumans = 0;

        for (User db : users[User.DATABASE]) {
            boolean matched = false;

            for (User d : detectors) {
                if (Distances.euclideanDistance(db.getFeatures(), d.getFeatures()) <= THRESHOLD) {
                    if (db.isBot()) {
                        realBots++;
                    } else {
                        fakeBots++;
                    }
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                if (db.isBot()) {
                    fakeHumans++;
                } else {
                    realHumans++;
                }
            }
        }

        Classification c = new Classification();
        c.setRealBots(realBots);
        c.setFakeBots(fakeBots);
        c.setRealHumans(realHumans);
        c.setFakeHumans(fakeHumans);
        c.calculateStatistics();
        classifications.add(c);

        tRealBots += realBots;
        tFakeBots += fakeBots;
        tRealHumans += realHumans;
        tFakeHumans += fakeHumans;
        tAccuracy += c.getAccuracy();
        tPrecision += c.getPrecision();
        tRecall += c.getRecall();
    }

    public void extractFile() {
        FileOperations.writeClassifications(classifications);

        System.out.println("Real bots (TP) mean: "
                + (double) (tRealBots) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Fake bots (FP) mean: "
                + (double) (tFakeBots) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Real humans (TN) mean: "
                + (double) (tRealHumans) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Fake humans (FN) mean: "
                + (double) (tFakeHumans) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Accuracy (ACC) mean: "
                + (double) (tAccuracy) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Precision (PPV) mean: "
                + (double) (tPrecision) / (double) (BotsDetection.EXECUTIONS));
        System.out.println("Recall (NPV) mean: "
                + (double) (tRecall) / (double) (BotsDetection.EXECUTIONS));
    }

    public static String getFilename() {
        return QTD_DETECTORS + "|" + THRESHOLD + FileOperations.EXTENSION;
    }
}
