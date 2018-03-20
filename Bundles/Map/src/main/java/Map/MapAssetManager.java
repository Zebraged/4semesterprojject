/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import dk.sdu.mmmi.cbse.map.MapServices;
import java.io.File;

/**
 *
 * @author Guest Account
 */
public class MapAssetManager implements MapServices{
    File file = new File("");
    boolean isLoadable;
    
    
    
    public void load(){
        isLoadable = true;
    }
    
    public String findPath(){
        String path = null;
        path = file.getAbsoluteFile().getPath() + "\\assets\\Background";
         
        return path;
    }
    
    public void stop(){
        isLoadable = false;
    }
    
}
