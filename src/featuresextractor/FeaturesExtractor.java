package featuresextractor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
                    hashtags = 0, lexicoRaw = 0, corpusRaw = 0;

            for (Post p : u.getProcessedPosts()) {
                lexico += Features.lexico(p.getTweet());
                corpus += Features.corpus(p.getTweet());
                citacoes += Features.citacoes(p.getTweet());
                links += Features.links(p.getTweet());
                hashtags += Features.hashtags(p.getTweet());
            }

            for (Post p : u.getPosts()) {
                lexicoRaw += Features.lexico(p.getTweet());
                corpusRaw += Features.corpus(p.getTweet());
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
            u.setFeatures(features);
        }
    }

    public void extractFiles() {
        ArrayList<User> concat = new ArrayList<>(users[User.BOTS]);
        concat.addAll(users[User.HUMANS]);
        FileOperations.writeFeatures(FileOperations.FEATURES_PATH, Features.features, concat);
    }
}
