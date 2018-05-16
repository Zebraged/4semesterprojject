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

    /**
     * Generates the .lvl-file found on the given path.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void generate() throws FileNotFoundException, IOException;

    /**
     * Sets the path to the .lvl-file wished to be generated.
     * @param path 
     */
    public void setPath(String path);
    
    /**
     * 
     * @return true if generation is in process.
     */
    public boolean isGenerating();
}
