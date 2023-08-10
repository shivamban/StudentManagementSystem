import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
                Student student1 = Student.create(student.get("name").getAsString(), student.get("id").getAsInt(), student.get("dob").getAsString());
                if (student1 != null) {
                    students.put(student.get("id").getAsInt(), student1);
                }
            }
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadStudentData() {
        System.out.println("please enter a filename followed by .json(json format)");
        String fileName = scanner.nextLine();
        if (!fileName.endsWith(".json")) {
            System.out.println("Provided file name is not with json format.");
            return;
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("./files/" + fileName);
            outputStream.write("{\"students\" : [".getBytes(StandardCharsets.UTF_8));
            Iterator<Student> iterator = students.values().iterator();
            while (iterator.hasNext()) {
                outputStream.write(new Gson().toJson(iterator.next()).getBytes(StandardCharsets.UTF_8));
                if (iterator.hasNext()) {
                    outputStream.write(",".getBytes(StandardCharsets.UTF_8));
                }
            }
            outputStream.write("]}".getBytes(StandardCharsets.UTF_8));
//            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveStudentData() {
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

    public void uploadStudentData() {
        System.out.println("Please provide a file's absolute path.");
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("File Chooser Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton openButton = new JButton("Open File");
            openButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(frame);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                        System.out.println("Selected file: " + selectedFilePath);
                        frame.dispose();
                    } else {
                        System.out.println("No file selected.");
                    }
                }
            });

            frame.getContentPane().add(openButton);
            frame.pack();
            frame.setVisible(true);
        });
        System.out.println("this ended here");
    }

    public void addStudent() {
        String dob, name;
        int id;
        try {
            name = getNameFromUser();
            id = getIdFromUser();
            dob = getDobFromUser();
        } catch (IllegalArgumentException e) {
            scanner.nextLine();
            System.out.println(e.getMessage());
            return;
        }
        Student student = Student.create(name, id, dob);
        if (students.get(id) == null) {
            students.put(id, student);
            System.out.println(Colors.green + "Student added successfully." + Colors.reset);
        } else {
            System.out.println(Colors.red + "Student with id : " + id + " already exists." + Colors.reset);
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
            Student student = students.get(id);
            System.out.println("Student information : " + new Gson().toJson(student));
            students.remove(id);
            System.out.println(Colors.green + "Student with id : " + id + ", removed successfully." + Colors.reset);
        } else {
            System.out.println(Colors.red + "Student with id=" + id + ", doesn't exist." + Colors.reset);
        }
    }

    public void updateStudent() {
        String name;
        String dob;
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        if(students.get(id) == null) {
            System.out.println(Colors.red + "Student with id = " + id + ", doesn't exist" + Colors.reset);
        } else {
            System.out.println(Colors.green + "Current Student Details : " + new Gson().toJson(students.get(id)));
            name = getNameFromUser();
            dob = getDobFromUser();
            Student student = Student.create(name, id, dob);
            students.put(id, student);
            System.out.println(Colors.green + "Updated Student Details : " + new Gson().toJson(students.get(id)));
            System.out.println("Student Updated Successfully." + Colors.reset);
        }
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
            Student student = students.get(id);
            System.out.println(new Gson().toJson(student));
            return student;
        } else {
            System.out.println("Student with rollNumber=" + id + ", doesn't exist.");
            return null;
        }
    }

    private String getNameFromUser() {
        System.out.print(Colors.yellow + "Please enter a name : " + Colors.reset);
        String name = scanner.nextLine();
        if (name.length() > 50) {
            throw new IllegalArgumentException(Colors.red + "The name should not exceed 50 characters." + Colors.reset);
        } else if (name.isBlank()) {
            throw new IllegalArgumentException(Colors.red + "This is not a valid name" + Colors.reset);
        } else {
            return name.toLowerCase();
        }
    }

    private int getIdFromUser() throws IllegalArgumentException {
        System.out.print(Colors.yellow + "Please enter an id : " + Colors.reset);
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();
            return id;
        } else {
            throw new IllegalArgumentException(Colors.red + "Please enter a number only as Id is in number format." + Colors.reset);
        }
    }

    private String getDobFromUser() throws IllegalArgumentException {
        System.out.print(Colors.yellow + "Please enter dob (dd-mm-yyyy format only) : " + Colors.reset);
        String dob = scanner.nextLine();
        if (StudentHelper.isDateInCorrectFormat(dob)) {
            return dob;
        } else {
            throw new IllegalArgumentException(Colors.red + "Please provide the correct date format(dd-MM-yyyy format only. Example : 01-08-2001)" + Colors.reset);
        }
    }

    public void displayAllStudents() {
        System.out.println(Colors.green);
        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            Student student = entry.getValue();
            System.out.println(student.getId() + " : " + new Gson().toJson(student));
        }
        System.out.println(Colors.reset);
    }
}


