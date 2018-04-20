/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy.EnemyAI;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author Marcg
 */
public class AIProcessor {
    AIMap map;
    
    public AIProcessor(World world, GameData data, Entity entity){
        map = new AIMap(world, data, entity);
    }
    
    
}
