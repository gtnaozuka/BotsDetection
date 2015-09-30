package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Features {

    public static final ArrayList<String> features;

    static {
        features = new ArrayList<>();
        features.add("lexico");
        features.add("corpus");
        features.add("qtd_citacoes");
        features.add("qtd_links");
        features.add("qtd_hashtags");
        features.add("avg_citacoes");
        features.add("avg_links");
        features.add("avg_hashtags");
        features.add("lexico_raw");
        features.add("corpus_raw");
        features.add("avg_terms");
    }

    public static int lexico(String tweet) {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(tweet.split(" ")));
        return set.size();
    }

    public static int corpus(String tweet) {
        return tweet.split(" ").length;
    }

    public static int citacoes(String tweet) {
        return tweet.split("@(?<arroba>[A-Za-z0-9]+)").length - 1;
    }

    public static int links(String tweet) {
        return tweet.split(
                "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        ).length - 1;
    }

    public static int hashtags(String tweet) {
        return tweet.split("#(?<hashtag>[A-Za-z0-9]+)").length - 1;
    }
}
