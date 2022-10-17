package projecttwo;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A Date test class that provides the JUnit testing for the Date class.
 * Provides methods for every test case applicable to the isValid method.
 * @author Chris Tai, Shreyank Yelagoila
 */
public class DateTest {

    /**
     * Tests the isValid method to see if the month is greater than 12.
     * Will return false if the month is greater than 12.
     */
    @Test
    public void testMonthGreaterThanTwelve() {
        Date date = new Date("13/23/2003");
        assertFalse(date.isValid());
    }

    /**
     * Tests the isValid method to see if the month is less than 0.
     * Will return false if the month is less than 0.
     */
    @Test
    public void testMonthLessThanZero() {
        Date date = new Date("0/23/2003");
        assertFalse(date.isValid());
    }

    /**
     * Tests the isValid method to see if the date is valid in a month with 31 days.
     * Will return true if the date is valid and false otherwise.
     */
    @Test
    public void testDaysInThirtyOneDayMonth() {
        Date date = new Date("05/32/2002");
        assertFalse(date.isValid());
        date = new Date("05/31/2002");
        assertTrue(date.isValid());
    }

    /**
     * Tests the isValid method to see if the date is valid in a month with 30 days.
     * Will return true if the date is valid and false otherwise.
     */
    @Test
    public void testDaysInThirtyDayMonth() {
        Date date = new Date("06/31/2003");
        assertFalse(date.isValid());
        date = new Date("06/30/2003");
        assertTrue(date.isValid());
    }

    /**
     * Tests the isValid method to see if the date is valid in the month of Feb during a non-leap year.
     * Will return true if the date is valid and false otherwise.
     */
    @Test
    public void testDaysInFebInNonLeapYear() {
        Date date = new Date("02/29/2003");
        assertFalse(date.isValid());
        date = new Date("02/29/1900");
        assertFalse(date.isValid());
        date = new Date("02/28/2003");
        assertTrue(date.isValid());
    }

    /**
     * Tests the isValid method to see if the date is valid in the month of Feb during a leap year.
     * Will return true if the date is valid and false otherwise.
     */
    @Test
    public void testDaysInFebLeapYear() {
        Date date = new Date("02/30/2000");
        assertFalse(date.isValid());
        date = new Date("02/29/2000");
        assertTrue(date.isValid());
        date = new Date("02/29/2004");
        assertTrue(date.isValid());
    }
}