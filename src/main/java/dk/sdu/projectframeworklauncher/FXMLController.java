package dk.sdu.projectframeworklauncher;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deleteFelixCache();
        try {
            FrameworkFactory fwFactory
                    = ServiceLoader.load(FrameworkFactory.class)
                            .iterator().next();
            Framework framework = fwFactory.newFramework(null);
            framework.init();
            BundleContext bndlCtxt = framework.getBundleContext();
            File folder = new File("./Bundles/");

            String[] directories = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });

            File files = null;
            for (String projectPath : directories) {
                files = new File("./Bundles/" + projectPath + "/target");

                try {
                    for (File file : files.listFiles()) {
                        if (file.getName().endsWith(".jar")) {
                            BundleObj bundleobj = new BundleObj(file, bndlCtxt);
                            bundleobj.install();
                            obs.add(bundleobj);
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }

                jfxListview.setItems(obs);
            }
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
     * Deletes the Felix server cache, which contain temp files from last run.
     */
    private void deleteFelixCache() {
        File dir = new File("./felix-cache/");
        System.out.println("Wipe Felix cache Folder!");
        System.out.println("");
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
     * @param event
     */
    @FXML
    private void listViewKey(KeyEvent event) {
        updateText();

    }
}
