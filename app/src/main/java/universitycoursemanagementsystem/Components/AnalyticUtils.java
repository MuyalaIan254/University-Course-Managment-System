package universitycoursemanagementsystem.Components;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import java.awt.Color;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeriesCollection;

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

    public static JFreeChart homeBarGraph(Map<String,Map<String,Double>> avgGradeByCourse_Semester) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String,Map<String,Double>> entry : avgGradeByCourse_Semester.entrySet()){
            for (Map.Entry<String,Double> entry2 : entry.getValue().entrySet()){
                dataset.setValue(entry2.getValue(), entry.getKey(), entry2.getKey());
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
            "Average Grade by Course and Semester",
            "Courses",
            "Average Marks",
            dataset
    );

        
        // Customizing the chart for better visualization
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.DARK_GRAY);
        plot.setOutlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.WHITE);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.ORANGE);
        renderer.setSeriesPaint(4, Color.MAGENTA);

        return chart;
    }

    public static JFreeChart gradeDistributionPieChart(DefaultPieDataset<String>dataset){
        JFreeChart chart = ChartFactory.createPieChart("Grade Distribution", dataset, true, true, false);
        return chart;
      
    }

    public static JFreeChart getAttendancePercentageGradeTrend(XYSeriesCollection dataset){
        JFreeChart chart = ChartFactory.createXYLineChart("Attendance Percentage Grade Trend", "Attendance Percentage", "Grade", dataset);
        return chart;
       
    }

}

