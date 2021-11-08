package users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;


@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
public class Administrator extends UserImpl {

    public Administrator withName (String name) {
        setName(name);
        return this;
    }
    public Administrator withLogin (String login) {
        setLogin(login);
        return this;
    }
    public Administrator withPassword (String password) {
        setPassword(password);
        return this;
    }
    public Administrator withAge (Integer age) {
        setAge(age);
        return this;
    }

    @Override
    public String getInf () {
        return super.getInf();
    }
  }