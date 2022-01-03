package users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import threadmodel.Salary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "salarylist")
@ToString (callSuper = true, exclude = "salarylist")
@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = "trainerById", query = "select t from Trainer t where t.id = :id")
        }
)
public class Trainer extends UserImpl {
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Salary> salarylist = new ArrayList<>();

    public Trainer withName(String name) {
        setName(name);
        return this;
    }

    public Trainer withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Trainer withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Trainer withAge(Integer age) {
        setAge(age);
        return this;
    }

    public Trainer addSalary(Salary salary) {
        getSalarylist().add(salary);
        return this;
    }

    public Trainer withSalary(List<Salary> salarylist) {
        setSalarylist(salarylist);
        return this;
    }

    @Override
    public String getInf() {
        return super.getInf() + "  " + salarylist.toString();
    }
}

