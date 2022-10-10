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

    public FitnessClass getClass(int indexOfClass){
        return classes[indexOfClass];
    }

    public int getNumOfClasses(){
        return numOfClasses;
    }

    public FitnessClass find(String instructor, Location location, String className){
        for(int i = 0; i<numOfClasses; i++){
            if(classes[i].getInstructor().equals(instructor) && classes[i].getLocation().equals(location) &&
                classes[i].getClass().equals(className)){
                return classes[i];
            }
        }
        return null;
    }

    public void addClass(FitnessClass fitClass){
        classes[numOfClasses] = fitClass;
    }

}
