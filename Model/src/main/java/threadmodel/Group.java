package threadmodel;

import users.Student;
import users.UserImpl;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Group {
    private int id;
    private String name;
    private UserImpl trainer;
    Map<Integer, UserImpl> studentMap;
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

    public Group withStudents (HashMap <Integer, UserImpl> students) {
        setStudentMap(students);
        return this;
    }

    public Group withTheam (Set <Theams> theams) {
        setTheamsSet(theams);
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


