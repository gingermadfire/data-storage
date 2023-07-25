package com.gingermadfire.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    USER("user"),
    ADMIN("admin");

    private final String permission;

}
