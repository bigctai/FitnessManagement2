package projecttwo;

public class Premium extends Family{

    private static final double PER_YEAR = 719.88;
    private static final double ONE_MONTH = 59.99;
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
