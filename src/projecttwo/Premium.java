package projecttwo;

public class Premium extends Family{

    private static final double MEMBERSHIP_FEE = 0;
    public Premium(String fname, String lname, Date dob, Date expire, Location location, int guestPass){
        super(fname, lname, dob, expire, location, guestPass);
    }
    @Override
    public double membershipFee(){
        return MEMBERSHIP_FEE;
    }

    public String toString(){
        return super.toString();
    }
}
