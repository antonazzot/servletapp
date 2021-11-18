package users;

import lombok.*;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;

import javax.naming.Name;
import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = "studenById", query = "select s from Student s where s.id = :id")
        }
)
public class Student extends UserImpl {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable   (
            name = "student_group",
            joinColumns = @JoinColumn (name = "student_id"),
            inverseJoinColumns = @JoinColumn (name = "group_id")
    )
    private Set <Group> groupSet;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_mark",
            joinColumns = @JoinColumn (name = "student_id"),
            inverseJoinColumns = @JoinColumn (name = "mark_id")

    )
    @MapKey
    private Map <Integer, Mark> markMap = new HashMap<>();
    @Transient
    private Map<Theams, List<Mark>> listOfMark;

    public Student withName(String name) {
        setName(name);
        return this;
    }

    public Student withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Student withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Student withAge(Integer age) {
        setAge(age);
        return this;
    }

    public Student withTheamMark(HashMap<Theams, List<Mark>> theamsListHashMap) {
        setListOfMark(theamsListHashMap);
        return this;
    }

    public void addTheam(Theams theams) {
        if (!this.listOfMark.containsKey(theams))
            this.listOfMark.put(theams, new ArrayList<Mark>());
    }

    @Override
    public String getInf() {
        return toString();
    }

}

