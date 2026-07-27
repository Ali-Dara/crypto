package org.dara.authenticationservice.model;

public record LoginEvent (Long userId, String username) {}
