package projecttwo;

import java.util.Calendar;

/**
 * A Date Class that provides the constructor for a Date object with a year, a month, and a day.
 * Provides methods for comparing equality between dates and checking if a date is valid.
 * @author Chris Tai, Shreyank Yelagoila
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public final int JAN = 1;
    public final int FEB = 2;
    public final int MAR = 3;
    public final int APR = 4;
    public final int MAY = 5;
    public final int JUN = 6;
    public final int JUL = 7;
    public final int AUG = 8;
    public final int SEP = 9;
    public final int OCT = 10;
    public final int NOV = 11;
    public final int DEC = 12;
    public final int QUADRENNIEL = 4;
    public final int CENTENNIEL = 100;
    public final int QUATERCENTENNIEL = 400;
    public final int THIRTIETH = 30;
    public final int THIRTY_FIRST = 31;
    public final int TWENTY_EIGHTH = 28;
    public final int TWENTY_NINTH = 29;
    private final int EXPIRATION = 3;


    /**
     * Constructs a Date with the current date
     */
    public Date() {
        Calendar cal = Calendar.getInstance();
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = cal.get(Calendar.MONTH);
        this.year = cal.get(Calendar.YEAR);
    } //create an object with today’s date (see Calendar class)

    /**
     * Constructs a Date from an inputted date string
     * Splits the String by "/" character to get the month, date, and year
     * @param date String with format "##/##/####"
     */
    public Date(String date) {
        String[] dateSections = date.split("/");
        this.month = Integer.parseInt(dateSections[0]);
        this.day = Integer.parseInt(dateSections[1]);
        this.year = Integer.parseInt(dateSections[2]);
    } //take “mm/dd/yyyy” and create a projectone.Date object
    @Override
    /**
     * Compares two Dates to see which one is earlier
     * @param the date to compare with the instance
     * @return 1 if the date passed in is later than the instance, 0 if
     * the dates are equal,
     * -1 if the date passed in is earlier than the instance
     */
    public int compareTo(Date date) {
        if(this.year>date.year){
            return 1;
        }
        else if(this.year==date.year){
            if(this.month>date.month){
                return 1;
            }
            else if(this.month==date.month){
                if(this.day>date.day){
                    return 1;
                }
                else if(this.day<date.day){
                    return -1;
                }
                else{
                    return 0;
                }
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }

    /**
     * Gets the year of the date
     * @return year as an integer
     */
    public int getYear(){
        return year;
    }

    /**
     * Gets the month of the date
     * @return month as an integer
     */
    public int getMonth(){
        return month;
    }

    /**
     * Gets the day of the date
     * @return day as an integer
     */
    public int getDay(){
        return day;
    }

    public void setExpire(){
        month += EXPIRATION;
    }

    /**
     * Checks if the month, day, and year are valid
     * @return true if the date exists and false otherwise
     */
    public boolean isValid() {
        if(month > DEC || month < 1){
            return false;
        } else {
            if((month == JAN || month == MAR || month == MAY || month == JUL
                    || month == AUG || month == OCT || month == DEC) &&
                    (day > THIRTY_FIRST || day < 1)){
                return false;
            } else if((month == APR || month == JUN || month == SEP ||
                    month == NOV) && (day > THIRTIETH || day < 1)){
                return false;
            } else if(month == FEB){
                boolean leap = false;
                if(year % QUADRENNIEL == 0){
                    if(year % CENTENNIEL == 0) {
                        if (year % QUATERCENTENNIEL == 0) {
                            leap = true;
                        }
                    } else {
                        leap = true;
                    }
                }
                if(leap){
                    if(day > TWENTY_NINTH || day < 1){
                        return false;
                    }
                } else {
                    if(day > TWENTY_EIGHTH || day < 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Runs all test cases to ensure that method is working properly
     * @param args the Strings passed in as input are put in args
     */
    public static void main(String[] args){
        boolean expectedOutput = false;
        //Test case 1
        Date date1 = new Date("13/23/2003");
        boolean actualOutput = date1.isValid();
        System.out.println("**Test case #1: If the value of month is greater than " +
                "12 or less than 1, will return false.");
        testResult(date1, expectedOutput, actualOutput);
        //Test case 2
        Date date2 = new Date("05/32/2002");
        actualOutput = date2.isValid();
        System.out.println("**Test case #2: If the month is Jan,Mar,May,Jul,Aug,Oct, " +
                "or Dec and day is greater than 31 or less than 1, will return false.");
        testResult(date2, expectedOutput, actualOutput);
        //Test case 3
        Date date3 = new Date("06/31/2003");
        actualOutput = date3.isValid();
        System.out.println("**Test case #3: If the month is Apr,Jun,Sep, or Nov and day " +
                "is greater than 30 or less than 1, will return false.");
        testResult(date3, expectedOutput, actualOutput);
        //Test case 4
        Date date4 = new Date("02/30/2003");
        actualOutput = date4.isValid();
        System.out.println("**Test case #4: If the month is Feb, year is not a leap year, " +
                "and day is greater than 28 or less than 1, will return false.");
        testResult(date4, expectedOutput, actualOutput);
        //Test case 5
        Date date5 = new Date("02/30/2000");
        actualOutput = date5.isValid();
        System.out.println("**Test case #5: If the month is Feb, year is a leap year, and day " +
                "is greater than 29 or less than 1, will return false.");
        testResult(date5, expectedOutput, actualOutput);
        expectedOutput = true;
        //Test case 6
        Date date6 = new Date("05/30/2002");
        actualOutput = date6.isValid();
        System.out.println("**Test case #6: If the month is Jan,Mar,May,Jul,Aug,Oct, or Dec " +
                "and day is less than or equal to 31 and greater than or equal to 1, will return true.");
        testResult(date6, expectedOutput, actualOutput);
        //Test case 7
        Date date7 = new Date("06/27/2003");
        actualOutput = date7.isValid();
        System.out.println("**Test case #7: If the month is Apr,Jun,Sep, or Nov and day is " +
                "less than or equal to 30 and greater than or equal to 1, will return true.");
        testResult(date7, expectedOutput, actualOutput);
        //Test case 8
        Date date8 = new Date("02/14/2003");
        actualOutput = date8.isValid();
        System.out.println("**Test case #8: If the month is Feb, year is not a leap year, and " +
                "day is less than or equal to 28 and greater than or equal to 1, will return true.");
        testResult(date8, expectedOutput, actualOutput);
        //Test case 9
        Date date9 = new Date("02/29/2000");
        actualOutput = date9.isValid();
        System.out.println("**Test case #9: If the month is Feb, year is a leap year, and day is " +
                "less than or equal to 29 and greater than or equal to 1, will return true.");
        testResult(date9, expectedOutput, actualOutput);

    }

    /**
     * Compares expected and actual outputs to see if the method is working properly
     * @param testDate the Date being tested
     * @param expectedOutput the output we are looking for as a result of the method
     * @param actualOutput the actual output we get as a result of the method
     */
    private static void testResult(Date testDate, boolean expectedOutput, boolean actualOutput) {
        System.out.print(testDate.getMonth() + "/" + testDate.getDay() + "/" + testDate.getYear() +
                " returns " + actualOutput);
        if (actualOutput == expectedOutput) {
            System.out.println(", PASS.\n");
        } else {
            System.out.println(", FAIL.\n");
        }
    }
}
