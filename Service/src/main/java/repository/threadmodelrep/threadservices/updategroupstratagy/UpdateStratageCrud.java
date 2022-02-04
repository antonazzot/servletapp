package repository.threadmodelrep.threadservices.updategroupstratagy;

import helperutils.myexceptionutils.MyJpaException;
import threadmodel.Group;

import javax.persistence.EntityManager;

public interface UpdateStratageCrud {
    Group updateGroup (Group group, int[] entytiIdforact);
}
