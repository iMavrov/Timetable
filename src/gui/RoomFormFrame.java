/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import university.*;

/**
 *
 * @author Mavrov
 */
public class RoomFormFrame extends JFrame {
    
    public static RoomFormFrame getInstance() {
        if (roomFormFrame == null) {
            roomFormFrame = new RoomFormFrame();
        }
        
        return roomFormFrame;
    }
        
    public void showNewRoomForm() {
        mode = FormMode.NEW;
        roomID = -1;
        
        setTitle("Нова стая");
        buildingCombo.setSelectedIndex(0);
        roomNameField.setText("");
        roomTypeCombo.setSelectedIndex(0);
        roomCapacityField.setValue(new Integer(0));
        computerCountField.setValue(new Integer(0));
        boardTypeCombo.setSelectedIndex(0);
        hasMultimediaCheckbox.setSelected(false);
        isActiveCheckbox.setSelected(true);
        
        setVisible(true);
    }
    
    public void showEditRoomForm(Room room) {
        mode = FormMode.EDIT;
        roomID = room.getRoomID();
        
        setTitle("Стая " + room.getName());
        buildingCombo.setSelectedIndex(room.getFaculty().ordinal());
        roomNameField.setText(room.getName());
        roomTypeCombo.setSelectedIndex(room.getType().ordinal());
        roomCapacityField.setValue(new Integer(room.getCapacity()));
        computerCountField.setValue(new Integer(room.getComputerCount()));
        boardTypeCombo.setSelectedIndex(room.getBoard().ordinal());
        hasMultimediaCheckbox.setSelected(room.hasMultimedia());
        isActiveCheckbox.setSelected(room.isActive());
        
        setVisible(true);
    }
    
    @Override
    protected Object clone() {
        return this;
    }

    private static final int ROOM_FRAME_WIDTH = 640;
    private static final int ROOM_FRAME_HEIGHT = 165;
    
    private static RoomFormFrame roomFormFrame = null;
    
    private JComboBox buildingCombo;
    private JTextField roomNameField;
    private JComboBox roomTypeCombo;
    private JFormattedTextField roomCapacityField;
    private JFormattedTextField computerCountField;
    private JComboBox boardTypeCombo;
    private JCheckBox hasMultimediaCheckbox;
    private JCheckBox isActiveCheckbox;
    private FormMode mode;
    
    private int roomID;
        
    private RoomFormFrame() {
        // Init JFrame stuff
        super();
        setTitle("Стая");
        setSize(ROOM_FRAME_WIDTH, ROOM_FRAME_HEIGHT);
        setPreferredSize(new Dimension(ROOM_FRAME_WIDTH, ROOM_FRAME_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        // Create UI components
        JLabel buildingLabel = new JLabel("Сграда:");
        String[] buildingOptions = FacultyType.getFacultyTypeNames();
        buildingCombo = new JComboBox(buildingOptions);
        buildingCombo.setSelectedIndex(0);
        
        JLabel roomNameLabel = new JLabel("Име на стая:");
        roomNameField = new JTextField(16);
        
        JLabel roomTypeLabel = new JLabel("Тип стая:");
        String[] roomTypeOptions = RoomType.getRoomTypeNames();
        roomTypeCombo = new JComboBox(roomTypeOptions);
        roomTypeCombo.setSelectedIndex(0);
        
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setParseIntegerOnly(true);
        
        NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setMinimum(0);
        integerFormatter.setMaximum(Integer.MAX_VALUE);
        //integerFormatter.setAllowsInvalid(false);
        
        JLabel roomCapacityLabel = new JLabel("Капацитет:");
        roomCapacityField = new JFormattedTextField(integerFormatter);
        roomCapacityField.setColumns(4);
                
        JLabel computerCountLabel = new JLabel("Брой компютъра:");
        computerCountField = new JFormattedTextField(integerFormatter);
        computerCountField.setColumns(4);
        
        JLabel boardTypeLabel = new JLabel("Тип дъска:");
        String[] boardOptions = BoardType.getBoardTypeNames();
        boardTypeCombo = new JComboBox(boardOptions);
        boardTypeCombo.setSelectedIndex(0);
        
        JLabel hasMultimediaLabel = new JLabel("Мултимедия:");
        hasMultimediaCheckbox = new JCheckBox();
        hasMultimediaCheckbox.setSelected(false);
        
        JLabel isActiveLabel = new JLabel("Активна:");
        isActiveCheckbox = new JCheckBox();
        isActiveCheckbox.setSelected(true);
        
        JButton saveButton = new JButton("Запази");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveRoomActiom();
            }
        });
        
        JButton cancelButton = new JButton("Отмени");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelRoomAction();
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
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(buildingLabel)
                        .addComponent(roomCapacityLabel)
                        .addComponent(boardTypeLabel))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buildingCombo)
                        .addComponent(roomCapacityField)
                        .addComponent(boardTypeCombo))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(roomNameLabel)
                        .addComponent(computerCountLabel)
                        .addComponent(hasMultimediaLabel))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(roomNameField)
                        .addComponent(computerCountField)
                        .addComponent(hasMultimediaCheckbox))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(roomTypeLabel)
                        .addComponent(isActiveLabel))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(roomTypeCombo)
                        .addComponent(isActiveCheckbox)))
                .addGroup(GroupLayout.Alignment.CENTER, groupLayout.createSequentialGroup()
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
        );
        
        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(buildingLabel)
                    .addComponent(buildingCombo)
                    .addComponent(roomNameLabel)
                    .addComponent(roomNameField)
                    .addComponent(roomTypeLabel)
                    .addComponent(roomTypeCombo))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(roomCapacityLabel)
                    .addComponent(roomCapacityField)
                    .addComponent(computerCountLabel)
                    .addComponent(computerCountField))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(boardTypeLabel)
                    .addComponent(boardTypeCombo)
                    .addComponent(hasMultimediaLabel)
                    .addComponent(hasMultimediaCheckbox)
                    .addComponent(isActiveLabel)
                    .addComponent(isActiveCheckbox))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
        );
        
        // Finalize frame
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }

    private boolean checkRoomInformation() {
        String roomName = roomNameField.getText();
        if (roomName.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, 
                "Моля въведете валидно име на зала/стая!", 
                "Невалидно име", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
        
    private void saveRoomActiom() {
        boolean isRoomInfoOK = checkRoomInformation();
        if (!isRoomInfoOK) {
            return;
        }

        Room newRoom = new Room(
            roomID,
            FacultyType.getFacultyType(buildingCombo.getSelectedIndex()),
            roomNameField.getText(),
            RoomType.getRoomType(roomTypeCombo.getSelectedIndex()),
            (Integer) roomCapacityField.getValue(),
            (Integer) computerCountField.getValue(),
            BoardType.getBoardType(boardTypeCombo.getSelectedIndex()),
            hasMultimediaCheckbox.isSelected(),
            isActiveCheckbox.isSelected()
        );
        
        try {
            if (mode == FormMode.NEW) {
                University.getInstance().addNewRoom(newRoom);
            } else {
                University.getInstance().updateRoom(newRoom);
            }
            
            setVisible(false);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Стаята вече съществува", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void cancelRoomAction() {
        setVisible(false);
    }
}
