package org.dara.authenticationservice.utils;

import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.CurrentUser;
import org.dara.authenticationservice.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static CurrentUser getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) getAuthentication().getPrincipal();
        return userDetails.getCurrentUser();
    }
}
