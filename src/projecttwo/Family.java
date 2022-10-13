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
    public double membershipFee(){
        double fee = ONE_TIME + (3 * FAM_PER_MONTH);
        return fee;
    }

    @Override
    public String toString(){
        return super.toString() + ", (Premium) guest-pass remaining: " + guestPass;
    }
}