package projecttwo;

/**
 * A ClassSchedule class that initializes a list of classes as an array.
 * Provides methods that can add a class to the schedule, return the number
 * of classes, and return the list of classes.
 * @author Chris Tai, Shreyank Yelagoila
 */
public class ClassSchedule {

    private FitnessClass[] classes;
    private int numOfClasses;

    /**
     * Initializes an array as a list of fitness classes and the
     * number of classes in the list as 0.
     */
    public ClassSchedule(){
        classes = new FitnessClass[15];
        numOfClasses = 0;
    }

    /**
     * Gets the list of classes in the class schedule
     *
     * @return the list of classes as an array
     */
    public FitnessClass[] returnList(){
        return classes;
    }

    /**
     * Gets the number of classes in the class schedule
     *
     * @return the number of classes as an integer
     */
    public int getNumOfClasses(){
        return numOfClasses;
    }

    /**
     * Adds a class to the list of classes in the schedule and updates
     * the number of classes in the schedule
     * @param fitClass the fitness class to be added to the list of classes
     */
    public void addClass(FitnessClass fitClass){
        classes[numOfClasses] = fitClass;
        numOfClasses++;
    }

}
