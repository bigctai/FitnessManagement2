package projecttwo;

public class Premium extends Family{
    public static final double PER_YEAR = 719.88;
    public static final double ONE_MONTH = 59.99;
    public Premium(){

    }

    public double membershipFee(){
        double fee = PER_YEAR-ONE_MONTH;
        return fee;
    }
}
