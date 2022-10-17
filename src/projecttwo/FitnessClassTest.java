package projecttwo;

import org.junit.Test;

import static org.junit.Assert.*;

public class FitnessClassTest {

    /**
     * Tests the checkInMember method to see if the dob is invalid.
     * Will return -10 if dob is invalid.
     */
    @Test
    public void testInvalidDateCheckInMember() {
        Date dob = new Date("1/32/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-10,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkInMember method to see if the member is in the database.
     * Will return -1 if member is not in the database.
     */
    @Test
    public void testNotInDatabaseCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        Family mem1 = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-1,testClass.checkInMember(mem1,classes));
    }

    /**
     * Tests the checkInMember method to see if the membership has expired.
     * Will return -2 if the membership has expired.
     */
    @Test
    public void testMembershipExpiredCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2020");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-2,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkInMember method to see if the member with standard membership is checking into a class at a different location.
     * Will return -3 if the member is at the wrong location.
     */
    @Test
    public void testWrongLocationCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.EDISON);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-3,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkInMember method to see if the member has already checked into the class.
     * Will return -4 if the member has already checked in.
     */
    @Test
    public void testAlreadyCheckedInCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        testClass.checkInMember(mem,classes);
        assertEquals(-4,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkInMember method to see if the member is scheduled to take a different class at the same time.
     * Will return -5 if there is a scheduling conflict.
     */
    @Test
    public void testSchedulingConflictCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        FitnessClass testClass1 = new FitnessClass(Time.MORNING, "DENISE", "SPINNING", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        classes.addClass(testClass1);
        assertEquals(-5,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkInMember method to see if the member can be checked in.
     * Will return 0 if the member is checked in.
     */
    @Test
    public void testCheckInMember() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(0,testClass.checkInMember(mem,classes));
    }

    /**
     * Tests the checkGuest method to see if the member is checking in a guest at a different location.
     * Will return -6 if the member is checking in a guest at the wrong location.
     */
    @Test
    public void testWrongGuestLocationCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family mem1 = new Family("Jane", "Doe", dob, expire, Location.EDISON, 1);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem1);
        Member[] participants = {mem1};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-6,testClass.checkGuest(mem1));
    }

    /**
     * Tests the checkGuest method to see if the member has no more guest passes to use.
     * Will return -7 if the member has no more guest passes to use.
     */
    @Test
    public void testNoMoreGuestCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family mem1 = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 0);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem1);
        Member[] participants = {mem1};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-7,testClass.checkGuest(mem1));
    }

    /**
     * Tests the checkGuest method to see if a member is checking in a guest with a standard membership.
     * Will return -8 if a standard member is checking in a guest.
     */
    @Test
    public void testStandardMembershipCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-8,testClass.checkGuest(mem));
    }

    /**
     * Tests the checkGuest method to see if the member can check in their guest.
     * Will return 0 if the member checks in their guest.
     */
    @Test
    public void testCheckGuest() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Family mem1 = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem1);
        Member[] participants = {mem1};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(0,testClass.checkGuest(mem1));
    }

    /**
     * Tests the dropMem method to see if the member is not in the database.
     * Will return -1 if the member is not in the database.
     */
    @Test
    public void testNotInDatabaseDropMem() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        Family mem1 = new Family("Jane", "Doe", dob, expire, Location.BRIDGEWATER, 1);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-1,testClass.dropMem(mem1));
    }

    /**
     * Tests the dropMem method to see if the member has not checked into the class.
     * Will return -9 if the member has not checked into the class.
     */
    @Test
    public void testNotCheckedInDropMem() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        assertEquals(-9,testClass.dropMem(mem));
    }

    /**
     * Tests the dropMem method to see if the member can drop the class.
     * Will return 0 if the member drops the class.
     */
    @Test
    public void testDropMem() {
        Date dob = new Date("1/20/2004");
        Date expire = new Date("2/15/2023");
        Member mem = new Member("John", "Doe", dob, expire, Location.BRIDGEWATER);
        MemberDatabase memData = new MemberDatabase();
        memData.add(mem);
        Member[] participants = {mem};
        FitnessClass testClass = new FitnessClass(Time.MORNING, "JENNIFER", "PILATES", Location.BRIDGEWATER, participants);
        ClassSchedule classes = new ClassSchedule();
        classes.addClass(testClass);
        testClass.checkInMember(mem,classes);
        assertEquals(0,testClass.dropMem(mem));
    }



}