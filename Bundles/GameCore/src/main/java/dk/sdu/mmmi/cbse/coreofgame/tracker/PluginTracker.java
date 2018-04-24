/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.tracker;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.coreofgame.game.AssetManager;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class PluginTracker {
    boolean active;
    BundleContext context;
    GameData gameData;
    World world;
    AssetManager assetManager;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    
    /**
     *  used for tracking new plugins being loaded
     * @param context
     * @param data
     * @param world
     * @param assetManager
     */
    public PluginTracker(BundleContext context, GameData data, World world, AssetManager assetManager){
        this.context = context;
        this.gameData = data;
        this.world = world;
        this.assetManager = assetManager;
    }
    
    //starts any new plugin that have been loaded
    private void loadPlugins(){
        IGamePluginService plugin;
        for(ServiceReference<IGamePluginService> reference : pluginReference()){
            plugin = (IGamePluginService) context.getService(reference);
            if(plugin.getStatus() == false){
                System.out.println("New plugin detected! "+plugin.getClass().toString());
                
                plugin.start(gameData, world, context);//adds the new loaded bundle to gameData for imageloading
                gameData.addBundle(reference.getBundle());
            }
        }
    }
    
    //returns a service reference for each plugin available in the program
    private Collection<ServiceReference<IGamePluginService>> pluginReference() {
        Collection<ServiceReference<IGamePluginService>> collection = null;
        try {
            collection = this.context.getServiceReferences(IGamePluginService.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Service not availlable!");
            active = false; //stop thread if service is unavailable
        }
        return collection;
    }
    
    /**
     * start a thread that keeps checking for new plugins every 5 seconds
     */
    public void startPluginTracker(){
        active = true;
        executor.execute(()->{
            while(active){
               try {
                    loadPlugins();
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    System.out.println("Thread failed");
                    System.out.println(ex);
                } 
            }
        });
    }
    
    /**
     * stop any thread looking for plugins and ungets any pluginService.
     */
    public void stopPluginTracker(){
        active = false;
        context.ungetService(context.getServiceReference(IGamePluginService.class.getName()));
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
