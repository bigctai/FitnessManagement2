package projecttwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.Adler32;

/**
 * Processes the inputs and calls other classes based on the input
 * Has methods for checking if the inputs are valid and printing out statements accordingly
 *
 * @author Chris Tai, Shreyank Yelagoila
 */
public class GymManager {
    private MemberDatabase memData = new MemberDatabase();

    private ClassSchedule classSchedule = new ClassSchedule();
    private Scanner scanUserInput = new Scanner(System.in);
    private String input;
    private final int NOT_FOUND = -1;
    private final int EXPIRED = -2;
    private final int WRONG_LOCATION = -3;
    private final int DUPLICATE = -4;
    private final int CONFLICT = -5;
    private final int WRONG_GUEST_LOCATION = -6;
    private final int NO_MORE_GUEST = -7;
    private final int STANDARD = -8;
    private static final int NOT_CHECKED_IN = -9;
    private final int INVALID_DATE = -10;
    private static final int ADULT = 18;

    /**
     * Calls methods based on user input
     * Reads user input and, depending on the command put in, runs a method associated with that command
     * and other inputted data
     */
    public void run() {
        System.out.println("Gym Manager running...");
        while (!(input = scanUserInput.nextLine()).equals("Q")) {
            String[] inputData = input.split(" ");
            if(inputData.length > 0 && inputData[0].length() > 0){
                switch (inputData[0].charAt(0)) {
                    case 'L':
                        load(inputData[0]);
                        break;
                    case 'A':
                        addMember(inputData);
                        break;
                    case 'R':
                        removeMember(inputData);
                        break;
                    case 'P':
                        printMembers(inputData[0]);
                        break;
                    case 'S':
                        printClasses(inputData[0]);
                        break;
                    case 'C':
                        checkIn(inputData);
                        break;
                    case 'D':
                        dropClass(inputData);
                        break;
                    default:
                        System.out.println(inputData[0] + " is an invalid command!");
                }
            }
        }
        System.out.println("Gym Manager terminated.");
    }

    private void load(String loadType){
        if(loadType.equals("LM")){
            loadMembers();
        }
        else{
            loadSchedule();
        }
    }

    /**
     *
     */

    private void loadMembers() {
        File memberList = new File("/Users/christai/IdeaProjects/Project2/src/memberList");
        try {
            Scanner memberScanner = new Scanner(memberList);
            System.out.println("-list of members loaded-");
            while (memberScanner.hasNextLine()) {
                String[] memberInputData = memberScanner.nextLine().replaceAll("  ", " ").split(" ");
                Member member = createMember(memberInputData, true);
                memData.add(member);
                System.out.println(member.toString());
            }
            System.out.println("-end of list-");
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.toString());
        }
    }

    private void loadSchedule(){
        File scheduleList = new File("/Users/christai/IdeaProjects/Project2/src/classSchedule");
        try{
            Scanner classScanner = new Scanner(scheduleList);
            System.out.println("-list of classes loaded-");
            while(classScanner.hasNextLine()){
                String [] classInputData = classScanner.nextLine().split(" ");
                FitnessClass fitClass = new FitnessClass(Time.valueOf(classInputData[2].toUpperCase()),
                        classInputData[1], classInputData[0], Location.valueOf(classInputData[3].toUpperCase()), new Member[0]);
                printClass(fitClass);
                System.out.println();
                classSchedule.addClass(fitClass);
            }
            System.out.println("-end of class list-");
        }
        catch(FileNotFoundException exception){
            System.out.println(exception.toString());
        }
    }

    /**
     * Performs checks to make sure that member data is valid
     * Checks location, if the member is already in database, and if the member's date of birth and
     * expiration date are valid
     *
     * @param memberToAdd contains member data as elements of an array
     */
    private void addMember(String[] memberToAdd) {
        if(!memberToAdd[0].equals("AF") && !memberToAdd[0].equals("AP") && !memberToAdd[0].equals("A")){
            System.out.println(memberToAdd[0] + " is an invalid command!");
            return;
        }
        if(!checkMemberCredentials(memberToAdd[4], memberToAdd[3])){
            return;
        }
        Member memToAdd = createMember(memberToAdd, false);
        for (int i = 0; i < memData.size(); i++) {
            if (memData.returnList()[i].equals(memToAdd)) {
                System.out.println(memToAdd.fullName() + " is already in the database.");
                return;
            }
        }
        if (!isOldEnough(memToAdd.dob())) {
            return;
        }
        if (memData.add(memToAdd))
            System.out.println(memToAdd.fullName() + " added.");
    }

    /**
     * Creates a member using the information inputted
     * Determines if the member created is Premium or Family
     * @param memberToAdd the information about the member held in an array of Strings
     * @param fromFile determines whether the Member is added from file or command line
     * @return
     */
    private Member createMember(String[] memberToAdd, boolean fromFile){
        String firstName;
        String lastName;
        Date dob;
        Date expirationDate;
        Location location = Location.valueOf(memberToAdd[4].toUpperCase());
        if(!fromFile){
            firstName = memberToAdd[1];
            lastName = memberToAdd[2];
            dob = new Date(memberToAdd[3]);
            expirationDate = new Date();
            if(memberToAdd[0].equals("AF")){
                expirationDate.setExpire();
                return new Family (firstName, lastName, dob, expirationDate, location, 1);
            }
            else if(memberToAdd[0].equals("AP")){
                expirationDate.setExpire();
                return new Premium (firstName, lastName, dob, expirationDate, location, 3);
            }
        }
        else{
            firstName = memberToAdd[0];
            lastName = memberToAdd[1];
            dob = new Date(memberToAdd[2]);
            expirationDate = new Date(memberToAdd[3]);
        }
        expirationDate.setExpire();
        return new Member(firstName, lastName, dob, expirationDate, location);
    }

    /**
     * Prints statements depending on if a member was successfully removed
     * Calls "remove" method in MemberDatabase, which returns true if the member is in the database
     * and false otherwise
     *
     * @param memberToRemove contains member data as elements of an array
     */
    private void removeMember(String[] memberToRemove) {
        if(!memberToRemove[0].equals("R")){
            System.out.println(memberToRemove[0] + " is an invalid command!");
            return;
        }
        if (memData.remove(new Member(memberToRemove[1].toUpperCase(), memberToRemove[2].toUpperCase(),
                new Date(memberToRemove[3]))))
            System.out.println(memberToRemove[1] + " " + memberToRemove[2] + " removed.");
        else {
            System.out.println(memberToRemove[1] + " " + memberToRemove[2] + " is not in the database.");
        }
    }

    /**
     * Prints out each fitness class in the fitness chain
     * Calls the printClass method in FitnessClass
     */
    private void printClasses(String input) {
        if(input.equals("S")) {
            System.out.println("-Fitness classes-");
            for (int i = 0; i < classSchedule.getNumOfClasses(); i++) {
                printClass(classSchedule.returnList()[i]);
            }
            System.out.println();
        }
        else{
            System.out.println(input + " is an invalid command!");
        }
    }

    /**
     * Performs checks to make sure that the member is allowed to check in
     * Checks if member's date of birth is valid, if their membership expired, if member exists,
     * if class exists, and if member has already checked in to the class or to another class
     *
     * @param memberToCheckIn contains member data as elements of an array
     */
    private void checkIn(String[] memberToCheckIn) {
        if(memberToCheckIn[0].equals("C") || memberToCheckIn[0].equals("CG")) {
            if(!checkClassCredentials(memberToCheckIn)) return;
            int fitClassIndex = getClassIndex(memberToCheckIn[2], memberToCheckIn[3], memberToCheckIn[1]);
            if(fitClassIndex < 0) return;
            FitnessClass classToCheckInto = classSchedule.returnList()[fitClassIndex];
            if(memberToCheckIn[0].equals("CG")){
                checkGuest(memberToCheckIn, classToCheckInto);
                return;
            }
            Member memToCheckIn = memData.getFullDetails(new Member(memberToCheckIn[4], memberToCheckIn[5],
                    new Date(memberToCheckIn[6])));
            int checkConditions = classToCheckInto.checkInMember(memToCheckIn, classSchedule);
            String memberName = memberToCheckIn[4] + " " + memberToCheckIn[5];
            if(checkConditions == INVALID_DATE){
                System.out.println("DOB " + memberToCheckIn[6] + ": invalid calendar date!");
            }
            if(checkConditions == NOT_FOUND){
                System.out.println(memberName + " " + memberToCheckIn[6] + " is not in the database.");
            }
            else if(checkConditions == EXPIRED){
                System.out.println(memberName + " " + memberToCheckIn[6] + " membership expired.");
            }
            else if(checkConditions == WRONG_LOCATION){
                System.out.println(memberToCheckIn[4] + " " + memberToCheckIn[5] + " checking in " +
                        classToCheckInto.getLocation().toString() + " - standard membership location restriction.");
            }
            else if(checkConditions == DUPLICATE){
                System.out.println(memberName + " already checked in.");
            }
            else if(checkConditions == CONFLICT){
                System.out.println("Time conflict - " + classToCheckInto.getClassName().toUpperCase() + " - " + classToCheckInto.getInstructor().toUpperCase() + ", "
                        + classToCheckInto.getTimeOfClass().toString() + ", " + classToCheckInto.getLocation().toString() + ".");
            }
            else{
                System.out.print(memberName + " checked in ");
                printClass(classToCheckInto);
                System.out.println();
            }
        }
        else{
            System.out.println(memberToCheckIn[0] + " is an invalid command!");
        }
    }

    private void checkGuest(String[] memberInfo, FitnessClass fitClass){
        Member mem = memData.getFullDetails(new Member(memberInfo[4], memberInfo[5], new Date(memberInfo[6])));
        int checkGuestCondition = fitClass.checkGuest(mem);
        if(checkGuestCondition == WRONG_GUEST_LOCATION){
            System.out.println(mem.fullName() +" Guest checking in " + fitClass.getLocation() + " - guest location restriction.");
        }
        else if(checkGuestCondition == NO_MORE_GUEST){
            System.out.println(mem.fullName() + " ran out of guest pass.");
        }
        else if(checkGuestCondition == STANDARD){
            System.out.println("Standard membership - guest check-in is not allowed");
        }
        else{
            System.out.print(mem.fullName() + " (guest) checked in ");
            printClass(fitClass);
            System.out.println();
        }
    }

    /**
     * Performs checks to make sure that the member can be dropped
     * Checks if member's date of birth is valid, if the member is a participant in the class,
     * and if the class exists
     *
     * @param memberToDrop contains member data as elements of a String array
     */
    private void dropClass(String[] memberToDrop) {
        if(memberToDrop[0].equals("D") || memberToDrop[0].equals("DG")) {
            if(!checkClassCredentials(memberToDrop)) return;
            int fitClassIndex = getClassIndex(memberToDrop[2], memberToDrop[3], memberToDrop[1]);
            if(fitClassIndex < 0){
                return;
            }
            FitnessClass classToDrop = classSchedule.returnList()[fitClassIndex];
            if(memberToDrop[0].equals("DG")){
                dropGuest(memberToDrop, classToDrop);
                return;
            }
            Member memToDrop = memData.getFullDetails(new Member(memberToDrop[4], memberToDrop[5],
                    new Date(memberToDrop[6])));
            int dropClassCondition = classToDrop.dropMem(memToDrop);
            if (dropClassCondition == NOT_FOUND) {
                System.out.println(memberToDrop[4] + " " + memberToDrop[5] + " " + memberToDrop[6] + " is not in the database.");
            }
            else if(dropClassCondition == NOT_CHECKED_IN){
                System.out.println(memberToDrop[4] + " " + memberToDrop[5] + " did not check in.");
            }
            else{
                System.out.println(memberToDrop[4] + " " + memberToDrop[5] + " done with the class.");
            }
        }
        else{
            System.out.println(memberToDrop[0] + " is an invalid command!");
        }
    }

    public void dropGuest(String[] memberInfo, FitnessClass classToDrop){
        Member memToDrop = memData.getFullDetails(new Member(memberInfo[4], memberInfo[5], new Date(memberInfo[6])));
        classToDrop.removeGuest(memToDrop);
        System.out.println(memToDrop.fullName() + " Guest done with the class.");
    }

    private boolean checkMemberCredentials(String location, String dob){
        if(!isValidLocation(location)){
            return false;
        }
        if (!isValidDateOfBirth(new Date(dob))) return false;
        return true;
    }

    /**
     * Gets the index of the inputted class in the array of classes
     * Also checks if the class inputted is not in the array of classes and checks if the member has already checked in
     * if they are adding and if the member has not checked in if they are dropping
     * @param className  the inputted class to be searched for
     * @param instructor the inputted instructor to be searched for
     * @param location the inputted location to be searched for
     * @return the index of the class if it is found, and they haven't checked in, else ALREADY_CHECKED_IN if
     * they already checked in, else NOT_FOUND
     */
    private int getClassIndex(String instructor, String location, String className){
        int classExists = NOT_FOUND;
        for(int i = 0; i < classSchedule.getNumOfClasses(); i++){
            FitnessClass classPtr = classSchedule.returnList()[i];
            if(classPtr.getClassName().equalsIgnoreCase(className)){
                if(classPtr.getInstructor().equalsIgnoreCase(instructor)){
                    if(classPtr.getLocation().name().equalsIgnoreCase(location)){
                        classExists = i;
                        break;
                    }
                }
            }
        }
        if(classExists < 0){
            System.out.println(className + " by " + instructor + " does not exist at " + location);
        }
        return classExists;
    }

    private boolean checkClassCredentials(String[] classInfo){
        if(!isValidLocation(classInfo[3])) return false;
        if(!isValidInstructor(classInfo[2])) return false;
        if(!isValidClass(classInfo[1])) return false;
        return true;
    }

    /**
     * Checks if the location exists
     * Iterates through the locations array and finds one that equals the argument
     *
     * @param location To be checked against the elements of the locations array
     * @return true if the location is in the array of locations, else false
     */
    public boolean isValidLocation(String location) {
        for (Location locations : Location.values()) {
            if (location.toUpperCase().equals(locations.name())) {
                return true;
            }
        }
        System.out.println(location + " - invalid location.");
        return false;
    }

    public boolean isValidInstructor(String instructor){
        for(int i = 0; i < classSchedule.getNumOfClasses(); i++){
            String checkInstructor = classSchedule.returnList()[i].getInstructor();
            if(instructor.equalsIgnoreCase(checkInstructor)){
                return true;
            }
        }
        return false;
    }

    private boolean isValidClass(String className) {
        for (int i = 0; i < classSchedule.getNumOfClasses(); i++) {
            if(className.equalsIgnoreCase(classSchedule.returnList()[i].getClassName())){
                return true;
            }
        }
        System.out.println(className + " - class does not exist.");
        return false;
    }

    /**
     * Checks if the date of birth is a valid calendar date
     * Calls isValid in Date Class
     *
     * @param dob the Member's date of birth to be checked
     * @return false if the date of birth is invalid, else true
     */
    private boolean isValidDateOfBirth(Date dob) {
        if (!(dob.isValid())) {
            System.out.println("DOB " + dob.dateString() + ": invalid calendar date!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the member is less than 18 years old
     * Compares the member's age to the current date
     *
     * @param checkDateOfBirth the member who is checking in's date of birth
     * @return false if the member is under 18, else true
     */
    private boolean isOldEnough(Date checkDateOfBirth) {
        Date currentDate = new Date();
        String dob = checkDateOfBirth.dateString();
        if (currentDate.compareTo(checkDateOfBirth) <= 0) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return false;
        }
        if (currentDate.getYear() - checkDateOfBirth.getYear() < ADULT) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return false;
        } else if (currentDate.getYear() - checkDateOfBirth.getYear() == ADULT) {
            if (currentDate.getMonth() < checkDateOfBirth.getMonth()) {
                System.out.println("DOB " + dob + ": must be 18 or older to join!");
                return false;
            } else if (currentDate.getMonth() == checkDateOfBirth.getMonth()) {
                if (currentDate.getDay() < checkDateOfBirth.getDay()) {
                    System.out.println("DOB " + dob + ": must be 18 or older to join!");
                    return false;
                }
            }
        }
        return true;
    }

    private void printMembers(String sortType){
        switch(sortType){
            case "P" :
                memData.print();
                break;
            case "PC":
                memData.printByCounty();
                break;
            case "PD":
                memData.printByExpirationDate();
                break;
            case "PN":
                memData.printByName();
                break;
            case "PF":
                memData.printWithFees();
                break;
        }
    }
    /**
     * Prints out the class along with the participants in it
     * Prints out the name of the class, instructor, and the time of the class, followed by each participant
     */
    public void printClass(FitnessClass fitClass) {
        System.out.println(fitClass.getClassName().toUpperCase() + " - " + fitClass.getInstructor().toUpperCase() + ", " +
                fitClass.getTimeOfClass().hourAndMinute() + ", " + fitClass.getLocation());
        if (fitClass.getSize() > 0) {
            System.out.println("- Participants -");
            for (int i = 0; i < fitClass.getSize(); i++) {
                System.out.println("\t" + fitClass.getParticipants()[i].toString());
            }
        }
        if(fitClass.getGuests().size() > 0) {
            System.out.println("- Guests -");
            for (Member guest : fitClass.getGuests()) {
                System.out.println("\t" + guest.toString());
            }
        }
    }
}
