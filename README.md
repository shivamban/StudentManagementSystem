# StudentManagementSystem

Requirements:
1 : The system should allow users to add a new student to the student list. The user should provide the student's ID
(integer), name (string), and age (integer).
2 : The system should allow users to remove a student from the student list based on their ID.
3 : The system should support updating the details of an existing student. Users should be able to select a
student by their ID and update their name and age. 
4 : The system should provide an option to search for a student by their ID. Upon searching, the system should display
the student's details if found or show a message if the student is not found.
5 : The system should ensure that the student IDs are unique. If a user tries to add a student with an ID that already
exists, the system should display an appropriate error message and reject the addition.
6 : The system should provide an option to print the list of students, displaying their ID, name, and age.
7 : The system should provide an option to save the student data to a file. The user should be able to specify the
filename for the data to be saved.
8 : The system should provide an option to load student data from a file. The user should be able to specify the
filename from which to load the data. Existing student data in memory should be preserved.
9 : The system should handle exceptions appropriately. For example, it should handle file-related exceptions
(e.g., file not found) and display appropriate error messages to the user.
10 : The system should limit the length of the student name to a reasonable number of
characters (e.g., 50 characters).
11 : The system should validate user input to ensure that valid data is entered. For example, the student ID
and age should be positive integers, and the name should not be empty.
12 : The system should handle edge cases, such as empty student lists or invalid menu options, gracefully,
providing informative messages to the user.
13 : The system should have proper encapsulation and follow object-oriented principles. The Student class
should have private attributes and appropriate getter and setter methods. The StudentManagementSystem class
should encapsulate the student list and provide public methods for performing operations on the student list.
14 : Your program should keep saving data into file backup.csv
15 : insted of Age as integer pleas use date
CASES :
========
1. Add a student :
Enter student ID: 1001
Enter student name: John Smith
Enter student age: 20
Expected Result : Student added successfully.
2. Remove a student
Enter student ID: 1001
Expected Result : Student with ID 1001 removed successfully.
3. Update student details
Enter student ID: 1002
Enter new name: Alice Johnson Smith
Enter new age: 23
Expected Result :Student details updated successfully.
4. Search for a student
Enter student ID: 1003
Expected Result: Student Found:
ID: 1003, Name: Bob Anderson, Age: 21
Searching for a non-existent student
4. Search for a student
Enter student ID: 1004
Expected Result: Student with ID 1004 not found.
Printing the list of students
Input: 5. Print students
Expected Result:
Student List:
ID: 1002, Name: Alice Johnson Smith, Age: 23
ID: 1003, Name: Bob Anderson, Age: 21
ID: 1005, Name: Emma Johnson, Age: 22
6. Save student data to file
Enter filename: students.csv
Expected Result:
Data saved to file: students.csv
7. Load student data from file
Enter filename: students.csv
Expected Result:
Data loaded from file: students.csv
8. Invalid option
Expected Result:
Invalid option. Please select a valid option from the menu.
1. Add a student
Enter student ID: ABC
Enter student name:
Enter student age: 25
Expected Result:
Invalid input. Please enter a valid student ID, name, and age.
1. Add a student
Enter student ID: 1002
Enter student name: Jane Doe
Enter student age: 19
Expected Result:
Error: Student with ID 1002 already exists.
1. Add a student
Enter student ID: 1004
Enter student name: Mark Johnson
Enter student age: -5
Expected Result:
Invalid age. Please enter a positive integer.
1. Add a student
Enter student ID: 1005
Enter student name: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Enter student age: 22
Expected Result:
Invalid name. The name should not exceed 50 characters.
===================================================================
Technical requirements for the Student Management System project:
1 : The project should be implemented in Java programming language.
2 : The code should follow proper coding conventions, such as meaningful variable and method names, indentation, and
comments for clarity.
3 : The project should utilize object-oriented principles, such as encapsulation, inheritance, and polymorphism,
to ensure modularity and maintainability of the code.
4 : The project should use appropriate data structures, such as arrays, ArrayLists, or other collections, to store
and manage the student data efficiently.
5 : The user interface should be implemented using a command-line interface (CLI), allowing users to interact with
the system through the terminal/console.
6 : The project should handle exceptions and errors gracefully, providing meaningful error messages and appropriate
error handling mechanisms.
7 : Input validation should be implemented to ensure that user inputs are properly validated, preventing invalid
or malicious data from being processed.
8 : The project should utilize file handling mechanisms to save and load student data from external files,
ensuring data persistence between program executions.
9 : The code should be modular and well-organized, with logical separation of concerns and reusable functions or
methods.
10 : The project should include appropriate documentation, such as code comments or README files, explaining the
system's functionality, usage, and any dependencies or prerequisites.
11 : The project should be tested thoroughly, covering different scenarios and edge cases, to ensure the
correctness and robustness of the system's behavior.
12 :  The code should be version controlled using a version control system like Git to track changes and enable
collaboration (optional but recommended).
These technical requirements will help ensure that the project is implemented efficiently, follows best coding practices, and delivers a reliable and user-friendly Student Management System.
