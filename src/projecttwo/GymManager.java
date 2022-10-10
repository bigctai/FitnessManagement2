package projecttwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private static final int NOT_FOUND = -1;
    private static final int ALREADY_CHECKED_IN = -2;
    private static final int NOT_CHECKED_IN = -3;
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
                fitClass.printClass();
            }
            System.out.println("-end of class list-");
        }
        catch(FileNotFoundException exception){

        }
    }

    private void addClass(){

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
        if(!isValidLocation(memberToAdd[4])){
            return;
        }
        Member memToAdd = createMember(memberToAdd, false);
        Date currentDate = new Date();
        Date expirationDate = currentDate;
        expirationDate.setExpire();
        for (int i = 0; i < memData.size(); i++) {
            if (memData.returnList()[i].equals(memToAdd)) {
                System.out.println(memToAdd.fullName() + " is already in the database.");
                return;
            }
        }
        if (!isOldEnough(memToAdd.dob())) {
            return;
        }
        if (!(memToAdd.dob().isValid())) {
            System.out.println("DOB " + memberToAdd[3] + ": invalid calendar date!");
            return;
        }
        if (memData.add(memToAdd))
            System.out.println(memToAdd.fullName() + " added.");
    }

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

    private Family createFamilyMember(String[] memberToAdd){
        String firstName = memberToAdd[0];
        String lastName = memberToAdd[1];
        Date dob = new Date(memberToAdd[2]);
        Date expirationDate = new Date(memberToAdd[3]);
        Location location = Location.valueOf(memberToAdd[4]);
        return new Family(firstName, lastName, dob, expirationDate, location, 1);
    }

    private Premium createPremiumMember(String[] memberToAdd){
        String firstName = memberToAdd[0];
        String lastName = memberToAdd[1];
        Date dob = new Date(memberToAdd[2]);
        Date expirationDate = new Date(memberToAdd[3]);
        Location location = Location.valueOf(memberToAdd[4]);
        return new Premium(firstName, lastName, dob, expirationDate, location, 1);
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
            System.out.println("\n-Fitness Classes-");
            for (int i = 0; i < classSchedule.getNumOfClasses(); i++) {
                classSchedule.getClass(i).printClass();
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
        if(memberToCheckIn[0].equals("C")) {
            Member memToCheckIn = memData.getFullDetails(new Member(memberToCheckIn[4], memberToCheckIn[5],
                    new Date(memberToCheckIn[6])));
            //NEED TO BREAK INTO SEPARATE METHOD THAT CHECKS EACH VALUE PASSED IN - INSTRUCTOR, CLASSNAME, and LOCATION
            FitnessClass classToCheckInto = classSchedule.find(memberToCheckIn[2],
                    Location.valueOf(memberToCheckIn[3].toUpperCase()), memberToCheckIn[1]);
            if (!isValidDateOfBirth(memToCheckIn.dob())) return;
            Date currentDate = new Date();
            boolean memExists = false;
            for (int i = 0; i < memData.size(); i++) {
                if (memData.returnList()[i].equals(memToCheckIn)) {
                    if (memData.returnList()[i].expirationDate().compareTo(currentDate) < 0) {
                        System.out.println(memToCheckIn.fullName() + " " + memToCheckIn.dob().dateString() + " membership expired.");
                        return;
                    }
                    memExists = true;
                }
            }
            if (!memExists) {
                System.out.println(memToCheckIn.fullName() + " " + memToCheckIn.dob().dateString() + " is not in the database.");
                return;
            }
            int fitClassIndex = getClassIndex(memberToCheckIn[1], memToCheckIn, true);
            if (fitClassIndex < 0) {
                return;
            }
            if (checkSchedulingConflict(classSchedule.getClass(fitClassIndex), memToCheckIn)) {
                return;
            }
            if (classSchedule.returnList()[fitClassIndex].checkInMember(memToCheckIn))
                System.out.println(memToCheckIn.fullName() + " checked in " + classSchedule.returnList()[fitClassIndex].className() + ".");
        }
        else{
            System.out.println(memberToCheckIn[0] + " is an invalid comand!");
        }
    }

    /**
     * Performs checks to make sure that the member can be dropped
     * Checks if member's date of birth is valid, if the member is a participant in the class,
     * and if the class exists
     *
     * @param memberToDrop contains member data as elements of an array
     */
    private void dropClass(String[] memberToDrop) {
        if(memberToDrop[0].equals("D")) {
            Date checkDateOfBirth = new Date(memberToDrop[4]);
            if (!(checkDateOfBirth.isValid())) {
                System.out.println("DOB " + memberToDrop[4] + ": invalid calendar date!");
                return;
            }
            Member memToDrop = new Member(memberToDrop[2], memberToDrop[3], checkDateOfBirth);
            int fitClassIndex = getClassIndex(memberToDrop[1], memToDrop, false);
            if (fitClassIndex < 0) {
                return;
            }
            if (classSchedule.getClass(fitClassIndex).dropMem(memToDrop))
                System.out.println(memberToDrop[2] + " " + memberToDrop[3] + " dropped " + classSchedule.getClass(fitClassIndex).className()
                        + ".");
        }
        else{
            System.out.println(memberToDrop[0] + " is an invalid command!");
        }
    }

    /**
     * Checks if the location exists
     * Iterates through the locations array and finds one that equals the argument
     *
     * @param location To be checked against the elements of the locations array
     * @return true if the location is in the array of locations, else false
     */
    private boolean isValidLocation(String location) {
        for (Location locations : Location.values()) {
            if (location.toUpperCase().equals(locations.name())) {
                return true;
            }
        }
        System.out.println(location + ": invalid location!");
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
        } else if (currentDate.getYear() - checkDateOfBirth.getYear() == 18) {
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

    /**
     * Gets the index of the inputted class in the array of classes
     * Also checks if the class inputted is not in the array of classes and checks if the member has already checked in
     * if they are adding and if the member has not checked in if they are dropping
     *
     * @param className  the inputted class to be searched for
     * @param memToCheck the inputted Member who is checking into or dropping the inputted class
     * @param checkingIn determines whether the member is checking into (true) or dropping (false) the class
     * @return the index of the class if it is found, and they haven't checked in, else ALREADY_CHECKED_IN if
     * they already checked in, else NOT_FOUND
     */
    private int getClassIndex(String className, Member memToCheck, boolean checkingIn) {
        for (int i = 0; i < classSchedule.returnList().length; i++) {
            FitnessClass fitClassPtr = classSchedule.getClass(i);
            if (className.equalsIgnoreCase(fitClassPtr.className())) {
                if (checkingIn) {
                    if (classSchedule.getClass(i).findParticipant(memToCheck) >= 0) {
                        System.out.println(memToCheck.fullName() + " has already checked in " + fitClassPtr.className() + ".");
                        return ALREADY_CHECKED_IN;
                    }
                } else if (classSchedule.getClass(i).findParticipant(memToCheck) < 0) {
                    System.out.println(memToCheck.fullName() + " is not a participant in " + fitClassPtr.className() + ".");
                    return NOT_CHECKED_IN;
                }
                return i;
            }
        }
        System.out.println(className + " class does not exist.");
        return NOT_FOUND;
    }

    /**
     * Checks if the member has a scheduling conflict
     * If the member has checked into another class that overlaps with the class they are trying to
     * check into, then they have a scheduling conflict
     *
     * @param fitClass     the class they are trying to check into
     * @param memToCheckIn the member who is trying to check in
     * @return true if the member has a scheduling conflict, else false
     */
    private boolean checkSchedulingConflict(FitnessClass fitClass, Member memToCheckIn) {
        for (int i = 0; i < classSchedule.returnList().length; i++) {
            if (classSchedule.getClass(i).timeOfClass().equals(fitClass.timeOfClass())
                    && classSchedule.getClass(i).findParticipant(memToCheckIn) >= 0) {
                System.out.println(fitClass.className() + " time conflict -- " + memToCheckIn.fullName()
                        + " has already checked in " + classSchedule.getClass(i).className() + ".");
                return true;
            }
        }
        return false;
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
}
