package projecttwo;

/**
 * A Member class that provides the constructor for a member with a first name, last name,
 * date of birth, expiration date, and where the person is located, which are added to
 * the MemberDatabase. Provides methods for comparing equality between members and converting a
 * member to a String, as well as methods to return variables.
 * @author Chris Tai, Shreyank Yelagoila
 */
public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    private static final double MEMBERSHIP_FEE = 29.99;
    public static final int EARLIER = -1;

    /**
     * Constructs a Member with a first name, last name, date of birth, expiration date, and location
     *
     * @param fname    the first name of the Member, saved as a String
     * @param lname    the last name of the Member, saved as a String
     * @param dob      the Member's date of birth, saved as a Date
     * @param expire   the expiration date for the Member's membership, saved as a date
     * @param location the place where the Member is located, which contains the town, zip code, and county
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.expire = expire;
        this.dob = dob;
        this.location = location;
    }

    /**
     * Constructs a Member with a first name, last name, and date of birth
     *
     * @param fname the first name of the Member, saved as a String
     * @param lname the last name of the Member, saved as a String
     * @param dob   the Member's date of birth, saved as a Date
     */
    public Member(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = null;
        this.location = null;
    }

    /**
     * Gets the full name of the Member
     *
     * @return the concatenation of the first and last name variables of a Member
     */
    public String fullName() {
        return this.fname + " " + this.lname;
    }

    /**
     * Gets the county the Member lives in
     *
     * @return the county that is part of the Member's location variable
     */
    public String county() {
        return this.location.county();
    }

    public Location getLocation(){return this.location;}
    public Date dob() {return this.dob;}
    public double membershipFee(){
        return MEMBERSHIP_FEE;
    }

    /**
     * Gets the zip code the member lives in
     *
     * @return the zip code that is part of the Member's location variable
     */
    public String zipCode() {
        return this.location.zipCode();
    }

    /**
     * Gets the date when the Member's membership expires
     *
     * @return the expire variable as a Date
     */
    public Date expirationDate() {
        return this.expire;
    }

    @Override
    /**
     * Converts a member object to a string
     * Takes in each attribute of the member and concatenates them into a single string
     * @return the string of the member
     */
    public String toString() {
        if (expire != null && location != null) {
            return fname + " " + lname + ", " + "DOB: " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ", " +
                    "Membership expires " + expire.getMonth() + "/" + expire.getDay() + "/" + expire.getYear() + ", " +
                    "Location: " + location.toString() + ", " + location.zipCode() + ", " + location.county();
        } else {
            return fname + " " + lname + ", " + "DOB: " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear();
        }
    }

    @Override
    /**Checks if a member is equal to an object
     * Compares the member's first name, last name, and date of birth, ignoring case
     * @return true if all fields are the same, otherwise false
     */
    public boolean equals(Object obj) {
        Member mem = (Member) obj;
        if (this.fname.equalsIgnoreCase(mem.fname) && this.lname.equalsIgnoreCase(mem.lname) &&
                this.dob.compareTo(mem.dob) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    /**
     * Compares the names of two Members
     * First compares by last name, then compares by first name
     * @param member the member to be compared to
     * @return 0 if the first and last names are equal
     * @return 1 if the name of the instance of the Member precedes the name of the passed in Member
     * @return NOT_FOUND if the name of the instance of the Member comes earlier than the name of the
     * passed in Member
     */
    public int compareTo(Member member) {
        if (this.lname.equals(member.lname)) {
            if (this.fname.equals(member.fname)) {
                return 0;
            } else if (this.fname.compareTo(member.fname) < 0) {
                return EARLIER;
            } else {
                return 1;
            }
        } else if (this.lname.compareTo(member.lname) < 0) {
            return EARLIER;
        } else {
            return 1;
        }

    }

    /**
     * Runs all test cases to ensure that method is working properly
     * @param args the Strings passed in as input are put in args
     */
    public static void main(String[] args) {
        Date dateOfBirth = new Date("01/01/2001");
        //Test case 1
        Member mem1 = new Member("John", "Campbell", dateOfBirth);
        Member mem2 = new Member("Andrew", "Donovan", dateOfBirth);
        int expectedOutput = EARLIER;
        int actualOutput = mem1.compareTo(mem2);
        System.out.println("**Test case #1: If the first member has a last name that is earlier " +
                "than the member passed in, will return EARLIER, or -1");
        testResult(mem1, mem2, expectedOutput, actualOutput);
        //Test case 2
        mem1 = new Member("Andrew", "Donovan", dateOfBirth);
        mem2 = new Member("John", "Campbell", dateOfBirth);
        expectedOutput = 1;
        actualOutput = mem1.compareTo(mem2);
        System.out.println("**Test case #2: If the first member has a later last name but an earlier " +
                "first name than the member passed in, will return 1");
        testResult(mem1, mem2, expectedOutput, actualOutput);
        //Test case 3
        mem1 = new Member("Andrew", "Campbell", dateOfBirth);
        mem2 = new Member("John", "Campbell", dateOfBirth);
        expectedOutput = EARLIER;
        actualOutput = mem1.compareTo(mem2);
        System.out.println("**Test case #3: If the first member has the same last name as the member passed" +
                " in, but an earlier first name, will return EARLIER, or -1");
        testResult(mem1, mem2, expectedOutput, actualOutput);
        //Test case 4
        mem1 = new Member("John", "Campbell", dateOfBirth);
        mem2 = new Member("Andrew", "Campbell", dateOfBirth);
        expectedOutput = 1;
        actualOutput = mem1.compareTo(mem2);
        System.out.println("**Test case #4: If the first member has the same last name as the member passed" +
                " in, but a later first name, will return 1");
        testResult(mem1, mem2, expectedOutput, actualOutput);
        //Test case 5
        mem1 = new Member("John", "Campbell", dateOfBirth);
        mem2 = new Member("John", "Campbell", dateOfBirth);
        expectedOutput = 0;
        actualOutput = mem1.compareTo(mem2);
        System.out.println("**Test case #5: If the first member has the same first and last names as " +
                "the member passed in, will return 0");
        testResult(mem1, mem2, expectedOutput, actualOutput);
    }

    /**
     * Compares expected and actual outputs to see if the method is working properly
     * @param mem1 the first member being compared
     * @param mem2 the second member being compared
     * @param expectedOutput the output we are looking for as a result of the method
     * @param actualOutput the actual output we get as a result of the method
     */
    private static void testResult(Member mem1, Member mem2, int expectedOutput, int actualOutput) {
        System.out.println(mem1.toString());
        System.out.println("compareTo");
        System.out.print(mem2.toString() + " returns " + actualOutput);
        if (actualOutput == expectedOutput) {
            System.out.println(", PASS.\n");
        } else {
            System.out.println(", FAIL.\n");
        }
    }
}
