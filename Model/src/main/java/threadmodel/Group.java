package threadmodel;

import lombok.*;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString ( exclude = {"theamsSet", "studentMap"})
@EqualsAndHashCode ( exclude = {"theamsSet", "studentMap"})
@Entity
@Table (name = "gr_oup")

public class Group {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne
    @JoinColumn (name = "trainer_id", referencedColumnName = "id")
    private Trainer trainer;
    @ManyToMany
    @JoinTable (
            name = "student_group",
            joinColumns = @JoinColumn (name = "group_id"),
            inverseJoinColumns = @JoinColumn (name = "student_id")

    )
    @MapKey
    private Map<Integer, Student> studentMap;
    @OneToMany
    @JoinTable (
            name = "theam_group",
            joinColumns = @JoinColumn (name = "group_id"),
            inverseJoinColumns = @JoinColumn (name = "theam_id")
    )
    private Set<Theams> theamsSet;

    public Group withId(Integer id) {
        setId(id);
        return this;
    }

    public Group withName(String name) {
        setName(name);
        return this;
    }

    public Group withTrainer(UserImpl user) {
        setTrainer((Trainer) user);
        return this;
    }

    public Group withStudents(Map<Integer, Student> students) {
        setStudentMap(students);
        return this;
    }

    public Group withTheam(Set<Theams> theams) {
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


