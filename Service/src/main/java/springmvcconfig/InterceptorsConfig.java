package springmvcconfig;

import interceptors.LogingIntercepter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@RequiredArgsConstructor
public class InterceptorsConfig implements WebMvcConfigurer {

    private final LogingIntercepter logingIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logingIntercepter);
    }
}
