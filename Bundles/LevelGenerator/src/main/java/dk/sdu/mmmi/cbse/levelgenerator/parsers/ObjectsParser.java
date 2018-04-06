/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemyGenerator;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import java.util.Collection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Kristian
 */
public class ObjectsParser implements ISpecificParser {

    private IEntityGenerator gen;
    private World world;
    private GameData data;

    public ObjectsParser(GameData data, World world) {
        this.world = world;
        this.data = data;
    }

    public void parse(String line) {
        if (this.gen != null) {
            String[] keypair = line.split("=");
            if (keypair.length != 2) {
                System.out.println("Error found in level file!"
                        + "\tFormat must be:"
                        + "\tObject=identifier,x,y");
            } else {
                String[] args = keypair[1].split(",");
                gen.generate(args[0], Integer.valueOf(args[1]) * 32, Integer.valueOf(args[2]) * 32, world, data);
            }

        } else {
            System.out.println("Warning: Generator was not found for Object Placement!");
        }
    }

    public void parse(Collection<IEntityGenerator> generators, String line) {
        for(IEntityGenerator gens : generators) {
            this.gen = gens;
            parse(line);
        }
    }

}
