package universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class UnitDAO {
   
    public Map<Integer,String> getAllUnits(){
        Map<Integer,String> units = new HashMap<>();
        String query = "SELECT unit_id,unit_name FROM units";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int unitID = rs.getInt("unit_id");
                String unitName = rs.getString("unit_name");
                units.put(unitID, unitName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return units;
    }

    public DefaultTableModel getUnitsByCourse(int courseID) {
        String query = "SELECT unit_id,unit_name FROM units WHERE course_id = ?"; // Ensure table & columns match DB
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name"}); // JTable headers

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("unit_id"));
                row.add(rs.getString("unit_name"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

   

    public void addUnit(String unitName, int courseID, boolean isCommon) {
        String query = "INSERT INTO units (unit_name, course_id, is_common) VALUES (?, ?, ?)"; // Ensure table & columns match DB

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, unitName);
            pstmt.setInt(2, courseID);
            pstmt.setBoolean(3, isCommon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> totalUnits() {
        String query = "SELECT COUNT(*) FROM units"; // Ensure table & columns match DB
        Map<String,Object> totalUnits = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                totalUnits.put("total_units", rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalUnits;
    }
    
}
