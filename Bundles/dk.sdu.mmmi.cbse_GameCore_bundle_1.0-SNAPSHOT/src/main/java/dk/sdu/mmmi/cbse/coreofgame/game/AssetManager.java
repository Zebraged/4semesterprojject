/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.game;


import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Asset;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private GameData data;

    public AssetManager(World world, GameData data) {
        this.world = world;
        this.data = data;
        
        this.batch = new SpriteBatch();
    }

    public void loadImages(BundleContext context) {
        batch.begin();
        for (Entity entity : world.getEntities()) {
            if(entity.getAsset() != null){
                InputStream in = entity.getAsset().getImagePath();
                Gdx2DPixmap pixmap = null;
                try {
                    pixmap = new Gdx2DPixmap(in, 1);
                    Texture texture = textureFromPixmap(pixmap);
                    Sprite sprite = new Sprite(texture);
                    PositionPart pos = entity.getPart(PositionPart.class);
                    sprite.setX(pos.getX());
                    sprite.setY(pos.getY());
                    sprite.draw(batch);
                } catch (IOException ex) {
                    System.out.println("input not avaiable");
                }
               
            }
        }
        batch.end();
    }
    
    Texture textureFromPixmap (Gdx2DPixmap pixmap) {
            Texture texture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Format.RGB565);
            texture.bind();
            //Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_2D, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            return texture;
    }


}
