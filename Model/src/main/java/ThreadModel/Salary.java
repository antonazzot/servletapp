package ThreadModel;

import java.math.BigDecimal;

public class Salary {
   private BigDecimal bigDecimalSalary;

    public Salary(BigDecimal bigDecimalSalary) {
        this.bigDecimalSalary = bigDecimalSalary;
    }

    public BigDecimal getBigDecimalSalary() {
        return bigDecimalSalary;
    }

    public void setBigDecimalSalary(BigDecimal bigDecimalSalary) {
        this.bigDecimalSalary = bigDecimalSalary;
    }
}
