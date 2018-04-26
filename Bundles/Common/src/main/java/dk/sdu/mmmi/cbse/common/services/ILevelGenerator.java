/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Kristian
 */
public interface ILevelGenerator {

    public void generate() throws FileNotFoundException, IOException;

    public void setPath(String path);
    
    public boolean isGenerating();
}
