class Student extends Person {
    private String studentID;
    private List<Course> coursesEnrolled;

    public Student(String name, String email, String studentID) {
        super(name, email);
        this.studentID = studentID;
        this.coursesEnrolled = new ArrayList<>();
    }

    @Override
    public void displayDetails() {
        System.out.println("Student: " + name + ", Email: " + email + ", ID: " + studentID);
    }

    public void enrollInCourse(Course course) {
        coursesEnrolled.add(course);
        course.enrollStudent(this);
    }
}