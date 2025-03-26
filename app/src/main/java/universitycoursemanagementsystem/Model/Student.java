package universitycoursemanagementsystem.Model;

public class Student extends Person {
        private int studentId;
        private int courseId;
        private boolean isActive;

        public Student(String firstName, String lastName, String email, String phoneNumber, String address, int courseId) {
            super(firstName, lastName, email, phoneNumber, address);
            this.courseId = courseId;
            this.isActive = true; // default to active
        }

        public int getStudentId() {
            return studentId;
        }

        public int getCourseId() {
            return courseId;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        @Override
        public String getRole() {
            return "Student";
        }
}
