package universitycoursemanagementsystem.Database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class StudentDAO {

    public DefaultTableModel getStudentTableModel() {
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
                row.add(rs.getInt("phone_number"));
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
}
