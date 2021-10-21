package Users;

import Action.IdFactory;
import lombok.*;

import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class UserImpl implements User {
    private int id;
    private Role role;
    private String name;
    private String login;
    private String password;
    private int age;

    public UserImpl withId (Integer id) {
        this.id =id;
        return this;
    }
    public UserImpl withRole (Role role) {
        this.role =role;
        return this;
    }
    public UserImpl withName (String name) {
        setName(name);
        return this;
    }
    public UserImpl withLogin (String login) {
        setLogin(login);
        return this;
    }
    public UserImpl withPassword (String password) {
        setPassword(password);
        return this;
    }
    public UserImpl withAge (Integer age) {
        setAge(age);
        return this;
    }

    @Override
    public String getInf() {
        return "ID " +  getId() + " NAME: " + getName() +
              " ROLE: " + getRole() + " AGE: " +  getAge() + " PASSWORD: " + getPassword();
    }
}
