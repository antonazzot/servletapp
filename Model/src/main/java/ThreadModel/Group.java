package ThreadModel;

import DataBase.DataBaseInf;
import Users.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Group {
    private int id;
    private Trainer trainer;
    Map <Integer, Student> studentMap;
    Set <Theams> theamsSet;

    public Group(int id, Trainer trainer, Map<Integer, Student> studentMap) {
        this.id = id;
        this.trainer = trainer;
        this.studentMap = studentMap;
    }

    public Group(int id, Trainer trainer, Map<Integer, Student> studentMap, Set<Theams> theamsSet) {
        this.id = id;
        this.trainer = trainer;
        this.studentMap = studentMap;
        this.theamsSet = theamsSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Map<Integer, Student> getStudentMap() {
        return studentMap;
    }

    public void setStudentMap(Map<Integer, Student> studentMap) {
        this.studentMap = studentMap;
    }

    public Set<Theams> getTheamsSet() {
        return theamsSet;
    }

    public void setTheamsSet(Set<Theams> theamsSet) {
        this.theamsSet = theamsSet;
    }

    public void aboutGroup ( ) {
        System.out.println("***");
        System.out.println("Group " + id);
        System.out.println();
        System.out.println(" head trainer: " + trainer.getName());
        System.out.println(" consist of next Student: "  + "------------------");

       for(Map.Entry<Integer, Student> entry:  studentMap.entrySet()) {
           System.out.println("Student ID " + entry.getKey().toString() + ": "  );
           entry.getValue().aboutStudent();
        }

        System.out.println("****************");
        for (Theams t:
             theamsSet) {
            System.out.println(t.name());
        }
    }

    @Override
    public String toString() {
        return "Group " + id + " head trainer: " + trainer.getName() + " consist of next Student: "
                + "------------------" +
                studentMap.toString() +
                ", Group contains teams " +
                 "------------------" +
                theamsSet.toString()
                ;
    }
}
