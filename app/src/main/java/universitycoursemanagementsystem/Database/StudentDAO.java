package main.java.universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import universitycoursemanagementsystem.Database.DatabaseConnection;

public class StudentDAO {

     public DefaultTableModel getStudentTableModel() {
        String query = "SELECT id, name, email, course FROM students"; // Adjust according to your DB schema
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Email", "Course"}); // Set column headers

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("course"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

}
