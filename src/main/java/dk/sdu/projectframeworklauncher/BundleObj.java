/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.projectframeworklauncher;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //   this.bundle = bundle;
        this.file = file;
        this.bndlCtxt = bndlCtxt;

    }

    @Override
    public String toString() {
        return file.getName();

    }

    public void install() {
        try {
            this.bundle = bndlCtxt.installBundle(file.toURI().toString());
            System.out.println(bundle + "Bundle Installed");
            state = BundleState.INSTALLED;
        } catch (BundleException ex) {
            state = BundleState.ERROR;
            System.out.println(ex.getMessage());
            Logger.getLogger(BundleObj.class.getName()).log(Level.SEVERE, null, ex);
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
            state = BundleState.ERROR;
            System.out.println(ex.getMessage());
            Logger.getLogger(BundleObj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        try {
            state = BundleState.STARTING;
            this.bundle.start();
            System.out.println("Bundle Started");
            state = BundleState.ACTIVE;
        } catch (BundleException ex) {
            state = BundleState.ERROR;
            System.out.println(ex.getMessage());
            Logger.getLogger(BundleObj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            state = BundleState.STOPPING;
            this.bundle.stop();
            state = BundleState.INSTALLED;
            System.out.println("Bundle stopped");
        } catch (BundleException ex) {
            state = BundleState.ERROR;
            System.out.println(ex.getMessage());
            Logger.getLogger(BundleObj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BundleState getState() {
        return state;
    }
}
