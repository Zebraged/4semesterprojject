package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jcs
 */
public class World {

    private final Map<String, Entity> entityMap;

    /**
     *
     */
    public World() {
        this.entityMap = new ConcurrentHashMap();
    }
    
    /**
     *
     * @param entity
     * @return
     */
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    /**
     *
     * @param entityID
     */
    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    /**
     *
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }
    
    /**
     *
     * @return
     */
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    /**
     *
     * @param <E>
     * @param entityTypes
     * @return
     */
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    /**
     *
     * @param ID
     * @return
     */
    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
    
    

}
