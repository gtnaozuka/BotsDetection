package featuresselection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Features;
import util.FileOperations;
import util.User;

public class FeaturesSelection {

    private final ArrayList<User> users;
    private final ArrayList<String> selectedFeatures;

    public FeaturesSelection(ArrayList<User> users) {
        this.users = users;
        this.selectedFeatures = new ArrayList<>();
    }

    public void run() {
        readSelectedFeatures();
        processFeatures();
    }

    public void extractFiles() {
        FileOperations.writeFeatures(FileOperations.SELECTED_FEATURES_PATH, selectedFeatures, users);
    }

    private void readSelectedFeatures() {
        for (int i = 0; i < Features.features.size(); i++) {
            System.out.println((i + 1) + " - " + Features.features.get(i));
        }

        while (true) {
            System.out.print("Entre com as características selecionadas: ");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int read = Integer.valueOf(br.readLine());

                if (read > 0 && read < Features.features.size() + 1) {
                    selectedFeatures.add(Features.features.get(read - 1));
                } else {
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(FeaturesSelection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                break;
            }
        }
    }

    private void processFeatures() {
        for (User u : users) {
            Iterator it = u.getFeatures().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Double> entry = (Map.Entry<String, Double>) it.next();
                if (!selectedFeatures.contains(entry.getKey())) {
                    it.remove();
                }
            }
        }
    }
}
