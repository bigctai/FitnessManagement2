package projecttwo;

/**
 * A Premium class that is a subclass of the Family class provides the constructor for
 * a member with a first name, last name,date of birth, expiration date, where the person
 * is located, and how many guest passes they have, which are added to the MemberDatabase.
 * Provides methods for retrieving a string related to the object of this class, as well as returning fees.
 * @author Chris Tai, Shreyank Yelagoila
 */
public class Premium extends Family{

    private static final double PER_YEAR = 719.88;
    private static final double ONE_MONTH = 59.99;

    /**
     * Constructs a member with Premium membership with a first name, last name, date of birth, expiration date, location, and number of guest passes
     *
     * @param fname     the first name of the Member, saved as a String
     * @param lname     the last name of the Member, saved as a String
     * @param dob       the Member's date of birth, saved as a Date
     * @param expire    the expiration date for the Member's membership, saved as a date
     * @param location  the place where the Member is located, which contains the town, zip code, and county
     * @param guestPass the number of guest passes that the member has available to use
     */
    public Premium(String fname, String lname, Date dob, Date expire, Location location, int guestPass){
        super(fname, lname, dob, expire, location, guestPass);
    }
    @Override
    /**
     * Gets the membership fee of a premium membership
     *
     * @return the membership fee as a double value
     */
    public double membershipFee(){
        double fee = PER_YEAR - ONE_MONTH;
        return fee;
    }

    @Override
    /**
     * @return the String for number of premium guest passes remaining
     */
    public String getClassName(){
        return "(Premium) Guess-pass remaining";
    }

}
