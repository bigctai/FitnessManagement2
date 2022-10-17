package projecttwo;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {

    @Test
    public void testEarlierLastName() {
        Date dateOfBirth = new Date("01/01/2001");
        //Test case 1
        Member mem1 = new Member("John", "Campbell", dateOfBirth);
        Member mem2 = new Member("Andrew", "Donovan", dateOfBirth);
        assertEquals(-1, mem1.compareTo(mem2));
    }
    @Test
    public void testLaterLastName() {
        Date dateOfBirth = new Date("01/01/2001");
        //Test case 2
        Member mem1 = new Member("Andrew", "Donovan", dateOfBirth);
        Member mem2 = new Member("John", "Campbell", dateOfBirth);
        assertEquals(1, mem1.compareTo(mem2));
    }
    @Test
    public void testSameLastName() {
        Date dateOfBirth = new Date("01/01/2001");
        //Test case 3
        Member mem1 = new Member("Andrew", "Campbell", dateOfBirth);
        Member mem2 = new Member("John", "Campbell", dateOfBirth);
        assertEquals(-1, mem1.compareTo(mem2));
        //Test case 4
        mem1 = new Member("John", "Campbell", dateOfBirth);
        mem2 = new Member("Andrew", "Campbell", dateOfBirth);
        assertEquals(1, mem1.compareTo(mem2));
    }
    @Test
    public void testSameName(){
        Date dateOfBirth = new Date("01/01/2001");
        //Test case 5
        Member mem1 = new Member("John", "Campbell", dateOfBirth);
        Member mem2 = new Member("John", "Campbell", dateOfBirth);
        assertEquals(0, mem1.compareTo(mem2));
    }
}