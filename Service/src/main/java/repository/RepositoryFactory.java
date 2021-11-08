package repository;

import repository.modelrepository.UserRepository;
import repository.hibernaterepositiry.UserRepositoryImplHibernate;
import repository.modelrepository.UserRepositoryImplInMemory;
import repository.modelrepository.UserRepositoryImplPostgres;

import java.io.IOException;
import java.util.Properties;

public class RepositoryFactory {
    private static  final RepositoryTypes TYPES;

    static  {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TYPES = RepositoryTypes.getTypeByStr(properties.getProperty("repository.type"));
    }

    private RepositoryFactory() {

    }

    public static UserRepository getRepository () {

        switch (TYPES) {
            case MEMORY:
                return  UserRepositoryImplInMemory.getInstance();

            case POSTGRES:
                return UserRepositoryImplPostgres.getInstance();

            case HIBERNATE:
                return UserRepositoryImplHibernate.getInstance();

            default:
                throw new IllegalStateException("Unexpected value: " + TYPES);
        }

    }
}
