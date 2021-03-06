package users;

import lombok.*;

import javax.persistence.*;

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
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Column(name = "name")
    private String name;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;
    private String email;

    public UserImpl withId(Integer id) {
        setId(id);
        return this;
    }

    public UserImpl withRole(Role role) {
        setRole(role);
        return this;
    }
    public UserImpl withEmail(String email) {
        setEmail(email);
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
