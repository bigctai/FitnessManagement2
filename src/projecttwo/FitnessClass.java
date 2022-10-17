package projecttwo;

import java.util.ArrayList;

/**
 * Defines a class for members to check in to, with a time, instructor and an array of participants
 * Has methods for finding members checked into each class, checking members in, and dropping members
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
    private final int EXPIRED = -2;
    private final int WRONG_LOCATION = -3;
    private final int DUPLICATE = -4;
    private final int CONFLICT = -5;
    private final int WRONG_GUEST_LOCATION = -6;
    private final int NO_MORE_GUEST = -7;
    private final int STANDARD = -8;
    private final int NOT_CHECKED_IN = -9;
    private final int INVALID_DATE = -10;

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
     * @param memToCheckIn the member trying to check into the class
     * @return true if the member is added successfully
     */
    public int checkInMember(Member memToCheckIn, ClassSchedule classes) {
        if (size == participants.length) {
            grow();
        }
        if(!memToCheckIn.dob().isValid()){
            return INVALID_DATE;
        }
        if (memToCheckIn.getLocation() == null) {
            return NOT_FOUND;
        }
        if (memToCheckIn.expirationDate().compareTo(new Date()) < 0) {
            return EXPIRED;
        }
        if(checkLocationRestriction(memToCheckIn)){
            return WRONG_LOCATION;
        }
        if (findParticipant(memToCheckIn) >= 0) {
            return DUPLICATE;
        }
        if(checkSchedulingConflict(classes, memToCheckIn)){
            return CONFLICT;
        }
        participants[size] = memToCheckIn;
        size++;
        return 0;
    }
    public int checkGuest(Member mem){
        if(mem instanceof Family || mem instanceof Premium){
            if(!(mem.getLocation().toString().equalsIgnoreCase(gymLocation.toString()))){
                return WRONG_GUEST_LOCATION;
            } else if(((Family) mem).getGuestPass() == 0){
                return NO_MORE_GUEST;
            } else {
                ((Family) mem).guestIn();
                guests.add(mem);
                return 0;
            }
        } else {
            return STANDARD;
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
        for (int i = 0; i < classes.getNumOfClasses(); i++) {
            if (classes.returnList()[i].getTimeOfClass().equals(timeOfClass)
                    && classes.returnList()[i].findParticipant(memToCheckIn) >= 0) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Removes a member from a class's participants
     *
     * @param memToDrop the member trying to drop the class
     * @return true if the member is successfully removed, false otherwise
     */
    public int dropMem(Member memToDrop) {
        if(!memToDrop.dob().isValid()){
            return INVALID_DATE;
        }
        if (memToDrop.getLocation() == null) {
            return NOT_FOUND;
        }
        else if(findParticipant(memToDrop) < 0){
            return NOT_CHECKED_IN;
        }
        else{
            int index = findParticipant(memToDrop);
            for (int i = index; i < size; i++) {
                participants[i] = participants[i++];
            }
            participants[findParticipant(memToDrop)] = participants[size - 1];
            participants[size - 1] = null;
            decrementSize();
            return 0;
        }
    }

    public void decrementSize(){
        size--;
    }

    public int removeGuest(Member guest){
        if(!guests.contains(guest)){
            return NOT_CHECKED_IN;
        }
        else {
            ((Family) guest).guestOut();
            guests.remove(guest);
            return 0;
        }
    }
}


