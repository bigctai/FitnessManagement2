package projecttwo;

public class Premium extends Family{

    private static final double PER_YEAR = 719.88;
    private static final double ONE_MONTH = 59.99;
    public Premium(String fname, String lname, Date dob, Date expire, Location location, int guestPass){
        super(fname, lname, dob, expire, location, guestPass);
    }
    @Override
    public double membershipFee(){
        double fee = PER_YEAR - ONE_MONTH;
        return fee;
    }

    public String toString(){
        return super.toString();
    }

    @Override
    protected String getClassName(){
        return "(Premium) Guess-pass remaining";
    }

    public int getGuestPass() { return guestPass; }

    public void guestIn() { guestPass--; }

    public void guestOut(){guestPass++;}
}
