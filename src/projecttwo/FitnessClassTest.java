package projecttwo;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class FitnessClassTest {
    private MemberDatabase memData = new MemberDatabase();
    private static final ClassSchedule classes = new ClassSchedule();
    private static final FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER",
            "PILATES", Location.BRIDGEWATER, new Member[]{});

    @Before
    public void clearDatabase(){
        classes.addClass(testClass);
        memData = new MemberDatabase();
        for(int i = 0; i < classes.getNumOfClasses(); i++){
            if(classes.returnList()[i].getSize() > 0) {
                int size = classes.returnList()[i].getSize();
                for(int j = 0; j < classes.returnList()[i].getSize(); j++) {
                    classes.returnList()[i].getParticipants()[j] = null;
                }
                for(int k = 0; k < size; k++){
                    classes.returnList()[i].decrementSize();
                }
            }
            classes.returnList()[i].getGuests().clear();
        }
    }

    /**
     * Tests the checkInMember and dropMem methods to see if the dob is invalid.
     * Will return -10 if dob is invalid.
     * Will return 0 if dob is valid
     */
    @Test
    public void testInvalidDate() {
        Member invalidMem = new Member ("John", "Doe", new Date("1/32/2000"), new Date("1/30/2023"), Location.BRIDGEWATER);
        memData.add(invalidMem);
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, new Member[]{});
        assertEquals(-10, testClass.dropMem(invalidMem));
        assertEquals(-10, testClass.checkInMember(invalidMem, classes));
        Member validMem = new Member ("John", "Doe", new Date("1/31/2000"), new Date("1/30/2023"), Location.BRIDGEWATER);
        memData.add(validMem);
        assertEquals(0, testClass.checkInMember(validMem, classes));
        assertEquals(0, testClass.dropMem(validMem));
    }

    /**
     * Tests the checkInMember method to see if the member is in the database.
     * Will return -1 if member is not in the database.
     * Will return 0 if member is in database
     */
    @Test
    public void testNotInDatabaseCheckInMember() {
        Member notInDatabase = new Member("Jane", "Doe", new Date("1/30/2000"));
        //Retrieves expiration date and location from database, else returns the same member
        notInDatabase = memData.getFullDetails(notInDatabase);
        assertEquals(-1, testClass.checkInMember(notInDatabase, classes));
        Member inDatabase = new Member("Jane", "Doe", new Date("1/30/2000"), new Date("1/30/2023"), Location.BRIDGEWATER);
        memData.add(inDatabase);
        inDatabase = memData.getFullDetails(inDatabase);
        assertEquals(0, testClass.checkInMember(inDatabase, classes));
    }

    /**
     * Tests the checkInMember method to see if the membership has expired.
     * Will return -2 if the membership has expired.
     * Will return 0 if the membership has not expired.
     */
    @Test
    public void testMembershipExpiredCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expired = new Date("2/15/2020");
        Date notYetExpired = new Date("2/15/2023");
        Member memExpired = new Member("John", "Doe", dob, expired, Location.BRIDGEWATER);
        Member memNotExpired = new Member("Jane", "Doe", dob, notYetExpired, Location.BRIDGEWATER);
        memData.add(memExpired);
        memData.add(memNotExpired);
        assertEquals(-2, testClass.checkInMember(memExpired, classes));
        assertEquals(0, testClass.checkInMember(memNotExpired, classes));
    }

    /**
     * Tests the checkInMember method to see if the member with standard membership is checking into a class at a different location.
     * Will return -3 if the member is at the wrong location.
     * Will return 0 if the member is at the correct location.
     */
    @Test
    public void testWrongLocationCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member invalidMem = new Member("John", "Doe", dob, expire, Location.EDISON);
        Member validMem = new Member("Jane", "Doe", dob, expire, Location.BRIDGEWATER);
        memData.add(invalidMem);
        memData.add(validMem);
        assertEquals(-3, testClass.checkInMember(invalidMem, classes));
        assertEquals(0, testClass.checkInMember(validMem, classes));
    }

    /**
     * Tests the checkInMember method to see if the member has already checked into the class.
     * Will return -4 if the member has already checked in.
     * Will return 0 if the member has not checked in yet.
     */
    @Test
    public void testAlreadyCheckedInCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member checkedInMem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        Member notCheckedInMem = new Member("Jane", "Doe", dob, expire, Location.BRIDGEWATER);
        memData.add(checkedInMem);
        memData.add(notCheckedInMem);
        testClass.checkInMember(checkedInMem, classes);
        assertEquals(-4, testClass.checkInMember(checkedInMem, classes));
        assertEquals(0, testClass.checkInMember(notCheckedInMem, classes));
    }

    /**
     * Tests the checkInMember method to see if the member is scheduled to take a different class at the same time.
     * Will return -5 if there is a scheduling conflict.
     * Will return 0 if there is no scheduling conflict
     */
    @Test
    public void testSchedulingConflictCheckInMember() {
        Member mem = new Member("John", "Doe", new Date("1/20/2004"), new Date("2/15/2023"), Location.BRIDGEWATER);
        memData.add(mem);
        FitnessClass conflictingClass = new FitnessClass(Time.MORNING, "DENISE", "SPINNING", Location.BRIDGEWATER, new Member[]{});
        FitnessClass notConflictingClass = new FitnessClass(Time.AFTERNOON, "DENISE", "SPINNING", Location.BRIDGEWATER, new Member[]{});
        classes.addClass(conflictingClass);
        classes.addClass(notConflictingClass);
        testClass.checkInMember(mem, classes);
        assertEquals(-5, conflictingClass.checkInMember(mem, classes));
        assertEquals(0, notConflictingClass.checkInMember(mem, classes));
    }

    /**
     * Tests the checkGuest method to see if the member is checking in a guest at a different location.
     * Will return -6 if the member is checking in a guest at the wrong location.
     * Will return 0 if the member is checking in a guest at the correct location
     */
    @Test
    public void testWrongGuestLocationCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family invalidGuest = new Family("John", "Doe", dob, expire, Location.EDISON, 1);
        Family validGuest = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        memData.add(invalidGuest);
        memData.add(validGuest);
        assertEquals(-6, testClass.checkGuest(invalidGuest));
        assertEquals(0, testClass.checkGuest(validGuest));
    }

    /**
     * Tests the checkGuest method to see if the member has no more guest passes to use.
     * Will return -7 if the member has no more guest passes to use.
     * Will return 0 if the member has guest passes to use.
     */
    @Test
    public void testNoMoreGuestCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family hasNoGuestPasses = new Family("John", "Doe", dob, expire, Location.BRIDGEWATER, 0);
        Family hasGuestPasses = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        memData.add(hasNoGuestPasses);
        memData.add(hasGuestPasses);
        assertEquals(-7, testClass.checkGuest(hasNoGuestPasses));
        assertEquals(0, testClass.checkGuest(hasGuestPasses));
    }

    /**
     * Tests the checkGuest method to see if a member is checking in a guest with a standard membership.
     * Will return -8 if a standard member is checking in a guest.
     * Will return 0 if the member is Premium or Family
     */
    @Test
    public void testStandardMembershipCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        Family fam = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        Premium prem = new Premium("Jacob", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        memData.add(mem);
        memData.add(fam);
        memData.add(prem);
        assertEquals(-8, testClass.checkGuest(mem));
        assertEquals(0, testClass.checkGuest(fam));
        assertEquals(0, testClass.checkGuest(prem));
    }

    /**
     * Tests the dropMem method to see if the member is not in the database.
     * Will return -1 if the member is not in the database.
     * Will return 0 if the member is in the database.
     */
    @Test
    public void testNotInDatabaseDropMem() {
        Member notInDatabase = new Member("Jane", "Doe", new Date("1/30/2000"));
        //Retrieves expiration date and location from database, else returns the same member
        notInDatabase = memData.getFullDetails(notInDatabase);
        assertEquals(-1, testClass.dropMem(notInDatabase));
        Member inDatabase = new Member("Jane", "Doe", new Date("1/30/2000"), new Date("1/30/2023"), Location.BRIDGEWATER);
        memData.add(inDatabase);
        inDatabase = memData.getFullDetails(inDatabase);
        testClass.checkInMember(inDatabase, classes);
        assertEquals(0, testClass.dropMem(inDatabase));
    }

    /**
     * Tests the dropMem method to see if the member has not checked into the class.
     * Will return -9 if the member has not checked into the class.
     */
    @Test
    public void testNotCheckedInDropMem() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member notCheckedInMem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        memData.add(notCheckedInMem);
        assertEquals(-9, testClass.dropMem(notCheckedInMem));
        Member checkedInMem = new Member("Jane", "Doe", dob, expire, Location.BRIDGEWATER);
        memData.add(checkedInMem);
        testClass.checkInMember(checkedInMem, classes);
        assertEquals(0, testClass.dropMem(checkedInMem));
    }

    /**
     * Tests the removeGuest method to see if the guest has not checked into the class.
     * Will return -9 if the guest has not checked into the class.
     * Will return 0 if the guest has checked into the class
     */
    @Test
    public void testNotCheckedInDropGuest(){
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family notCheckedInGuest = new Family("John", "Doe", dob, expire, Location.BRIDGEWATER, -1);
        memData.add(notCheckedInGuest);
        assertEquals(-9, testClass.removeGuest(notCheckedInGuest));
        Family checkedInGuest = new Family("John", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        memData.add(checkedInGuest);
        testClass.checkGuest(checkedInGuest);
        assertEquals(0, testClass.removeGuest(checkedInGuest));
    }
}