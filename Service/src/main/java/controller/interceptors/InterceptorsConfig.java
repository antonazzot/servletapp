package controller.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorsConfig implements WebMvcConfigurer {

    private final LogingIntercepter logingIntercepter = new LogingIntercepter();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logingIntercepter);
    }
}
