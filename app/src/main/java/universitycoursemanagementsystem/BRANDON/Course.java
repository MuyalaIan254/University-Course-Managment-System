class Course {
    private String courseID;
    private String courseName;
    private Instructor instructor;
    private List<Student> studentsEnrolled;
    private Map<Student, Double> grades;

    public Course(String courseID, String courseName, Instructor instructor) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.instructor = instructor;
        this.studentsEnrolled = new ArrayList<>();
        this.grades = new HashMap<>();
        instructor.teachCourse(this);
    }

    public void enrollStudent(Student student) {
        studentsEnrolled.add(student);
    }

    public void assignGrade(Student student, double grade) {
        grades.put(student, grade);
    }

    public List<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public Map<Student, Double> getGrades() {
        return grades;
    }
}