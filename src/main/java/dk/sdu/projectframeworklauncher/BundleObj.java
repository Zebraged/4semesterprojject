/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.projectframeworklauncher;

import java.io.File;
import java.util.ArrayList;
import org.osgi.framework.Bundle;

/**
 *
 * @author Chris
 */
public class BundleObj {
    
    private Bundle bundle;
    private File file;
    
    public BundleObj(Bundle bundle, File file){
        this.bundle = bundle;
        this.file = file;
    }
    
    @Override
    public String toString(){
        return file.getName();
        
    }
    
}
