import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentManagementSystem {
    public static Map<Integer, Student> students = new HashMap<>();

    public StudentManagementSystem() {
        String fileName = "./StudentsList.json";
        try (FileReader fileReader = new FileReader(fileName)){
            int data;
            String payload = "";
            while((data = fileReader.read()) != -1) {
                payload += (char)data;
            }
            System.out.println(payload);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addStudent(String name, int rollNumber, String dob) {
        Student student = Student.getInstance(name, rollNumber, dob);
        if (student != null) {
            students.put(rollNumber, student);
            System.out.println("Student added successfully.");
        }
    }

    public static void removeStudent(int rollNumber) {
        if (students.get(rollNumber) != null) {
            students.remove(rollNumber);
        } else {
            System.out.println("Student with rollNumber=" + rollNumber + ", doesn't exist.");
        }
    }

    public static Student updateStudent(int rollNumber, String name, String dob) {
        Student student = Student.getInstance(name, rollNumber, dob);
        if(student == null) {
            return student;
        }
        if(students.get(rollNumber) != null) {
            students.put(rollNumber, student);
            System.out.println("Student Updated Successfully.");
        }
        return student;
    }

    public static Student searchStudent(int rollNumber) {
        if (students.get(rollNumber) != null) {
            return students.get(rollNumber);
        } else {
            System.out.println("Student with rollNumber=" + rollNumber + ", doesn't exist.");
            return null;
        }
    }


    public static void main(String[] args) {
        addStudent("shivam", 1, "17-08-2001");
        addStudent("akash", 2, "23-07-1998");
        updateStudent(1, "aniruddh", "19-09-1998");
        Student student = students.get(1);
        System.out.println(student.getName());

    }
}
