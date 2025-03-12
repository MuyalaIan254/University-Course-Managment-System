package universitycoursemanagementsystem.Components;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;
import org.jfree.chart.ChartFactory;




public class AnalyticUtils {
    public static JFreeChart barGraph1(Map<String,Integer>studentsPerCourse){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String,Integer> entry : studentsPerCourse.entrySet()){
            dataset.setValue(entry.getValue(), "Students", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart("Students per Course", "Course", "Students", dataset, PlotOrientation.VERTICAL, false, true, false);
        return chart;
    }

    public static JFreeChart lineGraph1(Map<Integer,Double>unitAverages){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer,Double> entry : unitAverages.entrySet()){
            dataset.setValue(entry.getValue(), "Average", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createLineChart("Unit Average Grades", "Unit", "Average", dataset, PlotOrientation.VERTICAL, false, true, false);
        return chart;
    }

    public static JFreeChart lineGraph2(Map<String,Double>getStudentGradeByUnit){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String,Double> entry : getStudentGradeByUnit.entrySet()){
            dataset.setValue(entry.getValue(), "Average", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createLineChart("Student Average Grades by Unit", "Student-Unit", "Average", dataset, PlotOrientation.VERTICAL, false, true, false);
        return chart;
        
    }

    
   
}
