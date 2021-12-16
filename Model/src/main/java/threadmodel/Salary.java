package threadmodel;

import lombok.*;
import users.Trainer;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table (name = "salary")
public class Salary {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "salary_value")
    private BigDecimal bigDecimalSalary;
    @ManyToOne
    @JoinColumn (name = "trainer_id")
    private Trainer trainer;

    public Salary withValue(Integer value) {
        setBigDecimalSalary(new BigDecimal(value));
        return this;
    }

    public Salary withTrainer (Trainer trainer) {
        setTrainer(trainer);
        return this;
    }

}
