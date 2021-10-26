package Users;
import DataBase.DataBaseInf;
import ThreadModel.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Student extends UserImpl {

    private HashMap <Theams, List<Mark>> listOfMark;


    public Student withName (String name) {
        setName(name);
        return this;
    }
    public Student withLogin (String login) {
        setLogin(login);
        return this;
    }
    public Student withPassword (String password) {
        setPassword(password);
        return this;
    }
    public Student withAge (Integer age) {
        setAge(age);
        return this;
    }
    public Student withTheamMark (HashMap <Theams, List<Mark>> theamsListHashMap) {
        setListOfMark(theamsListHashMap);
        return this;
    }

    public Student addListOfMark (Mark mark) {

        return this;
    }

    public void addTheam (Theams theams) {
        if (!this.listOfMark.containsKey(theams))
            this.listOfMark.put(theams, new ArrayList<Mark>());
    }

    @Override
    public  String getInf () {
    String inf;
    ArrayList <String> strings = new ArrayList<>();
//    for (
//    Map.Entry<Theams, List<Mark>> entry: this.listOfMark.entrySet()){
//        String temp = entry.getKey().name() + ": " + '\n' +
//                Arrays.toString(entry.getValue().stream().
//                        map(m -> m.getValuesOfMark()).
//                        collect(Collectors.toList()).
//                        toString().split("/ "));
//        strings.add("Mark:" + temp);
//        }
    return inf = "ID: " + this.getId() + " Name: " +  this.getName() + " Theams: " + strings.toString();
    }

}

