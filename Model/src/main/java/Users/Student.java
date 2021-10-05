package Users;
import DataBase.DataBaseInf;
import ThreadModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Student extends UserImpl {

    private HashMap <Theams, List<Mark>> listOfMark;

    public Student( String name, String login, String password, int age, HashMap<Theams, List<Mark>> listOfMark) {
        super( Role.STUDENT, name, login, password, age);
        this.listOfMark = listOfMark;
        DataBaseInf.studentHashMap.put(this.getId(), this);
    }

    public HashMap<Theams, List<Mark>> getListOfMark() {
        return listOfMark;
    }

    public void setListOfMark(HashMap<Theams, List<Mark>> listOfMark) {
        this.listOfMark = listOfMark;
    }

    public  String getInf () {
    String inf;
    ArrayList <String> strings =null;
    for (
    Map.Entry<Theams, List<Mark>> entry: this.listOfMark.entrySet()){
        String temp = entry.getKey().name() + ": " + '\n' +
               entry.getValue().stream().map(m -> m.getValuesOfMark()).collect(Collectors.toList()).toString().split("/ ");
        strings.add(temp);
        }
    return inf = this.getName() + strings.toString();
    }

    @Override
    public String toString() {
        return "Student: " + this.getName() +
                "have got next profit" +'\n'
                ;
    }




}

