package universitycoursemanagementsystem.Database;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GradeDAO {

    public void addGrade(int studentID, int unitID, int courseworkMarks ,int finalExamMarks,int semesterId) {
        String query = "INSERT INTO grades (student_id, unit_id,coursework_marks,final_exam_marks,semester_id) VALUES (?, ?, ?,?,?)"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, unitID);
            pstmt.setInt(3, courseworkMarks);
            pstmt.setInt(4, finalExamMarks);
            pstmt.setInt(5, semesterId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Grade added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
    public int currentSemesterId(){
        String query ="SELECT semester_id FROM semesters WHERE ? BETWEEN start_date AND end_date";
        int semesterId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
          
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                semesterId = rs.getInt("semester_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return semesterId;

    }

    public DefaultTableModel getGradeByStudentAndUnit() {
        String query = "SELECT student_id,unit_id,course_marks,grade FROM grades ORDER BY student_id,unit_id"; 
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Student ID", "Unit ID","Course Marks","Grade"});
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("student_id"), rs.getInt("unit_id"), rs.getInt("course_marks"), rs.getString("grade")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    public  DefaultTableModel getAvgCourseGrade(){
        String query ="SELECT u.course_id,AVG(g.course_marks) as avg_marks,"+
        "CASE "+
        "WHEN AVG(g.course_marks) >= 70 THEN 'A' "+
        "WHEN AVG(g.course_marks) >= 60 THEN 'B' "+
        "WHEN AVG(g.course_marks) >= 50 THEN 'C' "+
        "WHEN AVG(g.course_marks) >= 40 THEN 'D' "+
        "ELSE 'F'"+
        "END as grade"+
        " FROM grades g JOIN units u ON g.unit_id = u.unit_id GROUP BY u.course_id";

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Course ID", "Average Marks","Grade"});
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("course_id"), rs.getInt("avg_marks"), rs.getString("grade")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

}
