/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.io.File;

/**
 *
 * @author admin
 */
public class MapObj {

    private File file = new File("");

    public String findPath() {
        String path = null;
        path = file.getAbsoluteFile().getPath() + "\\assets\\Background";

        return path;
    }

}
