package servlets.servletstratagy;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class WatchServletCreateStratagyImpl implements WatchServletStratagy {
    @Override
    public String watchEntity(String entity, HttpServletRequest req) {

        if (!entity.equals("group") && !entity.equals("theam")) {
            log.info("Create user ={}", entity);
            req.setAttribute("role", entity);
            return "adminControl/adduserpage.jsp";
        } else if (!entity.equals("theam")) {
            log.info("Create group ={}", entity);
            req.setAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
            req.setAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
            req.setAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
            return "adminControl/theamscreatelist.jsp";
        } else {
            return "adminControl/theamadd.jsp";
        }
    }
}
