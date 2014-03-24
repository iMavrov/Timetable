/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Mavrov
 */
public class MainFrame extends JFrame {
    
    public static JFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }
        
        return mainFrame;
    }
    
    @Override
    protected Object clone() {
        return this;
    }
    
    private static final int MAIN_FRAME_START_WIDTH = 1024;
    private static final int MAIN_FRAME_START_HEIGHT = 768;
    
    private static MainFrame mainFrame = null;
    
    private MainFrame() {
        // Init JFrame stuff
        super();
        setTitle("ФМИ Разписание");
        setPreferredSize(new Dimension(MAIN_FRAME_START_WIDTH, MAIN_FRAME_START_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Init JMenuBar stuff
        
        // Timetable menu
        JMenuItem newTimetableMenu = new JMenuItem("Създай ново разписание");
        newTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                newTimetableAction();
            }
        });
        
        JMenuItem openTimetableMenu = new JMenuItem("Отвори разписание");
        openTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                openTimetableAction();
            }
        });
        
        JMenuItem closeTimetableMenu = new JMenuItem("Затвори разписание");
        closeTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                closeTimetableAction();
            }
        });
        
        JMenuItem saveTimetableMenu = new JMenuItem("Запази разписание");
        saveTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveTimetableAction();
            }
        });
        
        JMenuItem saveAsTimetableMenu = new JMenuItem("Запази разписание като");
        saveAsTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveAsTimetableAction();
            }
        });
        
        JMenuItem exitTimetableMenu = new JMenuItem("Изход");
        exitTimetableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                exitTimetableAction();
            }
        });
        
        JMenu timetableMenu = new JMenu("Разписание");
        timetableMenu.add(newTimetableMenu);
        timetableMenu.addSeparator();
        timetableMenu.add(openTimetableMenu);
        timetableMenu.add(closeTimetableMenu);
        timetableMenu.addSeparator();
        timetableMenu.add(saveTimetableMenu);
        timetableMenu.add(saveAsTimetableMenu);
        timetableMenu.addSeparator();
        timetableMenu.add(exitTimetableMenu);
        
        // Add menu
        JMenuItem addDepartment = new JMenuItem("Добави катедра");
        addDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addDepartmentAction();
            }
        });
        
        JMenuItem addLecturer = new JMenuItem("Добави преподавател");
        addLecturer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addLecturerAction();
            }
        });
        
        JMenuItem addSpecialty = new JMenuItem("Добави специалност");
        addSpecialty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addSpecialtyAction();
            }
        });
        
        JMenuItem addCurriculum = new JMenuItem("Добави учебен план");
        addCurriculum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addCurriculumAction();
            }
        });
        
        JMenuItem addRoom = new JMenuItem("Добави стая");
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addRoomAction();
            }
        });

        JMenu addMenu = new JMenu("Добави");
        addMenu.add(addDepartment);
        addMenu.add(addLecturer);
        addMenu.add(addSpecialty);
        addMenu.add(addCurriculum);
        addMenu.add(addRoom);
        
        // Edit menu
        JMenuItem editDepartment = new JMenuItem("Редактирай катедра");
        editDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editDepartmentAction();
            }
        });
        
        JMenuItem editLecturer = new JMenuItem("Редактирай преподавател");
        editLecturer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editLecturerAction();
            }
        });
        
        JMenuItem editSpecialty = new JMenuItem("Редактирай специалност");
        editSpecialty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editSpecialtyAction();
            }
        });
        
        JMenuItem editCurriculum = new JMenuItem("Редактирай учебен план");
        editCurriculum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editCurriculumAction();
            }
        });
        
        JMenuItem editRoom = new JMenuItem("Редактирай стая");
        editRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editRoomAction();
            }
        });

        JMenu editMenu = new JMenu("Редактирай");
        editMenu.add(editDepartment);
        editMenu.add(editLecturer);
        editMenu.add(editSpecialty);
        editMenu.add(editCurriculum);
        editMenu.add(editRoom);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(timetableMenu);
        menuBar.add(addMenu);
        menuBar.add(editMenu);
        
        setJMenuBar(menuBar);
        
        // Set GroupLayout as layout
        JPanel contentPane = new JPanel();
        
        GroupLayout groupLayout = new GroupLayout(contentPane);
        contentPane.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        // Finalize frame
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void newTimetableAction() {}
    private void openTimetableAction() {}
    private void closeTimetableAction() {}
    private void saveTimetableAction() {}
    private void saveAsTimetableAction() {}
    private void exitTimetableAction() {}
    
    private void addDepartmentAction() {}
    private void addLecturerAction() {}
    private void addSpecialtyAction() {}
    private void addCurriculumAction() {}
    private void addRoomAction() {
        RoomFormFrame.getInstance().showNewRoomForm();
    }
    
    private void editDepartmentAction() {}
    private void editLecturerAction() {}
    private void editSpecialtyAction() {}
    private void editCurriculumAction() {}
    private void editRoomAction() {
        RoomSelectorFrame.getInstance().showRoomSelector();
    }
}
