package threadmodel;

import lombok.*;
import users.Student;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString (exclude = "student")
@EqualsAndHashCode (exclude = "student")
@Entity
@Table(name = "mark")
@NamedQueries(
        {
                @NamedQuery(name = "getMarkById", query = "select m from Mark m where m.id = :id")
        }
)
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mark_value")
    private int valuesOfMark;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_mark",
            joinColumns = @JoinColumn (name = "mark_id"),
            inverseJoinColumns = @JoinColumn (name = "student_id")
    )
    private Student student;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "theam_id")
    private Theams theams;

    public Mark withId(Integer id) {
        setId(id);
        return this;
    }

    public Mark withValue(Integer value) {
        setValuesOfMark(value);
        return this;
    }

    public Mark withStudent (Student student) {
        setStudent(student);
        return this;
    }

    public Mark withTheam (Theams theam) {
        setTheams(theam);
        return this;
    }

}
