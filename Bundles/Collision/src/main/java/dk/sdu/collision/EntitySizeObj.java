package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Jesper
 */
public class EntitySizeObj extends PositionObj {

    /**
     * 
     * @param e entity to be affected.
     * @param sizex width of the entity
     * @param sizey height of the entity
     */
    public EntitySizeObj(Entity e, int sizex, int sizey) {
        super(e, sizex, sizey);
    }
}
