package ThreadModel;

import Action.IdFactory;
import DataBase.DataBaseInf;
import Users.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Group {
    private String name;
    private int id;
    private Trainer trainer;
    Map <Integer, Student> studentMap;
    Set <Theams> theamsSet;

    public Group( Trainer trainer, Map<Integer, Student> studentMap) {
        this.id = IdFactory.idBuilder();
        this.trainer = trainer;
        this.studentMap = studentMap;
        this.name = "Group_"+Integer.toString(this.id);
        DataBaseInf.groupHashMap.put(this.id, this);
    }

    public Group(Trainer trainer, Map<Integer, Student> studentMap, Set<Theams> theamsSet) {
        this.id = IdFactory.idBuilder();
        this.trainer = trainer;
        this.studentMap = studentMap;
        this.theamsSet = theamsSet;
        this.name = "Group_"+Integer.toString(this.id);
        DataBaseInf.groupHashMap.put(this.id, this);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getInf ( ) {
        return  "***" + '\n' + "Treiner: " + trainer.getInf() + " " +
                " consist of next Student: " + '\n'+ "***********"+ '\n'+
                studentMap.values().stream().map(s->s.getInf()).peek(str->str.concat(" ****** ")).toString() +
                " " + '\n'+
                     + '\n' +
                    " *****************" +
                        +'\n' +
                theamsSet.toArray().toString();

    }

    @Override
    public String toString() {
        return "Group  " + id + "  head trainer: " + trainer.getName() + '\n' +
                " consist of next Student: " +'\n' +
                 "------------------" +'\n'+
                studentMap.values().stream().map(s->s.getName()).collect(Collectors.toList()).toArray().toString()  +
                '\n'+   ", Group contains teams " + '\n' +
                 "------------------" + '\n' +
                theamsSet.toArray().toString()
                ;
    }
}
