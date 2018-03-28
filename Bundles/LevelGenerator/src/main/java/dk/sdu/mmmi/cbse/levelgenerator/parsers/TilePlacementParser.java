/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.ITileGenerator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Kristian
 */
public class TilePlacementParser implements ISpecificParser {
    private ITileGenerator gen;
    private World world;
    private GameData data;
    public TilePlacementParser(ITileGenerator gen, GameData data, World world) {
        this.gen = gen;
        this.data = data;
        this.world = world;
    }

    public void parse(String line) {
        if(this.gen != null) {
            
        } else {
            System.out.println("Warning: Generator was not found for Tile Placement!");
        }
    }
    
}
