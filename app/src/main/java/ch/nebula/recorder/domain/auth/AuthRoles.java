package ch.nebula.recorder.domain.auth;


import io.javalin.core.security.Role;

public enum AuthRoles implements Role {
    ANYONE, AUTHENTICATED;
}