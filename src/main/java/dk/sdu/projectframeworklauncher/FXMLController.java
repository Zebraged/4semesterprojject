package dk.sdu.projectframeworklauncher;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.FileUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class FXMLController implements Initializable {

    private BundleContext bndlCtxt;

    @FXML
    private ListView<BundleObj> jfxListview;
    //  private ArrayList<Bundle> boo = new ArrayList<>(); 
    private ObservableList<BundleObj> obs = FXCollections.observableArrayList();
    @FXML
    private Button btnuni;
    @FXML
    private Button btnins;
    @FXML
    private Button btnsta;
    @FXML
    private Button btnsto;
    @FXML
    private Label pathLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Button refreshButton;
    @FXML
    private Button btnQuickStart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deleteFelixCache();
        try {
            FrameworkFactory fwFactory
                    = ServiceLoader.load(FrameworkFactory.class)
                            .iterator().next();
            Framework framework = fwFactory.newFramework(null);
            framework.init();
            bndlCtxt = framework.getBundleContext();

            checkForBundles();

            framework.start();
        } catch (BundleException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * updates the text when the list is clicked.
     *
     * @param event
     */
    @FXML
    private void ocListview(MouseEvent event) {
        updateText();
    }

    /**
     * *
     * Reads the Bundle folder in the project. It check for jar files in the
     * folder and install them if found.
     */
    private void checkForBundles() {
        File folder = new File("./Bundles/");

        String[] directories = folder.list(new FilenameFilter() { // Get all folders in the bundle folder. Each folder should be an project.
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        ArrayList<String> filenames = new ArrayList(); // Arraylist to save all filenames found for each .jar
        File files = null;
        for (String projectPath : directories) { // loop trougth all folders in the bundles folder.
            files = new File("./Bundles/" + projectPath + "/target");

            File[] fileslist = files.listFiles();
            if (fileslist != null) { // dont check for jarfiles if target folder is empty or dont exist
                for (File file : fileslist) { // runs trougth all files in the folder
                    if (file.getName().endsWith(".jar")) { // ignore other than .jar files 

                        filenames.add(file.getName());

                        boolean existingItem = false;
                        for (BundleObj bundle : obs) {
                            if (bundle.toString().equals(file.getName())) { // checks if the module already exist in the list
                                existingItem = true;
                            }
                        }

                        if (!existingItem) { // if the module dont exist in the existing list it will be added.
                            BundleObj bundleobj = new BundleObj(file, bndlCtxt);
                            bundleobj.install();
                            obs.add(bundleobj);
                        }
                    }
                }

                jfxListview.setItems(obs);
            }
        }

        for (BundleObj obj : obs) { // check if a moudle has been removed from the folder.
            boolean found = false;
            for (String name : filenames) {
                if (obj.toString().equals(name)) {
                    found = true;
                }
            }
            if (!found) {
                obs.remove(obj);
            }
        }

    }

    /**
     * *
     * Deletes the Felix server cache, which contain temp files from last run.
     */
    private void deleteFelixCache() {
        File dir = new File("./felix-cache/");
        System.out.println("Wipe Felix cache Folder!");
        try {
            FileUtils.cleanDirectory(dir);

        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Updates the text on the gui (modulename and module state)
     */
    private void updateText() {
        BundleObj obj = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (obj != null) {
            stateLabel.setText(obj.getState().toString());
            pathLabel.setText(obj.toString());
        }
    }

    @FXML
    private void btnUninstall(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.INSTALLED && current != null) {
            current.uninstall();
        }
        updateText();
    }

    @FXML
    private void btnInstall(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.UNINSTALLED || current.getState() == BundleState.ERROR && current != null) {
            current.install();
        }
        updateText();
    }

    @FXML
    private void btnStart(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.INSTALLED || current.getState() == BundleState.ERROR && current != null) {
            current.start();
        }
        updateText();
    }

    @FXML
    private void btnStop(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.ACTIVE && current != null) {
            current.stop();
        }
        updateText();
    }

    /**
     * *
     * updates the text if the user hits the keyboard.
     *
     * @param event
     */
    @FXML
    private void listViewKey(KeyEvent event) {
        updateText();

    }

    @FXML
    private void refreshButtonA(ActionEvent event) {
        checkForBundles();
    }

    @FXML
    private void onQuickStartPressed(ActionEvent event) {
        for (BundleObj current : obs) {
            if (current.getState() == BundleState.INSTALLED || current.getState() == BundleState.ERROR && current != null) {
                current.start();
            }
            updateText();
        }
    }
}
