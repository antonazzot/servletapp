package Repository.DAO;

import Users.Role;
import Users.User;
import Users.UserImpl;

public abstract class Dao  {
    public abstract void createUser (Role role, String name, String login, String pass, int age);
    public abstract UserImpl getUser (int id);
    public abstract void updateUser (int id, User user);
    public abstract void deleteUser (int id);
}
