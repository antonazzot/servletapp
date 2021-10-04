package Users;

import DataBase.DataBaseInf;

public class Administrator extends UserImpl {

    public Administrator(String name, String login, String password, int age) {
        super(Role.ADMINISTRATOR, name, login, password, age);
        DataBaseInf.adminHashMap.put(this.getId(), this);
    }
}
