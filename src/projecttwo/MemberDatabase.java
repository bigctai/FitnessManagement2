package projecttwo;

/**
 * Keeps track of all members of the fitness chain
 * Contains members in an array, and has methods to find a member in the array, increase the array length,
 * add members, remove members, and print members based on different sorts
 *
 * @author Christopher Tai, Shreyank Yelagoila
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;
    private final int NOT_FOUND = -1;

    /**
     * Initializes an array of members with a length of 4
     */
    public MemberDatabase() {
        this.mlist = new Member[4];
        size = 0;
    }

    /**
     * Gets the Member database
     *
     * @return the array of Members
     */
    public Member[] returnList() {
        return mlist;
    }

    /**
     * Gets the size of the member database
     *
     * @return the size of the array, in integer value
     */
    public int size() {
        return size;
    }

    /**
     * Locates a member in the database
     * Traverses through the array and, if a value equals the member passed in, then it has
     * successfully located that member
     *
     * @param member the member to be found in the database
     * @return the index of the member if the member exists, otherwise -1
     */
    private int find(Member member) {

        for (int i = 0; i < size; i++) {
            if (member.equals(mlist[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Doubles the size of the array
     * If the array is full, copies all previous values of the array onto a new array and
     * sets mlist to the new array
     */
    private void grow() {
        Member[] tempList = new Member[size + 4];
        for (int i = 0; i < size; i++) {
            tempList[i] = mlist[i];
        }
        mlist = tempList;
    }

    /**
     * Adds a member to the database
     * If the date of birth or expiration date are invalid, doesn't add. If the array is full, call grow
     *
     * @param member the member to be added to the database
     * @return true if the member does not exist and the data is valid, else return false
     */
    public boolean add(Member member) {
        if (size == mlist.length) {
            grow();
        }
        mlist[size] = member;
        size++;
        return true;
    }

    /**
     * Removes member from database
     * If the member exists, shifts every member afterwards
     *
     * @param member the member to be removed from the database
     * @return true if the member is in the database, otherwise false
     */
    public boolean remove(Member member) {
        int index = find(member);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                int nextElement = i + 1;
                mlist[i] = mlist[nextElement];
            }
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prints the unsorted array content
     */
    public void print() {
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println("\n-list of members-");
        for (int i = 0; i < size; i++) {
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-\n");
    }

    public Member getFullDetails(Member mem) {
        if (find(mem) >= 0) return mlist[find(mem)];
        else {
            return mem;
        }
    }

    /**
     * Prints the array content sorted by county name, then by zipcode
     */
    public void printByCounty() {
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Member tempSmallest;
                if (mlist[i].getLocation().county().compareTo(mlist[j].getLocation().county()) > 0) {
                    tempSmallest = mlist[i];
                    mlist[i] = mlist[j];
                    mlist[j] = tempSmallest;
                } else if (mlist[i].getLocation().county().compareTo(mlist[j].getLocation().county()) == 0 &&
                        Integer.parseInt(mlist[j].zipCode()) <= Integer.parseInt(mlist[i].zipCode())) {
                    tempSmallest = mlist[i];
                    mlist[i] = mlist[j];
                    mlist[j] = tempSmallest;
                }
            }
        }
        System.out.println("\n-list of members sorted by county and zip code-");
        for (int i = 0; i < size; i++) {
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-\n");
    }

    /**
     * Prints the member database sorted by their expiration dates
     * Sorted so that the earlier expiration dates come first
     */
    public void printByExpirationDate() {
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Member tempSmallest;
                if (mlist[i].expirationDate().compareTo(mlist[j].expirationDate()) >= 0) {
                    tempSmallest = mlist[i];
                    mlist[i] = mlist[j];
                    mlist[j] = tempSmallest;
                }
            }
        }
        System.out.println("\n-list of members sorted by membership expiration date-");
        for (int i = 0; i < size; i++) {
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-\n");
    } //sort by the expiration date

    /**
     * Prints the member database sorted by their names
     * Sorted by last name first in alphabetical order, then their first name
     */
    public void printByName() {
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Member tempSmallest = null;
                if (mlist[i].compareTo(mlist[j]) > 0) {
                    tempSmallest = mlist[i];
                    mlist[i] = mlist[j];
                    mlist[j] = tempSmallest;
                }
            }
        }
        System.out.println("\n-list of members sorted by last name, and first name-");
        for (int i = 0; i < size; i++) {
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-\n");
    } //sort by last name and then first name

    public void printWithFees(){
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println("\n-list of members with membership fees");
        for(int i = 0; i < size; i++){
            System.out.println(mlist[i].toString() + ", Membership fee: $" + mlist[i].membershipFee());
        }
        System.out.println("-end of list-\n");
    }
}