package projecttwo;

public class Premium extends Family{

    private static final double MEMBERSHIP_FEE = 0;
    public Premium(String fname, String lname, Date dob, Date expire, Location location){
        super(fname, lname, dob, expire, location);
    }
    @Override
    public double membershipFee(){
        return MEMBERSHIP_FEE;
    }
}
