/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import utilities.IObserver;
import university.UniversityClass.AddGroupEvent;
import university.UniversityClass.AddLecturerEvent;
import university.UniversityClass.RemoveGroupEvent;
import university.UniversityClass.RemoveLecturerEvent;
import university.UniversityClass.PlaceEvent;
import university.UniversityClass.DisplaceEvent;

/**
 *
 * @author Mavrov
 */
public interface IClassObserver extends IObserver<UniversityClass> {
    
    void onAddedLecturer(AddLecturerEvent event);
    
    void onRemovedLecturer(RemoveLecturerEvent event);
    
    void onAddedGroup(AddGroupEvent event);
    
    void onRemovedGroup(RemoveGroupEvent event);
    
    void onRoomPlacement(PlaceEvent event);
    
    void onRoomDisplacement(DisplaceEvent event);
}
