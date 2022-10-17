package projecttwo;

import org.junit.Test;

import static org.junit.Assert.*;

public class PremiumTest {

    private static final double MEMBER_FEE = 149.96;
    private static final double FAMILY_FEE = 209.96;
    private static final double PREMIUM_FEE = 659.89;
    private static final double VARIANCE = .01;

    @Test
    public void testMembershipFeeForStandard() {
        Member mem1 = new Member("John", "Doe", new Date("01/01/2002"), new Date("01/01/2023"), Location.BRIDGEWATER);
        assertEquals(MEMBER_FEE, mem1.membershipFee(), VARIANCE);
    }
    @Test
    public void testMembershipFeeForFamily() {
        Member mem1 = new Family("John", "Doe", new Date("01/01/2002"), new Date("01/01/2023"), Location.BRIDGEWATER, 1);
        assertEquals(FAMILY_FEE, mem1.membershipFee(), VARIANCE);
    }
    @Test
    public void testMembershipFeeForPremium() {
        Member mem1 = new Premium("John", "Doe", new Date("01/01/2002"), new Date("01/01/2023"), Location.BRIDGEWATER, 3);
        assertEquals(PREMIUM_FEE, mem1.membershipFee(), VARIANCE);
    }
}