package threadmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NamedQueries(
        {
                @NamedQuery(name = "getTheamById", query = "select t from Theams t where t.id = :id")
        }
)
public class Theams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "theam_name")
    private String theamName;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinTable (
            name = "theam_group",
            joinColumns = @JoinColumn (name = "theam_id"),
            inverseJoinColumns = @JoinColumn (name = "group_id")
    )
    @JsonIgnore
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
