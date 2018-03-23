/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Asset;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author Marcg
 */
public class AssetGenerator implements EntityPart{
    
    private int imageDelay = 10;
    private int delay = imageDelay;
    private int imageNumber = 0;
    private String imagePath;
    private String image;
    private Asset asset;
    private Map<String, ArrayList<String>> animation = new HashMap<>();
    
    public AssetGenerator(Entity source, String imagePath, String image){
        this.imagePath = imagePath;
        this.image = image;
        asset = new Asset(this.imagePath, this.image);
        source.setAsset(asset);
        loadAll(source);
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        asset.changeImagePath(imagePath);
        asset.changeImage(image);
    }
    
    public void addImagePath(String path){
        this.imagePath = path;
    }
    
    public void changeImage(String image){
        this.image = image;
    }
    
    public void setImageDelay(int delay){
        this.imageDelay = delay;
    }
    
    private void loadAll(Entity source){
        URL url;
        Enumeration<URL> urls = FrameworkUtil.getBundle(source.getClass()).findEntries(source.getAsset().getImagePath(), "*.png", true);
        while(urls.hasMoreElements()){
            url = urls.nextElement();
            String folder = url.getPath().substring(url.getPath().indexOf('/', url.getPath().indexOf('/') + 1) + 1, url.getPath().lastIndexOf('/'));
            String name = url.getPath().substring(url.getPath().lastIndexOf('/')+1, url.getPath().length());
            if(animation.get(folder) == null){
                animation.put(folder, new ArrayList<>());
                animation.get(folder).add(name);
            } else {
                animation.get(folder).add(name);
            }
        }
    }
    
    public void nextImage(String type){
        if(delay <= 0){
            imageNumber ++;
            if(imageNumber >= animation.get(type).size()){
                imageNumber = 0;
            }
            changeImage(animation.get(type).get(imageNumber));
            delay = imageDelay;
        } else {
            delay--;
        }
    }
    
}
