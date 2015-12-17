/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public enum RoomType {
    UNKNOWN,
    LECTURE_HALL,
    SEMINAR_HALL,
    COMPUTER_LAB,
    SPORTS_GYM;
    
    public static String[] getRoomTypeNames() {
        if (roomTypeNames == null) {
            roomTypeNames = new String[] {
                "Лекционна зала",
                "Семинарна стая",
                "Компютърна зала",
                "Спортна зала"
            };
        }
        
        return roomTypeNames;
    }
    
    public static RoomType getRoomType(int index) {
        if (roomTypes == null) {
            roomTypes = RoomType.values();
        }
        
        return roomTypes[index];
    }
    
    private static String[] roomTypeNames = null;
    private static RoomType[] roomTypes = null;
}
