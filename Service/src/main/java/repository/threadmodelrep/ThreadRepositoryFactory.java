package repository.threadmodelrep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:app.properties")
public class ThreadRepositoryFactory {

    @Value("${repository.type}")
    String threadRepositoryName;

    private static ThreadRepository threadRepository;
    @Autowired
    Map<String, ThreadRepository> repositoryMap;
    @PostConstruct
    private void start () {
     String name = repositoryMap.keySet().stream()
              .filter(s -> s.contains(threadRepositoryName)).collect(Collectors.joining());
      ThreadRepositoryFactory.threadRepository=repositoryMap.get(name);
    }

    public static ThreadRepository getRepository() {
        return threadRepository;
    }
}

