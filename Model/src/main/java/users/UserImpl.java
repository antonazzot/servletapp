package users;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@NamedQueries(
//        {
//                @NamedQuery(name = "all", query = "select t from Trainer t where t.role = :role_id")
//        }
//)

public class UserImpl implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "role_id")
    @Enumerated (EnumType.ORDINAL)
    private Role role;
    @NotEmpty(message = "It not be empty")
    @Size(min = 2, max = 100, message = "It must be beetwen 2 to 100")
    @Column(name = "name")
    private String name;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    @PositiveOrZero(message = "it must be positive or zero")
    private int age;

    public UserImpl withId(Integer id) {
        setId(id);
        return this;
    }

    public UserImpl withRole(Role role) {
        setRole(role);
        return this;
    }

    public UserImpl withName(String name) {
        setName(name);
        return this;
    }

    public UserImpl withLogin(String login) {
        setLogin(login);
        return this;
    }

    public UserImpl withPassword(String password) {
        setPassword(password);
        return this;
    }

    public UserImpl withAge(Integer age) {
        setAge(age);
        return this;
    }

    @Override
    public String getInf() {
        return "ID " + getId() + " NAME: " + getName() + "Login: " + getLogin() +
                " ROLE: " + getRole() + " AGE: " + getAge() + " PASSWORD: " + getPassword();
    }
}
