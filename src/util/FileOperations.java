package util;

import artificialimmunesystem.NegativeSelection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOperations {

    public static final String EXTENSION = ".csv";
    public static final String DB_BOTS_PATH = "./Base de Dados/BF/";
    public static final String DB_HUMANS_PATH = "./Base de Dados/H/";
    public static final String PROCESSED_BOTS_PATH = "./Base de Dados Processada/BF/";
    public static final String PROCESSED_HUMANS_PATH = "./Base de Dados Processada/H/";
    public static final String FEATURES_PATH = "./Caracteristicas/";
    public static final String FEATURES_BOTS_PATH = "./Caracteristicas/BF/";
    public static final String FEATURES_HUMANS_PATH = "./Caracteristicas/H/";
    public static final String CLASSIFICATIONS_PATH = "./Classificacoes/";

    public static ArrayList<User> readPosts(String path) {
        ArrayList<User> users = new ArrayList<>();

        File folder = new File(path);
        for (String filename : folder.list()) {
            ArrayList<String> posts = new ArrayList<>();
            try {
                for (String line : Files.readAllLines(Paths.get(path + filename), StandardCharsets.UTF_8)) {
                    posts.add(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }

            User u = new User();
            u.setName(filename);
            u.setPosts(posts);

            users.add(u);
        }

        return users;
    }

    public static void writePosts(String path, ArrayList<User> users) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (User u : users) {
            try (FileWriter writer = new FileWriter(path + u.getName())) {
                for (String p : u.getPosts()) {
                    writer.append(p + "\n");
                }
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void writeFeaturesByUser(String path, ArrayList<User> users) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (User u : users) {
            try (FileWriter writer = new FileWriter(path + u.getName())) {
                for (Features f : u.getFeatures()) {
                    writer.append(f.getValue() + "\n");
                }
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void writeUsersByFeature(ArrayList<User> users) {
        File folder = new File(FEATURES_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        ArrayList<Double>[] features = new ArrayList[Features.TOTAL];
        for (int i = 0; i < Features.TOTAL; i++) {
            features[i] = new ArrayList<>();
        }

        for (User u : users) {
            int i = 0;
            for (Features f : u.getFeatures()) {
                features[i].add(f.getValue());
                i++;
            }
        }

        for (int i = 0; i < Features.TOTAL; i++) {
            try (FileWriter writer = new FileWriter(FEATURES_PATH + Features.getFilename(i))) {
                for (Double value : features[i]) {
                    writer.append(value + "\n");
                }
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<User> readFeatures(String path, boolean bot) {
        ArrayList<User> users = new ArrayList<>();

        File folder = new File(path);
        for (String filename : folder.list()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path + filename), StandardCharsets.UTF_8);

                ArrayList<Features> features = new ArrayList<>();
                features.add(new Features(Features.LEXICO, Double.parseDouble(lines.get(Features.LEXICO))));
                features.add(new Features(Features.CORPUS, Double.parseDouble(lines.get(Features.CORPUS))));
                features.add(new Features(Features.QTD_CITACOES, Double.parseDouble(lines.get(Features.QTD_CITACOES))));
                features.add(new Features(Features.QTD_LINKS, Double.parseDouble(lines.get(Features.QTD_LINKS))));
                features.add(new Features(Features.QTD_HASHTAGS, Double.parseDouble(lines.get(Features.QTD_HASHTAGS))));
                features.add(new Features(Features.AVG_CITACOES, Double.parseDouble(lines.get(Features.AVG_CITACOES))));
                features.add(new Features(Features.AVG_LINKS, Double.parseDouble(lines.get(Features.AVG_LINKS))));
                features.add(new Features(Features.AVG_HASHTAGS, Double.parseDouble(lines.get(Features.AVG_HASHTAGS))));

                User u = new User();
                u.setName(filename);
                u.setFeatures(features);
                u.setBot(bot);
                users.add(u);
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return users;
    }

    public static void writeClassifications(ArrayList<Integer>[] classifications) {
        File folder = new File(CLASSIFICATIONS_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (FileWriter writer = new FileWriter(CLASSIFICATIONS_PATH + NegativeSelection.getFilename())) {
            for (int i = 0; i < classifications[NegativeSelection.REAL_BOTS].size(); i++) {
                int realBots = classifications[NegativeSelection.REAL_BOTS].get(i);
                int fakeBots = classifications[NegativeSelection.FAKE_BOTS].get(i);
                int realHumans = classifications[NegativeSelection.REAL_HUMANS].get(i);
                int fakeHumans = classifications[NegativeSelection.FAKE_HUMANS].get(i);

                writer.append(realBots + "\t" + fakeBots + "\t" + realHumans + "\t" + fakeHumans + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
