public class Student {
    private String name;
    private final int id;
    private int age;
    private String dob;

    public Student(String name, int id, String dob, int age) {
        this.name = name;
        this.id = id;
        this.dob = dob;
        this.age = age;
    }

    public static Student create(String name, int id, String dob) throws IllegalArgumentException {
        int age = StudentHelper.getAge(dob);
        return new Student(name, id, dob, age);
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
}
