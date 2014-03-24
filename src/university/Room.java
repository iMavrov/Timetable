/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 *
 * @author Mavrov
 */
public class Room {
    
    public Room() {
    
    }
    
    public Room(
        int roomID,
        FacultyType faculty, 
        String name, 
        RoomType type, 
        int capacity, 
        int computerCount, 
        BoardType board, 
        boolean hasMultimedia, 
        boolean isActive) {
        this.roomID = roomID;
        this.faculty = faculty;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.computerCount = computerCount;
        this.board = board;
        this.hasMultimedia = hasMultimedia;
        this.isActive = isActive;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public void setRoomID(int newRoomID) {
        if (roomID == -1) {
            roomID = newRoomID;
        }
    }
    
    public FacultyType getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyType faculty) {
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getComputerCount() {
        return computerCount;
    }

    public void setComputerCount(int computerCount) {
        this.computerCount = computerCount;
    }

    public BoardType getBoard() {
        return board;
    }

    public void setBoard(BoardType board) {
        this.board = board;
    }

    public boolean hasMultimedia() {
        return hasMultimedia;
    }

    public void setHasMultimedia(boolean hasMultimedia) {
        this.hasMultimedia = hasMultimedia;
    }
    
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        
        if (other instanceof Room) {
            Room otherRoom = (Room)other;
            return (name.equals(otherRoom.name) && (faculty == otherRoom.faculty));
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + faculty.ordinal() * 117;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public boolean save(BufferedWriter writer) {
        try {
            writer.write(String.valueOf(roomID));
            writer.newLine();
            writer.write(faculty.toString());
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(type.toString());
            writer.newLine();
            writer.write(String.valueOf(capacity));
            writer.newLine();
            writer.write(String.valueOf(computerCount));
            writer.newLine();
            writer.write(board.toString());
            writer.newLine();
            writer.write(String.valueOf(hasMultimedia));
            writer.newLine();
            writer.write(String.valueOf(isActive));
            writer.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
        
        return true;
    }
    
    public boolean load(BufferedReader reader) {
        try {
            roomID = Integer.valueOf(reader.readLine());
            faculty = FacultyType.valueOf(reader.readLine());
            name = reader.readLine();
            type = RoomType.valueOf(reader.readLine());
            capacity = Integer.valueOf(reader.readLine());
            computerCount = Integer.valueOf(reader.readLine());
            board = BoardType.valueOf(reader.readLine());
            hasMultimedia = Boolean.valueOf(reader.readLine());
            isActive = Boolean.valueOf(reader.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
        
        return true;
    }
    
    // Unique ID
    private int roomID;
    
    // Room info
    private FacultyType faculty;
    private String name;
    private RoomType type;
    private int capacity;
    private int computerCount;
    private BoardType board;
    private boolean hasMultimedia;
    private boolean isActive;
}
