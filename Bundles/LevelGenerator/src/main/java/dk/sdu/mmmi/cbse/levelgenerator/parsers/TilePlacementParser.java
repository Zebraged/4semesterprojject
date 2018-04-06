/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import java.util.Collection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Kristian
 */
public class TilePlacementParser implements ISpecificParser {

    /**
     * @param lengthY the lengthY to set
     */
    public void setLengthY(int lengthY) {
        this.currentPosY = lengthY;
        this.lengthY = lengthY;
    }
    private IEntityGenerator gen;
    private World world;
    private GameData data;
    private int lengthY, currentPosY;

    public TilePlacementParser(GameData data, World world) {
        this.data = data;
        this.world = world;
    }

    public void parse(String line) {
        if (this.gen != null) {
            char[] chars = line.toCharArray();
            int x = 0;
            for (char c : chars) {
                this.gen.generate("" + c, x * 32, currentPosY * 32, world, data);
                x++;
            }
            currentPosY--;
        } else {
            System.out.println("Warning: Generator was not found for Tile Placement!");
        }
    }

    public void parse(Collection<IEntityGenerator> generators, String line) {
        for (IEntityGenerator gens : generators) {
            this.gen = gens;
            parse(line);
        }
    }

}
