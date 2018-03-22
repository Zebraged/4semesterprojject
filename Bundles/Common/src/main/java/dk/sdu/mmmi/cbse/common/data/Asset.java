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
    
    InputStream imagePath;
    
    public Asset(InputStream path){
        this.imagePath = path;
    }
    
    public void changeImagePath(InputStream path){
        this.imagePath = path;
    }
    
    public InputStream getImagePath(){
        return imagePath;
    }
}