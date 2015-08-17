package util;

import java.util.ArrayList;

public class User {

    private String name;
    private ArrayList<String> posts;
    private ArrayList<Features> features;
    private boolean bot;

    public static final int DB_BOTS = 0, DB_HUMANS = 1, PROCESSED_BOTS = 2,
            PROCESSED_HUMANS = 3;
    public static final int SELF = 0, DATABASE = 1;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }

    public void setFeatures(ArrayList<Features> features) {
        this.features = features;
    }

    public ArrayList<Features> getFeatures() {
        return features;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
