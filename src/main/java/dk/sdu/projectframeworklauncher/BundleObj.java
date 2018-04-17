/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.projectframeworklauncher;

import java.awt.Component;
import java.io.File;
import javax.swing.JOptionPane;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 *
 * @author Chris
 */
public class BundleObj {

    private Bundle bundle;
    private File file;
    private BundleContext bndlCtxt;
    private BundleState state = BundleState.UNINSTALLED;

    public BundleObj(File file, BundleContext bndlCtxt) {
        this.file = file;
        this.bndlCtxt = bndlCtxt;

    }

    /**
     * *
     * returns the name of the module, given from the filename.
     *
     * @return modulename
     */
    @Override
    public String toString() {
        return file.getName();

    }

    /**
     * *
     * Installs the module
     */
    public void install() {
        try {
            this.bundle = bndlCtxt.installBundle(file.toURI().toString());
            System.out.println(bundle + "Bundle Installed");
            state = BundleState.INSTALLED;
        } catch (BundleException ex) {

            printError(ex);
        }
    }

    /**
     * Uninstall the bundle.
     */
    public void uninstall() {
        try {
            this.bundle.uninstall();
            System.out.println("Bundle Uninstalled");
            state = BundleState.UNINSTALLED;
        } catch (BundleException ex) {

            printError(ex);
        }
    }

    /**
     * *
     * Start the module if no dependency error, otherwise a dialog will show.
     */
    public void start() {
        try {
            state = BundleState.STARTING;
            this.bundle.start();
            System.out.println("Bundle Started: "+this.bundle.getSymbolicName());
            state = BundleState.ACTIVE;
        } catch (BundleException ex) {

            printError(ex);
        }
    }

    /**
     * *
     * Stop the module from running
     */
    public void stop() {
        try {
            state = BundleState.STOPPING;
            this.bundle.stop();
            state = BundleState.INSTALLED;
            System.out.println("Bundle stopped");
        } catch (BundleException ex) {

            printError(ex);
        }
    }

    /**
     * *
     * return the current state of the module
     *
     * @return BundleState Enum
     */
    public BundleState getState() {
        return state;
    }

    /**
     * *
     * Prints the error from install/uninstall/start/stop commands.
     *
     * @param ex the exception catched
     */
    private void printError(Exception ex) {

        String error = ex.getMessage(); // receive the error catched
        String parsedStr = error.replaceAll("(.{80})", "$1\n"); // set newline for every n char
        parsedStr = parsedStr.substring(0, Math.min(parsedStr.length(), 479)); // set max length of string containing error = 6 lines

        Component frame = null;
        JOptionPane.showMessageDialog(frame, parsedStr, "Error Catched", JOptionPane.ERROR_MESSAGE); // show a errorbox

        state = BundleState.ERROR;

    }
}
