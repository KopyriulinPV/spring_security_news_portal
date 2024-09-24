package com.example.news_portal.aopSpringSecurity;

import com.example.news_portal.model.Role;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class VerificationUserSpring {

    @Autowired
    UserRepository userRepository;

    @Around("@annotation(UserVerificationSpring)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Around method: {} is called", proceedingJoinPoint.getSignature().getName());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, Object>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        User userAuthenticated = userRepository.findByUserName(request.getRemoteUser()).get();

        User theRrequestUser = userRepository.findById(Long.parseLong(pathVariables.get("id").toString())).get();
        List<Role> roles = userAuthenticated.getRoles().stream().filter(q -> q.getAuthority().equals(RoleType.ROLE_ADMIN)
                || q.getAuthority().equals(RoleType.ROLE_MODERATOR)).toList();

        if (roles.size() > 0) {
            log.info("ROLE_ADMIN, ROLE_MODERATOR");
            return proceedingJoinPoint.proceed();
        } else if (userAuthenticated.equals(theRrequestUser)) {
            return proceedingJoinPoint.proceed();
        } else {
            log.info("!!!!!!!!!!! proceedingJoinPoint.proceed() don't execute");
            return null;
        }



    }
}
