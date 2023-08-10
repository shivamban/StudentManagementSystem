import java.awt.*;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        String instruction = Colors.blue + "Please choose one of the following options : \n" +
                "1 -> Add Student, \n2 -> Remove Student, \n3 -> Update Student, \n4 -> Search Student" +
                "\n5 -> Display All Student, \n6 -> Download Student data, \n7 -> upload students from a file, \n8 -> Quit" + Colors.reset;
        boolean isActice = true;
        int choice = 0;
        int maxAttempts = 5;
        Scanner scanner = new Scanner(System.in);
        while (isActice) {
            if (maxAttempts <= 0) {
                System.out.println(Color.red + "You have reached maximum number of attempts." + Colors.reset);
                break;
            }
            System.out.println(instruction);
            System.out.print(Colors.yellow + "please choose an Option : " + Colors.reset);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.print(Colors.red + "You need to enter a number only: (" + maxAttempts + " left)" + Colors.reset);
                scanner.nextLine();
                maxAttempts--;
                continue;
            }

            switch (choice) {
                case 1:
                    studentManagementSystem.addStudent();
                    break;
                case 2:
                    studentManagementSystem.removeStudent();
                    break;
                case 3:
                    studentManagementSystem.updateStudent();
                    break;
                case 4:
                    studentManagementSystem.searchStudent();
                    break;
                case 5:
                    studentManagementSystem.displayAllStudents();
                    break;
                case 6:
                    studentManagementSystem.downloadStudentData();
                    break;
                case 7:
                    studentManagementSystem.uploadStudentData();
                    break;
                case 8:
                    studentManagementSystem.saveStudentData();
                    isActice = false;
                    break;
                default:
                    System.out.println(Colors.red + "Entered number should be between 1-8 including 1 and 8." + Colors.reset);
                    maxAttempts--;
                    break;
            }

        }

    }
}
