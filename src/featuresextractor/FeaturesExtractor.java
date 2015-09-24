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
            features.put(Features.LEXICO, (double) lexico);
            features.put(Features.CORPUS, (double) corpus);
            features.put(Features.QTD_CITACOES, (double) citacoes);
            features.put(Features.QTD_LINKS, (double) links);
            features.put(Features.QTD_HASHTAGS, (double) hashtags);
            features.put(Features.AVG_CITACOES, (double) citacoes
                    / u.getProcessedPosts().size());
            features.put(Features.AVG_LINKS, (double) links
                    / u.getProcessedPosts().size());
            features.put(Features.AVG_HASHTAGS, (double) hashtags
                    / u.getProcessedPosts().size());
            features.put(Features.LEXICO_RAW, (double) lexicoRaw);
            features.put(Features.CORPUS_RAW, (double) corpusRaw);
            features.put(Features.AVG_TERMS, (double) corpus
                    / u.getProcessedPosts().size());
            u.setFeatures(features);
        }
    }

    public void extractFiles() {
        ArrayList<User> concat = new ArrayList<>(users[User.BOTS]);
        concat.addAll(users[User.HUMANS]);
        FileOperations.writeFeatures(concat);
    }
}
