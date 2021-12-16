package repository.modelrepository.modelfunction;

import users.Role;

public class RoleIDParametrCheker {

    private static final int STUDENT_ROLE_ID = 2;
    private static final int TRAINER_ROLE_ID = 1;
    private static final int ADMINISTRATOR_ROLE_ID = 0;

    public static Role checkRole(int role_id) {
        switch (role_id) {
            case ADMINISTRATOR_ROLE_ID:
                return Role.ADMINISTRATOR;
            case TRAINER_ROLE_ID:
                return Role.TRAINER;
            case STUDENT_ROLE_ID:
            default:
                return Role.STUDENT;
        }

    }

    public static int userGetRoleForDB(Role role) {
        if (Role.ADMINISTRATOR.equals(role)) {
            return ADMINISTRATOR_ROLE_ID;
        } else if (Role.TRAINER.equals(role)) {
            return TRAINER_ROLE_ID;
        }
        return STUDENT_ROLE_ID;
    }
}
