package start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("springmvcconfig");


//        UserRepository userRepository = ctx.getBean(UserRepository.class);
//        UserRepository repository = RepositoryFactory.getRepository();
//        ThreadRepositoryFactory.getRepository().trainerSalary().values().stream()
//              .forEach(System.out::println);
//        RepositoryFactory.getRepository().allTrainer().values()
//                .stream().map(user -> (Trainer)user)
//                .map(trainer -> trainer.getInf())
//                .forEach(System.out::println);
//        Map<UserImpl, Map<Theams, List<Mark>>> userMapMap = ThreadRepositoryFactory.getRepository().studentTheamMark(150);
//        userMapMap.isEmpty();
//        ThreadRepositoryFactory.getRepository().allGroup().values()
//                    .stream().map(group -> group.getInf()).forE  ach(System.out::println);
//        System.out.println(ThreadRepositoryFactory.getRepository().theamById(60));;
//            ThreadRepositoryFactory.getRepository()
//                    .getMarkListbyTheam(
//                            ThreadRepositoryFactory
//                                    .getRepository()
//                                    .theamById(60), 150)
//                    .stream()
//                    .map(Mark::getValuesOfMark)
//                    .forEach(System.out::println);


//        System.out.println(RepositoryFactory.getRepository().getTrainerById(181).getInf());

//                RepositoryFactory.getRepository()
//                        .allStudent().
//                        values().
//                        stream().
//                        map(UserImpl::getInf).forEach(System.out::println);


//        ThreadRepositoryFactory.getRepository().addSalaryToTrainer(264, 77777);
//        RepositoryFactory.getRepository().getTrainerById(264).getSalarylist().forEach(System.out::println);
//        ThreadRepositoryFactory.getRepository().addMarkToStudent(150, 60, 99);
//        System.out.println(ThreadRepositoryFactory.getRepository().theamById(60));
//        System.out.println("---------------->>>>>>>>"+repository);
//        repository.allStudent().values().stream().map(s->s.getInf()+"***************").forEach(System.out::println);
//
//         UserFunctionJpa.getAllUser().values().stream().map(s->s.getInf()).forEach(System.out::println);
//        UserImpl user = RepositoryFactory.getRepository().allUser().values().stream()
//                .filter(u -> u.getLogin().equals("Admin") && u.getPassword().equals("pass"))
//                .findFirst().orElse(null);
//        UserImpl user1 = new UserImpl().withName("name").withLogin("login").withPassword("passs").withAge(130);
//        RepositoryFactory.getRepository().saveUser(user1);
//         System.out.println(user.getInf());


//        Administrator administrator = (Administrator) new Administrator()
//                .withRole(Role.ADMINISTRATOR)
//                .withName("Anton")
//                .withLogin("Admin")
//                .withPassword("pass")
//                .withAge(34);
//        if (RepositoryFactory.getRepository().allUser().values().stream()
//                .filter(user -> user.getLogin()!=null
//                        &&  user.getName()!=null
//                        &&  user.getPassword()!=null)
//                .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
//                        u.getPassword().equals(administrator.getPassword()) &&
//                        u.getName().equals(administrator.getName()) &&
//                        u.getAge() == administrator.getAge())
//        )
//            System.out.println("++++++++++++");
//    }
    }
}
