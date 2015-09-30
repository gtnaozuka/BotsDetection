package util;

import artificialimmunesystem.NegativeSelection;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOperations {

    public static final String EXTENSION = ".csv";
    public static final String DB_BOTS_PATH = "./Base de Dados/B/";
    public static final String DB_HUMANS_PATH = "./Base de Dados/H/";
    public static final String PROCESSED_BOTS_PATH = "./Base de Dados Processada/B/";
    public static final String PROCESSED_HUMANS_PATH = "./Base de Dados Processada/H/";
    public static final String FEATURES_PATH = "./Caracteristicas/";
    public static final String SELECTED_FEATURES_PATH = "./Caracteristicas Selecionadas/";
    public static final String FEATURES_FILENAME = "features.csv";
    public static final String CLASSIFICATIONS_PATH = "./Classificacoes/";

    private static Post stringArrayToPost(String[] strings) {
        Post post = new Post();

        post.setTimestamp(strings[1]);
        post.setTweet(strings[2]);
        post.setRetweetCount(Integer.parseInt(strings[3]));
        post.setLon(Double.parseDouble(strings[4]));
        post.setLat(Double.parseDouble(strings[5]));
        post.setCountry(strings[6]);
        post.setName(strings[7]);
        post.setAddress(strings[8]);
        post.setType(strings[9]);
        //post.setPlaceURL(strings[10]);

        return post;
    }

    private static String[] postToStringArray(String username, Post post) {
        String[] strings = new String[11];

        strings[0] = username;
        strings[1] = post.getTimestamp();
        strings[2] = post.getTweet();
        strings[3] = String.valueOf(post.getRetweetCount());
        strings[4] = String.valueOf(post.getLon());
        strings[5] = String.valueOf(post.getLat());
        strings[6] = post.getCountry();
        strings[7] = post.getName();
        strings[8] = post.getAddress();
        strings[9] = post.getType();
        //strings[10] = post.getPlaceURL();

        return strings;
    }

    private static ArrayList<Post> readPosts(String path) {
        ArrayList<Post> posts = new ArrayList<>();

        try {
            String[] nextLine;
            CSVReader reader = new CSVReader(new FileReader(path));
            while ((nextLine = reader.readNext()) != null) {
                posts.add(stringArrayToPost(nextLine));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

    private static String[] featuresHeader(ArrayList<String> features) {
        String[] strings = new String[features.size() + 1];

        for (int i = 0; i < features.size(); i++) {
            strings[i] = features.get(i);
        }
        strings[strings.length - 1] = "class";

        return strings;
    }

    private static String[] featuresToStringArray(User u) {
        String[] strings = new String[u.getFeatures().size() + 1];

        int i = 0;
        for (Map.Entry<String, Double> entry : u.getFeatures().entrySet()) {
            strings[i] = entry.getValue().toString();
            i++;
        }

        if (u.isBot()) {
            strings[strings.length - 1] = "bot";
        } else {
            strings[strings.length - 1] = "human";
        }

        return strings;
    }

    private static LinkedHashMap<String, Double> stringArrayToFeatures(String[] header, String[] strings) {
        LinkedHashMap<String, Double> features = new LinkedHashMap<>();

        for (int i = 0; i < header.length - 1; i++) {
            features.put(header[i], Double.valueOf(strings[i]));
        }

        return features;
    }

    private static String[] classificationsHeader() {
        String[] strings = new String[Classification.classifications.size()];

        for (int i = 0; i < Classification.classifications.size(); i++) {
            strings[i] = Classification.classifications.get(i);
        }

        return strings;
    }

    private static String[] classificationsToStringArray(Classification c) {
        String[] strings = new String[Classification.classifications.size()];

        strings[0] = String.valueOf(c.getRealBots());
        strings[1] = String.valueOf(c.getFakeBots());
        strings[2] = String.valueOf(c.getRealHumans());
        strings[3] = String.valueOf(c.getFakeHumans());
        strings[4] = String.valueOf(c.getAccuracy());
        strings[5] = String.valueOf(c.getPrecision());
        strings[6] = String.valueOf(c.getRecall());

        return strings;
    }

    public static ArrayList<User> readUsers(String path, boolean isBot) {
        ArrayList<User> users = new ArrayList<>();

        File folder = new File(path);
        for (String filename : folder.list()) {
            User u = new User();
            u.setName(filename);
            u.setPosts(readPosts(path + filename));
            u.setBot(isBot);

            users.add(u);
        }

        return users;
    }

    public static void readProcessedUsers(ArrayList<User> users, String path) {
        for (User u : users) {
            u.setProcessedPosts(readPosts(path + u.getName()));
        }
    }

    public static ArrayList<User> readFeatures(String path) {
        ArrayList<User> users = new ArrayList<>();

        try {
            String[] header, nextLine;
            CSVReader reader = new CSVReader(new FileReader(path + FEATURES_FILENAME));
            header = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                User u = new User();
                u.setFeatures(stringArrayToFeatures(header, nextLine));
                if (nextLine[header.length - 1].equals("bot")) {
                    u.setBot(true);
                } else {
                    u.setBot(false);
                }

                users.add(u);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public static void writeUsers(String path, ArrayList<User> users) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (User u : users) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(path + u.getName()), ',')) {
                for (Post p : u.getProcessedPosts()) {
                    writer.writeNext(postToStringArray(u.getName(), p));
                }
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void writeFeatures(String path, ArrayList<String> features, ArrayList<User> users) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(path + FEATURES_FILENAME), ',')) {
            writer.writeNext(featuresHeader(features));
            for (User u : users) {
                writer.writeNext(featuresToStringArray(u));
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeClassifications(ArrayList<Classification> classifications) {
        File folder = new File(CLASSIFICATIONS_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(CLASSIFICATIONS_PATH + NegativeSelection.getFilename()), ',')) {
            writer.writeNext(classificationsHeader());
            for (Classification c : classifications) {
                writer.writeNext(classificationsToStringArray(c));
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
