package dk.sdu.mmmi.cbse.common.data;


import dk.sdu.mmmi.cbse.common.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    
    private float radius;
    private Map<Class, EntityPart> parts;
    private int[] color = new int[4];
    private Asset asset = null;
    
     public Entity() {
        parts = new ConcurrentHashMap<>();
    }
    
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    
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

    
    public void setAsset(Asset asset){
        this.asset = asset;
    }
    
    public Asset getAsset(){
        return this.asset;
    }
    
    public void setColor(int[] color){
        this.color = color;
    }
    
    public int[] getColor(){
        return this.color;
    }
    
    public void setRadius(float r){
        this.radius = r;
    }
    
    public float getRadius(){
        return radius;
    }

    
    public String getID() {
        return ID.toString();
    }
    
    public Entity getSource(){
        return this;
    }
}
