package servlets.servletstratagy.watchservletstratagy;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.UserImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WatchServletChangeStratagyImpl implements WatchServletStratagy {
    @Override
    public String watchEntity(String entity, HttpServletRequest req) {

        if (!entity.equals("group") && !entity.equals("theam")) {
            log.info("Change entity ={}", entity);
            req.setAttribute("map", this.mapToChange(entity));
            return "adminControl/changeUser.jsp";
        } else if (entity.equals("theam")) {
            req.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
            return "adminControl/changetheam.jsp";
        } else if (entity.equals("group")) {
            req.setAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
            return "adminControl/changeGroup.jsp";
        } else {
            return "exception.jsp";
        }
    }
    /**
     * This method given data for future users change
     **/
    private Map<Integer, UserImpl> mapToChange(String user) {
        Map<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                log.info("Watch student ={}", user);
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }
}
