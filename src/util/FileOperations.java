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
    
    private static String[] featuresTitle() {
        String[] strings = new String[Features.TOTAL + 1];
        
        strings[0] = Features.LEXICO;
        strings[1] = Features.CORPUS;
        strings[2] = Features.QTD_CITACOES;
        strings[3] = Features.QTD_LINKS;
        strings[4] = Features.QTD_HASHTAGS;
        strings[5] = Features.AVG_CITACOES;
        strings[6] = Features.AVG_LINKS;
        strings[7] = Features.AVG_HASHTAGS;
        strings[8] = Features.LEXICO_RAW;
        strings[9] = Features.CORPUS_RAW;
        strings[10] = Features.AVG_TERMS;
        
        strings[strings.length - 1] = "class";
        
        return strings;
    }
    
    private static String[] featuresToStringArray(User u) {
        String[] strings = new String[Features.TOTAL + 1];
        
        int i = 0;
        for (Map.Entry<String, Double> entry : u.getFeatures().entrySet()) {
            strings[i] = entry.getValue().toString();
            i++;
        }
        
        if (u.isBot())
            strings[strings.length - 1] = "bot";
        else
            strings[strings.length - 1] = "human";
        
        return strings;
    }
    
    private static LinkedHashMap<String, Double> stringArrayToFeatures(String[] strings) {
        LinkedHashMap<String, Double> features = new LinkedHashMap<>();
        
        features.put(Features.LEXICO, Double.valueOf(strings[0]));
        features.put(Features.CORPUS, Double.valueOf(strings[1]));
        features.put(Features.QTD_CITACOES, Double.valueOf(strings[2]));
        features.put(Features.QTD_LINKS, Double.valueOf(strings[3]));
        features.put(Features.QTD_HASHTAGS, Double.valueOf(strings[4]));
        features.put(Features.AVG_CITACOES, Double.valueOf(strings[5]));
        features.put(Features.AVG_LINKS, Double.valueOf(strings[6]));
        features.put(Features.AVG_HASHTAGS, Double.valueOf(strings[7]));
        features.put(Features.LEXICO_RAW, Double.valueOf(strings[8]));
        features.put(Features.CORPUS_RAW, Double.valueOf(strings[9]));
        features.put(Features.AVG_TERMS, Double.valueOf(strings[10]));
        
        return features;
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
    
    public static ArrayList<User> readFeatures() {
        ArrayList<User> users = new ArrayList<>();
        
        try {
            String[] nextLine;
            CSVReader reader = new CSVReader(new FileReader(FEATURES_PATH + FEATURES_FILENAME));
            nextLine = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                User u = new User();
                u.setFeatures(stringArrayToFeatures(nextLine));
                if (nextLine[nextLine.length - 1].equals("bot"))
                    u.setBot(true);
                else
                    u.setBot(false);
                
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

    public static void writeFeatures(ArrayList<User> users) {
        File folder = new File(FEATURES_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(FEATURES_PATH + FEATURES_FILENAME), ',')) {
            writer.writeNext(featuresTitle());
            for (User u : users) {
                writer.writeNext(featuresToStringArray(u));
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeClassifications(ArrayList<Integer>[] classifications) {
        File folder = new File(CLASSIFICATIONS_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(CLASSIFICATIONS_PATH + NegativeSelection.getFilename()), ',')) {
            for (int i = 0; i < classifications[NegativeSelection.REAL_BOTS].size(); i++) {
                String[] strings = new String[4];
                
                strings[0] = classifications[NegativeSelection.REAL_BOTS].get(i).toString();
                strings[1] = classifications[NegativeSelection.FAKE_BOTS].get(i).toString();
                strings[2] = classifications[NegativeSelection.REAL_HUMANS].get(i).toString();
                strings[3] = classifications[NegativeSelection.FAKE_HUMANS].get(i).toString();

                writer.writeNext(strings);
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
