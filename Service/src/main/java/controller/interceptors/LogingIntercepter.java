package controller.interceptors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
//@Component
public class LogingIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY={}", "ttttttttttttttttttttttttttt");
        LoggingURL(request);
        LoggingBody(request);
        LogingHeders(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle={}", "!!!!");
        ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
        String body = new String(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
        log.info("*************Response Body***************: \n {}", body);
    }

    private void LogingHeders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.debug("[{}]: {}", name, request.getHeader(name));
        }
    }

    @SneakyThrows
    private void LoggingBody(HttpServletRequest request) {
        RealContentCashingWrapper reqWrapper = (RealContentCashingWrapper) request;
        String body = new String(reqWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        log.info("*************Request Body***************: \n {}", body);
    }

    private void LoggingURL(HttpServletRequest request) {
        log.info("->>>>>>>>> {}, {}", request.getMethod(), ServletUriComponentsBuilder.fromRequest(request).toUriString());
    }

}
