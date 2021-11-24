package servlets.servletstratagy.watchservletstratagy;

import javax.servlet.http.HttpServletRequest;

public interface WatchServletStratagy {
    String watchEntity(String entity , HttpServletRequest req);
}
