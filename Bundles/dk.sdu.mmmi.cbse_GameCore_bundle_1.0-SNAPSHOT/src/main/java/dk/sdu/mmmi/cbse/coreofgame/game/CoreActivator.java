/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.game;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mr. Kinder
 */
public class CoreActivator implements BundleActivator {
    
    /**
     *
     * @param context
     * @throws Exception
     */
    public void start(BundleContext context) throws Exception {
        System.out.println("Info: Starting game..");
        Game.getApp(context);
        
    }
    
    /**
     *
     * @param context
     * @throws Exception
     */
    public void stop(BundleContext context) throws Exception {
        //TODO add deactivation code here
    }
    
}
