package umc.spring.validation.validator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import umc.spring.validation.annotation.CheckPage;

@Component
public class CheckPageArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CheckPage.class);
    }

    @Override
    public Integer resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String pageParam = request.getParameter("page");

        if (StringUtils.isEmpty(pageParam)) {
            throw new PageValidationException("Page parameter is required");
        }

        try {
            int page = Integer.parseInt(pageParam);

            // Adjust the page range (convert 1-based to 0-based)
            page--;

            // Validate the page range
            if (page < 0) {
                throw new PageValidationException("Page cannot be less than 1");
            }

            // Convert to Integer
            return page;
        } catch (NumberFormatException e) {
            throw new PageValidationException("Invalid page parameter");
        }
    }

    public static class PageValidationException extends RuntimeException {
        public PageValidationException(String message) {
            super(message);
        }
    }
}
