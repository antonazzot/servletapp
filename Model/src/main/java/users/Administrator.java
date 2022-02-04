package users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "persons")
@NamedQueries(
        {
                @NamedQuery(name = "adminById", query = "select a from Administrator a where a.id = :id")
        }
)
public class Administrator extends UserImpl {

    public Administrator withName(String name) {
        setName(name);
        return this;
    }

    public Administrator withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Administrator withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Administrator withAge(Integer age) {
        setAge(age);
        return this;
    }

    @Override
    public String getInf() {
        return super.getInf();
    }
}
