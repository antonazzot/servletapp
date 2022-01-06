package interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;

@Slf4j
@Component
public class LogingIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY={}", "ttttttttttttttttttttttttttt");
        LoggingURL(request);
        LoggingBody(request);
        return true;
    }

    private void LoggingBody(HttpServletRequest request) throws IOException {
        RealContentCashingWrapper reqWrapper = (RealContentCashingWrapper) request;
        String body = new String(reqWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        log.debug("*************Request Body***************: \n {}", body);
    }

    private void LoggingURL(HttpServletRequest request) {
        log.info("->>>>>>>>> {}, {}", request.getMethod(), ServletUriComponentsBuilder.fromRequest(request).toUriString());
    }

}
