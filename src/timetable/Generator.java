package timetable;

import java.util.Random;

/**
 *
 * @author Mavrov
 */
public class Generator {
    public static void initialize() {
        if (Settings.USE_DEBUG_SEED) {
            generator = new Random(Settings.DEBUG_SEED);
        } else {
            generator = new Random();
        }
    }
    
    /*
     * Returns uniformly distributed random positive byte values
     */
    public static byte getByte() {
        return (byte)generator.nextInt((int)Byte.MAX_VALUE + 1);
    }
    
    /*
     * Returns uniformly distributed random positive byte values in the [0, maxValue] range
     */
    public static byte getByte(byte maxValue) {
        return (byte)generator.nextInt((int)maxValue + 1);
    }

    /*
     * Returns uniformly distributed random positive short values
     */
    public static short getShort() {
        return (short)generator.nextInt((int)Short.MAX_VALUE + 1);
    }
    
    /*
     * Returns uniformly distributed random positive short values in the [0, maxValue] range
     */
    public static short getShort(short maxValue) {
        return (short)generator.nextInt((int)maxValue + 1);
    }
    
    /*
     * Returns uniformly distributed random positive integer values
     */
    public static int getInt() {
        return Math.abs(generator.nextInt());
    }
    
    /*
     * Returns uniformly distributed random positive integer values in the [0, maxValue) range.
     */
    public static int getInt(int maxValue) {
        return generator.nextInt(maxValue);
    }
    
    private static Random generator = null;
}
