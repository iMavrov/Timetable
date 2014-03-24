/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public enum BoardType {
    NO_BOARD,
    BLACKBOARD,
    WHITEBOARD;
    
    public static String[] getBoardTypeNames() {
        if (boardTypeNames == null) {
            boardTypeNames = new String[] {
                "Без дъска",
                "Черна дъска",
                "Бяла дъска"
            };
        }
        
        return boardTypeNames;
    }
    
    public static BoardType getBoardType(int index) {
        if (cachedValues == null) {
            cachedValues = values();
        }
        
        return cachedValues[index];
    }
    
    private static String[] boardTypeNames = null;
    private static BoardType[] cachedValues = null;
}
