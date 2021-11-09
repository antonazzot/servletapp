package threadmodel;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "theam")
public class Theams {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "theam_name")
    private String theamName;

    public Theams withId(int id) {
        setId(id);
        return this;
    }

    public Theams withValue(String theamName) {
        setTheamName(theamName);
        return this;
    }

}
