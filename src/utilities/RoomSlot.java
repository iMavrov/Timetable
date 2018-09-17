package utilities;

/**
 *
 * @author Mavrov
 */
public class RoomSlot extends IntervalTree.Interval {
    
    public RoomSlot(int inputStart, int inputLength, int inputRoomIndex) {
        super(inputStart, inputStart + inputLength);

        length = inputLength;
        room = inputRoomIndex;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getRoomIndex() {
        return room;
    }
    
    private int length;
    private int room;
}
