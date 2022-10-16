package projecttwo;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {
    @Test
    public void testMonthGreaterThanTwelve() {
        Date date = new Date("13/23/2003");
        assertFalse(date.isValid());
    }
    @Test
    public void testMonthLessThanZero() {
        Date date = new Date("0/23/2003");
        assertFalse(date.isValid());
    }
    @Test
    public void testDaysInThirtyOneDayMonth() {
        Date date = new Date("05/32/2002");
        boolean expectedOutput = false;
        assertFalse(date.isValid());
        date = new Date("05/31/2002");
        assertTrue(date.isValid());
    }
    @Test
    public void testDaysInThirtyDayMonth() {
        Date date = new Date("06/31/2003");
        assertFalse(date.isValid());
        date = new Date("06/30/2003");
        assertTrue(date.isValid());
    }
    @Test
    public void testDaysInFebInNonLeapYear() {
        Date date = new Date("02/29/2003");
        assertFalse(date.isValid());
        date = new Date("02/29/1900");
        assertFalse(date.isValid());
        date = new Date("02/28/2003");
        assertTrue(date.isValid());
    }
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