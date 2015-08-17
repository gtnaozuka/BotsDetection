package featuresextractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import util.Features;
import util.FileOperations;
import util.User;

public class FeaturesExtractor {

    private final ArrayList<User>[] users;

    public FeaturesExtractor(ArrayList<User>[] users) {
        this.users = users;
    }

    public void run() {
        //run(users[User.DB_BOTS]);
        //run(users[User.DB_HUMANS]);
        run(users[User.PROCESSED_BOTS]);
        run(users[User.PROCESSED_HUMANS]);
    }

    private void run(ArrayList<User> users) {
        for (User u : users) {
            int lexico = 0, corpus = 0, citacoes = 0, links = 0,
                    hashtags = 0;
            for (String p : u.getPosts()) {
                lexico += lexico(p);
                corpus += corpus(p);
                citacoes += citacoes(p);
                links += links(p);
                hashtags += hashtags(p);
            }

            ArrayList<Features> features = new ArrayList<>();
            features.add(new Features(Features.LEXICO, lexico));
            features.add(new Features(Features.CORPUS, corpus));
            features.add(new Features(Features.QTD_CITACOES, citacoes));
            features.add(new Features(Features.QTD_LINKS, links));
            features.add(new Features(Features.QTD_HASHTAGS, hashtags));
            features.add(new Features(Features.AVG_CITACOES, (double) citacoes
                    / u.getPosts().size()));
            features.add(new Features(Features.AVG_LINKS, (double) links
                    / u.getPosts().size()));
            features.add(new Features(Features.AVG_HASHTAGS, (double) hashtags
                    / u.getPosts().size()));
            u.setFeatures(features);
        }
    }

    public void extractFiles() {
        FileOperations.writeFeaturesByUser(FileOperations.FEATURES_BOTS_PATH,
                users[User.PROCESSED_BOTS]);
        FileOperations.writeFeaturesByUser(FileOperations.FEATURES_HUMANS_PATH,
                users[User.PROCESSED_HUMANS]);

        ArrayList<User> concat = new ArrayList<>(users[User.PROCESSED_BOTS]);
        concat.addAll(users[User.PROCESSED_HUMANS]);
        FileOperations.writeUsersByFeature(concat);
    }

    private int lexico(String texto) {
        int qtdLexico = 0;
        Map<String, Integer> mapaFreq = new HashMap<>();
        for (String palavra : texto.split("\\s+")) {
            if (!mapaFreq.containsKey(palavra)) {
                mapaFreq.put(palavra, 1);
            } else {
                mapaFreq.put(palavra, 1 + mapaFreq.get(palavra));
            }
        }
        for (Map.Entry<String, Integer> entrada : mapaFreq.entrySet()) {
            if (entrada.getValue() == 1) {
                qtdLexico++;
            }
        }
        return qtdLexico;
    }

    private int corpus(String texto) {
        String[] pedacos = texto.split(" ");
        return pedacos.length;
    }

    private int citacoes(String texto) {
        int qtdCitacoes = 0, cont = 0;
        while (cont < texto.length()) {
            if (texto.charAt(cont) == '@') {
                qtdCitacoes++;
            }
            cont++;
        }
        return qtdCitacoes;
    }

    private int links(String texto) {
        int qtdLinks = 0, cont = 0;
        String procurar = "http";
        String[] pedacos = texto.split(" ");
        while (cont < pedacos.length) {
            if ((pedacos[cont].toLowerCase().contains(procurar.toLowerCase()))) {
                qtdLinks++;
            }
            cont++;
        }
        return qtdLinks;
    }

    private int hashtags(String texto) {
        int qtdHashTags = 0, cont = 0;
        while (cont < texto.length()) {
            if (texto.charAt(cont) == '#') {
                qtdHashTags++;
            }
            cont++;
        }
        return qtdHashTags;
    }
}
