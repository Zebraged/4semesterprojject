/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemyGenerator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Kristian
 */
public class ObjectsParser implements ISpecificParser {
    private IEnemyGenerator gen;
    private World world;
    private GameData data;
    public ObjectsParser(BundleContext context, GameData data, World world) {
        this.gen = (IEnemyGenerator)context.getService(context.getServiceReference(IEnemyGenerator.class));
        this.world = world;
        this.data = data;
    }
    
    public void parse(String line) {
        if(this.gen != null) {
            String[] keypair = line.split("=");
            if(keypair.length != 2) {
                System.out.println("Error found in level file!"
                        + "\tFormat must be:"
                        + "\tObject=identifier,x,y");
            } else {
                String[] args = keypair[1].split(",");
                gen.generate(args[0], Integer.valueOf(args[1])*32, Integer.valueOf(args[2])*32, world, data);
            }           
            
        } else {
            System.out.println("Warning: Generator was not found for Object Placement!");
        }
    }

}
