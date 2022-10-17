package projecttwo;

/**
 * A Family class that is a subclass of the Member class provides the constructor for
 * a member with a first name, last name,date of birth, expiration date, where the person
 * is located, and how many guest passes they have, which are added to the MemberDatabase.
 * Provides methods for retrieving the attributes of an object of this class, converting a
 * member to a String, as well as returning fees.
 * @author Chris Tai, Shreyank Yelagoila
 */
public class Family extends Member{

    private int guestPass;
    private static final double ONE_TIME = 29.99;
    private static final double FAM_PER_MONTH = 59.99;

    /**
     * Constructs a member with Family membership with a first name, last name, date of birth, expiration date, location, and number of guest passes
     *
     * @param fname     the first name of the Member, saved as a String
     * @param lname     the last name of the Member, saved as a String
     * @param dob       the Member's date of birth, saved as a Date
     * @param expire    the expiration date for the Member's membership, saved as a date
     * @param location  the place where the Member is located, which contains the town, zip code, and county
     * @param guestPass the number of guest passes that the member has available to use
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location, int guestPass){
        super(fname, lname, dob, expire, location);
        this.guestPass = guestPass;
    }

    @Override
    /**
     * Gets the membership fee of a standard membership
     *
     * @return the membership fee as a double value
     */
    public double membershipFee(){
        double fee = ONE_TIME + (3 * FAM_PER_MONTH);
        return fee;
    }

    /**
     * Converts the Family object to a string with all attributes printed
     *
     * @return the string of the Family object
     */
    @Override
    public String toString(){
        return super.toString() + ", " + getClassName() + ": " + guestPass;
    }

    /**
     * @return the String for number of family guest passes remaining
     */
    public String getClassName(){
        return "(Family) guest-pass remaining";
    }

    /**
     * Gets the number of guest passes that a member has
     *
     * @return the number of guest passes as an int value
     */
    public int getGuestPass() { return guestPass; }

    /**
     * Checks a guest in by subtracting 1 from the number of guest passes remaining
     */
    public void guestIn() { guestPass--; }

    /**
     * Checks a guest out by adding 1 to the number of guest passes remaining
     */
    public void guestOut(){guestPass++;}
}