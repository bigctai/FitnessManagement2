package projecttwo;

public class Family extends Member{

    private int guestPass;
    private static final double MEMBERSHIP_FEE = 29.99;
    public Family(String fname, String lname, Date dob, Date expire, Location location, int guestPass){
        super(fname, lname, dob, expire, location);
        this.guestPass = guestPass;
    }

    @Override
    public double membershipFee(){
        return MEMBERSHIP_FEE;
    }

    @Override
    public String toString(){
        return super.toString() + ", (Family) guest-pass remaining: " + guestPass;
    }
}