package repository.threadmodelrep.threadfunction.updategroupstratagy;

import helperutils.MyExceptionUtils.MyJpaException;
import threadmodel.Group;

import javax.persistence.EntityManager;

public interface UpdateStratageJpa {
    void updateGroup (Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException;
}