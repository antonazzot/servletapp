package repository.threadmodelrep.threadservices.updategroupstratagy.crudstrategygroupupdate;

import lombok.RequiredArgsConstructor;
import repository.threadmodelrep.threadservices.crudthreadservices.TheamCrudRepository;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageCrud;
import threadmodel.Group;

@RequiredArgsConstructor
public class UpdateGroupStrategyCrudTheamDelete implements UpdateStratageCrud {

    private final TheamCrudRepository theamCrudRepository;

    @Override
    public Group updateGroup(Group group, int[] entytiIdforact) {
        for (int i : entytiIdforact) {
            group.getTheamsSet().remove(theamCrudRepository.findById(i));
        }
        return group;
    }
}
