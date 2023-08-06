import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class StudentHelper {

    public static boolean isDateInCorrectFormat(String dob){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            dateFormatter.parse(dob);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static final int getAge(String dob) {
        try {
            LocalDate dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(dateOfBirth, currentDate);
            return period.getYears();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return 0;
    }
}
