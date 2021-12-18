package controller.serviseforcontroller.actadminstratagy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MVCAdminActWatchStratagyImpl implements MVCAdminActStratagy {
    @Override
    public String watchEntity(String entity, Model model, String deleteId) {
        if (!entity.equals("group") && !entity.equals("theam")) {
            model.addAttribute("map", forDemonstratePage(entity));
            return "demonstrate";
        } else if (!entity.equals("theam")) {
            log.info("Watch group ={}", entity);
            model.addAttribute("map", groupStringHashMap());
            return "adminviews/demonstrategroup";
        } else {
            model.addAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminviews/theamdemonstrate";
        }

        }


    /**
     * This method  given  data for demonstrate  information about group
     **/
    private Map<Group, Map<List<Theams>, List<Student>>> groupStringHashMap() {
        HashMap<Group, Map<List<Theams>, List<Student>>> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Group> entry : ThreadRepositoryFactory.getRepository().allGroup().entrySet()) {
            Group group = entry.getValue();
            List<Theams> theams = new ArrayList<>(ThreadRepositoryFactory.getRepository().theamFromGroup(group.getId()));
            List<Student> students = RepositoryFactory.getRepository().studentFromGroup(group.getId());
            hashMap.put(group, new HashMap<>(Map.of(theams, students)));
        }
        return hashMap;
    }
    /**
     * This method providing represent of information about
     * user with @mapWithInf() method
     **/
    private Map<Integer, UserImpl> forDemonstratePage (String entity) {
        Map<Integer, UserImpl> result = new HashMap<>();
        switch (entity) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }
}
