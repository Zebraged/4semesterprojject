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
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.enemy.type.CloudEnemy;
import dk.sdu.mmmi.cbse.enemy.type.TeddyEnemy;
import dk.sdu.mmmi.cbse.enemy.type.UnicornEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import java.io.File;

/**
 *
 * @author Marcg
 */
public class EnemyFactory implements IEntityGenerator {

    private GameData data;
    private World world;

    public EnemyFactory() {

    }

    private Entity createTeddy(int x, int y) {
        Entity entity = new TeddyEnemy(world, data);
        entity.add(new PositionPart(x, y));


        entity = findImage(entity, "teddy");

        entity.add(new MovingPart(50, 800, 400));
        return entity;
    }

    private Entity findImage(Entity entity, String enemy) {
        File files = null;
        files = new File("./Bundles/Enemy/src/main/resources/image/" + enemy + "/Idle/");
        File[] fileslist = files.listFiles();
        if (fileslist != null) {
            boolean foundImage = false;
            for (File file : fileslist) {
                if (file.getName().endsWith(".png")) {
                    if (!foundImage) { // only load first image to entity
                        entity.add(new AssetGenerator(entity, "image/", file.getName()));
                        foundImage = true;
                    }
                }
            }
        }
        return entity;
    }

    private Entity createCloud(int x, int y) {
        Entity entity = new CloudEnemy();
        entity.add(new PositionPart(x, y));
        return entity;
    }

    private Entity createUnicorn(int x, int y) {
        Entity entity = new UnicornEnemy();
        entity.add(new PositionPart(x, y));
        return entity;
    }

    @Override
    public void generate(String identifier, int x, int y, World world, GameData data) {
        this.world = world;
        this.data = data;
        Entity enemy = null;
        switch (identifier.toLowerCase()) {
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
                break;
        }
    }

}
