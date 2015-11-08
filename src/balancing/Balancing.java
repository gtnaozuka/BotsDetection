package balancing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import util.FileOperations;
import util.User;

public class Balancing {

    private ArrayList<User>[] users;

    public Balancing(ArrayList<User>[] users) {
        this.users = users;
    }

    public void run() {
        int botsSize = users[User.BOTS].size();
        int humansSize = users[User.HUMANS].size();
        if (botsSize == humansSize) {
            return;
        }

        Random r = new Random();
        HashSet<Integer> positions = new HashSet<>();
        while (positions.size() != Math.min(botsSize, humansSize)) {
            positions.add(r.nextInt(Math.max(botsSize, humansSize)));
        }

        ArrayList<User>[] newUsers = new ArrayList[2];
        int type;
        if (botsSize < humansSize) {
            newUsers[User.BOTS] = new ArrayList<>(users[User.BOTS]);
            newUsers[User.HUMANS] = new ArrayList<>();
            type = User.HUMANS;
        } else {
            newUsers[User.BOTS] = new ArrayList<>();
            newUsers[User.HUMANS] = new ArrayList<>(users[User.HUMANS]);
            type = User.BOTS;
        }

        for (Integer p : positions) {
            newUsers[type].add(users[type].get(p));
        }

        users = newUsers;
    }

    public void extractFiles() {
        FileOperations.writeBalancedUsers(FileOperations.BALANCED_DB_BOTS_PATH, users[User.BOTS]);
        FileOperations.writeBalancedUsers(FileOperations.BALANCED_DB_HUMANS_PATH, users[User.HUMANS]);
    }
}
