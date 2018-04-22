/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import static com.badlogic.gdx.graphics.g2d.Gdx2DPixmap.GDX2D_FORMAT_RGBA8888;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.BundleObj;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
<<<<<<< HEAD:Bundles/GameCore/src/main/java/dk/sdu/mmmi/cbse/coreofgame/game/AssetManager.java
import java.util.Collections;
import java.util.Comparator;
=======
>>>>>>> master:Bundles/GameCore/src/main/java/dk/sdu/mmmi/cbse/coreofgame/managers/AssetManager.java
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera cam;
    private GameData data;
    private Texture background = null;
    private ArrayList<Bundle> loadedBundles = new ArrayList();

    private Map<String, Texture> textureMap;

    /**
     * for loading and choosing images for each entity in the game.
     *
     * @param world
     * @param data
     * @param cam
     */
    public AssetManager(World world, GameData data, OrthographicCamera cam) {
        this.world = world;
        this.data = data;
        this.textureMap = new ConcurrentHashMap();
        this.cam = cam;
        this.batch = new SpriteBatch();
    }

    /**
     * load all the sprites based on each entity that have an asset. the
     * textures are preloaded when the plugin is loaded. so all that are needed
     * is a string with the image name.
     *
     * @param context
     */
    public void loadImages(BundleContext context) {

        //Skal laves om til int depend
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
<<<<<<< HEAD:Bundles/GameCore/src/main/java/dk/sdu/mmmi/cbse/coreofgame/game/AssetManager.java
        //loadBackground();
        for (Entity entity :sortEntities() ){
            if (entity.getAsset() != null && textureMap.get(entity.getAsset().getImage()) != null) {
                //if (entity.getAsset().isBackground() == true) {
                //    background = textureMap.get(entity.getAsset().getImage());
                //} else {
                Sprite sprite = new Sprite(textureMap.get(entity.getAsset().getImage()));
                PositionPart pos = entity.getPart(PositionPart.class);
                if (entity.getAsset().getMirror() == true) {//Mirror the image if the value is true
                    sprite.flip(true, false);
                }
                sprite.setX((int) pos.getX()); //change x and y position of image based on position part
                sprite.setY((int) pos.getY());
                ;
                sprite.draw(batch);
                //}
=======
        loadBackground();
        for (Entity entity : world.getEntities()) {
            if (entity.getAsset() != null && entity.getAsset().getImage() != null) {
                if (textureMap.get(entity.getAsset().getImage()) != null) {
                    if (entity.getAsset().isBackground() == true) {
                        background = textureMap.get(entity.getAsset().getImage());
                    } else {
                        
                        Sprite sprite = new Sprite(textureMap.get(entity.getAsset().getImage()));
                        PositionPart pos = entity.getPart(PositionPart.class);
                        if (entity.getAsset().getMirror() == true) {//Mirror the image if the value is true
                            sprite.flip(true, false);
                        }
                        sprite.setX((int) pos.getX()); //change x and y position of image based on position part
                        sprite.setY((int) pos.getY());
                        sprite.draw(batch);
                    }
                }

>>>>>>> master:Bundles/GameCore/src/main/java/dk/sdu/mmmi/cbse/coreofgame/managers/AssetManager.java
            }
        }
        batch.end();
    }

    /**
     * when a this method is called, will all loaded plugins load their images
     * relevant to the entities created in world.
     *
     * @param bundle
     */
    public void loadAllPluginTextures() {
        for (BundleObj obj : data.getBundles()) {
            if (obj.getAssetPath() != null && imageExist(obj.getBundle()) == false) {
                URL url;
                Enumeration<URL> urls = obj.getBundle().findEntries(obj.getAssetPath(), "*.png", true);
                while (urls.hasMoreElements()) {
                    url = urls.nextElement();
                    Pixmap pixmap = null;
                    try {
                        pixmap = new Pixmap(new Gdx2DPixmap(url.openStream(), GDX2D_FORMAT_RGBA8888));
                        Texture texture = new Texture(pixmap);
                        textureMap.put(url.getPath().substring(url.getPath().lastIndexOf('/') + 1, url.getPath().length()), texture);
                        System.out.println(url.getPath().substring(url.getPath().lastIndexOf('/') + 1, url.getPath().length()) + " loaded!");
                        loadedBundles.add(obj.getBundle());
                    } catch (IOException ex) {
                        System.out.println("input not avaiable");
                    }
                }
            }
        }
    }

    /*public void loadBackground() {
        if (background != null) {
            Sprite sprite = new Sprite(background);
            sprite.setX(0);
            sprite.setY(0);
            sprite.draw(batch);
        }
    }*/

    public boolean imageExist(Bundle bundle) {
        for (Bundle bund : loadedBundles) {
            if (bundle.equals(bund)) {
                return true;
            }
        }
        return false;
    }

    private List<Entity> sortEntities() {
        List<Entity> sortedentitiesList = new ArrayList();
        for (Entity entity : world.getEntities()) {
            sortedentitiesList.add(entity);
        }
        Collections.sort(sortedentitiesList, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                PositionPart pos1 = e1.getPart(PositionPart.class);
                PositionPart pos2 = e2.getPart(PositionPart.class);
                return Float.compare(pos1.getZ(), pos2.getZ());
        
                
            }
});
      return sortedentitiesList;  
    }
}
