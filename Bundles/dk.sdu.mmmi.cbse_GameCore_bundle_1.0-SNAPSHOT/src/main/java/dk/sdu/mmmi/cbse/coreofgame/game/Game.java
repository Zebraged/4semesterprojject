package dk.sdu.mmmi.cbse.coreofgame.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.coreofgame.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private static OrthographicCamera cam;
    private SpriteBatch batch;
    private BitmapFont font;
    private final GameData gameData = new GameData();
    private World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList();

    @Override
    public void create() {
        font = new BitmapFont();
        
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        cam.update();

        batch = new SpriteBatch();

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
        
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        font.draw(batch, "Oh hi, Marc!", 50, 50);
        batch.end();
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
    }

    private void postUpdate() {
    }
    
    
    public void setContext(BundleContext context){
        this.context = context;
    }
    
    
}
