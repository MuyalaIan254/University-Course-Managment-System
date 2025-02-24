public class UniversityCourseManagementSystem {
    public static void main(String[] args) {
        // Create instances
        Instructor instructor = new Instructor("Dr. Smith", "smith@university.com", "I001");
        Student student1 = new Student("John Doe", "john@university.com", "S001");
        Student student2 = new Student("Jane Doe", "jane@university.com", "S002");

        Course course = new Course("C001", "Advanced Java", instructor);
        student1.enrollInCourse(course);
        student2.enrollInCourse(course);

        course.assignGrade(student1, 85.5);
        course.assignGrade(student2, 90.0);

        // Display details
        instructor.displayDetails();
        student1.displayDetails();
        student2.displayDetails();

        // Calculate statistics
        Statistics stats = new CourseStatistics(course);
        stats.calculateStatistics();

        // Launch GUI
        new UniversityGUI();
    }
}