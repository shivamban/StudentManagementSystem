import java.util.Scanner;

public class Start {

    public static void main(String[] args){
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        String instruction = "Please choose one of the following options : " +
                "1 -> Add Student, 2 -> Remove Student, 3 -> Update Student, 4 -> Search Student\n" +
                "5 -> Display All Student, 6 -> Download Student data, 7 -> Save students, 8 -> Quit";
        boolean isActice = true;
        int choice = 0;
        int maxAttempts = 5;
        Scanner scanner = new Scanner(System.in);
        while(isActice) {
            if(maxAttempts <= 0) {
                System.out.println("You have reached maximum number of attempts.");
                break;
            }
            System.out.println(instruction);
            System.out.print("please enter a valid Number : ");
            if(scanner.hasNextInt()) {
                choice = scanner.nextInt();
            }else{
                System.out.print("Please enter a valid Number : ");
                scanner.nextLine();
                maxAttempts--;
                continue;
            }

            switch (choice) {
                case 1 :
                    studentManagementSystem.addStudent();
                    break;
                case 2 :
                    studentManagementSystem.removeStudent();
                    break;
                case 3 :
                    studentManagementSystem.updateStudent();
                    break;
                case 4 :
                    studentManagementSystem.searchStudent();
                    break;
                case 5 :
                    studentManagementSystem.displayAllStudents();
                    break;
                case 6 :
                    System.out.println("please enter a filename followed by .json(json format)");
                    String fileName = scanner.nextLine();
                    if(fileName.endsWith(".json")) {
                        studentManagementSystem.uploadStudents(fileName);
                    }else{
                        System.out.println("Provided file name is not with json format.");
                    }
                    break;
                case 7 :
                    studentManagementSystem.uploadStudents();
                    break;
                case 8 :
                    studentManagementSystem.uploadStudents();
                    isActice = false;
                    break;
                default :
                    System.out.println("Please enter a valid number");
                    maxAttempts--;
                    break;
            }

        }

    }
}
