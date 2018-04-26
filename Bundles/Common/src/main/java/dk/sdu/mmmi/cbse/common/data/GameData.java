package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.Camera;
import org.osgi.framework.Bundle;

/**
 *
 * @author Marcg
 */
public class GameData {

    private float delta = 1.0f;
    private int displayWidth;
    private int displayHeight;
    private int difficulty = 1;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList();
    private List<BundleObj> pluginAsset = new CopyOnWriteArrayList();
    private float camX = 0;
    private float camY = 0;
    private float zoom = 0;

    /**
     *
     * @param b
     */
    public void addBundle(Bundle b) {
        BundleObj bund = new BundleObj(b);
        pluginAsset.add(bund);
    }

    /**
     *
     * @param b
     */
    public void removeBundle(Bundle b) {
        for (BundleObj bund : pluginAsset) {
            if (bund.getBundle().getBundleId() == b.getBundleId()) {
                pluginAsset.remove(bund);
            }
        }
    }

    /**
     *
     * @return
     */
    public List<BundleObj> getBundles() {
        return pluginAsset;
    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
    
    
    
    public void setBundleObjAssetPath(Bundle bund, String assetPath) {
        for (BundleObj bundle : pluginAsset) {
            if (bundle.getBundle().getBundleId() == bund.getBundleId()) {
                bundle.setAssetPath(assetPath);
            }
        }
    }

    public String getBundleObjAssetPath(Bundle bund) {
        for (BundleObj bundle : pluginAsset) {
            if (bundle.getBundle().getBundleId() == bund.getBundleId()) {
                return bundle.getAssetPath();
            }
        }
        return null;
    }

    /**
     *
     * @param e
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     *
     * @param e
     */
    public void removeEvent(Event e) {
        events.remove(e);
    }

    /**
     *
     * @return
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     *
     * @return
     */
    public GameKeys getKeys() {
        return keys;
    }

    /**
     *
     * @param delta
     */
    public void setDelta(float delta) {
        this.delta = delta;
    }

    /**
     *
     * @return
     */
    public float getDelta() {
        return delta;
    }

    /**
     *
     * @param width
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    /**
     *
     * @return
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     *
     * @param height
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    /**
     *
     * @return
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     *
     * @return
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     *
     */
    public void increaseDifficulty() {
        this.difficulty++;
    }

    /**
     *
     */
    public void reduceDifficultry() {
        this.difficulty--;
    }

    /**
     *
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     *
     * @param <E>
     * @param type
     * @param EntityClass
     * @return
     */
    public <E extends Event> List<Event> getEvents(Class<E> type, Class<?> EntityClass) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getClass().equals(EntityClass)) {
                r.add(event);
            }
        }

        return r;
    }

    /**
     *
     * @param <E>
     * @param type
     * @return
     */
    public <E extends Event> List<Event> getEvents(Class<E> type) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type)) {
                r.add(event);
            }
        }

        return r;
    }

}
