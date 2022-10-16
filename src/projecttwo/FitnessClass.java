package projecttwo;

import java.text.DecimalFormat;
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
        guests = new ArrayList<Member>();
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

    /**
     * Gets the time of the class
     *
     * @return timeOfClass as a Time object
     */
    public Time timeOfClass() {
        return this.timeOfClass;
    }

    /**
     * Prints out the class along with the participants in it
     * Prints out the name of the class, instructor, and the time of the class, followed by each participant
     */
    public void printClass() {
        System.out.println(className.toUpperCase() + " - " + instructor.toUpperCase() + ", " +
                timeOfClass.hourAndMinute() + ", " + gymLocation);
        printParticipants();
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
     * @param mem valid member to add to the array
     * @return true if the member is added successfully
     */
    public boolean checkInMember(Member mem) {
        if (size == participants.length) {
            grow();
        }
        participants[size] = mem;
        size++;
        return true;
    }

    /**
     * Removes a member from a class's participants
     *
     * @param mem valid member to remove from the array
     * @return true if the member is successfully removed, false otherwise
     */
    public boolean dropMem(Member mem) {
        int index = findParticipant(mem);
        if (index >= 0) {
            for (int i = index; i < size; i++) {
                participants[i] = participants[i++];
            }
            participants[findParticipant(mem)] = participants[size - 1];
            participants[size - 1] = null;
            size--;
            return true;
        } else {
            return false;
        }
    }

    public void addGuest(Member guest){
        guests.add(guest);
    }

    public void removeGuest(Member guest){
        guests.remove(guest);
    }

    public void printParticipantsAndGuests(){
        printParticipants();
        System.out.println("- Guests -");
        for(Member guest : guests){
            System.out.println("\t" + guest.toString());
        }
        System.out.println();
    }

    private void printParticipants(){
        if (participants.length > 0) {
            System.out.println("- Participants -");
            for (int i = 0; i < size; i++) {
                System.out.println("\t" + participants[i].toString());
            }
        }
    }
}