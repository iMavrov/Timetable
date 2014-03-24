/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;
import java.beans.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import university.*;

/**
 *
 * @author Mavrov
 */
public class CourseStructureFormFrame extends JFrame {
    
    public static CourseStructureFormFrame getInstance() {
        if (courseStructureFormFrame == null) {
            courseStructureFormFrame = new CourseStructureFormFrame();
        }
        
        return courseStructureFormFrame;
    }
    
    public void showNewCourseStructureForm() {
        
    }
    
    public void showEditCourseStructureForm(CourseStructure courseStructure) {
        
    }
    
    @Override
    protected Object clone() {
        return this;
    }
    
    private static final int COURSE_STRUCTURE_FRAME_WIDTH = 800;
    private static final int COURSE_STRUCTURE_FRAME_HEIGHT = 800;
    
    private static final String SPECIALTY_PANEL_NAME = "specialty";
    private static final String SUBJECT_PANEL_NAME = "subject";
    
    private static CourseStructureFormFrame courseStructureFormFrame = null;
    
    // Specialty panel members
    private JComboBox<Specialty> specialtyComboBox;
    private JFormattedTextField yearField;
    
    // Subject panel members
    private JFormattedTextField subjectFullNameField;
    private JFormattedTextField subjectCodeField;
    private JComboBox<Department> departmentCombo;
    private JFormattedTextField lectionsField;
    private JFormattedTextField seminarsField;
    private JFormattedTextField labsField;
    
    // Switchable panel
    private CardLayout cardlayout;
    private JPanel switchablePanel;
    
    // JTree subject tree
    private JTree subjectTree;
    private DefaultTreeModel subjectTreeModel;
    
    private FormMode mode;
    
    private CourseStructureFormFrame() {
         // Init JFrame stuff
        super();
        setTitle("Учебен план");
        setSize(COURSE_STRUCTURE_FRAME_WIDTH, COURSE_STRUCTURE_FRAME_HEIGHT);
        setPreferredSize(new Dimension(COURSE_STRUCTURE_FRAME_WIDTH, COURSE_STRUCTURE_FRAME_HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mode = FormMode.NEW;
        
        // Setup UI        
            // Specialty Panel
        JLabel specialtyLabel = new JLabel("Специалност");
        java.util.List<Specialty> specialties = University.getInstance().getSpecialties();
        DefaultComboBoxModel<Specialty> specialtyModel = new DefaultComboBoxModel<>();
        for (Specialty specialty : specialties) {
            specialtyModel.addElement(specialty);
        }
        
        specialtyComboBox = new JComboBox<>(specialtyModel);
        specialtyComboBox.setSelectedIndex(0);
        specialtyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                selectSpecialty();
            }
        });
        
        JLabel yearLabel = new JLabel("Випуск");
        
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setParseIntegerOnly(true);
        integerFormat.setGroupingUsed(false);
        
        NumberFormatter yearFormatter = new NumberFormatter(integerFormat);
        yearFormatter.setValueClass(Integer.class);
        yearFormatter.setMinimum(1900);
        yearFormatter.setMaximum(3000);
        yearFormatter.setCommitsOnValidEdit(true);
        
        yearField = new JFormattedTextField(yearFormatter);
        yearField.setColumns(4);
        yearField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                 selectYear();
            }
        });
        
        JPanel specialtyPanel = new JPanel();
        GroupLayout specialtyLayout = new GroupLayout(specialtyPanel);
        specialtyPanel.setLayout(specialtyLayout);
        
        specialtyLayout.setAutoCreateGaps(true);
        specialtyLayout.setAutoCreateContainerGaps(true);
        
        specialtyLayout.setHorizontalGroup(specialtyLayout.createSequentialGroup()
            .addGroup(specialtyLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(specialtyLabel)
                .addComponent(yearLabel))
            .addGroup(specialtyLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(specialtyComboBox)
                .addComponent(yearField)));
        
        specialtyLayout.setVerticalGroup(specialtyLayout.createSequentialGroup()
            .addGroup(specialtyLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(specialtyLabel)
                .addComponent(specialtyComboBox))
            .addGroup(specialtyLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(yearLabel)
                .addComponent(yearField)));
        
            // Subject Panel
        DefaultFormatter committingDefaultFormatter = new DefaultFormatter();
        committingDefaultFormatter.setCommitsOnValidEdit(true);
                
        JLabel subjectFullNameLabel = new JLabel("Пълно наименование на предмет:");
        subjectFullNameField = new JFormattedTextField(committingDefaultFormatter);
        subjectFullNameField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                setSubjectFullName();
            }
        });
        
        JLabel subjectCodeLabel = new JLabel("Код на предмет:");
        subjectCodeField = new JFormattedTextField(committingDefaultFormatter);
        subjectCodeField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                setSubjectCode();
            }
        });
        
        JLabel departmentLabel = new JLabel("Катедра:");
        
        java.util.List<Department> departments = University.getInstance().getDepartments();
        DefaultComboBoxModel<Department> departmentModel = new DefaultComboBoxModel<>();
        for (Department department : departments) {
            departmentModel.addElement(department);
        }
        
        departmentCombo = new JComboBox<>(departmentModel);
        departmentCombo.setSelectedIndex(0);
        departmentCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setSubjectDepartment();
            }
        });
        
        NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setMinimum(0);
        integerFormatter.setMaximum(Integer.MAX_VALUE);
        integerFormatter.setCommitsOnValidEdit(true);
        
        JLabel lectionsLabel = new JLabel("Лекции:");
        lectionsField = new JFormattedTextField(integerFormatter);
        lectionsField.setColumns(4);
        lectionsField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                setSubjectLectionHoursCount();
            }
        });
        
        JLabel seminarsLabel = new JLabel("Семинари:");
        seminarsField = new JFormattedTextField(integerFormatter);
        seminarsField.setColumns(4);
        seminarsField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                setSubjectSeminarHoursCount();
            }
        });
        
        JLabel labsLabel = new JLabel("Практикум:");
        labsField = new JFormattedTextField(integerFormatter);
        labsField.setColumns(4);
        labsField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                setSubjectLabHoursCount();
            }
        });
        
        JPanel subjectPanel = new JPanel();
        GroupLayout subjectLayout = new GroupLayout(subjectPanel);
        subjectPanel.setLayout(subjectLayout);
        
        subjectLayout.setAutoCreateGaps(true);
        subjectLayout.setAutoCreateContainerGaps(true);
        
        subjectLayout.setHorizontalGroup(subjectLayout.createParallelGroup()
            .addComponent(subjectFullNameLabel)
            .addComponent(subjectFullNameField)
            .addComponent(subjectCodeLabel)
            .addComponent(subjectCodeField)
            .addGroup(subjectLayout.createSequentialGroup()
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(departmentLabel)
                    .addComponent(lectionsLabel)
                    .addComponent(seminarsLabel)
                    .addComponent(labsLabel))
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(departmentCombo)
                    .addComponent(lectionsField)
                    .addComponent(seminarsField)
                    .addComponent(labsField))));
        
        subjectLayout.setVerticalGroup(subjectLayout.createSequentialGroup()
            .addComponent(subjectFullNameLabel)
            .addComponent(subjectFullNameField)
            .addComponent(subjectCodeLabel)
            .addComponent(subjectCodeField)
            .addGroup(subjectLayout.createSequentialGroup()
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(departmentLabel)
                    .addComponent(departmentCombo))
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lectionsLabel)
                    .addComponent(lectionsField))
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(seminarsLabel)
                    .addComponent(seminarsField))
                .addGroup(subjectLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labsLabel)
                    .addComponent(labsField)))
            .addContainerGap(400, 400));

        // Top level stuff
            // Switchable panel
        cardlayout = new CardLayout();
        switchablePanel = new JPanel(cardlayout);
        switchablePanel.add(specialtyPanel, SPECIALTY_PANEL_NAME);
        switchablePanel.add(subjectPanel, SUBJECT_PANEL_NAME);
        
            // Subject tree
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Специалност");
        subjectTreeModel = new DefaultTreeModel(rootNode);
        
        subjectTree = new JTree(subjectTreeModel);
        subjectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        subjectTree.setEditable(false);
        subjectTree.setRootVisible(true);
        subjectTree.setShowsRootHandles(true);
        subjectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                selectTreeNode();
            }
        });
        subjectTree.setPreferredSize(new Dimension(200, 600));

        JScrollPane treePane = new JScrollPane(subjectTree);
        
        JButton addSubjectButton = new JButton("Добави предмет");
        addSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addSubject();
            }
        });
        
        JButton removeSubjectButton = new JButton("Премахни предмет");
        removeSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                removeSubject();
            }
        });
        
        JButton saveButton = new JButton("Запази");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveCourseStructure();
            }
        });
        
        JButton cancelButton = new JButton("Отмени");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                closeCourseStructure();
            }
        });
        
        // Set GroupLayout as layout
        JPanel contentPane = new JPanel();
        
        GroupLayout groupLayout = new GroupLayout(contentPane);
        contentPane.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
            .addGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(treePane)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(addSubjectButton)
                        .addComponent(removeSubjectButton)))
                .addComponent(switchablePanel))
            .addGroup(GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
                .addComponent(saveButton)
                .addComponent(cancelButton)));
        
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup()
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(treePane)
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addSubjectButton)
                        .addComponent(removeSubjectButton)))
                .addComponent(switchablePanel))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(saveButton)
                .addComponent(cancelButton)));

        // Finalize frame
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void selectSpecialty() {
        Specialty specialty = (Specialty) specialtyComboBox.getSelectedItem();
        String specialtyName = specialty.getName();
        
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) subjectTreeModel.getRoot();
        rootNode.setUserObject(specialtyName);
        
        rootNode.removeAllChildren();
        
        int semesterCount = specialty.getSemesterCount();
        for (int i = 0; i < semesterCount; ++i) {
            DefaultMutableTreeNode semesterNode = new DefaultMutableTreeNode("Семестър " + String.valueOf(i + 1));
            rootNode.add(semesterNode);
        }
        
        subjectTreeModel.nodeChanged(rootNode);
    }
    
    private void selectYear() {
        Specialty specialty = (Specialty) specialtyComboBox.getSelectedItem();
        String specialtyName = specialty.getName();
        
        String yearText = yearField.getText();
        
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) subjectTreeModel.getRoot();
        rootNode.setUserObject(specialtyName + " " + yearText);
        
        subjectTreeModel.nodeChanged(rootNode);
    }
    
    private void selectTreeNode() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            return;
        }

        if (selectedNode.isRoot()) {
            cardlayout.show(switchablePanel, SPECIALTY_PANEL_NAME);
        } else {
            int nodeLevel = selectedNode.getLevel();
            if (nodeLevel == 2) {
                Subject subject = (Subject) selectedNode.getUserObject();
                subjectFullNameField.setText(subject.getFullName());
                subjectCodeField.setText(subject.getShortName());
                departmentCombo.setSelectedItem(subject.getDepartment());
                lectionsField.setValue(subject.getLectureHourCount());
                seminarsField.setValue(subject.getSeminarHourCount());
                labsField.setValue(subject.getLabHourCount());
                cardlayout.show(switchablePanel, SUBJECT_PANEL_NAME);
            }
        }
    }
    
    private void addSubject() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 1) {
            JOptionPane.showMessageDialog(
                this, 
                "Предмети се добавят единствено към семестри. Моля изберете семестър.", 
                "Неподходяща селекция", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Subject newSubject = new Subject();
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newSubject);
        subjectTreeModel.insertNodeInto(childNode, selectedNode, selectedNode.getChildCount());
        subjectTree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }
    
    private void removeSubject() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            JOptionPane.showMessageDialog(
                this, 
                "Моля изберете предмет за премахване.", 
                "Неподходяща селекция", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        subjectTreeModel.removeNodeFromParent(selectedNode);
    }
    
    private void setSubjectFullName() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
        
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        String subjectFullName = subjectFullNameField.getText();
        subject.setFullName(subjectFullName);
        
        subjectTreeModel.nodeChanged(selectedNode);
    }
    
    private void setSubjectCode() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
                
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
      
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        String subjectCode = subjectCodeField.getText();
        subject.setShortName(subjectCode);
    }
    
    private void setSubjectDepartment() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
                
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
      
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        Department subjectDepartment = (Department) departmentCombo.getSelectedItem();
        if (subjectDepartment == null) {
            return;
        }
        
        subject.setDepartment(subjectDepartment);
    }
    
    private void setSubjectLectionHoursCount() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
                
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
      
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        int subjectLectionHoursCount = (Integer) lectionsField.getValue();
        subject.setLectureHourCount(subjectLectionHoursCount);
    }
    
    private void setSubjectSeminarHoursCount() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
                
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
      
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        int seminarLectionHoursCount = (Integer) seminarsField.getValue();
        subject.setSeminarHourCount(seminarLectionHoursCount);
    }
    
    private void setSubjectLabHoursCount() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) subjectTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
              
        int treeLevel = selectedNode.getLevel();
        if (treeLevel != 2) {
            return;
        }
      
        Subject subject = (Subject) selectedNode.getUserObject();
        if (subject == null) {
            return;
        }
        
        int subjectLabHoursCount = (Integer) labsField.getValue();
        subject.setLabHourCount(subjectLabHoursCount);
    }
    
    private void saveCourseStructure() {
        CourseStructure newCourseStructure = new CourseStructure(
            (Specialty) specialtyComboBox.getSelectedItem(),
            (Integer) yearField.getValue());
        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) subjectTreeModel.getRoot();
        if (root == null) {
            return;
        }
        
        Enumeration semesters = root.children();
        if (semesters == null) {
            return;
        }
        
        int semesterIndex = 0;
        while (semesters.hasMoreElements()) {
            DefaultMutableTreeNode semesterNode = (DefaultMutableTreeNode) semesters.nextElement();
            if (semesterNode == null) {
                continue;
            }

            Enumeration semesterSubjects = semesterNode.children();
            if (semesterSubjects == null) {
                continue;
            }

            java.util.List<Subject> subjectList = new ArrayList<>();
            
            while (semesterSubjects.hasMoreElements()) {
                DefaultMutableTreeNode subjectNode = (DefaultMutableTreeNode) semesterSubjects.nextElement();
                if (subjectNode == null) {
                    continue;
                }

                Subject semesterSubject = (Subject) subjectNode.getUserObject();
                if (semesterSubject == null) {
                    continue;
                }

                subjectList.add(semesterSubject);
            }

            newCourseStructure.setSemesterSubjects(semesterIndex, subjectList);

            ++semesterIndex;
        }

        try {
            if (mode == FormMode.NEW) {
                University.getInstance().addNewCourseStructure(newCourseStructure);
            } else {
                University.getInstance().updateCourseStructure(newCourseStructure);
            }
            
            setVisible(false);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Учебният план вече съществува",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void closeCourseStructure() {
        setVisible(false);
    }
}
