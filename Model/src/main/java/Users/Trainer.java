package Users;

import DataBase.*;
import ThreadModel.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Trainer extends UserImpl {

    private List<Salary> salarylist = new ArrayList<>();

    public Trainer withName (String name) {
        setName(name);
        return this;
    }
    public Trainer withLogin (String login) {
        setLogin(login);
        return this;
    }
    public Trainer withPassword (String password) {
        setPassword(password);
        return this;
    }
    public Trainer withAge (Integer age) {
        setAge(age);
        return this;
    }
    public Trainer addSalary (Salary salary) {
        getSalarylist().add(salary);
        return this;
    }
    public Trainer withSalary (List <Salary> salarylist) {
        setSalarylist(salarylist);
        return this;
    }
    @Override
    public String getInf() {
        long l = 0;
        if (this.salarylist.size() != 0) {
            l = this.salarylist.stream().map(salary ->
                    salary.getBigDecimalSalary()).count() / (long) this.salarylist.size();
        }
        return super.getInf() + "  " + salarylist.toString();
    }
}

