package artificialimmunesystem;

import util.User;
import java.util.ArrayList;
import java.util.Random;
import util.Distances;
import util.FileOperations;

public class NegativeSelection {

    private final ArrayList<User>[] users;
    private final ArrayList<Integer>[] classifications;

    public static final int QTD_DETECTORS = 5;
    public static final double THRESHOLD = 0.5;
    public static final int REAL_BOTS = 0, FAKE_BOTS = 1, REAL_HUMANS = 2, FAKE_HUMANS = 3;

    public NegativeSelection(ArrayList<User>[] users) {
        this.users = users;
        this.classifications = new ArrayList[4];
        for (int i = 0; i < 4; i++) {
            this.classifications[i] = new ArrayList<>();
        }
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

        classifications[REAL_BOTS].add(realBots);
        classifications[FAKE_BOTS].add(fakeBots);
        classifications[REAL_HUMANS].add(realHumans);
        classifications[FAKE_HUMANS].add(fakeHumans);
    }

    public void extractFile() {
        FileOperations.writeClassifications(classifications);
    }

    public static String getFilename() {
        return QTD_DETECTORS + "|" + THRESHOLD + FileOperations.EXTENSION;
    }
}
