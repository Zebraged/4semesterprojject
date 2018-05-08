/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import static com.badlogic.gdx.graphics.g2d.Gdx2DPixmap.GDX2D_FORMAT_RGBA8888;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.BundleObj;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;
import dk.sdu.mmmi.cbse.common.services.IScoreService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class AssetManager {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera cam;
    private OrthographicCamera GUIcam;
    private GameData data;
    private Texture background = null;
    private ArrayList<Bundle> loadedBundles = new ArrayList();
    private BitmapFont font;
    private BundleContext context;
    private IScoreService score = null;
    private IPlayerInfoService playerInfo = null;

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

        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

        GUIcam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GUIcam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        GUIcam.update();
    }

    /**
     * load all the sprites based on each entity that have an asset. the
     * textures are preloaded when the plugin is loaded. so all that are needed
     * is a string with the image name.
     *
     * @param context
     */
    public void loadImages() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        GUIcam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        if (data.isGameWon() == true) {
            batch.setProjectionMatrix(GUIcam.combined);
            drawWinMessage();
        } else if (data.isGameLost()) {

        } else {
            Rectangle rect  = new Rectangle((cam.position.x - (cam.viewportWidth/2)), (cam.position.y - (cam.viewportHeight/2)), cam.viewportWidth, cam.viewportHeight);
            for (Entity entity : sortEntities()) {
                PositionPart pos = entity.getPart(PositionPart.class);
                
                if (entity.getAsset() != null && textureMap.get(entity.getAsset().getImage()) != null && pos.getZ() == 1){//draw backgrounds no matter what position
                    Sprite sprite = new Sprite(textureMap.get(entity.getAsset().getImage()));
                    sprite.setX((int) pos.getX());
                    sprite.setY((int) pos.getY());
                    sprite.draw(batch);
                } else if (entity.getAsset() != null && textureMap.get(entity.getAsset().getImage()) != null && rect.overlaps(new Rectangle(pos.getX(), pos.getY(), 32, 32))) {
                    Sprite sprite = new Sprite(textureMap.get(entity.getAsset().getImage()));
                    if (entity.getAsset().getMirror() == true) {//Mirror the image if the value is true
                        sprite.flip(true, false);
                    }
                    sprite.setX((int) pos.getX()); //change x and y position of image based on position part
                    sprite.setY((int) pos.getY());

                    sprite.draw(batch);

                } 
            }
            batch.setProjectionMatrix(GUIcam.combined);
            drawHud();
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

    public void drawWinMessage() {
        String won = "Game Won!!!";
        String finalScore = "Final score: " + score.getFinalScore();
        font.draw(batch, won, getPositionOffset(font, won), GUIcam.viewportHeight / 2);
        if (score != null) {
            font.draw(batch, finalScore, getPositionOffset(font, finalScore), GUIcam.viewportHeight / 2 - 40);
        }
    }
    
    public void drawLoseMessage() {
        String won = "Game Lost!!!";
        String finalScore = "Final score: " + score.getFinalScore();
        font.draw(batch, won, getPositionOffset(font, won), GUIcam.viewportHeight / 2);
        if (score != null) {
            font.draw(batch, finalScore, getPositionOffset(font, finalScore), GUIcam.viewportHeight / 2 - 40);
        }
    }
    
    public void drawPauseMessage() {
        batch.begin();
        font.setColor(Color.RED);
        String won = "Pause";
        String finalScore = "Generating new entities";
        font.draw(batch, won, getPositionOffset(font, won), GUIcam.viewportHeight / 2);
        if (score != null) {
            font.draw(batch, finalScore, getPositionOffset(font, finalScore), GUIcam.viewportHeight / 2 - 40);
        }
        font.setColor(Color.WHITE);
        batch.end();
    }

    public void drawHud() {
        ServiceReference ref = context.getServiceReference(IScoreService.class);
        ServiceReference playerRef = context.getServiceReference(IPlayerInfoService.class);
        if (ref != null) {
            score = (IScoreService) context.getService(ref);
            drawTimer();
            drawScore();
        }
        
        if (playerRef != null){
            playerInfo = (IPlayerInfoService) context.getService(playerRef);
            drawLife();
        }
        
    }

    public void drawTimer() {
        if (score != null) {
            String time = "time: " + score.getTimer(Gdx.graphics.getDeltaTime());
            font.draw(batch, time, getPositionOffset(font, time), GUIcam.viewportHeight - 50);
        }
    }

    public void drawScore() {
        if (score != null) {
            String sc = "score: " + score.getCurrentScore();
            font.draw(batch, sc, GUIcam.viewportWidth - 200, GUIcam.viewportHeight - 50);
        }
    }

    private float getPositionOffset(BitmapFont bitmapFont, String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (GUIcam.viewportWidth / 2) - (glyphLayout.width/2);
    }
    
    private void drawLife(){
        if(textureMap.containsKey("heart.png")){
            int xoffset = 50;
            for(int i = 0; i < playerInfo.getLife(); i++){
                Sprite sprite = new Sprite(textureMap.get("heart.png"));
                sprite.setSize(32, 32);
                sprite.setX(xoffset);
                sprite.setY(GUIcam.viewportHeight - 80);
                sprite.draw(batch);
                xoffset = xoffset + 40;
            }
        } else {
            font.draw(batch, "life: " + playerInfo.getLife(), 0 + 50, GUIcam.viewportHeight - 80);
        }
    }
}
