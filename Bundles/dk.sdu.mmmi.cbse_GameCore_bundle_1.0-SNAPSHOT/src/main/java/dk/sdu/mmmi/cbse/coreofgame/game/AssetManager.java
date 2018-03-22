/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
//import dk.sdu.mmmi.cbse.common.data.Asset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private GameData data;
    private List<Entity> entities;

    public AssetManager(World world, GameData data) {
        this.world = world;
        this.data = data;

        entities = (ArrayList<Entity>) world.getEntities();
        this.batch = new SpriteBatch();
        batch.begin();
    }

    public void loadImages() {
        batch.begin();
        for (Entity entity : entities) {
        }

    }

}
