package Users;

import ThreadModel.Mark;
import ThreadModel.Theams;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void addTheam (Theams theams) {
        if (!this.listOfMark.containsKey(theams))
            this.listOfMark.put(theams, new ArrayList<Mark>());
    }

    @Override
    public  String getInf () {
    return toString();
    }

}

