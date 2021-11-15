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
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mark_value")
    private int valuesOfMark;
    @ManyToOne
    @JoinTable(
            name = "student_mark",
            joinColumns = @JoinColumn (name = "mark_id"),
            inverseJoinColumns = @JoinColumn (name = "student_id")
    )
    private Student student;
    @ManyToOne
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

}
