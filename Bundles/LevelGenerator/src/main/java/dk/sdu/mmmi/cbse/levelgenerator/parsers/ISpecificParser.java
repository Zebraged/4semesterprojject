/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator.parsers;

import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import java.util.Collection;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Kristian
 */
public interface ISpecificParser {
    /**
     * Parses the data as implemented in the classes that implement this
     * interface.
     * @param generators
     * @param line 
     */
    public void parse(Collection<IEntityGenerator> generators, String line);
}
