package universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnrollmentDAO {
    public void enrollStudentInUnit(int studentID, int unitID) {
        String query = "INSERT INTO student_units (student_id, unit_id) VALUES (?, ?)"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, unitID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void assignLecturerToUnit(int lecturerID, int unitID) {
        String query = "INSERT INTO lecturer_course (lecturer_id, course_id) VALUES (?, ?)"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, lecturerID);
            pstmt.setInt(2, unitID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getEnrollmentsByStudents(int studentID) {
        String query = "SELECT student_id,unit_id FROM student_units WHERE student_id = ?"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
