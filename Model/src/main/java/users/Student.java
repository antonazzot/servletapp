package users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "persons")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NamedQueries(
        {
                @NamedQuery(name = "studenById", query = "select s from Student s where s.id = :id")
        }
)
public class Student extends UserImpl {
    @ManyToMany
    @JoinTable(
            name = "student_group",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private Set<Group> groupSet;
    @OneToMany
    @JoinTable(
            name = "student_mark",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    @MapKey
    @JsonIgnore
    private Map<Integer, Mark> markMap = new HashMap<>();
    @Transient
    @JsonIgnore
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

