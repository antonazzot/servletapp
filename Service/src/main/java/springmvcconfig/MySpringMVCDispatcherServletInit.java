package springmvcconfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import servlets.filters.AbstractFilter;
import servlets.filters.RequsetLoggingFilter;

import javax.servlet.Filter;

public class MySpringMVCDispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer  {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[] {
//                new AbstractFilter(),
//                new RequsetLoggingFilter(),
//
//        };
//    }
}