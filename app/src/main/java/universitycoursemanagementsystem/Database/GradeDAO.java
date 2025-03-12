package universitycoursemanagementsystem.Database;

import javax.swing.JOptionPane;
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

    public void getGradeByStudent(int studentID) {
        String query = "SELECT student_id,unit_id,course_marks FROM grades WHERE student_id = ?"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getGradeByUnit(int unitID) {
        String query = "SELECT student_id,unit_id,course_marks FROM grades WHERE unit_id = ?"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, unitID);
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
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

}
