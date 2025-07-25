package boardProject.board.config;

import boardProject.board.auth.resolver.LoginMemberArgumentResolver;
import boardProject.board.config.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// setAttribute, getAttribute 방법
//@Configuration
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//
//    private final LoginCheckInterceptor loginCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginCheckInterceptor)
//                .addPathPatterns("/**")        // 전체 경로에 적용
//                .excludePathPatterns("/api/auth/login", "/api/members/signup", "/css/**", "/js/**");
//    }
//}

// @CurrentUser 어노테이션 방식
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 리액트 dev 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true); // ✅ 쿠키 포함을 위해 꼭 필요
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")        // 전체 경로에 적용
                .excludePathPatterns("/api/auth/login", "/api/members/signup", "/css/**", "/js/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
}