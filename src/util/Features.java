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
        features.add("avg_sentenca");
        features.add("avg_sentenca_raw");
        features.add("var_rt_post");
        features.add("avg_posts_day");
        features.add("avg_posts_week");
        features.add("var_hour");
        features.add("var_weekday");
        features.add("avg_interval_posts");
    }

    private static final String CITACAO_REGEX = "@(?<arroba>[A-Za-z0-9]+)";
    private static final String HASHTAG_REGEX = "#(?<hashtag>[A-Za-z0-9]+)";
    private static final String LINK_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static int lexico(String tweet) {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(tweet.split(" ")));
        return set.size();
    }

    public static int corpus(String tweet) {
        return tweet.split(" ").length;
    }

    public static int citacoes(String tweet) {
        return tweet.split(CITACAO_REGEX).length - 1;
    }

    public static int links(String tweet) {
        return tweet.split(LINK_REGEX).length - 1;
    }

    public static int hashtags(String tweet) {
        return tweet.split(HASHTAG_REGEX).length - 1;
    }

    public static int sentenca(String tweet) {
        String newTweet = tweet
                .replaceAll(LINK_REGEX, "")
                .replaceAll(CITACAO_REGEX, "")
                .replaceAll(HASHTAG_REGEX, "");
        return newTweet.split("\\p{Punct}+").length;
    }
}
