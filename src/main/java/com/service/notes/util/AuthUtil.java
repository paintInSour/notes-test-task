package com.service.notes.util;

import com.service.notes.exception.AuthorizationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class AuthUtil {
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    // Simple auth resolver, in real application should be replaced by spring security auth filter with encrypted token
    public static Integer getUserIdFromAuthHeader(String authHeader) {
        if (!authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            throw new AuthorizationException("Wrong auth header");
        }
        try {

            String userIdString = authHeader.substring(AUTH_HEADER_PREFIX.length());
            return Integer.valueOf(userIdString);
        } catch (Exception e) {
            throw new AuthorizationException("Wrong auth header");
        }
    }
}
