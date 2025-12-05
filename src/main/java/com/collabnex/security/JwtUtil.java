package com.collabnex.security;


import com.collabnex.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

    public static Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;

        Object principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();   // <-- YOU ALREADY HAVE getId()
        }

        return null;
    }
}