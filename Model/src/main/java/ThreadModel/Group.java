package ThreadModel;

import DataBase.DataBaseInf;
import Users.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void getInf ( ) {
        String inf = "***" + '\n' + "Treiner: " + trainer.getName() + " " +
                " consist of next Student: " + studentMap.values().stream().map( s->s.getName()).
        collect(Collectors.toList()).toString() + " "
        + '\n' +
        " *****************" +
        +'\n' +
                theamsSet.toArray().toString();

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
