package projecttwo;

import java.text.DecimalFormat;

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
     * Gets the time in the format hh:mm
     *
     * @return the time as a string
     */
    public String hourAndMinute(){
        DecimalFormat formatTime = new DecimalFormat("00");
        return this.hour + ":" + formatTime.format(this.minute);
    }

}
