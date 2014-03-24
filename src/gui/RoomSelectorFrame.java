/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import university.*;

/**
 *
 * @author Mavrov
 */
public class RoomSelectorFrame extends JFrame {
    
    public static RoomSelectorFrame getInstance() {
        if (roomSelectorFrame == null) {
            roomSelectorFrame = new RoomSelectorFrame();
        }
        
        return roomSelectorFrame;
    }

    public void showRoomSelector() {
        updateList();
        setVisible(true);
    }
    
    @Override
    protected Object clone() {
        return this;
    }
    
    private static final int ROOM_SELECTOR_START_WIDTH = 480;
    private static final int ROOM_SELECTOR_START_HEIGHT = 240;
    
    private static RoomSelectorFrame roomSelectorFrame = null;
    
    private JComboBox buildingCombo;
    private JComboBox roomTypeCombo;
    private JList<Room> roomList;
        
    private RoomSelectorFrame() {
        // Init JFrame stuff
        super();
        setTitle("Избор стая");
        setSize(ROOM_SELECTOR_START_WIDTH, ROOM_SELECTOR_START_HEIGHT);
        setPreferredSize(new Dimension(ROOM_SELECTOR_START_WIDTH, ROOM_SELECTOR_START_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create UI components
        JLabel buildingLabel = new JLabel("Сграда:");
        String[] buildingOptions = FacultyType.getFacultyTypeNames();
        buildingCombo = new JComboBox(buildingOptions);
        buildingCombo.setSelectedIndex(0);
        buildingCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateList();
            }
        });
        
        JLabel roomTypeLabel = new JLabel("Тип стая:");
        String[] roomTypeOptions = RoomType.getRoomTypeNames();
        roomTypeCombo = new JComboBox(roomTypeOptions);
        roomTypeCombo.setSelectedIndex(0);
        roomTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateList();
            }
        });
        
        Room roomPrototype = new Room();
        roomPrototype.setName("123456");
        
        JLabel roomListLable = new JLabel("Резултати:");
        roomList = new JList<>();
        roomList.setPrototypeCellValue(roomPrototype);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        roomList.setVisibleRowCount(-1);
        
        JScrollPane listScrollPane = new JScrollPane(roomList);
        listScrollPane.setPreferredSize(new Dimension(400, 80));
        
        JButton editButton = new JButton("Редактирай");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editRoomAction();
            }
        });
        
        JButton cancelButton = new JButton("Отмени");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelRoomEditAction();
            }
        });    
        
        // Set GroupLayout as layout
        JPanel contentPane = new JPanel();
        
        GroupLayout groupLayout = new GroupLayout(contentPane);
        contentPane.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.LEADING, groupLayout.createSequentialGroup()
                    .addComponent(buildingLabel)
                    .addComponent(buildingCombo)
                    .addComponent(roomTypeLabel)
                    .addComponent(roomTypeCombo))
                .addComponent(listScrollPane, GroupLayout.Alignment.CENTER)
                .addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addComponent(editButton)
                    .addComponent(cancelButton)));
        
        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(buildingLabel)
                    .addComponent(buildingCombo)
                    .addComponent(roomTypeLabel)
                    .addComponent(roomTypeCombo))
                .addComponent(listScrollPane)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(cancelButton))
        );
        
        // Finalize frame
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void updateList() {
        List<Room> filteredRooms = University.getInstance().getRooms(
            FacultyType.getFacultyType(buildingCombo.getSelectedIndex()),
            RoomType.getRoomType(roomTypeCombo.getSelectedIndex()));
        
        DefaultListModel<Room> roomListModel = new DefaultListModel<>();
        for (Room room : filteredRooms) {
            roomListModel.addElement(room);
        }
        
        roomList.setModel(roomListModel);
    }
    
    private void editRoomAction() {
        if (roomList.isSelectionEmpty()) {
            return;
        }
        
        Room selectedRoom = roomList.getSelectedValue();
        RoomFormFrame.getInstance().showEditRoomForm(selectedRoom);
        
        setVisible(false);
    }
    
    private void cancelRoomEditAction() {
        setVisible(false);
    }
}
