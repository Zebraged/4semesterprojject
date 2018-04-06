package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable{
    private final Entity source;

    /**
     *
     * @param source
     */
    public Event(Entity source) {
        this.source = source;
    }

    /**
     *
     * @return
     */
    public Entity getSource() {
        return source;
    }
    
}
