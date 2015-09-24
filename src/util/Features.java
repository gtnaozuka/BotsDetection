package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Features {

    public static final String LEXICO = "lexico", CORPUS = "corpus", 
            QTD_CITACOES = "qtd_citacoes", QTD_LINKS = "qtd_links", 
            QTD_HASHTAGS = "qtd_hashtags", AVG_CITACOES = "avg_citacoes", 
            AVG_LINKS = "avg_links", AVG_HASHTAGS = "avg_hashtags", 
            LEXICO_RAW = "lexico_raw", CORPUS_RAW = "corpus_raw", 
            AVG_TERMS = "avg_terms";
    public static final int TOTAL = 11;
    
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
