package projecttwo;

public class Family extends Member{

    private static final double MEMBERSHIP_FEE = 29.99;
    public Family(String fname, String lname, Date dob, Date expire, Location location){
        super(fname, lname, dob, expire, location);
    }

    @Override
    public double membershipFee(){
        return MEMBERSHIP_FEE;
    }
}