package universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CoursesDAO {
    public DefaultTableModel getAllCourses() {
        String query = "SELECT course_id,course_name,department FROM courses"; // Ensure table & columns match DB
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name","Department Name"}); // JTable headers

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("course_id"));
                row.add(rs.getString("course_name"));
                row.add(rs.getString("department"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public void getCourseById(int id) {
        String query = "SELECT course_id,course_name,department FROM courses WHERE course_id = ?"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Course ID: " + rs.getInt("course_id"));
                System.out.println("Course Name: " + rs.getString("course_name"));
                System.out.println("Department: " + rs.getInt("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourse(String courseName, int department) {
        String query = "INSERT INTO courses (course_name, department) VALUES (?, ?)"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseName);
            pstmt.setInt(2, department);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
