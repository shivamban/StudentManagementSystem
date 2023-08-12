import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class StudentManagementSystem {
    private final Map<Integer, Student> students = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public StudentManagementSystem() {
        System.out.println(Colors.ORANGE + "Loading the Students data from StudentsList.json ..." + Colors.reset);
        fetchStudents();
        System.out.println(Colors.ORANGE + "Loading finished" + Colors.reset);
    }

    public void fetchStudents() {
        String filename = "src/main/java/StudentsList.json";
        students.clear();
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
                try {
                    Student student1 = Student.create(student.get("name").getAsString(), student.get("id").getAsInt(), student.get("dob").getAsString());
                    students.put(student.get("id").getAsInt(), student1);
                } catch (IllegalArgumentException e) {
                    e.getMessage();
                }

            }
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadStudentData() {
        System.out.println(Colors.yellow + "please enter a filename followed by .json(json format)" + Colors.reset);
        String fileName = scanner.nextLine();
        if (!fileName.contains(".")) {
            fileName = fileName + ".json";
        }
        if (!fileName.endsWith(".json")) {
            System.out.println(Colors.red + "Provided file name is not in json format." + Colors.reset);
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
            outputStream.close();
            System.out.println(Colors.green + "You have successfully downloaded student data in " + fileName);
            System.out.println("After you stop the server, go to files/" + fileName + " to see the file." + Colors.reset);
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
            System.out.println(Colors.green + "Student data saved successfully" + Colors.reset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void uploadStudentData() throws UserQuitsException{
        String format = "{\n" + "  \"students\": [\n" + "    {\n" + "      \"name\": \"akash\",\n" + "      \"id\": 1,\n" + "      \"age\": 23,\n" + "      \"dob\": \"19-09-1999\"\n" + "    },\n" + "    {\n" + "      \"name\": \"adity chaudhary\",\n" + "      \"id\": 2,\n" + "      \"age\": 21,\n" + "      \"dob\": \"12-07-2002\"\n" + "    }\n]\n}";
        System.out.println("data format : \n" + format);
        String fileName = uploadFileAndGetPath();
        if (!fileName.endsWith(".json")) {
            System.out.println(Colors.red + "Only Json Files are accepted." + Colors.reset);
            return;
        }
        try (FileInputStream input = new FileInputStream(fileName)) {
            byte[] bytes = input.readAllBytes();
            String data = new String(bytes);
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(data, JsonObject.class);
            if (json.get("students") == null) {
                System.out.println(Colors.red + "File can not be uploaded. Please check if the data is in correct format." + Colors.reset);
            }
            JsonArray array = json.getAsJsonArray("students");
            for (int i = 0; i < array.size(); i++) {
                JsonObject jsonObject = (JsonObject) array.get(i);
                Student student;
                try {
                    student = Student.create(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsInt(), jsonObject.get("dob").getAsString());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (students.containsKey(student.getId())) {
                    Student currentStudent = students.get(student.getId());
                    System.out.println(Colors.blue + "Student with id = " + student.getId() + ", already exist. This is the current student details :");
                    System.out.println(new Gson().toJson(currentStudent));
                    System.out.println("After update, student details will be : ");
                    System.out.println(new Gson().toJson(student) + Colors.reset);
                    System.out.print(Colors.yellow + "Enter 1 to update : " + Colors.reset);
                    if (scanner.hasNextInt()) {
                        int choice = scanner.nextInt();
                        if (choice == 1) {
                            students.put(jsonObject.get("id").getAsInt(), student);
                        }
                    } else {
                        String prompt = scanner.nextLine();
                        if(StudentHelper.checkQuit(prompt)) {
                            throw new UserQuitsException(Colors.red + "User stopped the server." + Colors.reset);
                        }
                    }
                } else {
                    students.put(jsonObject.get("id").getAsInt(), student);
                }
            }
            System.out.println(Colors.green + "Students data updated from the file : " + fileName + Colors.reset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String uploadFileAndGetPath() {
        Frame frame = new Frame();
        FileDialog fileDialog = new FileDialog(frame, "Upload a File", FileDialog.LOAD);
        fileDialog.setVisible(true);
        String filePath = fileDialog.getDirectory() + fileDialog.getFile();
        frame.dispose();
        return filePath;
    }

    public void addStudent() throws UserQuitsException {
        String dob, name;
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (students.get(id) != null) {
            System.out.println(Colors.red + "Student with id : " + id + " already exists." + Colors.reset);
            return;
        }

        try {
            name = getNameFromUser();
            dob = getDobFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            Student student = Student.create(name, id, dob);
            students.put(id, student);
            System.out.println(Colors.green + "Student added successfully." + Colors.reset);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeStudent() throws UserQuitsException {
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

    public void updateStudent() throws UserQuitsException {
        String name;
        String dob;
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (students.get(id) == null) {
            System.out.println(Colors.red + "Student with id = " + id + ", doesn't exist" + Colors.reset);
        } else {
            System.out.println(Colors.green + "Current Student Details : " + new Gson().toJson(students.get(id)));
            try {
                name = getNameFromUser();
                dob = getDobFromUser();
                Student student = Student.create(name, id, dob);
                students.put(id, student);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(Colors.green + "Updated Student Details : " + new Gson().toJson(students.get(id)));
            System.out.println("Student Updated Successfully." + Colors.reset);
        }
    }

    public Student searchStudent() throws UserQuitsException {
        int id;
        try {
            id = getIdFromUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (students.get(id) != null) {
            Student student = students.get(id);
            System.out.println(Colors.green + new Gson().toJson(student) + Colors.reset);
            return student;
        } else {
            System.out.println(Colors.red + "Student with id=" + id + ", doesn't exist." + Colors.reset);
            return null;
        }
    }

    private String getNameFromUser() throws UserQuitsException {
        System.out.print(Colors.yellow + "Please enter a name : " + Colors.reset);
        String name = scanner.nextLine();
        if (StudentHelper.checkQuit(name)) {
            throw new UserQuitsException(Colors.red + "User stopped the server." + Colors.reset);
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException(Colors.red + "The name should not exceed 50 characters." + Colors.reset);
        } else if (name.isBlank()) {
            throw new IllegalArgumentException(Colors.red + "This is not a valid name" + Colors.reset);
        } else {
            return name;
        }
    }

    private int getIdFromUser() throws IllegalArgumentException, UserQuitsException {
        System.out.print(Colors.yellow + "Please enter an id : " + Colors.reset);
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            if (id < 1) {
                throw new IllegalArgumentException(Colors.red + "Please enter a positive number only." + Colors.reset);
            }
            scanner.nextLine();
            return id;
        } else {
            String prompt = scanner.nextLine();
            if (StudentHelper.checkQuit(prompt)) {
                throw new UserQuitsException(Colors.red + "User stopped the server." + Colors.reset);
            }
            throw new IllegalArgumentException(Colors.red + "Please enter a number only as Id is in number format." + Colors.reset);
        }
    }

    private String getDobFromUser() throws IllegalArgumentException, UserQuitsException {
        System.out.print(Colors.yellow + "Please enter dob (dd-mm-yyyy format only) : " + Colors.reset);
        String dob = scanner.nextLine();
        if (StudentHelper.checkQuit(dob)) {
            throw new UserQuitsException(Colors.red + "User stopped the server." + Colors.reset);
        }
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


