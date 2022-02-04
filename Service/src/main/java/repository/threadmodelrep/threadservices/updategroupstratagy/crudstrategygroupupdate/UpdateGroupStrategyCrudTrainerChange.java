package repository.threadmodelrep.threadservices.updategroupstratagy.crudstrategygroupupdate;

import lombok.AllArgsConstructor;
import repository.modelrepository.modelservices.crudrepositorislikeservice.TrainerCrudRepository;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageCrud;
import threadmodel.Group;

@AllArgsConstructor
public class UpdateGroupStrategyCrudTrainerChange implements UpdateStratageCrud {
    private final TrainerCrudRepository trainerCrudRepository;

    @Override
    public Group updateGroup(Group group, int[] entytiIdforact) {
        group.setTrainer(trainerCrudRepository.findById(entytiIdforact[0]).get());
        return group;
    }
}
