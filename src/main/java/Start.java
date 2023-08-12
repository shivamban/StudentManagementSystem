import javax.xml.crypto.Data;
import java.awt.*;
import java.util.Date;
import java.util.Scanner;

public class Start {
    public static boolean isActice = true;

    public static void main(String[] args){
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        String instruction = Colors.blue + "WRITE \"OUT\" AT ANY TIME TO QUIT." +
                "Please choose one of the following options : \n" +
                "1 -> Add Student, \n2 -> Remove Student, \n3 -> Update Student, \n4 -> Search Student" +
                "\n5 -> Display All Student, \n6 -> Download Student data, \n7 -> Upload students from a file, " +
                "\n8 -> Save current data to Storage, \n9 -> Revert all the changes, \n10 -> Quit" + Colors.reset;

        int choice = 0;
        int maxAttempts = 5;
        Scanner scanner = new Scanner(System.in);
        while (isActice) {
            if (maxAttempts <= 0) {
                System.out.println(Colors.red + "You have reached maximum number of attempts." + Colors.reset);
                break;
            }
            System.out.println(instruction);
            System.out.print(Colors.yellow + "please choose an Option : " + Colors.reset);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                String prompt = scanner.nextLine();
                if (StudentHelper.checkQuit(prompt)) {
                    System.out.println(Colors.red + "User Stopped the server in between." + Colors.reset);
                    break;
                }
                System.out.print(Colors.red + "You need to enter a number only: (" + maxAttempts + " left)" + Colors.reset);

                maxAttempts--;
                continue;
            }
            String currentProcess = "\n\n Current Process (" + new Date() + ") : ";
            try {
                switch (choice) {
                    case 1:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Adding a student ...");
                        studentManagementSystem.addStudent();
                        System.out.println(Colors.ORANGE + "Adding a student finished.\n\n");
                        break;
                    case 2:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Removing a student ...");
                        studentManagementSystem.removeStudent();
                        System.out.println(Colors.ORANGE + "Removing a student finished.\n\n");
                        break;
                    case 3:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Updating a student ...");
                        studentManagementSystem.updateStudent();
                        System.out.println(Colors.ORANGE + "Updating a student finished.\n\n");
                        break;
                    case 4:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Searching a student ...");
                        studentManagementSystem.searchStudent();
                        System.out.println(Colors.ORANGE + "Searching a student finished.\n\n");
                        break;
                    case 5:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + "Displaying students ...");
                        studentManagementSystem.displayAllStudents();
                        System.out.println(Colors.ORANGE + "Displaying students finished.\n\n");
                        break;
                    case 6:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Dowloading  students ...");
                        studentManagementSystem.downloadStudentData();
                        System.out.println(Colors.ORANGE + "Downloading students finished.\n\n");
                        break;
                    case 7:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Uploading students ...");
                        studentManagementSystem.uploadStudentData();
                        System.out.println(Colors.ORANGE + "Uploading students finished.\n\n");
                        break;
                    case 8:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Saving students ...");
                        studentManagementSystem.saveStudentData();
                        System.out.println(Colors.ORANGE + "Saving students finished.\n\n");
                        break;
                    case 9:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Reverting students ...");
                        studentManagementSystem.fetchStudents();
                        System.out.println(Colors.green + "Student data is reverted." + Colors.reset);
                        System.out.println(Colors.ORANGE + "Reverting students finished.\n\n");
                        break;
                    case 10:
                        maxAttempts = 5;
                        System.out.println(Colors.ORANGE + currentProcess + "Quitting ..");
                        studentManagementSystem.saveStudentData();
                        System.out.println(Colors.ORANGE + "Closed the student management system" + Colors.reset);
                        isActice = false;
                        break;
                    default:
                        System.out.println(Colors.red + "Entered number should be between 1-8 including 1 and 8. Remaining Attempts : " + --maxAttempts + Colors.reset);
                        break;
                }
            } catch (UserQuitsException e) {
                studentManagementSystem.saveStudentData();
                System.out.println(e.getMessage());
                break;
            }


        }

    }
}
