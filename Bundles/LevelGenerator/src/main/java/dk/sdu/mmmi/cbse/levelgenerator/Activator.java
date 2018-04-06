/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Kristian
 */
public class Activator implements BundleActivator {
    private LevelActivator act;
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), (act = new LevelActivator()), null);
    }
    
    public void stop(BundleContext context) throws Exception {
        act.stop();
    }
    
}
