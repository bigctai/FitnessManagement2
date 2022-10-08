package projecttwo;

/**
 * Creates a Time constant specifying the times of classes with hour and minute variables
 *
 * @author Chris Tai, Shreyank Yelagoila
 */
public enum Time {
    MORNING(9, 30), AFTERNOON(14, 00), EVENING(18, 30);
    private final int hour;
    private final int minute;

    /**
     * Constructs a Time instance
     *
     * @param hour   hour of the time as an integer
     * @param minute minute of the time as an integer
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of the Time object
     *
     * @return hour as an integer
     */
    public int hour() {
        return this.hour;
    }

    /**
     * Gets the minute of the Time object
     *
     * @return minute as an integer
     */
    public int minute() {
        return this.minute;
    }
}
