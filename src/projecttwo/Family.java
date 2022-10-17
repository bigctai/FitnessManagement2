package projecttwo;

public class Family extends Member{

    private int guestPass;
    private static final double ONE_TIME = 29.99;
    private static final double FAM_PER_MONTH = 59.99;
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