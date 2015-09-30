package util;

import java.util.ArrayList;

public class Classification {

    private int realBots, fakeBots, realHumans, fakeHumans;
    private double accuracy, precision, recall;

    public static final ArrayList<String> classifications;

    static {
        classifications = new ArrayList<>();
        classifications.add("real_bots");
        classifications.add("fake_bots");
        classifications.add("real_humans");
        classifications.add("fake_humans");
        classifications.add("accuracy");
        classifications.add("precision");
        classifications.add("recall");
    }

    public int getRealBots() {
        return realBots;
    }

    public void setRealBots(int realBots) {
        this.realBots = realBots;
    }

    public int getFakeBots() {
        return fakeBots;
    }

    public void setFakeBots(int fakeBots) {
        this.fakeBots = fakeBots;
    }

    public int getRealHumans() {
        return realHumans;
    }

    public void setRealHumans(int realHumans) {
        this.realHumans = realHumans;
    }

    public int getFakeHumans() {
        return fakeHumans;
    }

    public void setFakeHumans(int fakeHumans) {
        this.fakeHumans = fakeHumans;
    }

    public void calculateStatistics() {
        accuracy = calculateAccuracy();
        precision = calculatePrecision();
        recall = calculateRecall();
    }

    private double calculateAccuracy() {
        return ((double) (realBots + realHumans))
                / (double) (realBots + fakeBots + realHumans + fakeHumans);
    }

    private double calculatePrecision() {
        return ((double) realBots) / ((double) (realBots + fakeBots));
    }

    private double calculateRecall() {
        return ((double) realHumans) / ((double) (realHumans + fakeHumans));
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }
}
