package dk.sdu.mmmi.cbse.coreofgame.game;

import dk.sdu.mmmi.cbse.coreofgame.tracker.PluginTracker;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.coreofgame.managers.GameInputProcessor;
import java.util.Collection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class Game implements ApplicationListener {
    
    
    public static LwjglApplication getApp(BundleContext context) {
        final LwjglApplicationConfiguration cfg
                = new LwjglApplicationConfiguration();
        
        cfg.title = "Test";
        cfg.width = 500;
        cfg.height = 400;
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

    @Override
    public void create() {
        
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        cam.update();
        
        assetManager = new AssetManager(world, gameData, cam);
        
        pluginTracker = new PluginTracker(context, gameData, world);
        pluginTracker.startPluginTracker();
        
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        
        
    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        IEntityProcessingService process;
        if(processReference() != null){
            for(ServiceReference<IEntityProcessingService> reference : processReference()){
                process = (IEntityProcessingService) context.getService(reference);
                process.process(gameData, world);
            }
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        assetManager.loadImages(context);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        pluginTracker.stopPluginTracker();
        context.ungetService(context.getServiceReference(IEntityProcessingService.class.getName()));
    }

    private void postUpdate() {
    }
    
    
    public void setContext(BundleContext context){
        this.context = context;
    }
    
    
    public Collection<ServiceReference<IEntityProcessingService>> processReference() {
        Collection<ServiceReference<IEntityProcessingService>> collection = null;
        try {
            collection = this.context.getServiceReferences(IEntityProcessingService.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Service not availlable!");
        }
        return collection;
    }
    
    
}
