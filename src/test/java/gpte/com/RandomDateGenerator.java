package gpte.com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateGenerator {

    private static final int MIN_DAYS_DIFFERENCE = 3; // Minimum number of days difference between start and end dates

    public String generateRandomStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2019, Calendar.JANUARY, 1); // Set the start of the date range to January 1, 2020
        long startDateInMillis = cal.getTimeInMillis();
        
        // Calculate the maximum possible date in 2020, which is December 31, 2020
        cal.set(2020, Calendar.DECEMBER, 31);
        long endDateInMillis = cal.getTimeInMillis();

        // Generate a random start date between January 1 and December 31, 2020
        long randomStartDateInMillis = ThreadLocalRandom.current().nextLong(startDateInMillis, endDateInMillis);

        cal.setTimeInMillis(randomStartDateInMillis);
        return formatDate(cal.getTime());
    }

    public String generateRandomEndDate(String startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(startDate));

        // Set the end date to 2 days after the start date
        cal.add(Calendar.DAY_OF_MONTH, 2);

        return formatDate(cal.getTime());
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
}
