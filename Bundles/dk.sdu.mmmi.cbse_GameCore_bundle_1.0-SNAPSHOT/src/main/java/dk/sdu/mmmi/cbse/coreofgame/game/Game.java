package dk.sdu.mmmi.cbse.coreofgame.game;

import dk.sdu.mmmi.cbse.coreofgame.tracker.PluginTracker;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import dk.sdu.mmmi.cbse.coreofgame.managers.GameInputProcessor;
import java.util.Collection;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class Game implements ApplicationListener {
    
    /**
     *
     * @param context
     * @return
     */
    public static LwjglApplication getApp(BundleContext context) {
        final LwjglApplicationConfiguration cfg
                = new LwjglApplicationConfiguration();
        
        cfg.title = "Test";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = true;
        
        Game game = new Game();
        LwjglApplication application = new LwjglApplication(game, cfg);
        game.setContext(context);
        
        return application;
    }
        

    private BundleContext context;
    private AssetManager assetManager;
    private PluginTracker pluginTracker;
    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private World world = new World();

    /**
     *
     */
    @Override
    public void create() {
        
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        
        cam.update();
        
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
         
        assetManager = new AssetManager(world, gameData, cam);
        
        pluginTracker = new PluginTracker(context, gameData, world, assetManager);
        pluginTracker.startPluginTracker();
        
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        
        
    }

    /**
     *
     */
    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        
        for(Bundle bundle : gameData.getBundles()){
            assetManager.loadAllPluginTextures(bundle);
            gameData.removeBundle(bundle);
        }
        
        IEntityProcessingService process;
        if(processReference() != null){
            for(ServiceReference<IEntityProcessingService> reference : processReference()){
                process = (IEntityProcessingService) context.getService(reference);
                process.process(gameData, world);
            }
        }
        placeCam();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        assetManager.loadImages(context);
    }

    /**
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     *
     */
    @Override
    public void pause() {
    }

    /**
     *
     */
    @Override
    public void resume() {
    }

    /**
     *
     */
    @Override
    public void dispose() {
        pluginTracker.stopPluginTracker();
        context.ungetService(context.getServiceReference(IEntityProcessingService.class.getName()));
    }

    private void postUpdate() {
    }
    
    /**
     *
     * @param context
     */
    public void setContext(BundleContext context){
        this.context = context;
    }
    
    /**
     *
     * @return
     */
    public Collection<ServiceReference<IEntityProcessingService>> processReference() {
        Collection<ServiceReference<IEntityProcessingService>> collection = null;
        try {
            collection = this.context.getServiceReferences(IEntityProcessingService.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Service not availlable!");
        }
        return collection;
    }
    
    public void placeCam(){
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
        if(reference == null){
            cam.lookAt(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
        } else {
            IPlayerPositionService playerPosition = (IPlayerPositionService) context.getService(reference);
            System.out.println(playerPosition.getX() + " " + playerPosition.getY());
            cam.position.x = playerPosition.getX();
            cam.position.y = playerPosition.getY();
            cam.update();
        }
    }
    
}
