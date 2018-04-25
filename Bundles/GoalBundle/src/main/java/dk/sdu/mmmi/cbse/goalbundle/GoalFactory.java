/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.goalbundle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import java.io.File;

/**
 *
 * @author Marcg
 */
public class GoalFactory implements IEntityGenerator{

    World world;
    GameData data;
    
    public void generate(String identifier, int x, int y, int z, World world, GameData data) {
        this.world = world;
        this.data = data;
        if(identifier.toLowerCase().equals("goal")){
            createGoal(x, y);
        }
    }
    
    private void createGoal(int x, int y){
        Entity goal = new Goal();
        goal.add(new PositionPart(x, y, 3));
        goal.add(new SizePart(32, 32));
        goal = findImage(goal);
        System.out.println("goal created");
        world.addEntity(goal);
    }
    
    private Entity findImage(Entity entity) {
        File files = null;
        files = new File("./Bundles/GoalBundle/src/main/resources/image/Idle");
        File[] fileslist = files.listFiles();
        if (fileslist != null) {
            boolean foundImage = false;
            for (File file : fileslist) {
                if (file.getName().endsWith(".png")) {
                    if (!foundImage) { // only load first image to entity
                        System.out.println("found: " + file.getName());
                        entity.add(new AssetGenerator(entity, "image/", file.getName()));
                        foundImage = true;
                    }
                }
            }
        }
        return entity;
    }
    
}
