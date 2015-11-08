package botsdetection;

import artificialimmunesystem.NegativeSelection;
import balancing.Balancing;
import featuresextractor.FeaturesExtractor;
import featuresselection.FeaturesSelection;
import java.util.ArrayList;
import preprocessing.Preprocessing;
import util.FileOperations;
import util.User;

public class BotsDetection {
    
    public static final int EXECUTIONS = 10000;

    public static void main(String[] args) {
        //--------------------BALANCEAMENTO--------------------
        /*
            ArrayList<User>[] users = new ArrayList[2];
            users[User.BOTS] = FileOperations.readUsers(FileOperations.DB_BOTS_PATH, true);
            users[User.HUMANS] = FileOperations.readUsers(FileOperations.DB_HUMANS_PATH, false);
            
            Balancing b = new Balancing(users);
            b.run();
            b.extractFiles();
            
        */
        //---------------------------------------------------------
        
        //--------------------PRE-PROCESSAMENTO--------------------
        /*
            ArrayList<User>[] users = new ArrayList[2];
            users[User.BOTS] = FileOperations.readUsers(FileOperations.BALANCED_DB_BOTS_PATH, true);
            users[User.HUMANS] = FileOperations.readUsers(FileOperations.BALANCED_DB_HUMANS_PATH, false);

            Preprocessing p = new Preprocessing(users);
            p.run();
            p.extractFiles();
        */
        //---------------------------------------------------------

        //--------------------EXTRACAO DE CARACTERISTICAS--------------------
        /*
            ArrayList<User>[] users = new ArrayList[2];
            users[User.BOTS] = FileOperations.readUsers(FileOperations.BALANCED_DB_BOTS_PATH, true);
            users[User.HUMANS] = FileOperations.readUsers(FileOperations.BALANCED_DB_HUMANS_PATH, false);
            FileOperations.readProcessedUsers(users[User.BOTS], FileOperations.PROCESSED_BOTS_PATH);
            FileOperations.readProcessedUsers(users[User.HUMANS], FileOperations.PROCESSED_HUMANS_PATH);

            FeaturesExtractor fe = new FeaturesExtractor(users);
            fe.run();
            fe.normalize();
            fe.extractFiles();
        */
        //-------------------------------------------------------------------
        
        //--------------------SELECAO DE CARACTERISTICAS--------------------
        /*
            ArrayList<User> users = FileOperations.readFeatures(FileOperations.FEATURES_PATH);

            FeaturesSelection fs = new FeaturesSelection(users);
            fs.run();
            fs.extractFiles();
        */
        //------------------------------------------------------------------
        
        //--------------------SISTEMA IMUNOLOGICO ARTIFICIAL--------------------
        ///*
            ArrayList<User>[] users = new ArrayList[2];
            users[User.DATABASE] = FileOperations.readFeatures(FileOperations.SELECTED_FEATURES_PATH);
            users[User.SELF] = User.splitSelf(users[User.DATABASE]);
            users[User.DATABASE].removeAll(users[User.SELF]);

            NegativeSelection nsa = new NegativeSelection(users);
            for (int i = 0; i < EXECUTIONS; i++) {
                nsa.run();
            }
            nsa.extractFile();
        //*/
        //----------------------------------------------------------------------
    }
}
