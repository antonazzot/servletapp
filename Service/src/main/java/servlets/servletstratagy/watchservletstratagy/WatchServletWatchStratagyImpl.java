package servlets.servletstratagy.watchservletstratagy;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WatchServletWatchStratagyImpl implements WatchServletStratagy {
    @Override
    public String watchEntity(String entity, HttpServletRequest req) {

        if (!entity.equals("group") && !entity.equals("theam")) {
            req.setAttribute("map", forDemonstratePage(entity));
            return "demonstrate.jsp";
        } else if (!entity.equals("theam")) {
            log.info("Watch group ={}", entity);
            req.setAttribute("map", groupStringHashMap());
            return "adminControl/demonstrategroup.jsp";
        } else {
            req.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminControl/theamdemonstrate.jsp";
        }
    }
    /**
     * This method  given  data for demonstrate  information about group
     **/
    private HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<Student>>> groupStringHashMap() {
        HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<Student>>> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Group> entry : ThreadRepositoryFactory.getRepository().allGroup().entrySet()) {
            Group group = entry.getValue();
            ArrayList<Theams> theams = new ArrayList<>(ThreadRepositoryFactory.getRepository().theamFromGroup(group.getId()));
            ArrayList<Student> students = RepositoryFactory.getRepository().studentFromGroup(group.getId());
            hashMap.put(group, new HashMap<>(Map.of(theams, students)));
        }
        return hashMap;
    }
    /**
     * This method providing represent of information about
     * user with @mapWithInf() method
     **/
    private HashMap<Integer, UserImpl> forDemonstratePage(String user) {
        HashMap<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
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
