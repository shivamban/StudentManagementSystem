public class Student {
    private String name;
    private final int id;
    private int age;
    private String dob;

    public Student(String name, int id, String dob) {
        this.name = name;
        this.id = id;
        this.dob = dob;
        this.age = StudentHelper.getAge(dob);
    }

    public static Student getInstance(String name, int id, String dob){
        if(name.length() > 25) {
            System.out.println("Name should not exceed 25 characters");
            return null;
        }
        if(!StudentHelper.isDateInCorrectFormat(dob)) {
            System.out.println("Please provide the correct date format(dd-MM-yyyy)");
            return null;
        }
        return new Student(name, id, dob);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String dob) {
        if(StudentHelper.isDateInCorrectFormat(dob)) {
            this.age = StudentHelper.getAge(dob);
        } else {
            throw new IllegalArgumentException("please provide the correct date format(dd-MM-yyyy)");
        }

    }

    public void setDob(String dob) {
        if(StudentHelper.isDateInCorrectFormat(dob)) {
            this.dob = dob;
        } else {
            throw new IllegalArgumentException("please provide the correct date format(dd-MM-yyyy)");
        }
    }
}
