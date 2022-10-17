package projecttwo;

import java.util.ArrayList;

/**
 * Defines a class for members to check in to, with a time, instructor and an array of participants
 * Has methods for printing out the class schedule, finding members checked into each class,
 * checking members in, and dropping members
 * @author Chris Tai, Shreyank Yelagoila
 */
public class FitnessClass {
    private Time timeOfClass;
    private String instructor;
    private String className;
    private Member[] participants;
    private Location gymLocation;
    private ArrayList<Member> guests;
    private int size;
    private final int NOT_FOUND = -1;

    /**
     * Initializes a projectone.FitnessClass that has a time, an instructor, a name, and
     * an array of participants
     *
     * @param timeOfClass the time the class takes place, passed in as a Time constant
     * @param instructor the name of the instructor, passed in as a String
     * @param className the name of the class, passed in as a String
     * @param participants the array of participants in the class
     */
    public FitnessClass(Time timeOfClass, String instructor, String className, Location gymLocation, Member[] participants) {
        this.timeOfClass = timeOfClass;
        this.instructor = instructor;
        this.className = className;
        this.participants = participants;
        this.gymLocation = gymLocation;
        this.size = this.participants.length;
        guests = new ArrayList<>();
    }

    /**
     * Gets the name of the class
     *
     * @return className as a String
     */
    public String getClassName() {
        return this.className;
    }

    public Location getLocation(){ return gymLocation;}
    public String getInstructor(){
        return instructor;
    }

    public Member[] getParticipants(){return participants;}
    public int getSize(){return size;}
    public ArrayList<Member> getGuests(){return guests;}

    /**
     * Gets the time of the class
     *
     * @return timeOfClass as a Time object
     */
    public Time getTimeOfClass() {
        return this.timeOfClass;
    }

    /**
     * Finds the member in the array of participants
     *
     * @param mem the member that the method searches for
     * @return the index of the member in the array if they exist, else NOT_FOUND
     */
    public int findParticipant(Member mem) {
        for (int i = 0; i < size; i++) {
            if (mem.equals(participants[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Doubles the size of the participants array
     * Creates a new array with double the size, and copies the elements over
     */
    private void grow() {
        Member[] tempList = new Member[size == 0 ? 1 : size * 2];
        for (int i = 0; i < size; i++) {
            tempList[i] = participants[i];
        }
        participants = tempList;
    }

    /**
     * Adds a member to the end of the array
     *
     * @param memberInfo the information of the member trying to check into the class
     * @return true if the member is added successfully
     */
    public int checkInMember(String[] memberInfo, ClassSchedule classes, MemberDatabase memData) {
        if (size == participants.length) {
            grow();
        }
        if(!new Date(memberInfo[6]).isValid()){
            return -10;
        }
        Member memToCheckIn = memData.getFullDetails(new Member(memberInfo[4], memberInfo[5],
                new Date(memberInfo[6])));
        if (memToCheckIn == null) {
            return -1;
        }
        if (memToCheckIn.expirationDate().compareTo(new Date()) < 0) {
            return -2;
        }
        if(checkLocationRestriction(memToCheckIn)){
            return -3;
        }
        if (findParticipant(memToCheckIn) >= 0) {
            return -4;
        }
        if(checkSchedulingConflict(classes, memToCheckIn)){
            return -5;
        }
        participants[size] = memToCheckIn;
        size++;
        return 0;
    }
    public int checkGuest(Member mem){
        if(mem instanceof Family || mem instanceof Premium){
            if(!(mem.getLocation().toString().equalsIgnoreCase(gymLocation.toString()))){
                return -6;
            } else if(((Family) mem).getGuestPass() == 0){
                return -7;
            } else {
                ((Family) mem).guestIn();
                guests.add(mem);
                return 0;
            }
        } else {
            return -8;
        }
    }

    private boolean checkLocationRestriction(Member memToCheckIn){
        if(memToCheckIn instanceof Family || memToCheckIn instanceof Premium){
            return false;
        }
        else if(!memToCheckIn.getLocation().equals(gymLocation)){
            return true;
        }
        return false;
    }

    /**
     * Checks if the member has a scheduling conflict
     * If the member has checked into another class that is at the same time as the class they are trying to
     * check into, then they have a scheduling conflict
     *
     * @param classes     the dataabase of classes to compare against
     * @param memToCheckIn the member who is trying to check in
     * @return true if the member has a scheduling conflict, else false
     */
    private boolean checkSchedulingConflict(ClassSchedule classes, Member memToCheckIn) {
        for (int i = 0; i < classes.returnList().length; i++) {
            if (classes.getClass(i).getTimeOfClass().equals(timeOfClass)
                    && classes.getClass(i).findParticipant(memToCheckIn) >= 0) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Removes a member from a class's participants
     *
     * @param memberInfo the information of the member trying to drop the class
     * @return true if the member is successfully removed, false otherwise
     */
    public int dropMem(String[] memberInfo, MemberDatabase memData) {
        Member memToDrop = memData.getFullDetails(new Member(memberInfo[4], memberInfo[5],
                new Date(memberInfo[6])));
        if (memToDrop == null) {
            return -8;
        }
        else if(findParticipant(memToDrop) < 0){
            return -9;
        }
        else{
            int index = findParticipant(memToDrop);
            for (int i = index; i < size; i++) {
                participants[i] = participants[i++];
            }
            participants[findParticipant(memToDrop)] = participants[size - 1];
            participants[size - 1] = null;
            size--;
            return 0;
        }
    }

    public void removeGuest(Member guest){
        ((Family) guest).guestOut();
        guests.remove(guest);
    }
}