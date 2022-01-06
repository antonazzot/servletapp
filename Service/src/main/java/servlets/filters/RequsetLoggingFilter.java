package servlets.filters;

import interceptors.RealContentCashingWrapper;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequsetLoggingFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RealContentCashingWrapper requestWrapper = new RealContentCashingWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }
}
