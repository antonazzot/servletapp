package repository.threadmodelrep.threadservices.updategroupstratagy.crudstrategygroupupdate;

import lombok.RequiredArgsConstructor;
import repository.modelrepository.modelservices.crudrepositorislikeservice.StudentCrudRepository;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageCrud;
import threadmodel.Group;

@RequiredArgsConstructor
public class UpdateGroupStrategyCrudStudentAdd implements UpdateStratageCrud {

    private final StudentCrudRepository studentCrudRepository;

    @Override
    public Group updateGroup(Group group, int[] entytiIdforact) {
        for (int i : entytiIdforact) {
            group.addStudent(studentCrudRepository.findById(i).get());
        }
        return group;
    }
}
