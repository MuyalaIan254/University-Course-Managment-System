package universitycoursemanagementsystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.general.DefaultPieDataset;

public class AnalyticsDAO {

    // Fetch average grades by unit
    public Map<Integer, Double> getUnitAverageGrades() {
        Map<Integer, Double> unitAverages = new HashMap<>();
        String query = "SELECT AVG(course_marks) AS average, unit_id FROM grades GROUP BY unit_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                unitAverages.put(rs.getInt("unit_id"), rs.getDouble("average"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return unitAverages;
    }

    // Fetch average grades by student
    public Map<Integer, Double> getStudentAverageGrades() {
        Map<Integer, Double> studentAverages = new HashMap<>();
        String query = "SELECT AVG(course_marks) AS average, student_id FROM grades GROUP BY student_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                studentAverages.put(rs.getInt("student_id"), rs.getDouble("average"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return studentAverages;
    }

    // Fetch average grades by student and unit
    public Map<String, Double> getStudentAverageGradesByUnit(int unitID) {
        Map<String, Double> studentAverages = new HashMap<>();
        String query = "SELECT AVG(course_marks) AS average, student_id, unit_id FROM grades WHERE unit_id = ? GROUP BY student_id, unit_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, unitID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String key = "Student " + rs.getInt("student_id") + " - Unit " + rs.getInt("unit_id");
                studentAverages.put(key, rs.getDouble("average"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return studentAverages;
    }

    // Fetch number of students per course
    public Map<String, Integer> getNumberOfStudentsPerCourse() {
        Map<String, Integer> studentsPerCourse = new HashMap<>();
        String query = "SELECT course_name, COUNT(student_id) AS student_count FROM students JOIN courses ON students.course_id = courses.course_id GROUP BY course_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                studentsPerCourse.put(rs.getString("course_name"), rs.getInt("student_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return studentsPerCourse;
    }

    // Fetch average grades by course
    public Map<String, Double> getStudentAverageGradesByCourse(int courseID) {
        Map<String, Double> studentAverages = new HashMap<>();
        String query = "SELECT AVG(course_marks) AS average, student_id, unit_id FROM grades WHERE unit_id IN (SELECT unit_id FROM units WHERE course_id = ?) GROUP BY student_id, unit_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String key = "Student " + rs.getInt("student_id") + " - Unit " + rs.getInt("unit_id");
                studentAverages.put(key, rs.getDouble("average"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return studentAverages;
    }

    public Map<String,Double> getStudentGradeByUnit(int StudentID){
        Map<String,Double> studentAverages = new HashMap<>();
        String query = "SELECT AVG(course_marks) AS average, unit_id FROM grades WHERE student_id = ? GROUP BY unit_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, StudentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                studentAverages.put("Unit " + rs.getInt("unit_id"), rs.getDouble("average"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return studentAverages;
    }

    public DefaultTableModel getStudentAvarageGradeInCourse(int studentID){
       
        String query = "SELECT student_id, AVG(course_marks) AS average, " +
                "CASE WHEN AVG(course_marks) >= 70 THEN 'A' " +
                "WHEN AVG(course_marks) >= 60 THEN 'B' " +
                "WHEN AVG(course_marks) >= 50 THEN 'C' " +
                "WHEN AVG(course_marks) >= 40 THEN 'D' " +
                "ELSE 'F' END AS grade " +
                "FROM grades WHERE student_id = ? GROUP BY student_id";
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Student ID","Average","Grade"});
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{rs.getInt("student_id"),rs.getDouble("average"),rs.getString("grade")});
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getRecentActivity(){
        String query ="SELECT activity_type,description,timestamp FROM recent_activity ORDER BY timestamp DESC";
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Activity","Description","Timestamp"});
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()){
            while(rs.next()){
                model.addRow(new Object[]{rs.getString("activity_type"),rs.getString("description"),rs.getTimestamp("timestamp")});
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return model;
    }

    public Map<String,Map<String,Double>> avgGradeByCourse_Semester(){
        String query = "SELECT g.semester_id, c.course_name, AVG(g.course_marks) AS average_marks " +
        "FROM grades g " +
        "JOIN units u ON g.unit_id = u.unit_id " +
        "JOIN courses c ON u.course_id = c.course_id " +
        "GROUP BY g.semester_id, c.course_name " +
        "ORDER BY g.semester_id, c.course_name";
        Map<String,Map<String,Double>> avgGradeByCourse_Semester = new HashMap<>();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()){
            while(rs.next()){
                String semester = "Semester " + rs.getInt("semester_id");
                String course = rs.getString("course_name");
                double average = rs.getDouble("average_marks");
                if(avgGradeByCourse_Semester.containsKey(semester)){
                    avgGradeByCourse_Semester.get(semester).put(course,average);
                }else{
                    Map<String,Double> courseAverage = new HashMap<>();
                    courseAverage.put(course,average);
                    avgGradeByCourse_Semester.put(semester,courseAverage);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return avgGradeByCourse_Semester;
    }

    public DefaultPieDataset<String> gradeDistribution(){
        String query ="SELECT grade,COUNT(grade) AS grade_count FROM grades GROUP BY grade";
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()){
            while(rs.next()){
                dataset.setValue(rs.getString("grade"),rs.getInt("grade_count"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return dataset;
    }

   
}