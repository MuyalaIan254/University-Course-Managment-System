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



public class PersonDAO {
     public DefaultTableModel getAllStudents() {
        String query = "SELECT student_id,first_name,last_name, email,phone_number,address,course_id,registration_date,is_active FROM students"; // Ensure table & columns match DB
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Email","phone_number","Address","course_id","registration","Active"}); // JTable headers

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("student_id"));
                row.add(rs.getString("first_name") + " " + rs.getString("last_name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("phone_number"));
                row.add(rs.getString("address"));
                row.add(rs.getInt("course_id"));
                row.add(rs.getDate("registration_date"));
                row.add(rs.getBoolean("is_active"));
                model.addRow(row);
              
               
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getAllLecturers() {
       String query = "SELECT lecturer_id,first_name,last_name, email,phone_number,address,is_active FROM lecturers"; // Ensure table & columns match DB
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Email","phone_number","Address","Active"}); // JTable headers

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("lecturer_id"));
                row.add(rs.getString("first_name") + " " + rs.getString("last_name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("phone_number"));
                row.add(rs.getString("address"));
                row.add(rs.getBoolean("is_active"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public boolean addStudent(String firstName, String lastName, String email, String phoneNumber, String address, int courseId) {
        String query = "INSERT INTO students (first_name, last_name, email, phone_number, address, course_id)\n" + //
                        "VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, address);
            pstmt.setInt(6, courseId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
       
    }

    public boolean addLecturer(String firstName, String lastName, String email, String phoneNumber, String address, int courseId) {
        String query = "INSERT INTO lecturers(first_name, last_name, email, phone_number, address) VALUES(?,?,?,?,?) RETURNING lecturer_id";
        String query1 = "INSERT INTO lecturer_courses(lecturer_id, course_id) VALUES(?,?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement pstmt1 = conn.prepareStatement(query1)) {
    
            conn.setAutoCommit(false);  // Start transaction
    
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, address);
            
    
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int lecturerId = rs.getInt("lecturer_id");
                pstmt1.setInt(1, lecturerId);
                pstmt1.setInt(2, courseId);
                pstmt1.executeUpdate();
            }
    
            conn.commit();  // Commit transaction
            return true;  // ✅ Now returns true on success
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Still return false on failure
        }
    }    

    public Map<String,Object> displayStudentDetails(int studentID){
        String query = "SELECT s.first_name, s.last_name, s.email, s.phone_number, s.address, c.course_name FROM students s JOIN courses c ON s.course_id = c.course_id WHERE student_id = ?";
        Map<String,Object> studentDetails = new HashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name=rs.getString("first_name")+" "+rs.getString("last_name");
                studentDetails.put("name", name);
                studentDetails.put("email", rs.getString("email"));
                studentDetails.put("phoneNumber",rs.getString("phone_number"));
                studentDetails.put("address", rs.getString("address"));
                studentDetails.put("course", rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentDetails;
    }
    public void updateDataFromStudentsTable(DefaultTableModel model) {
        String query = "UPDATE students SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, course_id = ? WHERE student_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
    
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    int id = (int) model.getValueAt(i, 0);
                    String fullName = (String) model.getValueAt(i, 1);
                    String[] nameParts = fullName.split(" ", 2);
                    String firstName = nameParts.length > 1 ? nameParts[0] : fullName;
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";
                    String email = (String) model.getValueAt(i, 2);
                    String phone = (String) model.getValueAt(i, 3);
                    String address = (String) model.getValueAt(i, 4);
                    int courseId = (int) model.getValueAt(i, 5);
    
                    // Bind values
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setString(3, email);
                    pstmt.setString(4, phone);
                    pstmt.setString(5, address);
                    pstmt.setInt(6, courseId);
                    pstmt.setInt(7, id);
    
                    pstmt.addBatch(); // Batching updates for performance
                }
    
                pstmt.executeBatch(); // Execute all updates at once
                conn.commit(); // Commit transaction
                JOptionPane.showMessageDialog(null, "Database updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating database!");
        }
    }

    public void updateDataFromLecturerTable(DefaultTableModel model) {
        String query = "UPDATE lecturers SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, is_active = ? WHERE lecturer_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
    
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    // Validate column count
                    int columnCount = model.getColumnCount();
                    if (columnCount < 6) {
                        throw new IllegalArgumentException("Table model must have at least 6 columns.");
                    }
    
                    // Retrieve values from the table model
                    int id = (int) model.getValueAt(i, 0); // Column 0: ID
                    String fullName = (String) model.getValueAt(i, 1); // Column 1: Name
                    String[] nameParts = fullName.split(" ", 2);
                    String firstName = nameParts.length > 1 ? nameParts[0] : fullName;
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";
                    String email = (String) model.getValueAt(i, 2); // Column 2: Email
                    String phone = (String) model.getValueAt(i, 3); // Column 3: Phone
                    String address = (String) model.getValueAt(i, 4); // Column 4: Address
                    boolean isActive = (boolean) model.getValueAt(i, 5); // Column 5: Active Status
    
                    // Bind values to the prepared statement
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setString(3, email);
                    pstmt.setString(4, phone);
                    pstmt.setString(5, address);
                    pstmt.setBoolean(6, isActive);
                    pstmt.setInt(7, id);
    
                    pstmt.addBatch(); // Add to batch
                }
    
                pstmt.executeBatch(); // Execute all updates at once
                conn.commit(); // Commit transaction
                JOptionPane.showMessageDialog(null, "Lecturer data updated successfully!");
    
            } catch (IllegalArgumentException e) {
                conn.rollback(); // Rollback transaction
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid table model: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating database: " + e.getMessage());
        }
    }
}