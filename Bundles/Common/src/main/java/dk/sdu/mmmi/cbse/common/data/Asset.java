/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import java.io.InputStream;

/**
 *
 * @author Marcg
 */
public class Asset {
    
    String imagePath;
    String image;
    boolean mirror = false;
    
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
     *
     * @param mirror
     */
    public void setMirror(boolean mirror){
        this.mirror = mirror;
    }
    
    /**
     *
     * @return
     */
    public boolean getMirror(){
        return mirror;
    }
    
    /**
     *
     * @param path
     */
    public void changeImagePath(String path){
        this.imagePath = path;
    }
    
    /**
     *
     * @param image
     */
    public void changeImage(String image){
        this.image = image;
    }
    
    /**
     *
     * @return
     */
    public String getImagePath(){
        return imagePath;
    }
    
    /**
     *
     * @return
     */
    public String getImage(){
        return this.image;
    }
}