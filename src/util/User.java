package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class User {

    private String name;
    private ArrayList<Post> posts, processedPosts;
    private LinkedHashMap<String, Double> features;
    private boolean bot;

    public static final int BOTS = 0, HUMANS = 1;
    public static final int DATABASE = 0, SELF = 1;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setFeatures(LinkedHashMap<String, Double> features) {
        this.features = features;
    }

    public LinkedHashMap<String, Double> getFeatures() {
        return features;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public ArrayList<Post> getProcessedPosts() {
        return processedPosts;
    }

    public void setProcessedPosts(ArrayList<Post> processedPosts) {
        this.processedPosts = processedPosts;
    }
}
