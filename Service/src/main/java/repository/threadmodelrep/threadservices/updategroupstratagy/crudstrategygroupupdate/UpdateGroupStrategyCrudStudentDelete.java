package repository.threadmodelrep.threadservices.updategroupstratagy.crudstrategygroupupdate;

import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageCrud;
import threadmodel.Group;

public class UpdateGroupStrategyCrudStudentDelete implements UpdateStratageCrud {

    @Override
    public Group updateGroup(Group group, int[] entytiIdforact) {
        for (int i : entytiIdforact) {
            group.getStudentMap().remove(i);
        }
        return group;
    }
}
