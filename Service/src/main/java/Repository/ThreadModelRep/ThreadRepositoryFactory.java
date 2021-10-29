package Repository.ThreadModelRep;

import Repository.RepositoryTypes;
import Repository.UserRepository;
import Repository.UserRepositoryImplInMemory;
import Repository.UserRepositoryImplPostgres;

import java.io.IOException;
import java.util.Properties;

public class ThreadRepositoryFactory {
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

    private ThreadRepositoryFactory() {

    }

    public static ThreadRepository getRepository () {
        switch (TYPES) {
            case MEMORY:  return  ThreadRepositoryImplInMemory.getInstance();
            case POSTGRES: return ThreadRepositoryImpl.getInstance();

        }
        return ThreadRepositoryImplInMemory.getInstance();
    }
}

