package Users;

import Action.IdFactory;

public class UserImpl implements User {
    private int id;
    private Role role;
    private String name;
    private String login;
    private String password;
    private int age;

    public UserImpl(Role role, String name, String login, String password, int age) {
        this.id = IdFactory.idBuilder();
        this.role = role;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
