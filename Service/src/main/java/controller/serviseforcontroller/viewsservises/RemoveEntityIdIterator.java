package controller.serviseforcontroller.viewsservises;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
@Slf4j
public class RemoveEntityIdIterator {
    public static void iterateAndDeleteEntity (Integer [] id, String entity )  {
        for (Integer integer : id) {
            log.info("entity for delete#### ={}", integer +" ---- "+ entity);
        }

        for (Integer integer : id) {
            RepositoryFactory.getRepository().removeUser(integer, entity);
        }

    }
}
