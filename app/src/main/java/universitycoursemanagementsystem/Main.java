// This is the main class of the application. It contains the main method that runs the application. It also contains the GUI components of the application.

package universitycoursemanagementsystem;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import universitycoursemanagementsystem.Components.AnalyticUtils;
import net.miginfocom.swing.MigLayout;
import universitycoursemanagementsystem.Database.PersonDAO;
import universitycoursemanagementsystem.Database.AnalyticsDAO;
import universitycoursemanagementsystem.Dialoges.AddPersonDialogue;
import universitycoursemanagementsystem.Dialoges.AddUnitDialogue;
import universitycoursemanagementsystem.Dialoges.AddCourseDialogue;
import universitycoursemanagementsystem.Database.UnitDAO;
import universitycoursemanagementsystem.Dialoges.StudentsProfileDialog;
import universitycoursemanagementsystem.Database.GradeDAO;
import universitycoursemanagementsystem.authentication.LogIn;
import universitycoursemanagementsystem.Dialoges.SettingsDialogue;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Dimension;

import universitycoursemanagementsystem.Database.CoursesDAO;

public class Main extends javax.swing.JFrame {
    private boolean initialized = false;
   
    public Main() {
       if (!initialized) {
            initComponents();
            cardLayout = (CardLayout) contentPanel.getLayout();
            displayCountdata();
            displayRecentActivity();
            loadHomeBarGraph();
            loadAttendanceGradeTrend();
            initialized = true;
        }
        setVisible(false);
    }

    /*This are the Methods of the class
     * loadStudentData() - Load student data from the database
     * loadLecturerData() - Load lecturer data from the database
     * loadEnrollmentBarGraph() - Load the enrollment bar graph
     * loadCoursesData() - Load courses data from the database
     * loadUnitsIntoComboBox() - Load units into the combo box
     * filterComboBoxItems() - Filter combo box items
     * filterStudentsTable() - Filter students table
     * filterLecturerTable() - Filter lecturer table
     */

    private void loadStudentData() {
        PersonDAO studentDAO = new PersonDAO();
        DefaultTableModel model = studentDAO.getAllStudents();
        studentsTable.setModel(model); // Set table model
    }

    private void loadLecturerData() {
        PersonDAO lecturerDAO = new PersonDAO();
        DefaultTableModel model = lecturerDAO.getAllLecturers();
        lecturerTable.setModel(model); // Set table model
    }

    private void loadEnrollmentBarGraph(){
        coursesBarGraph1.removeAll();
        coursesBarGraph1.revalidate();
        coursesBarGraph1.repaint();

        AnalyticsDAO analyticsDAO = new AnalyticsDAO();
        Map<String,Integer> studentsPerCourse = analyticsDAO.getNumberOfStudentsPerCourse();
        JFreeChart barGraph1 = AnalyticUtils.barGraph1(studentsPerCourse);

        ChartPanel chartPanel = new ChartPanel(barGraph1);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));

        coursesBarGraph1.setLayout(new MigLayout());
        coursesBarGraph1.add(chartPanel, "dock center"); 
    }
  
    private void loadCoursesData() {
        CoursesDAO courseDAO = new CoursesDAO();
        DefaultTableModel model = courseDAO.getAllCourses();
        coursesTable.setModel(model); // Set table model
    }

   private void loadUnitsIntoComboBox() {
    UnitDAO unitDAO = new UnitDAO();
    Map<Integer, String> units = unitDAO.getAllUnits();
    List<String> unitNames = new ArrayList<>(units.values());

    // Set the combo box model with all unit names
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(unitNames.toArray(new String[0]));
    unitsComboBox.setModel(model);
    unitsComboBox.setEditable(true);

    // Add a KeyListener to the combo box's editor component
    JTextField editor = (JTextField) unitsComboBox.getEditor().getEditorComponent();
    editor.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            String text = editor.getText().toLowerCase();
            filterComboBoxItems(text, unitNames);
        }
    });
}

    private void filterComboBoxItems(String searchText, List<String> allItems) {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String item : allItems) {
            if (item.toLowerCase().contains(searchText)) {
            model.addElement(item);
        }
        }
        unitsComboBox.setModel(model);
        unitsComboBox.setSelectedItem(searchText);
        unitsComboBox.showPopup();
    }

    

    private DefaultTableModel emptyTableModel(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Student ID");
        model.addColumn("Coursework Marks");
        model.addColumn("Final Exam Marks");
        return model;
    }

    private void filterStudentsTable(String searchTerm){
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) studentsTable.getModel());
        studentsTable.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(searchTerm));
    }

    private void filterLecturerTable(String searchTerm){
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) lecturerTable.getModel());
        lecturerTable.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(searchTerm));
    }

    private void displayCountdata(){
        PersonDAO personDAO = new PersonDAO();
        UnitDAO unitDAO = new UnitDAO();
        CoursesDAO courseDAO = new CoursesDAO();

        Map<String,Object> students = personDAO.totalStudents();
        Map<String,Object> lecturers = personDAO.totalLecturers();
        Map<String,Object> courses = courseDAO.totalCourses();
        Map<String,Object> units = unitDAO.totalUnits();

        studentsCount.setText(students.get("total_students").toString());
        lecturersCount.setText(lecturers.get("total_lecturers").toString());
        coursesCount.setText(courses.get("total_courses").toString());
        UnitsCount.setText(units.get("total_units").toString());
        
    }

    private void displayRecentActivity(){
        AnalyticsDAO analyticsDAO = new AnalyticsDAO();
        DefaultTableModel model = analyticsDAO.getRecentActivity();
        recentActivityTable.setModel(model);
    }

    private void loadHomeBarGraph(){
        
        homeBarGraphPanel.removeAll();
        homeBarGraphPanel.revalidate();
        homeBarGraphPanel.repaint();

        AnalyticsDAO analyticsDAO = new AnalyticsDAO();
        Map<String,Map<String,Double>> avgGradeByCourse_Semester = analyticsDAO.avgGradeByCourse_Semester();

        JFreeChart chart = AnalyticUtils.homeBarGraph(avgGradeByCourse_Semester);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1554,469));

        homeBarGraphPanel.setLayout(new MigLayout());
        homeBarGraphPanel.add(chartPanel, "dock center");

        Dimension size = attendanceGradePlane.getSize();
        System.out.println("Panel size: " + size.width + "x" + size.height);

    }

    public void loadGradesPieChart(){
        gradesPieChart.removeAll();
        gradesPieChart.revalidate();
        gradesPieChart.repaint();

        AnalyticsDAO analyticsDAO = new AnalyticsDAO();
        DefaultPieDataset<String> dataset = analyticsDAO.gradeDistribution();
        JFreeChart chart = AnalyticUtils.gradeDistributionPieChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(551, 398));

        gradesPieChart.setLayout(new MigLayout());
        gradesPieChart.add(chartPanel, "dock center");
        
    }

    public void loadAttendanceGradeTrend(){
            SwingWorker<XYDataset, Void> worker = new SwingWorker<>() {
            @Override
            protected XYDataset doInBackground() throws Exception {
                AnalyticsDAO analyticsDAO = new AnalyticsDAO();
                return analyticsDAO.getAttendancePercentageGradeTrend(); // Fetch dataset in background
            }

            @Override
            protected void done() {
                try {
                    XYDataset dataset = get();
                    if(dataset != null){
                        attendanceGradePlane.removeAll();
                        attendanceGradePlane.revalidate();
                        attendanceGradePlane.repaint();

                        JFreeChart chart = AnalyticUtils.getAttendancePercentageGradeTrend((XYSeriesCollection) dataset);
                        ChartPanel chartPanel = new ChartPanel(chart);
                        chartPanel.setPreferredSize(new java.awt.Dimension(1486,471));

                        attendanceGradePlane.setLayout(new MigLayout());
                        attendanceGradePlane.add(chartPanel, "dock center");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        };
        worker.execute();

    }

    private void loadGradesTable(){
        GradeDAO gradeDAO = new GradeDAO();
        DefaultTableModel model = gradeDAO.getGradeByStudentAndUnit();
        gradesTable.setModel(model);
    }

    private void loadAvgCourseGradeTable(){
        GradeDAO gradeDAO = new GradeDAO();
        DefaultTableModel model = gradeDAO.getAvgCourseGrade();
        avgCourseGradeTable.setModel(model);
    }

    private void filterGradesTable(String searchTerm){
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) gradesTable.getModel());
        gradesTable.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(searchTerm));
    }

   
    /*End of major class functions
     * Any additional functions should be added there
     */
   
   // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminDashboard = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        homeButton = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        coursesButton = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        studentsButton = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        gradesButton = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        analyticsButton = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        settingsButton = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        contentPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        studentsCount = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        coursesCount = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        lecturersCount = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        UnitsCount = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        recentActivityTable = new javax.swing.JTable();
        homeBarGraphPanel = new javax.swing.JPanel();
        coursesPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        unitsTable = new javax.swing.JTable();
        addCourseButton = new javax.swing.JButton();
        deleteCourse = new javax.swing.JButton();
        addUnitButton = new javax.swing.JButton();
        removeUnitButton = new javax.swing.JButton();
        studentsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentsTable = new javax.swing.JTable();
        search1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        addStudentButton = new javax.swing.JButton();
        editStudentsButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        gradesPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        gradesInputTable = new javax.swing.JTable();
        submitButton = new javax.swing.JButton();
        unitsComboBox = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        gradesTable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        avgCourseGradeTable = new javax.swing.JTable();
        editGradeTable = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        gradesSearch = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        analyticsPanel = new javax.swing.JPanel();
        coursesBarGraph1 = new javax.swing.JPanel();
        gradesPieChart = new javax.swing.JPanel();
        attendanceGradePlane = new javax.swing.JPanel();
        lecturerPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lecturerTable = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        search2 = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        addLecturerButton = new javax.swing.JButton();
        editLecturerTable = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Maven University");
        getContentPane().setLayout(new java.awt.CardLayout());

        adminDashboard.setPreferredSize(new java.awt.Dimension(1930, 1023));

        sidebar.setBackground(new java.awt.Color(60, 49, 71));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-university-100.png"))); // NOI18N

        homeButton.setText("Home");
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-home-30.png"))); // NOI18N

        coursesButton.setText("Courses");
        coursesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coursesButtonMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-e-learning-30.png"))); // NOI18N

        studentsButton.setText("Students");
        studentsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentsButtonMouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-students-35.png"))); // NOI18N

        gradesButton.setText("Grades");
        gradesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gradesButtonMouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-grade-30.png"))); // NOI18N

        analyticsButton.setText("Analytics");
        analyticsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                analyticsButtonMouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-analytics-30.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-log-out-30.png"))); // NOI18N

        jLabel9.setText("Log Out");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu Sans", 3, 24)); // NOI18N
        jLabel5.setText("Maven");

        jLabel13.setText("Lecturer");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-lecturer-30.png"))); // NOI18N

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-settings-30.png"))); // NOI18N

        settingsButton.setText("Settings");
        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator5)
                                .addGroup(sidebarLayout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(analyticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(sidebarLayout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(gradesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(sidebarLayout.createSequentialGroup()
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(sidebarLayout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(settingsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(coursesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(sidebarLayout.createSequentialGroup()
                                        .addGap(0, 6, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sidebarLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coursesButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addComponent(gradesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(analyticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settingsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );

        contentPanel.setLayout(new java.awt.CardLayout());

        jLabel14.setFont(new java.awt.Font("Ubuntu Mono", 3, 24)); // NOI18N
        jLabel14.setText("Welcome, Admin!");

        jPanel2.setBackground(new java.awt.Color(0, 128, 128));

        jLabel20.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        jLabel20.setText("        Total Students      :");

        studentsCount.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(studentsCount, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(studentsCount, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(228, 161, 27));

        jLabel22.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        jLabel22.setText("        Total Courses     :");

        coursesCount.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        coursesCount.setText("               ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(coursesCount, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(coursesCount, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 169, 165));

        jLabel24.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        jLabel24.setText("        Total Lecturers      :");

        lecturersCount.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        lecturersCount.setText("               ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(lecturersCount, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lecturersCount, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 140, 0));

        jLabel26.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        jLabel26.setText("               Total Units      :");

        UnitsCount.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        UnitsCount.setText("               ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(UnitsCount, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(UnitsCount, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        recentActivityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Activity", "Description", "Timestamp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(recentActivityTable);

        homeBarGraphPanel.setBackground(new java.awt.Color(102, 102, 0));

        javax.swing.GroupLayout homeBarGraphPanelLayout = new javax.swing.GroupLayout(homeBarGraphPanel);
        homeBarGraphPanel.setLayout(homeBarGraphPanelLayout);
        homeBarGraphPanelLayout.setHorizontalGroup(
            homeBarGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1554, Short.MAX_VALUE)
        );
        homeBarGraphPanelLayout.setVerticalGroup(
            homeBarGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(homeBarGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(82, 82, 82))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addComponent(homeBarGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        contentPanel.add(homePanel, "homeContent");

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Course ID", "Course Name", "Department"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        coursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coursesTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(coursesTable);

        jLabel11.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        jLabel11.setText("Courses");

        jLabel17.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        jLabel17.setText("Units by course");

        unitsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Unit ID", "Unit Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(unitsTable);

        addCourseButton.setText("Add Course");
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseButtonActionPerformed(evt);
            }
        });

        deleteCourse.setText("Delete Course");
        deleteCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCourseActionPerformed(evt);
            }
        });

        addUnitButton.setText("Add Unit");
        addUnitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUnitButtonActionPerformed(evt);
            }
        });

        removeUnitButton.setText("Remove  Unit");
        removeUnitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeUnitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coursesPanelLayout = new javax.swing.GroupLayout(coursesPanel);
        coursesPanel.setLayout(coursesPanelLayout);
        coursesPanelLayout.setHorizontalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coursesPanelLayout.createSequentialGroup()
                        .addComponent(addCourseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(deleteCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(coursesPanelLayout.createSequentialGroup()
                            .addComponent(addUnitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(48, 48, 48)
                            .addComponent(removeUnitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        coursesPanelLayout.setVerticalGroup(
            coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursesPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coursesPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCourseButton)
                            .addComponent(deleteCourse)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUnitButton)
                    .addComponent(removeUnitButton))
                .addContainerGap(238, Short.MAX_VALUE))
        );

        contentPanel.add(coursesPanel, "coursesContent");

        studentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Email", "Phone number", "Address", "Course ID", "Registration date", "Is_Active"
            }
        ));
        studentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(studentsTable);

        search1.setBackground(new java.awt.Color(60, 63, 65));
        search1.setText("Search");
        search1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        search1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                search1FocusGained(evt);
            }
        });
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
            }
        });
        search1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search1KeyPressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-search-25.png"))); // NOI18N

        jSeparator9.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));

        addStudentButton.setText("Add");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        editStudentsButton.setText("Edit");
        editStudentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editStudentsButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        jLabel3.setText("Students");

        javax.swing.GroupLayout studentsPanelLayout = new javax.swing.GroupLayout(studentsPanel);
        studentsPanel.setLayout(studentsPanelLayout);
        studentsPanelLayout.setHorizontalGroup(
            studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(917, 917, 917)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator9)
                            .addComponent(search1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(studentsPanelLayout.createSequentialGroup()
                        .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(studentsPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(editStudentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(69, 69, 69))))
        );
        studentsPanelLayout.setVerticalGroup(
            studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentsPanelLayout.createSequentialGroup()
                .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentsPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStudentButton)
                    .addComponent(editStudentsButton))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        contentPanel.add(studentsPanel, "studentsContent");

        gradesInputTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Student ID", "Coursework Marks", "Final Exam Marks"
            }
        ) {
            Class<?>[] types = new Class<?>[] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane5.setViewportView(gradesInputTable);

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        unitsComboBox.setEditable(true);
        unitsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        unitsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitsComboBoxActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel18.setText("Enter grades");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(unitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(unitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(submitButton)
                .addGap(10, 10, 10))
        );

        gradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Unit ID", "Course Marks", "Grade"
            }
        ));
        jScrollPane7.setViewportView(gradesTable);

        avgCourseGradeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Unit Name", "Mark", "Grade"
            }
        ));
        jScrollPane8.setViewportView(avgCourseGradeTable);

        editGradeTable.setText("Edit");
        editGradeTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGradeTableActionPerformed(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-search-25.png"))); // NOI18N

        gradesSearch.setBackground(new java.awt.Color(60, 63, 65));
        gradesSearch.setText("Settings");
        gradesSearch.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        gradesSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gradesSearchFocusGained(evt);
            }
        });
        gradesSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gradesSearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout gradesPanelLayout = new javax.swing.GroupLayout(gradesPanel);
        gradesPanel.setLayout(gradesPanelLayout);
        gradesPanelLayout.setHorizontalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gradesSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gradesPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane8))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gradesPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editGradeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(gradesPanelLayout.createSequentialGroup()
                                .addGap(593, 593, 593)
                                .addComponent(jSeparator10)))))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        gradesPanelLayout.setVerticalGroup(
            gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradesPanelLayout.createSequentialGroup()
                .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gradesPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(gradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(gradesSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editGradeTable)))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        contentPanel.add(gradesPanel, "gradesContent");

        coursesBarGraph1.setBackground(new java.awt.Color(204, 204, 204));
        coursesBarGraph1.setLayout(new java.awt.BorderLayout());

        gradesPieChart.setBackground(new java.awt.Color(255, 102, 51));

        javax.swing.GroupLayout gradesPieChartLayout = new javax.swing.GroupLayout(gradesPieChart);
        gradesPieChart.setLayout(gradesPieChartLayout);
        gradesPieChartLayout.setHorizontalGroup(
            gradesPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );
        gradesPieChartLayout.setVerticalGroup(
            gradesPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        attendanceGradePlane.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout attendanceGradePlaneLayout = new javax.swing.GroupLayout(attendanceGradePlane);
        attendanceGradePlane.setLayout(attendanceGradePlaneLayout);
        attendanceGradePlaneLayout.setHorizontalGroup(
            attendanceGradePlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        attendanceGradePlaneLayout.setVerticalGroup(
            attendanceGradePlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout analyticsPanelLayout = new javax.swing.GroupLayout(analyticsPanel);
        analyticsPanel.setLayout(analyticsPanelLayout);
        analyticsPanelLayout.setHorizontalGroup(
            analyticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(analyticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(attendanceGradePlane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addComponent(coursesBarGraph1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(gradesPieChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        analyticsPanelLayout.setVerticalGroup(
            analyticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(analyticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coursesBarGraph1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(gradesPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addComponent(attendanceGradePlane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        contentPanel.add(analyticsPanel, "analyticsContent");

        lecturerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Lecturer ID", "First Name", "Last Name", "Email", "Phone Number", "Address", "Course ID", "is Active"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(lecturerTable);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-search-25.png"))); // NOI18N

        search2.setBackground(new java.awt.Color(60, 63, 65));
        search2.setText("Search");
        search2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        search2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                search2FocusGained(evt);
            }
        });
        search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search2ActionPerformed(evt);
            }
        });
        search2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search2KeyPressed(evt);
            }
        });

        addLecturerButton.setText("Add");
        addLecturerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLecturerButtonActionPerformed(evt);
            }
        });

        editLecturerTable.setText("Edit");
        editLecturerTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editLecturerTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lecturerPanelLayout = new javax.swing.GroupLayout(lecturerPanel);
        lecturerPanel.setLayout(lecturerPanelLayout);
        lecturerPanelLayout.setHorizontalGroup(
            lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lecturerPanelLayout.createSequentialGroup()
                .addGroup(lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(lecturerPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addLecturerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editLecturerTable, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(lecturerPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1456, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, lecturerPanelLayout.createSequentialGroup()
                            .addGap(1164, 1164, 1164)
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(search2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                                .addComponent(jSeparator11)))))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        lecturerPanelLayout.setVerticalGroup(
            lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lecturerPanelLayout.createSequentialGroup()
                .addGroup(lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lecturerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(search2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lecturerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addLecturerButton)
                    .addComponent(editLecturerTable))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        contentPanel.add(lecturerPanel, "lecturerContent");

        javax.swing.GroupLayout adminDashboardLayout = new javax.swing.GroupLayout(adminDashboard);
        adminDashboard.setLayout(adminDashboardLayout);
        adminDashboardLayout.setHorizontalGroup(
            adminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminDashboardLayout.createSequentialGroup()
                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        adminDashboardLayout.setVerticalGroup(
            adminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(adminDashboard, "admin");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void coursesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coursesButtonMouseClicked
         cardLayout.show(contentPanel,"coursesContent");   
         loadCoursesData();
    }//GEN-LAST:event_coursesButtonMouseClicked

    private void studentsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsButtonMouseClicked
        cardLayout.show(contentPanel, "studentsContent");
        loadStudentData();
       
    }//GEN-LAST:event_studentsButtonMouseClicked

    private void gradesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gradesButtonMouseClicked
        cardLayout.show(contentPanel, "gradesContent");
        loadGradesTable();
        loadAvgCourseGradeTable();
        loadUnitsIntoComboBox();
    }//GEN-LAST:event_gradesButtonMouseClicked

    private void analyticsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analyticsButtonMouseClicked
       cardLayout.show(contentPanel, "analyticsContent");
       loadEnrollmentBarGraph();
       loadGradesPieChart();
    }//GEN-LAST:event_analyticsButtonMouseClicked

    private void search1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search1FocusGained
       search1.setText("");
    }//GEN-LAST:event_search1FocusGained

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        AddPersonDialogue addPersonDialogue = new AddPersonDialogue(this, true);
        addPersonDialogue.setVisible(true);
        addPersonDialogue.toFront();
    }//GEN-LAST:event_addStudentButtonActionPerformed


    private void editStudentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editStudentsButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
        PersonDAO personDAO = new PersonDAO();
        personDAO.updateDataFromStudentsTable(model);
    }//GEN-LAST:event_editStudentsButtonActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
       cardLayout.show(contentPanel, "lecturerContent");
       loadLecturerData();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void coursesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coursesTableMouseClicked
        if(evt.getClickCount() == 2){
            int row = coursesTable.getSelectedRow();
            int courseID = (int) coursesTable.getValueAt(row, 0);
            UnitDAO unitDAO = new UnitDAO();
            DefaultTableModel model = unitDAO.getUnitsByCourse(courseID);
            unitsTable.setModel(model);
            coursesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }//GEN-LAST:event_coursesTableMouseClicked

    private void addCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseButtonActionPerformed
        AddCourseDialogue addCourseDialogue = new AddCourseDialogue(this, true);
        addCourseDialogue.setVisible(true);
        addCourseDialogue.toFront();
    }//GEN-LAST:event_addCourseButtonActionPerformed

    private void addUnitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUnitButtonActionPerformed
        AddUnitDialogue addUnitDialogue = new AddUnitDialogue(this, true);
        addUnitDialogue.setVisible(true);
        addUnitDialogue.toFront();
    }//GEN-LAST:event_addUnitButtonActionPerformed

    private void studentsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsTableMouseClicked
        if(evt.getClickCount()==2){
            int row = studentsTable.getSelectedRow();
            int studentID = (int) studentsTable.getValueAt(row, 0);
            StudentsProfileDialog studentsProfileDialogue = new StudentsProfileDialog(this, true, studentID);
            studentsProfileDialogue.setVisible(true);
            studentsProfileDialogue.toFront();
        }
    }//GEN-LAST:event_studentsTableMouseClicked

    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
        String searchTerm = search1.getText().trim(); 
        filterStudentsTable(searchTerm);
         
    }//GEN-LAST:event_search1ActionPerformed

    private void search1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String searchTerm = search1.getText().trim();
            filterStudentsTable(searchTerm);
        }
    }//GEN-LAST:event_search1KeyPressed

    private void search2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search2FocusGained
       search2.setText("");
    }//GEN-LAST:event_search2FocusGained

    private void search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search2ActionPerformed
        String searchTerm= search2.getText().trim();
        filterLecturerTable(searchTerm);
    }//GEN-LAST:event_search2ActionPerformed

    private void search2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String searchTerm = search2.getText().trim();
            filterLecturerTable(searchTerm);
        }
    }//GEN-LAST:event_search2KeyPressed

    private void addLecturerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLecturerButtonActionPerformed
        AddPersonDialogue addPersonDialogue = new AddPersonDialogue(this, true);
        addPersonDialogue.setVisible(true);
        addPersonDialogue.toFront();
    }//GEN-LAST:event_addLecturerButtonActionPerformed

    private void editLecturerTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editLecturerTableActionPerformed
        DefaultTableModel model = (DefaultTableModel) lecturerTable.getModel();
        PersonDAO personDAO = new PersonDAO();
        personDAO.updateDataFromLecturerTable(model);
    }//GEN-LAST:event_editLecturerTableActionPerformed

    private void unitsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitsComboBoxActionPerformed
        
    }//GEN-LAST:event_unitsComboBoxActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        String selectedUnitName = (String) unitsComboBox.getSelectedItem();
        int unitID=-1;
        UnitDAO unitDAO = new UnitDAO();
        Map<Integer, String> units = unitDAO.getAllUnits();
        for(Map.Entry<Integer, String> entry: units.entrySet()){
            if(entry.getValue().equals(selectedUnitName)){
                unitID = entry.getKey();
                break;
            }
        }
        GradeDAO gradeDAO = new GradeDAO();

        int semesterId = gradeDAO.currentSemesterId();
        
        for(int i=0; i<gradesInputTable.getRowCount(); i++){
           
            int studentID=(Integer) gradesInputTable.getValueAt(i, 0);
            int courseworkMarks=(Integer) gradesInputTable.getValueAt(i, 1);
            int finalExamMarks = (Integer) gradesInputTable.getValueAt(i, 2);

            gradeDAO.addGrade(studentID, unitID, courseworkMarks, finalExamMarks,semesterId);
            
        }
        JOptionPane.showMessageDialog(this, "Grades added successfully");

        DefaultTableModel emptyTableModel= emptyTableModel();
        gradesInputTable.setModel(emptyTableModel);
            
    }//GEN-LAST:event_submitButtonActionPerformed

    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseClicked
        cardLayout.show(contentPanel,"homeContent");
    }//GEN-LAST:event_homeButtonMouseClicked

    private void settingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsButtonMouseClicked
        SettingsDialogue settingsDialogue = new SettingsDialogue(this, true);
        settingsDialogue.setVisible(true);
        settingsDialogue.toFront();
    }//GEN-LAST:event_settingsButtonMouseClicked

    private void gradesSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gradesSearchFocusGained
        gradesSearch.setText("");
    }//GEN-LAST:event_gradesSearchFocusGained

    private void editGradeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGradeTableActionPerformed
        
    }//GEN-LAST:event_editGradeTableActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {                                       
        this.dispose();
        LogIn logIn = new LogIn(null, true);
        logIn.setVisible(true);
        
    } 
    
    private void deleteCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCourseActionPerformed
        int row = coursesTable.getSelectedRow();
        int courseID = (int) coursesTable.getValueAt(row, 0);
        CoursesDAO courseDAO = new CoursesDAO();
        courseDAO.deleteCourse(courseID);
        DefaultTableModel model = courseDAO.getAllCourses();
        coursesTable.setModel(model);
    }//GEN-LAST:event_deleteCourseActionPerformed
    
    private void removeUnitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeUnitButtonActionPerformed
        int row = unitsTable.getSelectedRow();
        int unitID = (int) unitsTable.getValueAt(row, 0);
        UnitDAO unitDAO = new UnitDAO();
        unitDAO.deleteUnit(unitID);
        DefaultTableModel model = unitDAO.getUnitsByCourse(unitID);
        unitsTable.setModel(model);
    }//GEN-LAST:event_removeUnitButtonActionPerformed

    private void gradesSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gradesSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String searchTerm = gradesSearch.getText().trim();
            filterGradesTable(searchTerm);
        }
    }//GEN-LAST:event_gradesSearchKeyPressed

   
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            
            LogIn logIn = new LogIn(null, true);
            logIn.setVisible(true);
            
            if(logIn.isAuthenticated()) {
               
                main.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel UnitsCount;
    private javax.swing.JButton addCourseButton;
    private javax.swing.JButton addLecturerButton;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton addUnitButton;
    private javax.swing.JPanel adminDashboard;
    private javax.swing.JLabel analyticsButton;
    private javax.swing.JPanel analyticsPanel;
    private javax.swing.JPanel attendanceGradePlane;
    private javax.swing.JTable avgCourseGradeTable;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel coursesBarGraph1;
    private javax.swing.JLabel coursesButton;
    private javax.swing.JLabel coursesCount;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton deleteCourse;
    private javax.swing.JButton editGradeTable;
    private javax.swing.JButton editLecturerTable;
    private javax.swing.JButton editStudentsButton;
    private javax.swing.JLabel gradesButton;
    private javax.swing.JTable gradesInputTable;
    private javax.swing.JPanel gradesPanel;
    private javax.swing.JPanel gradesPieChart;
    private javax.swing.JTextField gradesSearch;
    private javax.swing.JTable gradesTable;
    private javax.swing.JPanel homeBarGraphPanel;
    private javax.swing.JLabel homeButton;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel lecturerPanel;
    private javax.swing.JTable lecturerTable;
    private javax.swing.JLabel lecturersCount;
    private javax.swing.JTable recentActivityTable;
    private javax.swing.JButton removeUnitButton;
    private javax.swing.JTextField search1;
    private javax.swing.JTextField search2;
    private javax.swing.JLabel settingsButton;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel studentsButton;
    private javax.swing.JLabel studentsCount;
    private javax.swing.JPanel studentsPanel;
    private javax.swing.JTable studentsTable;
    private javax.swing.JButton submitButton;
    private javax.swing.JComboBox<String> unitsComboBox;
    private javax.swing.JTable unitsTable;
    // End of variables declaration//GEN-END:variables
    private CardLayout cardLayout;
    
    
}
