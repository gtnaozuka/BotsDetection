package featuresextractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.joda.time.DateTime;
import util.Features;
import util.FileOperations;
import util.Post;
import util.User;

public class FeaturesExtractor {

    private final ArrayList<User>[] users;

    public FeaturesExtractor(ArrayList<User>[] users) {
        this.users = users;
    }

    public void run() {
        run(users[User.BOTS]);
        run(users[User.HUMANS]);
    }

    private void run(ArrayList<User> users) {
        for (User u : users) {
            int lexico = 0, corpus = 0, citacoes = 0, links = 0,
                    hashtags = 0, lexicoRaw = 0, corpusRaw = 0, sentenca = 0,
                    sentencaRaw = 0;

            long intervalPosts = 0L;
            DateTime lastTime = null;

            HashSet<Integer> days = new HashSet<>();
            HashSet<Integer> weeks = new HashSet<>();

            int[] hours = new int[24];
            int[] weekdays = new int[7];
            int sumHours = 0, sumWeekday = 0;

            int sumRt = 0;

            for (Post p : u.getProcessedPosts()) {
                lexico += Features.lexico(p.getTweet());
                corpus += Features.corpus(p.getTweet());
                citacoes += Features.citacoes(p.getTweet());
                links += Features.links(p.getTweet());
                hashtags += Features.hashtags(p.getTweet());
                sentenca += Features.sentenca(p.getTweet());

                if (lastTime != null) {
                    intervalPosts += (long) Math.abs(lastTime.getMillis() - p.getTimestamp().getMillis());
                }

                days.add(p.getTimestamp().getDayOfYear());
                weeks.add(p.getTimestamp().getWeekOfWeekyear());

                hours[p.getTimestamp().getHourOfDay()]++;
                weekdays[p.getTimestamp().getDayOfWeek() - 1]++;
                sumHours += p.getTimestamp().getHourOfDay();
                sumWeekday += p.getTimestamp().getDayOfWeek();

                lastTime = p.getTimestamp();

                sumRt += p.getRetweetCount();
            }

            double meanHours = (double) sumHours / u.getProcessedPosts().size();
            double sumVarHour = 0.0;
            for (int hour : hours) {
                sumVarHour += Math.pow(hour - meanHours, 2.0);
            }

            double meanWeekday = (double) sumWeekday / u.getProcessedPosts().size();
            double sumVarWeekday = 0.0;
            for (int weekday : weekdays) {
                sumVarWeekday += Math.pow(weekday - meanWeekday, 2.0);
            }

            double meanRt = (double) sumRt / u.getProcessedPosts().size();
            double sumVarRt = 0.0;
            for (Post p : u.getPosts()) {
                lexicoRaw += Features.lexico(p.getTweet());
                corpusRaw += Features.corpus(p.getTweet());
                sentencaRaw += Features.sentenca(p.getTweet());

                sumVarRt += Math.pow(p.getRetweetCount() - meanRt, 2.0);
            }

            LinkedHashMap<String, Double> features = new LinkedHashMap<>();
            features.put(Features.features.get(0), (double) lexico);
            features.put(Features.features.get(1), (double) corpus);
            features.put(Features.features.get(2), (double) citacoes);
            features.put(Features.features.get(3), (double) links);
            features.put(Features.features.get(4), (double) hashtags);
            features.put(Features.features.get(5), (double) citacoes
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(6), (double) links
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(7), (double) hashtags
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(8), (double) lexicoRaw);
            features.put(Features.features.get(9), (double) corpusRaw);
            features.put(Features.features.get(10), (double) corpus
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(11), (double) sentenca
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(12), (double) sentencaRaw
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(13), (double) sumVarRt
                    / u.getProcessedPosts().size());
            features.put(Features.features.get(14), (double) u.getProcessedPosts().size()
                    / days.size());
            features.put(Features.features.get(15), (double) u.getProcessedPosts().size()
                    / weeks.size());
            features.put(Features.features.get(16), (double) sumVarHour
                    / hours.length);
            features.put(Features.features.get(17), (double) sumVarWeekday
                    / weekdays.length);
            features.put(Features.features.get(18), (double) intervalPosts
                    / u.getProcessedPosts().size());
            u.setFeatures(features);
        }
    }

    public void normalize() {
        normalize(users[User.BOTS]);
        normalize(users[User.HUMANS]);
    }

    private void normalize(ArrayList<User> users) {
        LinkedHashMap<String, Double> maxValues = calculateMaxValues(users);
        for (User u : users) {
            LinkedHashMap<String, Double> normalizedFeatures = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : u.getFeatures().entrySet()) {
                normalizedFeatures.put(entry.getKey(), entry.getValue()
                        / maxValues.get(entry.getKey()));
            }
            u.setFeatures(normalizedFeatures);
        }
    }

    private LinkedHashMap<String, Double> calculateMaxValues(ArrayList<User> users) {
        LinkedHashMap<String, Double> maxValues = new LinkedHashMap<>();
        for (String feature : Features.features) {
            maxValues.put(feature, -1.0);
        }

        for (User u : users) {
            for (Map.Entry<String, Double> entry : u.getFeatures().entrySet()) {
                maxValues.put(entry.getKey(), Math.max(maxValues.get(entry.getKey()),
                        entry.getValue()));
            }
        }

        return maxValues;
    }

    public void extractFiles() {
        ArrayList<User> concat = new ArrayList<>(users[User.BOTS]);
        concat.addAll(users[User.HUMANS]);
        FileOperations.writeFeatures(FileOperations.FEATURES_PATH, Features.features, concat);
    }
}
