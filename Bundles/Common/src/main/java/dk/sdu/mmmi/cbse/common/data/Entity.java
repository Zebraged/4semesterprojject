package dk.sdu.mmmi.cbse.common.data;


import dk.sdu.mmmi.cbse.common.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Marcg
 */
public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    
    private float radius;
    private Map<Class, EntityPart> parts;
    private int[] color = new int[4];
    private Asset asset = null;
    private boolean ConstantPosition;
    private int alignment;
    
    /**
     *
     */
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }
    
    /**
     *
     * @param part
     */
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    /**
     *
     * @param partClass
     */
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    /**
     *
     * @param <E>
     * @param partClass
     * @return
     */
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    
    /**
     *
     * @param partClass
     * @return
     */
    public boolean containPart(Class partClass){
        boolean check = false;
        for(Class c : parts.keySet()){
            if(partClass.equals(c)){
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     *
     * @param asset
     */
    public void setAsset(Asset asset){
        this.asset = asset;
    }
    
    /**
     *
     * @return
     */
    public Asset getAsset(){
        return this.asset;
    }
    
    /**
     *
     * @param color
     */
    public void setColor(int[] color){
        this.color = color;
    }
    
    /**
     *
     * @return
     */
    public int[] getColor(){
        return this.color;
    }
    
    /**
     *
     * @param r
     */
    public void setRadius(float r){
        this.radius = r;
    }
    
    /**
     *
     * @return
     */
    public float getRadius(){
        return radius;
    }

    /**
     *
     * @return
     */
    public String getID() {
        return ID.toString();
    }
    
    /**
     *
     * @return
     */
    public Entity getSource(){
        return this;
    }

    /**If the entity has a tendency of moving
     * @return the ConstantPosition
     */
    public boolean isConstantPosition() {
        return ConstantPosition;
    }

    /**Set if the entity has a tendency of moving
     * @param ConstantPosition the ConstantPosition to set
     */
    public void setConstantPosition(boolean ConstantPosition) {
        this.ConstantPosition = ConstantPosition;
    }

    /**
     * Is the entity hostile, a player or neutral?
     * player=1, neutral=0,hostile=-1
     * @return the alignment
     */
    public int getAlignment() {
        return alignment;
    }

    /**Is the entity hostile, a player or neutral?
     * player=1, neutral=0,hostile=-1
     * @param alignment the alignment to set
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
}
