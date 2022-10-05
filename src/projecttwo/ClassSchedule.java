package projecttwo;

public class ClassSchedule {

    private FitnessClass[] classes;
    private int numOfClasses;

    public ClassSchedule(int numOfClasses){
        classes = new FitnessClass[numOfClasses];
        this.numOfClasses = numOfClasses;
    }

    public FitnessClass[] returnList(){
        return classes;
    }

    public FitnessClass getClass(int indexOfClass){
        return classes[indexOfClass];
    }

}
