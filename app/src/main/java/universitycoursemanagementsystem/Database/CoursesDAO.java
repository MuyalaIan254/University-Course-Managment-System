package universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CoursesDAO {
    public DefaultTableModel getAllCourses() {
        String query = "SELECT course_id,course_name,department FROM courses"; 
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name","Department Name"}); 

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

    
    public void addCourse(String courseName,String department) {
        String query = "INSERT INTO courses (course_name, department) VALUES (?, ?)"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseName);
            pstmt.setString(2, department);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Course added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add course");
        }
    }

   public Map<String,Object> totalCourses() {
        String query = "SELECT COUNT(*) FROM courses"; // Ensure table & columns match DB
        Map<String,Object> totalCourses = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                totalCourses.put("total_courses", rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCourses;
   }

   public void deleteCourse(int courseID){
        String query = "DELETE FROM courses WHERE course_id = ?"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Course deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete course");
        }
   }
    
}
