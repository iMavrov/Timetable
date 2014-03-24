/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.util.Random;

/**
 *
 * @author Mavrov
 */
public class Generator {
    public static Random getGenerator() {
        if (generator == null) {
            initializeGenerator();
        }
        
        return generator;
    }
    
    @Override
    protected Object clone() {
        return this;
    }
    
    private static final boolean USE_DEBUG_SEED = true;
    private static final long DEBUG_SEED = 0xCAFEBABEL;
    
    private static Random generator;
    
    private static void initializeGenerator() {
        if (USE_DEBUG_SEED) {
            generator = new Random(DEBUG_SEED);
        } else {
            generator = new Random();
        }
    }
    
    private Generator() {
    }
}
