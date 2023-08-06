import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class StudentManagementSystem {
    private final Map<Integer, Student> students = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public StudentManagementSystem() {
        fetchStudents();
    }

    public void fetchStudents() {
        String filename = "src/main/java/StudentsList.json";
        try {
            FileInputStream input;
            input = new FileInputStream(filename);
            byte[] bytes = input.readAllBytes();
            String result = new String(bytes);
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(result, JsonObject.class);
            JsonArray array = json.getAsJsonArray("students");
            for (int i = 0; i < array.size(); i++) {
                JsonObject student = (JsonObject) array.get(i);
                Student student1 = Student.getInstance(student.get("name").getAsString(), student.get("id").getAsInt(), student.get("dob").getAsString());
                if (student1 != null) {
                    students.put(student.get("id").getAsInt(), student1);
                }
            }
            System.out.println(students);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadStudents(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("{\"students\" : [");
            Iterator<Student> iterator = students.values().iterator();
            while (iterator.hasNext()) {
                fileWriter.write(new Gson().toJson(iterator.next()));
                if (iterator.hasNext()) {
                    fileWriter.write(",");
                }
            }
            fileWriter.write("]}");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void uploadStudents() {
        String filename = "src/main/java/StudentsList.json";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("{\"students\" : [");
            Iterator<Student> iterator = students.values().iterator();
            while (iterator.hasNext()) {
                fileWriter.write(new Gson().toJson(iterator.next()));
                if (iterator.hasNext()) {
                    fileWriter.write(",");
                }
            }
            fileWriter.write("]}");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addStudent() {
        String dob, name;
        int id;
        try {
            name = getNameFromUser();
            id = getIdFromUser();
            dob = getDobFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        Student student = Student.getInstance(name, id, dob);
        if (students.get(id) == null) {
            students.put(id, student);
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student with id : " + id + " already exists.");
        }
    }

    public void removeStudent() {
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (students.get(id) != null) {
            students.remove(id);
            System.out.println("Student with id : " + id + ", removed successfully.");
        } else {
            System.out.println("Student with rollNumber=" + id + ", doesn't exist.");
        }
    }

    public Student updateStudent() {
        String name;
        String dob;
        int id;
        try {
            name = getNameFromUser();
            id = getIdFromUser();
            dob = getDobFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        Student student = Student.getInstance(name, id, dob);
        if (student == null) {
            return student;
        }
        students.computeIfPresent(id, (existingId, existinStudent) -> {
            System.out.println("Student Updated Successfully");
            return student;
        });
        return student;
    }

    public Student searchStudent() {
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (students.get(id) != null) {
            return students.get(id);
        } else {
            System.out.println("Student with rollNumber=" + id + ", doesn't exist.");
            return null;
        }
    }

    private String getNameFromUser() {
        System.out.print("Please enter a name : ");
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            throw new IllegalArgumentException("this is not a valid name");
        }
    }

    private int getIdFromUser() throws IllegalArgumentException {
        System.out.print("Please enter an id : ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();
            return id;
        } else {
            throw new IllegalArgumentException("this is not a valid Id");
        }
    }

    private String getDobFromUser() throws IllegalArgumentException {
        System.out.print("Please enter dob (dd-mm-yyyy format only) : ");
        String dob = scanner.nextLine();
        if (StudentHelper.isDateInCorrectFormat(dob)) {
            return dob;
        } else {
            throw new IllegalArgumentException("\nThis is not a valid dob\n");
        }
    }

    public void displayAllStudents() {
        for(Map.Entry<Integer, Student> entry: students.entrySet()) {
            Student student = entry.getValue();
            System.out.println("FORMAT -: ID, NAME, DOB, AGE");
            System.out.println();
            System.out.println(student.getId() + ", " + student.getName() + ", " + student.getDob() + ", " + student.getAge());
        }
    }
}

