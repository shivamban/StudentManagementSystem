import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class StudentHelper {

    public static final boolean isDateInCorrectFormat(String dob) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            dateFormatter.parse(dob);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static final int getAge(String dob) throws IllegalArgumentException {

        LocalDate dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        int age = period.getYears();
        if(age < 5) {
            throw new IllegalArgumentException(Colors.red + "Age should atleast be 5 years. Age Provided : " + age + Colors.reset);
        }
        return age;
    }

    public static final boolean checkQuit(String prompt) {
        return prompt.equalsIgnoreCase("OUT");
    }
}
