package threadmodel;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode

public class Salary {
    private BigDecimal bigDecimalSalary;

    public Salary withValue(Integer value) {
        setBigDecimalSalary(new BigDecimal(value));
        return this;
    }

}
