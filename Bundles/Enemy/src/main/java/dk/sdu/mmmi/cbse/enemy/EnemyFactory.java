/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.GravityPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.enemy.type.CloudEnemy;
import dk.sdu.mmmi.cbse.enemy.type.TeddyEnemy;
import dk.sdu.mmmi.cbse.enemy.type.UnicornEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;

/**
 *
 * @author Marcg
 */
public class EnemyFactory implements IEntityGenerator {

    private GameData data;
    private World world;
    
    public EnemyFactory(){
        
    }
    
    
    private Entity createTeddy(int x, int y){
        Entity entity = new TeddyEnemy(world, data);
        entity.add(new PositionPart(x, y));
        entity.add(new GravityPart());
        entity.add(new AssetGenerator(entity, "image/teddy/", "Teddy_Idle1.png"));
        return entity;
    }
    
    private Entity createCloud(int x, int y){
        Entity entity = new CloudEnemy();
        entity.add(new PositionPart(x, y));
        return entity;
    }
    
    private Entity createUnicorn(int x, int y){
        Entity entity = new UnicornEnemy();
        entity.add(new PositionPart(x, y));
        return entity;
    }

    @Override
    public void generate(String identifier, int x, int y, World world, GameData data) {
        this.world = world;
        this.data = data;
        Entity enemy = null;
        switch(identifier.toLowerCase()){
            case "teddy":
                enemy = createTeddy(x, y);
                world.addEntity(enemy);
                break;
            case "cloud":
                enemy = createCloud(x, y);
                world.addEntity(enemy);
                break;
            case "unicorn":
                enemy = createUnicorn(x, y);
                world.addEntity(enemy);
                break;
            default:
                System.out.println("Unknown enemy type");
                break;
        }
    }
    
    
}
