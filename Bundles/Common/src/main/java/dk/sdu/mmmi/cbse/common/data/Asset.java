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
    
    public Asset(String path){
        this.imagePath = path;
    }
    
    public void changeImagePath(String path){
        this.imagePath = path;
    }
    
    public String getImagePath(){
        return imagePath;
    }
}