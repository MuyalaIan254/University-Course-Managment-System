package universitycoursemanagementsystem.Model;

public class Instructor extends Person {
    private int lecturerId;
    private boolean isActive;

    public Instructor(String firstName, String lastName, String email, String phoneNumber, String address) {
        super(firstName, lastName, email, phoneNumber, address);
        this.isActive = true; // default to active
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
    
}
