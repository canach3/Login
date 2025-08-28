package LoginProject.login.auth.resolver;

import LoginProject.login.auth.annotation.CurrentUser;
import LoginProject.login.auth.exception.UnAuthorizedAccessException;
import LoginProject.login.auth.model.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @CurrentUser 애노테이션이 붙었고, 타입이 LoginMember일 때만 처리
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Object loginMember = request.getAttribute("loginMember");

        if (loginMember == null) {
            throw new UnAuthorizedAccessException();
        }

        return loginMember;
    }
}
