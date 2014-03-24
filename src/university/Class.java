/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.List;

/**
 *
 * @author Mavrov
 */
public class Class {
    // Unique ID
    private int classID;
    
    // Subject info
    private String shortSubjectName;
    private String fullSubjectName;
    private int duration;
    
    private RoomType requiredRoomType;
    private BoardType requiredBoardType;
    private boolean requiresMultimedia;
    private int requiredComputers;
    
    private int requiredCapacity;
    
    // Info about subject/plan hierarchy here.
    
    // Connections
    private int lecturerID;
    private List<Integer> additionalLecturerIDs;
    
    private List<Integer> subgroupIDs;
}
