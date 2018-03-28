/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.game;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private GameData data;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    
    private Map<String, Texture> spriteMap; 

    public AssetManager(World world, GameData data, OrthographicCamera cam) {
        this.world = world;
        this.data = data;
        this.spriteMap = new ConcurrentHashMap();
        
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
    }

    public void loadImages(BundleContext context) {
        lock.readLock().lock();
        try{
        batch.begin();
        for (Entity entity : world.getEntities()) {
            if(entity.getAsset() != null){
                Sprite sprite = new Sprite(spriteMap.get(entity.getAsset().getImage()));
                PositionPart pos = entity.getPart(PositionPart.class);
                if(entity.getAsset().getMirror() == true){
                    System.out.println("Right");
                    sprite.flip(true, false);
                } 
                sprite.setX((int)pos.getX());
                sprite.setY((int)pos.getY());
                sprite.draw(batch);
               
            }
        }
        batch.end();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void loadAllPluginTextures(Bundle bundle){
        lock.writeLock().lock();
        try{
            for (Entity entity : world.getEntities()) {
                if(entity.getAsset() != null){
                    System.out.println("Asset path: "+entity.getAsset().getImagePath());
                    URL url;
                    Enumeration<URL> urls = bundle.findEntries(entity.getAsset().getImagePath(), "*.png", true);
                    while(urls.hasMoreElements()){
                        url = urls.nextElement();
                        Pixmap pixmap = null;
                        try {
                            pixmap = new Pixmap(new Gdx2DPixmap(url.openStream(), GDX2D_FORMAT_RGB565));
                            Texture texture = new Texture(pixmap);
                            spriteMap.put(url.getPath().substring(url.getPath().lastIndexOf('/')+1, url.getPath().length()), texture);
                            System.out.println(url.getPath().substring(url.getPath().lastIndexOf('/')+1, url.getPath().length()) + " loaded!");
                        } catch (IOException ex) {
                            System.out.println("input not avaiable");
                        }
                    }
                }
            }
        } finally{
            lock.writeLock().unlock();
        }
        
    }
}
