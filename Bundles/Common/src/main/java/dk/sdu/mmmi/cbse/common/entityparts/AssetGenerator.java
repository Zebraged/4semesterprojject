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
    
    InputStream imagePath;
    Asset asset;
    
    public AssetGenerator(Entity source, InputStream imagePath){
        this.imagePath = imagePath;
        asset = new Asset(this.imagePath);
        source.setAsset(asset);
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        asset.changeImagePath(imagePath);
    }
    
    public void addImagePath(InputStream path){
        this.imagePath = path;
    }
    
}
