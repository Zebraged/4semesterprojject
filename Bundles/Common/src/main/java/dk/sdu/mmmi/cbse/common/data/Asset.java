/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import java.io.InputStream;
import org.osgi.framework.Bundle;

/**
 *
 * @author Marcg
 */
public class Asset {
    
    String imagePath;
    String image;
    boolean loaded = false;
    boolean mirror = false;
    private Bundle bundle; 
    
    /**
     *
     * @param path
     * @param image
     */
    public Asset(String path, String image){
        this.imagePath = path;
        this.image = image;
    }
    
    /**
     * set true if image should change direction (left or right)
     * @param mirror
     */
    public void setMirror(boolean mirror){
        this.mirror = mirror;
    }
    
    /**
     * returns image direction
     * @return
     */
    public boolean getMirror(){
        return mirror;
    }
    
    /**
     * path to image
     * @param path
     */
    public void changeImagePath(String path){
        this.imagePath = path;
    }
    
    /**
     * image name
     * @param image
     */
    public void changeImage(String image){
        this.image = image;
    }
    
    /**
     * get path to image
     * @return
     */
    public String getImagePath(){
        return imagePath;
    }
    
    /**
     * get image name
     * @return
     */
    public String getImage(){
        return this.image;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    /**
     * @return the bundle
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
    
    
    
    
}