package projecttwo;

public class Family extends Member{
    public static final double ONE_TIME = 29.99;
    public static final double PER_MONTH = 59.99;

    public Family(){

    }

    public double membershipFee(){
        double fee = ONE_TIME + (3*PER_MONTH);
        return fee;
    }
}
