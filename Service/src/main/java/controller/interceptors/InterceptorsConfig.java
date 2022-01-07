package controller.interceptors;

import controller.interceptors.LogingIntercepter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.Access;
@Configuration
@RequiredArgsConstructor
public class InterceptorsConfig implements WebMvcConfigurer {

    private final LogingIntercepter logingIntercepter = new LogingIntercepter();
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logingIntercepter);
    }
}
