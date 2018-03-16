package dk.sdu.projectframeworklauncher;

import java.io.File;
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
import org.osgi.framework.Bundle;
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
        try {
            FrameworkFactory fwFactory
                    = ServiceLoader.load(FrameworkFactory.class)
                            .iterator().next();
            Framework framework = fwFactory.newFramework(null);
            framework.init();
            BundleContext bndlCtxt = framework.getBundleContext();
            File folder = new File(".");
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    BundleObj bundleobj = new BundleObj(file, bndlCtxt);
                    obs.add(bundleobj);
                }
            }
            jfxListview.setItems(obs);

            framework.start();
        } catch (BundleException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ocListview(MouseEvent event) {
        updateText();
    }

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
        if (current.getState() == BundleState.INSTALLED) {
            current.uninstall();
        }
        updateText();
    }

    @FXML
    private void btnInstall(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.UNINSTALLED || current.getState() == BundleState.ERROR) {
            current.install();
        }
        updateText();
    }

    @FXML
    private void btnStart(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.INSTALLED || current.getState() == BundleState.ERROR) {
            current.start();
        }
        updateText();
    }

    @FXML
    private void btnStop(ActionEvent event) {
        BundleObj current = jfxListview.selectionModelProperty().getValue().getSelectedItem();
        if (current.getState() == BundleState.ACTIVE) {
            current.stop();
        }
        updateText();
    }

    @FXML
    private void listViewKey(KeyEvent event) {
        updateText();

    }
}
