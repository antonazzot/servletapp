package users;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
public class UserImpl implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "postgres.id")
    private int id;
    @Column (name = "postgres.role_id")
    @OneToOne
    private Role role;
    @Column(name = "postgres.name")
    private String name;
    @Column(name = "postgres.login")
    private String login;
    @Column(name = "postgres.password")
    private String password;
    @Column(name = "postgres.age")
    private int age;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id1;

    @Column(name = "name", nullable = false, length = 50)
    private String name1;

    @Column(name = "login", length = 50)
    private String login1;

    @Column(name = "password", length = 50)
    private String password1;

    @Column(name = "age")
    private Integer age1;

    public Integer getAge1() {
        return age1;
    }

    public void setAge1(Integer age1) {
        this.age1 = age1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getLogin1() {
        return login1;
    }

    public void setLogin1(String login1) {
        this.login1 = login1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Role getRole1() {
        return role1;
    }

    public void setRole1(Role role1) {
        this.role1 = role1;
    }

    public UserImpl withId (Integer id) {
        setId(id);
        return this;
    }
    public UserImpl withRole (Role role) {
        setRole(role);
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
        return "ID " +  getId() + " NAME: " + getName() + "Login: " + getLogin() +
              " ROLE: " + getRole() + " AGE: " +  getAge() + " PASSWORD: " + getPassword();
    }
}
