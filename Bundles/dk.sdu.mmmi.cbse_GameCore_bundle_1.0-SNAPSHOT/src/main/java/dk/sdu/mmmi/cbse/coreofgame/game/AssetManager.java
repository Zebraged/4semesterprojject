/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import static com.badlogic.gdx.graphics.g2d.Gdx2DPixmap.GDX2D_FORMAT_RGB565;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import java.io.IOException;
import java.net.URL;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;


/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private GameData data;

    public AssetManager(World world, GameData data, OrthographicCamera cam) {
        this.world = world;
        this.data = data;
        
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
    }

    public void loadImages(BundleContext context) {
        batch.begin();
        for (Entity entity : world.getEntities()) {
            if(entity.getAsset() != null){
                URL url =  FrameworkUtil.getBundle(entity.getSource().getClass()).getResource(entity.getAsset().getImagePath());
                Gdx2DPixmap pixmap = null;
                try {
                    pixmap = new Gdx2DPixmap(url.openStream(), GDX2D_FORMAT_RGB565);
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
        batch.end();
        }
    }
    
    private Texture textureFromPixmap (Gdx2DPixmap pixmap) {
            Texture texture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Format.RGB565);
            texture.bind();
            Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_2D, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            return texture;
    }
    
    


}
