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

/**
 *
 * @author Marcg
 */
public class AssetGenerator implements EntityPart{
    
    String imagePath;
    String image;
    Asset asset;
    
    public AssetGenerator(Entity source, String imagePath, String image){
        this.imagePath = imagePath;
        this.image = image;
        asset = new Asset(this.imagePath, this.image);
        source.setAsset(asset);
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
    
}
