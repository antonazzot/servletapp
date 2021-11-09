package threadmodel;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mark_value")
    private int valuesOfMark;

    public Mark withId(Integer id) {
        setId(id);
        return this;
    }

    public Mark withValue(Integer value) {
        setValuesOfMark(value);
        return this;
    }

}
