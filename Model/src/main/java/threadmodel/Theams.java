package threadmodel;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "theam")
public class Theams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "theam_name")
    private String theamName;
    @ManyToOne
    @JoinTable (
            name = "theam_group",
            joinColumns = @JoinColumn (name = "theam_id"),
            inverseJoinColumns = @JoinColumn (name = "group_id")
    )
    private Group group;

    public Theams withId(int id) {
        setId(id);
        return this;
    }

    public Theams withValue(String theamName) {
        setTheamName(theamName);
        return this;
    }

}
