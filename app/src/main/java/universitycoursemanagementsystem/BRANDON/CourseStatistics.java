class CourseStatistics {
    private Course course;

    public CourseStatistics(Course course) {
        this.course = course;
    }

    @Override
    public void calculateStatistics() {
        Map<Student, Double> grades = course.getGrades();
        double sum = 0;
        for (double grade : grades.values()) {
            sum += grade;
        }
        double average = sum / grades.size();
        System.out.println("Average grade for course " + course.getCourseName() + " is " + average);
    }
}