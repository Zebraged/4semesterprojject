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
    
    public Asset(String path, String image){
        this.imagePath = path;
        this.image = image;
    }
    
    public void setMirror(boolean mirror){
        this.mirror = mirror;
    }
    
    public boolean getMirror(){
        return mirror;
    }
    
    
    public void changeImagePath(String path){
        this.imagePath = path;
    }
    
    public void changeImage(String image){
        this.image = image;
    }
    
    public String getImagePath(){
        return imagePath;
    }
    
    public String getImage(){
        return this.image;
    }
}