/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import org.osgi.framework.Bundle;

/**
 *
 * @author Marcg
 */
public class BundleObj {
    Bundle bundle;
    String AssetPath = null;

    public BundleObj(Bundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * 
     * @return the bundle underneath. Null if no bundle.
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Sets the bundle underneath
     * @param bundle 
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getAssetPath() {
        return AssetPath;
    }

    public void setAssetPath(String AssetPath) {
        this.AssetPath = AssetPath;
    }
    
    
    
}
