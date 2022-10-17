package projecttwo;

public class ClassSchedule {

    private FitnessClass[] classes;
    private int numOfClasses;

    public ClassSchedule(){
        classes = new FitnessClass[15];
        numOfClasses = 0;
    }

    public FitnessClass[] returnList(){
        return classes;
    }

    public int getNumOfClasses(){
        return numOfClasses;
    }


    public void addClass(FitnessClass fitClass){
        classes[numOfClasses] = fitClass;
        numOfClasses++;
    }

}
