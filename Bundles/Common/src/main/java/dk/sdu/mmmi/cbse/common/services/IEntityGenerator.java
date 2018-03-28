/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author Marcg
 */
public interface IEntityGenerator {
    
    /**
     * Generate a new Entity file based on the identifier. if identifier is invalid no entity will be generated. Factory classes should implement this interface in each plugin.
     * @param identifier Enemy: "teddy", "cloud", "unicorn"
     * @param x start position for entity
     * @param y start position for entity
     * @param world
     * @param data
     */
    public void generate(String identifier, int x, int y, World world, GameData data);
}
