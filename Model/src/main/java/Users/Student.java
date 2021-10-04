package Users;
import DataBase.DataBaseInf;
import ThreadModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends UserImpl {

    private HashMap <Theams, List<Mark>> listOfMark = null;

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

    public  String showInf () {
        String inf =null;
        List<String> stringList = new ArrayList<>();
       for(Map.Entry<Theams, List<Mark>> entry: listOfMark.entrySet()) {
          stringList.add( "Theams " + entry.getKey().name() + " Mark: " + '\n'
           +entry.getValue().toString() + '\n');
                 }
       StringBuilder stringBuilder = new StringBuilder();
        for (String str:
             stringList) {
            stringBuilder = stringBuilder.append( " " ).append(new StringBuilder(str));
        }
       inf = stringBuilder.toString();
       return inf;
    }
    public String aboutStudent () {
     return    toString() + showInf();
    }

    @Override
    public String toString() {
        return "Student: " + this.getName() +
                "have got next profit" +'\n'
                ;
    }




}

