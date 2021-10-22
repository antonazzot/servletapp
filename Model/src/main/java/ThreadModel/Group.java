package ThreadModel;

import Action.IdFactory;
import DataBase.DataBaseInf;
import Users.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Group {
    private int id;
    private String name;
    private UserImpl trainer;
    Map<Integer, Student> studentMap;
    Set<Theams> theamsSet;

    public Group withId(Integer id) {
        setId(id);
        return this;
    }
    public Group withName(String name) {
        setName(name);
        return this;
    }

    public Group withTrainer(UserImpl user) {
        setTrainer(user);
        return this;
    }

    public Group addStudent(Student student) {
        this.getStudentMap().put(student.getId(), student);
        return this;
    }

    public Group addTheam(Theams theams) {
        this.getTheamsSet().add(theams);
        return this;
    }

    public String getInf() {
        return toString() + name + id + studentMap.values().toString();

    }
}


