class Instructor extends Person {
    private String instructorID;
    private List<Course> coursesTaught;

    public Instructor(String name, String email, String instructorID) {
        super(name, email);
        this.instructorID = instructorID;
        this.coursesTaught = new ArrayList<>();
    }

    @Override
    public void displayDetails() {
        System.out.println("Instructor: " + name + ", Email: " + email + ", ID: " + instructorID);
    }

    public void teachCourse(Course course) {
        coursesTaught.add(course);
    }
}