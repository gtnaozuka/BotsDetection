package botsdetection;

import artificialimmunesystem.NegativeSelection;
import featuresextractor.FeaturesExtractor;
import java.util.ArrayList;
import preprocessing.Preprocessing;
import util.FileOperations;
import util.User;

public class BotsDetection {

    public static void main(String[] args) {
        //--------------------PRE-PROCESSAMENTO--------------------
        /*
         ArrayList<User>[] users = new ArrayList[2];
         users[User.BOTS] = FileOperations.readUsers(FileOperations.DB_BOTS_PATH, true);
         users[User.HUMANS] = FileOperations.readUsers(FileOperations.DB_HUMANS_PATH, false);
        
         Preprocessing p = new Preprocessing(users);
         p.run();
         p.extractFiles();
         */
        //---------------------------------------------------------

        //--------------------EXTRACAO DE CARACTERISTICAS--------------------
        /*
         ArrayList<User>[] users = new ArrayList[2];
         users[User.BOTS] = FileOperations.readUsers(FileOperations.DB_BOTS_PATH, true);
         users[User.HUMANS] = FileOperations.readUsers(FileOperations.DB_HUMANS_PATH, false);
         FileOperations.readProcessedUsers(users[User.BOTS], FileOperations.PROCESSED_BOTS_PATH);
         FileOperations.readProcessedUsers(users[User.HUMANS], FileOperations.PROCESSED_HUMANS_PATH);
        
         FeaturesExtractor fe = new FeaturesExtractor(users);
         fe.run();
         fe.extractFiles();
         */
        //-------------------------------------------------------------------
        
        //--------------------SISTEMA IMUNOLOGICO ARTIFICIAL--------------------
        ///*
        ArrayList<User>[] users = new ArrayList[2];
        users[User.DATABASE] = FileOperations.readFeatures();
        users[User.SELF] = splitSelf(users[User.DATABASE]);
        users[User.DATABASE].removeAll(users[User.SELF]);

        NegativeSelection nsa = new NegativeSelection(users);
        for (int i = 0; i < 10000; i++) {
            nsa.run();
        }
        nsa.extractFile();
        //*/
        //----------------------------------------------------------------------
    }
    
    private static ArrayList<User> splitSelf(ArrayList<User> dbUsers) {
        ArrayList<User> selfUsers = new ArrayList<>();
        
        for (User u : dbUsers) {
            if (!u.isBot())
                selfUsers.add(u);
        }
        
        return new ArrayList<>(selfUsers.subList(0, NegativeSelection.QTD_DETECTORS));
    }
}
