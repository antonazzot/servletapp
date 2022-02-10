package controller.serviseforcontroller.senderservice;

import repository.RepositoryFactory;
import users.TempStudent;
import users.User;
import users.UserImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrepareToSend {
    public static List <String> getUserTo (String toUsers) {
        Map<String, List<String>> result = Map.of(
                "student", RepositoryFactory.getRepository().allStudent().values().stream().map(UserImpl::getEmail).collect(Collectors.toList()),
                "trainer", RepositoryFactory.getRepository().allTrainer().values().stream().map(UserImpl::getEmail).collect(Collectors.toList()),
                "administrator", RepositoryFactory.getRepository().allAdmin().values().stream().map(UserImpl::getEmail).collect(Collectors.toList()),
                "tempstudent", RepositoryFactory.getRepository().allTemp().values().stream().map(TempStudent::getEmail).collect(Collectors.toList())
        );
        return result.get(toUsers);
    }
}
