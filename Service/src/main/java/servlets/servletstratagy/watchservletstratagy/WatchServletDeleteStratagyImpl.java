package servlets.servletstratagy.watchservletstratagy;

import repository.RepositoryFactory;

import javax.servlet.http.HttpServletRequest;

public class WatchServletDeleteStratagyImpl implements WatchServletStratagy {
    @Override
    public String watchEntity(String entity, HttpServletRequest req) {
        int entityId;
        try {
            entityId = Integer.parseInt(req.getParameter("id"));
        } catch (IllegalArgumentException var5) {
            return "exception.jsp";
        }

        RepositoryFactory.getRepository().removeUser(entityId, entity);
        return "adminControl/adminActList.jsp";
    }
}

