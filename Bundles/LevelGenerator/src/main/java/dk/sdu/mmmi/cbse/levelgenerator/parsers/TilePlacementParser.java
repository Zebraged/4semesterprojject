/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemyGenerator;
import dk.sdu.mmmi.cbse.common.services.ITileGenerator;
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
    private ITileGenerator gen;
    private World world;
    private GameData data;
    private int lengthY, currentPosY;

    public TilePlacementParser(BundleContext context, GameData data, World world) {
        ServiceReference reference = context.getServiceReference(ITileGenerator.class);
        if (reference != null) {
            this.gen = (ITileGenerator) context.getService(context.getServiceReference(ITileGenerator.class));
        }
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

}
