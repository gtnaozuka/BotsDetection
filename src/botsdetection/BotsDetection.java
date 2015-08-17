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
         users[User.DB_BOTS] = FileOperations.readPosts(FileOperations.DB_BOTS_PATH);
         users[User.DB_HUMANS] = FileOperations.readPosts(FileOperations.DB_HUMANS_PATH);
        
         Preprocessing p = new Preprocessing(users);
         p.run();
         p.extractFiles();
         */
        //---------------------------------------------------------

        //--------------------EXTRACAO DE CARACTERISTICAS--------------------
        /*
         ArrayList<User>[] users = new ArrayList[4];
         users[User.DB_BOTS] = FileOperations.readPosts(FileOperations.DB_BOTS_PATH);
         users[User.DB_HUMANS] = FileOperations.readPosts(FileOperations.DB_HUMANS_PATH);
         users[User.PROCESSED_BOTS] = FileOperations.readPosts(FileOperations.PROCESSED_BOTS_PATH);
         users[User.PROCESSED_HUMANS] = FileOperations.readPosts(FileOperations.PROCESSED_HUMANS_PATH);
        
         FeaturesExtractor fe = new FeaturesExtractor(users);
         fe.run();
         fe.extractFiles();
         */
        //-------------------------------------------------------------------
        
        //--------------------SISTEMA IMUNOLOGICO ARTIFICIAL--------------------
        ///*
        ArrayList<User>[] users = new ArrayList[2];
        users[User.SELF] = FileOperations.readFeatures(FileOperations.FEATURES_HUMANS_PATH, false);
        users[User.DATABASE] = FileOperations.readFeatures(FileOperations.FEATURES_BOTS_PATH, true);

        users[User.DATABASE].addAll(users[User.SELF]);
        users[User.SELF] = new ArrayList<>(users[User.SELF].subList(0, NegativeSelection.QTD_DETECTORS));
        users[User.DATABASE].removeAll(users[User.SELF]);

        NegativeSelection nsa = new NegativeSelection(users);
        for (int i = 0; i < 10000; i++) {
            nsa.run();
        }
        nsa.extractFile();
        //*/
        //----------------------------------------------------------------------
    }
}
